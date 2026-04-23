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
<div class="nombre-evento">
	<div>
		<form id="formEvento"
			action="${pageContext.request.contextPath}/ServPerfil" method="post">
			<input type="hidden" name="accion" value="editar-evento" /> <input
				type="hidden" name="id" value="${evento.idEvento}"> <input
				type="text" id="evento_nombre_editar" name="nombre"
				value="${evento.nombre}" readonly oninput="autoSize(this)">

			<button type="button" id="btnEditarEvento"
				style="background: none; border: none; cursor: pointer;"
				onclick="toggleEdicionEvento()">
				<img src="src/editar_nombre.png" alt="Editar nombre">
			</button>
		</form>
	</div>
	<div>
		<a
			href="${pageContext.request.contextPath}/evento?id=${evento.idEvento}"><img
			src="src/ojo.png"></a>
		<a href="#" data-bs-toggle="modal" data-bs-target="#modalEliminarEvento"><img src="src/basura.png"></a>
	</div>
</div>

<section class="section-evento">
	<h2>Solicitudes de voluntarios</h2>

	<div>
		<h3>Pendientes (${pendientes.size()})</h3>

		<c:forEach var="v" items="${pendientes}">
			<div class="admin-tarjeta voluntarios">
				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

					<div style="display: flex; gap: 10px;">

						<button type="submit" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">Ver información</button>

						<form action="${pageContext.request.contextPath}/ServPerfil"
							method="post">
							<input type="hidden" name="accion" value="gestionar-voluntarios" />
							<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
							<input type="hidden" name="accionVoluntario" value="aceptar" />
							<input type="hidden" name="idEvento" value="${param.id}" />
							<button type="submit" class="btn btn-success">Aceptar</button>
						</form>

						<form action="${pageContext.request.contextPath}/ServPerfil"
							method="post">
							<input type="hidden" name="accion" value="gestionar-voluntarios" />
							<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
							<input type="hidden" name="accionVoluntario" value="rechazar" />
							<input type="hidden" name="idEvento" value="${param.id}" />
							<button type="submit" class="btn btn-danger">Rechazar</button>
						</form>

					</div>
				</div>
				<div class="info-voluntario" style="display: none;">
					<p>
						El usuario se registro el <b>${v.voluntario.fecha_registro}</b>
					</p>
					<p>
						¿Tiene vehículo? <b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">Si</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose></b>
					</p>
					<p>
						Discapacidad: <b>${v.voluntario.discapacidadTexto}</b>
					</p>
					<p>
						Valoración media: <b>${v.voluntario.media}</b>
					</p>
				</div>
			</div>
		</c:forEach>
	</div>

	<div>
		<h3>Aceptados (${aceptados.size()})</h3>

		<c:forEach var="v" items="${aceptados}">
			<div class="admin-tarjeta voluntarios ">

				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
					<div style="display: flex; gap: 10px;">
						<button type="button" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">Ver información</button>

						<button type="button" class="btn btn-primary"
							onclick="mostrarAsignacion(this)">Asignar punto de
							control</button>
					</div>
				</div>

				<div class="info-voluntario" style="display: none;">
					<p>
						El usuario se registró el <b>${v.voluntario.fecha_registro}</b>
					</p>

					<p>
						¿Tiene vehículo? <b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">Sí</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</b>
					</p>

					<p>
						Discapacidad: <b>${v.voluntario.discapacidadTexto}</b>
					</p>
					<p>
						Valoración Media: <b>${v.voluntario.media}</b>
					</p>
				</div>

				<div class="asignar-voluntario" style="display: none;">
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">

						<input type="hidden" name="accion" value="asignar-voluntarios" />
						<input type="hidden" name="idInscripcion" value="${v.idInscripcion}" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
						<input type="hidden" name="accionVoluntario" value="asignar" /> <input
							type="hidden" name="idEvento" value="${param.id}" /> <select
							name="puntoControl">
							<option disabled selected>Selecciona un punto de control</option>
							<c:forEach var="p" items="${puntosControl}">
								<option value="${p}">${p}</option>
							</c:forEach>
						</select>

						<button type="submit" class="btn btn-success">Asignar</button>
					</form>
				</div>
			</div>

		</c:forEach>
		<c:forEach var="v" items="${asignados}">
			<div class="admin-tarjeta voluntarios asignados">

				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
					<div style="display: flex; gap: 10px;">
						<button type="button" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">Ver información</button>
					</div>
				</div>

				<div class="info-voluntario" style="display: none;">
					<p>
						El usuario se registró el <b>${v.voluntario.fecha_registro}</b>
					</p>

					<p>
						¿Tiene vehículo? <b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">Sí</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</b>
					</p>

					<p>
						Discapacidad: <b>${v.voluntario.discapacidadTexto}</b>
					</p>
					<p>
						Valoración Media: <b>${v.voluntario.media}</b>
					</p>
				</div>
			</div>

		</c:forEach>
	</div>

	<div>
		<h3>Lista de espera (${espera.size()})</h3>

		<c:forEach var="v" items="${espera}">
			<div class="admin-tarjeta">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

				<div style="display: flex; gap: 10px;">

					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idInscripcion"
							value="${v.idInscripcion}" /> <input type="hidden"
							name="accionVoluntario" value="aceptar" /> <input type="hidden"
							name="idEvento" value="${param.id}" />
						<button type="submit" class="btn btn-success">Aceptar</button>
					</form>

					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
						<input type="hidden" name="accionVoluntario" value="rechazar" />
						<input type="hidden" name="idEvento" value="${param.id}" />
						<button type="submit" class="btn btn-danger">Rechazar</button>
					</form>

				</div>
			</div>
		</c:forEach>
	</div>

	<div>
		<h3>Cancelados (${inactivos.size()})</h3>

		<c:forEach var="v" items="${inactivos}">
			<div>
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>
			</div>
		</c:forEach>
	</div>
