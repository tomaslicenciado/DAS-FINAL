drop table if exists dbo.Accesos_Publicidad
drop table if exists dbo.Registro_Tarifario_Publicidades
drop table if exists dbo.Publicidades
drop table if exists dbo.Banners
drop table if exists dbo.Transacciones
drop table if exists dbo.Detalles_Factura
drop table if exists dbo.Registro_Tarifario_Federaciones
drop table if exists dbo.Registro_Tarifario
drop table if exists dbo.Facturas_Publicista
drop table if exists dbo.Facturas_Plataforma
drop table if exists dbo.Facturas
drop table if exists dbo.Tipos_Factura
drop table if exists dbo.Publicistas
drop table if exists dbo.Federaciones
drop table if exists dbo.Visualizaciones
drop table if exists dbo.Likes_Usuarios
drop table if exists dbo.Tokens_Usuario
drop table if exists dbo.Usuarios
drop table if exists dbo.Niveles_Usuario
drop table if exists dbo.Plataformas_X_Contenido
drop table if exists dbo.Plataformas_Streaming
drop table if exists dbo.Tarifas
drop table if exists dbo.Tipos_Servicio
drop table if exists dbo.Actuaciones
drop table if exists dbo.Direcciones
drop table if exists dbo.Contenidos
drop table if exists dbo.Generos_Contenido
drop table if exists dbo.Tipos_Contenido
drop table if exists dbo.Paises_Contenido
drop table if exists dbo.Personas

create table dbo.Personas(
	id_persona integer identity constraint PK_Id_Persona_END primary key,
	nombres varchar(50) not null,
	apellidos varchar(50) not null
)

create table dbo.Paises_Contenido(
	id_pais integer identity constraint PK_Id_Pais_END primary key,
	pais varchar(50) not null
)

create table dbo.Tipos_Contenido(
	id_tipo_contenido integer identity constraint PK_Id_Tipo_Contenido_END primary key,
	tipo_contenido varchar(50)
)

create table dbo.Generos_Contenido(
	id_genero_contenido integer identity constraint PK_Id_Genero_Contenido_END primary key,
	genero varchar(50)
)

create table dbo.Contenidos(
	eidr_contenido varchar(255) not null constraint PK_Edir_Contenido_END primary key,
	titulo varchar(50) not null,
	url_imagen varchar(255),
	descripcion varchar(255),
	fecha_estreno date not null,
	id_genero_contenido integer constraint FK_Genero_Contenido_END references dbo.Generos_Contenido(id_genero_contenido),
	id_tipo_contenido integer constraint FK_Tipo_Contenido_END references dbo.Tipos_Contenido(id_tipo_contenido),
	id_pais integer constraint FK_Pais_Contenido_END references dbo.Paises_Contenido(id_pais),
	activo bit not null default 1
)

create table dbo.Actuaciones(
	id_actuacion integer identity constraint PK_Id_Actuacion_END primary key,
    eidr_contenido varchar(255) constraint FK_Contenido_Actuacion_END references dbo.Contenidos(eidr_contenido),
    id_persona integer constraint FK_Persona_END references dbo.Personas(id_persona)
)

create table dbo.Direcciones(
    id_direccion integer identity constraint PK_Id_Direccion_END primary key,
    eidr_contenido varchar(255) constraint FK_Contenido_Direccion_END references dbo.Contenidos(eidr_contenido),
    id_persona integer constraint FK_Persona_Direccion_END references dbo.Personas(id_persona)
)

create table dbo.Tipos_Servicio(
    id_tipo_servicio integer identity constraint PK_Id_Tipo_Servicio_END primary key,
    tipo varchar(50) not null
)

create table dbo.Tarifas(
	id_tarifa integer identity constraint PK_Id_Tarifa_END primary key,
	descripcion varchar(50) not null,
	monto float not null default 1,
	fecha_creacion datetime not null,
	activa bit not null default 1
)

create table dbo.Plataformas_Streaming(
    id_plataforma integer identity constraint PK_Id_Plataforma_END primary key,
    nombre varchar(50) not null,
    url_conexion varchar(255) not null,
    url_icono varchar(255),
    email varchar(50),
    fecha_catalogo datetime,
    token_servicio varchar(255) not null,
    id_tipo_servicio integer constraint FK_Tipo_Servicio_Plataforma_END references dbo.Tipos_Servicio(id_tipo_servicio),
	id_tarifa_nuevos_viewers integer constraint FK_Tarifa_Nuevos_Viewers_Plataforma_END references dbo.Tarifas (id_tarifa),
	id_tarifa_viewer_activo integer constraint FK_Tarifa_Viewers_Activos_Plataforma_END references dbo.Tarifas (id_tarifa),
	activa bit not null default 1
)

create table dbo.Plataformas_X_Contenido(
	id_plataforma_contenido integer identity constraint PK_Id_Plataforma_Contenido_END primary key,
	id_plataforma integer constraint FK_Plataforma_END references dbo.Plataformas_Streaming(id_plataforma),
	eidr_contenido varchar(255) constraint FK_Contenido_END references dbo.Contenidos(eidr_contenido),
	fecha_carga date not null,
	destacado bit not null default 0,
	activo bit not null default 1
)

create table dbo.Niveles_Usuario(
	id_nivel integer identity constraint PK_Id_Nivel_END primary key,
	nivel varchar(50) not null
)

create table dbo.Usuarios(
	id_usuario integer identity constraint PK_Id_Usuario_END primary key,
	email varchar(50) not null,
	u_password varchar(255) not null,
	nombres varchar(50) not null,
	apellidos varchar(50) not null,
	id_nivel integer constraint FK_Nivel_Usuario_END references dbo.Niveles_Usuario(id_nivel),
	validado bit not null default 1,
	activo bit not null default 1
)

create table dbo.Tokens_Usuario(
	id_token integer identity constraint PK_Id_Token_END primary key,
	id_usuario integer constraint FK_Usuario_Token_END references dbo.Usuarios(id_usuario),
	token varchar(255) not null
)

create table dbo.Likes_Usuarios(
	id_like integer identity constraint PK_Id_Like_END primary key,
	eidr_contenido varchar(255) constraint FK_Contenido_Like_END references dbo.Contenidos(eidr_contenido),
	id_usuario integer constraint FK_Usuario_Like_END references dbo.Usuarios(id_usuario),
	fecha_like datetime not null,
	activo bit not null default 1
)

create table dbo.Visualizaciones(
	id_visualizacion integer identity constraint PK_Id_Visualizacion_END primary key,
	id_plataforma_contenido integer constraint FK_Contenido_Visualizacion_END references dbo.Plataformas_X_Contenido(id_plataforma_contenido),
	id_usuario integer constraint FK_Usuario_Visualizacion_END references dbo.Usuarios(id_usuario),
	fecha_visualizacion datetime not null
)

create table dbo.Federaciones(
	id_federacion integer identity constraint PK_Id_Federacion_END primary key,
	id_usuario integer constraint FK_Usuario_Federacion_END references dbo.Usuarios(id_usuario),
	id_plataforma integer constraint FK_Plataforma_Federacion_END references dbo.Plataformas_Streaming(id_plataforma),
	is_new bit not null default 1,
	token varchar(255),
	activa bit not null default 1,
	fecha_federacion datetime,
	fecha_baja datetime,
	fecha_inicio datetime,
	transaction_id varchar(255),
	completa bit not null default 0
)

create table dbo.Publicistas(
	id_publicista integer identity constraint PK_Id_Publicista_END primary key,
	nombre varchar(50) not null,
	razon_social varchar(50) not null,
	id_tipo_servicio integer constraint FK_Tipo_Servicio_Publicista_END references dbo.Tipos_Servicio(id_tipo_servicio),
	telefono varchar(50),
	nombre_contacto varchar(50),
	url_servicio varchar(255),
	token_servicio varchar(255),
	id_usuario integer constraint FK_Usuario_Publicista_END references dbo.Usuarios(id_usuario),
	email varchar(50) not null,
	activo bit not null default 1
)

create table dbo.Tipos_Factura(
	id_tipo_factura integer identity constraint PK_Id_Tipo_Factura_END primary key,
	tipo_factura varchar(50) not null
)

create table dbo.Facturas(
	id_factura integer identity constraint PK_Id_Factura_END primary key,
	fecha_facturacion datetime not null,
	fecha_inicial date not null,
	fecha_final date not null,
	monto_total float not null default 0,
	id_tipo_factura integer constraint FK_Tipo_Factura_END references dbo.Tipos_Factura(id_tipo_factura)
)

create table dbo.Facturas_Plataforma(
	id_factura integer constraint FK_Factura_Plataforma_END references dbo.Facturas(id_factura),
	id_plataforma integer constraint FK_Plataforma_Factura_END references dbo.Plataformas_Streaming(id_plataforma),
	constraint PK_Facturas_Plataformas_END primary key (id_factura, id_plataforma)
)

create table dbo.Facturas_Publicista(
	id_factura integer constraint FK_Factura_Publicista_END references dbo.Facturas(id_factura),
	id_publicista integer constraint FK_Publicista_Factura_END references dbo.Publicistas(id_publicista),
	constraint PK_Facturas_Publicistas_END primary key (id_factura, id_publicista)
)

create table dbo.Registro_Tarifario(
	id_registro integer identity constraint PK_Id_Registro_END primary key,
	id_tarifa integer constraint FK_Tarifa_Registro_END references dbo.Tarifas(id_tarifa),
	fecha_registro datetime not null,
	facturado bit not null default 0,
	activo bit not null default 1
)

