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

@WebServlet("/common/catalogo")
public class CatalogoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoria = request.getParameter("categoria");
		String citta = request.getParameter("citta");
		try {
			AttivitaDaoImp dao = new AttivitaDaoImp(getDataSource());
			ArrayList<AttivitaBean> attivita;
			if (categoria != null && !categoria.isBlank()) {
				attivita = dao.doRetrieveAttiveByCategoria(categoria);
				request.setAttribute("filtro", "Categoria: " + categoria);
			} else if (citta != null && !citta.isBlank()) {
				attivita = dao.doRetrieveAttiveByCitta(citta);
				request.setAttribute("filtro", "Città: " + citta);
			} else {
				attivita = dao.doRetrieveAttive();
				request.setAttribute("filtro", "Tutte le esperienze");
			}
			request.setAttribute("attivita", attivita);
			forward(request, response, "catalogo.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
