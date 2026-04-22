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
    <div class="paso activo"><span>3</span><p>Recorrido</p></div>
</div>

<div class="enuncidado">
    <h1>Recorrido del evento</h1>
    <p>Indica el kil&oacute;metro de llegada y a&ntilde;ade los puntos del recorrido.</p>
</div>

<form id="formRecorrido" action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
    <input type="hidden" name="fase" value="3">
    <input type="hidden" name="idEvento" value="<%=idEvento%>">

    <div class="form-group">
        <label for="kmLlegada">Kil&oacute;metro de llegada (meta)</label>
        <input type="number" id="kmLlegada" name="kmLlegada" placeholder="Ej: 42" min="0" step="0.1" required>
    </div>

    <div class="puntos-container">
        <div class="puntos-header">
            <h3>Puntos del recorrido</h3>
            <button type="button" class="btn-agregar" id="btnAgregar">+ A&ntilde;adir punto</button>
        </div>
        <div id="puntosLista">
            <div class="empty-message" id="emptyMessage">
                No hay puntos a&ntilde;adidos. Pulsa "+ A&ntilde;adir punto" para incluir intermedios o controles.
            </div>
        </div>
    </div>

    <button type="submit" class="botones">Crear Recorrido</button>
</form>

<style>
.campos-control { display: flex; flex-direction: column; gap: 10px; margin-top: 10px; }
.campos-control label, .campos-intermedio label { font-size: 0.85rem; color: #555; font-weight: 600; }
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
        '<button type="button" class="btn-eliminar" onclick="eliminarPunto(this)">&#215;</button>' +
        '<div class="form-row"><div class="form-group tipo">' +
        '<label>Tipo de punto</label>' +
        '<select name="puntoTipo" class="tipo-select" onchange="cambiarTipo(this)">' +
        '<option value="2">Punto intermedio</option>' +
        '<option value="3">Punto de control</option>' +
        '</select></div></div>' +
        '<div class="campos-intermedio"><div class="form-group">' +
        '<label>Kilómetro</label>' +
        '<input type="number" name="puntoKm" placeholder="Ej: 10" min="0" step="0.1">' +
        '</div></div>' +
        '<div class="campos-control" style="display:none">' +
        '<div class="form-group"><label>Nombre del punto de control</label>' +
        '<input type="text" name="puntoNombre" placeholder="Ej: Control de hidratación"></div>' +
        '<div class="form-group"><label>Descripción</label>' +
        '<textarea name="puntoDesc" placeholder="Describe este punto de control..."></textarea></div>' +
        '<div class="form-group"><label>Tareas asignadas a este punto</label>' +
        '<div class="tareas-control-lista">' + generarCheckboxesTareas() + '</div></div>' +
        '</div>' +
        '<input type="hidden" name="puntoTareas" value="">';
    lista.appendChild(div);
});

function cambiarTipo(select) {
    const item = select.closest('.punto-item');
    const esControl = select.value === '3';
    item.querySelector('.campos-intermedio').style.display = esControl ? 'none' : '';
    item.querySelector('.campos-control').style.display = esControl ? '' : 'none';
    if (esControl) {
        const kmInput = item.querySelector('[name="puntoKm"]');
        if (kmInput) kmInput.value = '';
    } else {
        const nombreInput = item.querySelector('[name="puntoNombre"]');
        const descInput = item.querySelector('[name="puntoDesc"]');
        if (nombreInput) nombreInput.value = '';
        if (descInput) descInput.value = '';
    }
}

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
        const tipo = item.querySelector('[name="puntoTipo"]').value;
        const tareasHidden = item.querySelector('[name="puntoTareas"]');
        if (tipo === '3') {
            const checked = item.querySelectorAll('.tarea-check:checked');
            tareasHidden.value = Array.from(checked).map(cb => cb.value).join(',');
        }
    });
});
</script>
