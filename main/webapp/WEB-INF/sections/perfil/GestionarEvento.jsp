<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
boolean es = lang.equals("es");

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
				<img src="src/editar_nombre.png"
					alt="<%=es ? "Editar nombre" : "Edit name"%>">
			</button>

		</form>
	</div>

	<div>
		<a
			href="${pageContext.request.contextPath}/evento?id=${evento.idEvento}">
			<img src="src/ojo.png" alt="<%=es ? "Ver" : "View"%>">
		</a> <a href="#" data-bs-toggle="modal"
			data-bs-target="#modalEliminarEvento"> <img src="src/basura.png"
			alt="<%=es ? "Eliminar" : "Delete"%>">
		</a>
	</div>
</div>

<section class="section-evento">

	<h2><%=es ? "Solicitudes de voluntarios" : "Volunteer applications"%></h2>

	<!-- PENDIENTES -->
	<div>
		<h3><%=es ? "Pendientes" : "Pending"%>
			(${pendientes.size()})
		</h3>

		<c:forEach var="v" items="${pendientes}">
			<div class="admin-tarjeta voluntarios">
				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

					<div style="display: flex; gap: 10px;">

						<button type="button" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">
							<%=es ? "Ver información" : "View info"%>
						</button>

						<form action="${pageContext.request.contextPath}/ServPerfil"
							method="post">
							<input type="hidden" name="accion" value="gestionar-voluntarios" />
							<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
							<input type="hidden" name="accionVoluntario" value="aceptar" />
							<input type="hidden" name="idEvento" value="${param.id}" />

							<button type="submit" class="btn btn-success">
								<%=es ? "Aceptar" : "Accept"%>
							</button>
						</form>

						<form action="${pageContext.request.contextPath}/ServPerfil"
							method="post">
							<input type="hidden" name="accion" value="gestionar-voluntarios" />
							<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
							<input type="hidden" name="accionVoluntario" value="rechazar" />
							<input type="hidden" name="idEvento" value="${param.id}" />

							<button type="submit" class="btn btn-danger">
								<%=es ? "Rechazar" : "Reject"%>
							</button>
						</form>

					</div>
				</div>

				<div class="info-voluntario" style="display: none;">
					<p>
						<%=es ? "Registrado el" : "Registered on"%>
						<b>${v.voluntario.fecha_registro}</b>
					</p>

					<p>
						<%=es ? "¿Tiene vehículo?" : "Has vehicle?"%>
						<b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">
									<%=es ? "Sí" : "Yes"%>
								</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</b>
					</p>

					<p>
						<%=es ? "Discapacidad" : "Disability"%>: <b>${v.voluntario.getDiscapacidadTexto(lang)}</b>
					</p>

					<p>
						<%=es ? "Valoración media" : "Average rating"%>: <b>${v.voluntario.media}</b>
					</p>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- ACEPTADOS -->
	<div>
		<h3><%=es ? "Aceptados" : "Accepted"%>
			(${aceptados.size()})
		</h3>

		<c:forEach var="v" items="${aceptados}">
			<div class="admin-tarjeta voluntarios">

				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

					<div style="display: flex; gap: 10px;">
						<button type="button" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">
							<%=es ? "Ver información" : "View info"%>
						</button>

						<button type="button" class="btn btn-primary"
							onclick="mostrarAsignacion(this)">
							<%=es ? "Asignar punto de control" : "Assign checkpoint"%>
						</button>
					</div>
				</div>

				<div class="info-voluntario" style="display: none;">
					<p>
						<%=es ? "Registrado el" : "Registered on"%>
						<b>${v.voluntario.fecha_registro}</b>
					</p>

					<p>
						<%=es ? "¿Tiene vehículo?" : "Has vehicle?"%>
						<b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">
									<%=es ? "Sí" : "Yes"%>
								</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</b>
					</p>

					<p>
						<%=es ? "Discapacidad" : "Disability"%>: <b>${v.voluntario.getDiscapacidadTexto(lang)}</b>
					</p>

					<p>
						<%=es ? "Valoración media" : "Average rating"%>: <b>${v.voluntario.media}</b>
					</p>
				</div>

				<div class="asignar-voluntario" style="display: none;">
					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">

						<input type="hidden" name="accion" value="asignar-voluntarios" />
						<input type="hidden" name="idInscripcion"
							value="${v.idInscripcion}" /> <input type="hidden"
							name="idUsuario" value="${v.voluntario.id}" /> <input
							type="hidden" name="accionVoluntario" value="asignar" /> <input
							type="hidden" name="idEvento" value="${param.id}" /> <select
							name="puntoControl">
							<option disabled selected>
								<%=es ? "Selecciona un punto de control" : "Select a checkpoint"%>
							</option>

							<c:forEach var="p" items="${puntosControl}">
								<option value="${p}">${p}</option>
							</c:forEach>
						</select>

						<button type="submit" class="btn btn-success">
							<%=es ? "Asignar" : "Assign"%>
						</button>
					</form>
				</div>

			</div>
		</c:forEach>
	</div>

	<!-- ASIGNADOS -->
	<div>
		<h3><%=es ? "Asignados" : "Assigned"%>
			(${asignados.size()})
		</h3>

		<c:forEach var="v" items="${asignados}">
			<div class="admin-tarjeta voluntarios asignados">

				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

					<div style="display: flex; gap: 10px;">
						<button type="button" class="btn btn-outline-secondary"
							onclick="mostrarPerfil(this)">
							<%=es ? "Ver información" : "View info"%>
						</button>

						<button class="btn btn-success" disabled>
							<%=es ? "Asignado" : "Assigned"%>
						</button>
					</div>
				</div>
				<div class="info-voluntario" style="display: none;">
					<p>
						<%=es ? "Registrado el" : "Registered on"%>
						<b>${v.voluntario.fecha_registro}</b>
					</p>

					<p>
						<%=es ? "¿Tiene vehículo?" : "Has vehicle?"%>
						<b> <c:choose>
								<c:when test="${v.voluntario.vehiculo}">
									<%=es ? "Sí" : "Yes"%>
								</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</b>
					</p>

					<p>
						<%=es ? "Discapacidad" : "Disability"%>: <b>${v.voluntario.getDiscapacidadTexto(lang)}</b>
					</p>

					<p>
						<%=es ? "Valoración media" : "Average rating"%>: <b>${v.voluntario.media}</b>
					</p>
				</div>

			</div>
		</c:forEach>
	</div>

	<!-- LISTA ESPERA -->
	<div>
		<h3><%=es ? "Lista de espera" : "Waiting list"%>
			(${espera.size()})
		</h3>

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

						<button type="submit" class="btn btn-success">
							<%=es ? "Aceptar" : "Accept"%>
						</button>
					</form>

					<form action="${pageContext.request.contextPath}/ServPerfil"
						method="post">
						<input type="hidden" name="accion" value="gestionar-voluntarios" />
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
						<input type="hidden" name="accionVoluntario" value="rechazar" />
						<input type="hidden" name="idEvento" value="${param.id}" />

						<button type="submit" class="btn btn-danger">
							<%=es ? "Rechazar" : "Reject"%>
						</button>
					</form>

				</div>
			</div>
		</c:forEach>
	</div>

	<!-- CANCELADOS -->
	<div>
		<h3><%=es ? "Cancelados" : "Cancelled"%>
			(${inactivos.size()})
		</h3>

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
				<h5 class="modal-title">
					<%=es ? "Eliminar evento" : "Delete event"%>
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>

			<div class="modal-body">
				<p>
					<%=es ? "¿Estás seguro de que quieres eliminar el evento" : "Are you sure you want to delete the event"%>
					<b>${evento.nombre}</b>?
					<%=es ? "Esta acción no se puede deshacer y eliminará todas las inscripciones y puntos de control asociados."
		: "This action cannot be undone and will delete all registrations and associated checkpoints."%>
				</p>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">
					<%=es ? "Cancelar" : "Cancel"%>
				</button>

				<form action="${pageContext.request.contextPath}/ServPerfil"
					method="post">
					<input type="hidden" name="accion" value="eliminar-evento">
					<input type="hidden" name="idEvento" value="${param.id}">
					<button type="submit" class="btn btn-danger">
						<%=es ? "Eliminar" : "Delete"%>
					</button>
				</form>
			</div>

		</div>
	</div>
</div>
