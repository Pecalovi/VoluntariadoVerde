package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Usuario;
import model.Voluntario;

/**
 * Servlet implementation class ServInscripcion
 */
@WebServlet("/ServInscripcion")
public class ServInscripcion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServInscripcion() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
	    if (session == null || !(session.getAttribute("usuario") instanceof Voluntario)) {
	    	// Establecer atributo para mostrar mensaje
	        request.setAttribute("info", "Inicia sesion como voluntario para inscribirte.");

	        // Forward al login
	        request.getRequestDispatcher("/login").forward(request, response);
	        return;
	    }
	    	
	    Usuario user = (Usuario) session.getAttribute("usuario");
	    
	    int idUsuario = user.getId();
	    int idEvento = Integer.parseInt(request.getParameter("idEvento"));
	    	
	    try {
			AccesoBD bd = new AccesoBD();
			if(bd.inscribir(idUsuario, idEvento)) {
				
				request.setAttribute("success", "Inscripción realizada correctamente.");
				request.getRequestDispatcher("/home").forward(request, response);
		        return;
			}else {
				request.setAttribute("error", "Fallo en la inscripción.");
				request.getRequestDispatcher("/evento").forward(request, response);
		        return;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			
			
			e.printStackTrace();
		}
	       
	}

}
