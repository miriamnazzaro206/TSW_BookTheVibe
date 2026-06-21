package model;

import java.io.Serializable;
import java.time.LocalDate;

public class DisponibilitaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int attivita_id;
	private LocalDate data_evento;
	private int posti_rimanenti;
	
	public DisponibilitaBean() {
		
	}
	
	public DisponibilitaBean(int attivita_id, LocalDate data_evento, int posti_rimanenti) {
		super();
		this.attivita_id = attivita_id;
		this.data_evento = data_evento;
		this.posti_rimanenti = posti_rimanenti;
	}



	public int getAttivita_id() {
		return attivita_id;
	}

	public void setAttivita_id(int attivita_id) {
		this.attivita_id = attivita_id;
	}

	public LocalDate getData_evento() {
		return data_evento;
	}

	public void setData_evento(LocalDate data_evento) {
		this.data_evento = data_evento;
	}

	public int getPosti_rimanenti() {
		return posti_rimanenti;
	}

	public void setPosti_rimanenti(int posti_rimanenti) {
		this.posti_rimanenti = posti_rimanenti;
	}

	@Override
	public String toString() {
		return "DisponibilitaBean [attivita_id=" + attivita_id + ", data_evento=" + data_evento + ", posti_rimanenti="
				+ posti_rimanenti + "]";
	}
	
	
	

}
