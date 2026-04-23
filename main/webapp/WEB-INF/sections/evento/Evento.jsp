<%@ page pageEncoding="UTF-8"%>

<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
boolean es = lang.equals("es");

Boolean inscrito = (Boolean) request.getAttribute("inscrito");
String estadoInscripcion = (String) request.getAttribute("estadoInscripcion");
%>

<div id="tarjeta">

	<div id="banner"
		style="background: linear-gradient(rgba(0, 0, 0, 0.548), rgba(43, 43, 43, 0.3)), 
             url('${pageContext.request.contextPath}/src/eventos/${evento.tipo}.jpg') center center / cover no-repeat;">

		<h1>${evento.nombre} ${evento.edicion}</h1>
	</div>

	<div id="tarjeta-info">

		<div id="resumen">

			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_fecha.png" alt="date icon">
				<p>${evento.fecha_inicio} / ${evento.fecha_fin}</p>
			</div>

			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_ubi.png"
					alt="location icon">
				<p>${evento.lugar}</p>
			</div>

			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_inscritos.png"
					alt="participants icon">
				<p>${inscritos}
					<%=es ? "Voluntarios" : "Volunteers"%></p>
			</div>

		</div>

		<hr>

		<p id="descripcion">${evento.descripcion}</p>

		<div id="inscripcion">

			<%
			if ("Rechazado".equals(estadoInscripcion)) {
			%>

			<span class="botones"
				style="background-color: #aaa; color: white; border: 1px solid #999; margin-top: 0; cursor: not-allowed;">
				<%=es ? "Inscripción rechazada" : "Registration rejected"%>
			</span>

			<%
			} else if ("Cancelado".equals(estadoInscripcion) || inscrito == null || !inscrito) {
			%>

			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">

				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="inscribir"> <input
					type="submit" class="botones"
					value="<%=es ? "Inscríbete" : "Sign up"%>">
			</form>

			<%
			} else {
			%>

			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">

				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="cancelar"> <input
					type="submit" class="botones"
					value="<%=es ? "Cancelar inscripción" : "Cancel registration"%>"
					style="background-color: #d9534f; color: white; border: 1px solid #d43f3a;">
			</form>

			<%
			}
			%>

		</div>

	</div>

</div>