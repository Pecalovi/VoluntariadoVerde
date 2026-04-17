<div class="form-wrap">
	<div class="titulo">
		<h2>Registro de voluntarios</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">

		<!-- DATOS PERSONALES -->
		<input type="text" id="fname" name="fname" placeholder="Nombre"
			required> <input type="text" id="fsurname" name="fsurname"
			placeholder="Primer apellido"><input type="text"
			id="fsurname2" name="fsurname2" placeholder="Segundo apellido">
		<input type="date" id="edad" name="fedad" placeholder="Edad" required>

		<!-- DATOS DE CONTACTO -->
		<input type="tel" name="fphone" maxlength="9" pattern="[0-9]{9}"
			inputmode="numeric" placeholder="Número de teléfono" required>
		<input type="email" id="femail" name="femail"
			placeholder="Correo electrónico" required>

		<!-- ACCESO -->
		<input type="password" id="fpwd" name="fpwd" placeholder="Contraseña"
			required>

		<!-- INFORMACIÓN ADICIONAL -->

		<div id="vehiculo">
			<input type="checkbox"
				name="fvehiculo" value="true" id="inputvehiculo"> <span>Tengo vehículo</span>
		</div>

		<label>Discapacidad: 
		<select name="fdisc">
			<option value="0">Ninguna</option>
			<option value="1">Leve</option>
			<option value="2">Moderada</option>
			<option value="3">Severa</option>
		</select>
		</label> 
		<label id="terminos"> <input type="checkbox" name="terms"
			required> Acepto los <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">términos
				y condiciones</a> y la <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">política
				de privacidad</a>
		</label> <input class="botones" type="submit" value="Crear cuenta"></input> <input
			type="hidden" name="tipo" value="voluntario" />

		<p style="color: #570300;">${error}</p>
	</form>

</div>