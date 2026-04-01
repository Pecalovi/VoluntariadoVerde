<div class="form-wrap">
	<div class="titulo">
		<h2>Volunteer registration</h2>
	</div>

	<form class="form" action="<%=request.getContextPath()%>/ServRegister"
		method="POST">

		<!-- PERSONAL INFORMATION -->
		<input type="text" id="fname" name="fname" placeholder="First name"
			required>
		<input type="text" id="fsurname" name="fsurname"
			placeholder="Last name">
		<input type="date" id="edad"
			name="fedad" placeholder="Date of birth" required>

		<!-- CONTACT INFORMATION -->
		<input type="tel" id="fphone" name="fphone"
			placeholder="Phone number" pattern="[0-9]{9}" required>
		<input type="email" id="femail" name="femail" placeholder="Email"
			required>

		<!-- ACCESS -->
		<input type="password" id="fpwd" name="fpwd" placeholder="Password"
			required>

		<!-- ADDITIONAL INFORMATION -->
		<label id="vehitxt">Do you have a vehicle?</label>
		<div id="vehiculo">

			<input type="radio" id="vehiculo-si" name="fvehiculo" value="si"
				required>
			<label for="vehiculo-si">Yes</label>
			<input type="radio" id="vehiculo-no" name="fvehiculo" value="no">
			<label for="vehiculo-no">No</label>

		</div>

		<label id="diversidad">If you have any functional diversity, please indicate it below.</label>
		<input type="textarea" id="fdisc" name="fdisc" placeholder="Tell us your situation or needs">

		<input class="botones" type="submit" value="Create account"></input>
		<input type="hidden" name="tipo" value="voluntario" />

		<p style="color: #570300;">${error}</p>
	</form>

</div>

<script>
	const hoy = new Date();
	hoy.setHours(0, 0, 0, 0);
	document.getElementById("edad").max = hoy.toISOString().slice(0, 10);
</script>
