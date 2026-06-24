package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import dao.AttivitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AttivitaBean;

@WebServlet("/catalogo/filtra")
public class FiltroCatalogoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String query = normalize(request.getParameter("q"));
		String categoria = normalize(request.getParameter("categoria"));
		String citta = normalize(request.getParameter("citta"));

		try {
			ArrayList<AttivitaBean> attivita = new AttivitaDaoImp(getDataSource()).doRetrieveAttive();
			StringBuilder json = new StringBuilder("[");
			boolean first = true;
			for (AttivitaBean item : attivita) {
				if (!matches(item, query, categoria, citta)) {
					continue;
				}
				if (!first) {
					json.append(",");
				}
				first = false;
				json.append("{")
					.append("\"id\":").append(item.getId_attivita()).append(",")
					.append("\"titolo\":\"").append(escape(item.getTitolo())).append("\",")
					.append("\"categoria\":\"").append(escape(item.getCategoria())).append("\",")
					.append("\"citta\":\"").append(escape(item.getCitta())).append("\",")
					.append("\"prezzo\":\"").append(String.format(Locale.US, "%.2f", item.getPrezzo_unitario())).append("\"")
					.append("}");
			}
			json.append("]");
			response.getWriter().write(json.toString());
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private boolean matches(AttivitaBean item, String query, String categoria, String citta) {
		if (!query.isEmpty()) {
			String text = normalize(item.getTitolo() + " " + item.getDescrizione() + " " + item.getProvider());
			if (!text.contains(query)) {
				return false;
			}
		}
		if (!categoria.isEmpty() && !normalize(item.getCategoria()).equals(categoria)) {
			return false;
		}
		if (!citta.isEmpty() && !normalize(item.getCitta()).equals(citta)) {
			return false;
		}
		return true;
	}

	private String normalize(String value) {
		return value == null ? "" : value.trim().toLowerCase();
	}

	private String escape(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
	}
}
