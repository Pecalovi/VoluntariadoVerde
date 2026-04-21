<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Tarea" %>
<%
    Integer paso = (Integer) request.getAttribute("paso");
    if (paso == null) paso = 1;
%>

<div class="indicador-pasos">
    <div class="paso <%= paso >= 1 ? (paso > 1 ? "completado" : "activo") : "" %>">
        <span>1</span>
        <p>Evento</p>
    </div>
    <div class="paso <%= paso >= 2 ? "activo" : "" %>">
        <span>2</span>
        <p>Tareas</p>
    </div>
    <div class="paso">
        <span>3</span>
        <p>Recorrido</p>
    </div>
</div>

<% if (paso == 1) { %>

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

<% } else { %>

<%
    List<Tarea> tareas = (List<Tarea>) request.getAttribute("tareas");
%>

<div class="enuncidado">
    <h1>Tareas del evento</h1>
    <p>Selecciona las tareas que necesitarás cubrir durante el evento.</p>
</div>

<form method="get" action="${pageContext.request.contextPath}/recorridoevento">
    <div class="tareas-lista">
        <% if (tareas != null && !tareas.isEmpty()) {
            for (Tarea t : tareas) { %>
                <label class="tarea-item">
                    <input type="checkbox" name="tareas" value="<%= t.getId() %>">
                    <span><%= t.getNombre() %></span>
                </label>
        <%  }
           } else { %>
            <p>No hay tareas disponibles.</p>
        <% } %>
    </div>

    <input type="submit" class="botones" value="Siguiente">
</form>

<% } %>
