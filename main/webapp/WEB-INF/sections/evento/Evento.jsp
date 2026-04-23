<%@ page pageEncoding="UTF-8" %>
<div id="tarjeta">

	<div id="banner"
		style="background: linear-gradient(rgba(0, 0, 0, 0.548), rgba(43, 43, 43, 0.3)), 
             url('${pageContext.request.contextPath}/src/eventos/${evento.tipo}.jpg') center center / cover no-repeat;">
		<h1>${evento.nombre} ${evento.edicion}</h1>
	</div>

	<div id="tarjeta-info">

		<div id="resumen">
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_fecha.png"
					alt="icono fecha">
				<p>${evento.fecha_inicio} / ${evento.fecha_fin}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_ubi.png" alt="icono ubi">
				<p>${evento.lugar}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_inscritos.png"
					alt="icono inscritos">
				<p>${inscritos} Voluntarios</p>
			</div>
		</div>
		<hr>
		<p id="descripcion">${evento.descripcion}</p>

		<div id="inscripcion">

			<%
			String estadoInscripcion = (String) request.getAttribute("estadoInscripcion");
			if (estadoInscripcion != null) {
				String cssEstado = "btn-estado-" + estadoInscripcion.toLowerCase().replace(" ", "-");
			%>
			<form action="${pageContext.request.contextPath}/ServInscripcion" method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="cancelar">
				<button type="submit" class="botones btn-estado <%=cssEstado%>" onclick="return confirm('¿Estás seguro de que quieres cancelar tu inscripción?')">
					<span class="texto-estado"><%=estadoInscripcion%></span>
					<span class="texto-cancelar">Cancelar inscripción</span>
				</button>
			</form>
			<%
			} else {
			%>
			<form action="${pageContext.request.contextPath}/ServInscripcion" method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">
				<input type="hidden" name="accion" value="inscribir">
				<input type="submit" class="botones" value="Inscríbete" style="margin-top: 0;">
			</form>
			<%
			}
			%>
		</div>
	</div>

</div>