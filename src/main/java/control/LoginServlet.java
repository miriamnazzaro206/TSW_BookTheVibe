package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.UtenteDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

@WebServlet(name = "LoginServlet", urlPatterns = { "/common/login" })
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward(request, response, "login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("txtEmail");
		String password = request.getParameter("txtPwd");

		if (email == null || password == null) {
			forward(request, response, "login.jsp");
			return;
		}

		try {
			UtenteBean utente = new UtenteDaoImp(getDataSource()).doRetrieveByEmailAndPassword(email, password);

			if (utente != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("utenteLoggato", utente);
				session.setAttribute("isAdmin", Boolean.valueOf("ADMIN".equalsIgnoreCase(utente.getRuolo())));
				redirect(request, response, "/common/home");
				return;
			}

			request.setAttribute("errorMessage", "Email o password errate.");
			forward(request, response, "login.jsp");
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Errore di connessione al database. Riprova piu tardi.");
			forward(request, response, "login.jsp");
		}
	}
}
