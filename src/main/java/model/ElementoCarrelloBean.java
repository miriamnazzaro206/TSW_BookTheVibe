package model;

import java.io.Serializable;
import java.time.LocalDate;

public class ElementoCarrelloBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private AttivitaBean attivita; //attività selezionata dall'utente
    private LocalDate dataScelta; //sarebbe la data prescelta dall'utente in cui si svolgerà l'attività selezionata
    private int quantita; //sarebbe il numero di biglietti che un utente acquista per l'attività selezionata        

    public ElementoCarrelloBean() {
    }

    public ElementoCarrelloBean(AttivitaBean attivita, LocalDate dataScelta, int quantita) {
        this.attivita = attivita;
        this.dataScelta = dataScelta;
        this.quantita = quantita;
    }

    public AttivitaBean getAttivita() {
        return attivita;
    }

    public void setAttivita(AttivitaBean attivita) {
        this.attivita = attivita;
    }

    public LocalDate getDataScelta() {
        return dataScelta;
    }

    public void setDataScelta(LocalDate dataScelta) {
        this.dataScelta = dataScelta;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

	@Override
	public String toString() {
		return "ElementoCarrelloBean [attivita=" + attivita + ", dataScelta=" + dataScelta + ", quantita=" + quantita
				+ "]";
	}
    
    
}
