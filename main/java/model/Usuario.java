package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public abstract class Usuario {
	// Atributos
	protected String nombre;
	protected String apellidos;
	protected int id;
	protected String numTelf;
	protected String email;
	protected String pass;
	protected LocalDate fecha_registro;

	// Constructor
	
	// Crear usuario en bbdd
	public Usuario(String nombre, String apellidos, String numTelf, String email, String pass) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.numTelf = numTelf;
		this.email = email;
		this.pass = pass;
	}
	
	// Pillar usuario de bbdd
	public Usuario(String nombre, String apellidos, int id, String numTelf, String email, String pass,
			LocalDate fecha_registro) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id = id;
		this.numTelf = numTelf;
		this.email = email;
		this.pass = pass;
		this.fecha_registro = fecha_registro;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public LocalDate getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(LocalDate fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumTelf() {
		return numTelf;
	}

	public void setNumTelf(String numTelf) {
		this.numTelf = numTelf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public static String sha256(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());

			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String capitalizarTexto(String str) {
		if (str == null || str.trim().isEmpty())
			return "";

		String[] palabras = str.toLowerCase().split(" ");
		StringBuilder resultado = new StringBuilder();

		for (String palabra : palabras) {
			if (!palabra.isEmpty()) {
				resultado.append(Character.toUpperCase(palabra.charAt(0))).append(palabra.substring(1)).append(" ");
			}
		}

		return resultado.toString().trim();
	}

}
