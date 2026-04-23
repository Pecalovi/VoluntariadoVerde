package control;

import java.io.IOException;
import java.util.ArrayList;
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

			List<Evento> eventos = AccesoBD.obtenerEventos("id_organizador", user.getId(), true);

			List<Evento> enProceso = new java.util.ArrayList<>();
			List<Evento> finalizado = new java.util.ArrayList<>();

			for (Evento e : eventos) {

				if (e.getEstado() == null) {
					continue;
				}

				if ("Finalizado".equals(e.getEstado())) {
					finalizado.add(e);
				} else {
					enProceso.add(e);
				}
			}

			request.setAttribute("enProceso", enProceso);
			request.setAttribute("finalizados", finalizado);

			break;

		case "gestionar-evento":
			perfilView = "GestionarEvento.jsp";
			opcion = "gestionar";

			String idEvento = request.getParameter("id");

			List<Evento> eventosId = AccesoBD.obtenerEventos("id_evento", idEvento, true);
			Evento evento1 = eventosId.isEmpty() ? null : eventosId.get(0);

			request.setAttribute("evento", evento1);

			if (idEvento != null) {

				List<Inscripcion> voluntarios = AccesoBD.obtenerVoluntarios(Integer.parseInt(idEvento));

				List<Inscripcion> pendientes = new java.util.ArrayList<>();
				List<Inscripcion> aceptados = new java.util.ArrayList<>();
				List<Inscripcion> asignados = new java.util.ArrayList<>();
				List<Inscripcion> espera = new java.util.ArrayList<>();
				List<Inscripcion> inactivos = new java.util.ArrayList<>();

				for (Inscripcion i : voluntarios) {

					String estado = i.getEstado();

					if (estado == null)
						continue;

					switch (estado) {

					case "Pendiente":
						pendientes.add(i);
						break;

					case "Aceptado":
						aceptados.add(i);
						break;

					case "Asignado":
						asignados.add(i);
						break;

					case "Lista de espera":
						espera.add(i);
						break;

					case "Rechazado":
					case "Cancelado":
						inactivos.add(i);
						break;
					}
				}

				List<String> puntosControl = AccesoBD.obtenerPuntosControl(idEvento);

				request.setAttribute("puntosControl", puntosControl);
				request.setAttribute("pendientes", pendientes);
				request.setAttribute("aceptados", aceptados);
				request.setAttribute("asignados", asignados);
				request.setAttribute("espera", espera);
				request.setAttribute("inactivos", inactivos);
			}

			break;
		case "valorar-voluntarios":
			perfilView = "ValorarVoluntarios.jsp";
			opcion = "gestionar";
			String idEvento1 = request.getParameter("id");

			List<Evento> eventosId1 = AccesoBD.obtenerEventos("id_evento", idEvento1, true);
			Evento eventoFinalizado = eventosId1.isEmpty() ? null : eventosId1.get(0);

			List<Inscripcion> voluntarios = AccesoBD.obtenerVoluntarios(Integer.parseInt(idEvento1));
			List<Inscripcion> asignados = new ArrayList<>();
			List<Inscripcion> valorados = new ArrayList<>();

			for (Inscripcion i : voluntarios) {

				String estado = i.getEstado();
				Integer valoracion = i.getValoracion();

				if ("Asignado".equals(estado)) {

					if (valoracion != null) {
						valorados.add(i);
					} else {
						asignados.add(i);
					}
				}
			}
			request.setAttribute("asignados", asignados);
			request.setAttribute("valorados", valorados);
			request.setAttribute("evento", eventoFinalizado);
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