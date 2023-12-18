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

@WebServlet("/registrarse")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login_id = request.getParameter("id");
            request.setAttribute("id", login_id);

            this.gotoPage("/register_page.jsp", request, response);

        } catch (WebServiceException e) {
	        response.setStatus(400);
			request.setAttribute("error", e.getMessage());
			this.gotoPage("/error.jsp", request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login_id = request.getParameter("id");
            request.setAttribute("id", login_id);
            String email = request.getParameter("r-email");
            String password = request.getParameter("r-password");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");

            HBOWSService service = new HBOWSService();
            HBOWS client = service.getHBOWSPort();

            RespuestaBean resp = client.register(Integer.parseInt(login_id), nombres, apellidos, email, password);

            if (resp.getStatus() != Codigo.OK){
                request.setAttribute("error", resp.getBody());
                request.setAttribute("mensaje", resp.getMensaje());
                gotoPage("/error.jsp", request, response);
            }
            else{
                String url_retorno = JsonParser.parseString(resp.getBody()).getAsJsonObject().get("url_retorno").getAsString();
                request.setAttribute("url_retorno", url_retorno);
                gotoPage("/confirmacion.jsp", request, response);
            }

        } catch (WebServiceException e) {
	        response.setStatus(400);
			request.setAttribute("error", e.getMessage());
			this.gotoPage("/error.jsp", request, response);
        }
    }

    private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(address);
		                  dispatcher.forward(request, response);
	}
}
