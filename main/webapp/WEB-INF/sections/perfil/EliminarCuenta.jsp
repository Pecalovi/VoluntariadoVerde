<%@ page language="java" import="model.Usuario" pageEncoding="UTF-8"%>
<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
    lang = "es";

Usuario user = (Usuario) session.getAttribute("usuario");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}

String errorEliminar = (String) session.getAttribute("errorEliminar");
session.removeAttribute("errorEliminar");
%>

<div class="eliminar-cuenta">
    <h2><%=lang.equals("en") ? "Delete my account" : "Eliminar mi cuenta"%></h2>
    <p><%=lang.equals("en") ? "This action is irreversible. All your data will be permanently deleted." : "Esta acción es irreversible. Todos tus datos serán eliminados permanentemente."%></p>

    <% if (errorEliminar != null) { %>
        <p class="error-msg"><%=errorEliminar%></p>
    <% } %>

    <form action="<%=request.getContextPath()%>/ServPerfil" method="post">
        <input type="hidden" name="accion" value="eliminar-cuenta" />
        <label for="passConfirm"><%=lang.equals("en") ? "Confirm your password" : "Confirma tu contraseña"%></label>
        <input type="password" id="passConfirm" name="passConfirm"
               placeholder="<%=lang.equals("en") ? "Your password" : "Tu contraseña"%>" required />
        <button type="submit" class="btn btn-danger"><%=lang.equals("en") ? "Delete my account" : "Eliminar mi cuenta"%></button>
    </form>
</div>

<style>
.eliminar-cuenta { max-width: 480px; }
.eliminar-cuenta form { display: flex; flex-direction: column; gap: 12px; margin-top: 24px; }
.eliminar-cuenta label { font-weight: 600; font-size: 0.9rem; color: #333; }
.error-msg { color: #dc3545; font-size: 0.9rem; margin-top: 8px; }
.btn-danger {
    background-color: #dc3545;
    color: white;
    border: none;
    padding: 12px 24px;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s;
    margin-top: 8px;
}
.btn-danger:hover { background-color: #c82333; }
</style>