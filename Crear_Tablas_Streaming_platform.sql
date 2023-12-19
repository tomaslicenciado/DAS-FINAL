/*Eliminar tablas anteriores*/
drop table if exists dbo.Direcciones
drop table if exists dbo.Actuaciones
drop table if exists dbo.Almacenamientos_Contenidos
drop table if exists dbo.Visualizaciones
drop table if exists dbo.Likes_Viewers
drop table if exists dbo.Contenidos
drop table if exists dbo.Generos_Contenidos
drop table if exists dbo.Tipos_Contenidos
drop table if exists dbo.Paises_Contenidos
drop table if exists dbo.Personas
drop table if exists dbo.Sesiones
drop table if exists dbo.Logins
drop table if exists dbo.Viewers
drop table if exists dbo.Facturas_Servicios
drop table if exists dbo.Servicios_Externos
drop table if exists dbo.Detalles_Facturas
drop table if exists dbo.Transacciones
drop table if exists dbo.Facturas
drop table if exists dbo.Tipos_Facturas
drop table if exists dbo.Tarifas

/*Crear tablas*/
create table dbo.Personas(
	id_persona integer identity constraint PK_Id_Persona_END primary key,
	nombres varchar(50) not null,
	apellidos varchar(50) not null
)

create table dbo.Generos_Contenidos(
	id_genero integer identity constraint PK_Id_Genero_Contenido_END primary key,
	genero varchar(50) not null
)

create table dbo.Tipos_Contenidos(
	id_tipo_contenido integer identity constraint PK_Id_Tipo_Contenido_END primary key,
	tipo varchar(50) not null
)

create table dbo.Paises_Contenidos(
	id_pais integer identity constraint PK_Id_Pais_Contenido_END primary key,
	pais varchar(50) not null
)

create table dbo.Contenidos(
	eidr_contenido varchar(255) not null constraint PK_Eidr_Contenido_END primary key,
	titulo varchar(50) not null,
	url_imagen varchar(255),
	descripcion varchar(255),
	fecha_estreno date not null,
	id_genero integer constraint FK_Genero_Contenido_END references dbo.Generos_Contenidos(id_genero),
	id_tipo_contenido integer constraint FK_Tipo_Contenido_END references dbo.Tipos_Contenidos(id_tipo_contenido),
	id_pais integer constraint FK_Pais_Contenido_END references dbo.Paises_Contenidos(id_pais),
	destacado bit not null default 0,
	fecha_carga date not null,
	activo bit not null default 1
)

create table dbo.Direcciones(
	id_direccion integer identity constraint PK_Id_Direccion_END primary key,
	id_persona integer constraint FK_Persona_Direccion_END references dbo.Personas(id_persona),
	eidr_contenido varchar(255) constraint FK_Contenido_Direccion_END references dbo.Contenidos(eidr_contenido)
)

create table dbo.Actuaciones(
	id_actuacion integer identity constraint PK_Id_Actuacion_END primary key,
	id_persona integer constraint FK_Persona_Actuacion_END references dbo.Personas(id_persona),
	eidr_contenido varchar(255) constraint FK_Contenido_Actuacion_END references dbo.Contenidos(eidr_contenido)
)

create table dbo.Almacenamientos_Contenidos(
	id_alamacenamiento integer identity constraint PK_Id_Almacenamiento_END primary key,
	eidr_contenido varchar(255) constraint FK_Contenido_Almacenamiento_END references dbo.Contenidos(eidr_contenido),
	url_contenido varchar(255) not null,
	activo bit not null default 1
)

create table dbo.Viewers(
	id_viewer integer identity constraint PK_Id_Viewer_END primary key,
	nombres varchar(50) not null,
	apellidos varchar(50) not null,
	email varchar(50) not null,
	v_password varchar(50) not null,
	activo bit not null default 1
)

create table dbo.Likes_Viewers(
	id_like integer identity constraint PK_Id_Like_END primary key,
	eidr_contenido varchar(255) constraint FK_Contenido_Like_END references dbo.Contenidos(eidr_contenido),
	id_viewer integer constraint FK_Viewer_Like_END references dbo.Viewers(id_viewer),
	fecha_like datetime not null,
	activo bit not null default 1
)

create table dbo.Visualizaciones(
	id_visualizacion integer identity constraint PK_Id_Visualizacion_END primary key,
	eidr_contenido varchar(255) constraint FK_Contenido_Visualizacion_END references dbo.Contenidos(eidr_contenido),
	id_viewer integer constraint FK_Viewer_Visualizacion_END references dbo.Viewers(id_viewer),
	fecha_visualizacion datetime not null
)

create table dbo.Servicios_Externos(
	id_servicio integer identity constraint PK_Id_Servicio_END primary key,
	nombre varchar(50) not null,
	razon_social varchar(50) not null,
	token_servicio varchar(255) not null
)

create table dbo.Logins(
	id_login integer identity constraint PK_Id_Login_END primary key,
	id_viewer integer constraint FK_Viewer_Login_END references dbo.Viewers(id_viewer),
	token varchar(255),
	fecha_creacion datetime not null,
	fecha_expiracion datetime,
	fecha_baja datetime,
	transaction_id varchar(255),
	new bit,
	url_retorno varchar(255),
	id_servicio integer constraint FK_Servicio_Login_END references dbo.Servicios_Externos(id_servicio)
)

create table dbo.Sesiones(
	id_sesion integer identity constraint PK_Id_Sesion_END primary key,
	id_login integer constraint FK_Login_Sesion_END references dbo.Logins(id_login),
	sesion varchar(255),
	fecha_creacion datetime,
	fecha_vencimiento datetime,
	fecha_uso datetime,
	fecha_baja datetime
)

