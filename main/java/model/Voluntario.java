package model;

import java.time.LocalDate;

public class Voluntario extends Usuario {

	private int discapacidad;
	private LocalDate fechaNac;
	private boolean vehiculo;

	public Voluntario(String nombre, String apellidos, int id, String numTelf, String email, String pass,
			int discapacidad, boolean vehiculo, LocalDate fechaNac) {
		super(nombre, apellidos, id, numTelf, email, pass);
		this.discapacidad = discapacidad;
		this.vehiculo = vehiculo;
		this.fechaNac = fechaNac;
	}
	
	public int getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(int discapacidad) {
		this.discapacidad = discapacidad;
	}

	public boolean getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(boolean vehiculo) {
		this.vehiculo = vehiculo;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}
}