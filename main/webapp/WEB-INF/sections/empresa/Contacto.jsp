<%
String lang = (String) session.getAttribute("lang");
if (lang == null)
    lang = "es";
%>

<div id="info">
    <h1 id="contactanosh1">
        <%=lang.equals("en") ? "Contact us" : "Contáctanos"%>
    </h1>

    <div id="contacto_descripcion">
        <p>
            <%=lang.equals("en") ? 
            "If you need more information or have any questions, we will be happy to assist you."
            : "Si necesitas más información o tienes alguna duda, estaremos encantados de atenderte."%>
        </p>
        <p>
            <%=lang.equals("en") ? 
            "Fill in this form so we can get in touch with you."
            : "Rellena este formulario para que podamos ponernos en contacto contigo."%>
        </p>
    </div>

    <p id="barra">_________________________________________________</p>
    <div id="contacto_info">
        <div class="icono_texto">
            <img alt="" src="src/ubiblanco.png">
            <a href="https://www.google.com/maps/place/cpes+SAN+LUIS+LH+bhip" target="_blank">
                <p>
                    <%=lang.equals("en") ? 
                    "Licenciado Poza Street 31, 48011 Bilbao, Biscay"
                    : "Licenciado Poza Kalea 31, 48011 Bilbao, Vizcaya"%>
                </p>
            </a>
        </div>

        <div class="icono_texto">
            <img alt="" src="src/movilblanco.png">
            <p>+34 123 45 67 89</p>
        </div>

        <div class="icono_texto">
            <img alt="" src="src/emailblanco.png">
            <a href="mailto:voluntariadoverdev@gmail.com">
                voluntariadoverdev@gmail.com
            </a>
        </div>
    </div>

<div id="info">
	<h1><%=lang.equals("en") ? "Contact us" : "Contáctanos"%></h1>

	<div id="contacto_descripcion">
		<p><%=lang.equals("en") ? "If you need more information or have any questions, we will be happy to assist you."
		: "Si necesitas más información o tienes alguna duda, estaremos encantados de atenderte."%>
		</p>
		<p><%=lang.equals("en") ? "Fill in this form so we can get in touch with you."
		: "Rellena este formulario para poder ponernos en contacto contigo."%>
		</p>
	</div>

	<div id="contacto_info">
		<a
			href="https://www.google.com/maps/place/cpes+SAN+LUIS+LH+bhip+-+Bachillerato+y+Formaci%C3%B3n+Profesional/@43.2615541,-2.9419887,17z/data=!3m1!4b1!4m6!3m5!1s0xd4e502873b41fd5:0x1ab0ace3022b6ab9!8m2!3d43.2615502!4d-2.9394138!16s%2Fg%2F1wd3xc60?entry=ttu&g_ep=EgoyMDI1MTAyMi4wIKXMDSoASAFQAw%3D%3D"
			target="_blank">
			<p><%=lang.equals("en") ? "Licenciado Poza Kalea 31, 48011 Bilbao, Biscay"
		: "Licenciado Poza Kalea 31, 48011 Bilbao, Vizcaya"%>
			</p>
		</a>
		<p>+34 123 45 67 89</p>
		<a href="mailto:correoelectronico@gmail.com">correoelectronico@gmail.com</a>
	</div>

</div>

<form id="contactForm" action="contacto" method="post">
    <h1><%=lang.equals("en") ? "Form" : "Formulario"%></h1>

    <input type="text" class="inputcont" name="funame" 
           placeholder="<%=lang.equals("en") ? "Name" : "Nombre"%>" required> 
    
    <input type="email" class="inputcont" name="femail" 
           placeholder="<%=lang.equals("en") ? "Email" : "Correo"%>" required> 
    
    <input type="text" class="inputcont" name="fasunto" 
           placeholder="<%=lang.equals("en") ? "Subject" : "Asunto"%>" required>

    <textarea class="inputcont" name="Text1" rows="4" placeholder="<%=lang.equals("en") ? "Write your message..." : "Escribe tu mensaje..."%>" required></textarea>

    <input type="submit" class="botones" 
           value="<%=lang.equals("en") ? "Send" : "Enviar"%>">
           
           <% if(request.getAttribute("mensajeStatus") != null) { %>
        <p style="color: blue; font-weight: bold;">
            <%= request.getAttribute("mensajeStatus") %>
        </p>
    <% } %>
</form>