package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.DisponibilitaDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DisponibilitaBean;

@WebServlet("/disponibilita/controlla")
public class ControlloDisponibilitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			int attivitaId = Integer.parseInt(request.getParameter("attivitaId"));
			String data = request.getParameter("data");
			int posti = 0;
			for (DisponibilitaBean disponibilita : new DisponibilitaDaoImp(getDataSource()).doRetrieveByKey(attivitaId)) {
				if (disponibilita.getData_evento().toString().equals(data)) {
					posti = disponibilita.getPosti_rimanenti();
					break;
				}
			}
			response.getWriter().write("{\"posti\":" + posti + "}");
		} catch (SQLException | NumberFormatException e) {
			throw new ServletException(e);
		}
	}
}
