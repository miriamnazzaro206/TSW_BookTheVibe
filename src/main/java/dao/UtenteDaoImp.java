package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.UtenteBean;

public class UtenteDaoImp implements UtenteDao{
	private static final String TABLE_NAME = "utente";
	private DataSource ds=null;

	// Il costruttore riceve il DataSource dal MainContext tramite la Servlet
	public UtenteDaoImp(DataSource ds){
		this.ds = ds;
	}
	
	public void doSave(UtenteBean utente) throws SQLException{
		// id_utente viene saltato perchè è AUTO_INCREMENT nel DB
				String sql = "INSERT INTO utente (nome, cognome, email, ruolo, password, data_nascita, cellulare, via, civico, cap, nazione, citta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				try (Connection con = ds.getConnection(); 
					 PreparedStatement ps = con.prepareStatement(sql)) {
					
					ps.setString(1, utente.getNome());
					ps.setString(2, utente.getCognome());
					ps.setString(3, utente.getEmail());
					ps.setString(4, utente.getRuolo()); // Di default è "USER"
					ps.setString(5, utente.getPassword());
					if (utente.getData_nascita() != null) {
						ps.setDate(6, java.sql.Date.valueOf(utente.getData_nascita()));
					} else {
						ps.setDate(6, null);
					}
					ps.setString(7, utente.getCellulare());
					ps.setString(8, utente.getVia());
					ps.setString(9, utente.getCivico());
					ps.setString(10, utente.getCap());
					ps.setString(11, utente.getNazione());
					ps.setString(12, utente.getCitta());

					ps.executeUpdate();
				}
	}
	
	public boolean doDelete(int idUtente) throws SQLException{
		String sql = "DELETE FROM utente WHERE id_utente = ?";
		try (Connection con = ds.getConnection(); 
			PreparedStatement ps = con.prepareStatement(sql)) {	
			ps.setInt(1, idUtente);
			int rows = ps.executeUpdate();
			return rows>0;
		}
	}

	public UtenteBean doRetrieveByKey(int idUtente) throws SQLException{
		String sql = "SELECT * FROM utente WHERE id_utente = ?";
	    UtenteBean utente = null;

	    try (Connection con = ds.getConnection(); 
	        PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, idUtente);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                // Inizializziamo l'oggetto visto che abbiamo trovato l'utente
	                utente = new UtenteBean();
	                
	                // Estraiamo i dati dal ResultSet e li inseriamo nel Bean
	                utente.setId_utente(rs.getInt("id_utente"));
	                utente.setNome(rs.getString("nome"));
	                utente.setCognome(rs.getString("cognome"));
	                utente.setEmail(rs.getString("email"));
	                utente.setRuolo(rs.getString("ruolo"));
	                utente.setPassword(rs.getString("password"));
	                
	                // Conversione sicura da java.sql.Date a LocalDate
	                java.sql.Date dbDate = rs.getDate("data_nascita");
	                if (dbDate != null) {
	                    utente.setData_nascita(dbDate.toLocalDate());
	                }
	                
	                utente.setCellulare(rs.getString("cellulare"));
	                utente.setVia(rs.getString("via"));
	                utente.setCivico(rs.getString("civico"));
	                utente.setCap(rs.getString("cap"));
	                utente.setNazione(rs.getString("nazione"));
	                utente.setCitta(rs.getString("citta"));
	            }
	        }
	    }
		return utente;	
	}
	
	public ArrayList<UtenteBean> doRetrieveAll() throws SQLException{
		ArrayList<UtenteBean> listaUtenti = new ArrayList<>();
	    String sql = "SELECT * FROM utente";

	    try (Connection con = ds.getConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql); 
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            // Per ogni riga trovata nel DB, creiamo un nuovo oggetto Bean
	            UtenteBean utente = new UtenteBean();
	            // Popoliamo il Bean campo per campo
	            utente.setId_utente(rs.getInt("id_utente"));
	            utente.setNome(rs.getString("nome"));
	            utente.setCognome(rs.getString("cognome"));
	            utente.setEmail(rs.getString("email"));
	            utente.setRuolo(rs.getString("ruolo"));
	            utente.setPassword(rs.getString("password"));
	            
	            java.sql.Date dbDate = rs.getDate("data_nascita");
	            if (dbDate != null) {
	                utente.setData_nascita(dbDate.toLocalDate());
	            }
	            utente.setCellulare(rs.getString("cellulare"));
	            utente.setVia(rs.getString("via"));
	            utente.setCivico(rs.getString("civico"));
	            utente.setCap(rs.getString("cap"));
	            utente.setNazione(rs.getString("nazione"));
	            utente.setCitta(rs.getString("citta"));
	            
	            // Aggiungiamo l'utente alla lista
	            listaUtenti.add(utente);
	        }
	    }
	    return listaUtenti;
	}

}
