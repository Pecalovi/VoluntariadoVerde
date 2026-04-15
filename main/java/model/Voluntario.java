package model;

import java.sql.Date;
import java.time.LocalDate;

public class Voluntario extends Usuario {

	private String discapacidad, vehiculo, estado;
	private LocalDate fechaNac;

	public Voluntario(String nombre, String apellidos, int id, String numTelf, String email, String pass,
			String discapacidad, String vehiculo, LocalDate fechaNac, String estado) {
		super(nombre, apellidos, id, numTelf, email, pass);
		this.discapacidad = discapacidad;
		this.vehiculo = vehiculo;
		this.fechaNac = fechaNac;
		this.setEstado(estado);
	}

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

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}