package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.UtenteDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteBean;

@WebServlet("/common/registrazione")
public class RegistrazioneServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward(request, response, "registrazione.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!validateAccessToken(request, response)) {
			return;
		}
		try {
			UtenteBean utente = buildUtente(request);
			new UtenteDaoImp(getDataSource()).doSave(utente);
			redirect(request, response, "/common/login");
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Email gia registrata o dati non validi.");
			forward(request, response, "registrazione.jsp");
		}
	}

	static UtenteBean buildUtente(HttpServletRequest request) {
		UtenteBean utente = new UtenteBean();
		utente.setNome(request.getParameter("nome"));
		utente.setCognome(request.getParameter("cognome"));
		utente.setEmail(request.getParameter("email"));
		utente.setPassword(request.getParameter("password"));
		utente.setRuolo("USER");
		String data = request.getParameter("data_nascita");
		if (data != null && !data.isBlank()) {
			utente.setData_nascita(LocalDate.parse(data));
		}
		utente.setCellulare(request.getParameter("cellulare"));
		utente.setVia(request.getParameter("via"));
		utente.setCivico(request.getParameter("civico"));
		utente.setCap(request.getParameter("cap"));
		utente.setNazione(request.getParameter("nazione"));
		utente.setCitta(request.getParameter("citta"));
		return utente;
	}
}