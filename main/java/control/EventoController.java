package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

		List<Evento> eventos = AccesoBD.obtenerEventos("id_evento", id, false);

		if (eventos.isEmpty()) {
			response.sendRedirect(contextPath + "/home");
			return;
		}

		Evento e = eventos.get(0);

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		boolean inscrito = false;
		String estadoInscripcion = null;

		if (user != null) {
			int idUsuario = user.getId();
			try {
				AccesoBD bd = new AccesoBD();
				inscrito = bd.usuarioInscrito(idUsuario, id);
				estadoInscripcion = bd.obtenerEstadoInscripcion2(idUsuario, id);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		request.setAttribute("inscrito", inscrito);
		request.setAttribute("estadoInscripcion", estadoInscripcion);
		request.setAttribute("inscritos", AccesoBD.contarInscritos(id));
		request.setAttribute("evento", e);

		String lang = (String) session.getAttribute("lang");

		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		// 2. Cargar la vista
		request.setAttribute("view", "evento/Evento.jsp");
		request.setAttribute("estilo", "estilos/Evento.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
