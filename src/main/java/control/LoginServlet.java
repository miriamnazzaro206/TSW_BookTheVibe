package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.UtenteDaoImp;

@WebServlet(name = "LoginServlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String email = request.getParameter("txtEmail");
		String password = request.getParameter("txtPwd");
		
		if (email == null || password == null) {
	        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	        return;
	    }
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		UtenteDaoImp utenteDao = new UtenteDaoImp(ds);
		try {
		    // Interroghiamo il database usando il metodo specifico dell'interfaccia
		    UtenteBean utente = utenteDao.doRetrieveByEmailAndPassword(email, password);
		    
		    if (utente != null) {
		        // Creiamo o recuperiamo la sessione corrente
		        HttpSession session = request.getSession(true);
		        
		        //Salviamo l'intero oggetto utente all'interno della sessione
		        session.setAttribute("utenteLoggato", utente);
		        
		        
		        if (utente.getRuolo().equals("ADMIN")){ 
		            session.setAttribute("isAdmin", Boolean.TRUE);
		            request.getRequestDispatcher("/WEB-INF/view/homepage.jsp").forward(request, response);
		            	            
		            // Se hai una pagina speciale per l'admin puoi mandarlo lì, altrimenti lascialo andare alla index
		            //response.sendRedirect("adminDashboard.jsp"); 
		        } else {
		            session.setAttribute("isAdmin", Boolean.FALSE);		            
		            request.getRequestDispatcher("/WEB-INF/view/homepage.jsp").forward(request, response);
		        }
		        
		        return; // Interrompe l'esecuzione per evitare di scendere al forward di errore
		        
		    } else {
		        // LOGIN FALLITO (Credenziali errate)
		        request.setAttribute("errorMessage", "Email o Password errate.");
		        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		    }
		    
		} catch (SQLException e) {
		    e.printStackTrace();
		    request.setAttribute("errorMessage", "Errore di connessione al database. Riprova più tardi.");
		    request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
		
		
	}

}
