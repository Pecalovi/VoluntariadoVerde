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
        <p>Puntos de control</p>
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

    <input type="text" min="2000" max="2099" name="edicion" placeholder="Edición (ej: 2026)">

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
    <p>Selecciona las tareas que necesitar&aacute;s cubrir durante el evento.</p>
</div>

<form method="post" action="${pageContext.request.contextPath}/ServCrearEvento">
    <input type="hidden" name="fase" value="2">

    <div class="tareas-lista" id="listaTareas">
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

    <!-- Nuevas tareas creadas por el usuario (tags) -->
    <div id="nuevasTareasContainer"></div>

    <!-- Input para añadir nueva tarea -->
    <div id="nuevaTareaBox" style="display:none;">
        <div class="nueva-tarea-input">
            <input type="text" id="inputNuevaTarea" placeholder="Nombre de la nueva tarea" maxlength="100">
            <button type="button" id="btnConfirmarTarea">A&ntilde;adir</button>
            <button type="button" id="btnCancelarTarea">Cancelar</button>
        </div>
    </div>

    <button type="button" class="btn-otro" id="btnOtro">+ Otra tarea</button>

    <input type="submit" class="botones" value="Siguiente">
</form>

<style>
.nueva-tarea-input {
    display: flex;
    gap: 10px;
    align-items: center;
    margin: 10px 0;
    flex-wrap: wrap;
}
.nueva-tarea-input input[type="text"] {
    flex: 1;
    min-width: 200px;
    padding: 10px 14px;
    border: 1px solid var(--verde-principal, #2e8b57);
    border-radius: 8px;
    font-size: 0.95rem;
    outline: none;
}
.nueva-tarea-input button {
    padding: 10px 18px;
    border-radius: 8px;
    border: none;
    font-weight: 600;
    cursor: pointer;
    font-size: 0.9rem;
}
#btnConfirmarTarea { background: var(--verde-principal, #2e8b57); color: white; }
#btnCancelarTarea  { background: #f0f0f0; color: #555; }
.btn-otro {
    background: none;
    border: 2px dashed #aaa;
    color: #666;
    padding: 10px 20px;
    border-radius: 8px;
    font-size: 0.9rem;
    cursor: pointer;
    transition: all 0.2s;
    margin-top: 4px;
}
.tarea-nueva-tag {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    background: #e8f5e9;
    border: 1px solid #a5d6a7;
    border-radius: 20px;
    padding: 6px 14px;
    margin: 4px;
    font-size: 0.9rem;
}
.tarea-nueva-tag button {
    background: none;
    border: none;
    color: #666;
    cursor: pointer;
    font-size: 1rem;
    line-height: 1;
    padding: 0;
}
</style>

<script>
document.getElementById('btnOtro').addEventListener('click', function () {
    document.getElementById('nuevaTareaBox').style.display = 'block';
    document.getElementById('inputNuevaTarea').focus();
});

document.getElementById('btnCancelarTarea').addEventListener('click', function () {
    document.getElementById('nuevaTareaBox').style.display = 'none';
    document.getElementById('inputNuevaTarea').value = '';
});

document.getElementById('btnConfirmarTarea').addEventListener('click', añadirTarea);
document.getElementById('inputNuevaTarea').addEventListener('keydown', function (e) {
    if (e.key === 'Enter') { e.preventDefault(); añadirTarea(); }
});

function añadirTarea() {
    const nombre = document.getElementById('inputNuevaTarea').value.trim();
    if (!nombre) return;

    const container = document.getElementById('nuevasTareasContainer');
    const tag = document.createElement('div');
    tag.className = 'tarea-nueva-tag';

    const span = document.createElement('span');
    span.textContent = nombre;

    const btn = document.createElement('button');
    btn.type = 'button';
    btn.innerHTML = '&#215;';
    btn.onclick = function () { tag.remove(); };

    const hidden = document.createElement('input');
    hidden.type = 'hidden';
    hidden.name = 'nuevasTareas';
    hidden.value = nombre;

    tag.appendChild(span);
    tag.appendChild(btn);
    tag.appendChild(hidden);
    container.appendChild(tag);

    document.getElementById('inputNuevaTarea').value = '';
    document.getElementById('nuevaTareaBox').style.display = 'none';
}
</script>

<% } %>
