<%
String lang = (String) session.getAttribute("lang");
if (lang == null) lang = "es";
%>

<footer>

	<div class="colaboracion">
		<h2>
			<%= lang.equals("es") 
			? "Colabora con nosotros y sé parte del cambio." 
			: "Collaborate with us and be part of the change." %>
		</h2>

		<h3>
			<%= lang.equals("es") 
			? "Juntos hacemos crecer un futuro más verde." 
			: "Together we grow a greener future." %>
		</h3>

		<div class="btn-colab">
			<a class="botones animate__animated animate__pulse animate__infinite"
			   href="<%=request.getContextPath()%>/registrovoluntario">
				<%= lang.equals("es") 
				? "Únete al voluntariado" 
				: "Join the volunteering" %>
			</a>

			<a class="botones"
			   href="<%=request.getContextPath()%>/trabajanosotros">
				<%= lang.equals("es") 
				? "Trabaja con nosotros" 
				: "Work with us" %>
			</a>
		</div>
	</div>

	<div class="contacto">

		<div class="iconos">

			<a class="icon">
				<img src="<%=request.getContextPath()%>/src/movil.png">
				<p>+34 123 45 67 89</p>
			</a>

			<a href="mailto:voluntariadoverv@gmail.com" class="icon">
				<img src="<%=request.getContextPath()%>/src/email.png">
				<p>voluntariadoverdev@gmail.com</p>
			</a>

			<a href="https://www.google.com/maps/place/cpes+SAN+LUIS+LH+bhip+-+Bachillerato+y+Formaci%C3%B3n+Profesional/"
			   class="icon">
				<img src="<%=request.getContextPath()%>/src/mapa.png">
				
				<p class="maps">
				<%= lang.equals("es") 
				? "Licenciado Poza Kalea 31, Bilbao" 
				: "Licenciado Poza Street 31, Bilbao" %></p>
			</a>

		</div>

		<div class="rrss">
			<a href="https://www.instagram.com/">
				<img src="<%=request.getContextPath()%>/src/insta.png">
			</a>

			<a href="https://www.facebook.com/">
				<img src="<%=request.getContextPath()%>/src/facebook.png">
			</a>

			<a href="https://x.com/">
				<img src="<%=request.getContextPath()%>/src/twitter.png">
			</a>
		</div>

	</div>

	<div class="copyright">
		<p>
			<%= lang.equals("es") 
			? "© 2026 Voluntariado Verde. Todos los derechos reservados." 
			: "© 2026 Voluntariado Verde. All rights reserved." %>
		</p>
	</div>

</footer>