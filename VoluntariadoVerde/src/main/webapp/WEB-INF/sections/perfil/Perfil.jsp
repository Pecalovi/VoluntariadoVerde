<%@ page isELIgnored="false" language="java" import="model.Usuario, model.Organizador"
	pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
boolean esOrganizador = (user != null && user instanceof Organizador);
%>

<div class="tarjeta-perfil">
	<div class="menu-perfil">
		<a href="<%=request.getContextPath()%>/perfil?opcion=perfil" class="activo"><img src="src/editar.png"> <%=lang.equals("es") ? "Perfil" : "Profile"%></a>
		<a href="<%=request.getContextPath()%>/perfil?opcion=mis-eventos"><img src="src/evento.png"> <%=lang.equals("es") ? "Mis eventos" : "My events"%></a>

		<%if (esOrganizador) {%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=gestionar-eventos"><img src="src/editar_evento.png"> <%=lang.equals("es") ? "Gestionar eventos" : "Manage events"%></a>
		<%}%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=eliminar-cuenta" class="eliminar"><img src="src/editar.png"> <%=lang.equals("es") ? "Eliminar cuenta" : "Delete account"%></a>
	</div>

	<section>
		<jsp:include page="/WEB-INF/sections/perfil/${perfilView}" />
	</section>
</div>