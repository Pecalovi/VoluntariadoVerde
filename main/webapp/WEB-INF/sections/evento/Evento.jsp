<%@ page pageEncoding="UTF-8"%>
<div id="tarjeta">

	<div id="banner"
		style="background: linear-gradient(rgba(0, 0, 0, 0.548), rgba(43, 43, 43, 0.3)), 
             url('${pageContext.request.contextPath}/src/eventos/${evento.tipo}.jpg') center center / cover no-repeat;">
		<h1>${evento.nombre}${evento.edicion}</h1>
	</div>

	<div id="tarjeta-info">

		<div id="resumen">
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_fecha.png"
					alt="icono fecha">
				<p>${evento.fecha_inicio}/${evento.fecha_fin}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_ubi.png" alt="icono ubi">
				<p>${evento.lugar}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_inscritos.png"
					alt="icono inscritos">
				<p>${inscritos}Voluntarios</p>
			</div>
		</div>
		<hr>
		<p id="descripcion">${evento.descripcion}</p>

		<div id="inscripcion">

			<%
			Boolean inscrito = (Boolean) request.getAttribute("inscrito");
			String estadoInscripcion = (String) request.getAttribute("estadoInscripcion");

			if ("Rechazado".equals(estadoInscripcion)) {
			%>

			<span class="botones"
				style="background-color: #aaa; color: white; border: 1px solid #999; margin-top: 0; cursor: not-allowed; display: inline-block;">
				Inscripción rechazada </span>

			<%
			} else if ("Cancelado".equals(estadoInscripcion) || inscrito == null || !inscrito) {
			%>

			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="inscribir"> <input
					type="submit" class="botones" value="Inscríbete"
					style="margin-top: 0;">
			</form>

			<%
			} else {
			%>

			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="cancelar"> <input
					type="submit" class="botones" value="Cancelar inscripción"
					style="background-color: #d9534f; color: white; border: 1px solid #d43f3a; margin-top: 0; cursor: pointer;">
			</form>

			<%
			}
			%>

		</div>
	</div>

</div>