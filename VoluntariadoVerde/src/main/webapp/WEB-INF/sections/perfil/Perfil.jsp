<%@ page isELIgnored="false" language="java" import="model.Usuario, model.Organizador"
	pageEncoding="UTF-8"%>

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
		<a href="<%=request.getContextPath()%>/perfil?opcion=perfil" class="<%= "perfil".equals(opcion) ? "activo" : "" %>"><img src="src/editar.png"> <%=lang.equals("es") ? "Perfil" : "Profile"%></a>
		<a href="<%=request.getContextPath()%>/perfil?opcion=mis-eventos" class="<%= "eventos".equals(opcion) ? "activo" : "" %>"><img src="src/evento.png"> <%=lang.equals("es") ? "Mis eventos" : "My events"%></a>

		<%if (esOrganizador) {%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=gestionar-eventos" class="<%= "gestionar".equals(opcion) ? "activo" : "" %>"><img src="src/editar_evento.png"> <%=lang.equals("es") ? "Gestionar eventos" : "Manage events"%></a>
		<%}%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=eliminar-cuenta" class="eliminar <%= "eliminar".equals(opcion) ? "activo" : "" %>"><img src="src/borrar-usuario.png"> <%=lang.equals("es") ? "Eliminar cuenta" : "Delete account"%></a>
	</div>

	<section>
		<jsp:include page="/WEB-INF/sections/perfil/${perfilView}" />
	</section>
</div>