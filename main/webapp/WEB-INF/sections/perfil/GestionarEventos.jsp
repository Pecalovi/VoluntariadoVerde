<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'es'}" />

<c:choose>
	<c:when test="${not empty eventos}">
		<c:forEach var="evento" items="${eventos}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/evento?id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
					<p class="evento-fecha">${evento.fechaFormateada}</p>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No has asistido a ningun evento.
                </c:when>
				<c:otherwise>
                    You have not attended any events.
                </c:otherwise>
			</c:choose>
		</p>
		<a href="${pageContext.request.contextPath}/home"> <c:choose>
				<c:when test="${lang == 'es'}">
                    Apúntate a algun evento
                </c:when>
				<c:otherwise>
                    Sign up for an event
                </c:otherwise>
			</c:choose>
		</a>
	</c:otherwise>
</c:choose> 
<a class="crearevento " href="${pageContext.request.contextPath}/crearevento">+ <span class="tooltiptext">Crear evento</span></a>