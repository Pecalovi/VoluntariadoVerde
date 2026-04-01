<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento"%>
<%
ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("eventos");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>

<section id="imagen_index">
	<h1>
		<%=lang.equals("es") ? "Te damos la bienvenida a Voluntariado Verde" : "Welcome to Voluntariado Verde"%>
	</h1>
</section>

<section id="container_eventos">
	<h1>
		<%=lang.equals("es") ? "Próximos eventos" : "Upcoming events"%>
	</h1>

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
				<h2><%=evento.getNombre()%></h2>

				<div class="extra_info">
					<p><%=evento.getTipo()%></p>

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

				<p class="descripcion_evento"><%=evento.getDescripcion()%></p>
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
</section>