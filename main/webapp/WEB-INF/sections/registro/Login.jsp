<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";
%>

<div class="form-wrap">
	<div class="titulo">
		<h2>
			<%=lang.equals("es") ? "Inicio de sesión" : "Login"%>
		</h2>
	</div>

	<form action="<%=request.getContextPath()%>/ServLogin" method="POST">

		<input type="text" id="fuser" name="fuser"
			placeholder="<%=lang.equals("es") ? "Email" : "Email"%>"> <input
			type="password" id="fpwd" name="fcontra"
			placeholder="<%=lang.equals("es") ? "Contraseña" : "Password"%>">
		</a> <input type="submit" class="botones"
			value="<%=lang.equals("es") ? "Iniciar sesión" : "Sign in"%>">

	</form>

	<div class="crearCuenta">
		<p>
			<%=lang.equals("es") ? "¿No tienes una cuenta?" : "Don't have an account?"%>
		</p>
		<a href="<%=request.getContextPath()%>/registro"> <%=lang.equals("es") ? "Crear cuenta" : "Create account"%>
		</a>
	</div>
</div>