create table dbo.Detalles_Factura(
	id_detalle integer identity constraint PK_Id_Detalle_END primary key,
	id_factura integer constraint FK_Factura_Detalle_END references dbo.Facturas(id_factura),
	id_tarifa integer constraint FK_Tarifa_Detalle_END references dbo.Tarifas(id_tarifa),
	cantidad integer not null default 1,
	subtotal float not null default 1,
	descripcion varchar(50)
)

create table dbo.Registro_Tarifario_Federaciones(
	id_registro_t integer constraint FK_RTF_Registro_END references dbo.Registro_Tarifario(id_registro),
	id_federacion integer constraint FK_RTF_Federacion_END references dbo.Federaciones(id_federacion),
	constraint PK_RTF_END primary key (id_registro_t, id_federacion)
)

create table dbo.Transacciones(
	id_transaccion integer identity constraint PK_Id_Transaccion_END primary key,
	id_factura integer constraint FK_Factura_Transaccion_END references dbo.Facturas(id_factura),
	monto float not null default 1,
)

create table dbo.Banners(
	banner_code integer not null constraint PK_Banner_Code_END primary key,
	id_tarifa_base integer constraint FK_Tarifa_Base_Banner_END references dbo.Tarifas(id_tarifa),
	id_ult_publicidad integer,
	id_tarifa_uso_exclusivo integer constraint FK_Tarifa_Exclusivo_Banner_END references dbo.Tarifas(id_tarifa)
)

create table dbo.Publicidades(
	id_publicidad integer identity constraint PK_Id_Publicidad_END primary key,
	id_publicista integer constraint FK_Publicista_Publicidad_END references dbo.Publicistas(id_publicista),
	banner_code integer constraint FK_Banner_Publicidad_END references dbo.Banners(banner_code),
	exclusiva bit not null default 0,
	fecha_inicio date not null,
	fecha_fin date not null,
	url_conexion varchar(255),
	url_imagen varchar(255)
)

create table dbo.Registro_Tarifario_Publicidades(
	id_registro_t integer constraint FK_RTP_Registro_END references dbo.Registro_Tarifario(id_registro),
	id_publicidad integer constraint FK_RTP_Publicidad_END references dbo.Publicidades(id_publicidad),
	constraint PK_RTP_END primary key (id_registro_t, id_publicidad)
)

create table dbo.Accesos_Publicidad(
	id_acceso integer identity constraint PK_Id_Acceso_END primary key,
	id_publicidad integer constraint FK_Publicidad_Acceso_END references dbo.Publicidades(id_publicidad),
	id_usuario integer constraint FK_Usuario_Acceso_END references dbo.Usuarios(id_usuario),
	fecha_acceso datetime not null
)

go

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------TRIGGERS----------------------------------------------------------
-- Actualización de monto de factura al agregar un detalle
CREATE OR ALTER TRIGGER tr_ActualizarMontoFactura
ON dbo.Detalles_Factura
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Actualizar montos en la tabla Facturas para las facturas afectadas
    UPDATE F
    SET monto_total = (
            SELECT ISNULL(SUM(D.cantidad * T.monto), 0)
            FROM dbo.Detalles_Factura D
            INNER JOIN inserted I ON D.id_detalle = I.id_detalle
			INNER JOIN dbo.Tarifas T ON D.id_tarifa = T.id_tarifa
            WHERE D.id_factura = F.id_factura
        )
    FROM dbo.Facturas F
    INNER JOIN inserted I ON F.id_factura = I.id_factura;
END;
go


--------------------------SP-------------------------------------------
CREATE OR ALTER PROCEDURE dbo.obtener_datos_servicio
    @id_servicio INT,
    @es_plataforma BIT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    IF @es_plataforma = 1
    BEGIN
        -- Es una plataforma de streaming
        SELECT
            nombre,
            url_conexion,
            p.id_tipo_servicio,
            tipo,
			p.token_servicio
        FROM dbo.Plataformas_Streaming p
        JOIN dbo.Tipos_Servicio t ON p.id_tipo_servicio = t.id_tipo_servicio
        WHERE id_plataforma = @id_servicio;
    END
    ELSE
    BEGIN
        -- Es un publicista
        SELECT
            nombre,
            url_servicio AS url_conexion,
            pu.id_tipo_servicio,
            tipo,
			pu.token_servicio
        FROM dbo.Publicistas pu
        JOIN dbo.Tipos_Servicio t ON pu.id_tipo_servicio = t.id_tipo_servicio
        WHERE id_publicista = @id_servicio;
    END
END;
go 

CREATE OR ALTER PROCEDURE dbo.obtener_tokens_disponibles
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    WITH PlataformasConToken AS (
        SELECT
            P.id_plataforma,
            F.token,
			F.fecha_federacion
        FROM dbo.Plataformas_Streaming P
        JOIN dbo.Federaciones F ON P.id_plataforma = F.id_plataforma
        WHERE F.activa = 1
          AND F.completa = 1
		  AND F.token IS NOT NULL
    ),
    UltimasFederaciones AS (
        SELECT
            id_plataforma,
            MAX(fecha_federacion) AS ultima_fecha_federacion
        FROM dbo.Federaciones
        WHERE activa = 1
        GROUP BY id_plataforma
    )
    
    SELECT
        PT.id_plataforma,
        PT.token
    FROM PlataformasConToken PT
    JOIN UltimasFederaciones UF ON PT.id_plataforma = UF.id_plataforma AND PT.fecha_federacion = UF.ultima_fecha_federacion;
END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_token_viewer
    @token_usuario VARCHAR(255),
    @id_plataforma INT,
	@token_viewer VARCHAR(255) OUT
AS
BEGIN
    SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Obtener el token de federación activo correspondiente al usuario y plataforma
    SELECT TOP 1 @token_viewer = F.token
    FROM dbo.Federaciones F
    INNER JOIN dbo.Tokens_Usuario TU ON F.id_usuario = TU.id_usuario
    WHERE TU.token = @token_usuario
      AND F.id_plataforma = @id_plataforma
      AND F.activa = 1
      AND F.completa = 1
    ORDER BY F.fecha_federacion DESC;

END;
go

CREATE OR ALTER PROCEDURE dbo.obtener_usuario
    @email VARCHAR(50),
    @password VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_usuario INT,
            @nombres VARCHAR(50),
            @apellidos VARCHAR(50),
            @id_nivel INT,
            @nivel VARCHAR(50),
            @token VARCHAR(255),
			@validado BIT;

    -- Verificar si el usuario está activo y las credenciales son correctas
    SELECT TOP 1
        @id_usuario = U.id_usuario,
        @nombres = U.nombres,
        @apellidos = U.apellidos,
        @id_nivel = U.id_nivel,
        @nivel = NU.nivel,
        @token = TU.token,
		@validado = U.validado
    FROM dbo.Usuarios U
    INNER JOIN dbo.Niveles_Usuario NU ON U.id_nivel = NU.id_nivel
    LEFT JOIN dbo.Tokens_Usuario TU ON U.id_usuario = TU.id_usuario
    WHERE U.email = @email
      AND U.u_password = @password
      AND U.activo = 1;

    -- Devolver los datos del usuario si se encuentra activo
    IF @@ROWCOUNT > 0
    BEGIN
		IF @id_usuario IS NULL
		BEGIN
			SET @id_usuario = 0
		END
        SELECT
            @id_usuario AS id_usuario,
            @nombres AS nombres,
            @apellidos AS apellidos,
            @id_nivel AS id_nivel,
            @nivel AS nivel,
            @token AS token,
			@validado AS validado;
    END
END;
go

CREATE OR ALTER PROCEDURE dbo.registrar_suscriptor
    @email VARCHAR(50),
    @password VARCHAR(255),
    @nombres VARCHAR(50),
    @apellidos VARCHAR(50),
    @token VARCHAR(255),
	@id_usuario INT OUT,
	@nivel VARCHAR(50) OUT
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

	if exists (select 1 from dbo.Usuarios u where u.email = @email)
	begin
		raiserror('El correo electrónico ya está en uso', 16, 1)
	end
    -- Registrar el suscriptor en la tabla Usuarios
    INSERT INTO dbo.Usuarios (email, u_password, nombres, apellidos, id_nivel)
    VALUES (@email, @password, @nombres, @apellidos, 1);
	
	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la inserción. No se insertó el usuario', 16, 1)
	end

    -- Obtener el id de usuario recién generado
    SET @id_usuario = SCOPE_IDENTITY();

    -- Registrar el token en la tabla Tokens_Usuario
    INSERT INTO dbo.Tokens_Usuario (id_usuario, token)
    VALUES (@id_usuario, @token);
	
	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la inserción. No se agregó el token', 16, 1)
	end

	SELECT @nivel = nivel
	FROM dbo.Niveles_Usuario
	WHERE id_nivel = 1
END;
go

CREATE OR ALTER PROCEDURE dbo.iniciar_federacion
    @id_plataforma INT,
    @token_usuario VARCHAR(255),
	@transaction_id VARCHAR(255),
	@id_federacion INT OUT
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_usuario INT,
            @fecha_federacion DATETIME;

    -- Obtener el id de usuario asociado al token
    SELECT TOP 1 @id_usuario = U.id_usuario
    FROM dbo.Usuarios U
    INNER JOIN dbo.Tokens_Usuario TU ON U.id_usuario = TU.id_usuario
    WHERE TU.token = @token_usuario
      AND U.activo = 1;

    -- Verificar si se encontró un usuario activo asociado al token
    IF @id_usuario IS NOT NULL
    BEGIN
        -- Obtener la fecha de inicio del sistema
        SET @fecha_federacion = GETDATE();

        -- Insertar un nuevo registro en la tabla Federaciones
        INSERT INTO dbo.Federaciones (id_usuario, id_plataforma, fecha_federacion, transaction_id)
        VALUES (@id_usuario, @id_plataforma, @fecha_federacion, @transaction_id);

		SET @id_federacion = SCOPE_IDENTITY()

		UPDATE F
		SET activa = 0
		FROM dbo.Federaciones F
		WHERE F.id_usuario = @id_usuario
		  AND F.id_plataforma = @id_plataforma
		  AND F.activa = 1
		  AND (F.fecha_baja IS NULL OR F.fecha_baja > GETDATE())
		  AND F.id_federacion <> @id_federacion;
    END
    ELSE
    BEGIN
        -- Devolver un valor negativo o un código de error para indicar que no se encontró un usuario activo
        SET @id_federacion = -1;
    END
