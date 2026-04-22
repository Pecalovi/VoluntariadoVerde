<div class="form-wrap">
	<div class="titulo">
		<h2>Registro de organizadores</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">
		<input type="text" id="fname" name="fname" placeholder="Nombre"
			required> <input type="text" id="fsurname" name="fsurname"
			placeholder="Primer apellido"><input type="text"
			id="fsurname2" name="fsurname2" placeholder="Segundo apellido">
		<input type="text" id="fenterprise" name="fenterprise"
			placeholder="Empresa" required> <input type="tel"
			name="fphone" maxlength="9" pattern="[0-9]{9}" inputmode="numeric"
			placeholder="Número de teléfono" required> <input
			type="email" id="femail" name="femail"
			placeholder="Correo electrónico" required> <input
			type="password" id="fpwd" name="fpwd" placeholder="Contraseña"
			required> <label id="termorg"> <input type="checkbox"
			name="terms" required> Acepto los <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">términos
				y condiciones</a> y la <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">política
				de privacidad</a>
		</label> <input class="botones" type="submit" value="Crear cuenta"></input> <input
			type="hidden" name="tipo" value="organizador" />

	</form>
</div>