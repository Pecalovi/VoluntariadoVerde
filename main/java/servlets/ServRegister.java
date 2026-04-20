package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.Mailer;

import java.sql.SQLException;
import java.time.LocalDate;

import model.AccesoBD;
import model.Organizador;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServRegister")
public class ServRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextPath = request.getContextPath();

		String tipo = request.getParameter("tipo");
		String nombre = Usuario.capitalizarTexto(request.getParameter("fname"));
		String apellido = Usuario.capitalizarTexto(request.getParameter("fsurname"));
		String apellido2 = Usuario.capitalizarTexto(request.getParameter("fsurname2"));
		String fechaString = request.getParameter("fedad");
		String tlf = request.getParameter("fphone");
		String email = request.getParameter("femail");
		String pass = request.getParameter("fpwd");

		String empresa = Usuario.capitalizarTexto(request.getParameter("fenterprise"));

		LocalDate fechaNac = null;
		if (fechaString != null && !fechaString.isEmpty()) {
			fechaNac = LocalDate.parse(fechaString);
		}

		boolean vehiculo = request.getParameter("fvehiculo") != null;

		int discapacidad = 0;
		String discStr = request.getParameter("fdisc");
		if (discStr != null && !discStr.isEmpty()) {
			discapacidad = Integer.parseInt(discStr);
		}

		String apellidos = apellido + " " + apellido2;

		String passCifrada = Usuario.sha256(pass);

		try {

			AccesoBD bd = new AccesoBD();
			Usuario u = null;

			if ("voluntario".equals(tipo)) {
				u = new Voluntario(nombre, apellidos, 0, tlf, email, passCifrada, discapacidad, vehiculo, fechaNac);
			} else if ("organizador".equals(tipo)) {
				u = new Organizador(nombre, apellidos, 0, tlf, email, passCifrada, empresa);
			}

			if (bd.registrar(u)) {

				String asunto = "¡Bienvenido a Voluntariado Verde!";
				String cuerpo = "Hola " + nombre + ",\n\n" + "¡Gracias por registrarte!\n"
						+ "Tu cuenta ha sido creada correctamente. Ahora formas parte de nuestra "
						+ "comunidad para mejorar el medio ambiente.\n\n"
						+ "Saludos,\nEl equipo de Voluntariado Verde.";

				Mailer.send(email, asunto, cuerpo);

				HttpSession session = request.getSession();
				session.setAttribute("message", "Registro completado correctamente. ¡Bienvenido!");
				session.setAttribute("messageType", "success");

				response.sendRedirect(contextPath + "/login");
				return;
			} else {
				HttpSession session = request.getSession();

				session.setAttribute("message", "Error al crear la cuenta.");
				session.setAttribute("messageType", "danger");

				// Volver al formulario correcto
				if ("voluntario".equals(tipo)) {
					request.getRequestDispatcher("/registrovoluntario").forward(request, response);
				} else if ("organizador".equals(tipo)) {
					request.getRequestDispatcher("/registroorg").forward(request, response);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("message", "Error interno, inténtalo más tarde.");
			session.setAttribute("messageType", "danger");

			if ("voluntario".equals(tipo)) {
				response.sendRedirect(contextPath + "/registrovoluntario");
			} else if ("organizador".equals(tipo)) {
				response.sendRedirect(contextPath + "/registroorg");
			}
		}

	}

}
