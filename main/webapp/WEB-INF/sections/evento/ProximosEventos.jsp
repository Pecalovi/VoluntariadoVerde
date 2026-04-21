<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento"%>
<%
ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("eventos");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

String nombreFiltro = request.getParameter("nombre");
String tipoFiltro = request.getParameter("tipo");
String ciudadFiltro = request.getParameter("ciudad");

boolean hayFiltros = (nombreFiltro != null && !nombreFiltro.isEmpty()) || (tipoFiltro != null && !tipoFiltro.isEmpty())
		|| (ciudadFiltro != null && !ciudadFiltro.isEmpty());
%>

<section id="container_eventos">
	<h1>
		<%=lang.equals("es") ? "Próximos eventos" : "Upcoming events"%>
	</h1>

	<form id="formBuscar" class="buscar"
		action="<%=request.getContextPath()%>/eventos" method="POST">

		<img src="src/lupa.png"> <input type="text" name="nombre"
			placeholder="<%=lang.equals("es") ? "Buscar evento..." : "Find event..."%>">

		<img src="src/filtrar.png" class="icono-filtro" id="btn-toggle-filtro">

		<%
		if (hayFiltros) {
		%>
		<img src="src/limpiar-filtro.png" class="icono-filtro"
			id="btn-reset-filtro">
		<%
		}
		%>

	</form>

	<!-- PANEL DE FILTROS -->
	<div id="panel-filtros" class="oculto">
		<select name="tipo" form="formBuscar">
			<option value="" disabled selected><%=lang.equals("es") ? "Selecciona tipo de evento" : "Select event type"%></option>
			<option value="Estatico"><%=lang.equals("es") ? "Estático" : "Static"%></option>
			<option value="Dinamico"><%=lang.equals("es") ? "Dinámico" : "Dynamic"%></option>
		</select> <input type="text" name="ciudad" placeholder="<%=lang.equals("es") ? "Ciudad" : "City"%>"
			form="formBuscar">

		<button type="submit" form="formBuscar" class="botones"><%=lang.equals("es") ? "Filtrar" : "Filter"%></button>
	</div>

	<div class="eventos">
		<%
		if (eventos != null && !eventos.isEmpty()) {
			double delay = 0.0;
			for (Evento evento : eventos) {
		%>

		<a
			href="<%=request.getContextPath()%>/evento?id=<%=evento.getIdEvento()%>"
			class="evento_index1 animate__animated animate__bounceInLeft"
			style="animation-delay: <%=delay%>s;">
			<div class="evento_index">
				<img src="src/eventos/<%=evento.getTipo()%>.jpg"
					alt="<%=evento.getTipo()%>">
				<div class="informacion-evento">
					<p><%=evento.getTipo()%></p>
					<h2><%=evento.getNombre()%> <%=evento.getEdicion()%></h2>

					<div class="extra_info">

						<div>
							<img class="icono" src="src/Icono-evento_ubi.png"
								alt="icono ubicación">
							<p><%=evento.getLugar()%></p>
						</div>

						<div>
							<img class="icono" src="src/Icono-evento_fecha.png"
								alt="icono fecha">
							<p><%=evento.getFecha_inicio()%></p>
						</div>
					</div>
				</div>
			</div>
		</a>

		<%
		delay += 0.3;
		}
		} else {
		%>
		<p>
			<%=hayFiltros
		? (lang.equals("es")
				? "No se encontraron eventos con los filtros seleccionados."
				: "No events found with the selected filters.")
		: (lang.equals("es") ? "No hay ningún evento próximamente." : "No upcoming events.")%>
		</p>
		<%
		}
		%>
	</div>
</section>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const panel = document.getElementById("panel-filtros");
    const btnToggle = document.getElementById("btn-toggle-filtro");
    const btnReset = document.getElementById("btn-reset-filtro");

    // Abrir/Cerrar
    if (btnToggle) {
        btnToggle.addEventListener("click", function(e) {
            e.preventDefault();
            panel.classList.toggle("activo");
        });
    }

    // Resetear (si existe el botón)
    if (btnReset) {
        btnReset.addEventListener("click", function() {
            window.location.href = "<%=request.getContextPath()%>/eventos";
			});
		}

		// Cerrar al hacer clic fuera
		document.addEventListener("click", function(e) {
			if (!panel.contains(e.target)
					&& !e.target.classList.contains("icono-filtro")) {
				panel.classList.remove("activo");
			}
		});
	});
</script>