create table dbo.Tipos_Facturas(
	id_tipo_factura integer identity constraint PK_Id_Tipo_Factura_END primary key,
	tipo varchar(50) not null
)

create table dbo.Facturas(
	id_factura integer identity constraint PK_Id_Factura_END primary key,
	fecha_facturacion date not null,
	fecha_inicial date not null,
	fecha_final date not null,
	monto_total float not null default 0,
	id_tipo_factura integer constraint FK_Tipo_Factura_END references dbo.Tipos_Facturas(id_tipo_factura)
)

create table dbo.Facturas_Servicios(
	id_factura integer constraint FK_Factura_Servicio_END references dbo.Facturas(id_factura),
	id_servicio integer constraint FK_Servicio_Factura_END references dbo.Servicios_Externos(id_servicio),
	constraint PK_Primary_Key_Factura_Servicoo_END primary key (id_factura, id_servicio)
)

create table dbo.Tarifas(
	id_tarifa integer identity constraint PK_Id_Tarifa_END primary key,
	descripcion varchar(50) NOT NULL,
	monto float not null default 0
)

create table dbo.Detalles_Facturas(
	id_detalle integer identity constraint PK_Id_Detalle_Factura_END primary key,
	id_factura integer constraint FK_Factura_Detalle_END references dbo.Facturas(id_factura),
	id_tarifa integer constraint FK_Tarifa_Detalle_END references dbo.Tarifas(id_tarifa),
	cantidad integer not null default 1,
	subtotal float not null default 0,
	descripcion varchar(50)
)

create table dbo.Transacciones(
	id_transaccion integer identity constraint PK_Id_Transaccion_END primary key,
	id_factura integer constraint FK_Factura_Transaccion_END references dbo.Facturas(id_factura),
	monto float not null default 0
)
go

-----------------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE dbo.iniciar_login
    @token_servicio varchar(255),
    @transaction_id varchar(255),
    @url_retorno varchar(255),
    @id_login INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_servicio integer;

    -- Obtener el id de servicio asociado al token proporcionado
    SELECT @id_servicio = id_servicio
    FROM dbo.Servicios_Externos
    WHERE token_servicio = @token_servicio;

    -- Verificar si se encontró el id de servicio
    IF @id_servicio IS NOT NULL
    BEGIN
        -- Insertar un nuevo registro en la tabla logins
        INSERT INTO dbo.Logins (id_viewer, fecha_creacion, transaction_id, url_retorno, id_servicio)
        VALUES (null, GETDATE(), @transaction_id, @url_retorno, @id_servicio);
		
        SET @id_login = SCOPE_IDENTITY();
    END
	ELSE
	BEGIN
		SET @id_login = 0;
	END
END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_viewer_token
    @transaction_id varchar(255),
    @token_servicio varchar(255),
    @viewer_token varchar(255) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_servicio integer;
    
    -- Obtener el id de servicio asociado al token de servicio proporcionado
    SELECT @id_servicio = id_servicio
    FROM dbo.Servicios_Externos
    WHERE token_servicio = @token_servicio;

    -- Verificar si se encontró el id de servicio
    IF @id_servicio IS NOT NULL
    BEGIN
        -- Obtener el token de la tabla Logins asociado al transaction_id y al id_servicio correspondiente
        SELECT @viewer_token = token
        FROM dbo.Logins
        WHERE transaction_id = @transaction_id AND id_servicio = @id_servicio;
    END
    ELSE
    BEGIN
        -- Si no se encuentra el id de servicio, asignar un valor nulo al viewer_token
        SET @viewer_token = NULL;
    END
END;
go 

CREATE OR ALTER PROCEDURE dbo.validar_token_servicio
    @token varchar(255),
    @resultado bit OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @cantidad_servicios integer;

    -- Verificar si el token existe y obtener el ID del servicio
    SELECT @cantidad_servicios = COUNT(id_servicio)
    FROM dbo.Servicios_Externos

    WHERE token_servicio = @token;

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

create or alter procedure dbo.viewer_nuevo
	@transaction_id varchar(255),
	@token_servicio varchar(255),
	@login_completo bit output,
	@resultado bit output
as
begin
	set nocount on;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	declare @id_viewer integer;
	DECLARE @id_servicio integer;

	set @login_completo = 0;
    
    -- Obtener el id de servicio asociado al token de servicio proporcionado
    SELECT @id_servicio = id_servicio
    FROM dbo.Servicios_Externos
    WHERE token_servicio = @token_servicio;

    -- Verificar si se encontró el id de servicio
    IF @id_servicio IS NOT NULL
	begin
		select @resultado = new, @id_viewer = id_viewer
		from dbo.Logins
		where transaction_id = @transaction_id;

		if @resultado is null
		begin
			set @resultado = 0
		end;

		if @id_viewer > 0
		begin
			set @login_completo = 1
		end
	end
end;
go

CREATE OR ALTER PROCEDURE dbo.obtener_contenidos
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    SELECT
        c.eidr_contenido,
        c.titulo,
        c.url_imagen,
        c.descripcion,
        c.fecha_estreno,
        gc.genero AS genero,
        pc.pais AS pais,
        tc.tipo AS tipo_contenido,
		c.destacado,
		c.fecha_carga
    FROM dbo.Contenidos c
    LEFT JOIN dbo.Generos_Contenidos gc ON c.id_genero = gc.id_genero
    LEFT JOIN dbo.Tipos_Contenidos tc ON c.id_tipo_contenido = tc.id_tipo_contenido
    LEFT JOIN dbo.Paises_Contenidos pc ON c.id_pais = pc.id_pais
    WHERE c.activo = 1;
