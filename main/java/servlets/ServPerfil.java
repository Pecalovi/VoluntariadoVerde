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

    // doGet: Úsalo solo para acciones que NO cambian datos masivos o para borrar
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");
        String accion = request.getParameter("accion");

        if (user == null || accion == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        AccesoBD bd = null;
		try {
			bd = new AccesoBD();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
            switch (accion) {
                case "eliminar-cuenta":
                    bd.borrarDatosUsuario(user.getId());
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/home?parametro=2");
                    break;

                case "gestionar-voluntarios":
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    int idEvento = Integer.parseInt(request.getParameter("idEvento"));
                    String accionVol = request.getParameter("accionVoluntario");
                    String estado = "aceptar".equals(accionVol) ? "Aceptado" : "Rechazado";

                    bd.cambiarEstadoInscripcion(idUsuario, idEvento, estado);
                    response.sendRedirect(request.getContextPath() + "/perfil?opcion=gestionar-evento&id=" + idEvento);
                    break;
                
                default:
                    response.sendRedirect(request.getContextPath() + "/perfil");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/perfil?error=1");
        }
    }

    // doPost: AQUÍ es donde debe ir toda la lógica de EDITAR PERFIL
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");

        if ("editar-cuenta".equals(accion) && user != null) {
            try {
                // 1. Datos comunes con capitalización (muy buena idea que tenías en el doGet)
                user.setNombre(Usuario.capitalizarTexto(request.getParameter("fname")));
                user.setApellidos(Usuario.capitalizarTexto(request.getParameter("fsurname")));
                user.setEmail(request.getParameter("femail"));
                user.setNumTelf(request.getParameter("fphone"));

                // 2. Lógica según tipo de usuario
                if (user instanceof Voluntario) {
                    Voluntario vol = (Voluntario) user;
                    
                    // Checkbox y Select
                    vol.setVehiculo(request.getParameter("fvehiculo") != null);
                    
                    String discStr = request.getParameter("fdisc");
                    if (discStr != null) vol.setDiscapacidad(Integer.parseInt(discStr));

                    // Fecha de nacimiento (esto te faltaba en el doPost)
                    String fecha = request.getParameter("fedad");
                    if (fecha != null && !fecha.isEmpty()) {
                        vol.setFechaNac(LocalDate.parse(fecha));
                    }

                } else if (user instanceof Organizador) {
                    Organizador org = (Organizador) user;
                    org.setEntidad(Usuario.capitalizarTexto(request.getParameter("fenterprise")));
                }

                // 3. Guardar en BD
                AccesoBD bd = new AccesoBD();
                if (bd.editarDatosUsuario(user)) {
                    session.setAttribute("usuario", user);
                    session.setAttribute("success", "Perfil actualizado correctamente.");
                } else {
                    session.setAttribute("error", "No se pudo actualizar en la base de datos.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Error al procesar los datos.");
            }
        }
        
        // Redirigir siempre al perfil al terminar el POST
        response.sendRedirect(request.getContextPath() + "/perfil");
    }
}