package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ImmagineDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ImmagineBean;

@WebServlet("/image")
public class ImageServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int attivitaId = Integer.parseInt(request.getParameter("attivitaId"));
			ArrayList<ImmagineBean> immagini = new ImmagineDaoImp(getDataSource()).doRetrieveByActivityId(attivitaId);
			if (immagini.isEmpty()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			int index = 0;
			if (request.getParameter("index") != null) {
				index = Integer.parseInt(request.getParameter("index"));
			}
			ImmagineBean immagine = immagini.get(Math.max(0, Math.min(index, immagini.size() - 1)));
			response.setContentType(immagine.getFormato());
			response.getOutputStream().write(immagine.getDati_immagine());
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
