<div id="tarjeta">

	<div id="banner"
		style="background: linear-gradient(rgba(0, 0, 0, 0.548), rgba(43, 43, 43, 0.3)), 
             url('${pageContext.request.contextPath}/src/eventos/${evento.tipo}.jpg') center center / cover no-repeat;">
		<h1>${evento.nombre}</h1>
	</div>

	<div id="tarjeta-info">

		<div id="resumen">
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_fecha.png"
					alt="icono fecha">
				<p>${evento.fechaFormateada}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_ubi.png" alt="icono ubi">
				<p>${evento.ubicacion}</p>
			</div>
			<div class="icoytxt">
				<img class="icono" src="src/Icono-evento_inscritos.png"
					alt="icono fecha">
				<p>${evento.plazasTotales}</p>
			</div>
		</div>
		<hr>
		<p id="descripcion">${evento.descripcion}</p>

		<div id="inscripcion">
			<p>${evento.inscritos}/${evento.plazasTotales} inscritos</p>
			<form action="${pageContext.request.contextPath}/ServInscripcion"
				method="post">
				<input type="hidden" name="idEvento" value="${evento.idEvento}">

				<%
				Boolean inscrito = (Boolean) request.getAttribute("inscrito");
				if (inscrito) {
				%>
				<input type="button" class="botones" value="¡Ya estas inscrito!" disabled
					style="background-color: #ccc; color: #666; border: 1px solid #999; cursor: not-allowed; margin-top: 0;">

				<%
				} else {
				%>
				<input type="submit" class="botones" value="Inscribete"
					style="margin-top: 0;">
				<%
				}
				%>
			</form>
		</div>
	</div>

</div>