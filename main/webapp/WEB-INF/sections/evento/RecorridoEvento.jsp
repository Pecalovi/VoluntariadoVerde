<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Tarea" %>
<%
    Integer idEvento = (Integer) session.getAttribute("idEvento");
    if (idEvento == null) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }
    List<Tarea> tareas = (List<Tarea>) request.getAttribute("tareas");
%>

<div class="indicador-pasos">
    <div class="paso completado"><span>1</span><p>Datos</p></div>
    <div class="paso completado"><span>2</span><p>Tareas</p></div>
    <div class="paso activo"><span>3</span><p>Puntos de control</p></div>
</div>

<div class="enuncidado">
    <h1>Puntos de control</h1>
    <p>Selecciona los puntos de control que necesitar&aacute;s cubrir durante el evento. Añadele un nombre, una descripci&oacute;n y asigna las tareas necesarias.</p>
</div>

<form id="formRecorrido" action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
    <input type="hidden" name="fase" value="3">
    <input type="hidden" name="idEvento" value="<%=idEvento%>">

    <div class="puntos-container">
        <div class="puntos-header" style="justify-content:center;">
            <button type="button" class="btn-agregar" id="btnAgregar">+ A&ntilde;adir punto de control</button>
        </div>
        <div id="puntosLista">
            <div class="empty-message" id="emptyMessage">
                No hay puntos a&ntilde;adidos. Pulsa el bot&oacute;n para a&ntilde;adir un punto de control.
            </div>
        </div>
    </div>

    <button type="submit" class="botones">Crear Recorrido</button>
</form>

<style>
.campos-control { display: flex; flex-direction: column; gap: 10px; margin-top: 10px; }
.campos-control label { font-size: 0.85rem; color: #555; font-weight: 600; }
.campos-control input[type="text"],
.campos-control textarea {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 0.95rem;
    box-sizing: border-box;
    font-family: inherit;
}
.campos-control textarea { resize: vertical; min-height: 70px; }
.tareas-control-lista {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-top: 4px;
}
.tarea-check-label {
    display: flex;
    align-items: center;
    gap: 6px;
    background: #f4f8fc;
    border: 1px solid #c0d9ee;
    border-radius: 20px;
    padding: 5px 12px;
    font-size: 0.85rem;
    cursor: pointer;
    transition: background 0.2s, border-color 0.2s;
}
.tarea-check-label:has(input:checked) {
    background: #d0eaff;
    border-color: #0070c0;
    font-weight: 600;
}
.tarea-check-label input { margin: 0; cursor: pointer; }
.sin-tareas { color: #aaa; font-size: 0.85rem; font-style: italic; }
</style>

<script>
// Tareas disponibles generadas por el servidor
const TAREAS = [
    <% if (tareas != null) { for (int i = 0; i < tareas.size(); i++) { Tarea t = tareas.get(i); %>
        {id: <%= t.getId() %>, nombre: "<%= t.getNombre().replace("\"", "\\\"") %>"}<%=i < tareas.size()-1 ? "," : ""%>
    <% }} %>
];

function generarCheckboxesTareas() {
    if (TAREAS.length === 0) return '<span class="sin-tareas">No hay tareas disponibles</span>';
    return TAREAS.map(function(t) {
        return '<label class="tarea-check-label">' +
               '<input type="checkbox" class="tarea-check" value="' + t.id + '">' +
               '<span>' + t.nombre + '</span>' +
               '</label>';
    }).join('');
}

document.getElementById('btnAgregar').addEventListener('click', function () {
    const lista = document.getElementById('puntosLista');
    const emptyMsg = document.getElementById('emptyMessage');
    if (emptyMsg) emptyMsg.style.display = 'none';

    const div = document.createElement('div');
    div.className = 'punto-item';
    div.innerHTML =
        '<input type="hidden" name="puntoTipo" value="3">' +
        '<input type="hidden" name="puntoKm" value="">' +
        '<button type="button" class="btn-eliminar" onclick="eliminarPunto(this)">&#215;</button>' +
        '<div class="form-group"><label>Nombre del punto de control</label>' +
        '<input type="text" name="puntoNombre" placeholder="Ej: Control de hidratación"></div>' +
        '<div class="form-group"><label>Descripción</label>' +
        '<textarea name="puntoDesc" placeholder="Describe este punto de control..."></textarea></div>' +
        '<div class="form-group"><label>Tareas asignadas a este punto</label>' +
        '<div class="tareas-control-lista">' + generarCheckboxesTareas() + '</div></div>' +
        '<input type="hidden" name="puntoTareas" value="">';
    lista.appendChild(div);
});

function eliminarPunto(btn) {
    btn.parentElement.remove();
    const lista = document.getElementById('puntosLista');
    if (lista.querySelectorAll('.punto-item').length === 0) {
        const emptyMsg = document.getElementById('emptyMessage');
        if (emptyMsg) emptyMsg.style.display = '';
    }
}

// Antes de enviar, recoger las tareas marcadas de cada punto de control
document.getElementById('formRecorrido').addEventListener('submit', function () {
    document.querySelectorAll('.punto-item').forEach(function (item) {
        const checked = item.querySelectorAll('.tarea-check:checked');
        item.querySelector('[name="puntoTareas"]').value =
            Array.from(checked).map(function(cb) { return cb.value; }).join(',');
    });
});
</script>
