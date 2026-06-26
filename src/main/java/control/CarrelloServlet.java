package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.CodiceScontoDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CarrelloBean;
import model.CodiceScontoBean;
import model.ElementoCarrelloBean;

@WebServlet("/carrello")
public class CarrelloServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("carrello", getCarrello(request));
		forward(request, response, "carrello.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		CarrelloBean carrello = getCarrello(request);
		if ("sconto".equals(action) || request.getParameter("codice") != null) {
			applySconto(request, response, carrello);
			return;
		}
		if (!validateAccessToken(request, response)) {
			return;
		}

		if ("svuota".equals(action)) {
			carrello.svuota();
		} else if ("rimuovi".equals(action)) {
			carrello.rimuovi(Integer.parseInt(request.getParameter("id")), LocalDate.parse(request.getParameter("data")));
		} else if ("aggiorna".equals(action)) {
			if (aggiornaQuantita(request, response, carrello)) {
				return;
			}
		}
		redirect(request, response, "/carrello");
	}

	private boolean isAjax(HttpServletRequest request) {
		return "true".equals(request.getParameter("ajax"));
	}

	private void writeCartJson(HttpServletResponse response, boolean valid, CarrelloBean carrello, String message)
			throws IOException {
		writeJson(response, "{\"valid\":" + valid + ",\"message\":\"" + message + "\",\"totale\":\""
				+ formatDecimal(carrello.getPrezzoScontato()) + "\"}");
	}

	private boolean aggiornaQuantita(HttpServletRequest request, HttpServletResponse response, CarrelloBean carrello)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		LocalDate data = LocalDate.parse(request.getParameter("data"));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		try {
			if (!hasPostiDisponibili(id, data, quantita)) {
				if (isAjax(request)) {
					writeCartJson(response, false, carrello, "Quantita non disponibile");
					return true;
				}
				redirect(request, response, "/carrello");
				return true;
			}
			for (ElementoCarrelloBean elemento : carrello.getElementi()) {
				if (elemento.getAttivita().getId_attivita() == id && elemento.getDataScelta().equals(data)) {
					elemento.setQuantita(quantita);
					break;
				}
			}
			if (isAjax(request)) {
				writeCartJson(response, true, carrello, "Quantita aggiornata");
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void applySconto(HttpServletRequest request, HttpServletResponse response, CarrelloBean carrello)
			throws IOException, ServletException {
		String codice = request.getParameter("codice");
		if (codice == null || codice.trim().isEmpty()) {
			writeJson(response, "{\"valid\":false,\"message\":\"Inserisci un codice sconto\"}");
			return;
		}
		try {
			CodiceScontoBean sconto = new CodiceScontoDaoImp(getDataSource()).doRetrieveByKey(codice.trim());
			if (sconto != null && sconto.getStato()) {
				carrello.setCodiceSconto(sconto.getCode_id());
				carrello.setPercentualeSconto(sconto.getPercentuale());
				writeJson(response, "{\"valid\":true,\"totale\":\"" + formatDecimal(carrello.getPrezzoScontato()) + "\"}");
			} else {
				writeJson(response, "{\"valid\":false,\"message\":\"Codice non valido o non attivo\"}");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
