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
		String fase = request.getParameter("fase"); // Paso 1 o 2 o 3
		String contextPath = request.getContextPath();

		try {
			AccesoBD bd = new AccesoBD();

			switch (fase) {
			case "1":
				// Paso 1: Guardar datos en sesión
				session.setAttribute("nombre", request.getParameter("fname"));
				session.setAttribute("fecha", request.getParameter("fdate"));
				session.setAttribute("tipo", request.getParameter("ftype"));
				session.setAttribute("numVol", request.getParameter("fnumvol"));
				session.setAttribute("idOrganizador", request.getParameter("fid"));
				
				// Concatenar dirección: "CP Pueblo (Provincia)"
			    String cp = request.getParameter("fcp");
			    String pueblo = request.getParameter("fpueblo");
			    String provincia = request.getParameter("fprovincia");

			    String lugarCompleto = String.format("%s %s (%s)", cp, pueblo, provincia);
			    session.setAttribute("lugar", lugarCompleto);
			    
				response.sendRedirect(contextPath + "/descripcionevento");
				break;

			case "2":
				// Paso 2: Recuperar datos de sesión y crear evento
				try {
					String nombre = (String) session.getAttribute("nombre");
					LocalDate fecha = LocalDate.parse((String) session.getAttribute("fecha"));
					String tipo = (String) session.getAttribute("tipo");
					String lugar = (String) session.getAttribute("lugar");
					int numVol = Integer.parseInt((String) session.getAttribute("numVol"));
					int idOrganizador = Integer.parseInt((String) session.getAttribute("idOrganizador"));
					String descripcion = request.getParameter("fdescripcion");

					Evento e = new Evento(nombre, lugar, fecha, tipo, 0, descripcion, numVol, idOrganizador, 0);

					if (bd.crearEvento(e)) {

						int idEvento = AccesoBD.obtenerUltimoEvento();
						session.setAttribute("idEvento", idEvento);
					} else {
						request.setAttribute("error", "Error al crear el evento.");
					}

					request.getRequestDispatcher("/recorridoevento").forward(request, response);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;

			case "3":
				// Paso 3: Crear recorrido
				try {
					double kmLlegada = Double.parseDouble(request.getParameter("kmLlegada"));
					int idEvento = Integer.parseInt(request.getParameter("idEvento"));

					int puntoId = 1;

					bd.insertarRecorrido(new Puntos(idEvento, 2, kmLlegada));

					while (request.getParameter("tipoPunto_" + puntoId) != null
							&& request.getParameter("kmPunto_" + puntoId) != null) {

						int tipoPunto = Integer.parseInt(request.getParameter("tipoPunto_" + puntoId));
						double kmPunto = Double.parseDouble(request.getParameter("kmPunto_" + puntoId));

						Puntos punto = new Puntos(idEvento, tipoPunto, kmPunto);
						bd.insertarRecorrido(punto);

						puntoId++;
					}

					request.setAttribute("success", "Evento creado correctamente");
					request.getRequestDispatcher("/evento?" + idEvento).forward(request, response);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;

			default:
				response.sendRedirect(contextPath + "/home");
				break;
			}

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			request.setAttribute("error", "Error en el servidor: " + ex.getMessage());
			request.getRequestDispatcher("/crearevento").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
