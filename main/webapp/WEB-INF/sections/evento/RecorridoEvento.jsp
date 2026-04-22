<%
    Integer idEvento = (Integer) session.getAttribute("idEvento");

    if (idEvento == null) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }
%>

<div class="indicador-pasos">
    <div class="paso completado">
        <span>1</span>
        <p>Datos</p>
    </div>
    <div class="paso completado">
        <span>2</span>
        <p>Tareas</p>
    </div>
    <div class="paso activo">
        <span>3</span>
        <p>Recorrido</p>
    </div>
</div>

<div class="enuncidado">
    <h1>Recorrido del evento</h1>
    <p>Indica el kil&oacute;metro de llegada y a&ntilde;ade los puntos del recorrido.</p>
</div>

<form action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
    <input type="hidden" name="fase" value="2">
    <input type="hidden" name="idEvento" value="<%=session.getAttribute("idEvento")%>">

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

<script>
document.getElementById('btnAgregar').addEventListener('click', function () {
    const lista = document.getElementById('puntosLista');
    const emptyMsg = document.getElementById('emptyMessage');
    if (emptyMsg) emptyMsg.style.display = 'none';

    const div = document.createElement('div');
    div.className = 'punto-item';
    div.innerHTML = `
        <button type="button" class="btn-eliminar" onclick="eliminarPunto(this)">&#215;</button>
        <div class="form-row">
            <div class="form-group tipo">
                <label>Tipo de punto</label>
                <select name="tipoPunto">
                    <option value="2">Punto intermedio</option>
                    <option value="3">Punto de control</option>
                </select>
            </div>
            <div class="form-group km">
                <label>Kil&oacute;metro</label>
                <input type="number" name="kmPunto" placeholder="Ej: 10" min="0" step="0.1" required>
            </div>
        </div>
    `;
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
</script>