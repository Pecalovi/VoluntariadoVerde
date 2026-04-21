<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="tarjeta-perfil">
	<div class="menu-perfil">
		<a href="${pageContext.request.contextPath}/admin?opcion=voluntarios"
			class="${opcion == 'voluntarios' ? 'activo' : ''}"> <img
			src="src/editar.png"> ${lang == 'es' ? 'Gestionar voluntarios' : 'Manage volunteers'}
		</a> <a href="${pageContext.request.contextPath}/admin?opcion=empresas"
			class="${opcion == 'empresas' ? 'activo' : ''}"> <img
			src="src/editar_evento.png"> ${lang == 'es' ? 'Gestionar organizadores' : 'Manage organizers'}
		</a> <a href="${pageContext.request.contextPath}/admin?opcion=eventos"
			class="${opcion == 'eventos' ? 'activo' : ''}"> <img
			src="src/evento.png"> ${lang == 'es' ? 'Gestionar eventos' : 'Manage events'}
		</a>
	</div>

	<section>
		<c:choose>
			<c:when test="${opcion == 'voluntarios'}">
				<c:forEach var="item" items="${tablas}">
					<div class="admin-tarjeta">
						<h2>${item.apellidos},${item.nombre}</h2>
						<div>
							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post" style="display: inline;">
								<input type="hidden" name="accion" value="verDatos" /> <input
									type="hidden" name="idUsuario" value="${item.id}" />
								<button type="submit" class="btn btn-link">Ver datos</button>
							</form>

							<form action="${pageContext.request.contextPath}/ServAdmin"
								method="post" style="margin-top: 10px;">
								<input type="hidden" name="accion" value="eliminarCuenta" /> <input
									type="hidden" name="idUsuario" value="${item.id}" />

								<textarea name="motivo" required
									placeholder="Motivo de la baja..."
									style="width: 100%; display: block; margin-bottom: 5px;"></textarea>

								<button type="submit" class="btn btn-danger"
									onclick="return confirm('¿Seguro que deseas eliminar esta cuenta?')">
									Eliminar cuenta</button>
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
							<button type="button" class="btn btn-link">
								Organizadores (${item.total})</button>
							<button type="button" class="btn btn-danger">Eliminar
								organización</button>
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
				<p>Selecciona una opción del menú.</p>
			</c:otherwise>

		</c:choose>
		
		
		
		<c:if test="${not empty usuarioDetalle or not empty sessionScope.usuarioDetalle}">
    <c:set var="det" value="${not empty usuarioDetalle ? usuarioDetalle : sessionScope.usuarioDetalle}" />

    <div id="miModal" class="modal-overlay" style="display: flex; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.7); z-index: 9999; justify-content: center; align-items: center;">
        <div class="modal-contenido" style="background: white; padding: 0; border-radius: 8px; width: 450px; box-shadow: 0 4px 20px rgba(0,0,0,0.5); font-family: Arial, sans-serif;">
            
            <div style="background: #005900; padding: 15px; border-radius: 8px 8px 0 0; text-align: center;">
                <h3 style="margin: 0; color: white;">INFORME DE VOLUNTARIO</h3>
            </div>

            <div style="padding: 20px;">
                <table style="width: 100%; border-collapse: collapse;">
                    <tr style="border-bottom: 1px solid #eee;">
                        <td style="padding: 10px; font-weight: bold; color: #666;">ID:</td>
                        <td style="padding: 10px;">${det.id}</td>
                    </tr>
                    <tr style="border-bottom: 1px solid #eee;">
                        <td style="padding: 10px; font-weight: bold; color: #666;">Nombre:</td>
                        <td style="padding: 10px;">${det.nombre} ${det.apellidos}</td>
                    </tr>
                    <tr style="border-bottom: 1px solid #eee;">
                        <td style="padding: 10px; font-weight: bold; color: #666;">Email:</td>
                        <td style="padding: 10px; color: #2980b9;">${det.email}</td>
                    </tr>
                    <tr style="border-bottom: 1px solid #eee;">
                        <td style="padding: 10px; font-weight: bold; color: #666;">Teléfono:</td>
                        <td style="padding: 10px;">${det.numTelf}</td>
                    </tr>
                    <tr>
                        <td style="padding: 10px; font-weight: bold; color: #666;">Vehículo:</td>
                        <td style="padding: 10px;">${det.vehiculo ? 'Disponible' : 'No dispone'}</td>
                    </tr>
                </table>
            </div>

            <div style="padding: 15px; text-align: center; background: #f9f9f9; border-radius: 0 0 8px 8px;">
                <button type="button" onclick="cerrarEInforme()" style="background: #2c3e50; color: white; border: none; padding: 10px 25px; border-radius: 4px; cursor: pointer;">
                    Cerrar Informe
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