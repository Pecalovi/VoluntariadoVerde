package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {

	// Atributos
	private int idEvento;
	private String nombre;
	private String tipo;
	private String descripcion;
	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	private String lugar;
	private String edicion;
	private String estado;
	private int id_organizador;

	// Constructor
	public Evento(int idEvento, String nombre, String tipo, String descripcion, LocalDate fecha_inicio,
			LocalDate fecha_fin, String lugar, String edicion, String estado, int id_organizador) {
		super();
		this.idEvento = idEvento;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.lugar = lugar;
		this.edicion = edicion;
		this.estado = estado;
		this.id_organizador = id_organizador;
	}

	// Getters y Setters
	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(LocalDate fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public LocalDate getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(LocalDate fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getEdicion() {
		return edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getId_organizador() {
		return id_organizador;
	}

	public void setId_organizador(int id_organizador) {
		this.id_organizador = id_organizador;
	}
	public String finalizarEvento() {
	    LocalDate hoy = LocalDate.now();
	    if (this.fecha_fin.isEqual(hoy) || this.fecha_fin.isBefore(hoy)) {
	        this.estado = "Finalizado";
	    }
	    return this.estado;
	}
}
