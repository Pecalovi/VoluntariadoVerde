<%@ page isELIgnored="false" language="java" import="model.Usuario"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");

// IDIOMAS
String lang = request.getParameter("lang");

if (lang != null) {
	session.setAttribute("lang", lang);
} else {
	lang = (String) session.getAttribute("lang");
	if (lang == null) {
		lang = "es";
		session.setAttribute("lang", lang);
	}
}
%>

<!DOCTYPE html>
<html lang="<%=lang%>">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Voluntariado Verde</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

<link rel="shortcut icon" href="src/favicon.ico" />

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<link rel="stylesheet" href="estilos/normalize.css">
<link rel="stylesheet" href="estilos/Layout.css">
<link rel="stylesheet" href="${estilo}">
</head>

<body>

	<jsp:include page="WEB-INF/sections/Header.jsp" />

	<main class="main">
		<jsp:include page="${view}" />
	</main>

	<jsp:include page="WEB-INF/sections/Footer.jsp" />

	<script>
<%String success = (String) request.getAttribute("success");
String error = (String) request.getAttribute("error");
String info = (String) request.getAttribute("info");
String logoutParam = request.getParameter("logout");%>

<%if (success != null) {%>
Swal.fire({
	icon: 'success',
	text: '<%=success.replace("'", "\\'")%>',
	confirmButtonText: 'Aceptar'
});
<%}%>

<%if ("1".equals(logoutParam)) {%>
Swal.fire({
    icon: 'success',
    text: 'Has cerrado sesión correctamente.',
    confirmButtonText: 'Aceptar'
}).then(() => {
    // Borra el parámetro de la URL para que no se repita al recargar
    const url = new URL(window.location);
    url.searchParams.delete('logout');
    window.history.replaceState({}, document.title, url);
});
<%}%>

<%if (error != null) {%>
Swal.fire({
	icon: 'error',
	text: '<%=error.replace("'", "\\'")%>',
	confirmButtonText: 'Aceptar'
});
<%}%>

<%if (info != null) {%>
Swal.fire({
	icon: 'info',
	text: '<%=info.replace("'", "\\'")%>
		',
			confirmButtonText : 'Aceptar'
		});
	<%}%>
		
	</script>

	<script src="scripts/main.js"></script>

</body>
</html>