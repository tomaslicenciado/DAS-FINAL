package ar.edu.ubp.das.mss.repositories;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import ar.edu.ubp.das.mss.models.ActuacionCatalogo;
import ar.edu.ubp.das.mss.models.BannerBean;
import ar.edu.ubp.das.mss.models.Catalogo;
import ar.edu.ubp.das.mss.models.Codigo;
import ar.edu.ubp.das.mss.models.ContenidoSqlBean;
import ar.edu.ubp.das.mss.models.ContenidoCatalogo;
import ar.edu.ubp.das.mss.models.ContenidoFrontBean;
import ar.edu.ubp.das.mss.models.ContenidoXPlataformaSqlBean;
import ar.edu.ubp.das.mss.models.DetalleFacturaBean;
import ar.edu.ubp.das.mss.models.DireccionCatalogo;
import ar.edu.ubp.das.mss.models.FacturaBean;
import ar.edu.ubp.das.mss.models.GeneroContenidoBean;
import ar.edu.ubp.das.mss.models.PlataformaBean;
import ar.edu.ubp.das.mss.models.PublicacionBean;
import ar.edu.ubp.das.mss.models.PublicistaBean;
import ar.edu.ubp.das.mss.models.RegistroEstadisticoAcceso;
import ar.edu.ubp.das.mss.models.RegistroEstadisticoContenido;
import ar.edu.ubp.das.mss.models.RegistroEstadisticoViewer;
import ar.edu.ubp.das.mss.models.RespuestaBean;
import ar.edu.ubp.das.mss.models.TmpFD;
import ar.edu.ubp.das.mss.models.Usuario;
import ar.edu.ubp.das.mss.utils.ResponseHandler;
import ar.edu.ubp.das.mss.utils.ThreadPoolConfig;
import ar.edu.ubp.das.mss.utils.TokenGenerator;

@Repository
public class MSSRepository implements IMSSRepository{
    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    private ServicesRepository services;
    
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private SqlParameterSource in;
    private SimpleJdbcCall jdbcCall;
    private Map<String, Object> out;
    private Gson gson;

    public enum Nivel{
        SUSCRIPTOR(1),
        PUBLICISTA(2),
        ADMINISTRADOR(3),
        SISTEMA(4);

        private int nivel;

        Nivel(int codigo){
            this.nivel = codigo;
        }

        public int getNivel(){
            return nivel;
        }
    }

    public MSSRepository(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        this.gson = gsonBuilder.create();
    }
    

