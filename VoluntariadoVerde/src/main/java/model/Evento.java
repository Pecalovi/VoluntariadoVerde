package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {

	// Atributos
	private String nombre;
	private String ubicacion;
	private LocalDate fecha;
	private int idEvento;
	private String descripcion;
	private int plazasTotales;
	private String tipo;
	private int id_usuario;
	private int inscritos;

	// Constructor
	public Evento(String nombre, String ubicacion, LocalDate fecha, String tipo, int idEvento, String descripcion,
			int plazasTotales, int id_usuario, int inscritos) {
		super();
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.fecha = fecha;
		this.idEvento = idEvento;
		this.descripcion = descripcion;
		this.plazasTotales = plazasTotales;
		this.tipo = tipo;
		this.id_usuario = id_usuario;
		this.inscritos = inscritos;
	}

	// Getters y Setters

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getFechaFormateada() {
	    if (fecha == null) return "";
	    return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(int plazasTotales) {
		this.plazasTotales = plazasTotales;
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

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getInscritos() {
		return inscritos;
	}

	public void setInscritos(int inscritos) {
		this.inscritos = inscritos;
	}

}
