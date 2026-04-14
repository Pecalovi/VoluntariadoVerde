<div class="form-wrap">
	<div class="titulo">
		<h2>Registro de voluntarios</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">

		<!-- DATOS PERSONALES -->
		<input type="text" id="fname" name="fname" placeholder="Nombre"
			required> <input type="text" id="fsurname" name="fsurname"
			placeholder="Apellidos"> <input type="date" id="edad"
			name="fedad" placeholder="Edad" required>

		<!-- DATOS DE CONTACTO -->
		<input type="tel" id="fphone" name="fphone"
			placeholder="Número de teléfono" pattern="[6-9][0-9]{8}" required>
		<input type="email" id="femail" name="femail" placeholder="Correo"
			required>

		<!-- ACCESO -->
		<input type="password" id="fpwd" name="fpwd" placeholder="Contraseña"
			required>

		<!-- INFORMACIÓN ADICIONAL -->
		<label id="vehitxt">¿Tienes vehículo?</label>
		<div id="vehiculo">

			<input type="radio" id="vehiculo-si" name="fvehiculo" value="si"
				required> <label for="vehiculo-si">Sí</label> <input
				type="radio" id="vehiculo-no" name="fvehiculo" value="no"> <label
				for="vehiculo-no">No</label>

		</div>

		<label id="diversidad">En caso de tener alguna diversidad
			funcional, indícalo debajo.</label> <input type="textarea" id="fdisc"
			name="fdisc" placeholder="Indícanos tu caso o tus necesidades">

		<label> <input type="checkbox" name="terms" required>
			Acepto los <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">términos
				y condiciones</a> y la <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">política
				de privacidad</a>
		</label> <input class="botones" type="submit" value="Crear cuenta"></input> <input
			type="hidden" name="tipo" value="voluntario" />

		<p style="color: #570300;">${error}</p>
	</form>

</div>

<script>
	const hoy = new Date();

	// Restar 16 años
	hoy.setFullYear(hoy.getFullYear() - 16);
	hoy.setHours(0, 0, 0, 0);

	// Formato YYYY-MM-DD
	const fechaMaxima = hoy.toISOString().split("T")[0];

	document.getElementById("edad").max = fechaMaxima;
</script>