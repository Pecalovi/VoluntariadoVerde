<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>

<div class="selector">

	<div class="titulo">
		<h2>
			<%=lang.equals("es") ? "Elige una opción" : "Choose an option"%>
		</h2>
	</div>

	<div class="cajas">

		<a href="<%=request.getContextPath()%>/registrovoluntario"
			class="rol-link">
			<div class="btn-rol">
				<img class="icono" src="src/trabajo-voluntario.png"
					alt="<%=lang.equals("es") ? "Voluntario/a" : "Volunteer"%>">

				<h3>
					<%=lang.equals("es") ? "Voluntario/a" : "Volunteer"%>
				</h3>

				<p>
					<%=lang.equals("es") ? "Quiero participar y ayudar en actividades"
		: "I want to participate and help in activities"%>
				</p>
			</div>
		</a> <a href="<%=request.getContextPath()%>/registroorg" class="rol-link">
			<div class="btn-rol">
				<img class="icono" src="src/administracion.png"
					alt="<%=lang.equals("es") ? "Entidad organizadora" : "Organization"%>">

				<h3>
					<%=lang.equals("es") ? "Entidad organizadora" : "Organization"%>
				</h3>

				<p>
					<%=lang.equals("es") ? "Quiero crear y gestionar actividades" : "I want to create and manage activities"%>
				</p>
			</div>
		</a>

	</div>

</div>