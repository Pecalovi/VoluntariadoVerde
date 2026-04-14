package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Evento;
import model.Organizador;
import model.Usuario;
import model.Voluntario;

@WebServlet("/perfil")
public class PerffilController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		String seccion;
		switch (opcion) {
		case "mis-eventos":
			seccion = "MisEventos.jsp";
			opcion = "eventos";
			try {
				AccesoBD bd = new AccesoBD();
				List<Evento> eventos = AccesoBD.obtenerEventosUsuario(user.getId());
				request.setAttribute("eventos", eventos);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "gestionar-eventos":
			seccion = "GestionarEventos.jsp";
			opcion = "gestionar";
			break;
		case "eliminar-cuenta":
			seccion = "EliminarCuenta.jsp";
			opcion = "eliminar";
			break;
		default:
			seccion = "EditarPerfil.jsp";
			opcion = "perfil";
			break;
		}
		// 2. Cargar la vista

		request.setAttribute("view", "perfil/Perfil.jsp");

		request.setAttribute("perfilView", seccion);
		request.setAttribute("opcion", opcion);

		request.setAttribute("estilo", "estilos/Perfil.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
