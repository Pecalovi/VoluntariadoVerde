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
				request.setAttribute("error", "No se pudo eliminar la cuenta.");
			}

			// ir al home con un success de eliminar cuenta
			break;
		case "editar-cuenta":

		    if (user == null) {
		        response.sendRedirect(request.getContextPath() + "/login");
		        return;
		    }

		    String nombre = request.getParameter("fname");
		    String apellido = request.getParameter("fsurname");
		    String fechaString = request.getParameter("fedad");
		    String email = request.getParameter("femail");
		    String vehiculo = request.getParameter("fvehiculo");
		    String discapacidad = request.getParameter("fdisc");
		    String empresa = request.getParameter("fenterprise");

		    int tlf = 0;
		    String tlfStr = request.getParameter("fphone");
		    if (tlfStr != null && !tlfStr.isBlank()) {
		        tlf = Integer.parseInt(tlfStr);
		    }

		    LocalDate fechaNac = null;
		    if (fechaString != null && !fechaString.isEmpty()) {
		        try {
		            fechaNac = LocalDate.parse(fechaString);
		        } catch (Exception e) {
		            fechaNac = null;
		        }
		    }

		    user.setNombre(nombre);
		    user.setApellidos(apellido);
		    user.setEmail(email);
		    user.setNumTelf(tlf);

		    if (user instanceof Voluntario v) {
		        v.setFechaNac(fechaNac);
		        v.setVehiculo(vehiculo);
		        v.setDiscapacidad(discapacidad);
		    }

		    if (user instanceof Organizador o) {
		        o.setEntidad(empresa);
		    }

		    try {
		        bd = new AccesoBD();

		        if (bd.editarDatosUsuario(user)) {
		            session.setAttribute("usuario", user);
		            response.sendRedirect(request.getContextPath() + "/perfil");
		        } else {
		            request.setAttribute("error", "Error al editar los datos.");
		            request.getRequestDispatcher("/index.jsp").forward(request, response);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        request.setAttribute("error", "Error al editar los datos.");
		        request.getRequestDispatcher("/index.jsp").forward(request, response);
		    }

		    break;

		}

	}

}
