package model;

public class Puntos {

	// Atributos
	private int idEvento;
	private int tipoPunto;
	private Double kmPunto;

	// Constructor
	public Puntos(int idEvento, int tipoPunto, Double kmPunto) {
		super();
		this.setIdEvento(idEvento);
		this.setTipoPunto(tipoPunto);
		this.setKmPunto(kmPunto);
	}

	// Getters y Setters
	public int getTipoPunto() {
		return tipoPunto;
	}

	public void setTipoPunto(int tipoPunto) {
		this.tipoPunto = tipoPunto;
	}

	public Double getKmPunto() {
		return kmPunto;
	}

	public void setKmPunto(Double kmPunto) {
		this.kmPunto = kmPunto;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

}
