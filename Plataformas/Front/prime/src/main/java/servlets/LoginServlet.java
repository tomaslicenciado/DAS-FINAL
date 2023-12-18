package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import beans.Codigo;
import beans.RespuestaBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login_id = request.getParameter("id");
            request.setAttribute("id", login_id);

            this.gotoPage("/login_page.jsp", request, response);

        } catch (WebServiceException e) {
	        response.setStatus(400);
			request.setAttribute("error", e.getMessage());
			this.gotoPage("/error.jsp", request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String login_id = request.getParameter("id");
            request.setAttribute("id", login_id);

            Properties properties = new Properties();
            properties.load(input);
            String url_conexion = properties.getProperty("url_backend");

            URI uri = URI.create(url_conexion + "/login");
            HttpPost req = new HttpPost();
            req.setURI(uri);

            Map<String, String> params = new LinkedHashMap<>();
            params.put("id_login", login_id);
            params.put("email", email);
            params.put("password", password);

            StringEntity stringEntity = new StringEntity(new Gson().toJson(params), "UTF-8");
            req.setHeader("Accept", "application/json");
            req.setHeader("Content-type", "application/json;charset=UTF-8");
            req.setEntity(stringEntity);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse resp = client.execute(req);
            HttpEntity responsEntity = resp.getEntity();
            StatusLine responseStatus = resp.getStatusLine();

            RespuestaBean respuesta = new Gson().fromJson(EntityUtils.toString(responsEntity), new TypeToken<RespuestaBean>(){}.getType());

            if (responseStatus.getStatusCode() != HttpStatus.SC_OK){
                response.setStatus(400);
                request.setAttribute("error", respuesta.getMensaje());
                request.setAttribute("mensaje", respuesta.getBody());
                gotoPage("/error.jsp", request, response);
            }
            
            if (respuesta.getStatus() != Codigo.OK){
                request.setAttribute("error", respuesta.getMensaje());
                request.setAttribute("mensaje", respuesta.getBody());
                gotoPage("/error.jsp", request, response);
            }
            else{
                String url_retorno = JsonParser.parseString(respuesta.getBody()).getAsJsonObject().get("url_retorno").getAsString();
                request.setAttribute("url_retorno", url_retorno);
                gotoPage("/confirmacion.jsp", request, response);
            }

        } catch (WebServiceException e) {
	        response.setStatus(400);
			request.setAttribute("error", e.getMessage());
			this.gotoPage("/error.jsp", request, response);
        } catch (NumberFormatException ne){
            response.setStatus(500);
			request.setAttribute("error", ne.getMessage());
			this.gotoPage("/error.jsp", request, response);
        }
    }

    private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(address);
		                  dispatcher.forward(request, response);
	}
}
