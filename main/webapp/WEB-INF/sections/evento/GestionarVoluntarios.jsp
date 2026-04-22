<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Solicitudes de voluntarios</h2>

<div>
	<c:set var="pendientes" value="0" />
	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Pendiente'}">
			<c:set var="pendientes" value="${pendientes + 1}" />
		</c:if>
	</c:forEach>

	<h3>Pendientes (${pendientes})</h3>

	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Pendiente'}">
			<div class="admin-tarjeta">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
				<div style="display: flex; gap: 10px;">
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="verPerfil">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-outline-secondary">Ver perfil</button>
					</form>
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="aceptar">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-success">Aceptar</button>
					</form>
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="rechazar">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-danger">Rechazar</button>
					</form>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>

<div>
	<c:set var="aceptados" value="0" />
	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Aceptado'}">
			<c:set var="aceptados" value="${aceptados + 1}" />
		</c:if>
	</c:forEach>

	<h3>Aceptados (${aceptados})</h3>

	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Aceptado'}">
			<div class="admin-tarjeta">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
				<div style="display: flex; gap: 10px;">
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="verPerfil">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-outline-secondary">Ver perfil</button>
					</form>
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="aceptar">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-warning">Asignar</button>
					</form>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>

<div>
	<c:set var="espera" value="0" />
	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Lista de espera'}">
			<c:set var="espera" value="${espera + 1}" />
		</c:if>
	</c:forEach>

	<h3>Lista de espera (${espera})</h3>

	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Lista de espera'}">
			<div class="admin-tarjeta">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
				<div style="display: flex; gap: 10px;">
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="aceptar">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-success">Aceptar</button>
					</form>
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}">
						<input type="hidden" name="accionVoluntario" value="rechazar">
						<input type="hidden" name="idEvento" value="${param.id}">
						<button type="submit" class="btn btn-danger">Rechazar</button>
					</form>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>

<div>
	<c:set var="totalInactivos" value="0" />

	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Rechazado' || v.estado == 'Cancelado'}">
			<c:set var="totalInactivos" value="${totalInactivos + 1}" />
		</c:if>
	</c:forEach>

	<h3>Cancelados (${totalInactivos})</h3>

	<c:forEach var="v" items="${voluntarios}">
		<c:if test="${v.estado == 'Rechazado' || v.estado == 'Cancelado'}">
			<div>
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
			</div>
		</c:if>
	</c:forEach>
</div>