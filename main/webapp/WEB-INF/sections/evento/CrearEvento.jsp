<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Tarea"%>

<%
Integer paso = (Integer) request.getAttribute("paso");
if (paso == null)
	paso = 1;

String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

boolean es = lang.equals("es");
%>

<div class="indicador-pasos">
	<div
		class="paso <%=paso >= 1 ? (paso > 1 ? "completado" : "activo") : ""%>">
		<span>1</span>
		<p><%=es ? "Evento" : "Event"%></p>
	</div>
	<div class="paso <%=paso >= 2 ? "activo" : ""%>">
		<span>2</span>
		<p><%=es ? "Tareas" : "Tasks"%></p>
	</div>
	<div class="paso">
		<span>3</span>
		<p><%=es ? "Puntos de control" : "Checkpoints"%></p>
	</div>
</div>

<%
if (paso == 1) {
%>

<div class="enuncidado">
	<h1><%=es ? "Datos del evento" : "Event details"%></h1>
	<p>
		<%=es
		? "Completa la información básica y la descripción para crear tu evento."
		: "Fill in the basic information and description to create your event."%>
	</p>
</div>

<form method="post"
	action="${pageContext.request.contextPath}/ServCrearEvento">
	<input type="hidden" name="fase" value="1"> <input type="text"
		name="nombre" placeholder="<%=es ? "Nombre" : "Name"%>" required>

	<select name="tipo" required>
		<option value="" disabled selected>
			<%=es ? "Selecciona tipo" : "Select type"%>
		</option>
		<option value="Dinamico"><%=es ? "Dinámico" : "Dynamic"%></option>
		<option value="Estatico"><%=es ? "Estático" : "Static"%></option>
	</select> <input type="text" name="edicion"
		placeholder="<%=es ? "Edición (ej: 2026)" : "Edition (e.g. 2026)"%>">

	<label for="fecha_inicio"><%=es ? "Fecha de inicio" : "Start date"%></label>
	<input id="fecha_inicio" type="date" name="fecha_inicio" required>

	<label for="fecha_fin"><%=es ? "Fecha de fin (Obligatorio)" : "End date (required)"%></label>
	<input id="fecha_fin" type="date" name="fecha_fin">

	<fieldset>
		<label><%=es ? "Dirección del evento" : "Event location"%></label> <input
			type="text" name="pueblo"
			placeholder="<%=es ? "Pueblo / Ciudad" : "City / Town"%>" required>
		<input type="text" name="provincia"
			placeholder="<%=es ? "Provincia" : "Province"%>" required> <input
			type="number" name="cp"
			placeholder="<%=es ? "Código Postal" : "Postal Code"%>" min="1000"
			max="99999" required>
	</fieldset>

	<textarea name="descripcion"
		placeholder="<%=es ? "Descripción del evento" : "Event description"%>"
		rows="5" required></textarea>

	<input type="hidden" name="id" value="${sessionScope.usuario.id}">
	<input type="submit" class="botones"
		value="<%=es ? "Siguiente" : "Next"%>">
</form>

<script>
	const hoy = new Date();
	hoy.setHours(0, 0, 0, 0);
	const minFecha = hoy.toISOString().slice(0, 10);
	document.getElementById("fecha_inicio").min = minFecha;
	document.getElementById("fecha_fin").min = minFecha;

	document.getElementById("fecha_inicio").addEventListener("change",
			function() {
				document.getElementById("fecha_fin").min = this.value;
			});
</script>

<%
} else {
%>

<%
List<Tarea> tareas = (List<Tarea>) request.getAttribute("tareas");
%>

<div class="enuncidado">
	<h1><%=es ? "Tareas del evento" : "Event tasks"%></h1>
	<p>
		<%=es
		? "Selecciona las tareas que necesitarás cubrir durante el evento."
		: "Select the tasks you will need to cover during the event."%>
	</p>
</div>

<form method="post"
	action="${pageContext.request.contextPath}/ServCrearEvento">
	<input type="hidden" name="fase" value="2">

	<div class="tareas-lista" id="listaTareas">
		<%
		if (tareas != null && !tareas.isEmpty()) {
			for (Tarea t : tareas) {
		%>
		<label class="tarea-item"> <input type="checkbox"
			name="tareas" value="<%=t.getId()%>"> <span><%=t.getNombre()%></span>
		</label>
		<%
		}
		} else {
		%>
		<p><%=es ? "No hay tareas disponibles." : "No tasks available."%></p>
		<%
		}
		%>
	</div>

	<div id="nuevasTareasContainer"></div>

	<div id="nuevaTareaBox" style="display: none;">
		<div class="nueva-tarea-input">
			<input type="text" id="inputNuevaTarea"
				placeholder="<%=es ? "Nombre de la nueva tarea" : "New task name"%>"
				maxlength="100">
			<button type="button" id="btnConfirmarTarea"><%=es ? "Añadir" : "Add"%></button>
			<button type="button" id="btnCancelarTarea"><%=es ? "Cancelar" : "Cancel"%></button>
		</div>
	</div>

	<button type="button" class="btn-otro" id="btnOtro">
		+
		<%=es ? "Otra tarea" : "Another task"%>
	</button>

	<input type="submit" class="botones"
		value="<%=es ? "Siguiente" : "Next"%>">
</form>

<style>
.nueva-tarea-input {
	display: flex;
	gap: 10px;
	align-items: center;
	margin: 10px 0;
	flex-wrap: wrap;
}

.nueva-tarea-input input[type="text"] {
	flex: 1;
	min-width: 200px;
	padding: 10px 14px;
	border: 1px solid var(--verde-principal, #2e8b57);
	border-radius: 8px;
	font-size: 0.95rem;
	outline: none;
}

.nueva-tarea-input button {
	padding: 10px 18px;
	border-radius: 8px;
	border: none;
	font-weight: 600;
	cursor: pointer;
	font-size: 0.9rem;
}

#btnConfirmarTarea {
	background: var(--verde-principal, #2e8b57);
	color: white;
}

#btnCancelarTarea {
	background: #f0f0f0;
	color: #555;
}

.btn-otro {
	background: none;
	border: 2px dashed #aaa;
	color: #666;
	padding: 10px 20px;
	border-radius: 8px;
	font-size: 0.9rem;
	cursor: pointer;
	transition: all 0.2s;
	margin-top: 4px;
}

.tarea-nueva-tag {
	display: inline-flex;
	align-items: center;
	gap: 6px;
	background: #e8f5e9;
	border: 1px solid #a5d6a7;
	border-radius: 20px;
	padding: 6px 14px;
	margin: 4px;
	font-size: 0.9rem;
}

.tarea-nueva-tag button {
	background: none;
	border: none;
	color: #666;
	cursor: pointer;
	font-size: 1rem;
	line-height: 1;
	padding: 0;
}
</style>

<script>
	document.getElementById('btnOtro').addEventListener('click', function() {
		document.getElementById('nuevaTareaBox').style.display = 'block';
		document.getElementById('inputNuevaTarea').focus();
	});

	document
			.getElementById('btnCancelarTarea')
			.addEventListener(
					'click',
					function() {
						document.getElementById('nuevaTareaBox').style.display = 'none';
						document.getElementById('inputNuevaTarea').value = '';
					});

	document.getElementById('btnConfirmarTarea').addEventListener('click',
			añadirTarea);
	document.getElementById('inputNuevaTarea').addEventListener('keydown',
			function(e) {
				if (e.key === 'Enter') {
					e.preventDefault();
					añadirTarea();
				}
			});

	function añadirTarea() {
		const nombre = document.getElementById('inputNuevaTarea').value.trim();
		if (!nombre)
			return;

		const container = document.getElementById('nuevasTareasContainer');
		const tag = document.createElement('div');
		tag.className = 'tarea-nueva-tag';

		const span = document.createElement('span');
		span.textContent = nombre;

		const btn = document.createElement('button');
		btn.type = 'button';
		btn.innerHTML = '&#215;';
		btn.onclick = function() {
			tag.remove();
		};

		const hidden = document.createElement('input');
		hidden.type = 'hidden';
		hidden.name = 'nuevasTareas';
		hidden.value = nombre;

		tag.appendChild(span);
		tag.appendChild(btn);
		tag.appendChild(hidden);
		container.appendChild(tag);

		document.getElementById('inputNuevaTarea').value = '';
		document.getElementById('nuevaTareaBox').style.display = 'none';
	}
</script>

<%
}
%>
