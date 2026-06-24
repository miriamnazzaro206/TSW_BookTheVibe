package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import model.UtenteBean;

public class UtenteDaoImp implements UtenteDao {
	
	private static final String TABLE_NAME = "utente";
	private DataSource ds = null;

	// Il costruttore riceve il DataSource dal MainContext tramite la Servlet
	public UtenteDaoImp(DataSource ds) {
		this.ds = ds;
	}
	
	
	public void doSave(UtenteBean utente) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " (nome, cognome, email, ruolo, password, data_nascita, cellulare, via, civico, cap, nazione, citta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setString(1, utente.getNome());
			ps.setString(2, utente.getCognome());
			ps.setString(3, utente.getEmail());
			ps.setString(4, utente.getRuolo()); 
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
	
	public boolean doDelete(int idUtente) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
		
		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {	
			
			ps.setInt(1, idUtente);
			int rows = ps.executeUpdate();
			return rows > 0;
		}
	}

	public UtenteBean doRetrieveByKey(int idUtente) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
		UtenteBean utente = null;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, idUtente);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					utente = new UtenteBean();
					
					mapUtente(rs, utente);
				}
			}
		}
		return utente;	
	}
	
	public ArrayList<UtenteBean> doRetrieveAll() throws SQLException {
		ArrayList<UtenteBean> listaUtenti = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				UtenteBean utente = new UtenteBean();
				
				mapUtente(rs, utente);
				
				listaUtenti.add(utente);
			}
		}
		return listaUtenti;
	}
	
	
	public UtenteBean doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
	    
	    String query = "SELECT * FROM utente WHERE email = ? AND password = ?";
	    UtenteBean utente = null;

	    try (Connection con = ds.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, email);
	        ps.setString(2, password);
	        
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            utente = new UtenteBean();
	          
	            utente.setId_utente(rs.getInt("id"));
	            utente.setNome(rs.getString("nome"));
	            utente.setCognome(rs.getString("cognome"));
	            utente.setEmail(rs.getString("email"));
	            utente.setPassword(rs.getString("password"));
	            utente.setRuolo(rs.getString("ruolo"));
	        }
	    }
	    
	 return utente;
	}

	public boolean doUpdate(UtenteBean utente) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET nome=?, cognome=?, email=?, password=?, data_nascita=?, cellulare=?, via=?, civico=?, cap=?, nazione=?, citta=? WHERE id=?";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, utente.getNome());
			ps.setString(2, utente.getCognome());
			ps.setString(3, utente.getEmail());
			ps.setString(4, utente.getPassword());
			if (utente.getData_nascita() != null) {
				ps.setDate(5, java.sql.Date.valueOf(utente.getData_nascita()));
			} else {
				ps.setDate(5, null);
			}
			ps.setString(6, utente.getCellulare());
			ps.setString(7, utente.getVia());
			ps.setString(8, utente.getCivico());
			ps.setString(9, utente.getCap());
			ps.setString(10, utente.getNazione());
			ps.setString(11, utente.getCitta());
			ps.setInt(12, utente.getId_utente());
			return ps.executeUpdate() > 0;
		}
	}

	private void mapUtente(ResultSet rs, UtenteBean utente) throws SQLException {
		utente.setId_utente(rs.getInt("id"));
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
	}
}
