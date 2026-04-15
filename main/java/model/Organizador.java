package model;

public class Organizador extends Usuario {

	// Atributos
	private String entidad;

	// Constructor
	public Organizador(String nombre, String apellidos, int id, String numTelf, String email, String pass, String entidad) {
		super(nombre, apellidos, id, numTelf, email, pass);
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
