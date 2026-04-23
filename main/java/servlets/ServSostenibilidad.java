package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sostenibilidad")
public class ServSostenibilidad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String doc = request.getParameter("doc");
		String nombreArchivo;

		if ("guia".equals(doc)) {
			nombreArchivo = "Guia (Sostenibilidad).pdf";
		} else if ("prl".equals(doc)) {
			nombreArchivo = "PRL.docx.pdf";
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String path = getServletContext().getRealPath("/WEB-INF/sostenibilidad/" + nombreArchivo);
		File file = new File(path);

		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "\"");
		response.setContentLengthLong(file.length());

		try (FileInputStream fis = new FileInputStream(file);
				OutputStream os = response.getOutputStream()) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		}
	}
}
