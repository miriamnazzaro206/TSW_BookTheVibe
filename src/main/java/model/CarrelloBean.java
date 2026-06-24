package model;

import java.io.Serializable;
import java.time.LocalDate; 
import java.util.ArrayList;

public class CarrelloBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<ElementoCarrelloBean> elementi;
    private String codiceSconto;
    private double percentualeSconto;

    public CarrelloBean() {
        this.elementi = new ArrayList<ElementoCarrelloBean>();
    }

    public ArrayList<ElementoCarrelloBean> getElementi() {
        return elementi;
    }

    public void aggiungi(ElementoCarrelloBean nuovoElemento) {
        for (int i = 0; i < elementi.size(); i++) {
            ElementoCarrelloBean e = elementi.get(i);
            
            if (e.getAttivita().getId_attivita() == nuovoElemento.getAttivita().getId_attivita() 
                    && e.getDataScelta().equals(nuovoElemento.getDataScelta())) {
                
                e.setQuantita(e.getQuantita() + nuovoElemento.getQuantita());
                return;
            }
        }
        this.elementi.add(nuovoElemento);
    }

    /**
     * Rimuove un elemento in base a ID e LocalDate
     */
    public void rimuovi(int idAttivita, LocalDate dataScelta) {
        for (int i = 0; i < elementi.size(); i++) {
            ElementoCarrelloBean elementoCorrente = elementi.get(i);
            if (elementoCorrente.getAttivita().getId_attivita() == idAttivita 
                    && elementoCorrente.getDataScelta().equals(dataScelta)) {
                elementi.remove(i);
                return;
            }
        }
    }

    public double getPrezzoTotale() {
        double totale = 0;
        for (int i = 0; i < elementi.size(); i++) {
            ElementoCarrelloBean e = elementi.get(i);
            totale += e.getAttivita().getPrezzo_unitario() * e.getQuantita();
        }
        return totale;
    }

    public double getPrezzoScontato() {
        double totale = getPrezzoTotale();
        if (percentualeSconto <= 0) {
            return totale;
        }
        return totale - (totale * percentualeSconto / 100.0);
    }

    public int getNumeroElementi() {
        return elementi.size();
    }
    
    public void svuota() {
        this.elementi.clear();
        this.codiceSconto = null;
        this.percentualeSconto = 0;
    }

    public String getCodiceSconto() {
        return codiceSconto;
    }

    public void setCodiceSconto(String codiceSconto) {
        this.codiceSconto = codiceSconto;
    }

    public double getPercentualeSconto() {
        return percentualeSconto;
    }

    public void setPercentualeSconto(double percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }
}
