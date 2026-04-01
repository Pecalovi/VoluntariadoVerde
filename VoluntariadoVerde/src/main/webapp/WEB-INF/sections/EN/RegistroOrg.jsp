<div class="form-wrap">
	<div class="titulo">
		<h2>Organizer registration</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">
		<input type="text" id="fname" name="fname" placeholder="Name"
			required>
		<input type="text" id="fsurname" name="fsurname"
			placeholder="Last name">
		<input type="text" id="fenterprise"
			name="fenterprise" placeholder="Company (Optional)">
		<input type="tel" id="fphone" name="fphone" placeholder="Phone number"
			pattern="^(\+?\s?34\s?)?(\s*\d\s*){9}$" required>
		<input type="email" id="femail" name="femail" placeholder="Email" required>
		<input type="password" id="fpwd" name="fpwd" placeholder="Password"
			required>
		<input class="botones" type="submit"
			value="Create account"></input>
		<input type="hidden" name="tipo" value="organizador" />
		
		<p style="color:#570300;">${error}</p>

	</form>
</div>
