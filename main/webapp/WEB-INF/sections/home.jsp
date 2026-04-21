<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento, model.AccesoBD"%>

<%
ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("eventos");
String lang = (String) session.getAttribute("lang");
if (lang == null)
	lang = "es";

boolean es = "es".equals(lang);
%>

<section id="imagen-index">
	<video autoplay muted loop playsinline id="videoFondo">
		<source src="<%=request.getContextPath()%>/videos/fondo.mp4"
			type="video/mp4">
	</video>

	<div class="eslogan">
		<h1><%=es ? "Juntos, mejorando el mundo" : "Together, improving the world"%></h1>
	</div>
</section>

<div class="container text-center">
	<div class="row">
		<div class="col-md-5">
			<p class="frase"><%=es
		? "Cada día haciendo del mundo un lugar <span class=\"frase-verde\">sostenible</span>"
		: "Every day building a <span class=\"frase-verde\">better world</span>"%></p>
		</div>
		<div class="col-6 col-md-5">

			<section class="animate__animated animate__bounceInRight"
				id="contador-impacto">
				<div class="impacto-grid">

					<div class="container text-center">
						<div class="row">
							<div class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero"
										data-target="<%=AccesoBD.contadorHome("usuarios", 1)%>">0</span>
									<p><%=es ? "Voluntarios" : "Volunteers"%></p>
								</div>
							</div>
							<div class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero"
										data-target="<%=AccesoBD.contadorHome("eventos", null)%>">0</span>
									<p><%=es ? "Eventos realizados" : "Events held"%></p>
								</div>
							</div>

							<div class="w-100"></div>

							<div id="impacto-texto" class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero"
										data-target="<%=AccesoBD.contadorHome("usuarios", 2)%>">0</span>
									<p><%=es ? "Entidades organizadoras" : "Organizers"%></p>
								</div>
							</div>
							<div id="impacto-texto" class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero"
										data-target="<%=AccesoBD.contadorInscripciones()%>">0</span>
									<p><%=es ? "Inscripciones" : "Inscription"%></p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>

<section id="container-eventos">
	<h1>
		<%=es ? "Próximos eventos" : "Upcoming events"%>
	</h1>

	<div class="eventos">
		<%
		if (eventos != null && !eventos.isEmpty()) {
			double delay = 0.0;

			for (int i = 0; i < eventos.size() && i < 3; i++) {
				Evento evento = eventos.get(i);
		%>

		<a
			href="<%=request.getContextPath()%>/evento?id=<%=evento.getIdEvento()%>"
			class="evento-index1 animate__animated animate__flipInY"
			style="animation-delay: <%=delay%>s;">
			<div class="evento-index">
				<img src="src/eventos/<%=evento.getTipo()%>.jpg"
					alt="<%=evento.getTipo()%>">

				<div class="informacion-evento">
					<p class="tipo"><%=evento.getTipo()%></p>
					<h2><%=evento.getNombre()%></h2>
					<p><%=evento.getFecha_inicio()%></p>
				</div>
			</div>

		</a>

		<%
		delay += 0.3;
		}
		} else {
		%>
		<p>
			<%=es ? "No hay ningún evento próximamente." : "No upcoming events."%>
		</p>
		<%
		}
		%>
	</div>
</section>

<section class="mapa">
	<iframe
		src="https://www.google.com/maps/d/u/1/embed?mid=1u2CIR7cbPodUWkWd8Y64IERjAgK79OI&ehbc=2E312F&noprof=1"
		width="940" height="480"></iframe>
</section>

<section id="faq">
	<h1><%=es ? "Preguntas frecuentes" : "Frequently asked questions"%></h1>

	<div class="faq-item">
		<h4><%=es ? "¿Qué es esta plataforma?" : "What is this platform?"%></h4>
		<p><%=es
		? "Una plataforma de voluntariado donde organizaciones publican eventos y voluntarios pueden inscribirse para participar."
		: "A volunteering platform where event organizers publish events and volunteers can sign up to participate in them."%></p>
	</div>

	<div class="faq-item">
		<h4><%=es ? "¿Es gratuito?" : "Is it free?"%></h4>
		<p><%=es
		? "Sí, tanto el registro como la inscripción a eventos son completamente gratuitos."
		: "Yes, both signing up and inscribing for events are completely free."%></p>
	</div>

	<div class="faq-item">
		<h4><%=es ? "¿Necesito una cuenta para ver los eventos?" : "Do I need an account to view events?"%></h4>
		<p><%=es
		? "Puedes consultar los eventos sin registrarte, pero para inscribirte necesitarás una cuenta."
		: "You can take a look at events without signing up, but in order to request your participation you need to have an account."%></p>
	</div>

	<div class="faq-item">
		<h4><%=es ? "¿Cómo me inscribo en un evento?" : "How do I sign up for an event?"%></h4>
		<p><%=es
		? "Entra en el evento que te interese y pulsa el botón de inscribirse. Tu solicitud quedará pendiente hasta que el organizador la acepte."
		: "Go to the event you are interested in and click the sign up button. Your request will remain pending until the organizer accepts it."%></p>
	</div>

	<div class="faq-item">
		<h4><%=es ? "¿Puedo cancelar mi inscripción a un evento?" : "Can I cancel my inscription to an event?"%></h4>
		<p><%=es
		? "Sí, puedes cancelar tu inscripción desde tu perfil en cualquier momento antes del evento."
		: "Yes, you can cancel your inscription from your profile at any time before the event."%></p>
	</div>

	<div class="faq-item">
		<h4><%=es ? "¿Cómo puedo crear un evento?" : "How can I create an event?"%></h4>
		<p><%=es
		? "Regístrate como organizador y desde tu perfil encontrarás la opción de crear un nuevo evento."
		: "Sign in as an organizer and in your profile you will find the option to create an event."%></p>
	</div>

</section>
<script>
    const observerImpacto = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                setTimeout(() => {
                    entry.target.querySelectorAll('.impacto-numero').forEach(el => {
                        const target = +el.dataset.target;
                        const duration = 3000;
                        const step = Math.ceil(target / (duration / 16));
                        let current = 0;
                        const timer = setInterval(() => {
                            current = Math.min(current + step, target);
                            el.textContent = current.toLocaleString();
                            if (current >= target) clearInterval(timer);
                        }, 16);
                    });
                }, 300);
                observerImpacto.unobserve(entry.target);
            }
        });
    }, { threshold: 0.3 });
    observerImpacto.observe(document.getElementById('contador-impacto'));
</script>
<script>
    document.querySelectorAll('.faq-item h4').forEach(pregunta => {
        pregunta.addEventListener('click', () => {
            const item = pregunta.parentElement;
            const estaAbierto = item.classList.contains('abierto');
            document.querySelectorAll('.faq-item').forEach(i => i.classList.remove('abierto'));
            if (!estaAbierto) item.classList.add('abierto');
        });
    });
</script>