<%@ page language="java"
	import="model.Usuario, model.Voluntario, model.Organizador"
	pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
boolean esOrganizador = (user != null && user instanceof Organizador);
%>

<form action="<%=request.getContextPath()%>/perfil/actualizar"
	method="post" id="editarPerfil">

	<!-- IZQUIERDA -->
	<div class="form-col">
		<label for="nombre">Nombre</label> <input type="text" id="nombre"
			name="nombre" value="<%=user != null ? user.getNombre() : ""%>"
			required readonly> <label for="apellidos">Apellidos</label> <input
			type="text" id="apellidos" name="apellidos"
			value="<%=user != null ? user.getApellidos() : ""%>" required
			readonly> <label for="email">Email</label> <input
			type="email" id="email" name="email"
			value="<%=user != null ? user.getEmail() : ""%>" required readonly>
		<label for="pass">Contraseña</label> <input type="password" id="pass"
			name="pass" readonly> <label for="numTelf">Teléfono</label> <input
			type="text" id="numTelf" name="numTelf"
			value="<%=user != null ? user.getNumTelf() : ""%>" required readonly>
	</div>

	<!-- DERECHA -->
	<div class="form-col">
		<%
		if (esOrganizador) {
		%>

		<label for="entidad">Entidad</label> <input type="text" id="entidad"
			name="entidad" value="<%=((Organizador) user).getEntidad()%>"
			readonly>

		<%
		} else if (user instanceof Voluntario) {
		%>

		<label for="fechaNac">Fecha de Nacimiento</label> <input type="date"
			id="fechaNac" name="fechaNac"
			value="<%=((Voluntario) user).getFechaNac() != null ? ((Voluntario) user).getFechaNac() : ""%>" readonly>

		<label for="vehiculo">Vehículo</label> <input type="text"
			id="vehiculo" name="vehiculo" maxlength="2"
			value="<%=((Voluntario) user).getVehiculo()%>" readonly> <label
			for="discapacidad">Discapacidad</label> <input type="text"
			id="discapacidad" name="discapacidad"
			value="<%=((Voluntario) user).getDiscapacidad()%>" readonly>

		<%
		}
		%>
	</div>

	<!-- BOTÓN -->
	<div class="form-full">
		<button type="button" id="btnEditar" class="botones" onclick="toggleEdicion()">Editar</button>
	</div>

</form>

<script>
let editando = false;

function toggleEdicion() {
  const inputs = document.querySelectorAll("#editarPerfil input");
  const boton = document.getElementById("btnEditar");

  editando = !editando;

  inputs.forEach(input => {
    if (editando) {
      input.removeAttribute("readonly");
    } else {
      input.setAttribute("readonly", true);
    }
  });

  boton.textContent = editando ? "Guardar" : "Editar";
}
</script>