END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_actuaciones
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    SELECT
        p.nombres,
        p.apellidos,
        a.eidr_contenido
    FROM dbo.Actuaciones a
    INNER JOIN dbo.Personas p ON a.id_persona = p.id_persona
    INNER JOIN dbo.Contenidos c ON a.eidr_contenido = c.eidr_contenido
    WHERE c.activo = 1;
END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_direcciones
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    SELECT
        p.nombres,
        p.apellidos,
        d.eidr_contenido
    FROM dbo.Direcciones d
    INNER JOIN dbo.Personas p ON d.id_persona = p.id_persona
    INNER JOIN dbo.Contenidos c ON d.eidr_contenido = c.eidr_contenido
    WHERE c.activo = 1;
END;
go

CREATE OR ALTER PROCEDURE dbo.usar_sesion
    @sesion varchar(255)
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Actualizar la fecha de uso de la sesión
    UPDATE dbo.Sesiones
    SET fecha_uso = GETDATE()
    WHERE sesion = @sesion;
END;
go

CREATE OR ALTER PROCEDURE dbo.sesion_valida
	@token_servicio varchar(255),
    @sesion varchar(255),
    @resultado INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	DECLARE @id_login INTEGER;

	SELECT @id_login = l.id_login
	FROM dbo.Logins l
	LEFT JOIN dbo.Servicios_Externos s ON s.id_servicio = l.id_servicio
	WHERE s.token_servicio = @token_servicio

	SELECT @resultado = CASE
								WHEN fecha_uso IS NOT NULL THEN 2 -- Sesión usada
								WHEN fecha_vencimiento < GETDATE() THEN 3 -- Sesión vencida
								WHEN fecha_baja IS NOT NULL THEN 4 -- Sesión dada de baja
								WHEN id_sesion IS NOT NULL THEN 1 -- Sesión válida
								ELSE 5 -- Sesion inexistente
							END
	FROM dbo.Sesiones
	WHERE sesion = @sesion AND id_login = @id_login;
END;
go

CREATE OR ALTER PROCEDURE dbo.validar_token_viewer
    @token_viewer varchar(255),
	@token_servicio varchar(255),
    @resultado bit OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @cantidad_logins integer;
	DECLARE @id_servicio integer;

	SELECT @id_servicio = id_servicio
	FROM dbo.Servicios_Externos
	WHERE token_servicio = @token_servicio

    -- Verificar si el token existe y obtener el ID del servicio
    SELECT @cantidad_logins = COUNT(l.id_login)
    FROM dbo.Logins l
    WHERE l.token = @token_viewer 
	AND l.fecha_baja IS NULL
	AND l.fecha_expiracion > GETDATE()
	AND l.fecha_creacion < GETDATE()
	AND l.id_servicio = @id_servicio;

    -- Si encontramos exactamente una fila con el token, devolver TRUE; de lo contrario, devolver FALSE
    IF @cantidad_logins = 1
    BEGIN
        SET @resultado = 1; -- TRUE
    END
    ELSE
    BEGIN
        SET @resultado = 0; -- FALSE
    END
END;
go

CREATE OR ALTER PROCEDURE dbo.insertar_sesion
    @token_viewer VARCHAR(255),
    @token_servicio VARCHAR(255),
    @sesion VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_servicio INTEGER;
    DECLARE @id_login INTEGER;

    -- Obtener el ID del servicio
    SELECT @id_servicio = id_servicio
    FROM dbo.Servicios_Externos
    WHERE token_servicio = @token_servicio;

    -- Obtener el ID del login
    SELECT @id_login = id_login
    FROM dbo.Logins
    WHERE token = @token_viewer AND id_servicio = @id_servicio;

    -- Insertar la sesión con fecha de vencimiento media hora después de la creación
    INSERT INTO dbo.Sesiones (id_login, sesion, fecha_creacion, fecha_vencimiento, fecha_uso, fecha_baja)
    VALUES (
        @id_login,
        @sesion,
        GETDATE(),
        DATEADD(MINUTE, 30, GETDATE()), -- Vencimiento en media hora
        NULL,
        NULL

    );
END;
go

create or alter procedure dbo.obtener_contenido
	@eidr_contenido varchar(255)
as
begin
	set nocount on;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	select url_contenido
	from dbo.Almacenamientos_Contenidos
	where eidr_contenido = @eidr_contenido
	and activo = 1
end;
go

CREATE OR ALTER PROCEDURE dbo.validar_id_login
    @id_login INT,
    @valido bit OUTPUT,
    @id_servicio INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Inicializar las variables de salida
    SET @valido = 0;
    SET @id_servicio = 0;

    -- Obtener la información del login
    SELECT @valido = 1,
           @id_servicio = l.id_servicio
    FROM dbo.Logins l
    WHERE l.id_login = @id_login
          AND l.fecha_baja IS NULL  -- No está dado de baja
          AND (l.fecha_expiracion IS NULL OR l.fecha_expiracion > GETDATE()) -- No está expirado
		  AND (l.id_viewer IS NULL OR l.id_viewer = 0); --No se encuentra en uso
END;
go

