package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.CodiceScontoDaoImp;
import dao.DisponibilitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CarrelloBean;
import model.CodiceScontoBean;
import model.DisponibilitaBean;
import model.ElementoCarrelloBean;

@WebServlet("/carrello")
public class CarrelloServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("carrello", getCarrello(request));
		forward(request, response, "carrello.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!validateAccessToken(request, response)) {
			return;
		}
		String action = request.getParameter("action");
		CarrelloBean carrello = getCarrello(request);

		if ("svuota".equals(action)) {
			carrello.svuota();
		} else if ("rimuovi".equals(action)) {
			carrello.rimuovi(Integer.parseInt(request.getParameter("id")), LocalDate.parse(request.getParameter("data")));
		} else if ("aggiorna".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			LocalDate data = LocalDate.parse(request.getParameter("data"));
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			if (!hasPostiDisponibili(id, data, quantita)) {
				if (isAjax(request)) {
					writeCartJson(response, false, carrello, "Quantita non disponibile");
					return;
				}
				response.sendRedirect(request.getContextPath() + "/carrello");
				return;
			}
			for (ElementoCarrelloBean elemento : carrello.getElementi()) {
				if (elemento.getAttivita().getId_attivita() == id && elemento.getDataScelta().equals(data)) {
					elemento.setQuantita(quantita);
					break;
				}
			}
			if (isAjax(request)) {
				writeCartJson(response, true, carrello, "Quantita aggiornata");
				return;
			}
		} else if ("sconto".equals(action)) {
			applySconto(request, response, carrello);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/carrello");
	}

	private boolean isAjax(HttpServletRequest request) {
		return "true".equals(request.getParameter("ajax"));
	}

	private void writeCartJson(HttpServletResponse response, boolean valid, CarrelloBean carrello, String message)
			throws IOException {
		response.setContentType("application/json");
		response.getWriter().write("{\"valid\":" + valid + ",\"message\":\"" + message + "\",\"totale\":\""
				+ String.format("%.2f", carrello.getPrezzoScontato()).replace(",", ".") + "\"}");
	}

	private boolean hasPostiDisponibili(int id, LocalDate data, int quantita) throws ServletException {
		if (quantita <= 0) {
			return false;
		}
		try {
			for (DisponibilitaBean disponibilita : new DisponibilitaDaoImp(getDataSource()).doRetrieveByKey(id)) {
				if (disponibilita.getData_evento().equals(data) && disponibilita.getPosti_rimanenti() >= quantita) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void applySconto(HttpServletRequest request, HttpServletResponse response, CarrelloBean carrello)
			throws IOException, ServletException {
		response.setContentType("application/json");
		String codice = request.getParameter("codice");
		if (codice == null || codice.trim().isEmpty()) {
			response.getWriter().write("{\"valid\":false,\"message\":\"Inserisci un codice sconto\"}");
			return;
		}
		try {
			CodiceScontoBean sconto = new CodiceScontoDaoImp(getDataSource()).doRetrieveByKey(codice.trim());
			if (sconto != null && sconto.getStato()) {
				carrello.setCodiceSconto(sconto.getCode_id());
				carrello.setPercentualeSconto(sconto.getPercentuale());
				response.getWriter().write("{\"valid\":true,\"totale\":\"" + String.format("%.2f", carrello.getPrezzoScontato()).replace(",", ".") + "\"}");
			} else {
				response.getWriter().write("{\"valid\":false,\"message\":\"Codice non valido o non attivo\"}");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
