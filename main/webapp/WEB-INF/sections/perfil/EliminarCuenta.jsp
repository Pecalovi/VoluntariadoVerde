<%@ page language="java" import="model.Usuario" pageEncoding="UTF-8"%>
<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
    lang = "es";
%>
<%
Usuario user = (Usuario) session.getAttribute("usuario");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
%>

<div class="eliminar-cuenta">
    <h2><%=lang.equals("en") ? "Delete my account" : "Eliminar mi cuenta"%></h2>
    <p><%=lang.equals("en") ? "This action is irreversible. All your data will be permanently deleted" : "Esta acción es irreversible. Todos tus datos serán eliminados permanentemente."%></p>
    <form action="<%=request.getContextPath()%>/ServPerfil" method="post">
        <input type="hidden" name="accion" value="eliminar-cuenta" />
        <button type="submit" class="btn btn-danger"><%=lang.equals("en") ? "Delete my account" : "Eliminar mi cuenta"%></button>
    </form>
</div>