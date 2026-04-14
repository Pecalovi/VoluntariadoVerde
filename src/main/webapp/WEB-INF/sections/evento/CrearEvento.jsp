
<div class="indicador-pasos">
	<div class="paso activo">
		<span>1</span>
		<p>Datos</p>
	</div>
	<div class="paso">
		<span>2</span>
		<p>Descripción</p>
	</div>
	<div class="paso">
		<span>3</span>
		<p>Recorrido</p>
	</div>
</div>

<div class="enuncidado">
	<h1>Datos del evento</h1>
	<p>Completa la información básica para crear tu evento.</p>
</div>

<form method="post" action="${pageContext.request.contextPath}/ServCrearEvento">
    <input type="hidden" name="fase" value="1">
    <input type="text" name="fname" placeholder="Nombre" required>
    <input id="fdate" type="date" name="fdate" placeholder="Fecha"  required>
    <select name="ftype" required>
        <option value="" disabled selected>Selecciona tipo</option>
        <option value="Motocros">Motocross</option>
        <option value="Maraton">Maratón</option>
        <option value="Ciclismo">Ciclismo</option>
        <option value="Senderismo">Senderismo / Trail</option>
        <option value="Otro">Otro</option>
    </select>
    <input type="number" name="fnumvol" placeholder="Voluntarios" min="1" required>
    <fieldset>
        <legend>Dirección del evento</legend>

        <input type="text" name="fpueblo" placeholder="Pueblo / Ciudad" required>
        <input type="text" name="fprovincia" placeholder="Provincia" required>
        <input type="number" name="fcp" placeholder="Código Postal" pattern="[0-9]{5}" title="Introduce un CP válido de 5 dígitos" required>
    </fieldset>
    <input type="hidden" name="fid" value="${sessionScope.usuario.id}">
    <input type="submit" class="botones" value="Siguiente">
</form>


<script>
  const hoy = new Date();
  hoy.setHours(0,0,0,0);
  document.getElementById("fdate").min = hoy.toISOString().slice(0,10);
</script>