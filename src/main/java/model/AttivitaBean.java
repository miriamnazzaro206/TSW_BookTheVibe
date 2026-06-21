package model;

import java.io.Serializable;

public class AttivitaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id_attivita;
	private String titolo;
	private String provider;
	private String descrizione;
	private String categoria;
	private String durata;
	private int capacita_massima;
	private String citta;
	
	private double prezzo_unitario = 0.0;
	
	private boolean stato = true;
	
	
	public AttivitaBean() {
		
	}
	
	public AttivitaBean(int id_attivita, String titolo, String provider, String descrizione, String categoria,
			String durata, int capacita_massima, String citta, double prezzo_unitario, boolean stato) {
		super();
		this.id_attivita = id_attivita;
		this.titolo = titolo;
		this.provider = provider;
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.durata = durata;
		this.capacita_massima = capacita_massima;
		this.citta = citta;
		this.prezzo_unitario = prezzo_unitario;
		this.stato = stato;
	}



	public int getId_attivita() {
		return id_attivita;
	}

	public void setId_attivita(int id_attivita) {
		this.id_attivita = id_attivita;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDurata() {
		return durata;
	}

	public void setDurata(String durata) {
		this.durata = durata;
	}

	public int getCapacita_massima() {
		return capacita_massima;
	}

	public void setCapacita_massima(int capacita_massima) {
		this.capacita_massima = capacita_massima;
	}

	public boolean isStato() {
		return stato;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}


	public double getPrezzo_unitario() {
		return prezzo_unitario;
	}

	public void setPrezzo_unitario(double prezzo_unitario) {
		this.prezzo_unitario = prezzo_unitario;
	}

	@Override
	public String toString() {
		return "AttivitaBean [id_attivita=" + id_attivita + ", titolo=" + titolo + ", provider=" + provider
				+ ", descrizione=" + descrizione + ", categoria=" + categoria + ", durata=" + durata
				+ ", capacita_massima=" + capacita_massima + ", citta=" + citta + ", prezzo_unitario=" + prezzo_unitario
				+ ", stato=" + stato + "]";
	}
	
	
	
}



