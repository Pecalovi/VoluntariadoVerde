package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/trabajanosotros")
public class TrabajaNosotrosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String lang = (String) session.getAttribute("lang");
		
		if (lang == null) {
		    lang = "es";
		    session.setAttribute("lang", lang);
		}
		//2. Cargar la vista
		if (lang.equals("es")) {
			request.setAttribute("view", "WEB-INF/sections/TrabajaNosotros.jsp");
		} else {
			request.setAttribute("view", "WEB-INF/sections/EN/TrabajaNosotros.jsp");
		}
		request.setAttribute("estilo", "estilos/Contacto.css");;

		//Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
		
	}

}
