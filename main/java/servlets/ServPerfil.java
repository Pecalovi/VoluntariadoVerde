package servlets;

import java.io.IOException;
import java.sql.SQLException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
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

			if ("editar-evento".equals(accion) && user != null) {
				try {
					int idEvento = Integer.parseInt(request.getParameter("id"));
					String nombre = Usuario.capitalizarTexto(request.getParameter("nombre"));

					if (nombre != null && !nombre.trim().isEmpty()) {

						AccesoBD bd2 = new AccesoBD();
						bd2.actualizarNombreEvento(idEvento, nombre.trim());

						session.setAttribute("message", "Evento actualizado correctamente.");
						session.setAttribute("messageType", "success");
					} else {
						session.setAttribute("message", "El nombre no puede estar vacío.");
						session.setAttribute("messageType", "danger");
					}

				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("message", "Error al actualizar el evento.");
					session.setAttribute("messageType", "danger");
				}

				response.sendRedirect(
						request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + request.getParameter("id"));
				return;
			}

			if ("eliminar-evento".equals(accion) && user != null) {
				int idEvento = Integer.parseInt(request.getParameter("idEvento"));
				bd.eliminarEvento(idEvento);
				session.setAttribute("message", "Evento eliminado correctamente.");
				session.setAttribute("messageType", "success");
				response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-eventos");
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

			if ("asignar-voluntarios".equals(accion)) {

				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				int idInscripcion = Integer.parseInt(request.getParameter("idInscripcion"));
				int idEvento = Integer.parseInt(request.getParameter("idEvento"));
				String puntoControl = request.getParameter("puntoControl");

				if (puntoControl != null && !puntoControl.isEmpty()) {

					bd.cambiarEstadoInscripcion(idUsuario, idEvento, "Asignado");
					bd.asignarPuntoControl(idInscripcion, puntoControl);
					
					session.setAttribute("message", "Voluntario asignado correctamente.");
					session.setAttribute("messageType", "success");

				} else {
					session.setAttribute("message", "Debes seleccionar un punto de control.");
					session.setAttribute("messageType", "danger");
				}

				response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento);
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
			HttpSession session2 = request.getSession();
			session2.setAttribute("message", "Error inesperado: " + e.getMessage());
			session2.setAttribute("messageType", "danger");
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}
}