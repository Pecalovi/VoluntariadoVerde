package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/ServAdmin")
public class ServAdmin extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		String accion = request.getParameter("accion");
        String idParam = request.getParameter("idUsuario");

        AccesoBD bd;
        try {
            bd = new AccesoBD();

            switch (accion) {
            default:
            
                case "verDatos":
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
                    request.getSession().removeAttribute("usuarioDetalle");
                    response.sendRedirect("admin?opcion=voluntarios");
                    break;

                case "eliminarCuenta":
                    String motivo = request.getParameter("motivo");
                    if (idParam == null || motivo == null || motivo.trim().isEmpty()) {
                        response.sendRedirect("admin?opcion=voluntarios&error=faltaMotivo");
                        break;
                    }
                    
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
                    HttpSession session = request.getSession();

        			session.setAttribute("message", "Voluntario eliminado correctamente.");
        			session.setAttribute("messageType", "success");
                    
                    response.sendRedirect("admin?opcion=voluntarios");
                    break;
                    
                    
                case "eliminarOrganizacion":
                    String motivoOrg = request.getParameter("motivo");
                    String nombreEmpresa = request.getParameter("idOrganizador");
                    
                    System.out.println("=== eliminarOrganizacion ===");
                    System.out.println("nombreEmpresa: " + nombreEmpresa);
                    System.out.println("motivo: " + motivoOrg);

                    if (nombreEmpresa == null || motivoOrg == null || motivoOrg.trim().isEmpty()) {
                        response.sendRedirect("admin?opcion=empresas&error=faltaMotivo");
                        break;
                    }

                    List<Organizador> orgs = bd.obtenerOrganizadoresPorEmpresa(nombreEmpresa);
                    bd.borrarOrganizadoresPorEmpresa(nombreEmpresa);

                    for (Organizador org : orgs) {
                        String asunto = "Baja de Voluntariado Verde";
                        String cuerpo = "Hola " + org.getNombre() + ",\n\n"
                                + "Te informamos de que el administrador ha decidido eliminar la organización \""
                                + nombreEmpresa + "\" por el siguiente motivo:\n"
                                + "\"" + motivoOrg + "\"\n\n"
                                + "En caso de que no estés de acuerdo, respóndenos a este correo.\n\n"
                                + "Saludos,\nEl equipo de Voluntariado Verde.";
                        control.Mailer.send(org.getEmail(), asunto, cuerpo);
                    }

                    HttpSession sessionOrg = request.getSession();
                    sessionOrg.setAttribute("message", "Organización eliminada correctamente.");
                    sessionOrg.setAttribute("messageType", "success");
                    response.sendRedirect("admin?opcion=empresas");
                    break;
                case "verDatosEmpresa":
                    String empresa = request.getParameter("empresa");
                    if (empresa != null) {
                        List<Organizador> orgDetalle = bd.obtenerOrganizadoresPorEmpresa(empresa);
                        HttpSession session1 = request.getSession();
                        session1.setAttribute("empresaDetalle", orgDetalle);
                        session1.setAttribute("empresaNombre", empresa);
                        response.sendRedirect("admin?opcion=empresas");
                    }
                    break;

                case "limpiarDetalleEmpresa":
                    request.getSession().removeAttribute("empresaDetalle");
                    request.getSession().removeAttribute("empresaNombre");
                    response.sendRedirect("admin?opcion=empresas");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    doPost(request, response);
	}
}