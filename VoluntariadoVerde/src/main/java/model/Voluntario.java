package model;

import java.sql.Date;
import java.time.LocalDate;

public class Voluntario extends Usuario {

	// Atributos
	private String discapacidad, vehiculo;
	private LocalDate fechaNac;
	

	public Voluntario(String nombre, String apellidos, int id, int numTelf, String email, String pass, String discapacidad, String vehiculo, LocalDate fechaNac) {
		super(nombre, apellidos, id, numTelf, email, pass);
		this.discapacidad = discapacidad;
		this.vehiculo = vehiculo;
		
		
		
		this.fechaNac = fechaNac;
	}

	// Getters y Setters

	public String getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		
	}
	
	public void setFechaNac(String fechaNac) {
		
	}
	

	
}
