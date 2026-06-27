package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import dao.AttivitaDaoImp;
import dao.DisponibilitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.DisponibilitaBean;
import model.UtenteBean;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected DataSource getDataSource() {
		return (DataSource) getServletContext().getAttribute("DataSource");
	}

	protected void loadNavbar(HttpServletRequest request) throws SQLException {
		AttivitaDaoImp attivitaDao = new AttivitaDaoImp(getDataSource());
		request.setAttribute("categorieNav", attivitaDao.doRetrieveAllCategorie());
		request.setAttribute("cittaNav", attivitaDao.doRetrieveAllCitta());
	}

	protected void forward(HttpServletRequest request, HttpServletResponse response, String view)
			throws ServletException, java.io.IOException {
		try {
			loadNavbar(request);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		String folder = view.startsWith("admin-") ? "admin" : "common";
		request.getRequestDispatcher("/WEB-INF/views/" + folder + "/" + view).forward(request, response);
	}

	protected void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws java.io.IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	protected CarrelloBean getCarrello(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		CarrelloBean carrello = (CarrelloBean) session.getAttribute("carrello");
		if (carrello == null) {
			carrello = new CarrelloBean();
			session.setAttribute("carrello", carrello);
		}
		return carrello;
	}

	protected UtenteBean getUtente(HttpServletRequest request) {
		return (UtenteBean) request.getSession().getAttribute("utenteLoggato");
	}

	protected boolean requireUser(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {
		if (getUtente(request) == null) {
			redirect(request, response, "/common/login");
			return false;
		}
		return true;
	}

	protected boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {
		UtenteBean utente = getUtente(request);
		if (utente == null || !"ADMIN".equalsIgnoreCase(utente.getRuolo())) {
			redirect(request, response, "/common/login");
			return false;
		}
		return true;
	}

	protected boolean hasPostiDisponibili(int id, LocalDate data, int quantita) throws SQLException {
		if (quantita <= 0) {
			return false;
		}
		for (DisponibilitaBean disponibilita : new DisponibilitaDaoImp(getDataSource()).doRetrieveByKey(id)) {
			if (disponibilita.getData_evento().equals(data) && disponibilita.getPosti_rimanenti() >= quantita) {
				return true;
			}
		}
		return false;
	}

	protected String formatDecimal(double value) {
		return String.format("%.2f", value).replace(",", ".");
	}

	protected void writeJson(HttpServletResponse response, String json) throws java.io.IOException {
		response.setContentType("application/json");
		response.getWriter().write(json);
	}

	protected ArrayList<String> splitCsvValues(String value) {
		ArrayList<String> values = new ArrayList<>();
		if (value == null) {
			return values;
		}
		for (String item : value.split(",")) {
			String trimmed = item.trim();
			if (!trimmed.isEmpty()) {
				values.add(trimmed);
			}
		}
		return values;
	}
}