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
					alt="icono fecha">
			</div>
		</div>
		<hr>
		<p id="descripcion">${evento.descripcion}</p>

		<div id="inscripcion">

			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">

				<%
				Boolean inscrito = (Boolean) request.getAttribute("inscrito");
				if (inscrito != null && inscrito) {
				%>
				<input type="hidden" name="accion" value="cancelar"> <input
					type="submit" class="botones" value="Cancelar inscripción"
					style="background-color: #d9534f; color: white; border: 1px solid #d43f3a; margin-top: 0; cursor: pointer;">
				<%
				} else {
				%>
				<input type="hidden" name="accion" value="inscribir"> <input
					type="submit" class="botones" value="Inscríbete"
					style="margin-top: 0;">
				<%
				}
				%>
			</form>
		</div>
	</div>

</div>