    @Override
    public RespuestaBean iniciarSesion(String email, String password) {
        try {
            jdbcCall = nuevaCall("obtener_usuario")
                                .returningResultSet("datos", BeanPropertyRowMapper.newInstance(Usuario.class));
            in = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
            out = jdbcCall.execute(in);
            if (!out.containsKey("datos"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta.", "dbo.obtener_usuario");
            List<Usuario> users = (List<Usuario>)out.get("datos");
            Usuario user = users.get(0);
            if (user.getId_nivel() == 0 || user.getToken() == null || user.getToken().isEmpty() || user.getId_nivel() == 0)
                return ResponseHandler.handleErrorResponse("Datos nulos o inválidos", new Exception("ERROR en iniciarSesion"));
            return new RespuestaBean(Codigo.OK, "Sesión iniciada con éxito", gson.toJson(user));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al iniciar sesión", e);
        }
    }

    @Override
    public RespuestaBean registrarSuscriptor(String email, String password, String nombres, String apellidos,
            List<Integer> id_preferencias_generos) {
        try {
            String token_usuario = TokenGenerator.generateToken(nombres+apellidos+email+password);
            jdbcCall = nuevaCall("registrar_suscriptor");
            in = new MapSqlParameterSource().addValue("email", email)
                                            .addValue("password", password)
                                            .addValue("nombres", nombres)
                                            .addValue("apellidos", apellidos)
                                            .addValue("token", token_usuario)
                                            .addValue("id_usuario", null, Types.INTEGER)
                                            .addValue("nivel", null, Types.VARCHAR);
            out = jdbcCall.execute(in);
            if (!out.containsKey("id_usuario"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta", "dbo.registrar_suscriptor");
            int id_usuario = (int)out.get("id_usuario");
            String nivel = (String)out.get("nivel");
            Usuario user = new Usuario(id_usuario, nombres, apellidos, 1, nivel, token_usuario, true);
            return new RespuestaBean(Codigo.CREADO, "Suscriptor registrado con éxito",gson.toJson(user));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al registrar el nuevo suscriptor", e);
        }
    }

    @Override
    public RespuestaBean iniciarFederacionPlataforma(int id_plataforma, String token_suscriptor, String url_retorno) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            Map<String, String> parametros = new LinkedHashMap<>();
            parametros.put("url_retorno", url_retorno);
            RespuestaBean respuestaPlataforma = services.consultarPlataforma(id_plataforma, "obtenerLoginUrl", parametros);
            if (respuestaPlataforma.getStatus() != Codigo.OK)
                return respuestaPlataforma;
            String transaction_id = obtenerValorDeJsonBody(respuestaPlataforma, "transaction_id", String.class,false);
            jdbcCall = nuevaCall("iniciar_federacion");
            in = new MapSqlParameterSource().addValue("id_plataforma", id_plataforma).addValue("token_usuario", token_suscriptor)
                            .addValue("transaction_id", transaction_id).addValue("id_federacion", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (!out.containsKey("id_federacion"))
                return ResponseHandler.handleSQLErrorResponse("Resultado de consulta vacío en variable id_federacion", "dbo.iniciar_federacion");
            int id_federacion = (int)out.get("id_federacion");
            if (id_federacion <= 0)
                return ResponseHandler.handleSQLErrorResponse("Error en la inserción. Datos incorectos", "dbo.iniciar_federacion");
            return respuestaPlataforma;
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar federar la nueva plataforma", e);
        }
    }

    @Override
    public RespuestaBean finalizarFederacionPlataforma(String token_suscriptor, int id_plataforma) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            String transaction_id = obtenerTransactionId(token_suscriptor, id_plataforma);
            Map<String, String> parametros = new LinkedHashMap<>();
            parametros.put("transaction_id", transaction_id);
            RespuestaBean tokenViewer = services.consultarPlataforma(id_plataforma, "obtenerTokenViewer", parametros);
            RespuestaBean usuarioNuevo = services.consultarPlataforma(id_plataforma, "esUsuarioNuevo", parametros);
            if (tokenViewer.getStatus() != Codigo.OK)
                return tokenViewer;
            if (usuarioNuevo.getStatus() != Codigo.OK)
                return usuarioNuevo;
            String token = obtenerValorDeJsonBody(tokenViewer, "token", String.class,false);
            boolean es_nuevo = gson.fromJson(usuarioNuevo.getBody(), new TypeToken<Boolean>(){}.getType());
            jdbcCall = nuevaCall("finalizar_federacion");
            in = new MapSqlParameterSource().addValue("token_usuario", token_suscriptor)
                                            .addValue("id_plataforma", id_plataforma)
                                            .addValue("token_viewer", token)
                                            .addValue("es_nuevo", es_nuevo)
                                            .addValue("actualizarCatalogo", null, Types.BIT);
            out = jdbcCall.execute(in);
            
            if (!out.containsKey("actualizarCatalogo"))
                return ResponseHandler.handleSQLErrorResponse("Resultado de consulta vacío en variable actualizarCatalogo", "dbo.iniciar_federacion");
            boolean actualizarCatalogo = (boolean)out.get("actualizarCatalogo");
            if (actualizarCatalogo)
                actualizarCatalogo();

            if (es_nuevo){
                jdbcCall = nuevaCall("insertar_registro_tarifario_federacion");
                in = new MapSqlParameterSource().addValue("transaction_id", transaction_id);
                jdbcCall.execute(in);
            }
            return new RespuestaBean(Codigo.OK, "Federación finalizada con éxito","");
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar finalizar la federación de la nueva plataforma", e);
        }
    }

    @Override
    public RespuestaBean obtenerListadoGenerosContenido(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("listar_generos").returningResultSet("generos", BeanPropertyRowMapper.newInstance(GeneroContenidoBean.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (!out.containsKey("generos"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta", "dbo.listar_generos");
            List<GeneroContenidoBean> generos = (List<GeneroContenidoBean>)out.get("generos");
            return new RespuestaBean(Codigo.OK, "Géneros obtenidos con éxito", gson.toJson(generos));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al obtener el listado de géneros", e);
        }
    }

    @Override
    public RespuestaBean obtenerListadoPlataformas(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("listar_plataformas").returningResultSet("plataformas", BeanPropertyRowMapper.newInstance(PlataformaBean.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (!out.containsKey("plataformas"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta", "dbo.listar_plataformas");
            List<PlataformaBean> plataformas = (List<PlataformaBean>)out.get("plataformas");
            return new RespuestaBean(Codigo.OK, "Plataformas obtenidas con éxito", gson.toJson(plataformas));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al obtener el listado de plataformas", e);
        }
    }

    @Override
    public RespuestaBean desuscribirPlataforma(int id_plataforma, String token_suscriptor) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("desuscribir_plataforma");
            in = new MapSqlParameterSource().addValue("id_plataforma", id_plataforma).addValue("token_usuario", token_suscriptor);
            out = jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Plataforma desuscrita con éxito","");
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar desuscribir la plataforma", e);
        }
    }

    @Override
    public RespuestaBean obtenerContenido(String token_suscriptor, int id_plataforma, String eidr_contenido) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            String sesion = obtenerSesion(id_plataforma, token_suscriptor, null);
            Map<String, String> parametros = new LinkedHashMap<>();
            parametros.put("sesion", sesion);
            parametros.put("eidr_contenido", eidr_contenido);
            return services.consultarPlataforma(id_plataforma, "obtenerUrlContenido", parametros);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al obtener el contenido", e);
        }
    }

    @Override
    public RespuestaBean obtenerCatalogo(String token_suscriptor) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("obtener_contenidos_activos").returningResultSet("contenidos", (rs, rowNum) -> {
                                ContenidoFrontBean contenido = new ContenidoFrontBean(rs.getString("eidr_contenido"),
                                                                                            rs.getString("titulo"),
                                                                                            rs.getString("url_imagen"),
                                                                                            rs.getString("descripcion"),
                                                                                            rs.getDate("fecha_estreno"),
                                                                                            rs.getString("genero"),
                                                                                            rs.getString("tipo_contenido"),
                                                                                            rs.getString("pais"),
                                                                                            null, null, null);
                                contenido.setActuaciones(gson.fromJson(rs.getString("actuaciones"), new TypeToken<List<ActuacionCatalogo>>(){}.getType()));
                                contenido.setDirecciones(gson.fromJson(rs.getString("direcciones"), new TypeToken<List<DireccionCatalogo>>(){}.getType()));
                                contenido.setCont_x_plataforma(gson.fromJson(rs.getString("plataformas_x_contenido"), new TypeToken<List<ContenidoXPlataformaSqlBean>>(){}.getType()));
                                return contenido;
                            });
            in = new MapSqlParameterSource().addValue("token_suscriptor", token_suscriptor);
            out = jdbcCall.execute(in);
            if (!out.containsKey("contenidos"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta", "dbo.obtener_contenidos_activos");
            List<ContenidoFrontBean> catalogo = (List<ContenidoFrontBean>)out.get("contenidos");
            return new RespuestaBean(Codigo.OK, "Catalogo obtenido con éxito", gson.toJson(catalogo));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al obtener el catálogo", e);
        }
    }

    @Override
    public RespuestaBean obtenerPublicidades(String token_suscriptor) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("obtener_publicidades_a_mostrar").returningResultSet("publicidades", BeanPropertyRowMapper.newInstance(PublicacionBean.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (!out.containsKey("publicidades"))
                return ResponseHandler.handleSQLErrorResponse("Resultado de consulta vacío", "dbo.obtener_publicidades_a_mostrar");
            List<PublicacionBean> publicidades = (List<PublicacionBean>)out.get("publicidades");
            return new RespuestaBean(Codigo.OK, "Publicidades obtenidas con éxito", gson.toJson(publicidades));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al obtener las publicidades", e);
        }
    }

    @Override
    public RespuestaBean registrarVisualizacion(String token_suscriptor, int id_plataforma, String eidr_contenido) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("registrar_visualizacion");
            in = new MapSqlParameterSource().addValue("token_suscriptor", token_suscriptor)
                                            .addValue("id_plataforma", id_plataforma)
                                            .addValue("eidr_contenido", eidr_contenido);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Visualización de contenido registrada con éxito", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar registrar la visualización", e);
        }
    }

