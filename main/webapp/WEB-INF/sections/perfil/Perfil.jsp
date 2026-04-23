<%@ page isELIgnored="false" language="java"
	import="model.Usuario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
boolean esOrganizador = (user != null && user instanceof Organizador);

String opcion = (String) request.getAttribute("opcion");
%>

<h1 class="miPerfil">Mi perfil</h1>

<div class="tarjeta-perfil">
	<div class="menu-perfil">
		<a href="<%=request.getContextPath()%>/perfil?opcion=perfil"
			class="<%="perfil".equals(opcion) ? "activo" : ""%>"><img
			src="src/editar.png"> <%=lang.equals("es") ? "Editar perfil" : "Edit profile"%></a>
		<%if (esOrganizador) {%>
		<a
			href="<%=request.getContextPath()%>/perfil?opcion=gestionar-eventos"
			class="<%="gestionar".equals(opcion) ? "activo" : ""%>"><img
			src="src/editar_evento.png"> <%=lang.equals("es") ? "Gestionar eventos" : "Manage events"%></a>
		<%
		} else {
		%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=mis-eventos"
			class="<%="eventos".equals(opcion) ? "activo" : ""%>"><img
			src="src/evento.png"> <%=lang.equals("es") ? "Mis eventos" : "My events"%></a>
		<%
		}
		%>
		<a href="<%=request.getContextPath()%>/perfil?opcion=eliminar-cuenta"
			class="eliminar <%="eliminar".equals(opcion) ? "activo" : ""%>"><img
			src="src/borrar-usuario.png"> <%=lang.equals("es") ? "Eliminar cuenta" : "Delete account"%></a>

		<div class="info-sostenibilidad-wrapper">
			<button class="btn-info-sostenibilidad" data-bs-toggle="modal"
				data-bs-target="#modalSostenibilidad"
				title="Documentos de sostenibilidad">i</button>
		</div>
	</div>

	<!-- Modal sostenibilidad -->
	<div class="modal fade" id="modalSostenibilidad" tabindex="-1">
		<div class="modal-dialog" style="margin-top: 5rem;">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title"><%=lang.equals("es") ? "Documentos de sostenibilidad" : "Sustainability documents"%></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<p><%=lang.equals("es") ? "Consulta nuestros documentos de sostenibilidad:"
		: "Check out our sustainability documents:"%></p>
					<ul class="lista-docs">
						<li><a
							href="<%=request.getContextPath()%>/sostenibilidad?doc=guia"
							target="_blank"><%=lang.equals("es") ? "Guía de Sostenibilidad" : "Sustainability Guide"%></a></li>
						<li><a
							href="<%=request.getContextPath()%>/sostenibilidad?doc=prl"
							target="_blank"><%=lang.equals("es") ? "Prevención de Riesgos Laborales (PRL)" : "Occupational Health and Safety (OHS)"%></a></li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal"><%=lang.equals("es") ? "Cerrar" : "Close"%></button>
				</div>
			</div>
		</div>
	</div>

	<section>
		<jsp:include page="/WEB-INF/sections/perfil/${perfilView}" />
	</section>
</div>