<%@ page language="java" import="model.Usuario" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
%>

<div class="eliminar-cuenta">
    <h2>Eliminar mi cuenta</h2>
    <p>Esta acción es irreversible. Todos tus datos serán eliminados permanentemente.</p>
    
    <form action="<%=request.getContextPath()%>/ServPerfil" method="post">
        <input type="hidden" name="accion" value="eliminar-cuenta" />
        <button type="submit" class="btn btn-danger">Eliminar mi cuenta</button>
    </form>
</div>