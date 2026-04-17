<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
boolean esOrganizador = (user != null && user instanceof Organizador);

String opcion = (String) request.getAttribute("opcion");
%>

<div class="tarjeta-perfil">
	<div class="menu-perfil">
		<a href="<%=request.getContextPath()%>/admin?opcion=voluntarios"
			class="<%="perfil".equals(opcion) ? "activo" : ""%>"><img
			src="src/editar.png"> <%=lang.equals("es") ? "Gestionar voluntarios" : "Edit profile"%></a>
		<a href="<%=request.getContextPath()%>/admin?opcion=organizadores"
			class="<%="gestionar".equals(opcion) ? "activo" : ""%>"><img
			src="src/editar_evento.png"> <%=lang.equals("es") ? "Gestionar organizadores" : "Manage events"%></a>
		<a href="<%=request.getContextPath()%>/admin?opcion=eventos"
			class="<%="eventos".equals(opcion) ? "activo" : ""%>"><img
			src="src/evento.png"> <%=lang.equals("es") ? "Gestionar eventos" : "My events"%></a>
	</div>

	<section>
		<%=request.getAttribute("tablas") %>
	</section>
</div>