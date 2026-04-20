<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'es'}" />

<c:choose>
	<c:when test="${not empty eventos}">
		<c:forEach var="evento" items="${eventos}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No has creado ningun evento.
                </c:when>
				<c:otherwise>
                    You have not created any events.
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
<a class="crearevento " href="${pageContext.request.contextPath}/crearevento">+ <span class="tooltiptext">Crear evento</span></a>