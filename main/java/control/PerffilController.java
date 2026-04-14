package control;

import java.io.IOException;
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

@WebServlet("/perfil")
public class PerffilController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		// idioma
		String lang = (String) session.getAttribute("lang");
		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		// =========================
		// OPCION PRINCIPAL (PERFIL)
		// =========================
		String opcion = request.getParameter("opcion");
		if (opcion == null || opcion.isEmpty()) {
			opcion = "perfil";
		}

		String perfilView;

		switch (opcion) {

		case "mis-eventos":
			perfilView = "MisEventos.jsp";
			opcion = "eventos";

			request.setAttribute("eventos", AccesoBD.obtenerEventosUsuario(user.getId()));
			break;

		case "gestionar-eventos":
			perfilView = "GestionarEventos.jsp";
			opcion = "gestionar";

			request.setAttribute("eventos", AccesoBD.obtenerEventos("id_organizador", user.getId()));
			break;

		case "gestionar-evento":
			perfilView = "GestionarEvento.jsp";
			opcion = "gestionar";
			String idEvento = request.getParameter("id");
			List<Evento> eventosId = AccesoBD.obtenerEventos("id_evento", idEvento);
			Evento evento1 = eventosId.isEmpty() ? null : eventosId.get(0);
			request.setAttribute("evento", evento1);
			break;

		case "eliminar-cuenta":
			perfilView = "EliminarCuenta.jsp";
			opcion = "eliminar";
			break;

		default:
			perfilView = "EditarPerfil.jsp";
			opcion = "perfil";
			break;
		}

		// =========================
		// ACCION (SUBVISTA EVENTO)
		// =========================
		String accion = request.getParameter("accion");

		if (accion == null || accion.isEmpty()) {
			accion = "ver";
		}

		String eventoView = "GestionarVoluntarios.jsp";

			switch (accion) {

			case "editar-evento":
				eventoView = "EditarEvento.jsp";
				accion = "editar";
				break;

			case "gestionar-voluntarios":
				eventoView = "GestionarVoluntarios.jsp";
				accion = "voluntarios";
				break;

			default:
				eventoView = "GestionarVoluntarios.jsp";
				accion = "ver";
				break;
			}

		// =========================
		// VIEW GLOBAL
		// =========================
		request.setAttribute("view", "perfil/Perfil.jsp");
		request.setAttribute("perfilView", perfilView);
		request.setAttribute("eventoView", eventoView);
		request.setAttribute("opcion", opcion);
		request.setAttribute("accion", accion);
		request.setAttribute("estilo", "estilos/Perfil.css");

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}