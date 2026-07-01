package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.PrenotazioneDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireAdmin(request, response)) {
			return;
		}
		try {
			PrenotazioneDaoImp dao = new PrenotazioneDaoImp(getDataSource());
			String filtro = request.getParameter("filtro");
			if ("date".equals(filtro)) {
				request.setAttribute("prenotazioni", dao.doRetrieveCompreseTraDate(LocalDate.parse(request.getParameter("da")), LocalDate.parse(request.getParameter("a"))));
			} else if ("utente".equals(filtro)) {
				request.setAttribute("prenotazioni", dao.doRetrieveByUtenteId(Integer.parseInt(request.getParameter("utenteId"))));
			} else {
				request.setAttribute("prenotazioni", dao.doRetrieveAll());
			}
			request.setAttribute("utentiConPrenotazioni", dao.doRetrieveUtentiConPrenotazioni());
			forward(request, response, "admin-ordini.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
