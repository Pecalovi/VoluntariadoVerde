package model;

import java.time.LocalDateTime;

public class Inscripcion {

	// Atributos
	private int idInscripcion;
	private Voluntario voluntario;
	private int idEvento;
	private LocalDateTime fechaInscripcion;
	private String estado;
	private Integer valoracion;

	// Constructor
	public Inscripcion(int idInscripcion, Voluntario voluntario, int idEvento, LocalDateTime fechaInscripcion,
			String estado, Integer valoracion) {

		this.idInscripcion = idInscripcion;
		this.voluntario = voluntario;
		this.idEvento = idEvento;
		this.fechaInscripcion = fechaInscripcion;
		this.estado = estado;
		this.valoracion = valoracion;
	}

	// Getters y Setters

	public int getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(int idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Voluntario getVoluntario() {
		return voluntario;
	}

	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public LocalDateTime getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	// Métodos de negocio

	public void confirmarInscripcion() {
		this.estado = "Aceptado";
	}

	public void cancelarInscripcion() {
		this.estado = "Cancelado";
	}
}