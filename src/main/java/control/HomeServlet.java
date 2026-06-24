package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.AttivitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AttivitaBean;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AttivitaDaoImp dao = new AttivitaDaoImp(getDataSource());
			ArrayList<AttivitaBean> attivita = dao.doRetrieveAttive();
			request.setAttribute("attivita", attivita);
			forward(request, response, "homepage.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