END;
go

create or alter procedure dbo.obtener_transaction_id
	@token_usuario VARCHAR(255),
	@id_plataforma int,
	@transaction_id varchar(255) out
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select @transaction_id = f.transaction_id
	from dbo.Federaciones f
	left join dbo.Tokens_Usuario tu on tu.id_usuario = f.id_usuario
	where tu.token = @token_usuario
	and f.id_plataforma = @id_plataforma
	and f.activa = 1
	and f.completa = 0
	and f.fecha_baja is null
end;
go

CREATE OR ALTER PROCEDURE dbo.finalizar_federacion
    @token_usuario VARCHAR(255),
    @id_plataforma INT,
    @token_viewer VARCHAR(255),
    @es_nuevo BIT,
	@actualizarCatalogo BIT OUT
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_usuario INT;
    DECLARE @id_federacion INT;

    -- Obtener el id de usuario correspondiente al token
    SELECT @id_usuario = F.id_usuario
    FROM dbo.Federaciones F
    INNER JOIN dbo.Tokens_Usuario TU ON F.id_usuario = TU.id_usuario
    WHERE TU.token = @token_usuario
      AND F.id_plataforma = @id_plataforma
      AND F.activa = 1
      AND F.completa = 0;

    -- Obtener el id de la federación activa correspondiente al usuario y plataforma
    SELECT TOP 1 @id_federacion = id_federacion
    FROM dbo.Federaciones
    WHERE id_usuario = @id_usuario
      AND id_plataforma = @id_plataforma
      AND activa = 1
      AND completa = 0;

    -- Actualizar la federación encontrada
    IF @id_federacion IS NOT NULL
    BEGIN
        UPDATE dbo.Federaciones
        SET is_new = @es_nuevo,
            token = @token_viewer,
            completa = 1,
            fecha_inicio = GETDATE()
        WHERE id_federacion = @id_federacion;
		
		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción. No se finalizó la federación', 16, 1)
		end

		if @es_nuevo = 1
		begin
			declare @id_registro integer;

			insert into dbo.Registro_Tarifario (id_tarifa, fecha_registro)
			values ((select top 1 p.id_tarifa_nuevos_viewers from dbo.Plataformas_Streaming p where p.id_plataforma = @id_plataforma), GETDATE())

			set @id_registro = SCOPE_IDENTITY()

			insert into dbo.Registro_Tarifario_Federaciones (id_federacion, id_registro_t)
			values (@id_federacion, @id_registro)
		end

		if exists (select * from dbo.Plataformas_X_Contenido pxc where pxc.id_plataforma = @id_plataforma and pxc.activo = 1)
		begin
			set @actualizarCatalogo = 0
		end
		else
		begin
			set @actualizarCatalogo = 1
		end
    END
	ELSE
	BEGIN
		RAISERROR('No se encontró federación activa asociada al usuario y plataforma indicados.', 16, 1);
	END
END;
go

CREATE OR ALTER PROCEDURE dbo.listar_generos
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Seleccionar la lista de géneros
    SELECT id_genero_contenido, genero
    FROM dbo.Generos_Contenido;
END;
go

CREATE OR ALTER PROCEDURE dbo.listar_plataformas
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    -- Seleccionar el listado de plataformas activas
    SELECT p.id_plataforma, p.nombre, p.url_icono, p.url_conexion,
			p.token_servicio, p.id_tipo_servicio, tn.monto as tarifa_nuevo_viewer, ta.monto as tarifa_viewer_activo
    FROM dbo.Plataformas_Streaming p
	left join dbo.Tarifas tn on tn.id_tarifa = p.id_tarifa_nuevos_viewers
	left join dbo.Tarifas ta on ta.id_tarifa = p.id_tarifa_viewer_activo
    WHERE p.activa = 1;
END;
go

CREATE OR ALTER PROCEDURE dbo.desuscribir_plataforma
    @id_plataforma INT,
    @token_usuario VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @id_usuario INT;

    -- Obtener el id de usuario correspondiente al token
    SELECT @id_usuario = TU.id_usuario
    FROM dbo.Tokens_Usuario TU 
    WHERE TU.token = @token_usuario

    -- Desactivar todas las federaciones asociadas al usuario y plataforma indicados
    UPDATE dbo.Federaciones
    SET activa = 0,
        fecha_baja = GETDATE()
    WHERE id_usuario = @id_usuario
      AND id_plataforma = @id_plataforma
      AND activa = 1;
  
	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la inserción. No se desuscribió la plataforma', 16, 1)
	end
END;
go


CREATE OR ALTER PROCEDURE dbo.insertar_contenidos_batch
    @json nvarchar(max)
AS
BEGIN
    SET NOCOUNT ON;

	declare @cantidad int;
	create table #contenidosList (
		eidr_contenido VARCHAR(255) NOT NULL,
		titulo VARCHAR(50) NOT NULL,
		url_imagen VARCHAR(255),
		descripcion VARCHAR(255),
		fecha_estreno DATE NOT NULL,
		genero VARCHAR(50),
		pais VARCHAR(50),
		tipo_contenido VARCHAR(50)
	)
	
	insert into #contenidosList
	select *
	from openjson(@json) with (
		eidr_contenido VARCHAR(255) 'strict $.eidr_contenido',
		titulo VARCHAR(50) '$.titulo',
		url_imagen VARCHAR(255) '$.url_imagen',
		descripcion VARCHAR(255) '$.descripcion',
		fecha_estreno DATE  '$.fecha_estreno',
		genero VARCHAR(50) '$.genero',
		pais VARCHAR(50) '$.pais',
		tipo_contenido VARCHAR(50) '$.tipo_contenido'
	)

	select @cantidad = COUNT(*) from #contenidosList
	
    BEGIN TRY
        BEGIN TRANSACTION;

		-- Insertar nuevos valores en Generos_Contenido
        INSERT INTO dbo.Generos_Contenido (genero)
        SELECT DISTINCT c.genero
        FROM #contenidosList c
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Generos_Contenido gc
            WHERE gc.genero = c.genero
        );

        -- Insertar nuevos valores en Tipos_Contenido
        INSERT INTO dbo.Tipos_Contenido (tipo_contenido)
        SELECT DISTINCT c.tipo_contenido
        FROM #contenidosList c
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Tipos_Contenido tc
            WHERE tc.tipo_contenido = c.tipo_contenido
        );

        -- Insertar nuevos valores en Paises_Contenido
        INSERT INTO dbo.Paises_Contenido (pais)
        SELECT DISTINCT c.pais
        FROM #contenidosList c
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Paises_Contenido pc
            WHERE pc.pais = c.pais
        );

		-- Desactivar contenidos existentes que no están en la lista
        UPDATE c
        SET c.activo = 0
        FROM dbo.Contenidos c
        LEFT JOIN #contenidosList lc ON c.eidr_contenido = lc.eidr_contenido
        WHERE lc.eidr_contenido IS NULL;

        -- Insertar los contenidos en la tabla
        INSERT INTO dbo.Contenidos (eidr_contenido, titulo, url_imagen, descripcion, fecha_estreno, id_genero_contenido, id_tipo_contenido, id_pais, activo)
        SELECT
            c.eidr_contenido,
            c.titulo,
            c.url_imagen,
            c.descripcion,
            c.fecha_estreno,
            COALESCE(g.id_genero_contenido, 0) AS id_genero_contenido,
            COALESCE(tc.id_tipo_contenido, 0) AS id_tipo_contenido,
            COALESCE(p.id_pais, 0) AS id_pais,
            1 AS activo
        FROM
            #contenidosList c
        LEFT JOIN
            dbo.Generos_Contenido g ON c.genero = g.genero
        LEFT JOIN
            dbo.Tipos_Contenido tc ON c.tipo_contenido = tc.tipo_contenido
        LEFT JOIN
            dbo.Paises_Contenido p ON c.pais = p.pais
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Contenidos
            WHERE eidr_contenido = c.eidr_contenido
        );

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Manejo de errores
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH;
END;
go



----------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE dbo.insertar_actuaciones_batch
    @json nvarchar(max)
AS
BEGIN
    SET NOCOUNT ON;

	CREATE TABLE #actuacionesList
	(
		eidr_contenido VARCHAR(255) NOT NULL,
		nombres VARCHAR(50) NOT NULL,
		apellidos VARCHAR(50) NOT NULL
	)

	insert into #actuacionesList
	select *
	from openjson(@json) with (
		eidr_contenido VARCHAR(255) 'strict $.eidr_contenido',
		nombres VARCHAR(50) '$.nombres',
		apellidos VARCHAR(50) '$.apellidos'
	)

    BEGIN TRY
        BEGIN TRANSACTION;

		INSERT INTO dbo.Personas (nombres, apellidos)
		SELECT la.nombres, la.apellidos
		FROM #actuacionesList la
		WHERE NOT EXISTS (
			SELECT 1
			FROM dbo.Personas p
			WHERE p.nombres = la.nombres AND p.apellidos = la.apellidos
		);

        -- Insertar nuevas actuaciones en la tabla
        INSERT INTO dbo.Actuaciones (eidr_contenido, id_persona)
        SELECT
            la.eidr_contenido,
            p.id_persona
        FROM
            #actuacionesList la
        INNER JOIN dbo.Personas p ON la.nombres = p.nombres AND la.apellidos = p.apellidos
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Actuaciones a
            WHERE a.eidr_contenido = la.eidr_contenido AND a.id_persona = p.id_persona
        );

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Manejo de errores
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH;
END;
go

