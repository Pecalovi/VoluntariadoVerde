package control;

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

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("usuario");

        // Idioma
        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "es";
            session.setAttribute("lang", lang);
        }

        // Instancia AccesoBD
        AccesoBD bd = null;
        try {
            bd = new AccesoBD();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String opcion = request.getParameter("opcion");
        if (opcion == null || opcion.isEmpty()) {
            opcion = "voluntarios";
        }

        if (bd != null) {
            switch (opcion) {
                case "voluntarios":
                    request.setAttribute("tablas", bd.PanelAdmin("voluntarios"));
                    break;

                case "organizadores":
                    request.setAttribute("tablas", bd.PanelAdmin("organizadores"));
                    break;

                case "eventos":
                    request.setAttribute("tablas", bd.PanelAdmin("eventos"));
                    break;

                default:
                	request.setAttribute("tablas", bd.PanelAdmin("voluntarios"));
                    break;
            }
        }

        // =========================
        // VIEW GLOBAL
        // =========================
        request.setAttribute("view", "admin/Admin.jsp");
        request.setAttribute("opcion", opcion);
        request.setAttribute("estilo", "estilos/Perfil.css");

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}