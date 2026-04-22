package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.Mailer;
import model.AccesoBD;
import model.Evento;
import model.Organizador;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServPerfil")
public class ServPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// doGet: Úsalo solo para acciones que NO cambian datos masivos o para borrar
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");
		String accion = request.getParameter("accion");

		if (user == null || accion == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}

		AccesoBD bd = null;
		try {
			bd = new AccesoBD();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			switch (accion) {
			case "eliminar-cuenta":
				bd.borrarVoluntario(user.getId());
				session.invalidate();

				session.setAttribute("message", "Cuenta eliminada correctamente.");
				session.setAttribute("messageType", "success");

				response.sendRedirect(request.getContextPath() + "/home");
				break;

			case "gestionar-voluntarios":
				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				int idEvento = Integer.parseInt(request.getParameter("idEvento"));
				String accionVol = request.getParameter("accionVoluntario");
				String estado = "aceptar".equals(accionVol) ? "Aceptado" : "Rechazado";

				bd.cambiarEstadoInscripcion(idUsuario, idEvento, estado);
				response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento);
				break;

			default:
				response.sendRedirect(request.getContextPath() + "/perfil");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		try {

			AccesoBD bd = new AccesoBD();

			if ("gestionar-voluntarios".equals(accion)) {

				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				int idEvento = Integer.parseInt(request.getParameter("idEvento"));
				String accionVol = request.getParameter("accionVoluntario");

				String estado;

				if ("aceptar".equals(accionVol)) {
					estado = "Aceptado";
					Voluntario voluntario = bd.obtenerVoluntarioPorId(idUsuario);
					ArrayList<Evento> eventos = AccesoBD.obtenerEventos("id_evento", idEvento);
					String nombreEvento = (!eventos.isEmpty()) ? eventos.get(0).getNombre() : "el evento";

					String asuntoConfirmacion = "¡Tu inscripción como voluntario ha sido aceptada!";
					String cuerpoConfirmacion = "Hola " + voluntario.getNombre() + ",\n\n"
							+ "Nos complace informarte de que tu solicitud para participar "
							+ "como voluntario en el evento \"" + nombreEvento + "\" ha sido ACEPTADA.\n\n"
							+ "Ya formas parte del equipo. Pronto recibirás más detalles " + "sobre el evento.\n\n"
							+ "¡Muchas gracias por tu compromiso!\n" + "Saludos, el equipo de Voluntariado Verde.";

					Mailer.send(voluntario.getEmail(), asuntoConfirmacion, cuerpoConfirmacion);
				} else if ("rechazar".equals(accionVol)) {
					estado = "Rechazado";
				} else {
					estado = "Pendiente";
				}

				bd.cambiarEstadoInscripcion(idUsuario, idEvento, estado);

				response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento
						+ "&accion=gestionar-voluntarios");
				return;
			}

			if ("eliminar-cuenta".equals(accion) && user != null) {

				String passConfirm = request.getParameter("passConfirm");
				String passHash = Usuario.sha256(passConfirm != null ? passConfirm : "");

				if (!passHash.equals(user.getPass())) {
					session.setAttribute("errorEliminar", "Contraseña incorrecta. No se ha eliminado la cuenta.");
					response.sendRedirect(request.getContextPath() + "/perfil?opcion=eliminar-cuenta");
					return;
				}

				if (user instanceof Voluntario) {
					bd.borrarVoluntario(user.getId());
				} else if (user instanceof Organizador) {
					bd.borrarOrganizador(user.getId());
				}

				session.invalidate();
				HttpSession newSession = request.getSession(true);
				newSession.setAttribute("message", "Cuenta eliminada correctamente.");
				newSession.setAttribute("messageType", "success");
				response.sendRedirect(request.getContextPath() + "/home");
				return;
			}

			if ("editar-cuenta".equals(accion) && user != null) {

				user.setNombre(Usuario.capitalizarTexto(request.getParameter("fname")));
				user.setApellidos(Usuario.capitalizarTexto(request.getParameter("fsurname")));
				user.setEmail(request.getParameter("femail"));
				user.setNumTelf(request.getParameter("fphone"));

				if (user instanceof Voluntario) {

					Voluntario vol = (Voluntario) user;

					vol.setVehiculo(request.getParameter("fvehiculo") != null);

					String discStr = request.getParameter("fdisc");
					if (discStr != null && !discStr.isEmpty()) {
						vol.setDiscapacidad(Integer.parseInt(discStr));
					}

					String fecha = request.getParameter("fedad");
					if (fecha != null && !fecha.isEmpty()) {
						vol.setFechaNac(java.time.LocalDate.parse(fecha));
					}

				} else if (user instanceof Organizador) {

					Organizador org = (Organizador) user;
					org.setEntidad(Usuario.capitalizarTexto(request.getParameter("fenterprise")));
				}

				if (bd.editarDatosUsuario(user)) {
					session.setAttribute("usuario", user);
					session.setAttribute("message", "Perfil actualizado correctamente.");
					session.setAttribute("messageType", "success");
				} else {

					session.setAttribute("message", "No se han podido editar los datos.");
					session.setAttribute("messageType", "danger");

					response.sendRedirect(request.getContextPath() + "/login");
				}

				response.sendRedirect(request.getContextPath() + "/perfil");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/perfil");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}
}