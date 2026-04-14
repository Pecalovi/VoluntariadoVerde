<div class="indicador-pasos">
	<div class="paso completado">
		<span>1</span>
		<p>Details</p>
	</div>
	<div class="paso activo">
		<span>2</span>
		<p>Description</p>
	</div>
	<div class="paso">
		<span>3</span>
		<p>Route</p>
	</div>
</div>

<div class="enuncidado">
	<h1>Event description</h1>
	<p>Explain what the event is about and what volunteers should know.</p>
</div>

<form action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
	<input type="hidden" name="fase" value="2">

	<textarea id="descripcion" name="fdescripcion" rows="1"
		placeholder="Write the description..."></textarea>

	<input type="submit" class="botones" value="Next">

</form>
