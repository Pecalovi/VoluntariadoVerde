package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String fechaString = request.getParameter("fedad");
		String tlf = request.getParameter("fphone");
		String email = request.getParameter("femail");
		String pass = request.getParameter("fpwd");

		String empresa = Usuario.capitalizarTexto(request.getParameter("fenterprise"));

		// ✔ FECHA
		LocalDate fechaNac = null;
		if (fechaString != null && !fechaString.isEmpty()) {
			fechaNac = LocalDate.parse(fechaString);
		}

		// ✔ VEHÍCULO (checkbox o radio)
		boolean vehiculo = request.getParameter("fvehiculo") != null;

		// ✔ DISCAPACIDAD (seguro)
		int discapacidad = 0;
		String discStr = request.getParameter("fdisc");
		if (discStr != null && !discStr.isEmpty()) {
			discapacidad = Integer.parseInt(discStr);
		}

		String passCifrada = Usuario.sha256(pass);

		try {

			AccesoBD bd = new AccesoBD();
			Usuario u = null;

			if ("voluntario".equals(tipo)) {
				u = new Voluntario(nombre, apellido, 0, tlf, email, passCifrada, discapacidad, vehiculo, fechaNac);
			} else if ("organizador".equals(tipo)) {
				u = new Organizador(nombre, apellido, 0, tlf, email, passCifrada, empresa);
			}

			if (bd.registrar(u)) {

				String asunto = "¡Bienvenido a Voluntariado Verde!";
				String cuerpo = "Hola " + nombre + ",\n\n" + "¡Gracias por registrarte como voluntario!\n"
						+ "Tu cuenta ha sido creada correctamente. Ahora formas parte de nuestra "
						+ "comunidad para mejorar el medio ambiente.\n\n"
						+ "¡Esperamos que puedas apuntarte a algún evento pronto!\n\n"
						+ "Saludos,\nEl equipo de Voluntariado Verde.";

				Mailer.send(email, asunto, cuerpo);
				response.sendRedirect(contextPath + "/login");
				return;
			} else {
				request.setAttribute("error", "Error al crear la cuenta.");
				// Volver al formulario correcto
				if ("voluntario".equals(tipo)) {
					request.getRequestDispatcher("/registrovoluntario").forward(request, response);
				} else if ("organizador".equals(tipo)) {
					request.getRequestDispatcher("/registroorg").forward(request, response);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Error interno, inténtalo más tarde.");

			if ("voluntario".equals(tipo)) {
				request.getRequestDispatcher("/registrovoluntario").forward(request, response);
			} else if ("organizador".equals(tipo)) {
				request.getRequestDispatcher("/registroorg").forward(request, response);
			}
		}

	}

}
