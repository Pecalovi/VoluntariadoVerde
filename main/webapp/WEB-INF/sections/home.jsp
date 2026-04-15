<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Evento"%>

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
		<div class="col-md-7">
			<p><%=es ? "Cada dia haciendo del mundo un lugar sostenible" : "Every day building a better world"%></p>
		</div>
		<div class="col-6 col-md-5">

			<section class="animate__animated animate__bounceInRight"
				id="contador-impacto">
				<div class="impacto-grid">

					<div class="container text-center">
						<div class="row">
							<div class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero" data-target="1200">0</span>
									<p><%=es ? "Voluntarios" : "Volunteers"%></p>
								</div>
							</div>
							<div class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero" data-target="340">0</span>
									<p><%=es ? "Eventos realizados" : "Events held"%></p>
								</div>
							</div>

							<div class="w-100"></div>

							<div id="impacto-texto" class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero" data-target="85">0</span>
									<p><%=es ? "Organizaciones" : "Organizations"%></p>
								</div>
							</div>
							<div id="impacto-texto" class="col-6 col-sm-3">
								<div class="impacto-item">
									<span class="impacto-numero" data-target="15">0</span>
									<p><%=es ? "Ciudades recorridas" : "Visited cities"%></p>
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
		<%=es ? "Eventos destacados" : "Top events"%>
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
			class="evento-index1 animate__animated animate__bounceInLeft"
			style="animation-delay: <%=delay%>s;">

			<div class="evento-index">
				<img src="src/eventos/<%=evento.getTipo()%>.jpg"
					alt="<%=evento.getTipo()%>">

				<div class="informacion-evento">
					<p class="tipo"><%=evento.getTipo()%></p>
					<h2><%=evento.getNombre()%></h2>
					<p><%=evento.getFechaFormateada()%></p>
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