package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServLogout
 */
@WebServlet("/logout")
public class ServLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtener la sesión actual sin crear una nueva
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // Destruye la sesión
		}

		// Redirigir a home con parámetro para SweetAlert
		response.sendRedirect(request.getContextPath() + "/home?parametro=1");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