------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE dbo.insertar_direcciones_batch
    @json nvarchar(max)
AS
BEGIN
    SET NOCOUNT ON;

	CREATE TABLE #direccionesList
	(
		eidr_contenido VARCHAR(255) NOT NULL,
		nombres VARCHAR(50) NOT NULL,
		apellidos VARCHAR(50) NOT NULL
	)

	insert into #direccionesList
	select *
	from openjson(@json) with (
		eidr_contenido VARCHAR(255) 'strict $.eidr_contenido',
		nombres VARCHAR(50) '$.nombres',
		apellidos VARCHAR(50) '$.apellidos'
	)

    BEGIN TRY
        BEGIN TRANSACTION;

		INSERT INTO dbo.Personas (nombres, apellidos)
		SELECT ld.nombres, ld.apellidos
		FROM #direccionesList ld
		WHERE NOT EXISTS (
			SELECT 1
			FROM dbo.Personas p
			WHERE p.nombres = ld.nombres AND p.apellidos = ld.apellidos
		);

        -- Insertar nuevas direcciones en la tabla
        INSERT INTO dbo.Direcciones (eidr_contenido, id_persona)
        SELECT
            ld.eidr_contenido,
            p.id_persona
        FROM
            #direccionesList ld
        INNER JOIN dbo.Personas p ON ld.nombres = p.nombres AND ld.apellidos = p.apellidos
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Direcciones d
            WHERE d.eidr_contenido = ld.eidr_contenido AND d.id_persona = p.id_persona
        );

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Manejo de errores
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH;
END;
go

------------------------------------------------------------------------------------------------------------

CREATE OR ALTER PROCEDURE dbo.insertar_plataformas_x_contenido_batch
    @json nvarchar(max)
AS
BEGIN
    SET NOCOUNT ON;

	CREATE TABLE #plataformasXContenidoList
	(
		id_plataforma INT,
		eidr_contenido VARCHAR(255),
		fecha_carga DATE,
		destacado BIT
	)

	insert into #plataformasXContenidoList
	select *
	from openjson(@json) with (
		id_plataforma INT '$.id_plataforma',
		eidr_contenido VARCHAR(255) '$.eidr_contenido',
		fecha_carga DATE '$.fecha_carga',
		destacado BIT '$.destacado'
	)

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Desactivar registros existentes que no están en la lista
        UPDATE px
        SET px.activo = 0
        FROM dbo.Plataformas_X_Contenido px
        LEFT JOIN #plataformasXContenidoList pc ON px.eidr_contenido = pc.eidr_contenido AND px.id_plataforma = pc.id_plataforma
        WHERE pc.eidr_contenido IS NULL OR pc.id_plataforma IS NULL;

        -- Insertar nuevos registros en la tabla
        INSERT INTO dbo.Plataformas_X_Contenido (id_plataforma, eidr_contenido, fecha_carga, destacado, activo)
        SELECT
            pc.id_plataforma,
            pc.eidr_contenido,
            pc.fecha_carga,
            pc.destacado,
            1 AS activo
        FROM
            #plataformasXContenidoList pc
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Plataformas_X_Contenido px
            WHERE px.eidr_contenido = pc.eidr_contenido AND px.id_plataforma = pc.id_plataforma
        );

		UPDATE ps
		SET ps.fecha_catalogo = GETDATE()
		FROM dbo.Plataformas_Streaming ps
		WHERE EXISTS (
			SELECT 1
			FROM #plataformasXContenidoList pl
			WHERE pl.id_plataforma = ps.id_plataforma
		);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Manejo de errores
        DECLARE @ErrorMessage NVARCHAR(4000);
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH;
END;
go

create or alter procedure dbo.obtener_nivel_usuario
	@token_usuario VARCHAR(255),
	@nivel INTEGER OUT
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select @nivel = u.id_nivel
	from dbo.Usuarios u
	left join dbo.Tokens_Usuario tu on tu.id_usuario = u.id_usuario
	where tu.token = @token_usuario
	and u.activo = 1

	if @nivel IS NULL
	begin
		set @nivel = -1
	end
end;
go

CREATE OR ALTER PROCEDURE dbo.obtener_contenidos_activos
	@token_suscriptor VARCHAR(255)
AS
BEGIN
	set nocount on;
	set transaction isolation level repeatable read;
    -- Seleccionar los contenidos activos con sus registros asociados
    SELECT 
        c.eidr_contenido,
        c.titulo,
        c.url_imagen,
        c.descripcion,
        c.fecha_estreno,
        g.genero,
        t.tipo_contenido,
        p.pais,
        (
            SELECT p.nombres, p.apellidos, a.eidr_contenido as eidr_contenido
            FROM dbo.Actuaciones a
			LEFT JOIN dbo.Personas p ON p.id_persona = a.id_persona
            WHERE eidr_contenido = c.eidr_contenido
            FOR JSON AUTO
        ) AS actuaciones,
        (
            SELECT p.nombres, p.apellidos, d.eidr_contenido as eidr_contenido
            FROM dbo.Direcciones d
			LEFT JOIN dbo.Personas p ON p.id_persona = d.id_persona
            WHERE eidr_contenido = c.eidr_contenido
            FOR JSON AUTO
        ) AS direcciones,
        (
            SELECT pc.id_plataforma, pc.eidr_contenido, pc.fecha_carga, pc.destacado
            FROM dbo.Plataformas_X_Contenido pc
            WHERE pc.eidr_contenido = c.eidr_contenido
            FOR JSON AUTO
        ) AS plataformas_x_contenido
    FROM dbo.Contenidos c
    LEFT JOIN dbo.Generos_Contenido g ON c.id_genero_contenido = g.id_genero_contenido
    LEFT JOIN dbo.Tipos_Contenido t ON c.id_tipo_contenido = t.id_tipo_contenido
    LEFT JOIN dbo.Paises_Contenido p ON c.id_pais = p.id_pais
    WHERE c.activo = 1
	AND EXISTS (
          SELECT 1
          FROM dbo.Plataformas_X_Contenido pc
		  LEFT JOIN dbo.Federaciones f ON f.id_plataforma = pc.id_plataforma
		  LEFT JOIN dbo.Usuarios u ON u.id_usuario = f.id_usuario
		  LEFT JOIN dbo.Tokens_Usuario tu ON tu.id_usuario = f.id_usuario
          WHERE pc.eidr_contenido = c.eidr_contenido
		  AND f.activa = 1
		  AND u.activo = 1
		  AND tu.token = @token_suscriptor
      )
END;
go

CREATE OR ALTER PROCEDURE dbo.registrar_visualizacion
    @token_suscriptor VARCHAR(255),
    @id_plataforma INTEGER,
    @eidr_contenido VARCHAR(255)
AS
BEGIN
	SET NOCOUNT ON;

    DECLARE @id_usuario INTEGER;
    DECLARE @id_plataforma_contenido INTEGER;

    -- Obtener el ID de usuario usando el token
    SELECT @id_usuario = u.id_usuario
    FROM dbo.Tokens_Usuario tu
    INNER JOIN dbo.Usuarios u ON tu.id_usuario = u.id_usuario
    WHERE tu.token = @token_suscriptor
	AND u.activo = 1;

    -- Verificar que existe una federación activa con la plataforma
    IF EXISTS (
        SELECT 1
        FROM dbo.Federaciones f
        WHERE f.id_usuario = @id_usuario
          AND f.id_plataforma = @id_plataforma
          AND f.activa = 1
    )
    BEGIN
        -- Obtener el ID de plataforma por contenido
        SELECT @id_plataforma_contenido = pc.id_plataforma_contenido
        FROM dbo.Plataformas_X_Contenido pc
        WHERE pc.id_plataforma = @id_plataforma
          AND pc.eidr_contenido = @eidr_contenido;

        -- Insertar la visualización
        INSERT INTO dbo.Visualizaciones (id_plataforma_contenido, id_usuario, fecha_visualizacion)
        VALUES (@id_plataforma_contenido, @id_usuario, GETDATE());
		
		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción. No se registró la visualización', 16, 1)
		end
    END
    ELSE
    BEGIN
        -- Mensaje de error si no hay federación activa
        RAISERROR ('Error: No hay federación activa con la plataforma.', 16, 1);
    END;
END;
GO

create or alter procedure dbo.obtener_banners
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select b.banner_code, tb.monto as tarifa_base, b.id_ult_publicidad, te.monto as tarifa_uso_exclusivo
	from dbo.Banners b
	left join dbo.Tarifas tb on tb.id_tarifa = b.id_tarifa_base
	left join dbo.Tarifas te on te.id_tarifa = b.id_tarifa_uso_exclusivo

end;
go

create or alter procedure dbo.listar_id_publicistas
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select p.id_publicista
	from dbo.Publicistas p
	where activo = 1
	and exists (
		select 1
		from dbo.Publicidades pu
		where pu.fecha_inicio < GETDATE()
		and pu.fecha_fin > GETDATE()
		and pu.id_publicista = p.id_publicista
	)
end;
go

