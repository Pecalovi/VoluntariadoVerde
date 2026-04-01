package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Evento;
import model.Usuario;

@WebServlet("/evento")
public class EventoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Obtener los datos necesarios para reenviarselos a la view
		// *Controlador contacta con el modelo y reenvia a la vista
		String contextPath = request.getContextPath();
		int id = 0;

		String idStr = request.getParameter("id");

		if (idStr != null) {
			try {
				id = Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendRedirect(contextPath + "/home");
				return;
			}
		} else {
			response.sendRedirect(contextPath + "/home");
			return;
		}

		Evento e = AccesoBD.obtenerEvento(id);

		if (e == null) {
			response.sendRedirect(contextPath + "/home");
			return;
		}

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		boolean inscrito = false;

		if (user != null) {
		    int idUsuario = user.getId();
		    try {
		        AccesoBD bd = new AccesoBD();
		        inscrito = bd.usuarioInscrito(idUsuario, id);
		    } catch (ClassNotFoundException | SQLException e1) {
		        e1.printStackTrace();
		    }
		}
		request.setAttribute("inscrito", inscrito);
		
		request.setAttribute("evento", e);
		
		String lang = (String) session.getAttribute("lang");
		
		if (lang == null) {
		    lang = "es";
		    session.setAttribute("lang", lang);
		}
		
		//2. Cargar la vista
		if (lang.equals("es")) {
			request.setAttribute("view", "WEB-INF/sections/Evento.jsp");
		} else {
			request.setAttribute("view", "WEB-INF/sections/EN/Evento.jsp");
		}
		request.setAttribute("estilo", "estilos/Evento.css");
		;

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
