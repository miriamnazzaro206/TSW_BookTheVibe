package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.AttivitaDaoImp;
import dao.PrenotazioneDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/common/prenotazioni")
public class PrenotazioniServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		try {
			request.setAttribute("prenotazioni", new PrenotazioneDaoImp(getDataSource()).doRetrieveByUtenteId(getUtente(request).getId_utente()));
			request.setAttribute("attivitaDao", new AttivitaDaoImp(getDataSource()));
			forward(request, response, "prenotazioni.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