create or alter procedure dbo.actualizar_publicidad_en_banner
	@banner_code integer,
	@id_publicidad integer
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	update dbo.Banners
	set id_ult_publicidad = @id_publicidad
	where banner_code = @banner_code
	and id_ult_publicidad <> @id_publicidad

	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la inserción. No se agregó la plataforma', 16, 1)
	end
end;
go

create or alter procedure dbo.registrar_acceso_publicidad
	@token_usuario varchar(255),
	@id_publicidad integer
as
begin
	set nocount on;
	set transaction isolation level repeatable read;
	
	declare @id_usuario integer;

	select @id_usuario = tu.id_usuario
	from dbo.Tokens_Usuario tu
	join dbo.Usuarios u on u.id_usuario = tu.id_usuario
	where u.activo = 1
	and tu.token = @token_usuario

	if @id_usuario is not null
	begin
		insert into dbo.Accesos_Publicidad (id_publicidad, id_usuario, fecha_acceso)
		values (@id_publicidad, @id_usuario, GETDATE())

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción. No se registró el acceso a la publicidad', 16, 1)
		end
	end
	else
	begin
		raiserror('El usuario no existe', 16, 1);
	end
end;
go

create or alter procedure dbo.insertar_plataforma
	@nombre varchar(50),
	@url_conexion varchar(255),
	@url_icono varchar(255),
	@email varchar(50),
	@id_tipo_servicio integer,
	@token_servicio varchar(255),
	@tarifa_nuevos_viewers float,
	@tarifa_viewers_activos float
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_tarifa_nuevos_viewers integer;
	declare @id_tarifa_viewers_activos integer;

	insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
	values ('Tarifa por registro de viewers nuevos en plataforma ' + @nombre, GETDATE(), @tarifa_nuevos_viewers)

	set @id_tarifa_nuevos_viewers = SCOPE_IDENTITY();

	insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
	values ('Tarifa por viewers activos en plataforma ' + @nombre, GETDATE(), @tarifa_viewers_activos)

	set @id_tarifa_viewers_activos = SCOPE_IDENTITY();

	if not exists (select 1 from dbo.Plataformas_Streaming ps where ps.activa = 1 and ps.nombre = @nombre)
	begin
		insert into dbo.Plataformas_Streaming (email, fecha_catalogo, id_tipo_servicio, nombre, token_servicio, url_conexion, url_icono, id_tarifa_nuevos_viewers, id_tarifa_viewer_activo)
		values (@email, null, @id_tipo_servicio, @nombre, @token_servicio, @url_conexion, @url_icono, @id_tarifa_nuevos_viewers, @id_tarifa_viewers_activos)

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción. No se agregó la plataforma', 16, 1)
		end
	end
end;
go

create or alter procedure dbo.actualizar_plataforma
	@id_plataforma integer,
	@nombre varchar(50),
	@url_conexion varchar(255),
	@url_icono varchar(255),
	@email varchar(50),
	@id_tipo_servicio integer,
	@token_servicio varchar(255),
	@tarifa_nuevos_viewers float,
	@tarifa_viewers_activos float
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_tarifa_nuevos_viewers integer;
	declare @id_tarifa_viewers_activos integer;

	select @id_tarifa_nuevos_viewers = id_tarifa_nuevos_viewers, @id_tarifa_viewers_activos = id_tarifa_viewer_activo
	from dbo.Plataformas_Streaming
	where id_plataforma = @id_plataforma

	if (select t.monto from dbo.Tarifas t where t.id_tarifa = @id_tarifa_nuevos_viewers) <> @tarifa_nuevos_viewers
	begin
		update t
		set t.activa = 0
		from dbo.Tarifas t
		where t.id_tarifa = @id_tarifa_nuevos_viewers

		insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
		values ('Tarifa por registro de viewers nuevos en plataforma ' + @nombre, GETDATE(), @tarifa_nuevos_viewers)

		set @id_tarifa_nuevos_viewers = SCOPE_IDENTITY()
	end;

	if (select t.monto from dbo.Tarifas t where t.id_tarifa = @id_tarifa_viewers_activos) <> @tarifa_viewers_activos
	begin
		update t
		set t.activa = 0
		from dbo.Tarifas t
		where t.id_tarifa = @id_tarifa_viewers_activos

		insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
		values ('Tarifa por viewers activos en plataforma ' + @nombre, GETDATE(), @tarifa_viewers_activos)

		set @id_tarifa_viewers_activos = SCOPE_IDENTITY()
	end;

	update ps
	set ps.nombre = @nombre, 
		ps.url_conexion = @url_conexion, 
		ps.url_icono = @url_icono,
		ps.email = @email,
		ps.id_tipo_servicio = @id_tipo_servicio,
		ps.token_servicio = @token_servicio,
		ps.id_tarifa_nuevos_viewers = @id_tarifa_nuevos_viewers,
		ps.id_tarifa_viewer_activo = @id_tarifa_viewers_activos
	from dbo.Plataformas_Streaming ps
	where ps.id_plataforma = @id_plataforma
	
	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la actualización. No se modificó la plataforma', 16, 1)
	end
end;
go

create or alter procedure dbo.desactivar_plataforma
	@id_plataforma integer
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	update ps
	set ps.activa = 0
	from dbo.Plataformas_Streaming ps
	where ps.id_plataforma = @id_plataforma

	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la desactivación. No se modificó la plataforma', 16, 1)
	end

	update f
	set f.activa = 0,
		f.fecha_baja = GETDATE()
	from dbo.Federaciones f
	where f.id_plataforma = @id_plataforma

	update pxc
	set pxc.activo = 0
	from dbo.Plataformas_X_Contenido pxc
	where pxc.id_plataforma = @id_plataforma
	
	update t
	set t.activa = 0
	from dbo.Tarifas t
	join dbo.Plataformas_Streaming ps on ps.id_tarifa_nuevos_viewers = t.id_tarifa
	where ps.id_plataforma = @id_plataforma

end;
go

create or alter procedure dbo.modificar_banner
	@banner_code integer,
	@tarifa_base float,
	@tarifa_exclusividad float
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_tarifa_base integer;
	declare @id_tarifa_exclusiva integer;

	select @id_tarifa_base = id_tarifa_base, @id_tarifa_exclusiva = id_tarifa_uso_exclusivo
	from dbo.Banners
	where banner_code = @banner_code

	if (select t.monto from dbo.Tarifas t where t.id_tarifa = @id_tarifa_base) <> @tarifa_base
	begin
		update t
		set t.activa = 0
		from dbo.Tarifas t
		where t.id_tarifa = @id_tarifa_base

		insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
		values ('Tarifa base banner', GETDATE(), @tarifa_base)

		set @id_tarifa_base = SCOPE_IDENTITY()

		if @id_tarifa_base is not null
		begin
			update b
			set b.id_tarifa_base = @id_tarifa_base
			from dbo.Banners b
			where b.banner_code = @banner_code

			if @@ROWCOUNT = 0
			begin
				raiserror('Error en la modificación. No se modificó el banner', 16, 1)
			end
		end
		else
		begin
			raiserror('Error en la modificación. No se modificó el banner', 16, 1)
		end
	end

	if (select t.monto from dbo.Tarifas t where t.id_tarifa = @id_tarifa_exclusiva) <> @tarifa_exclusividad
	begin
		update t
		set t.activa = 0
		from dbo.Tarifas t
		where t.id_tarifa = @id_tarifa_exclusiva

		insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
		values ('Tarifa base banner', GETDATE(), @tarifa_exclusividad)

		set @id_tarifa_exclusiva = SCOPE_IDENTITY()

		if @id_tarifa_exclusiva is not null
		begin
			update b
			set b.id_tarifa_uso_exclusivo = @id_tarifa_exclusiva
			from dbo.Banners b
			where b.banner_code = @banner_code

			if @@ROWCOUNT = 0
			begin
				raiserror('Error en la modificación. No se modificó el banner', 16, 1)
			end
		end
		else
		begin
			raiserror('Error en la modificación. No se modificó el banner', 16, 1)
		end
	end
end;
go

create or alter procedure dbo.insertar_registro_tarifario_federacion
	@transaction_id varchar(255)
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_tarifa_nuevos_viewers integer;
	declare @id_plataforma integer;
	declare @id_registro integer;
	declare @id_federacion integer;

	select @id_plataforma = f.id_plataforma, @id_federacion = f.id_federacion
	from dbo.Federaciones f
	where f.transaction_id = @transaction_id

	select @id_tarifa_nuevos_viewers = p.id_tarifa_nuevos_viewers
	from dbo.Plataformas_Streaming p
	where p.id_plataforma = @id_plataforma

	insert into dbo.Registro_Tarifario (id_tarifa, fecha_registro)
	values (@id_tarifa_nuevos_viewers, GETDATE())

	set @id_registro = SCOPE_IDENTITY()

	insert into dbo.Registro_Tarifario_Federaciones (id_federacion, id_registro_t)
	values (@id_federacion, @id_registro)
end;
go

