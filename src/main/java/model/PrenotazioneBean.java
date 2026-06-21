package model;

import java.io.Serializable;
import java.time.LocalDate;

public class PrenotazioneBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id_prenotazione;
	private int utente_id;
	private String codice_sconto_id;
	private int attivita_id;
	private LocalDate data_evento;
	private LocalDate data_prenotazione;
	private double prezzo_tot;
	private String stato_pagamento;
	
	private int num_prenotati = 1;

	public PrenotazioneBean() {
		
	}

	public PrenotazioneBean(int id_prenotazione, int utente_id, String codice_sconto_id, int attivita_id,
			LocalDate data_evento, LocalDate data_prenotazione, double prezzo_tot, String stato_pagamento,
			int num_prenotati) {
		super();
		this.id_prenotazione = id_prenotazione;
		this.utente_id = utente_id;
		this.codice_sconto_id = codice_sconto_id;
		this.attivita_id = attivita_id;
		this.data_evento = data_evento;
		this.data_prenotazione = data_prenotazione;
		this.prezzo_tot = prezzo_tot;
		this.stato_pagamento = stato_pagamento;
		this.num_prenotati = num_prenotati;
	}

	public int getId_prenotazione() {
		return id_prenotazione;
	}

	public void setId_prenotazione(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
	}

	public int getUtente_id() {
		return utente_id;
	}

	public void setUtente_id(int utente_id) {
		this.utente_id = utente_id;
	}

	public String getCodice_sconto_id() {
		return codice_sconto_id;
	}

	public void setCodice_sconto_id(String codice_sconto_id) {
		this.codice_sconto_id = codice_sconto_id;
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

	public LocalDate getData_prenotazione() {
		return data_prenotazione;
	}

	public void setData_prenotazione(LocalDate data_prenotazione) {
		this.data_prenotazione = data_prenotazione;
	}

	public double getPrezzo_tot() {
		return prezzo_tot;
	}

	public void setPrezzo_tot(double prezzo_tot) {
		this.prezzo_tot = prezzo_tot;
	}

	public String getStato_pagamento() {
		return stato_pagamento;
	}

	public void setStato_pagamento(String stato_pagamento) {
		this.stato_pagamento = stato_pagamento;
	}
	
	public int getNum_prenotati() {
		return num_prenotati;
	}

	public void setNum_prenotati(int num_prenotati) {
		this.num_prenotati = num_prenotati;
	}	

	@Override
	public String toString() {
		return "PrenotazioneBean [id_prenotazione=" + id_prenotazione + ", utente_id=" + utente_id
				+ ", codice_sconto_id=" + codice_sconto_id + ", attivita_id=" + attivita_id + ", data_evento="
				+ data_evento + ", data_prenotazione=" + data_prenotazione + ", prezzo_tot=" + prezzo_tot
				+ ", stato_pagamento=" + stato_pagamento + ", num_prenotati=" + num_prenotati + "]";
	}

	
	

	
}
