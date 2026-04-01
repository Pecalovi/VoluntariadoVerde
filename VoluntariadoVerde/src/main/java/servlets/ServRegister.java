package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String nombre = request.getParameter("fname");
		String apellido = request.getParameter("fsurname");
		String fechaString = request.getParameter("fedad");
		int tlf = Integer.parseInt(request.getParameter("fphone"));
		String email = request.getParameter("femail");
		String pass = request.getParameter("fpwd");
		String vehiculo = request.getParameter("fvehiculo");
		String discapacidad = request.getParameter("fdisc");
		String empresa = request.getParameter("fenterprise");
		
		LocalDate fechaNac = null;

		if (fechaString != null && !fechaString.isEmpty()) {
		    fechaNac = LocalDate.parse(fechaString);
		}

		String passCifrada = Usuario.sha256(pass);
		
		try {
			
			AccesoBD bd = new AccesoBD();
			Usuario u = null;
			
			if (tipo.equals("voluntario")) {
				u = new Voluntario(nombre, apellido, 0, tlf, email, passCifrada, discapacidad, vehiculo, fechaNac);
			} else if (tipo.equals("organizador")) {
				u = new Organizador(nombre, apellido, 0, tlf, email, passCifrada, empresa);
			}

			if (bd.registrar(u)) {
	            response.sendRedirect(contextPath + "/login");
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