CREATE OR ALTER PROCEDURE dbo.login_viewer
    @id_login INT,
    @email VARCHAR(50),
    @password VARCHAR(50),
    @token VARCHAR(255),
    @id_servicio INT,
    @url_retorno VARCHAR(255) OUTPUT,
    @is_new BIT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_viewer INT;

    -- Validar el email y contraseña en la tabla viewers
    SELECT @id_viewer = id_viewer
    FROM dbo.Viewers
    WHERE email = @email AND v_password = @password;

    IF @id_viewer IS NULL
    BEGIN
        SET @url_retorno = NULL;
    END
    ELSE
    BEGIN
        -- Actualizar la tabla login
        UPDATE dbo.Logins
        SET id_viewer = @id_viewer,
            token = @token,
            new = @is_new,
			fecha_expiracion = DATEADD(DAY, 30, fecha_creacion)
        WHERE id_login = @id_login AND id_servicio = @id_servicio;

        -- Obtener la url de retorno
        SELECT @url_retorno = url_retorno
        FROM dbo.Logins
        WHERE id_login = @id_login AND id_servicio = @id_servicio;
    END;
END;
go

CREATE OR ALTER PROCEDURE dbo.insertar_viewer
    @nombres VARCHAR(50),
    @apellidos VARCHAR(50),
    @email VARCHAR(50),
    @password VARCHAR(50),
    @id_viewer INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Declarar una variable para almacenar el nuevo id_viewer
    DECLARE @new_id_viewer INT;

    -- Insertar el nuevo viewer
    INSERT INTO dbo.Viewers (nombres, apellidos, email, v_password)
    VALUES (@nombres, @apellidos, @email, @password);

    -- Obtener el nuevo id_viewer
    SELECT @new_id_viewer = SCOPE_IDENTITY();

    -- Asignar el valor a la variable de salida
    SET @id_viewer = @new_id_viewer;
	IF @id_viewer IS NULL
	BEGIN
		SET @id_viewer = 0
	END
END;
go

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--                                                                   DATOS
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--HBO
-- eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkhCTyIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.6CnlRhfy3tEzsk6Yt-yJ2sNfNrDkXUoZOC5y51lmUW0
--Netflix
-- eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6Ik5ldGZsaXgiLCJxdWVsb3F1ZSI6InBsYXRhZm9ybWEifQ._h5nT3TxxNjEo2O0VBoV4humz7KUtfISOCSj2wTkAAg
--Prime
-- eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IlByaW1lIiwicXVlbG9xdWUiOiJwbGF0YWZvcm1hIn0.o1SW91Ztlwo1xSpqMGHur0AODep-so3Ahf-xgGrFKFA
--Disney
-- eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkRpc25leSIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.NBAinIs17FbbTjyEBLuE4RO-kzotWfhQxjTAOeHJaL4


