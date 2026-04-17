package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/contacto")
public class ContactoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String lang = (String) session.getAttribute("lang");
	    
	    String status = (String) session.getAttribute("mensajeStatus");
	    if (status != null) {
	        request.setAttribute("mensajeStatus", status);
	        session.removeAttribute("mensajeStatus");
	    }

		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		request.setAttribute("view", "empresa/Contacto.jsp");
		request.setAttribute("activePage", "contacto");
		request.setAttribute("estilo", "estilos/Contacto.css");

		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("funame");
        String emailCliente = request.getParameter("femail");
        String asunto = request.getParameter("fasunto");
        String mensaje = request.getParameter("Text1");

        boolean enviadoAmi = Mailer.send("voluntariadoverdev@gmail.com", 
                "Nuevo mensaje de: " + nombre,
                "Has recibido una duda."+ "\nDe: "+ emailCliente +"\n\nAsunto: " + asunto + "\nCuerpo: " + mensaje);

        if (enviadoAmi) {
            String asuntoConfirmacion = "Hemos recibido tu consulta: " + asunto;
            String cuerpoConfirmacion = "Hola " + nombre + ",\n\n" +
                                        "Gracias por contactar con nosotros. Este es un mensaje automático " +
                                        "para confirmarte que hemos recibido tu solicitud correctamente.\n\n" +
                                        "Esto es lo que nos has enviado:\n" +
                                        "------------------------------------------\n" +
                                        mensaje + "\n" +
                                        "------------------------------------------\n\n" +
                                        "Te responderemos lo antes posible.\n" +
                                        "Saludos, el equipo de Voluntariado Verde.";

            Mailer.send(emailCliente, asuntoConfirmacion, cuerpoConfirmacion);
        }

        if (enviadoAmi) {
            // En lugar de request.setAttribute, usamos la sesión para que sobreviva a la redirección
            request.getSession().setAttribute("mensajeStatus", "¡Mensaje enviado!");
        } else {
            request.getSession().setAttribute("mensajeStatus", "Error al procesar el envío.");
        }
        response.sendRedirect(request.getContextPath() + "/contacto");
        
    }

}
