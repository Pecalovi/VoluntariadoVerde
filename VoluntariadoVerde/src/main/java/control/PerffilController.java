package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String lang = (String) session.getAttribute("lang");

		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		String opcion = request.getParameter("opcion");
		if (opcion == null || opcion.isEmpty()) {
			opcion = "perfil";
		}

		String seccion;
		switch (opcion) {
		case "mis-eventos":
			seccion = "MisEventos.jsp";
			break;
		case "gestionar-eventos":
			seccion = "GestionarEventos.jsp";
			break;
		case "eliminar-cuenta":
			seccion = "EliminarCuenta.jsp";
			break;
		default:
			seccion = "EditarPerfil.jsp";
			opcion = "perfil";
			break;
		}
		// 2. Cargar la vista

		request.setAttribute("view", "perfil/Perfil.jsp");

		request.setAttribute("perfilView", seccion);

		request.setAttribute("estilo", "estilos/Perfil.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");
		String accion = request.getParameter("accion");

		switch (accion) {
		case "eliminar-cuenta":

			AccesoBD bd;
			try {
				bd = new AccesoBD();
				bd.borrarDatosUsuario(user.getId());

				// Cerrar sesión
				session.invalidate();

				// Mandar mensaje de info al home
				response.sendRedirect(request.getContextPath() + "/home?parametro=2");
				
				return;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("error", "No se pudo eliminar la cuenta.");
			}

			// ir al home con un success de eliminar cuenta
			break;
		}

		doGet(request, response);

	}

}
