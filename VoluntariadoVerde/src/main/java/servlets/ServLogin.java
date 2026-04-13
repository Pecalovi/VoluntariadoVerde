package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Organizador;
import model.Usuario;

/**
 * Servlet implementation class ServLogin
 */
@WebServlet("/ServLogin")
public class ServLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServLogin() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("fuser");
		String emailnormalizado = email.trim().toLowerCase();
		String pass = request.getParameter("fcontra");

		String passCifrada = Usuario.sha256(pass);
		String contextPath = request.getContextPath();

		// Validar campos vacíos
		if (emailnormalizado == null || pass == null || emailnormalizado.isEmpty() || pass.isEmpty()) {
			request.setAttribute("info", "Por favor, completa todos los campos.");
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		Usuario user = AccesoBD.iniciarSesion(emailnormalizado, passCifrada);
		// Resultado
		if (user != null) {

			// Iniciar sesión
			HttpSession session = request.getSession();
			session.setAttribute("usuario", user);

			// Redirigir al home
			
			boolean esOrganizador = (user != null && user instanceof Organizador);
			
			if (esOrganizador) {
				response.sendRedirect(contextPath + "/perfil?opcion=gestionar-eventos");
			}else {
				response.sendRedirect(contextPath + "/home");
			}
		} else {
			// Si no se encuentra el usuario
			request.setAttribute("error", "El email o la contraseña no son correctos.");
			request.getRequestDispatcher("/login").forward(request, response);
		}
	}
}