create or alter procedure dbo.insertar_publicista
	@nombre varchar(50),
	@razon_socail varchar(50),
	@id_tipo_servicio integer,
	@telefono integer,
	@nombre_contacto varchar(50),
	@url_servicio varchar(255),
	@token_servicio varchar(255),
	@email varchar(50),
	@token_usuario varchar(255)
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	if not exists (select 1 from dbo.Publicistas where nombre = @nombre and activo = 1)
	begin
		declare @id_usuario integer;

		insert into dbo.Usuarios (nombres, apellidos, email, u_password, id_nivel)
		values ('Administrador Publicista', @nombre, @email, '1234', 2)

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción de Publcista. No se registró el usuario', 16, 1)
		end
	
		set @id_usuario = SCOPE_IDENTITY()

		insert into dbo.Tokens_Usuario (id_usuario, token)
		values (@id_usuario, @token_usuario)

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción de Publcista. No se registró el token de usuario', 16, 1)
		end

		insert into dbo.Publicistas (id_tipo_servicio, id_usuario, nombre, nombre_contacto, razon_social, telefono, token_servicio, url_servicio, email)
		values (@id_tipo_servicio, @id_usuario, @nombre, @nombre_contacto, @razon_socail, @telefono, @token_servicio, @url_servicio, @email)

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la inserción de Publcista. No se registró el publicista', 16, 1)
		end
	end
	else
	begin
		declare @mensaje varchar(50);
		set @mensaje = 'Ya existe un publicista con el nombre '+ @nombre;
		raiserror(@mensaje, 16, 1);
	end
end;
go

create or alter procedure dbo.modificar_publicista
	@id_publicista integer,
	@nombre varchar(50),
	@razon_social varchar(50),
	@id_tipo_servicio integer,
	@telefono integer,
	@nombre_contacto varchar(50),
	@url_servicio varchar(255),
	@token_servicio varchar(255),
	@email varchar(50)
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	if exists (select 1 from dbo.Publicistas where id_publicista = @id_publicista and activo = 1)
	begin
		update p
		set p.nombre = @nombre,
			p.razon_social = @razon_social,
			p.id_tipo_servicio = @id_tipo_servicio,
			p.telefono = @telefono,
			p.nombre_contacto = @nombre_contacto,
			p.url_servicio = @url_servicio,
			p.token_servicio = @token_servicio,
			p.email = @email
		from dbo.Publicistas p
		where p.id_publicista = @id_publicista

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la modificación del Publcista. No se registraron los nuevos datos', 16, 1)
		end
	end
	else
	begin
		raiserror ('No existe un publicista con el id indicado', 16, 1)
	end
end;
go

create or alter procedure dbo.eliminar_publicista
	@id_publicista integer
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_usuario integer;

	if exists (select 1 from dbo.Publicistas where id_publicista = @id_publicista)
	begin
		select @id_usuario = id_usuario
		from dbo.Publicistas
		where id_publicista = @id_publicista

		update u
		set u.activo = 0
		from dbo.Usuarios u
		where u.id_usuario = @id_usuario

		update p
		set p.activo = 0
		from dbo.Publicistas p
		where p.id_publicista = @id_publicista

		if @@ROWCOUNT = 0
		begin
			raiserror('Error en la eliminación del Publcista. No se registraron los cambios', 16, 1)
		end

		update ps
		set ps.fecha_fin = GETDATE()
		from dbo.Publicidades ps
		where ps.id_publicista = @id_publicista
	end
	else
	begin
		raiserror('No existe un publicista con el id indicado', 16, 1)
	end
end
go

create or alter procedure dbo.insertar_publicidad
	@id_publicista integer,
	@banner_code integer,
	@exclusiva bit,
	@fecha_inicio date,
	@fecha_fin date,
	@id_publicidad integer out
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_registro integer;
	declare @id_tarifa_base integer;
	declare @id_tarifa_exclusiva integer;

	if not exists (select 1 from dbo.Banners where banner_code = @banner_code)
	begin
		raiserror('No existe el banner indicado', 16, 1)
	end;

	if not exists (select 1 from dbo.Publicistas where id_publicista = @id_publicista)
	begin
		raiserror('No existe un publicista con el id indicado', 16, 1)
	end;

	select @id_tarifa_base = id_tarifa_base, @id_tarifa_exclusiva = id_tarifa_uso_exclusivo
	from dbo.Banners
	where banner_code = @banner_code;

	insert into dbo.Publicidades (banner_code, exclusiva, fecha_inicio, fecha_fin, id_publicista)
	values (@banner_code, @exclusiva, @fecha_inicio, @fecha_fin, @id_publicista);

	if @@ROWCOUNT = 0
	begin
		raiserror('Se produjo un error al insertar la publicidad', 16, 1)
	end;

	set @id_publicidad = SCOPE_IDENTITY()

	insert into dbo.Registro_Tarifario (fecha_registro, id_tarifa)
	values (GETDATE(), @id_tarifa_base)

	set @id_registro = SCOPE_IDENTITY()

	insert into dbo.Registro_Tarifario_Publicidades (id_publicidad, id_registro_t)
	values (@id_publicidad, @id_registro)

	if @exclusiva = 1
	begin
		insert into dbo.Registro_Tarifario (fecha_registro, id_tarifa)
		values (GETDATE(), @id_tarifa_exclusiva)

		set @id_registro = SCOPE_IDENTITY()

		insert into dbo.Registro_Tarifario_Publicidades (id_publicidad, id_registro_t)
		values (@id_publicidad, @id_registro)
	end;
end
go

create or alter procedure dbo.modificar_publicidad
	@id_publicidad integer,
	@exclusiva bit,
	@fecha_inicio date,
	@fecha_fin date
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	if not exists (select 1 from dbo.Publicidades where id_publicidad = @id_publicidad)
	begin
		raiserror('El id de publicidad proporcionado no existe', 16, 1)
	end

	update p
	set p.fecha_fin = @fecha_fin,
		p.exclusiva = @exclusiva,
		p.fecha_inicio = @fecha_inicio
	from dbo.Publicidades p
	where p.id_publicidad = @id_publicidad

	if @@ROWCOUNT = 0
	begin
		raiserror('Error en la actualización de datos', 16, 1)
	end
end
go

create or alter procedure dbo.eliminar_publicidad
	@id_publicidad integer
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	if not exists (select 1 from dbo.Publicidades where id_publicidad = @id_publicidad)
	begin
		raiserror('El id de publicidad proporcionado no existe', 16, 1)
	end

	update p
	set p.fecha_fin = GETDATE()
	from dbo.Publicidades p
	where p.id_publicidad = @id_publicidad

	if @@ROWCOUNT = 0
	begin
		raiserror('Error al eliminar la publicidad', 16, 1)
	end
end
go

create or alter procedure dbo.obtener_fees_a_pagar
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select distinct p.id_plataforma, COUNT(distinct f.id_usuario) as cantidad_viewers_activos, t.monto as tarifa_x_viewer
	from dbo.Plataformas_Streaming p
	inner join dbo.Federaciones f on f.id_plataforma = p.id_plataforma
	inner join dbo.Tarifas t on t.id_tarifa = p.id_tarifa_viewer_activo
	where p.activa = 1
	and f.activa = 1
	and f.completa = 1
	group by p.id_plataforma, t.monto
end
go

create or alter procedure dbo.modificar_usuario
	@token_usuario varchar(255),
	@nombres varchar(50),
	@apellidos varchar(50),
	@password varchar(50)
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @id_usuario integer;

	select @id_usuario = id_usuario
	from dbo.Tokens_Usuario
	where token = @token_usuario

	if @id_usuario is null
	begin
		raiserror('El token proporcionado no corresponde a ningún usuario', 16, 1)
	end

	update u
	set u.nombres = @nombres,
		u.apellidos = @apellidos,
		u.u_password = @password
	from dbo.Usuarios u
	where u.id_usuario = @id_usuario
end
go

create or alter procedure dbo.obtener_federaciones_pendientes
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select f.id_plataforma, tu.token
	from dbo.Federaciones f
	left join dbo.Tokens_Usuario tu on tu.id_usuario = f.id_usuario
	where f.activa = 1
	and f.completa = 0
	and f.fecha_baja is null

end
go

create or alter procedure dbo.facturar_federaciones_nuevas
as
begin
	set nocount on;

	declare @FechaInicioMesAnterior date, @FechaFinMesAnterior date;
    set @FechaInicioMesAnterior = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0);
    set @FechaFinMesAnterior = DATEADD(DAY, -1, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0));

	create table #TempFederaciones(
		id_registro int,
		id_plataforma int,
		id_tarifa int,
		monto float,
		descripcion varchar,
		fecha_tarifa date,
		cantidad_federaciones int
	)

	insert into #TempFederaciones (id_registro, id_plataforma , id_tarifa, monto, descripcion, fecha_tarifa, cantidad_federaciones)
	select rt.id_registro, f.id_plataforma, rt.id_tarifa, t.monto, t.descripcion, t.fecha_creacion as fecha_tarifa, COUNT(DISTINCT rtf.id_federacion) as cantidad_federaciones
	from dbo.Registro_Tarifario rt
	join dbo.Registro_Tarifario_Federaciones rtf on rtf.id_registro_t = rt.id_registro
	join dbo.Federaciones f on rtf.id_federacion = f.id_federacion
	join dbo.Tarifas t on t.id_tarifa = rt.id_tarifa
	where f.is_new = 1
	and f.fecha_federacion between @FechaInicioMesAnterior and @FechaFinMesAnterior
	and f.completa = 1
	and rt.activo = 1
	and rt.facturado = 0
	group by rt.id_registro, f.id_plataforma, rt.id_tarifa, t.monto, t.descripcion, t.fecha_creacion

	create table #TempFacturas(
		id_factura int
	)

	insert into dbo.Facturas (fecha_facturacion, fecha_inicial, fecha_final, monto_total, id_tipo_factura)
	output inserted.id_factura into #TempFacturas
	values (getdate(), @FechaInicioMesAnterior, @FechaFinMesAnterior, 0,1)

	insert into dbo.Facturas_Plataforma (id_plataforma, id_factura)
	SELECT
		t1.id_plataforma,
		t2.id_factura
	FROM
		(SELECT id_plataforma, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) as rn FROM #TempFederaciones) t1
	JOIN
		(SELECT id_factura, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) as rn FROM #TempFacturas) t2
	ON
		t1.rn = t2.rn;

	insert into dbo.Detalles_Factura (id_factura, id_tarifa, cantidad, subtotal, descripcion)
	select fp.id_factura,
			tf.id_tarifa,
			tf.cantidad_federaciones as cantidad,
			tf.cantidad_federaciones * t.monto as subtotal,
			t.descripcion
	from #TempFederaciones tf
	join dbo.Facturas_Plataforma fp on fp.id_plataforma = tf.id_plataforma
	join dbo.Tarifas t on t.id_tarifa = tf.id_tarifa

	update rt
	set rt.facturado = 1
	from dbo.Registro_Tarifario rt
	join #TempFederaciones tf on tf.id_registro = rt.id_registro

	drop table #TempFederaciones;

	select f.id_factura, fp.id_plataforma as id_cliente, f.fecha_facturacion, f.fecha_inicial, f.fecha_final, f.monto_total,
			dt.descripcion, dt.cantidad, t.monto as monto_unitario, dt.subtotal
	from dbo.Facturas f
	join dbo.Facturas_Plataforma fp on fp.id_factura = f.id_factura
	join #TempFacturas tf on tf.id_factura = f.id_factura
	join dbo.Detalles_Factura dt on dt.id_factura = f.id_factura
	left join dbo.Tarifas t on t.id_tarifa = dt.id_tarifa

	drop table #TempFacturas;
