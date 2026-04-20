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

@WebServlet("/admin")
public class ServAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");

        if ("eliminarCuenta".equals(accion) && user != null) {
            try {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                AccesoBD bd = new AccesoBD();
                bd.borrarDatosUsuario(idUsuario);
                session.setAttribute("success", "Cuenta eliminada correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Error al eliminar la cuenta.");
            }

        } else if ("verDatos".equals(accion) && user != null) {
            try {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                ArrayList<Object> tablas = (ArrayList<Object>) request.getSession().getAttribute("tablas");
                
                for (Object obj : tablas) {
                    Voluntario v = (Voluntario) obj;
                    if (v.getId() == idUsuario) {
                        request.setAttribute("usuarioDetalle", v);
                        break;
                    }
                }
                
                request.getRequestDispatcher("/admin.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Error al obtener los datos.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin?opcion=voluntarios");
    }
}