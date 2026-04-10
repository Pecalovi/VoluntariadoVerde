<%@ page language="java"
	import="model.Usuario, model.Voluntario, model.Organizador"
	pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
boolean esOrganizador = (user != null && user instanceof Organizador);
%>

<form action="<%=request.getContextPath()%>/perfil" method="post"
	id="editarPerfil">
	<input type="hidden" name="accion" value="editar-cuenta" />

	<!-- IZQUIERDA -->
	<div class="form-col">
		<label for="nombre">Nombre</label> <input type="text" id="nombre"
			name="fname" value="<%=user != null ? user.getNombre() : ""%>"
			required readonly> <label for="apellidos">Apellidos</label> <input
			type="text" id="apellidos" name="fsurname"
			value="<%=user != null ? user.getApellidos() : ""%>" required
			readonly> <label for="email">Email</label> <input
			type="email" id="email" name="femail"
			value="<%=user != null ? user.getEmail() : ""%>" required readonly>

		<label for="numTelf">Teléfono</label> <input type="tel" id="numTelf"
			name="fphone" value="<%=user != null ? user.getNumTelf() : ""%>"
			required readonly>
	</div>

	<!-- DERECHA -->
	<div class="form-col">
		<%
		if (esOrganizador) {
		%>

		<label for="entidad">Entidad</label> <input type="text" id="entidad"
			name="fenterprise" value="<%=((Organizador) user).getEntidad()%>"
			readonly>

		<%
		} else if (user instanceof Voluntario) {
		%>

		<label for="fechaNac">Fecha de Nacimiento</label> <input type="date"
			id="fechaNac" name="fedad"
			value="<%=((Voluntario) user).getFechaNac() != null ? ((Voluntario) user).getFechaNac() : ""%>"
			readonly> <label for="vehiculo">Vehículo</label> <input
			type="text" id="vehiculo" name="fvehiculo" maxlength="2"
			value="<%=((Voluntario) user).getVehiculo()%>" readonly> <label
			for="discapacidad">Discapacidad</label> <input type="text"
			id="discapacidad" name="fdisc"
			value="<%=((Voluntario) user).getDiscapacidad()%>" readonly>

		<%
		}
		%>
	</div>

	<!-- BOTÓN -->
	<div class="form-full">
		<button type="button" id="btnEditar" class="botones"
			onclick="toggleEdicion()">Editar</button>
	</div>

</form>

<script>
let editando = false;

function toggleEdicion() {
    const form = document.getElementById("editarPerfil");
    const inputs = document.querySelectorAll("#editarPerfil input:not([type='hidden'])");
    const btn = document.getElementById("btnEditar");

    editando = !editando;

    if (editando) {
        inputs.forEach(i => i.removeAttribute("readonly"));
        btn.textContent = "Guardar";
    } else {

        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        inputs.forEach(i => i.setAttribute("readonly", true));
        btn.textContent = "Editar";

        form.submit();
    }
}
</script>