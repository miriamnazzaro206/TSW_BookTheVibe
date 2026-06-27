package control;

import java.io.IOException;
import java.sql.SQLException;

import dao.CodiceScontoDaoImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CodiceScontoBean;

@WebServlet("/admin/coupon")
public class AdminCouponServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireAdmin(request, response)) {
			return;
		}
		try {
			request.setAttribute("coupon", new CodiceScontoDaoImp(getDataSource()).doRetrieveAll());
			forward(request, response, "admin-coupon.jsp");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!requireAdmin(request, response)) {
			return;
		}
		try {
			CodiceScontoDaoImp dao = new CodiceScontoDaoImp(getDataSource());
			if ("stato".equals(request.getParameter("action"))) {
				dao.doUpdateStato(request.getParameter("code_id"), Boolean.parseBoolean(request.getParameter("stato")));
			} else {
				CodiceScontoBean coupon = new CodiceScontoBean();
				coupon.setCode_id(request.getParameter("code_id"));
				coupon.setPercentuale(Double.parseDouble(request.getParameter("percentuale")));
				coupon.setStato(true);
				dao.doSave(coupon);
			}
			response.sendRedirect(request.getContextPath() + "/admin/coupon");
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}
