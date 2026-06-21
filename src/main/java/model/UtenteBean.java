package model;

import java.io.Serializable;
import java.time.LocalDate;

public class UtenteBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id_utente;
	private String nome;
	private String cognome;
	private String email;
	private String ruolo= "USER";
	private String password;
	private LocalDate data_nascita;
	private String cellulare;
	private String via;
	private String civico;
	private String cap;
	private String nazione;
	private String citta;
	
	public UtenteBean(){
		
	}
	
	public UtenteBean(int id_utente, String nome, String cognome, String email, String ruolo, String password,
			LocalDate data_nascita, String cellulare, String via, String civico, String cap, String nazione,
			String citta) {
		super();
		this.id_utente = id_utente;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo = ruolo;
		this.password = password;
		this.data_nascita = data_nascita;
		this.cellulare = cellulare;
		this.via = via;
		this.civico = civico;
		this.cap = cap;
		this.nazione = nazione;
		this.citta = citta;
	}



	public int getId_utente() {
		return id_utente;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(LocalDate data_nascita) {
		this.data_nascita = data_nascita;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	@Override
	public String toString() {
		return "UtenteBean [id_utente=" + id_utente + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
				+ ", ruolo=" + ruolo + ", password=" + password + ", data_nascita=" + data_nascita + ", cellulare="
				+ cellulare + ", via=" + via + ", civico=" + civico + ", cap=" + cap + ", nazione=" + nazione
				+ ", citta=" + citta + "]";
	}
	
}
