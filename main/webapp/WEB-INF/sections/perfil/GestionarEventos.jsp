<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="lang"
	value="${sessionScope.lang != null ? sessionScope.lang : 'es'}" />

<h1>
	<c:choose>
		<c:when test="${lang == 'es'}">
            Selecciona un evento para gestionarlo
        </c:when>
		<c:otherwise>
            Select an event to manage
        </c:otherwise>
	</c:choose>
</h1>

<h2>
	<c:choose>
		<c:when test="${lang == 'es'}">
            Eventos en proceso
        </c:when>
		<c:otherwise>
            Ongoing events
        </c:otherwise>
	</c:choose>
</h2>

<c:choose>
	<c:when test="${not empty enProceso}">
		<c:forEach var="evento" items="${enProceso}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/perfil?opcion=gestionar-evento&id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
					<button class="btn btn-warning" disabled>
						<c:choose>
							<c:when test="${lang == 'es'}">
                                En proceso
                            </c:when>
							<c:otherwise>
                                In progress
                            </c:otherwise>
						</c:choose>
					</button>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No tienes eventos en proceso.
                </c:when>
				<c:otherwise>
                    You don't have any ongoing events.
                </c:otherwise>
			</c:choose>
		</p>
	</c:otherwise>
</c:choose>

<h2>
	<c:choose>
		<c:when test="${lang == 'es'}">
            Eventos finalizados
        </c:when>
		<c:otherwise>
            Completed events
        </c:otherwise>
	</c:choose>
</h2>

<c:choose>
	<c:when test="${not empty finalizados}">
		<c:forEach var="evento" items="${finalizados}">
			<div class="evento-tarjeta">
				<a
					href="${pageContext.request.contextPath}/perfil?opcion=valorar-voluntarios&id=${evento.idEvento}">
					<h2>${evento.nombre}</h2>
					<button class="btn btn-dark" disabled>
						<c:choose>
							<c:when test="${lang == 'es'}">
                                Finalizado
                            </c:when>
							<c:otherwise>
                                Completed
                            </c:otherwise>
						</c:choose>
					</button>
				</a>
			</div>
		</c:forEach>
	</c:when>

	<c:otherwise>
		<p>
			<c:choose>
				<c:when test="${lang == 'es'}">
                    No tienes eventos finalizados.
                </c:when>
				<c:otherwise>
                    You don't have any completed events.
                </c:otherwise>
			</c:choose>
		</p>
	</c:otherwise>
</c:choose>

<a class="crearevento"
	href="${pageContext.request.contextPath}/crearevento"> + <span
	class="tooltiptext"> <c:choose>
			<c:when test="${lang == 'es'}">
                Crear evento
            </c:when>
			<c:otherwise>
                Create new event
            </c:otherwise>
		</c:choose>
</span>
</a>