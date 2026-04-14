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

		<img src="src/filtrar.png" class="icono-filtro"
			onclick="toggleFiltros(); return false;">

		<%
		if (hayFiltros) {
		%>
		<img src="src/limpiar-filtro.png" class="icono-filtro"
			onclick="resetFiltros(); return false;">
		<%
		}
		%>

	</form>

	<!-- PANEL DE FILTROS -->
	<div id="panel-filtros" class="oculto">
		<select name="tipo" form="formBuscar">
			<option value="" disabled selected>Selecciona tipo de evento</option>
			<option value="Motocros">Motocross</option>
			<option value="Maraton">Maratón</option>
			<option value="Ciclismo">Ciclismo</option>
			<option value="Senderismo">Senderismo / Trail</option>
			<option value="Otro">Otro</option>
		</select> <input type="text" name="ciudad" placeholder="Ciudad"
			form="formBuscar">

		<button type="submit" form="formBuscar" class="botones">Filtrar</button>
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
				<img src="src/eventos/<%=evento.getTipo()%>.jpg" alt="<%=evento.getTipo()%>">
				<div class="informacion-evento">
					<p><%=evento.getTipo()%></p>
					<h2><%=evento.getNombre()%></h2>

					<div class="extra_info">

						<div>
							<img class="icono" src="src/Icono-evento_ubi.png"
								alt="icono ubicación">
							<p><%=evento.getUbicacion()%></p>
						</div>

						<div>
							<img class="icono" src="src/Icono-evento_fecha.png"
								alt="icono fecha">
							<p><%=evento.getFechaFormateada()%></p>
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
	// Abrir / cerrar panel al hacer click en el icono
	function toggleFiltros() {
		document.getElementById("panel-filtros").classList.toggle("activo");
	}

	// Cerrar panel al hacer click fuera
	document.addEventListener("click", function(e) {
		const panel = document.getElementById("panel-filtros");
		const icono = document.querySelector(".icono-filtro");

		if (!panel.contains(e.target) && !icono.contains(e.target)) {
			panel.classList.remove("activo");
		}
	});
	
	function resetFiltros() {
	    window.location.href = "<%=request.getContextPath()%>/eventos";
	}
</script>