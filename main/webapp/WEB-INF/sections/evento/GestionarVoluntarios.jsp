<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h2>Solicitudes de voluntarios</h2>

	<div>

		<!-- PENDIENTES -->
		<c:set var="pendientes" value="0" />

		<div>
			<h3>
				Solicitudes pendientes (
				<c:forEach var="v" items="${voluntarios}">
					<c:if test="${v.estado == 'Pendiente'}">
						<c:set var="pendientes" value="${pendientes + 1}" />
					</c:if>
				</c:forEach>
				${pendientes} )
			</h3>

			<c:forEach var="v" items="${voluntarios}">
				<c:if test="${v.estado == 'Pendiente'}">
					<span>${v.nombre} ${v.apellidos}</span>
					<!-- ACEPTAR -->
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post" style="display: inline;">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.id}"> <input
							type="hidden" name="accionVoluntario" value="aceptar"> <input
							type="hidden" name="idEvento" value="${param.id}">
						<button type="submit">Aceptar</button>
					</form>

					<!-- LISTA DE ESPERA -->
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post" style="display: inline;">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.id}"> <input
							type="hidden" name="accionVoluntario" value="rechazar"> <input
							type="hidden" name="idEvento" value="${param.id}">
						<button type="submit">Lista de espera</button>
					</form>
					<br />
				</c:if>
			</c:forEach>
		</div>

		<!-- ACEPTADOS -->
		<c:set var="aceptados" value="0" />

		<div>
			<h3>
				Solicitudes aceptadas (
				<c:forEach var="v" items="${voluntarios}">
					<c:if test="${v.estado == 'Aceptado'}">
						<c:set var="aceptados" value="${aceptados + 1}" />
					</c:if>
				</c:forEach>
				${aceptados} )
			</h3>

			<c:forEach var="v" items="${voluntarios}">
				<c:if test="${v.estado == 'Aceptado'}">
					<span>${v.nombre} ${v.apellidos}</span>
					<br />
				</c:if>
			</c:forEach>
		</div>

		<!-- RECHAZADOS -->
		<c:set var="rechazados" value="0" />

		<div>
			<h3>
				Lista de espera (
				<c:forEach var="v" items="${voluntarios}">
					<c:if test="${v.estado == 'Rechazado'}">
						<c:set var="rechazados" value="${rechazados + 1}" />
					</c:if>
				</c:forEach>
				${rechazados} )
			</h3>

			<c:forEach var="v" items="${voluntarios}">
				<c:if test="${v.estado == 'Rechazado'}">
					<span>${v.nombre} ${v.apellidos}</span>
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post" style="display: inline;">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.id}"> <input
							type="hidden" name="accionVoluntario" value="aceptar"> <input
							type="hidden" name="idEvento" value="${param.id}">
						<button type="submit">Aceptar</button>
					</form>
					<br />
				</c:if>
			</c:forEach>
		</div>

		<c:set var="cancelados" value="0" />

		<div>
			<h3>
				Solicitudes canceladas por el voluntario (
				<c:forEach var="v" items="${voluntarios}">
					<c:if test="${v.estado == 'Cancelado'}">
						<c:set var="cancelados" value="${cancelados + 1}" />
					</c:if>
				</c:forEach>
				${cancelados} )
			</h3>

			<c:forEach var="v" items="${voluntarios}">
				<c:if test="${v.estado == 'Cancelado'}">
					<span>${v.nombre} ${v.apellidos}</span>
					<br />
				</c:if>
			</c:forEach>
		</div>

	</div>
</div>