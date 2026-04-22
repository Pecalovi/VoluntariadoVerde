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
import model.Inscripcion;
import model.Usuario;

@WebServlet("/perfil")
public class PerffilController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		String lang = (String) session.getAttribute("lang");
		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		String opcion = request.getParameter("opcion");
		if (opcion == null || opcion.isEmpty()) {
			opcion = "perfil";
		}

		String perfilView = null;

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
			String idEventoAccion = request.getParameter("id");

			if (idEventoAccion != null) {

				List<Inscripcion> voluntarios = AccesoBD.obtenerVoluntarios(Integer.parseInt(idEventoAccion));

				List<Inscripcion> pendientes = new java.util.ArrayList<>();
				List<Inscripcion> aceptados = new java.util.ArrayList<>();
				List<Inscripcion> espera = new java.util.ArrayList<>();
				List<Inscripcion> inactivos = new java.util.ArrayList<>();

				for (Inscripcion i : voluntarios) {

					String estado = i.getEstado();

					if ("Pendiente".equals(estado)) {
						pendientes.add(i);

					} else if ("Aceptado".equals(estado)) {
						aceptados.add(i);

					} else if ("Lista de espera".equals(estado)) {
						espera.add(i);

					} else if ("Rechazado".equals(estado) || "Cancelado".equals(estado)) {
						inactivos.add(i);
					}
				}

				request.setAttribute("pendientes", pendientes);
				request.setAttribute("aceptados", aceptados);
				request.setAttribute("espera", espera);
				request.setAttribute("inactivos", inactivos);
			}

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

		request.setAttribute("view", "perfil/Perfil.jsp");
		request.setAttribute("perfilView", perfilView);
		request.setAttribute("opcion", opcion);
		request.setAttribute("estilo", "estilos/Perfil.css");

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}