drop table if exists dbo.Estadisticas_Externas_Accesos
drop table if exists dbo.Publicaciones
drop table if exists dbo.Publicidades
drop table if exists dbo.Servicios_Exposicion

create table dbo.Publicidades(
	id_publicidad integer identity constraint PK_Id_Publicidad_END primary key,
	url_contenido varchar(255) not null,
	url_imagen varchar(MAX) not null
)

create table dbo.Servicios_Exposicion(
	id_servicio integer identity constraint PK_Id_Servicio_END primary key,
	nombre varchar(50) not null,
	token varchar(255) not null
)

create table dbo.Publicaciones(
	id_publicacion integer identity constraint PK_Id_Publicacion_END primary key,
	id_publicidad integer constraint FK_Publicidad_Publicacion_END references dbo.Publicidades(id_publicidad),
	id_servicio integer constraint FK_Servicio_Publicacion_END references dbo.Servicios_Exposicion(id_servicio),
	banner_code integer not null,
	fecha_inicio date not null,
	fecha_fin date not null,
	codigo_unico_id integer not null
)
go

create table dbo.Estadisticas_Externas_Accesos(
	id_estadistica integer identity constraint PK_Id_Estadistica_END primary key,
	id_publicacion integer constraint FK_Publicacion_Estadistica_END references dbo.Publicaciones(id_publicacion),
	fecha_acceso datetime not null
)
go

---------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE dbo.validar_token
    @token varchar(255),
    @resultado bit OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @cantidad_servicios integer;

    -- Verificar si el token existe y obtener el ID del servicio
    SELECT @cantidad_servicios = COUNT(id_servicio)
    FROM dbo.Servicios_Exposicion
    WHERE token = @token;

    -- Si encontramos exactamente una fila con el token, devolver TRUE; de lo contrario, devolver FALSE
    IF @cantidad_servicios = 1
    BEGIN
        SET @resultado = 1; -- TRUE
    END
    ELSE
    BEGIN
        SET @resultado = 0; -- FALSE
    END
END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_publicidades
    @token_servicio varchar(255)
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @fecha_actual date = GETDATE();

    -- Obtener las publicidades activas para el servicio asociado al token
    SELECT
        p.banner_code,
        pu.url_imagen,
        pu.url_contenido,
		p.codigo_unico_id
    FROM
        dbo.Publicaciones p
    INNER JOIN
        dbo.Publicidades pu ON p.id_publicidad = pu.id_publicidad
    INNER JOIN
        dbo.Servicios_Exposicion s ON p.id_servicio = s.id_servicio
    WHERE
        s.token = @token_servicio
        AND @fecha_actual BETWEEN p.fecha_inicio AND p.fecha_fin;
END;
go

create or alter procedure dbo.insertar_estadisticas_externas
	@json nvarchar(max)
as
begin
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	create table #Estadisticas(
		codigo_unico_id integer,
		fecha_acceso datetime,
	)

	insert into #Estadisticas
	select codigo_unico_id, fecha_acceso
	from openjson(@json) with (
		codigo_unico_id integer '$.codigo_unico_id',
		fecha_acceso datetime '$.fecha_acceso'
	)
	
	insert into dbo.Estadisticas_Externas_Accesos (id_publicacion, fecha_acceso)
	select (
		select p.id_publicacion 
		from dbo.Publicaciones p 
		where p.codigo_unico_id = e.codigo_unico_id ) as id_publicacion, 
		e.fecha_acceso
	from #Estadisticas e
end
go

/*Sólo para JPG*/
create or alter procedure dbo.registrar_acceso_publicitario
	@codigo_unico_id integer
AS
begin
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	declare @id_publicacion integer;
	set @id_publicacion = 0;

	select @id_publicacion = p.id_publicacion
	from dbo.Publicaciones p
	where p.codigo_unico_id = @codigo_unico_id

	if @id_publicacion = 0
	BEGIN
		RAISERROR('No se encontró la publicidad asociada al acceso publicitario a registrar', 16, 0)
	end

	insert into dbo.Estadisticas_Externas_Accesos (id_publicacion, fecha_acceso)
	values (@id_publicacion, GETDATE())

	if @@ROWCOUNT = 0
	BEGIN
		RAISERROR('No se pudo insertar',16,0)
	END
END
go
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--                                                                   DATOS
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Sietesentidos:
--	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InNpZXRlc2VudGlkb3MifQ.AeHMr9DjJt9dC37D8kHa4TllK7GQjPqzqEIg3h3Aneg
--JPG:
--	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkpQRyJ9.Lo1_nZVLhP09NtnWQJ4dCH5AITyViPYZrz0Ko2BJDJY
--Positivo:
--	eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InBvc2l0aXZvIn0.Q4ihAhMVdfHN0_EueMizfpTMLftozw9NApTNxeIctPA

/*sietesentidos*/
insert into dbo.Servicios_Exposicion (nombre, token) 
values ('MSS', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InNpZXRlc2VudGlkb3MifQ.AeHMr9DjJt9dC37D8kHa4TllK7GQjPqzqEIg3h3Aneg')

insert into dbo.Publicidades (url_contenido, url_imagen)
values ('https://www.princessauto.com', 'https://www.atvmag.com/wp-content/uploads/2021/11/PrincessAuto-Background-2019-e1648965668976.jpg'), --V
		('https://www.avon.com.ar/','https://i.ebayimg.com/images/g/7z4AAOSwfqBhgrGI/s-l1600.png') --V

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 1001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 1)


--JPG
insert into dbo.Servicios_Exposicion (nombre, token) 
values ('MSS', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkpQRyJ9.Lo1_nZVLhP09NtnWQJ4dCH5AITyViPYZrz0Ko2BJDJY')

insert into dbo.Publicidades (url_contenido, url_imagen)
values ('https://store.steampowered.com/app/1608230/Planet_of_Lana/', 'https://drws-production.s3.amazonaws.com/uploads/screenshot/9041/thumb_api-20210622-10-19oaq3e.jpg'), --V
		('https://www.bmw.com.ar/es/index.html','https://di-uploads-pod28.dealerinspire.com/bmwofalbany/uploads/2023/06/BMWAlbany_i5_Banners_CTA_Update-1344x150_09-23.jpg') --H

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 2001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 2)



--positivo
insert into dbo.Servicios_Exposicion (nombre, token) 
values ('MSS', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InBvc2l0aXZvIn0.Q4ihAhMVdfHN0_EueMizfpTMLftozw9NApTNxeIctPA')

insert into dbo.Publicidades (url_contenido, url_imagen)
values ('https://www.usgolfpro.com', 'https://www.usgolfpro.com/wp-content/uploads/2017/03/USGolfPro_com-logo.jpg'), --H
		('https://www.coca-cola.com/ar/es/brands/fanta','https://www.google.com/imgres?imgurl=https%3A%2F%2Fmineralka.store%2Fwa-data%2Fpublic%2Fshop%2Fproducts%2F22%2F79%2F7922%2Fimages%2F9673%2F9673.970.png&tbnid=qsLKWSVzK8FvKM&vet=12ahUKEwjzo-jjwsqEAxXEUbgEHc95AssQMygvegUIARCPAQ..i&imgrefurl=https%3A%2F%2Fmineralka.store%2Ffanta--fanta-05l24-pet-kazakh%2F&docid=N4EOHN4ARk6g0M&w=270&h=970&q=imagesize%3A270x970&ved=2ahUKEwjzo-jjwsqEAxXEUbgEHc95AssQMygvegUIARCPAQ') --V

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 3001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 3)

