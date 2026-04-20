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
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

        AccesoBD bd;
        try {
            bd = new AccesoBD();

            switch (accion) {

                case "verDatos":
                    // cargar datos del usuario
                    request.setAttribute("usuarioDetalle", idUsuario);
                    request.getRequestDispatcher("/admin/detalleUsuario.jsp")
                           .forward(request, response);
                    break;

                case "eliminarCuenta":
                    bd.borrarVoluntario(idUsuario);
                    response.sendRedirect("admin?opcion=voluntarios");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}