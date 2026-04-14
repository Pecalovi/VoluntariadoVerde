package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Usuario {
	// Atributos
	protected String nombre;
	protected String apellidos;
	protected int id;
	protected int numTelf;
	protected String email;
	protected String pass;
	
	
	// Constructor
	public Usuario(String nombre, String apellidos, int id, int numTelf, String email, String pass) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.id = id;
		this.numTelf = numTelf;
		this.email = email;
		this.pass = pass;
	}
	
    // Getters y Setters
    public String getNombre() {
        return nombre;
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

    public int getNumTelf() {
        return numTelf;
    }

    public void setNumTelf(int numTelf) {
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
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