end
go

create or alter procedure dbo.facturar_publicidades_activas
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	declare @FechaInicioMesAnterior date, @FechaFinMesAnterior date, @FechaInicioMesActual date;
    set @FechaInicioMesAnterior = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0);
    set @FechaFinMesAnterior = DATEADD(DAY, -1, DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0));
	set @FechaInicioMesActual = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0);

	create table #TempPublicidades(
		id_registro int,
		id_publicista int,
		id_publicidad int,
		id_tarifa int,
		monto float,
		descripcion varchar,
		fecha_tarifa date
	)

	insert into #TempPublicidades (id_registro, id_publicista, id_publicidad, id_tarifa, monto, descripcion, fecha_tarifa)
	select rt.id_registro, p.id_publicista, p.id_publicidad, rt.id_tarifa, t.monto, t.descripcion, t.fecha_creacion as fecha_tarifa
	from dbo.Registro_Tarifario rt
	join dbo.Registro_Tarifario_Publicidades rtp on rtp.id_registro_t = rt.id_registro
	join dbo.Publicidades p on rtp.id_publicidad = p.id_publicidad
	join dbo.Tarifas t on t.id_tarifa = rt.id_tarifa
	where rt.activo = 1
	and rt.facturado = 0
	and p.fecha_fin > @FechaInicioMesAnterior
	and p.fecha_inicio < @FechaFinMesAnterior
	group by rt.id_registro, p.id_publicista, p.id_publicidad, rt.id_tarifa, t.monto, t.descripcion, t.fecha_creacion

	create table #TempFacturas(
		id_factura int
	)
	
	insert into dbo.Facturas (fecha_facturacion, fecha_inicial, fecha_final, monto_total, id_tipo_factura)
	output inserted.id_factura into #TempFacturas
	values (getdate(), @FechaInicioMesAnterior, @FechaFinMesAnterior, 0,1)

	insert into dbo.Facturas_Publicista(id_publicista, id_factura)
	SELECT
		t1.id_publicista,
		t2.id_factura
	FROM
		(SELECT id_publicista, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) as rn FROM #TempPublicidades) t1
	JOIN
		(SELECT id_factura, ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) as rn FROM #TempFacturas) t2
	ON
		t1.rn = t2.rn;

	update rt
	set rt.facturado = 1
	from dbo.Registro_Tarifario rt
	join #TempPublicidades tp on tp.id_registro = rt.id_registro

	create table #TempPRegistrar(
		id_publicidad int, 
		exclusiva bit, 
		id_tarifa_base int, 
		monto_base float, 
		id_tarifa_uso_exclusivo int, 
		monto_exclusivo float,
		id_registro int default 0
	)

	insert into #TempPRegistrar(id_publicidad,exclusiva,id_tarifa_base, monto_base, id_tarifa_uso_exclusivo, monto_exclusivo)
	select p.id_publicidad, p.exclusiva, b.id_tarifa_base, tb.monto as monto_base, b.id_tarifa_uso_exclusivo, te.monto as monto_exclusivo
	from dbo.Publicidades p
	join dbo.Banners b on b.banner_code = p.banner_code
	join dbo.Tarifas tb on tb.id_tarifa = b.id_tarifa_base
	join dbo.Tarifas te on te.id_tarifa = b.id_tarifa_uso_exclusivo
	where p.fecha_fin > @FechaInicioMesActual
	group by p.id_publicidad, p.exclusiva, b.id_tarifa_base, tb.monto, b.id_tarifa_uso_exclusivo, te.monto
	
	create table #TempRegistros(
		id_registro int,
		id_tarifa int
	)

	insert into dbo.Registro_Tarifario (id_tarifa, fecha_registro)
	output inserted.id_registro, inserted.id_tarifa into #TempRegistros (id_registro, id_tarifa)
	select tp.id_tarifa_base, GETDATE()
	from #TempPRegistrar tp

	update tp
	set tp.id_registro = tr.id_registro
	from #TempPRegistrar tp
	join #TempRegistros tr on tr.id_tarifa = tp.id_tarifa_base

	delete from #TempRegistros;

	insert into dbo.Registro_Tarifario (id_tarifa, fecha_registro)
	output inserted.id_registro, inserted.id_tarifa into #TempRegistros (id_registro, id_tarifa)
	select tp.id_tarifa_uso_exclusivo, GETDATE()
	from #TempPRegistrar tp
	where tp.exclusiva = 1

	update tp
	set tp.id_registro = tr.id_registro
	from #TempPRegistrar tp
	join #TempRegistros tr on tr.id_tarifa = tp.id_tarifa_uso_exclusivo
	where tp.exclusiva = 1

	insert into dbo.Registro_Tarifario_Publicidades (id_publicidad, id_registro_t)
	select tp.id_publicidad, tp.id_registro
	from #TempPRegistrar tp
	where tp.id_registro <> 0

	drop table #TempRegistros;
	drop table #TempPRegistrar;

	insert into dbo.Detalles_Factura (id_factura, id_tarifa, cantidad, subtotal, descripcion)
	select fp.id_factura,
			tp.id_tarifa,
			1 as cantidad,
			t.monto as subtotal,
			t.descripcion
	from #TempPublicidades tp
	join dbo.Facturas_Publicista fp on fp.id_publicista = tp.id_publicista
	join dbo.Tarifas t on t.id_tarifa = tp.id_tarifa

	drop table #TempPublicidades

	select f.id_factura, fp.id_publicista as id_cliente, f.fecha_facturacion, f.fecha_inicial, f.fecha_final, f.monto_total,
			dt.descripcion, dt.cantidad, t.monto as monto_unitario, dt.subtotal
	from dbo.Facturas f
	join dbo.Facturas_Publicista fp on fp.id_factura = f.id_factura
	join #TempFacturas tf on tf.id_factura = f.id_factura
	join dbo.Detalles_Factura dt on dt.id_factura = f.id_factura
	left join dbo.Tarifas t on t.id_tarifa = dt.id_tarifa

	drop table #TempFacturas;
end
go

create or alter procedure dbo.obtener_tipos_servicio
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select id_tipo_servicio, tipo
	from dbo.Tipos_Servicio
end
go

create or alter procedure dbo.obtener_publicistas
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select p.id_publicista, p.nombre, p.razon_social, p.email, p.telefono,
			p.nombre_contacto, p.id_tipo_servicio, p.url_servicio as url_conexion, p.token_servicio
	from dbo.Publicistas p
	where p.activo = 1
end
go

CREATE OR ALTER PROCEDURE dbo.obtener_publicidades_a_mostrar
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

    DECLARE @min_id_publicidad INT;

    -- Obtener el id_publicidad más bajo cuando no hay ninguna publicidad que cumpla la condición
    SELECT TOP 1 @min_id_publicidad = id_publicidad
    FROM dbo.Publicidades
    WHERE fecha_fin >= GETDATE()
    ORDER BY id_publicidad;

    -- Seleccionar el id_publicidad de las publicidades que cumplen la condición o el mínimo si no hay ninguna
    SELECT COALESCE(p.id_publicidad, @min_id_publicidad) AS codigo_unico_id, p.banner_code, p.url_conexion as url_contenido, p.url_imagen
    FROM dbo.Publicidades p
    JOIN dbo.Banners b ON b.banner_code = p.banner_code
    WHERE p.id_publicidad > b.id_ult_publicidad
      AND (p.fecha_fin >= GETDATE());
END
GO

create or alter procedure dbo.actualizar_publicidades_batch
	@json nvarchar(max)
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	create table #publicidadesList
	(
		codigo_unico_id int,
		banner_code int,
		url_imagen varchar(255),
		url_contenido varchar(255)
	)

	insert into #publicidadesList
	select *
	from openjson(@json) with (
		codigo_unico_id int '$.codigo_unico_id',
		banner_code int '$.banner_code',
		url_imagen varchar(255) '$.url_imagen',
		url_contenido varchar(255) '$.url_contenido'
	)

	update p
	set p.url_conexion = pl.url_contenido,
		p.url_imagen = pl.url_imagen
	from dbo.Publicidades p
	left join #publicidadesList pl on pl.codigo_unico_id = p.id_publicidad
end
go