</section>

<script>
	function mostrarPerfil(btn) {
		// buscar tarjeta completa
		const tarjeta = btn.closest(".admin-tarjeta");

		// buscar panel info dentro de esa tarjeta
		const info = tarjeta.querySelector(".info-voluntario");

		// toggle
		if (info.style.display === "none" || info.style.display === "") {
			info.style.display = "flex";
		} else {
			info.style.display = "none";
		}
	}

	let editandoEvento = false;

	function toggleEdicionEvento() {

		const form = document.getElementById("formEvento");
		const input = document.getElementById("evento_nombre_editar");

		editandoEvento = !editandoEvento;

		if (editandoEvento) {
			input.removeAttribute("readonly");
			input.focus();
		} else {

			if (!form.checkValidity()) {
				form.reportValidity();
				return;
			}

			form.submit();
		}
	}

	function mostrarAsignacion(btn) {
		const tarjeta = btn.closest(".admin-tarjeta");
		const panel = tarjeta.querySelector(".asignar-voluntario");

		if (panel.style.display === "none" || panel.style.display === "") {
			panel.style.display = "block";
		} else {
			panel.style.display = "none";
		}
	}
</script>

<!-- Modal confirmar eliminación -->
<div class="modal fade" id="modalEliminarEvento" tabindex="-1">
	<div class="modal-dialog" style="margin-top: 5rem;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Eliminar evento</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<p>¿Estás seguro de que quieres eliminar el evento <b>${evento.nombre}</b>?
				Esta acción no se puede deshacer y eliminará todas las inscripciones y puntos de control asociados.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
				<form action="${pageContext.request.contextPath}/ServPerfil" method="post">
					<input type="hidden" name="accion" value="eliminar-evento">
					<input type="hidden" name="idEvento" value="${param.id}">
					<button type="submit" class="btn btn-danger">Eliminar</button>
				</form>
			</div>
		</div>
	</div>
</div>
