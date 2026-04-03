<%@ page language="java" import="model.Usuario, model.Voluntario, model.Organizador" pageEncoding="UTF-8"%>

<%
Usuario user = (Usuario) session.getAttribute("usuario");
boolean esOrganizador = (user != null && user instanceof Organizador);
%>

<form action="<%=request.getContextPath()%>/perfil/actualizar" method="post">

    <!-- IZQUIERDA -->
    <div class="form-col">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="<%=user != null ? user.getNombre() : "" %>" required>

        <label for="apellidos">Apellidos</label>
        <input type="text" id="apellidos" name="apellidos" value="<%=user != null ? user.getApellidos() : "" %>" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" value="<%=user != null ? user.getEmail() : "" %>" required>

        <label for="pass">Contraseña</label>
        <input type="password" id="pass" name="pass">

        <label for="numTelf">Teléfono</label>
        <input type="text" id="numTelf" name="numTelf" value="<%=user != null ? user.getNumTelf() : "" %>" required>
    </div>

    <!-- DERECHA -->
    <div class="form-col">
        <% if (esOrganizador) { %>

            <label for="entidad">Entidad</label>
            <input type="text" id="entidad" name="entidad"
                value="<%= ((Organizador) user).getEntidad() %>">

        <% } else if (user instanceof Voluntario) { %>

            <label for="fechaNac">Fecha de Nacimiento</label>
            <input type="date" id="fechaNac" name="fechaNac"
                value="<%= ((Voluntario) user).getFechaNac() != null ? ((Voluntario) user).getFechaNac() : "" %>">

            <label for="vehiculo">Vehículo</label>
            <input type="text" id="vehiculo" name="vehiculo" maxlength="2"
                value="<%= ((Voluntario) user).getVehiculo() %>">

            <label for="discapacidad">Discapacidad</label>
            <input type="text" id="discapacidad" name="discapacidad"
                value="<%= ((Voluntario) user).getDiscapacidad() %>">

        <% } %>
    </div>

    <!-- BOTÓN -->
    <div class="form-full">
        <button class="botones" type="submit">Actualizar</button>
    </div>

</form>