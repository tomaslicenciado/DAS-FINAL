package servlets;

import java.io.IOException;

import com.google.gson.JsonParser;

import ar.edu.ubp.das.hbo.Codigo;
import ar.edu.ubp.das.hbo.HBOWS;
import ar.edu.ubp.das.hbo.HBOWSService;
import ar.edu.ubp.das.hbo.RespuestaBean;
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
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String login_id = request.getParameter("id");
            request.setAttribute("id", login_id);
            
            HBOWSService service = new HBOWSService();
            HBOWS client = service.getHBOWSPort();

            RespuestaBean respuesta = client.login(Integer.parseInt(login_id), email, password);
            if (respuesta.getStatus() != Codigo.OK){
                request.setAttribute("error", respuesta.getBody());
                request.setAttribute("mensaje", respuesta.getMensaje());
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