--HBO
insert into dbo.Servicios_Externos (nombre, razon_social, token_servicio)
values ('MSS', 'Multiplataforma de Sistemas de Streaming SA', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkhCTyIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.6CnlRhfy3tEzsk6Yt-yJ2sNfNrDkXUoZOC5y51lmUW0')

insert into dbo.Personas (nombres, apellidos)
values
  ('Ellen', 'Pompeo'),
  ('Sandra', 'Oh'),
  ('Katherine', 'Haigl'),
  ('Shonda', 'Rhimes'),
  ('Vin', 'Diesel'),
  ('Paul', 'Walker'),
  ('Dwayne', 'Johnson'),
  ('Michelle', 'Rodriguez'),
  ('Justin', 'Lin'),
  ('Peter', 'O`Toole'),
  ('Malcom', 'McDowell'),
  ('Helen', 'Mirren'),
  ('Tinto', 'Brass');

insert into dbo.Generos_Contenidos (genero) values ('Drama'),('Acción'),('Erótica')

insert into dbo.Tipos_Contenidos (tipo) values ('Películas'), ('Series')

insert into dbo.Paises_Contenidos (pais) values ('EEUU'),('Italia')

insert into dbo.Contenidos (eidr_contenido, titulo, descripcion, url_imagen, fecha_estreno, id_genero, id_tipo_contenido, id_pais, fecha_carga, destacado)
values ('10.5240/FC9B-BB73-4B80-E953-D8A2-U','Grey`s Anatomy','La serie se centra en la vida de los cirujanos internos, residentes y especialistas a medida de que se convierten en médicos cirujanos experimentados mientras tratan de equilibrar sus relaciones personales y profesionales. ','https://mx.web.img3.acsta.net/pictures/22/10/06/21/38/5529680.jpg',CONVERT(DATE,'2005-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Drama'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Series'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2022-01-01'),0),
('10.5240/21A6-ED2A-A2C0-619A-9746-4','Rápidos y Furiosos 6','Después de su exitoso golpe en Río de Janeiro, Brasil, Dominic "Dom" Toretto (Vin Diesel) y Brian O`Conner (Paul Walker) están en una carrera en las Islas Canarias, España, hasta llegar a un hospital, ya que el hijo de Brian está a punto de nacer. ','https://m.media-amazon.com/images/S/pv-target-images/2a711d2b91761c98205aa9f8b62e8cb47f423cc8f7697bfac5e76b71af850fda.jpg',CONVERT(DATE,'2013-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Acción'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),GETDATE(),1),
('10.5240/7C73-9E06-C6D2-3C33-4DFC-5','Calígula','El impopular emperador romano Tiberio, quien se encuentra enfermo de sífilis y con una salud frágil, también padece de inestabilidad mental y lleva años autoexiliado de Roma','https://es.web.img3.acsta.net/medias/nmedia/18/83/26/26/20533346.jpg',CONVERT(DATE,'1979-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Erótica'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'Italia'),CONVERT(DATE,'2015-01-01'),0)

insert into dbo.Direcciones (eidr_contenido, id_persona)
values ('10.5240/FC9B-BB73-4B80-E953-D8A2-U',(select id_persona from dbo.Personas where nombres = 'Shonda' and apellidos = 'Rhimes')),
('10.5240/21A6-ED2A-A2C0-619A-9746-4',(select id_persona from dbo.Personas where nombres = 'Justin' and apellidos = 'Lin')),
('10.5240/7C73-9E06-C6D2-3C33-4DFC-5',(select id_persona from dbo.Personas where nombres = 'Tinto' and apellidos = 'Brass'))

insert into dbo.Actuaciones (eidr_contenido, id_persona)
values ('10.5240/FC9B-BB73-4B80-E953-D8A2-U',(select id_persona from dbo.Personas where nombres = 'Ellen' and apellidos = 'Pompeo')),
('10.5240/FC9B-BB73-4B80-E953-D8A2-U',(select id_persona from dbo.Personas where nombres = 'Sandra' and apellidos = 'Oh')),
('10.5240/FC9B-BB73-4B80-E953-D8A2-U',(select id_persona from dbo.Personas where nombres = 'Katherine' and apellidos = 'Haigl')),
('10.5240/21A6-ED2A-A2C0-619A-9746-4',(select id_persona from dbo.Personas where nombres = 'Vin' and apellidos = 'Diesel')),
('10.5240/21A6-ED2A-A2C0-619A-9746-4',(select id_persona from dbo.Personas where nombres = 'Paul' and apellidos = 'Walker')),
('10.5240/21A6-ED2A-A2C0-619A-9746-4',(select id_persona from dbo.Personas where nombres = 'Dwayne' and apellidos = 'Johnson')),
('10.5240/21A6-ED2A-A2C0-619A-9746-4',(select id_persona from dbo.Personas where nombres = 'Michelle' and apellidos = 'Rodriguez')),
('10.5240/7C73-9E06-C6D2-3C33-4DFC-5',(select id_persona from dbo.Personas where nombres = 'Peter' and apellidos = 'O`Toole')),
('10.5240/7C73-9E06-C6D2-3C33-4DFC-5',(select id_persona from dbo.Personas where nombres = 'Malcom' and apellidos = 'McDowell')),
('10.5240/7C73-9E06-C6D2-3C33-4DFC-5',(select id_persona from dbo.Personas where nombres = 'Helen' and apellidos = 'Miiren'))

insert into dbo.Almacenamientos_Contenidos (eidr_contenido, url_contenido)
values ('10.5240/FC9B-BB73-4B80-E953-D8A2-U','https://www.youtube.com/watch?v=q1pcpgREQ5c&ab_channel=RachelIsabelle'),('10.5240/21A6-ED2A-A2C0-619A-9746-4','https://www.youtube.com/watch?v=yY6JUPCgDxU&ab_channel=UniversalSpain'),('10.5240/7C73-9E06-C6D2-3C33-4DFC-5','https://www.youtube.com/watch?v=WACkBTh0W6w&ab_channel=HDRetroTrailers')

--Netflix-----------------------------------------------------------------------------------------------------------------------------------------------------

insert into dbo.Servicios_Externos (nombre, razon_social, token_servicio)
values ('MSS', 'Multiplataforma de Sistemas de Streaming SA', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6Ik5ldGZsaXgiLCJxdWVsb3F1ZSI6InBsYXRhZm9ybWEifQ._h5nT3TxxNjEo2O0VBoV4humz7KUtfISOCSj2wTkAAg')

insert into dbo.Personas (nombres, apellidos)
values
  ('Brendan', 'Fraser'),
  ('Hong', 'Chou'),
  ('Sadie', 'Sink'),
  ('Darren', 'Aronofsky'),
  ('Timothée', 'Chalamet'),
  ('Rebecca', 'Ferguson'),
  ('Zendaya', 'Coleman'),
  ('Denis', 'Villeneuve'),
  ('Elijah', 'Wood'),
  ('Ian', 'McKellen'),
  ('Liv', 'Tyler'),
  ('Viggo', 'Mortensen'),
  ('Orlando', 'Bloom'),
  ('Peter', 'Jackson');

insert into dbo.Generos_Contenidos (genero) values ('Drama'),('Ciencia Ficción'),('Fantasía')

insert into dbo.Tipos_Contenidos (tipo) values ('Películas')

insert into dbo.Paises_Contenidos (pais) values ('EEUU')

insert into dbo.Contenidos (eidr_contenido, titulo, descripcion, url_imagen, fecha_estreno, id_genero, id_tipo_contenido, id_pais, fecha_carga, destacado)
values ('10.5240/DB77-E21B-22E7-F52D-2084-I','The Whale','Charlie (Brendan Fraser) es un profesor de inglés con 272 kg, se avergüenza de tener obesidad mórbida y tiene miedo de mostrar su apariencia a los estudiantes','https://resizer.glanacion.com/resizer/v2/la-ballena-el-gran-regreso-de-brendan-fraser-4INVGSI3TZCJZMOZMSV6JPPCGY.webp?auth=338dc5c17ea0ab21e38d1db4ca16a3deec5b88607758ff3e23c0d832cb16448e&width=768&quality=70&smart=false',CONVERT(DATE,'2022-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Drama'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2023-01-01'),1),
('10.5240/DA52-689A-551F-3066-2DEE-B','Dune','En el año 10191, el duque Leto de la Casa Atreides, gobernante del planeta oceánico Caladan, es asignado por el emperador Padishah Shaddam Corrino IV para reemplazar a la Casa Harkonnen como gobernante del feudo de Arrakis.','https://www.lavanguardia.com/files/content_image_mobile_filter/uploads/2021/09/01/612fa2a1f0ba5.jpeg',CONVERT(DATE,'2021-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Ciencia Ficción'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),GETDATE(),1),
('10.5240/A825-3C17-2202-0C96-A34C-P','El Señor de los Anillos','En la Segunda Edad de la Tierra Media, los señores de los Elfos, los Enanos y los Hombres reciben anillos de poder. Sin saberlo, el Señor Oscuro Sauron forja el anillo Único en el Monte del Destino','https://play-lh.googleusercontent.com/imeAs3_Nb9fyoj56LgLzSRBs3UXTZTH_TLg2xMkg6J90ZPzxscAXPvtsR9Q9azxe-WCy5A',CONVERT(DATE,'2001-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Fantasía'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2005-01-01'),0)

insert into dbo.Direcciones (eidr_contenido, id_persona)
values ('10.5240/DB77-E21B-22E7-F52D-2084-I',(select id_persona from dbo.Personas where nombres = 'Darren' and apellidos = 'Aronofsky')),
('10.5240/DA52-689A-551F-3066-2DEE-B',(select id_persona from dbo.Personas where nombres = 'Denis' and apellidos = 'Villeneuve')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Peter' and apellidos = 'Jackson'))

insert into dbo.Actuaciones (eidr_contenido, id_persona)
values ('10.5240/DB77-E21B-22E7-F52D-2084-I',(select id_persona from dbo.Personas where nombres = 'Brendan' and apellidos = 'Fraser')),
('10.5240/DB77-E21B-22E7-F52D-2084-I',(select id_persona from dbo.Personas where nombres = 'Hong' and apellidos = 'Chou')),
('10.5240/DB77-E21B-22E7-F52D-2084-I',(select id_persona from dbo.Personas where nombres = 'Sadie' and apellidos = 'Sink')),
('10.5240/DA52-689A-551F-3066-2DEE-B',(select id_persona from dbo.Personas where nombres = 'Timothée' and apellidos = 'Chalamet')),
('10.5240/DA52-689A-551F-3066-2DEE-B',(select id_persona from dbo.Personas where nombres = 'Rebecca' and apellidos = 'Ferguson')),
('10.5240/DA52-689A-551F-3066-2DEE-B',(select id_persona from dbo.Personas where nombres = 'Zendaya' and apellidos = 'Coleman')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Vigo' and apellidos = 'Mortensen')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Elijah' and apellidos = 'Wood')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Ian' and apellidos = 'McKellen')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Liv' and apellidos = 'Tyler')),
('10.5240/A825-3C17-2202-0C96-A34C-P',(select id_persona from dbo.Personas where nombres = 'Orlando' and apellidos = 'Bloom'))

insert into dbo.Almacenamientos_Contenidos (eidr_contenido, url_contenido)
values ('10.5240/DB77-E21B-22E7-F52D-2084-I','https://www.youtube.com/watch?v=i8zQKSI6wGU&ab_channel=CINECONECTA'),('10.5240/DA52-689A-551F-3066-2DEE-B','https://www.youtube.com/watch?v=TTgk_iT8Uts&ab_channel=WarnerBros.PicturesLatinoam%C3%A9rica'),('10.5240/A825-3C17-2202-0C96-A34C-P','https://www.youtube.com/watch?v=iFOucwxKRFE&ab_channel=CineFantasia')

--Prime

insert into dbo.Servicios_Externos (nombre, razon_social, token_servicio)
values ('MSS', 'Multiplataforma de Sistemas de Streaming SA', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IlByaW1lIiwicXVlbG9xdWUiOiJwbGF0YWZvcm1hIn0.o1SW91Ztlwo1xSpqMGHur0AODep-so3Ahf-xgGrFKFA')

insert into dbo.Personas (nombres, apellidos)
values
  ('Sean', 'Bean'),
  ('Emilia', 'Clarke'),
  ('Jason', 'Momoa'),
  ('Kit', 'Harington'),
  ('Tim', 'Van Patten'),
  ('Brian', 'Kirk'),
  ('Daniel', 'Minahan'),
  ('Alejo', 'García Pintos'),
  ('Pepe', 'Monje'),
  ('Vita', 'Escardó'),
  ('Héctor', 'Olivera'),
  ('Ricardo', 'Darín'),
  ('Gastón', 'Pauls'),
  ('Leticia', 'Brédice'),
  ('Tomas', 'Fonzi'),
  ('Fabián', 'Bielinsky');

insert into dbo.Generos_Contenidos (genero) values ('Fantasía'),('Histórica'),('Suspenso')

insert into dbo.Tipos_Contenidos (tipo) values ('Películas'), ('Series')

insert into dbo.Paises_Contenidos (pais) values ('EEUU'),('Argentina')

insert into dbo.Contenidos (eidr_contenido, titulo, descripcion, url_imagen, fecha_estreno, id_genero, id_tipo_contenido, id_pais, fecha_carga, destacado)
values ('10.5240/C1B5-3BA1-8991-A571-8472-W','Juego de Tronos','La primera temporada comienza quince años después de la guerra civil conocida como la «rebelión de Robert», con la cual Robert Baratheon expulsó del Trono de Hierro a los Targaryen y se proclamó gobernante de Poniente','https://es.web.img3.acsta.net/pictures/19/03/22/10/08/5883111.jpg?coixp=50&coiyp=40',CONVERT(DATE,'2011-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Fantasía'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Series'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2012-01-01'),0),
('10.5240/2FF5-DE47-6253-4E94-7A63-4','La Noche de los Lápices','Corre el año 1975 en Argentina, a los estudiantes de diferentes colegios se les quita el boleto estudiantil —con el que obtenían un importante descuento en la tarifa del viaje en colectivo— durante el gobierno de Isabel Martínez de Perón.','https://m.media-amazon.com/images/S/pv-target-images/47ed4001b825ca6a084a0f78a7c1b84945dded10d200d5399f97353d90b7984e.jpg',CONVERT(DATE,'1986-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Histórica'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'Argentina'),CONVERT(DATE,'2008-01-01'),0),
('10.5240/9B6C-ED71-5811-2A48-5809-T','Nueve Reinas','Cuenta la historia de dos estafadores que se conocen por casualidad y deciden unirse para trabajar juntos.','https://cloudfront-us-east-1.images.arcpublishing.com/infobae/WAIMDDPU2ZDL7FB62INBQA6PZY.jpg',CONVERT(DATE,'2000-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Suspenso'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'Argentina'),CONVERT(DATE,'2015-01-01'),0)

insert into dbo.Direcciones (eidr_contenido, id_persona)
values ('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Tim' and apellidos = 'Van Patten')),
('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Brian' and apellidos = 'Kirk')),
('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Daniel' and apellidos = 'Minahan')),
('10.5240/2FF5-DE47-6253-4E94-7A63-4',(select id_persona from dbo.Personas where nombres = 'Héctor' and apellidos = 'Olivera')),
('10.5240/9B6C-ED71-5811-2A48-5809-T',(select id_persona from dbo.Personas where nombres = 'Fabián' and apellidos = 'Bielinsky'))

insert into dbo.Actuaciones (eidr_contenido, id_persona)
values ('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Sean' and apellidos = 'Bean')),
('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Emilia' and apellidos = 'Clarke')),
('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Jason' and apellidos = 'Momoa')),
('10.5240/C1B5-3BA1-8991-A571-8472-W',(select id_persona from dbo.Personas where nombres = 'Kit' and apellidos = 'Harington')),
('10.5240/2FF5-DE47-6253-4E94-7A63-4',(select id_persona from dbo.Personas where nombres = 'Alejo' and apellidos = 'García Pintos')),
('10.5240/2FF5-DE47-6253-4E94-7A63-4',(select id_persona from dbo.Personas where nombres = 'Pepe' and apellidos = 'Monje')),
('10.5240/2FF5-DE47-6253-4E94-7A63-4',(select id_persona from dbo.Personas where nombres = 'Vita' and apellidos = 'Escardó')),
('10.5240/9B6C-ED71-5811-2A48-5809-T',(select id_persona from dbo.Personas where nombres = 'Ricardo' and apellidos = 'Darín')),
('10.5240/9B6C-ED71-5811-2A48-5809-T',(select id_persona from dbo.Personas where nombres = 'Gastón' and apellidos = 'Pauls')),
('10.5240/9B6C-ED71-5811-2A48-5809-T',(select id_persona from dbo.Personas where nombres = 'Leticia' and apellidos = 'Brédice')),
('10.5240/9B6C-ED71-5811-2A48-5809-T',(select id_persona from dbo.Personas where nombres = 'Tomas' and apellidos = 'Fonzi'))

