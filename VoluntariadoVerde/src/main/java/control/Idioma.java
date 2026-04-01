package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/idioma")
public class Idioma extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    
	    String lang = request.getParameter("lang");
	    
	    if (lang == null) {
	        lang = (String) session.getAttribute("lang");
	        if (lang == null) lang = "es";
	        lang = lang.equals("es") ? "en" : "es";
	    }
	    
	    session.setAttribute("lang", lang);
	    
	    String referer = request.getHeader("Referer");
	    response.sendRedirect(referer != null ? referer : request.getContextPath() + "/home");
	}

}
