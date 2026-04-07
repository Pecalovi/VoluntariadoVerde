package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Organizador;
import model.Usuario;


@WebServlet("/crearevento")
public class CrearEventoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("usuario") == null) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }

	    Usuario usuario = (Usuario) session.getAttribute("usuario");

	    // Verifica que sea un Organizador (id_rol = 2)
	    if (!(usuario instanceof Organizador)) {
	    	response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }

		String lang = (String) session.getAttribute("lang");
		
		if (lang == null) {
		    lang = "es";
		    session.setAttribute("lang", lang);
		}
		
		//2. Cargar la vista
		if (lang.equals("es")) {
			request.setAttribute("view", "evento/CrearEvento.jsp");
		} else {
			request.setAttribute("view", "EN/CrearEvento.jsp");
		}
	    request.setAttribute("estilo", "estilos/CrearEvento.css");

	    request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
	
}
