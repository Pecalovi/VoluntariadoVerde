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
			src="src/ojo.png"></a> <a><img src="src/basura.png"></a>
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
						Media: <b>${v.voluntario.media}</b>
					</p>
				</div>
			</div>
		</c:forEach>
	</div>

	<div>
		<h3>Aceptados (${aceptados.size()})</h3>

		<c:forEach var="v" items="${aceptados}">
			<div class="admin-tarjeta voluntarios">

				<div class="info">
					<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

					<button type="button" class="btn btn-outline-secondary"
						onclick="mostrarPerfil(this)">Ver información</button>
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
						Media: <b>${v.voluntario.media}</b>
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
						<input type="hidden" name="idUsuario" value="${v.voluntario.id}" />
						<input type="hidden" name="accionVoluntario" value="aceptar" /> <input
							type="hidden" name="idEvento" value="${param.id}" />
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
</script>
