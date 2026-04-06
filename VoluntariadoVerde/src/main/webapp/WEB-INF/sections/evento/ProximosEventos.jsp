<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento"%>
<%
ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("eventos");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>

<section id="container_eventos">
	<h1>
		<%=lang.equals("es") ? "Próximos eventos" : "Upcoming events"%>
	</h1>

	<form id="formBuscar" class="buscar"
		action="<%=request.getContextPath()%>/ProximosEventos" method="POST">

		<img src="src/lupa.png"> <input type="text" name="nombre"
			placeholder="<%=lang.equals("es") ? "Buscar evento..." : "Find event..."%>">

		<img src="src/filtrar.png" class="icono-filtro"
			onclick="toggleFiltros(); return false;">

	</form>

	<!-- PANEL DE FILTROS -->
	<div id="panelFiltros" class="oculto">
		<select name="tipo" form="formBuscar">
			<option value="">Tipo</option>
			<option value="musica">Música</option>
			<option value="deporte">Deporte</option>
			<option value="teatro">Teatro</option>
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
				<img src="src/<%=evento.getTipo()%>.jpg" alt="<%=evento.getTipo()%>">
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
		<p><%=lang.equals("es") ? "No hay ningún evento próximamente." : "No upcoming events."%></p>
		<%
		}
		%>
	</div>
</section>

<script>
	// Abrir / cerrar panel al hacer click en el icono
	function toggleFiltros() {
		document.getElementById("panelFiltros").classList.toggle("activo");
	}

	// Cerrar panel al hacer click fuera
	document.addEventListener("click", function(e) {
		const panel = document.getElementById("panelFiltros");
		const icono = document.querySelector(".icono-filtro");

		if (!panel.contains(e.target) && !icono.contains(e.target)) {
			panel.classList.remove("activo");
		}
	});
</script>