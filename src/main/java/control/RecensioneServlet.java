package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.AttivitaDaoImp;
import dao.RecensioneDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RecensioneBean;

@WebServlet("/common/recensione")
public class RecensioneServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		try {
			int id = Integer.parseInt(request.getParameter("attivitaId"));
			request.setAttribute("attivita", new AttivitaDaoImp(getDataSource()).doRetrieveByKey(id));
			forward(request, response, "recensione.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		try {
			int attivitaId = Integer.parseInt(request.getParameter("attivitaId"));
			RecensioneDaoImp dao = new RecensioneDaoImp(getDataSource());
			if (dao.doRetrieveByAttivitaAndUtente(attivitaId, getUtente(request).getId_utente()) == null) {
				RecensioneBean recensione = new RecensioneBean();
				recensione.setAttivita_id(attivitaId);
				recensione.setUtente_id(getUtente(request).getId_utente());
				recensione.setPunteggio(Integer.parseInt(request.getParameter("punteggio")));
				recensione.setTesto(request.getParameter("testo"));
				recensione.setData_recensione(LocalDate.now());
				dao.doSave(recensione);
			}
			redirect(request, response, "/common/prenotazioni");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
