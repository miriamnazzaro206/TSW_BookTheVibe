package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.DisponibilitaDaoImp;
import dao.PrenotazioneDaoImp;
import dao.UtenteDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CarrelloBean;
import model.ElementoCarrelloBean;
import model.PrenotazioneBean;
import model.UtenteBean;

@WebServlet("/common/checkout")
public class CheckoutServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		request.setAttribute("carrello", getCarrello(request));
		forward(request, response, "checkout.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		UtenteBean utente = getUtente(request);
		CarrelloBean carrello = getCarrello(request);
		if (carrello.getElementi().isEmpty()) {
			redirect(request, response, "/common/carrello");
			return;
		}
		try {
			aggiornaIndirizzoUtente(request, utente);
			new UtenteDaoImp(getDataSource()).doUpdate(utente);
			salvaPrenotazioni(carrello, utente);
			carrello.svuota();
			forward(request, response, "ordine-successo.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void aggiornaIndirizzoUtente(HttpServletRequest request, UtenteBean utente) {
		utente.setVia(request.getParameter("via"));
		utente.setCivico(request.getParameter("civico"));
		utente.setCap(request.getParameter("cap"));
		utente.setCitta(request.getParameter("citta"));
		utente.setNazione(request.getParameter("nazione"));
	}

	private void salvaPrenotazioni(CarrelloBean carrello, UtenteBean utente) throws SQLException {
		PrenotazioneDaoImp prenotazioneDao = new PrenotazioneDaoImp(getDataSource());
		DisponibilitaDaoImp disponibilitaDao = new DisponibilitaDaoImp(getDataSource());
		for (ElementoCarrelloBean elemento : carrello.getElementi()) {
			prenotazioneDao.doSave(buildPrenotazione(carrello, utente, elemento));
			disponibilitaDao.doUpdatePostiRimanenti(elemento.getAttivita().getId_attivita(), elemento.getDataScelta(),
					elemento.getQuantita());
		}
	}

	private PrenotazioneBean buildPrenotazione(CarrelloBean carrello, UtenteBean utente, ElementoCarrelloBean elemento) {
		PrenotazioneBean prenotazione = new PrenotazioneBean();
		prenotazione.setUtente_id(utente.getId_utente());
		prenotazione.setCodice_sconto_id(carrello.getCodiceSconto());
		prenotazione.setAttivita_id(elemento.getAttivita().getId_attivita());
		prenotazione.setData_evento(elemento.getDataScelta());
		prenotazione.setData_prenotazione(LocalDate.now());
		prenotazione.setPrezzo_tot(calcolaPrezzoRiga(carrello, elemento));
		prenotazione.setStato_pagamento("COMPLETATO");
		prenotazione.setNum_prenotati(elemento.getQuantita());
		return prenotazione;
	}

	private double calcolaPrezzoRiga(CarrelloBean carrello, ElementoCarrelloBean elemento) {
		double prezzoRiga = elemento.getAttivita().getPrezzo_unitario() * elemento.getQuantita();
		if (carrello.getPercentualeSconto() > 0) {
			prezzoRiga = prezzoRiga - (prezzoRiga * carrello.getPercentualeSconto() / 100.0);
		}
		return prezzoRiga;
	}
}
