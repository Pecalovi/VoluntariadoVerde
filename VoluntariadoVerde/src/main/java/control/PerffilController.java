package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Evento;

@WebServlet("/perfil")
public class PerffilController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
	    if (session.getAttribute("usuario") == null) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
	    
		String lang = (String) session.getAttribute("lang");
		
		if (lang == null) {
		    lang = "es";
		    session.setAttribute("lang", lang);
		}

		// 2. Cargar la vista
		
		request.setAttribute("view", "WEB-INF/sections/Perfil.jsp");

		request.setAttribute("estilo", "estilos/Perfil.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
