package servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Organizador;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServPerfil")
public class ServPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");

		String accion = request.getParameter("accion");

		if (accion == null) {
			response.sendRedirect(request.getContextPath() + "/perfil");
			return;
		}

		try {
			AccesoBD bd;

			switch (accion) {

			// =========================
			// ELIMINAR CUENTA
			// =========================
			case "eliminar-cuenta":

				bd = new AccesoBD();
				bd.borrarDatosUsuario(user.getId());

				session.invalidate();
				response.sendRedirect(request.getContextPath() + "/home?parametro=2");
				return;

			// =========================
			// EDITAR CUENTA
			// =========================
			case "editar-cuenta":

				bd = new AccesoBD();

				user.setNombre(Usuario.capitalizarTexto(request.getParameter("fname")));
				user.setApellidos(Usuario.capitalizarTexto(request.getParameter("fsurname")));
				user.setEmail(request.getParameter("femail"));

				String telefono = request.getParameter("fphone");
				if (telefono != null) {
					user.setNumTelf(telefono.trim());
				}

				// =========================
				// ORGANIZADOR
				// =========================
				if (user instanceof Organizador) {

					Organizador org = (Organizador) user;
					org.setEntidad(Usuario.capitalizarTexto(request.getParameter("fenterprise")));
				}

				// =========================
				// VOLUNTARIO
				// =========================
				if (user instanceof Voluntario) {

					Voluntario vol = (Voluntario) user;

					boolean vehiculo = request.getParameter("fvehiculo") != null;
					vol.setVehiculo(vehiculo);

					int discapacidad = 0;
					String discStr = request.getParameter("fdisc");

					if (discStr != null && !discStr.isEmpty()) {
						discapacidad = Integer.parseInt(discStr);
					}

					vol.setDiscapacidad(discapacidad);

					String fecha = request.getParameter("fedad");

					if (fecha != null && !fecha.isEmpty()) {
						try {
							vol.setFechaNac(LocalDate.parse(fecha));
						} catch (Exception e) {
							session.setAttribute("error", "Fecha inválida");
							response.sendRedirect(request.getContextPath() + "/perfil");
							return;
						}
					}
				}

				bd.editarDatosUsuario(user);

				session.setAttribute("usuario", user);
				session.setAttribute("success", "Se ha editado el perfil.");

				response.sendRedirect(request.getContextPath() + "/perfil");
				return;

			// =========================
			// GESTIONAR INSCRIPCIONES
			// =========================
			case "gestionar-voluntarios":

				bd = new AccesoBD();

				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				int idEvento = Integer.parseInt(request.getParameter("idEvento"));
				String accionVoluntario = request.getParameter("accionVoluntario");

				String estado;

				if ("aceptar".equals(accionVoluntario)) {
					estado = "Aceptado";
				} else if ("rechazar".equals(accionVoluntario)) {
					estado = "Rechazado";
				} else {
					response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento
							+ "&accion=gestionar-voluntarios");
					return;
				}

				bd.cambiarEstadoInscripcion(idUsuario, idEvento, estado);

				response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento
						+ "&accion=gestionar-voluntarios");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", "Error en la operación.");
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
