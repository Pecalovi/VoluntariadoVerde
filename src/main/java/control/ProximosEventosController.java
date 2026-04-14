package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Evento;

@WebServlet("/eventos")
public class ProximosEventosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String lang = (String) session.getAttribute("lang");
		
		if (lang == null) {
		    lang = "es";
		    session.setAttribute("lang", lang);
		}

		ArrayList<Evento> eventos = AccesoBD.obtenerEventos("","");

		// 2. Cargar la vista
		request.setAttribute("eventos", eventos);
		request.setAttribute("view", "evento/ProximosEventos.jsp");
		request.setAttribute("estilo", "estilos/ProximosEventos.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String nombre = request.getParameter("nombre");
	    String tipo = request.getParameter("tipo");
	    String ciudad = request.getParameter("ciudad");

	    String atributo = "";
	    String valor = "";

	    if (nombre != null && !nombre.isEmpty()) {
	        atributo = "nombre";
	        valor = nombre;
	    } else if (tipo != null && !tipo.isEmpty()) {
	        atributo = "tipo";
	        valor = tipo;
	    } else if (ciudad != null && !ciudad.isEmpty()) {
	        atributo = "lugar";
	        valor = ciudad;
	    }

	    ArrayList<Evento> eventos = AccesoBD.obtenerEventos(atributo, valor);

	    request.setAttribute("eventos", eventos);
	    request.setAttribute("view", "evento/ProximosEventos.jsp");
	    request.setAttribute("estilo", "estilos/ProximosEventos.css");

	    request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
