<div class="form-wrap">
	<div class="titulo">
		<h2>Registro de organizadores</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">
		<input type="text" id="fname" name="fname" placeholder="Nombre"
			required> <input type="text" id="fsurname" name="fsurname"
			placeholder="Apellidos"> <input type="text" id="fenterprise"
			name="fenterprise" placeholder="Empresa (Opcional)"> <input
			type="tel" id="fphone" name="fphone" placeholder="Número de teléfono"
			pattern="^(\+?\s?34\s?)?(\s*\d\s*){9}$" required> <input
			type="email" id="femail" name="femail" placeholder="Correo" required>
		<input type="password" id="fpwd" name="fpwd" placeholder="Contraseña"
			required> <label> <input type="checkbox" name="terms"
			required> Acepto los <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">términos
				y condiciones</a> y la <a
			href="https://drive.google.com/file/d/1Pul60eAL7ZasHGLtT0SkbltUSnCWCsiu/view?usp=sharing">política
				de privacidad</a>
		</label> <input class="botones" type="submit" value="Crear cuenta"></input> <input
			type="hidden" name="tipo" value="organizador" />

	</form>
</div>