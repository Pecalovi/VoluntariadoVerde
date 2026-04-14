<%
    // Get idEvento from session
    Integer idEvento = (Integer) session.getAttribute("idEvento");
    
    // If it doesn't exist, redirect to home
    if (idEvento == null) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }
%>

<div class="indicador-pasos">
    <div class="paso completado">
        <span>1</span>
        <p>Details</p>
    </div>
    <div class="paso completado">
        <span>2</span>
        <p>Description</p>
    </div>
    <div class="paso activo">
        <span>3</span>
        <p>Route</p>
    </div>
</div>

<div class="enuncidado">
    <h1>Event route</h1>
    <p>Indicate the start, finish, and intermediate points with their distances.</p>
</div>

<form action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
    <input type="hidden" name="fase" value="3">
    <input type="hidden" name="idEvento" value="<%=session.getAttribute("idEvento")%>">

    <div class="form-row">
        <div class="form-group">
            <label for="kmSalida">Start km</label>
            <input type="number" id="kmSalida" name="kmSalida" placeholder="0" min="0" step="0.1" required>
        </div>
        <div class="form-group">
            <label for="kmLlegada">Finish km</label>
            <input type="number" id="kmLlegada" name="kmLlegada" placeholder="e.g. 42" min="0" step="0.1" required>
        </div>
    </div>

    <div class="puntos-container">
        <div class="puntos-header">
            <h3>Intermediate points</h3>
            <button type="button" class="btn-agregar" id="btnAgregar">+ Add point</button>
        </div>
        <div id="puntosLista">
            <div class="empty-message" id="emptyMessage">
                There are no intermediate points. Click "+ Add point" to add one.
            </div>
        </div>
    </div>

    <button type="submit" class="botones">Create route</button>
</form>
