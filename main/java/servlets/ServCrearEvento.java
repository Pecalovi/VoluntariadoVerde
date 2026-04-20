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
import model.Evento;
import model.Puntos;

@WebServlet("/ServCrearEvento")
public class ServCrearEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		int fase = Integer.parseInt(request.getParameter("fase"));
		
		// =========================
		// FASE 1: Crear el evento
		// =========================
		if (fase == 1) {
			String nombre      = request.getParameter("nombre");
			String tipo        = request.getParameter("tipo");
			String edicion     = request.getParameter("edicion");
			String estado      = "Publicado";
			String lugar       = request.getParameter("lugar");
			String descripcion = request.getParameter("descripcion");
			int idOrganizador  = Integer.parseInt(request.getParameter("id"));

			LocalDate fechaInicio = LocalDate.parse(request.getParameter("fecha_inicio"));
			String fechaFinStr = request.getParameter("fecha_fin");
			LocalDate fechaFin = (fechaFinStr != null && !fechaFinStr.isEmpty()) ? LocalDate.parse(fechaFinStr) : null;

			Evento evento = new Evento(0, nombre, tipo, descripcion, fechaInicio, fechaFin, lugar, edicion, estado, idOrganizador);

			try {
				AccesoBD bd = new AccesoBD();
				boolean ok = bd.crearEvento(evento);

				if (ok) {
					int idEvento = AccesoBD.obtenerUltimoEvento();
					session.setAttribute("idEvento", idEvento);
					bd.disconnect();
					response.sendRedirect(contextPath + "/recorridoevento");
				} else {
					bd.disconnect();
					response.sendRedirect(contextPath + "/crearevento?error=1");
				}
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
				response.sendRedirect(contextPath + "/crearevento?error=1");
			}

		// =========================
		// FASE 2: Guardar recorrido
		// =========================
		} else if (fase == 2) {
			Integer idEvento = (Integer) session.getAttribute("idEvento");

			if (idEvento == null) {
				response.sendRedirect(contextPath + "/home");
				return;
			}

			try {
				AccesoBD bd = new AccesoBD();

				// Punto de llegada
				double kmLlegada = Double.parseDouble(request.getParameter("kmLlegada"));
				bd.insertarRecorrido(new Puntos(idEvento, 1, kmLlegada));

				// Puntos intermedios
				String[] kmsIntermedios = request.getParameterValues("kmIntermedio");
				if (kmsIntermedios != null) {
					for (String km : kmsIntermedios) {
						if (km != null && !km.isEmpty()) {
							bd.insertarRecorrido(new Puntos(idEvento, 2, Double.parseDouble(km)));
						}
					}
				}

				bd.disconnect();
				session.removeAttribute("idEvento");
				response.sendRedirect(contextPath + "/home");

			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
				response.sendRedirect(contextPath + "/recorridoevento?error=1");
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
