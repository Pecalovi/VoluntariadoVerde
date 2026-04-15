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

		if (lang == null) {
			lang = "es";
			session.setAttribute("lang", lang);
		}

		request.setAttribute("view", "empresa/Contacto.jsp");

		request.setAttribute("estilo", "estilos/Contacto.css");

		// Encadenar la petición y cargar otro recurso
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Recoger parámetros del formulario (nombres coinciden con el 'name' de tus inputs)
        String nombre = request.getParameter("funame");
        String emailCliente = request.getParameter("femail");
        String asunto = request.getParameter("fasunto");
        String mensaje = request.getParameter("Text1");

        boolean enviadoAmi = Mailer.send("voluntariadoverdev@gmail.com", 
                "Nuevo mensaje de: " + nombre,
                "Has recibido una duda."+ "\nDe: "+ emailCliente +"\n\nAsunto: " + asunto + "\nCuerpo: " + mensaje);

        // --- CORREO 2: Para el CLIENTE (Confirmación) ---
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

            // Enviamos al correo que el usuario puso en el input 'femail'
            Mailer.send(emailCliente, asuntoConfirmacion, cuerpoConfirmacion);
        }

        // 3. Resultado final para la web
        if (enviadoAmi) {
            request.setAttribute("mensajeStatus", "¡Mensaje enviado! Revisa tu bandeja de entrada.");
        } else {
            request.setAttribute("mensajeStatus", "Error al procesar el envío.");
        }
        
        doGet(request, response);
    }

}
