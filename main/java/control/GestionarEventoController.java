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

@WebServlet("/gestionarevento")
public class GestionarEventoController extends HttpServlet {
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

		String accion = request.getParameter("accion");
		if (accion == null || accion.isEmpty()) {
			accion = "perfil";
		}

		String seccion = null;
		switch (accion) {
		case "editar-evento":
			seccion = "EditarEvento.jsp";
			accion = "editar";
			break;
		case "gestionar-voluntarios":
			seccion = "GestionarVoluntarios.jsp";
			accion = "voluntarios";
			break;
		default:
			seccion = "GestionarVoluntarios.jsp";
			accion = "editar";
			break;
		}

	// 2. Cargar la vista

	request.setAttribute("view","perfil/Perfil.jsp");

	request.setAttribute("eventoView",seccion);request.setAttribute("accion",accion);

	request.setAttribute("estilo","estilos/Perfil.css");

	// Encadenar la petición y cargar otro recurso
	request.getRequestDispatcher("/index.jsp").forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
