<div class="indicador-pasos">
	<div class="paso completado">
		<span>1</span>
		<p>Datos</p>
	</div>
	<div class="paso activo">
		<span>2</span>
		<p>Tareas</p>
	</div>
	<div class="paso">
		<span>3</span>
		<p>Recorrido</p>
	</div>
</div>

<div class="enuncidado">
	<h1>Tareas del evento</h1>
	<p>Explica en qué consisten las tareas y qué deben saber los
		voluntarios.</p>
</div>

<form action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
	<input type="hidden" name="fase" value="2">

	<textarea id="descripcion" name="fdescripcion" rows="1"
		placeholder="Escribe la descripción.."></textarea>

	<input type="submit" class="botones" value="Siguiente">

</form>