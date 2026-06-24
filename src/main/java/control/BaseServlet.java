package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.DataSource;

import dao.AttivitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CarrelloBean;
import model.UtenteBean;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected DataSource getDataSource() {
		return (DataSource) getServletContext().getAttribute("DataSource");
	}

	protected void loadNavbar(HttpServletRequest request) throws SQLException {
		getAccessToken(request);
		AttivitaDaoImp attivitaDao = new AttivitaDaoImp(getDataSource());
		request.setAttribute("categorieNav", attivitaDao.doRetrieveAllCategorie());
		request.setAttribute("cittaNav", attivitaDao.doRetrieveAllCitta());
	}

	protected String getAccessToken(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String token = (String) session.getAttribute("accessToken");
		if (token == null) {
			token = UUID.randomUUID().toString();
			session.setAttribute("accessToken", token);
		}
		return token;
	}

	protected boolean validateAccessToken(HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException {
		String sessionToken = (String) request.getSession().getAttribute("accessToken");
		String requestToken = request.getParameter("accessToken");
		if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return true;
	}

	protected void forward(HttpServletRequest request, HttpServletResponse response, String view)
			throws ServletException, java.io.IOException {
		try {
			loadNavbar(request);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		request.getRequestDispatcher("/WEB-INF/view/" + view).forward(request, response);
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
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return false;
		}
		return true;
	}

	protected boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException {
		UtenteBean utente = getUtente(request);
		if (utente == null || !"ADMIN".equalsIgnoreCase(utente.getRuolo())) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return false;
		}
		return true;
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
