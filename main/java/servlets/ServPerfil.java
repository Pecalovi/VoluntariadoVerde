package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AccesoBD;
import model.Organizador;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServPerfil")
public class ServPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuario");
		String accion = request.getParameter("accion");
		AccesoBD bd;

		switch (accion) {
		case "eliminar-cuenta":

			try {
				bd = new AccesoBD();
				bd.borrarDatosUsuario(user.getId());

				// Cerrar sesión
				session.invalidate();

				// Mandar mensaje de info al home
				response.sendRedirect(request.getContextPath() + "/home?parametro=2");

				return;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				session.setAttribute("error", "No se pudo eliminar la cuenta.");
			}

			// ir al home con un success de eliminar cuenta
			break;
		case "editar-cuenta":
			try {
		        bd = new AccesoBD();

		        user.setNombre(request.getParameter("fname"));
		        user.setApellidos(request.getParameter("fsurname"));
		        user.setEmail(request.getParameter("femail"));
		        user.setNumTelf(Integer.parseInt(request.getParameter("fphone")));
		        
		        if (user instanceof Organizador) {
		            Organizador org = (Organizador) user;
		            org.setEntidad(request.getParameter("fenterprise"));
		        }
		        if (user instanceof Voluntario) {
		            Voluntario vol = (Voluntario) user;

		            vol.setVehiculo(request.getParameter("fvehiculo"));
		            vol.setDiscapacidad(request.getParameter("fdisc"));

		            String fecha = request.getParameter("fedad");

		            if (fecha != null && !fecha.isEmpty()) {
		                try {
		                    LocalDate fechaLocal = LocalDate.parse(fecha);
		                    vol.setFechaNac(fechaLocal);
		                } catch (Exception e) {
		                    session.setAttribute("error", "Fecha inválida");
		                }
		            }
		        }

		        bd.editarDatosUsuario(user);

		        session.setAttribute("usuario", user);
		        session.setAttribute("success", "Se ha editado el perfil.");

		        response.sendRedirect(request.getContextPath() + "/perfil");
		        return;

		    } catch (ClassNotFoundException | SQLException e) {
		        e.printStackTrace();
		        session.setAttribute("error", "No se ha podido actualizar el perfil.");
		    }
		    break;
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
