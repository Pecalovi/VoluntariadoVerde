// =========================
// MENÚ HAMBURGUESA
// =========================
const hamburguesa = document.querySelector('.hamburguesa');
const menu = document.querySelector('.menu');

hamburguesa.addEventListener('click', () => menu.classList.toggle('activo'));

// =========================
// HEADER CON EFECTO SCROLL
// =========================
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
  
  // =========================
  // GESTIÓN DE PUNTOS DINÁMICOS
  // =========================
	var contadorPuntos = 0;
  
      function agregarPunto() {
        contadorPuntos++;
        
        var puntosLista = document.getElementById('puntosLista');
        var emptyMessage = document.getElementById('emptyMessage');
        var kmLlegada = parseFloat(document.getElementById('kmLlegada').value);
        
        if (emptyMessage) {
          emptyMessage.style.display = 'none';
        }
        
        var puntoDiv = document.createElement('div');
        puntoDiv.className = 'punto-item';
        puntoDiv.id = 'punto-' + contadorPuntos;
        
        var puntoId = contadorPuntos;
        
        puntoDiv.innerHTML = 
          '<button type="button" class="btn-eliminar" onclick="eliminarPunto(' + puntoId + ')">x</button>' +
          '<div class="form-row">' +
            '<div class="form-group">' +
              '<label>Tipo de Punto</label>' +
              '<select name="tipoPunto_' + puntoId + '" required>' +
                '<option value="">Seleccionar tipo...</option>' +
                '<option value="4">Avituallamiento</option>' +
                '<option value="3">Punto de Control</option>' +
              '</select>' +
            '</div>' +
            '<div class="form-group">' +
              '<label>Kilometro</label>' +
              '<input type="number" name="kmPunto_' + puntoId + '" placeholder="Km" min="0" step="0.1" max="' + kmLlegada + '" required>' +
            '</div>' +
          '</div>';
        
        puntosLista.appendChild(puntoDiv);
      }
  
      function eliminarPunto(id) {
        var punto = document.getElementById('punto-' + id);
        if (punto) {
          punto.remove();
        }
        
        var puntosLista = document.getElementById('puntosLista');
        var puntos = puntosLista.querySelectorAll('.punto-item');
        var emptyMessage = document.getElementById('emptyMessage');
        
        if (puntos.length === 0 && emptyMessage) {
          emptyMessage.style.display = 'block';
        }
      }
  
      document.getElementById('btnAgregar').addEventListener('click', agregarPunto); 