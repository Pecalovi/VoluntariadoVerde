package model;

public class Inscripcion {

	// Atributos
	private Voluntario voluntario;
	private Evento evento;
	private String fechaInscripcion, estado;

	// Constructor
	public Inscripcion(Voluntario voluntario, Evento evento, String fechaInscripcion) {
		this.voluntario = voluntario;
		this.evento = evento;
		this.fechaInscripcion = fechaInscripcion;
	}

	// Getters y Setters
	public Voluntario getVoluntario() {
		return voluntario;
	}

	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	// Metodos
	
	public void confirmarInscripcion() {
	}

	public void cancelarInscripcion() {
	}

}
