<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

String accion = (String) request.getAttribute("accion");
%>

<h1>${evento.nombre}</h1>
<div class="menu-evento">
	<a
		href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}&accion=gestionar-voluntarios"
		class="<%="gestionar".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Gestionar voluntarios" : "Manage volunteers"%></a>

	<a
		href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}&accion=editar-evento"
		class="<%="perfil".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Editar evento" : "Edit event"%></a>

	<a
		href="${pageContext.request.contextPath}/evento?id=${evento.idEvento}"
		class="<%="eventos".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "ver evento" : "View event"%></a>
</div>

<section>
	<jsp:include page="/WEB-INF/sections/evento/${eventoView}" />
</section>

