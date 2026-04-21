<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

String accion = (String) request.getAttribute("accion");
%>

<h1 id="evento_nombre_editar">${evento.nombre}</h1>
<div class="menu-evento">
	<a
		href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}&accion=gestionar-voluntarios"
		class="<%="voluntarios".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Gestionar voluntarios" : "Manage volunteers"%></a>

	<a
		href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}&accion=editar-evento"
		class="<%="editar".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Editar evento" : "Edit event"%></a>

	<a
		href="${pageContext.request.contextPath}/evento?id=${evento.idEvento}"
		class="<%="eventos".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Visualizar evento" : "View event"%></a>
	<a
		href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}&accion=eliminar-evento"
		class="<%="eliminar-evento".equals(accion) ? "activo" : ""%>"><%=lang.equals("es") ? "Eliminar evento" : "View event"%></a>
</div>

<section>
	<c:choose>

		<c:when test="${empty eventoView}">
			<div class="mensaje-seleccion">Selecciona una opción del menú
				para comenzar</div>
		</c:when>

		<c:otherwise>
			<jsp:include page="/WEB-INF/sections/evento/${eventoView}" />
		</c:otherwise>

	</c:choose>
</section>
