package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServInscripcion")
public class ServInscripcion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServInscripcion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || !(session.getAttribute("usuario") instanceof Voluntario)) {

			session.setAttribute("message", "Inicia sesión como voluntario para inscribirte a eventos.");
			session.setAttribute("messageType", "info");

			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Usuario user = (Usuario) session.getAttribute("usuario");
		int idVoluntario = user.getId();
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		String accion = request.getParameter("accion");

		try {
			AccesoBD bd = new AccesoBD();

			if ("cancelar".equals(accion)) {

				bd.cancelarInscripcion(idVoluntario, idEvento);
			} else {

				bd.inscribir(idVoluntario, idEvento);
			}

			bd.disconnect();

			response.sendRedirect(request.getContextPath() + "/evento?id=" + idEvento);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

}
