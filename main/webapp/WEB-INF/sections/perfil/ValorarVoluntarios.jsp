<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

boolean es = "es".equals(lang);

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
		<a href="#" data-bs-toggle="modal"
			data-bs-target="#modalEliminarEvento"> <img src="src/basura.png">
		</a>
	</div>
</div>

<section class="section-evento">
	<h2><%=es ? "Valoración de voluntarios" : "Volunteer rating"%></h2>

	<c:forEach var="v" items="${asignados}">
		<div class="admin-tarjeta voluntarios">

			<div class="info">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

				<div style="display: flex; gap: 10px;">
					<button class="btn btn-success" onclick="mostrarPerfil(this)">
						<%=es ? "Valorar" : "Rate"%>
					</button>
				</div>
			</div>

			<div class="asignar-voluntario" style="display: none;">
				<form action="${pageContext.request.contextPath}/ServPerfil"
					method="post" style="display: flex; gap: 8px; align-items: center;">

					<input type="hidden" name="accion" value="valorar-voluntario" /> <input
						type="hidden" name="idInscripcion" value="${v.idInscripcion}" />
					<input type="hidden" name="idEvento" value="${param.id}" /> <select
						name="valoracion">
						<option disabled selected>
							<%=es ? "Selecciona valoración del voluntario" : "Select a volunteer rating"%>
						</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>

					<button type="submit" class="btn btn-success">
						<%=es ? "Valorar" : "Submit rating"%>
					</button>
				</form>
			</div>
		</div>
	</c:forEach>

	<c:forEach var="v" items="${valorados}">
		<div class="admin-tarjeta voluntarios">

			<div class="info">
				<span>${v.voluntario.nombre} ${v.voluntario.apellidos}</span>

				<div style="display: flex; gap: 10px;">
					<button class="btn btn-success" disabled>
						<%=es ? "Valorado" : "Rated"%>
					</button>
				</div>
			</div>

		</div>
	</c:forEach>
</section>

<script>
	function mostrarPerfil(btn) {
		const tarjeta = btn.closest(".admin-tarjeta");
		const info = tarjeta.querySelector(".asignar-voluntario");

		if (info.style.display === "none" || info.style.display === "") {
			info.style.display = "flex";
		} else {
			info.style.display = "none";
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
		: "This action cannot be undone and will delete all related registrations and checkpoints."%>
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