<%@ page pageEncoding="UTF-8" %>
<div class="indicador-pasos">
	<div class="paso activo">
		<span>1</span>
		<p>Evento</p>
	</div>
	<div class="paso">
		<span>2</span>
		<p>Tareas</p>
	</div>
	<div class="paso">
		<span>3</span>
		<p>Recorrido</p>
	</div>
</div>

<div class="enuncidado">
	<h1>Datos del evento</h1>
	<p>Completa la información básica y la descripción para crear tu evento.</p>
</div>

<form method="post" action="${pageContext.request.contextPath}/ServCrearEvento">
    <input type="hidden" name="fase" value="1">

    <input type="text" name="nombre" placeholder="Nombre" required>

    <select name="tipo" required>
        <option value="" disabled selected>Selecciona tipo</option>
        <option value="Dinamico">Dinámico</option>
        <option value="Estatico">Estático</option>
    </select>

    <input type="text" name="edicion" placeholder="Edición (ej: 2025, 3ª edición)">

    <label for="fecha_inicio">Fecha de inicio</label>
    <input id="fecha_inicio" type="date" name="fecha_inicio" required>

    <label for="fecha_fin">Fecha de fin (Obligatorio)</label>
    <input id="fecha_fin" type="date" name="fecha_fin">

    <fieldset>
        <label for="fecha_inicio">Direccion del evento</label>
        <input type="text" name="pueblo" placeholder="Pueblo / Ciudad" required>
        <input type="text" name="provincia" placeholder="Provincia" required>
        <input type="number" name="cp" placeholder="C&oacute;digo Postal" min="1000" max="99999" required>
    </fieldset>

    <textarea name="descripcion" placeholder="Descripción del evento" rows="5" required></textarea>

    <input type="hidden" name="id" value="${sessionScope.usuario.id}">
    <input type="submit" class="botones" value="Siguiente">
</form>

<script>
  const hoy = new Date();
  hoy.setHours(0,0,0,0);
  const minFecha = hoy.toISOString().slice(0,10);
  document.getElementById("fecha_inicio").min = minFecha;
  document.getElementById("fecha_fin").min = minFecha;

  document.getElementById("fecha_inicio").addEventListener("change", function() {
    document.getElementById("fecha_fin").min = this.value;
  });
</script>
