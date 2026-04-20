//Header con efecto scroll
const header = document.querySelector("header");

function handleScroll() {
	if (window.innerWidth > 860 && window.scrollY > 250) {
		header.classList.add("isla");
	} else {
		header.classList.remove("isla");
	}
}

window.addEventListener("scroll", handleScroll);
window.addEventListener("resize", handleScroll);

//Menu hamburguesa

const btn = document.getElementById('hamburgerBtn');
const menu = document.getElementById('menuMovil');
btn.addEventListener('click', function() {
	btn.classList.toggle('open');
	menu.classList.toggle('open');
});
menu.querySelectorAll('a').forEach(function(link) {
	link.addEventListener('click', function() {
		btn.classList.remove('open');
		menu.classList.remove('open');
	});
});

//Edad minima 18 años
const edad = document.getElementById("edad");

if (edad) {
	const hoy = new Date();
	hoy.setFullYear(hoy.getFullYear() - 18);
	hoy.setHours(0, 0, 0, 0);

	edad.max = hoy.toISOString().split("T")[0];
}