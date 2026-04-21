package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccesoBD;
import model.Usuario;
import model.Voluntario;

@WebServlet("/ServAdmin")
public class ServAdmin extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		String accion = request.getParameter("accion");
        // Capturamos el parámetro como texto primero
        String idParam = request.getParameter("idUsuario");

        AccesoBD bd;
        try {
            bd = new AccesoBD();

            switch (accion) {

                case "verDatos":
                    // Solo parseamos aquí porque verDatos SI necesita el ID
                    if (idParam != null) {
                        int idUsuario = Integer.parseInt(idParam);
                        Voluntario volDetalle = bd.obtenerVoluntarioPorId(idUsuario);
                        if (volDetalle != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("usuarioDetalle", volDetalle);
                            response.sendRedirect("admin?opcion=voluntarios"); 
                        } else {
                            response.sendRedirect("admin?opcion=voluntarios&error=noEncontrado");
                        }
                    }
                    break;

                case "limpiarDetalle":
                    // Esta acción ahora no falla porque no intenta parsear ningún ID
                    request.getSession().removeAttribute("usuarioDetalle");
                    response.sendRedirect("admin?opcion=voluntarios");
                    break;

                case "eliminarCuenta":
                    String motivo = request.getParameter("motivo");
                    if (idParam == null || motivo == null || motivo.trim().isEmpty()) {
                        response.sendRedirect("admin?opcion=voluntarios&error=faltaMotivo");
                        break;
                    }
                    
                    // Parseamos aquí el ID para la eliminación
                    int idEliminar = Integer.parseInt(idParam);
                    Voluntario vol = bd.obtenerVoluntarioPorId(idEliminar);
                    if (vol != null) {
                        bd.borrarVoluntario(idEliminar);
                        String asunto = "Baja de Voluntariado Verde";
                        String cuerpo = "Hola " + vol.getNombre() + ",\n\n"
                                + "Te informamos de que el administrador ha decidido eliminar tu cuenta por el siguiente motivo:\n"
                                + "\"" + motivo + "\"\n\n"
                                + "En caso de que no estés de acuerdo, respóndenos a este correo y te ayudaremos.\n\n"
                                + "Saludos,\nEl equipo de Voluntariado Verde.";
                        
                        control.Mailer.send(vol.getEmail(), asunto, cuerpo);
                    }
                    response.sendRedirect("admin?opcion=voluntarios");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    // Redirigimos todas las peticiones GET al doPost para que no de error
	    doPost(request, response);
	}
}