insert into dbo.Almacenamientos_Contenidos (eidr_contenido, url_contenido)
values ('10.5240/C1B5-3BA1-8991-A571-8472-W','https://www.youtube.com/watch?v=KPLWWIOCOOQ&ab_channel=GameofThrones'),('10.5240/2FF5-DE47-6253-4E94-7A63-4','https://www.youtube.com/watch?v=Y41L4oZfWrg&ab_channel=SolPortillo'),('10.5240/9B6C-ED71-5811-2A48-5809-T','https://www.youtube.com/watch?v=O2D3rXfNjWw&ab_channel=Patagonik')

--Disney

insert into dbo.Servicios_Externos (nombre, razon_social, token_servicio)
values ('MSS', 'Multiplataforma de Sistemas de Streaming SA', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkRpc25leSIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.NBAinIs17FbbTjyEBLuE4RO-kzotWfhQxjTAOeHJaL4')

insert into dbo.Personas (nombres, apellidos)
values
  ('Ben', 'Burtt'),
  ('Elissa', 'Knight'),
  ('Sigourney', 'Weaver'),
  ('Andrew', 'Stanton'),
  ('Daveigh', 'Chase'),
  ('Chris', 'Sanders'),
  ('Tia', 'Carrere'),
  ('Dean', 'DeBlois'),
  ('Meryl', 'Streep'),
  ('Clint', 'Eastwood'),
  ('Bee', 'Vang');

insert into dbo.Generos_Contenidos (genero) values ('Drama'),('Aventura'),('Comedia')

insert into dbo.Tipos_Contenidos (tipo) values ('Películas')

insert into dbo.Paises_Contenidos (pais) values ('EEUU')

insert into dbo.Contenidos (eidr_contenido, titulo, descripcion, url_imagen, fecha_estreno, id_genero, id_tipo_contenido, id_pais, fecha_carga, destacado)
values ('10.5240/3240-057D-88D7-9A82-A5E5-V','Wall-E','En el siglo XXIX, específicamente en el año 2805, el consumismo desenfrenado, la codicia empresarial y la negligencia ambiental han convertido al planeta Tierra en un páramo lleno de basura','https://lumiere-a.akamaihd.net/v1/images/p_walle_19753_69f7ff00.jpeg',CONVERT(DATE,'2008-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Aventura'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2022-01-01'),0),
('10.5240/32B1-B567-14AC-65C2-B8EF-X','Lilo y Stich','La historia gira en torno a dos personajes: Lilo Pelekai (Daveigh Chase), una niña solitaria y un extraterrestre llamado Stitch','https://es.web.img3.acsta.net/pictures/14/03/27/11/39/176864.jpg',CONVERT(DATE,'2002-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Comedia'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),GETDATE(),1),
('10.5240/A87B-E087-FA21-D93C-66A4-U','Los Puentes de Madison','Una solitaria ama de casa del Medio Oeste natural de la ciudad de Bari (Italia), casada con un soldado estadounidense destinado en Italia y llegada con él a los Estados Unidos.','https://es.web.img2.acsta.net/medias/nmedia/18/68/46/37/20340605.jpg',CONVERT(DATE,'1995-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Drama'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2015-01-01'),0),
('10.5240/8212-4AA9-5A03-63AE-638B-W','Gran Torino','Walt Kowalski (Clint Eastwood) es un anciano veterano de la guerra de Corea y trabajador jubilado de la fábrica de Ford, de actitud amargada, quien acaba de enviudar','https://static.independent.co.uk/s3fs-public/thumbnails/image/2009/07/02/18/204860.jpg?width=1200',CONVERT(DATE,'2008-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Drama'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2015-01-01'),1)

insert into dbo.Direcciones (eidr_contenido, id_persona)
values ('10.5240/3240-057D-88D7-9A82-A5E5-V',(select id_persona from dbo.Personas where nombres = 'Andrew' and apellidos = 'Stanton')),
('10.5240/32B1-B567-14AC-65C2-B8EF-X',(select id_persona from dbo.Personas where nombres = 'Chris' and apellidos = 'Sanders')),
('10.5240/32B1-B567-14AC-65C2-B8EF-X',(select id_persona from dbo.Personas where nombres = 'Dean' and apellidos = 'DeBlois')),
('10.5240/A87B-E087-FA21-D93C-66A4-U',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood')),
('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood'))

insert into dbo.Actuaciones (eidr_contenido, id_persona)
values ('10.5240/3240-057D-88D7-9A82-A5E5-V',(select id_persona from dbo.Personas where nombres = 'Ben' and apellidos = 'Burtt')),
('10.5240/3240-057D-88D7-9A82-A5E5-V',(select id_persona from dbo.Personas where nombres = 'Elisa' and apellidos = 'Knight')),
('10.5240/3240-057D-88D7-9A82-A5E5-V',(select id_persona from dbo.Personas where nombres = 'Sigourney' and apellidos = 'Weaver')),
('10.5240/32B1-B567-14AC-65C2-B8EF-X',(select id_persona from dbo.Personas where nombres = 'Daveigh' and apellidos = 'Chase')),
('10.5240/32B1-B567-14AC-65C2-B8EF-X',(select id_persona from dbo.Personas where nombres = 'Chris' and apellidos = 'Sanders')),
('10.5240/32B1-B567-14AC-65C2-B8EF-X',(select id_persona from dbo.Personas where nombres = 'Tia' and apellidos = 'Carrere')),
('10.5240/A87B-E087-FA21-D93C-66A4-U',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood')),
('10.5240/A87B-E087-FA21-D93C-66A4-U',(select id_persona from dbo.Personas where nombres = 'Meryl' and apellidos = 'Streep')),
('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood')),
('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Bee' and apellidos = 'Vang'))

insert into dbo.Almacenamientos_Contenidos (eidr_contenido, url_contenido)
values ('10.5240/3240-057D-88D7-9A82-A5E5-V','https://www.youtube.com/watch?v=4rDD3SccHxQ&ab_channel=Gertson0000'),('10.5240/32B1-B567-14AC-65C2-B8EF-X','https://www.youtube.com/watch?v=_WbTe5hjSkE&ab_channel=JoyasDeLaAnimaci%C3%B3n'),('10.5240/A87B-E087-FA21-D93C-66A4-U','https://www.youtube.com/watch?v=UkohDUeP2tk&ab_channel=DVDmaniaMX'),('10.5240/8212-4AA9-5A03-63AE-638B-W','https://www.youtube.com/watch?v=U_bZWFLTp-c&ab_channel=HDclipvideo')

exec dbo.obtener_contenidos


----------------------------------------

insert into dbo.Personas (nombres, apellidos)
values('Clint', 'Eastwood'),
  ('Bee', 'Vang');

insert into dbo.Generos_Contenidos (genero) values ('Drama')

insert into dbo.Contenidos (eidr_contenido, titulo, descripcion, url_imagen, fecha_estreno, id_genero, id_tipo_contenido, id_pais, fecha_carga, destacado)
values ('10.5240/8212-4AA9-5A03-63AE-638B-W','Gran Torino','Walt Kowalski (Clint Eastwood) es un anciano veterano de la guerra de Corea y trabajador jubilado de la fábrica de Ford, de actitud amargada, quien acaba de enviudar','https://static.independent.co.uk/s3fs-public/thumbnails/image/2009/07/02/18/204860.jpg?width=1200',CONVERT(DATE,'2008-01-01'),(select id_genero from dbo.Generos_Contenidos where genero = 'Drama'),(select id_tipo_contenido from dbo.Tipos_Contenidos where tipo = 'Películas'),(select id_pais from dbo.Paises_Contenidos where pais = 'EEUU'),CONVERT(DATE,'2015-01-01'),1)

insert into dbo.Direcciones (eidr_contenido, id_persona)
values ('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood'))

insert into dbo.Actuaciones (eidr_contenido, id_persona)
values ('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Clint' and apellidos = 'Eastwood')),
('10.5240/8212-4AA9-5A03-63AE-638B-W',(select id_persona from dbo.Personas where nombres = 'Bee' and apellidos = 'Vang'))

insert into dbo.Almacenamientos_Contenidos (eidr_contenido, url_contenido)
values ('10.5240/8212-4AA9-5A03-63AE-638B-W','https://www.youtube.com/watch?v=U_bZWFLTp-c&ab_channel=HDclipvideo')