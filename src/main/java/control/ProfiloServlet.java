package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.UtenteDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;

@WebServlet("/common/profilo")
public class ProfiloServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		forward(request, response, "profilo.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireUser(request, response)) {
			return;
		}
		UtenteBean corrente = getUtente(request);
		UtenteBean aggiornato = RegistrazioneServlet.buildUtente(request);
		aggiornato.setId_utente(corrente.getId_utente());
		aggiornato.setRuolo(corrente.getRuolo());
		try {
			new UtenteDaoImp(getDataSource()).doUpdate(aggiornato);
			request.getSession().setAttribute("utenteLoggato", aggiornato);
			request.setAttribute("successMessage", "Profilo aggiornato.");
			forward(request, response, "profilo.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
