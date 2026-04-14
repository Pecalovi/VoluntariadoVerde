<div class="indicador-pasos">
	<div class="paso activo">
		<span>1</span>
		<p>Details</p>
	</div>
	<div class="paso">
		<span>2</span>
		<p>Description</p>
	</div>
	<div class="paso">
		<span>3</span>
		<p>Route</p>
	</div>
</div>

<div class="enuncidado">
	<h1>Event details</h1>
	<p>Complete the basic information to create your event.</p>
</div>

<form method="post" action="${pageContext.request.contextPath}/ServCrearEvento">
    <input type="hidden" name="fase" value="1">
    <input type="text" name="fname" placeholder="Name" required>
    <input id="fdate" type="date" name="fdate" placeholder="Date" required>
    <select name="ftype" required>
        <option value="" disabled selected>Select type</option>
        <option value="Motocros">Motocross</option>
        <option value="Maraton">Marathon</option>
        <option value="Ciclismo">Cycling</option>
        <option value="Senderismo">Hiking / Trail</option>
        <option value="Otro">Other</option>
    </select>
    <input type="number" name="fnumvol" placeholder="Volunteers" min="1" required>
    <input type="text" name="flugar" placeholder="Location" required>
    <input type="hidden" name="fid" value="${sessionScope.usuario.id}">
    <input type="submit" class="botones" value="Next">
</form>

<script>
  const hoy = new Date();
  hoy.setHours(0,0,0,0);
  document.getElementById("fdate").min = hoy.toISOString().slice(0,10);
</script>
