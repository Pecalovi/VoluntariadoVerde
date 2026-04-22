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

		// FASE 1: Crear el evento

		if (fase == 1) {
			String nombre = request.getParameter("nombre");
			String tipo = request.getParameter("tipo");
			String edicion = request.getParameter("edicion");
			String estado = "Publicado";
			String pueblo = request.getParameter("pueblo");
			String provincia = request.getParameter("provincia");
			String cp = request.getParameter("cp");
			String lugar = cp + ", " + pueblo + " (" + provincia + ")";
			String descripcion = request.getParameter("descripcion");
			int idOrganizador = Integer.parseInt(request.getParameter("id"));

			LocalDate fechaInicio = LocalDate.parse(request.getParameter("fecha_inicio"));
			String fechaFinStr = request.getParameter("fecha_fin");
			LocalDate fechaFin = (fechaFinStr != null && !fechaFinStr.isEmpty()) ? LocalDate.parse(fechaFinStr) : null;

			Evento evento = new Evento(0, nombre, tipo, descripcion, fechaInicio, fechaFin, lugar, edicion, estado,
					idOrganizador);

			try {
				AccesoBD bd = new AccesoBD();
				boolean ok = bd.crearEvento(evento);

				if (ok) {
					int idEvento = AccesoBD.obtenerUltimoEvento();
					session.setAttribute("idEvento", idEvento);
					bd.disconnect();
					response.sendRedirect(contextPath + "/crearevento?paso=2");
				} else {
					bd.disconnect();
					response.sendRedirect(contextPath + "/crearevento?error=1");
				}
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
				response.sendRedirect(contextPath + "/crearevento?error=1");
			}

		// FASE 2: Crear tareas nuevas y avanzar al paso 3

		} else if (fase == 2) {
			try {
				String[] nuevasTareas = request.getParameterValues("nuevasTareas");
				if (nuevasTareas != null) {
					AccesoBD bd = new AccesoBD();
					for (String nombreTarea : nuevasTareas) {
						if (nombreTarea != null && !nombreTarea.trim().isEmpty()) {
							bd.crearTarea(capitalizarPrimera(nombreTarea.trim()));
						}
					}
					bd.disconnect();
				}
				response.sendRedirect(contextPath + "/recorridoevento");
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
				response.sendRedirect(contextPath + "/recorridoevento");
			}

		// FASE 3: Guardar recorrido y puntos de control

		} else if (fase == 3) {
			Integer idEvento = (Integer) session.getAttribute("idEvento");

			if (idEvento == null) {
				response.sendRedirect(contextPath + "/home");
				return;
			}

			try {
				AccesoBD bd = new AccesoBD();

				// Puntos de control
				String[] tipos    = request.getParameterValues("puntoTipo");
				String[] kms      = request.getParameterValues("puntoKm");
				String[] nombres  = request.getParameterValues("puntoNombre");
				String[] descs    = request.getParameterValues("puntoDesc");
				String[] tareas   = request.getParameterValues("puntoTareas");

				if (tipos != null) {
					for (int i = 0; i < tipos.length; i++) {
						int tipo = Integer.parseInt(tipos[i]);

						if (tipo == 2) {
							if (kms != null && kms[i] != null && !kms[i].isEmpty()) {
								bd.insertarRecorrido(new Puntos(idEvento, 2, Double.parseDouble(kms[i])));
							}
						} else if (tipo == 3) {
							String nombre  = capitalizarPrimera((nombres != null && nombres[i] != null) ? nombres[i].trim() : "");
							String desc    = (descs   != null && descs[i]   != null) ? descs[i].trim()   : "";
							int idPc = bd.insertarPuntoControl(nombre, desc, idEvento);
							if (idPc > 0 && tareas != null && tareas[i] != null && !tareas[i].isEmpty()) {
								for (String idTareaStr : tareas[i].split(",")) {
									String s = idTareaStr.trim();
									if (!s.isEmpty()) {
										bd.asignarTareaAPuntoControl(idPc, Integer.parseInt(s));
									}
								}
							}
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

	private static String capitalizarPrimera(String s) {
		if (s == null || s.isEmpty()) return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}
