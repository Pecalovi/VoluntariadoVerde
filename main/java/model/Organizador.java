package model;

import java.time.LocalDate;

public class Organizador extends Usuario {

	// Atributos
	private String entidad;

	// Constructor
	
	// Crear Organizador en bbdd
	public Organizador(String nombre, String apellidos, String numTelf, String email, String pass, String entidad) {
		super(nombre, apellidos, numTelf, email, pass);
		this.entidad = entidad;
	}

	// Pillar Organizador de bbdd
	public Organizador(String nombre, String apellidos, int id, String numTelf, String email, String pass,
			LocalDate fecha_registro, String entidad) {
		super(nombre, apellidos, id, numTelf, email, pass, fecha_registro);
		this.entidad = entidad;
	}

	// Getters y Setters

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}
