package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.AttivitaDaoImp;
import dao.DisponibilitaDaoImp;
import dao.ImmagineDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.AttivitaBean;
import model.DisponibilitaBean;
import model.ImmagineBean;

@WebServlet("/admin/catalogo")
@MultipartConfig(maxFileSize = 1024 * 1024 * 8, maxRequestSize = 1024 * 1024 * 40)
public class AdminCatalogoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireAdmin(request, response)) {
			return;
		}
		try {
			request.setAttribute("attivita", new AttivitaDaoImp(getDataSource()).doRetrieveAll());
			forward(request, response, "admin-catalogo.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireAdmin(request, response)) {
			return;
		}
		if (!validateAccessToken(request, response)) {
			return;
		}
		String action = request.getParameter("action");
		try {
			AttivitaDaoImp attivitaDao = new AttivitaDaoImp(getDataSource());
			if ("stato".equals(action)) {
				attivitaDao.doUpdateStato(Integer.parseInt(request.getParameter("id")), Boolean.parseBoolean(request.getParameter("stato")));
			} else if ("modifica".equals(action)) {
				int id = Integer.parseInt(request.getParameter("id"));
				attivitaDao.doUpdatePrezzo(id, Double.parseDouble(request.getParameter("prezzo")));
				attivitaDao.doUpdateDescrizione(id, request.getParameter("descrizione"));
			} else if ("date".equals(action)) {
				salvaNuoveDate(Integer.parseInt(request.getParameter("id")), request.getParameter("date_evento"),
						request.getParameter("posti_nuove_date"));
			} else {
				int id = attivitaDao.doSaveAndReturnId(buildAttivita(request));
				DisponibilitaDaoImp disponibilitaDao = new DisponibilitaDaoImp(getDataSource());
				for (String data : splitCsvValues(request.getParameter("date_evento"))) {
					disponibilitaDao.doSave(new DisponibilitaBean(id, LocalDate.parse(data), Integer.parseInt(request.getParameter("capacita_massima"))));
				}
				ImmagineDaoImp immagineDao = new ImmagineDaoImp(getDataSource());
				for (Part part : request.getParts()) {
					if ("foto".equals(part.getName()) && part.getSize() > 0) {
						ImmagineBean img = new ImmagineBean();
						img.setAttivita_id(id);
						img.setFormato(part.getContentType());
						img.setDati_immagine(part.getInputStream().readAllBytes());
						img.setTesto_alternativo(request.getParameter("titolo"));
						immagineDao.doSave(img);
					}
				}
			}
			response.sendRedirect(request.getContextPath() + "/admin/catalogo");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private AttivitaBean buildAttivita(HttpServletRequest request) {
		AttivitaBean attivita = new AttivitaBean();
		attivita.setTitolo(request.getParameter("titolo"));
		attivita.setProvider(request.getParameter("provider"));
		attivita.setDescrizione(request.getParameter("descrizione"));
		attivita.setCategoria(request.getParameter("categoria"));
		attivita.setDurata(request.getParameter("durata"));
		attivita.setCapacita_massima(Integer.parseInt(request.getParameter("capacita_massima")));
		attivita.setCitta(request.getParameter("citta"));
		attivita.setPrezzo_unitario(Double.parseDouble(request.getParameter("prezzo_unitario")));
		attivita.setStato(true);
		return attivita;
	}

	private void salvaNuoveDate(int attivitaId, String dateEvento, String postiNuoveDate) throws SQLException {
		if (postiNuoveDate == null || postiNuoveDate.trim().isEmpty()) {
			return;
		}
		DisponibilitaDaoImp disponibilitaDao = new DisponibilitaDaoImp(getDataSource());
		int posti = Integer.parseInt(postiNuoveDate.trim());
		for (String data : splitCsvValues(dateEvento)) {
			disponibilitaDao.doUpsert(new DisponibilitaBean(attivitaId, LocalDate.parse(data), posti));
		}
	}
}
