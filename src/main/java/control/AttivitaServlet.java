package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.AttivitaDaoImp;
import dao.DisponibilitaDaoImp;
import dao.ImmagineDaoImp;
import dao.RecensioneDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AttivitaBean;
import model.DisponibilitaBean;
import model.ElementoCarrelloBean;

@WebServlet("/attivita")
public class AttivitaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			AttivitaDaoImp attivitaDao = new AttivitaDaoImp(getDataSource());
			DisponibilitaDaoImp disponibilitaDao = new DisponibilitaDaoImp(getDataSource());
			ImmagineDaoImp immagineDao = new ImmagineDaoImp(getDataSource());
			RecensioneDaoImp recensioneDao = new RecensioneDaoImp(getDataSource());

			AttivitaBean attivita = attivitaDao.doRetrieveByKey(id);
			ArrayList<DisponibilitaBean> disponibilita = disponibilitaDao.doRetrieveByKey(id);
			request.setAttribute("attivita", attivita);
			request.setAttribute("disponibilita", disponibilita);
			request.setAttribute("immagini", immagineDao.doRetrieveByActivityId(id));
			request.setAttribute("recensioni", recensioneDao.doRetrieveByKey(id));
			forward(request, response, "attivita.jsp");
		} catch (SQLException | NumberFormatException e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!validateAccessToken(request, response)) {
			return;
		}
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			LocalDate data = LocalDate.parse(request.getParameter("dataEvento"));
			DisponibilitaDaoImp disponibilitaDao = new DisponibilitaDaoImp(getDataSource());
			boolean disponibile = false;
			for (DisponibilitaBean d : disponibilitaDao.doRetrieveByKey(id)) {
				if (d.getData_evento().equals(data) && d.getPosti_rimanenti() >= quantita && quantita > 0) {
					disponibile = true;
					break;
				}
			}
			if (!disponibile) {
				response.sendRedirect(request.getContextPath() + "/attivita?id=" + id);
				return;
			}
			AttivitaBean attivita = new AttivitaDaoImp(getDataSource()).doRetrieveByKey(id);
			getCarrello(request).aggiungi(new ElementoCarrelloBean(attivita, data, quantita));
			response.sendRedirect(request.getContextPath() + "/carrello");
		} catch (SQLException | NumberFormatException e) {
			throw new ServletException(e);
		}
	}
}
