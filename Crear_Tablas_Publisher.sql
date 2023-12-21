drop table if exists dbo.Estadisticas_Externas_Accesos
drop table if exists dbo.Publicaciones
drop table if exists dbo.Publicidades
drop table if exists dbo.Servicios_Exposicion

create table dbo.Publicidades(
	id_publicidad integer identity constraint PK_Id_Publicidad_END primary key,
	url_contenido varchar(255) not null,
	url_imagen varchar(255) not null
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
	cant_accesos integer not null default 0,
	fecha_inicio date not null,
	fecha_fin date not null
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
		cant_accesos integer,
		fecha_inicio date,
		fecha_fin date
	)

	insert into #Estadisticas
	select codigo_unico_id, cant_accesos, fecha_inicio, fecha_fin
	from openjson(@json) with (
		codigo_unico_id integer '$.codigo_unico_id',
		cant_accesos integer '$.cant_accesos',
		fecha_inicio date '$.fecha_inicio',
		fecha_fin date '$.fecha_fin'
	)

	insert into dbo.Estadisticas_Externas_Accesos (id_publicacion, cant_accesos, fecha_inicio, fecha_fin)
	select (
		select p.id_publicacion 
		from dbo.Publicaciones p 
		where p.codigo_unico_id = e.codigo_unico_id 
		and GETDATE() between p.fecha_inicio and p.fecha_fin) as id_publicacion, 
		e.cant_accesos, e.fecha_inicio, e.fecha_fin
	from #Estadisticas e
end
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
values ('https://www.ubp.edu.ar/', 'https://lawapa.com.ar/02-2021/resize_1613736109.jpg'), --V
		('https://www.unc.edu.ar/','https://sociales.unc.edu.ar/sites/default/files/Lotman%20revisitado.jpg') --V

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 1001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 1)


--JPG
insert into dbo.Servicios_Exposicion (nombre, token) 
values ('MSS', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkpQRyJ9.Lo1_nZVLhP09NtnWQJ4dCH5AITyViPYZrz0Ko2BJDJY')

insert into dbo.Publicidades (url_contenido, url_imagen)
values ('https://store.steampowered.com/', 'https://cl.buscafs.com/www.qore.com/public/uploads/images/98372/98372.png'), --V
		('https://www.microsoft.com/es-es/software-download/windows10','https://i.blogs.es/3a9f3d/win10/1366_2000.jpg') --H

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 2001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 2)



--positivo
insert into dbo.Servicios_Exposicion (nombre, token) 
values ('MSS', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InBvc2l0aXZvIn0.Q4ihAhMVdfHN0_EueMizfpTMLftozw9NApTNxeIctPA')

insert into dbo.Publicidades (url_contenido, url_imagen)
values ('https://www.verisure.com.ar/', 'https://www.verisure.com.ar/sites/ar/files/flmngr/kit-home-24_1.png?no-cache=1703009254287'), --H
		('https://www.fiat.com.ar/','https://c8.alamy.com/compes/2awap6d/1959-ca-torino-italia-la-industria-automovilistica-italiana-fiat-f-i-a-t-fabbrica-italiana-automobili-torino-publicidad-para-fiat-500-automatico-automatico-2awap6d.jpg') --V

insert into dbo.Publicaciones (id_publicidad, id_servicio, banner_code, fecha_inicio, fecha_fin, codigo_unico_id)
values (1, 1, 3001, GETDATE(), DATEADD(MONTH,1,GETDATE()), 3)

