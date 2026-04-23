<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>

<div class="tarjeta-perfil">

	<div class="menu-perfil">

		<a href="${pageContext.request.contextPath}/admin?opcion=voluntarios"
			class="${opcion == 'voluntarios' ? 'activo' : ''}"> <img
			src="src/editar.png"> ${lang == 'es' ? 'Gestionar voluntarios' : 'Manage volunteers'}
		</a> <a href="${pageContext.request.contextPath}/admin?opcion=empresas"
			class="${opcion == 'empresas' ? 'activo' : ''}"> <img
			src="src/editar_evento.png"> ${lang == 'es' ? 'Gestionar organizadores' : 'Manage organizers'}
		</a>

	</div>

	<section>

		<c:choose>

			<c:when test="${opcion == 'voluntarios'}">

				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">

						<h2>${item.apellidos}, ${item.nombre}</h2>

						<div>

							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post">
								<input type="hidden" name="accion" value="verDatos" /> <input
									type="hidden" name="idUsuario" value="${item.id}" />

								<button type="submit" class="btn btn-link">${lang == 'es' ? 'Ver datos' : 'View data'}
								</button>
							</form>

							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post">

								<input type="hidden" name="accion" value="eliminarCuenta" /> <input
									type="hidden" name="idUsuario" value="${item.id}" />

								<textarea name="motivo" required
									placeholder="${lang == 'es' ? 'Motivo de la baja...' : 'Reason for deletion...'}"
									class="textarea-admin"></textarea>

								<button type="submit" class="btn btn-danger"
									onclick="return confirm('${lang == 'es' ? '¿Seguro que deseas eliminar esta cuenta?' : 'Are you sure you want to delete this account?'}')">

									${lang == 'es' ? 'Eliminar cuenta' : 'Delete account'}</button>

							</form>

						</div>

					</div>
				</c:forEach>

			</c:when>

			<c:when test="${opcion == 'empresas'}">

				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">

						<h2>${item.empresa}</h2>

						<div>

							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post">

								<input type="hidden" name="accion" value="verDatosEmpresa" /> <input
									type="hidden" name="empresa" value="${item.empresa}" />

								<button type="submit" class="btn btn-link">${lang == 'es' ? 'Organizadores' : 'Organizers'}
									(${item.total})</button>

							</form>

							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post">

								<input type="hidden" name="accion" value="eliminarOrganizacion" />
								<input type="hidden" name="idOrganizador" value="${item.id}" />

								<textarea name="motivo" required
									placeholder="${lang == 'es' ? 'Motivo de la baja...' : 'Reason for deletion...'}"
									class="textarea-admin"></textarea>

								<button type="submit" class="btn btn-danger"
									onclick="return confirm('${lang == 'es' ? '¿Seguro que deseas eliminar esta organización?' : 'Are you sure you want to delete this organization?'}')">

									${lang == 'es' ? 'Eliminar organización' : 'Delete organization'}

								</button>

							</form>

						</div>

					</div>
				</c:forEach>

			</c:when>

			<c:when test="${opcion == 'eventos'}">

				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">
						<p>${item.nombre}</p>
						<p>${item.lugar}</p>
						<p>${item.fecha}</p>
					</div>
				</c:forEach>

			</c:when>

			<c:otherwise>
				<p>${lang == 'es' ? 'Selecciona una opción del menú.' : 'Select an option from the menu.'}</p>
			</c:otherwise>

		</c:choose>


		<!-- MODAL EMPRESA -->
		<c:if test="${not empty sessionScope.empresaDetalle}">

			<div id="modalEmpresa" class="modal-overlay">

				<div class="modal-contenido">

					<div class="modal-header">
						<h3>${lang == 'es' ? 'ORGANIZADORES' : 'ORGANIZERS'} —
							${sessionScope.empresaNombre}</h3>
					</div>

					<div class="modal-body">
						<table>

							<tr>
								<th>${lang == 'es' ? 'Nombre' : 'Name'}</th>
								<th>Email</th>
								<th>${lang == 'es' ? 'Teléfono' : 'Phone'}</th>
							</tr>

							<c:forEach var="org" items="${sessionScope.empresaDetalle}">
								<tr>
									<td>${org.nombre}${org.apellidos}</td>
									<td class="email">${org.email}</td>
									<td>${org.numTelf}</td>
								</tr>
							</c:forEach>

						</table>
					</div>

					<div class="modal-footer">
						<button type="button" onclick="cerrarModalEmpresa()">
							${lang == 'es' ? 'Cerrar' : 'Close'}</button>
					</div>

				</div>
			</div>

			<script>
				function cerrarModalEmpresa() {
					window.location.href = "${pageContext.request.contextPath}/ServAdmin?accion=limpiarDetalleEmpresa";
				}
			</script>

		</c:if>


		<!-- MODAL USUARIO -->
		<c:if
			test="${not empty usuarioDetalle or not empty sessionScope.usuarioDetalle}">

			<c:set var="det"
				value="${not empty usuarioDetalle ? usuarioDetalle : sessionScope.usuarioDetalle}" />

			<div id="miModal" class="modal-overlay">

				<div class="modal-contenido">

					<div class="modal-header">
						<h3>${lang == 'es' ? 'INFORME DE VOLUNTARIO' : 'VOLUNTEER REPORT'}
						</h3>
					</div>

					<div class="modal-body">
						<table>

							<tr>
								<td><b>${lang == 'es' ? 'Nombre' : 'Name'}:</b></td>
								<td>${det.nombre}${det.apellidos}</td>
							</tr>

							<tr>
								<td><b>Email:</b></td>
								<td class="email">${det.email}</td>
							</tr>

							<tr>
								<td><b>${lang == 'es' ? 'Teléfono' : 'Phone'}:</b></td>
								<td>${det.numTelf}</td>
							</tr>

						</table>
					</div>

					<div class="modal-footer">
						<button type="button" onclick="cerrarEInforme()">${lang == 'es' ? 'Cerrar Informe' : 'Close report'}
						</button>
					</div>

				</div>
			</div>

			<script>
				function cerrarEInforme() {
					window.location.href = "${pageContext.request.contextPath}/ServAdmin?accion=limpiarDetalle";
				}
			</script>

		</c:if>

	</section>
</div>