package model;

import java.io.Serializable;
import java.time.LocalDate;

public class RecensioneBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int attivita_id;
	private int utente_id;
	private int punteggio;
	private String testo;
	private LocalDate data_recensione;
	
	public RecensioneBean() {
		
	}

	public RecensioneBean(int attivita_id, int utente_id, int punteggio, String testo, LocalDate data_recensione) {
		super();
		this.attivita_id = attivita_id;
		this.utente_id = utente_id;
		this.punteggio = punteggio;
		this.testo = testo;
		this.data_recensione = data_recensione;
	}




	public int getAttivita_id() {
		return attivita_id;
	}
	public void setAttivita_id(int attivita_id) {
		this.attivita_id = attivita_id;
	}
	public int getUtente_id() {
		return utente_id;
	}
	public void setUtente_id(int utente_id) {
		this.utente_id = utente_id;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public LocalDate getData_recensione() {
		return data_recensione;
	}
	public void setData_recensione(LocalDate data_recensione) {
		this.data_recensione = data_recensione;
	}

	@Override
	public String toString() {
		return "RecensioneBean [attivita_id=" + attivita_id + ", utente_id=" + utente_id + ", punteggio=" + punteggio
				+ ", testo=" + testo + ", data_recensione=" + data_recensione + "]";
	}
	
	

}