create or alter procedure dbo.obtener_contenidos_mas_vistos
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select top 10 c.eidr_contenido, count (v.id_visualizacion) as cantidad
	from dbo.Contenidos c
	join dbo.Plataformas_X_Contenido pxc on pxc.eidr_contenido = c.eidr_contenido
	join dbo.Visualizaciones v on v.id_plataforma_contenido = pxc.id_plataforma_contenido
	where v.fecha_visualizacion between DATEADD(DAY, -7, GETDATE()) and GETDATE()
	group by c.eidr_contenido
	order by count (v.id_visualizacion) desc
	
end
go

create or alter procedure dbo.obtener_estadisticas_viewers
	@fecha_inicio date,
	@fecha_fin date
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select p.id_plataforma as id_plataforma,
		(
			select count(f.id_federacion)
			from dbo.Federaciones f
			where f.id_plataforma = p.id_plataforma
			and (f.fecha_baja is null or f.fecha_baja <= @fecha_fin)
			and f.fecha_inicio >= @fecha_inicio
		) as cant_usuarios_activos,
		(
			select count(fn.id_federacion)
			from dbo.Federaciones fn
			where fn.id_plataforma = p.id_plataforma
			and (fn.fecha_baja is null or fn.fecha_baja <= @fecha_fin)
			and fn.fecha_inicio >= @fecha_inicio
			and fn.is_new = 1
		) as cant_registros_nuevos
	from dbo.Plataformas_Streaming p
end
go

create or alter procedure dbo.obtener_estadisticas_contenidos
	@fecha_inicio date,
	@fecha_fin date
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select p.id_plataforma as id_plataforma, p.eidr_contenido as eidr_contenido,
		(
			select count(*)
			from dbo.Visualizaciones v
			where v.id_plataforma_contenido = p.id_plataforma_contenido
			and v.fecha_visualizacion between @fecha_inicio and @fecha_fin
		) as cant_visualizaciones,
		(
			select count(*)
			from dbo.Likes_Usuarios l
			where l.eidr_contenido = p.eidr_contenido
			and l.fecha_like between @fecha_inicio and @fecha_fin
		) as cant_likes
	from dbo.Plataformas_X_Contenido p
end
go

create or alter procedure dbo.obtener_estadisticas_publicidades
	@fecha_inicio date,
	@fecha_fin date
as
begin
	set nocount on;
	set transaction isolation level repeatable read;

	select p.id_publicista as id_publicista, a.id_publicidad as id_publicidad, COUNT (a.id_acceso) as cant_accesos
	from dbo.Accesos_Publicidad a
	join dbo.Publicidades p on p.id_publicidad = a.id_publicidad
	where a.fecha_acceso between @fecha_inicio and @fecha_fin
	group by p.id_publicista, a.id_publicidad
end
go

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--                                                                   DATOS
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

insert into dbo.Niveles_Usuario (nivel)
values ('Suscriptor'), ('Publicista'), ('Administrador'),('Sistema')

insert into dbo.Usuarios (apellidos, email, id_nivel, nombres, u_password, validado)
values ('Ferreyra', 'tomaslicenciado@gmail.com',3,'Tomás','123456',1)

insert into dbo.Tokens_Usuario (id_usuario, token) values (1,'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiIxMjM0NTY3ODkwIiwiYXBlbGxpZG8iOiJKb2huIERvZSIsImVtYWlsIjoiVG9tYSQ2NDEzNjAifQ.HydLQ_FWDzv7yjPvntHXu3Ewk5lvKsUz1V32gJiQj7Y')

insert into dbo.Tarifas (descripcion, fecha_creacion, monto)
values ('Tarifa base banner vertical', GETDATE(), 1000),
('Tarifa uso exclusivo banner vertical', GETDATE(), 500),
('Tarifa base banner horizontal', GETDATE(), 800),
('Tarifa uso exclusivo banner horizontal', GETDATE(), 600)

insert into dbo.Banners (banner_code, id_tarifa_base, id_tarifa_uso_exclusivo, id_ult_publicidad)
values (1001,1,2,0),(2001,1,2,0),(3001,3,4,0)

insert into dbo.Tipos_Servicio (tipo) values ('SOAP'),('API/REST')

------------------------------------------------------------------------------------------------------------------------------------------------------
insert into dbo.Tarifas (descripcion, monto, fecha_creacion)
values ('Tarifa viewer activo HBO',50,GETDATE()),('Tarifa viewer activo Netflix',45,GETDATE()),('Tarifa viewer activo Prime',60,GETDATE()),('Tarifa viewer activo Disney',50,GETDATE()),
('Tarifa viewer nuevo',100,GETDATE())


select * from dbo.Tarifas

insert into dbo.Plataformas_Streaming (nombre, url_conexion, url_icono, email, token_servicio, id_tarifa_nuevos_viewers, id_tarifa_viewer_activo, id_tipo_servicio)
values ('HBO','http://localhost:8084/hbo','https://play-lh.googleusercontent.com/1iyX7VdQ7MlM7iotI9XDtTwgiVmqFGzqwz10L67XVoyiTmJVoHX87QtqvcXgUnb0AC8=w240-h480-rw','tomaslicenciado@gmail.com','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkhCTyIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.6CnlRhfy3tEzsk6Yt-yJ2sNfNrDkXUoZOC5y51lmUW0',9,5,1),
('Netflix','http://localhost:8085/netflix','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgzF_0vHXX2keCjLBvbQ-haFEyBjsd8iPvTAmSJEHNYDbL0de02Z-l8f7QUdnU75eZKTQ&usqp=CAU','tomaslicenciado@gmail.com','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6Ik5ldGZsaXgiLCJxdWVsb3F1ZSI6InBsYXRhZm9ybWEifQ._h5nT3TxxNjEo2O0VBoV4humz7KUtfISOCSj2wTkAAg',9,6,2),
('Prime','http://localhost:8086/prime','https://img.utdstc.com/icon/657/e60/657e607bbf486997cb7307e11ed88b3a90c40a94b093adc50c389721a250e65a:200','tomaslicenciado@gmail.com','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IlByaW1lIiwicXVlbG9xdWUiOiJwbGF0YWZvcm1hIn0.o1SW91Ztlwo1xSpqMGHur0AODep-so3Ahf-xgGrFKFA',9,7,2),
('Disney','http://localhost:8087/disney','https://i.pinimg.com/474x/d1/32/ab/d132abffb1021e2f5ae191f46a17680d.jpg','tomaslicenciado@gmail.com','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkRpc25leSIsInF1ZWxvcXVlIjoicGxhdGFmb3JtYSJ9.NBAinIs17FbbTjyEBLuE4RO-kzotWfhQxjTAOeHJaL4',9,8,2)

insert into dbo.Usuarios (apellidos, email, id_nivel, nombres, u_password)
values ('Juan','positivo@mail.com',2,'Juan','123456'),
('Pedro','jpg@mail.com',2,'Pedro','123456'),
('Paco','sietesentidos@mail.com',2,'Paco','123456')

select * from dbo.Usuarios

insert into dbo.Publicistas(nombre,razon_social, id_tipo_servicio, telefono, nombre_contacto, url_servicio, token_servicio, id_usuario, email)
values ('Positivo BGH','Positivo BGH',2,2,'Juan','http://localhost:8083/positivo','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InBvc2l0aXZvIn0.Q4ihAhMVdfHN0_EueMizfpTMLftozw9NApTNxeIctPA',2,'tomaslicenciado@gmail.com'),
('Siete Sentidos','Siete Sentidos',1,4,'Paco','http://localhost:8081/sietesentidos','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6InNpZXRlc2VudGlkb3MifQ.AeHMr9DjJt9dC37D8kHa4TllK7GQjPqzqEIg3h3Aneg',4,'tomaslicenciado@gmail.com'),
('JPG Grupo de Comunicación','JPG Grupo de Comunicación',1,3,'Pedro','http://localhost:8082/jpg','eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21icmUiOiJNU1MiLCJmZWNoYSI6IjEzLzEyLzIwMjMgMDA6MDA6MDAiLCJzZXJ2aWNpbyI6IkpQRyJ9.Lo1_nZVLhP09NtnWQJ4dCH5AITyViPYZrz0Ko2BJDJY',3,'tomaslicenciado@gmail.com')

select * from dbo.Publicistas

insert into dbo.Publicidades (id_publicista, banner_code, exclusiva, fecha_inicio, fecha_fin)
values (2,1001,0,GETDATE(), DATEADD(MONTH, 1, GETDATE())),(3,2001,0,GETDATE(), DATEADD(MONTH, 1, GETDATE())),(1,3001,0,GETDATE(), DATEADD(MONTH, 1, GETDATE()))

--------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------------------------------------------

--PRUEBAS

select * from dbo.Publicidades p left join dbo.Publicistas pl on p.id_publicista = pl.id_publicista

exec dbo.obtener_publicidades_a_mostrar;

exec dbo.obtener_banners

exec dbo.obtener_contenidos_activos '8a6ceeb81add49d8bb97459c04d35bcc9f69687e2411b2e2ab3e568bf8a08d29';


declare @fecha_inicio date, @fecha_fin date;
set @fecha_fin = GETDATE();
set @fecha_inicio = DATEADD(DAY, -7, @fecha_fin);

select p.id_publicista as id_publicista, a.id_publicidad as id_publicidad, COUNT (a.id_acceso) as cant_accesos
	from dbo.Accesos_Publicidad a
	join dbo.Publicidades p on p.id_publicidad = a.id_publicidad
	where a.fecha_acceso between @fecha_inicio and @fecha_fin
	group by p.id_publicista, a.id_publicidad

exec dbo.obtener_estadisticas_publicidades @fecha_inicio, @fecha_fin
exec dbo.obtener_estadisticas_contenidos @fecha_inicio, @fecha_fin