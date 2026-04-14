<%@ page language="java" import="model.Usuario, model.Organizador"
	pageEncoding="UTF-8"%>
<%
Usuario user = (Usuario) session.getAttribute("usuario");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>
<header>
	<div class="logo">
		<a href="<%=request.getContextPath()%>/home">
			<img class="iconOrd" src="src/Logo_VV_v1_Blanco.png">
			<img class="iconMovil" src="src/IconoBlanco.png">
		</a>
	</div>
	<div class="menu">
		<a href="<%=request.getContextPath()%>/eventos">
			<%=lang.equals("es") ? "Proximos eventos" : "Upcoming events"%>
		</a>
		<a href="<%=request.getContextPath()%>/sobrenosotros">
			<%=lang.equals("es") ? "Sobre nosotros" : "About us"%>
		</a>
		<a href="<%=request.getContextPath()%>/contacto">
			<%=lang.equals("es") ? "Contacto" : "Contact"%>
		</a>
		<%
		if (user != null) {
		%>
		<div class="dropdown">
			<button class="btn btn-secondary dropdown-toggle" type="button"
				id="dropdownUserButton" data-bs-toggle="dropdown"
				aria-expanded="false">
				<img src="src/PerfilInicioSesion.png" class="perfil-icon"
					style="width: 30px; height: 30px;">
			</button>
			<ul class="dropdown-menu dropdown-menu-end"
				aria-labelledby="dropdownUserButton">
				<li><a class="dropdown-item"
					href="<%=request.getContextPath()%>/perfil">
					<%=lang.equals("es") ? "Mi perfil" : "My profile"%>
				</a></li>
				<li><a class="dropdown-item"
					href="<%=request.getContextPath()%>/logout">
					<%=lang.equals("es") ? "Cerrar sesión" : "Log out"%>
				</a></li>
			</ul>
		</div>
		<%
		} else {
		%>
		<a href="<%=request.getContextPath()%>/login">
			<%=lang.equals("es") ? "Iniciar sesión" : "Log in"%>
		</a>
		<a href="<%=request.getContextPath()%>/registro">
			<%=lang.equals("es") ? "Registrate" : "Sign in"%>
		</a>
		<%
		}
		%>
		<div class="idioma">
			<a href="<%=request.getContextPath()%>/idioma?lang=es"
				class="idiom-opcion-es <%=lang.equals("es") ? "active" : ""%>">🇪🇸</a>
			<a href="<%=request.getContextPath()%>/idioma?lang=en"
				class="idiom-opcion-en <%=lang.equals("en") ? "active" : ""%>">🇬🇧</a>
		</div>
	</div>
	<button class="hamburger-btn" id="hamburgerBtn" aria-label="Menú">
		<span></span>
		<span></span>
		<span></span>
	</button>
</header>
<!-- HEADER PARA EL MOVIL -->
<nav class="menu-movil" id="menuMovil">
	<a href="<%=request.getContextPath()%>/home">
		<%=lang.equals("es") ? "Inicio" : "Home"%>
	</a>
	<a href="<%=request.getContextPath()%>/eventos">
		<%=lang.equals("es") ? "Proximos eventos" : "Upcoming events"%>
	</a>
	<a href="<%=request.getContextPath()%>/sobrenosotros">
		<%=lang.equals("es") ? "Sobre nosotros" : "About us"%>
	</a>
	<a href="<%=request.getContextPath()%>/contacto">
		<%=lang.equals("es") ? "Contacto" : "Contact"%>
	</a>
	<%
	if (user != null) {
	%>
	<a href="<%=request.getContextPath()%>/perfil">
		<%=lang.equals("es") ? "Mi perfil" : "My profile"%>
	</a>
	<a href="<%=request.getContextPath()%>/logout">
		<%=lang.equals("es") ? "Cerrar sesión" : "Log out"%>
	</a>
	<%
	} else {
	%>
	<a href="<%=request.getContextPath()%>/login">
		<%=lang.equals("es") ? "Iniciar sesión" : "Log in"%>
	</a>
	<a href="<%=request.getContextPath()%>/registro">
		<%=lang.equals("es") ? "Registrate" : "Sign in"%>
	</a>
	<%
	}
	%>
	<div class="idioma-movil">
		<a href="<%=request.getContextPath()%>/idioma?lang=es"
			class="<%=lang.equals("es") ? "active" : ""%>">🇪🇸</a>
		<a href="<%=request.getContextPath()%>/idioma?lang=en"
			class="<%=lang.equals("en") ? "active" : ""%>">🇬🇧</a>
	</div>
</nav>
<!-- EL JAVASCRIPT PARA EL MENU -->
<script>
	const btn = document.getElementById('hamburgerBtn');
	const menu = document.getElementById('menuMovil');
	btn.addEventListener('click', function () {
		btn.classList.toggle('open');
		menu.classList.toggle('open');
	});
	menu.querySelectorAll('a').forEach(function (link) {
		link.addEventListener('click', function () {
			btn.classList.remove('open');
			menu.classList.remove('open');
		});
	});
</script>
