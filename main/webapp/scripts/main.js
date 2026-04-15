//Header con efecto scroll
const header = document.querySelector("header");

function handleScroll() {
    if (!header) return;

    if (window.innerWidth > 860 && window.scrollY > 250) {
        header.classList.add("isla");
    } else {
        header.classList.remove("isla");
    }
}

window.addEventListener("scroll", handleScroll);
window.addEventListener("resize", handleScroll);


//Edad minima 16 años
const edad = document.getElementById("edad");

if (edad) {
    const hoy = new Date();
    hoy.setFullYear(hoy.getFullYear() - 16);
    hoy.setHours(0, 0, 0, 0);

    edad.max = hoy.toISOString().split("T")[0];
}