    @Override
    public RespuestaBean registrarAccesoPublicidad(String token_suscriptor, int id_publicidad) {
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel != Nivel.SUSCRIPTOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("registrar_acceso_publicidad");
            in = new MapSqlParameterSource().addValue("token_usuario", token_suscriptor).addValue("id_publicidad", id_publicidad);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Acceso a publicidad registrado con éxito", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar registrar el acceso a la publicidad", e);
        }
    }

    @Override
    public RespuestaBean registrarNuevaPlataforma(String token_usuario, String nombre, String url_conexion,
            String url_icono, String email, int id_tipo_servicio, String token_servicio, float tarifa_nuevos_viewers, float tarifa_viewers_activos) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            
            jdbcCall = nuevaCall("insertar_plataforma");
            in = new MapSqlParameterSource().addValue("nombre", nombre)
                                            .addValue("url_conexion", url_conexion)
                                            .addValue("url_icono", url_icono)
                                            .addValue("email", email)
                                            .addValue("id_tipo_servicio", id_tipo_servicio)
                                            .addValue("token_servicio", token_servicio)
                                            .addValue("tarifa_nuevos_viewers", tarifa_nuevos_viewers)
                                            .addValue("tarifa_viewers_activos", tarifa_viewers_activos);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.CREADO, "Se registró correctamente la nueva plataforma", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar registrar la nueva plataforma", e);
        }
    }

    @Override
    public RespuestaBean actualizarPlataforma(String token_usuario, int id_plataforma, String nombre,
            String url_conexion, String url_icono, String email, int id_tipo_servicio, String token_servicio, float tarifa_nuevos_viewers, float tarifa_viewers_activos) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("actualizar_plataforma");
            in = new MapSqlParameterSource().addValue("nombre", nombre)
                                            .addValue("url_conexion", url_conexion)
                                            .addValue("url_icono", url_icono)
                                            .addValue("email", email)
                                            .addValue("id_tipo_servicio", id_tipo_servicio)
                                            .addValue("token_servicio", token_servicio)
                                            .addValue("tarifa_nuevos_viewers", tarifa_nuevos_viewers)
                                            .addValue("tarifa_viewers_activos", tarifa_viewers_activos);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se actualizó correctamente la plataforma", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar actualizar los datos de la plataforma", e);
        }
    }

    @Override
    public RespuestaBean eliminarPlataforma(String token_usuario, int id_plataforma) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("desactivar_plataforma");
            in = new MapSqlParameterSource().addValue("id_plataforma", id_plataforma);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se eliminó correctamente la plataforma", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar eliminar la plataforma", e);
        }
    }

    @Override
    public RespuestaBean modificarBanner(String token_usuario, int banner_code, float tarifa_base,
            float tarifa_exclusividad) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("modificar_banner");
            in = new MapSqlParameterSource().addValue("banner_code", banner_code).addValue("tarifa_base", tarifa_base)
                        .addValue("tarifa_exclusividad", tarifa_exclusividad);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se modificó correctamente el banner", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar modificar el banner", e);
        }
    }

    @Override
    public RespuestaBean agregarPublicista(String token_usuario, String nombre, String razon_social, String email,
            int telefono, String nombre_contacto, String url_conexion, String token_servicio, int id_tipo_servicio) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            String token_usr_p = TokenGenerator.generateToken(nombre + nombre_contacto + token_servicio);
            jdbcCall = nuevaCall("insertar_publicista");
            in = new MapSqlParameterSource().addValue("nombre", nombre)
                                            .addValue("razon_socail", razon_social)
                                            .addValue("id_tipo_servicio", id_tipo_servicio)
                                            .addValue("telefono", telefono)
                                            .addValue("nombre_contacto", nombre_contacto)
                                            .addValue("url_servicio", url_conexion)
                                            .addValue("token_servicio", token_servicio)
                                            .addValue("email", email)
                                            .addValue("token_usuario", token_usr_p);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.CREADO, "Se insertó correctamente el publicista", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar agregar el publicista", e);
        }
    }

    @Override
    public RespuestaBean modificarPublicista(String token_usuario, int id_publicista, String nombre,
            String razon_social, String email, int telefono, String nombre_contacto,
            int id_tipo_servicio, String url_conexion, String token_servicio) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("modificar_publicista");
            in = new MapSqlParameterSource().addValue("id_publicista", id_publicista)
                                            .addValue("nombre", nombre)
                                            .addValue("razon_social", razon_social)
                                            .addValue("id_tipo_servicio", id_tipo_servicio)
                                            .addValue("telefono", telefono)
                                            .addValue("nombre_contacto", nombre_contacto)
                                            .addValue("url_servicio", url_conexion)
                                            .addValue("token_servicio", token_servicio)
                                            .addValue("email", email);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se modificó correctamente el publicista", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar modificar el publicista", e);
        }
    }

    @Override
    public RespuestaBean eliminarPublicista(String token_usuario, int id_publicista) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("eliminar_publicista");
            in = new MapSqlParameterSource().addValue("id_publicista", id_publicista);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se eliminó correctamente el publicista", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar eliminar el publicista", e);
        }
    }

    @Override
    public RespuestaBean agregarPublicidad(String token_usuario, int id_publicista, int banner_code, boolean exclusiva,
            Date fecha_inicio, Date fecha_fin) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("insertar_publicidad");
            in = new MapSqlParameterSource().addValue("id_publicista", id_publicista)
                                            .addValue("banner_code", banner_code)
                                            .addValue("exclusiva", exclusiva)
                                            .addValue("fecha_inicio", fecha_inicio)
                                            .addValue("fecha_fin", fecha_fin)
                                            .addValue("id_publicidad", null, Types.INTEGER);
            out = jdbcCall.execute(in);
            if (!out.containsKey("id_publicidad"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta.", "dbo.insertar_publicidad");
            int id_publicidad = (int)out.get("id_publicidad");
            Map<String, String> datos = new HashMap<>();
            datos.put("id_publicidad", Integer.toString(id_publicidad));
            return new RespuestaBean(Codigo.CREADO, "Publicidad agregada con éxtio. Transmitir el id de publicidad al publicista", gson.toJson(datos));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar agregar la publicidad", e);
        }
    }

    @Override
    public RespuestaBean modificarPublicidad(String token_usuario, int id_publicidad, boolean exclusiva, Date fecha_inicio, Date fecha_fin) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("modificar_publicidad");
            in = new MapSqlParameterSource().addValue("id_publicidad", id_publicidad).addValue("exclusiva", exclusiva)
                                            .addValue("fecha_inicio", fecha_inicio)
                                            .addValue("fecha_fin", fecha_fin);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se actualizó correctamente la publicidad", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar modificar la publicidad", e);
        }
    }

    @Override
    public RespuestaBean eliminarPublicidad(String token_usuario, int id_publicidad) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("eliminar_publicidad");
            in = new MapSqlParameterSource().addValue("id_publicidad", id_publicidad);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Se eliminó correctamente la publicidad", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar eliminar la publicidad", e);
        }
    }

    @Override
    public RespuestaBean obtenerFeesAPagar(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            List<String> jsons = new ArrayList<>();
            jdbcCall = nuevaCall("obtener_fees_a_pagar")
                        .returningResultSet("viewers_activos", (rs,rowNum) -> {
                            Map<String, Object> row = new HashMap<>();
                            int cant = rs.getInt("cantidad_viewers_activos");
                            float tarifa = rs.getFloat("tarifa_x_viewer");
                            float total = tarifa * (float)cant;
                            row.put("id_plataforma", rs.getInt("id_plataforma"));
                            row.put("cantidad_viewers", cant);
                            row.put("tarifa_x_viewer", tarifa);
                            row.put("total_a_pagar", total);
                            jsons.add(gson.toJson(row));
                            return row;
                        });
            in = new MapSqlParameterSource();
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Totales calculados con éxito", gson.toJson(jsons));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar obtener los fees a pagar", e);
        }
    }

    @Override
    public RespuestaBean modificarDatosPersonales(String token_usuario, String nombres, String apellidos,
            String password) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("modificar_usuario");
            in = new MapSqlParameterSource().addValue("token_usuario", token_usuario)
                                            .addValue("nombres", nombres)
                                            .addValue("apellidos", apellidos)
                                            .addValue("password", password);
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Datos actualizados con éxito", null);
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar modificar los datos del usuario", e);
        }
    }
    

    @Override
    public RespuestaBean obtenerListadoPublicistas(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel != Nivel.ADMINISTRADOR.getNivel())
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("obtener_publicistas").returningResultSet("publicistas", BeanPropertyRowMapper.newInstance(PublicistaBean.class));
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (!out.containsKey("publicistas"))
                return ResponseHandler.handleSQLErrorResponse("Resulset vacío al realizar la consulta.", "dbo.obtener_publicistas");
            List<PublicistaBean> publicistas = (List<PublicistaBean>)out.get("publicistas");
            return new RespuestaBean(Codigo.OK, "Publicistas obtenidos con éxito", gson.toJson(publicistas));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar obtener los banners", e);
        }
    }

    @Override
    public RespuestaBean obtenerListadoTiposServicio(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            Map<Integer, String> tipos_servicio = new HashMap<>();
            jdbcCall = nuevaCall("obtener_tipos_servicio").returningResultSet("tipos", (rs, rowNum) -> {
                tipos_servicio.put(rs.getInt("id_tipo_servicio"), rs.getString("tipo"));
                return null;
            });
            in = new MapSqlParameterSource();
            jdbcCall.execute(in);
            return new RespuestaBean(Codigo.OK, "Tipos de servicio obtenidos con éxito", gson.toJson(tipos_servicio));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar obtener los banners", e);
        }
    }

    @Override
    public RespuestaBean obtenerListadoBanners(String token_usuario) {
        try {
            int nivel = obtenerNivelUsuario(token_usuario);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            List<BannerBean> banners = obtenerBanners();
            return new RespuestaBean(Codigo.OK, "Banners obtenidos con éxito", gson.toJson(banners));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error al intentar obtener los banners", e);
        }
    }

    @Override
    public RespuestaBean obtenerContenidosMasVistos(String token_suscriptor){
        try {
            int nivel = obtenerNivelUsuario(token_suscriptor);
            if (nivel == -1)
                return ResponseHandler.handleUnauthorizedResponse("No tiene permisos para realizar esta operación");
            jdbcCall = nuevaCall("obtener_contenidos_mas_vistos")
                        .returningResultSet("contenidos", (rs, rowNum) -> {
                            return rs.getString("eidr_contenido");
                        });
            in = new MapSqlParameterSource();
            out = jdbcCall.execute(in);
            if (!out.containsKey("contenidos"))
                return ResponseHandler.handleSQLErrorResponse("MSS: Resulset vacío al realizar la consulta", "dbo.obtener_contenidos_mas_vistos");
            List<String> eidrs = (List<String>)out.get("contenidos");
            return new RespuestaBean(Codigo.OK, "Contenidos obtenidos correctamente", gson.toJson(eidrs));
        } catch (Exception e) {
            return ResponseHandler.handleErrorResponse("Error en la obtención de contenido más visto", e);
        }
    }

    /*----------------------------------------------------------------------------------------------------------------------------------- */
    /*                                                    TAREAS PROGRAMADAS                                                              */
    /*----------------------------------------------------------------------------------------------------------------------------------- */
    
    @Override
    public void finalizarFederacionesPendientes() throws Exception {
        Map<Integer, String> federaciones_pendientes = new HashMap<>();
        jdbcCall = nuevaCall("obtener_federaciones_pendientes")
                        .returningResultSet("federaciones_pendientes", (rs,rowNum) -> {
                            federaciones_pendientes.put(rs.getInt("id_plataforma"), rs.getString("token"));
                            return rs;
                        });
        in = new MapSqlParameterSource();
        jdbcCall.execute(in);
        if (out.containsKey("federaciones_pendientes") && federaciones_pendientes.size() > 0){
            for (Map.Entry<Integer, String> fed : federaciones_pendientes.entrySet()){
                finalizarFederacionPlataforma(fed.getValue(), fed.getKey());
            }
        }
    }

    @Override
    public void enviarFacturas() throws Exception {
        List<FacturaBean> facturasPlataformas = new ArrayList<>();
        List<FacturaBean> facturasPublicistas = new ArrayList<>();
        //Proceso el resultado de las facturaciones a plataformas
        jdbcCall = nuevaCall("facturar_federaciones_nuevas")
                    .returningResultSet("resultado", BeanPropertyRowMapper.newInstance(TmpFD.class));
        in = new MapSqlParameterSource();
        out = jdbcCall.execute(in);
        List<TmpFD> resultadoFederaciones = (List<TmpFD>)out.get("resultado");
        FacturaBean factura = null;
        for (int i = 0; i < resultadoFederaciones.size(); i++) {
            TmpFD rf = resultadoFederaciones.get(i);
            if (factura == null || factura.getId_factura() != rf.getId_factura()){
                OptionalInt indice = IntStream.range(0, facturasPlataformas.size())
                                                .filter(j -> facturasPlataformas.get(j).getId_factura() == rf.getId_factura())
                                                .findFirst();
                if (indice.isPresent())
                    factura = facturasPlataformas.get(indice.getAsInt());
                else{
                    factura = new FacturaBean(rf.getId_factura(), rf.getId_cliente(),rf.getFecha_facturacion(),
                                                        rf.getFecha_final(), rf.getFecha_final(),rf.getMonto_total(), new ArrayList<DetalleFacturaBean>());
                    facturasPlataformas.add(factura);
                }
            }
            List<DetalleFacturaBean> detalles = factura.getDetalles();
            detalles.add(new DetalleFacturaBean(rf.getDescripcion(),rf.getCantidad(),rf.getMonto_unitario(),rf.getSubtotal()));
        }

        //proceso el resultado de las facturaciones a publicistas
        jdbcCall = nuevaCall("facturar_publicidades_activas")
                    .returningResultSet("resultado", BeanPropertyRowMapper.newInstance(TmpFD.class));
        in = new MapSqlParameterSource();
        out = jdbcCall.execute(in);
        List<TmpFD> resultadoPublicidades = (List<TmpFD>)out.get("resultado");
        factura = null;
        for (int i = 0; i < resultadoPublicidades.size(); i++) {
            TmpFD rf = resultadoPublicidades.get(i);
            if (factura == null || factura.getId_factura() != rf.getId_factura()){
                OptionalInt indice = IntStream.range(0, facturasPublicistas.size())
                                                .filter(j -> facturasPublicistas.get(j).getId_factura() == rf.getId_factura())
                                                .findFirst();
                if (indice.isPresent())
                    factura = facturasPublicistas.get(indice.getAsInt());
                else{
                    factura = new FacturaBean(rf.getId_factura(), rf.getId_cliente(),rf.getFecha_facturacion(),
                                                        rf.getFecha_final(), rf.getFecha_final(),rf.getMonto_total(), new ArrayList<DetalleFacturaBean>());
                    facturasPublicistas.add(factura);
                }
            }
            List<DetalleFacturaBean> detalles = factura.getDetalles();
            detalles.add(new DetalleFacturaBean(rf.getDescripcion(),rf.getCantidad(),rf.getMonto_unitario(),rf.getSubtotal()));
        }

        //Envío por mail?
    }

    @Override
    public void enviarEstadisticas() throws Exception {
        Map<Integer, List<RegistroEstadisticoViewer>> estadisticasVPlataformas = new HashMap<>();
        Map<Integer, List<RegistroEstadisticoContenido>> estadisticasCPlataformas = new HashMap<>();
        Map<Integer, List<RegistroEstadisticoAcceso>> estadisticasPublicidades = new HashMap<>();
        Date fecha_fin = new Date();
        long fn_milisegundos = fecha_fin.getTime();
        long siete_dias_milisegundos = 7 * 24 * 60 * 60 * 1000;
        Date fecha_inicio = new Date(fn_milisegundos - siete_dias_milisegundos);

        //Obtengo estadísticas de Viewers por plataforma
        jdbcCall = nuevaCall("obtener_estadisticas_viewers")
            .returningResultSet("estadisticas", (rs, rowNum) -> {
                int id = rs.getInt("id_plataforma");
                RegistroEstadisticoViewer rv = new RegistroEstadisticoViewer(
                    fecha_inicio,
                    fecha_fin,
                    rs.getInt("cant_usuarios_activos"),
                    rs.getInt("cant_registros_nuevos")
                );
                if (estadisticasVPlataformas.containsKey(id))
                    estadisticasVPlataformas.get(id).add(rv);
                else{
                    estadisticasVPlataformas.put(id, new ArrayList<RegistroEstadisticoViewer>());
                    estadisticasVPlataformas.get(id).add(rv);
                }
                return null;
            });
        in = new MapSqlParameterSource().addValue("fecha_inicio", fecha_inicio).addValue("fecha_fin", fecha_fin);
        jdbcCall.execute(in);

        //Obtengo estadísticas de contenidos por plataforma
        jdbcCall = nuevaCall("obtener_estadisticas_contenidos")
            .returningResultSet("estadisticas", (rs, rowNum) -> {
                int id = rs.getInt("id_plataforma");
                RegistroEstadisticoContenido rc = new RegistroEstadisticoContenido(
                    rs.getString("eidr_contenido"),
                    fecha_inicio,
                    fecha_fin,
                    rs.getInt("cant_visualizaciones"),
                    rs.getInt("cant_likes")
                );
                if (estadisticasCPlataformas.containsKey(id))
                    estadisticasCPlataformas.get(id).add(rc);
                else{
                    estadisticasCPlataformas.put(id, new ArrayList<RegistroEstadisticoContenido>());
                    estadisticasCPlataformas.get(id).add(rc);
                }
                return null;
            });
        in = new MapSqlParameterSource().addValue("fecha_inicio", fecha_inicio).addValue("fecha_fin", fecha_fin);
        jdbcCall.execute(in);

        //Obtengo estadísticas de accesos a publicidades
        jdbcCall = nuevaCall("obtener_estadisticas_publicidades")
            .returningResultSet("estadisticas", (rs, rowNum) -> {
                int id = rs.getInt("id_publicista");
                RegistroEstadisticoAcceso ra = new RegistroEstadisticoAcceso(
                    rs.getInt("id_publicidad"),
                    rs.getInt("cant_accesos"),
                    fecha_inicio, 
                    fecha_fin
                );
                if (estadisticasPublicidades.containsKey(id))
                    estadisticasPublicidades.get(id).add(ra);
                else{
                    estadisticasPublicidades.put(id, new ArrayList<RegistroEstadisticoAcceso>());
                    estadisticasPublicidades.get(id).add(ra);
                }
                return null;
            });
        in = new MapSqlParameterSource().addValue("fecha_inicio", fecha_inicio).addValue("fecha_fin", fecha_fin);
        jdbcCall.execute(in);

        List<RespuestaBean> respuestas = new ArrayList<>();
        //Enviar estadísticas
        System.out.println(gson.toJson(estadisticasPublicidades));
        for (Map.Entry<Integer, List<RegistroEstadisticoAcceso>> entry : estadisticasPublicidades.entrySet()) {
            Map<String, String> parametros = new HashMap<>();
            parametros.put("estadisticas_accesos_json", gson.toJson(entry.getValue()));
            respuestas.add(services.consultarPublicista(entry.getKey(), "insertarEstadisticas", parametros));
        }

        List<Integer> ids_plataformas = new ArrayList<>();
        for (Integer i : estadisticasCPlataformas.keySet()) {
            if (!ids_plataformas.contains(i))
                ids_plataformas.add(i);
        }
        for (Integer i : estadisticasVPlataformas.keySet()) {
            if (!ids_plataformas.contains(i))
                ids_plataformas.add(i);
            
        }

        for (Integer i : ids_plataformas) {
            List<RegistroEstadisticoContenido> contenidos = new ArrayList<>();
            List<RegistroEstadisticoViewer> viewers = new ArrayList<>();
            if (estadisticasCPlataformas.containsKey(i))
                contenidos = estadisticasCPlataformas.get(i);
            if (estadisticasVPlataformas.containsKey(i))
                viewers = estadisticasVPlataformas.get(i);
            Map<String, String> parametros = new HashMap<>();
            parametros.put("estadisticas_contenidos_json", gson.toJson(contenidos));
            parametros.put("estadisticas_viewers_json", gson.toJson(viewers));
            respuestas.add(services.consultarPlataforma(i, "insertarEstadisticas", parametros));
        }
    }

    @Override
    public void actualizarCatalogo() throws Exception {
        List<AtomicReference<Exception>> exceptionReferences = new ArrayList<>();
        Map<Integer, String> tokens = obtenerTokensViewers();
        Map<Integer, Catalogo> catalogosObtenidos = new LinkedHashMap<>();
        //Realizo llamada en paralelo a todas las plataformas para obtener el catálogo de cada una
        for (Map.Entry<Integer, String> token : tokens.entrySet()) {
            Map<String, String> params = new LinkedHashMap<>();
            String sesion = obtenerSesion(token.getKey(), null, token.getValue());
            params.put("sesion", sesion);
            RespuestaBean resp = services.consultarPlataforma(token.getKey(), "obtenerCatalogo", params);
            if (resp.getStatus() != Codigo.OK)
                throw new Exception("Error al obtener el catálogo - " + resp.getBody() + " - " + resp.getMensaje());
            catalogosObtenidos.put(token.getKey(), gson.fromJson(resp.getBody(), new TypeToken<Catalogo>(){}.getType()));
        }
        //Espero que se completen todas las llamadas paralelas
        //Verifico si hay alguna excepción
        List<DireccionCatalogo> direcciones = new ArrayList<>();
        List<ActuacionCatalogo> actuaciones = new ArrayList<>();
        List<ContenidoSqlBean> contenidos = new ArrayList<>();
        List<ContenidoXPlataformaSqlBean> contenidosXPlataformas = new ArrayList<>();
        for (Map.Entry<Integer, Catalogo> entry : catalogosObtenidos.entrySet()) {
            int id_plataforma = entry.getKey();
            Catalogo catalogo = entry.getValue();
            direcciones.addAll(catalogo.getDirecciones());
            actuaciones.addAll(catalogo.getActuaciones());
            //Separo los contenidos en ContenidoBean y ContenidoXPlataformaBean, para adecuar el contenido a la estructura de la base de datos
            for (ContenidoCatalogo cont : catalogo.getContenidos()){
                if (!contenidos.stream().anyMatch(contenido -> contenido.getEidr_contenido() == cont.getEidr_contenido()))
                    contenidos.add(new ContenidoSqlBean(cont.getEidr_contenido(), 
                                cont.getTitulo(), cont.getUrl_imagen(),cont.getDescripcion(),
                                cont.getFecha_estreno(),cont.getGenero(),cont.getPais(),cont.getTipo_contenido()));
                contenidosXPlataformas.add(new ContenidoXPlataformaSqlBean(id_plataforma, cont.getEidr_contenido(),cont.getFecha_Carga(),cont.isDestacado()));
            }
        }

        jdbcCall = nuevaCall("insertar_contenidos_batch");
        in = new MapSqlParameterSource().addValue("json", gson.toJson(contenidos));
        jdbcCall.execute(in);
        jdbcCall = nuevaCall("insertar_plataformas_x_contenido_batch");
        in = new MapSqlParameterSource().addValue("json", gson.toJson(contenidosXPlataformas));
        jdbcCall.execute(in);
        //Inserto los datos de Direcciones
        jdbcCall = nuevaCall("insertar_direcciones_batch");
        in = new MapSqlParameterSource().addValue("json", gson.toJson(direcciones));
        jdbcCall.execute(in);
        //Inserto los datos de Actuaciones
        jdbcCall = nuevaCall("insertar_actuaciones_batch");
        in = new MapSqlParameterSource().addValue("json", gson.toJson(actuaciones));
        jdbcCall.execute(in);
    }

    @Override
    public void actualizarPublicidades() throws Exception {
        List<Integer> ids_publicistas_activos = obtenerIdsPublicistas();
        List<PublicacionBean> publicidades = new ArrayList<>();
        for (int id_publicista : ids_publicistas_activos){
            RespuestaBean respPublicista = services.consultarPublicista(id_publicista, "obtenerPublicidades", null);
            if (respPublicista.getStatus() != Codigo.OK)
                throw new Exception(respPublicista.getMensaje() + " - " + id_publicista);
            List<PublicacionBean> pub = (List<PublicacionBean>) obtenerValorDeJsonBody(respPublicista, null, PublicacionBean.class, true);
            publicidades.addAll(pub);
        }
        jdbcCall = nuevaCall("actualizar_publicidades_batch");
        in = new MapSqlParameterSource().addValue("json", gson.toJson(publicidades)); 
        out = jdbcCall.execute(in);
    }
    /*----------------------------------------------------------------------------------------------------------------------------------- */
    /*                                                    FUNCIONES PRIVADAS                                                              */
    /*----------------------------------------------------------------------------------------------------------------------------------- */

    private String obtenerSesion(int id_plataforma, String token_suscriptor, String viewer_token) throws Exception{
        String token_viewer = viewer_token != null && !viewer_token.isEmpty() ? viewer_token : obtenerTokenViewer(token_suscriptor, id_plataforma);
        if (token_viewer == null || token_viewer.isEmpty())
            throw new Exception("Error al obtener el token de viewer. Token no válido o vacío");
        Map<String, String> parametros = new LinkedHashMap<>();
        parametros.put("token_viewer", token_viewer);
        RespuestaBean sesion = services.consultarPlataforma(id_plataforma, "obtenerSesion", parametros);
        if (sesion.getStatus() != Codigo.OK)
            throw new Exception(sesion.getMensaje() + " - " + sesion.getBody());
        String sesionValue = obtenerValorDeJsonBody(sesion, "sesion", String.class, false);
        if (sesionValue == null || sesionValue.isEmpty())
            throw new Exception("Error al obtener la sesión. Sesión no válida o vacía");
        return sesionValue;
    }

    private String obtenerTokenViewer(String token_suscriptor, int id_plataforma) throws Exception { //throws Exception
        jdbcCall = nuevaCall("obtener_token_viewer");
        in = new MapSqlParameterSource().addValue("token_usuario", token_suscriptor).addValue("id_plataforma", id_plataforma)
                        .addValue("token_viewer", null, Types.VARCHAR);
        out = jdbcCall.execute(in);
        if (!out.containsKey("token_viewer"))
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.obtener_token_viewer");
        return (String)out.get("token_viewer");
    }

    private Map<Integer, String> obtenerTokensViewers() throws Exception{
        jdbcCall = nuevaCall("obtener_tokens_disponibles")
                            .returningResultSet("tokens", (rs, rowNum) -> {
                                Map.Entry<Integer, String> result;
                                result = new AbstractMap.SimpleEntry<>(rs.getInt("id_plataforma"), rs.getString("token"));
                                return result;
                            });
        in = new MapSqlParameterSource();
        out = jdbcCall.execute(in);
        if (!out.containsKey("tokens"))
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.obtener_tokens_disponibles");
        List<Map.Entry<Integer, String>> entryList = (List<Map.Entry<Integer, String>>)out.get("tokens");
        Map<Integer, String> resultMap = Map.ofEntries(
            entryList.stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue()))
                .toArray(Map.Entry[]::new)
        );
        return resultMap;
    }

    private String obtenerTransactionId(String token_suscriptor, int id_plataforma) throws Exception{
        jdbcCall = nuevaCall("obtener_transaction_id");
        in = new MapSqlParameterSource().addValue("token_usuario", token_suscriptor)
                        .addValue("id_plataforma", id_plataforma)
                        .addValue("transaction_id", null, Types.VARCHAR);
        out = jdbcCall.execute(in);
        if (!out.containsKey("transaction_id")) 
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.obtener_transaction_id");
        String transaction_id = (String)out.get("transaction_id");
        if (transaction_id == null || transaction_id.isEmpty())
            throw new Exception("El transaction id es inexistente o no es válido");
        return transaction_id;
    }

    public int obtenerNivelUsuario(String token_usuario) throws Exception{
        jdbcCall = nuevaCall("obtener_nivel_usuario");
        in = new MapSqlParameterSource().addValue("token_usuario", token_usuario).addValue("nivel", null, Types.INTEGER);
        out = jdbcCall.execute(in);
        if (!out.containsKey("nivel"))
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.obtener_nivel_usuario");
        return (int)out.get("nivel");
    }

    private List<BannerBean> obtenerBanners() throws Exception{
        jdbcCall = nuevaCall("obtener_banners").returningResultSet("banners", BeanPropertyRowMapper.newInstance(BannerBean.class));
        in = new MapSqlParameterSource();
        out = jdbcCall.execute(in);
        if (!out.containsKey("banners"))
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.obtener_banners");
        List<BannerBean> banners = (List<BannerBean>)out.get("banners");
        return banners;
    }

    private List<Integer> obtenerIdsPublicistas() throws Exception{
        jdbcCall = nuevaCall("listar_id_publicistas").returningResultSet("ids", (rs, rowNum) -> {
            return Integer.valueOf(rs.getInt("id_publicista"));
        });
        in = new MapSqlParameterSource();
        out = jdbcCall.execute(in);
        if (!out.containsKey("ids"))
            throw new SQLException("Resulset vacío al realizar la consulta - dbo.listar_id_publicistas");
        return (List<Integer>)out.get("ids");
    }

    /*---------------------------------------------UTILITARIOS---------------------------------------------------------- */
    private SimpleJdbcCall nuevaCall(String procedureName){
        return new SimpleJdbcCall(jdbcTpl).withSchemaName("dbo").withProcedureName(procedureName);
    }

    private <T> T obtenerValorDeJsonBody(RespuestaBean resp, String clave, Class<T> clase, boolean esLista) {
        JsonElement jsonElement;
        if (clave != null) {
            jsonElement = JsonParser.parseString(resp.getBody()).getAsJsonObject().get(clave);
        } else {
            jsonElement = JsonParser.parseString(resp.getBody());
        }
    
        Type type;
        if (esLista) {
            type = TypeToken.getParameterized(List.class, clase).getType();
        } else {
            type = clase;
        }
    
        return gson.fromJson(jsonElement, type);
    }

    private static <T> List<T> mergeListsDistinct(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
