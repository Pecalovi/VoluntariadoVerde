<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="tarjeta-perfil">
	<div class="menu-perfil">
		<a href="${pageContext.request.contextPath}/admin?opcion=voluntarios"
			class="${opcion == 'voluntarios' ? 'activo' : ''}"> <img
			src="src/editar.png"> ${lang == 'es' ? 'Gestionar voluntarios' : 'Manage volunteers'}
		</a> <a
			href="${pageContext.request.contextPath}/admin?opcion=organizadores"
			class="${opcion == 'organizadores' ? 'activo' : ''}"> <img
			src="src/editar_evento.png"> ${lang == 'es' ? 'Gestionar organizadores' : 'Manage organizers'}
		</a> <a href="${pageContext.request.contextPath}/admin?opcion=eventos"
			class="${opcion == 'eventos' ? 'activo' : ''}"> <img
			src="src/evento.png"> ${lang == 'es' ? 'Gestionar eventos' : 'Manage events'}
		</a>
	</div>

	<section>
		<c:choose>
			<c:when test="${empty tablas}">
				<p>No hay <c:out value="${opcion}" />.</p>
			</c:when>

			<c:when test="${opcion == 'voluntarios'}">
				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">
						<h2>${item.id}. ${item.apellidos}, ${item.nombre}</h2>
						<div>
							<button type="button" class="btn btn-link">Ver datos</button>
							<button type="button" class="btn btn-danger">Eliminar
								cuenta</button>
						</div>
					</div>
				</c:forEach>
			</c:when>

			<c:when test="${opcion == 'organizadores'}">
				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">
						<h2>${item.entidad}</h2>
						<div>
							<button type="button" class="btn btn-link">Ver
								organizadores</button>
							<button type="button" class="btn btn-danger">Eliminar
								organización</button>
						</div>
					</div>
				</c:forEach>
			</c:when>

			<c:when test="${opcion == 'eventos'}">
				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">
						<p>${item.nombre}</p>
						<p>${item.lugar}</p>
						<p>${item.fecha}</p>
					</div>
				</c:forEach>
			</c:when>

			<c:otherwise>
				<p>Selecciona una opción del menú.</p>
			</c:otherwise>

		</c:choose>
	</section>
</div>