<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento"%>

<%
ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("eventos");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

boolean es = "es".equals(lang);
%>

<section id="imagen-index">
	<h1>
		<%=es ? "Te damos la bienvenida a Voluntariado Verde" : "Welcome to Voluntariado Verde"%>
	</h1>
</section>

<section id="container-eventos">
	<h1>
		<%=es ? "Eventos destacados" : "Top events"%>
	</h1>

	<div class="eventos">
		<%
		if (eventos != null && !eventos.isEmpty()) {
			double delay = 0.0;

			for (int i = 0; i < eventos.size() && i < 3; i++) {
				Evento evento = eventos.get(i);
		%>

		<a
			href="<%=request.getContextPath()%>/evento?id=<%=evento.getIdEvento()%>"
			class="evento-index1 animate__animated animate__bounceInLeft"
			style="animation-delay: <%=delay%>s;">

			<div class="evento-index">
				<img src="src/eventos/<%=evento.getTipo()%>.jpg" alt="<%=evento.getTipo()%>">

				<div class="informacion-evento">
					<p><%=evento.getTipo()%></p>
					<h2><%=evento.getNombre()%></h2>
					<p><%=evento.getFechaFormateada()%></p>
				</div>
			</div>

		</a>

		<%
		delay += 0.3;
		}
		} else {
		%>
		<p>
			<%=es ? "No hay ningún evento próximamente." : "No upcoming events."%>
		</p>
		<%
		}
		%>
	</div>
</section>