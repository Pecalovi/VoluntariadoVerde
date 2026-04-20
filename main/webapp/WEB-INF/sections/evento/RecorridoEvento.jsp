<%
    // Obtener idEvento de la sesi�n
    Integer idEvento = (Integer) session.getAttribute("idEvento");
    
    // Si no existe, redirige al home
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
            <div class="paso activo">
                <span>2</span>
                <p>Recorrido</p>
            </div>
        </div>

        <div class="enuncidado">
            <h1>Recorrido del evento</h1>
            <p>Indica el kilometro de la meta y puntos intermedios con su kilometraje.</p>
        </div>

        <form action="<%=request.getContextPath()%>/ServCrearEvento" method="POST">
        <input type="hidden" name="fase" value="2">
        <input type="hidden" name="idEvento" value="<%=session.getAttribute("idEvento")%>">

            <div class="form-row">
                <div class="form-group">
                    <label for="kmLlegada">N� km de Llegada</label>
                    <input type="number" id="kmLlegada" name="kmLlegada" placeholder="Ej: 42" min="0" step="0.1" required>
                </div>
            </div>

            <div class="puntos-container">
                <div class="puntos-header">
                    <h3>Puntos Intermedios</h3>
                    <button type="button" class="btn-agregar" id="btnAgregar">+ Agregar Punto</button>
                </div>
                <div id="puntosLista">
                    <div class="empty-message" id="emptyMessage">
                        No hay puntos intermedios. Haz clic en " + Agregar Punto" para anadir uno.
                    </div>
                </div>
            </div>

            <button type="submit" class="botones">Crear Recorrido</button>
        </form>