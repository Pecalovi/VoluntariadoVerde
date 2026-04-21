package model;

public class Tarea {
    private int id;
    private String nombre;

    public Tarea(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}
