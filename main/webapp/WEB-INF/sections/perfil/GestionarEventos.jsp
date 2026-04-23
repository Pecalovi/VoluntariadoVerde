<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'es'}" />
<h1>Selecciona un evento para gestionarlo</h1>
<c:choose>
	<c:when test="${not empty enProceso}">
		<h2>Eventos en proceso</h2>
		<c:forEach var="evento" items="${enProceso}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
					<button class="btn btn-warning" disabled>En proceso</button>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No hay eventos en proceso.
                </c:when>
				<c:otherwise>
                    You have not events in process.
                </c:otherwise>
			</c:choose>
		</p>
		<a href="${pageContext.request.contextPath}/crearevento"> <c:choose>
				<c:when test="${lang == 'es'}">
                    Crea tu primer evento
                </c:when>
				<c:otherwise>
                    Create your first event
                </c:otherwise>
			</c:choose>
		</a>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${not empty finalizados}">
		<h2>Eventos finalizados</h2>
		<c:forEach var="evento" items="${finalizados}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/perfil?opcion=valorar-voluntarios&id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
					<button class="btn btn-dark" disabled>Finalizado</button>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No has creado ningun evento finalizado.
                </c:when>
				<c:otherwise>
                    You have not any finished event.
                </c:otherwise>
			</c:choose>
		</p>
	</c:otherwise>
</c:choose>
<a class="crearevento "
	href="${pageContext.request.contextPath}/crearevento">+ <span
	class="tooltiptext">Crear evento</span></a>