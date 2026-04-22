package model;

import java.time.LocalDate;

public class Voluntario extends Usuario {

	private int discapacidad;
	private LocalDate fechaNac;
	private boolean vehiculo;
	private double media;

	// Crear voluntario en bbdd
	public Voluntario(String nombre, String apellidos, String numTelf, String email, String pass, int discapacidad,
			boolean vehiculo, LocalDate fechaNac) {
		super(nombre, apellidos, numTelf, email, pass);
		this.discapacidad = discapacidad;
		this.fechaNac = fechaNac;
		this.vehiculo = vehiculo;
	}

	// pillar voluntario de bbdd
	public Voluntario(String nombre, String apellidos, int id, String numTelf, String email, String pass,
			LocalDate fecha_registro, int discapacidad, boolean vehiculo, LocalDate fechaNac, double media) {
		super(nombre, apellidos, id, numTelf, email, pass, fecha_registro);
		this.discapacidad = discapacidad;
		this.fechaNac = fechaNac;
		this.vehiculo = vehiculo;
		this.media = media;
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

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public String getDiscapacidadTexto() {
		return switch (discapacidad) {
		case 0 -> "Ninguna";
		case 1 -> "Leve";
		case 2 -> "Moderada";
		case 3 -> "Severa";
		default -> "No especificada";
		};
	}
}