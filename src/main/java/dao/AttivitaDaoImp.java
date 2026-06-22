package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.AttivitaBean;

public class AttivitaDaoImp implements AttivitaDao{
	
	private static final String TABLE_NAME = "attivita";
	private DataSource ds = null;

	public AttivitaDaoImp(DataSource ds) {
		this.ds = ds;
	}
	
	
	public void doSave(AttivitaBean attivita) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " (titolo, provider, descrizione, categoria, durata, capacita_massima, citta, prezzo_unitario, stato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, attivita.getTitolo());
			ps.setString(2, attivita.getProvider());
			ps.setString(3, attivita.getDescrizione());
			ps.setString(4, attivita.getCategoria());
			ps.setString(5, attivita.getDurata());
			ps.setInt(6, attivita.getCapacita_massima());
			ps.setString(7, attivita.getCitta());
			ps.setDouble(8, attivita.getPrezzo_unitario());
			ps.setBoolean(9, attivita.isStato());

			ps.executeUpdate();
		}
	}
	
		public boolean doDelete(int idAttivita) throws SQLException{
			String sql = "UPDATE " + TABLE_NAME + " SET stato = false WHERE id_attivita = ?";
			
			try (Connection con = ds.getConnection(); 
				 PreparedStatement ps = con.prepareStatement(sql)) {	
				
				ps.setInt(1, idAttivita);
				int rows = ps.executeUpdate();
				return rows > 0;
			}
		}

	public AttivitaBean doRetrieveByKey(int idAttivita) throws SQLException{
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_attivita = ?";
		AttivitaBean attivita = null;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, idAttivita);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					attivita = new AttivitaBean();
					
					attivita.setId_attivita(rs.getInt("id_attivita"));
					attivita.setTitolo(rs.getString("titolo"));
					attivita.setProvider(rs.getString("provider"));
					attivita.setDescrizione(rs.getString("descrizione"));
					attivita.setCategoria(rs.getString("categoria"));
					attivita.setDurata(rs.getString("durata"));
					attivita.setCapacita_massima(rs.getInt("capacita_massima"));
					attivita.setCitta(rs.getString("citta"));
					attivita.setPrezzo_unitario(rs.getDouble("prezzo_unitario"));
					attivita.setStato(rs.getBoolean("stato"));
				}
			}
		}
		return attivita;
	}
	
	public ArrayList<AttivitaBean> doRetrieveAll() throws SQLException{
		ArrayList<AttivitaBean> listaAttivita = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				AttivitaBean attivita = new AttivitaBean();
				
				attivita.setId_attivita(rs.getInt("id_attivita"));
				attivita.setTitolo(rs.getString("titolo"));
				attivita.setProvider(rs.getString("provider"));
				attivita.setDescrizione(rs.getString("descrizione"));
				attivita.setCategoria(rs.getString("categoria"));
				attivita.setDurata(rs.getString("durata"));
				attivita.setCapacita_massima(rs.getInt("capacita_massima"));
				attivita.setCitta(rs.getString("citta"));
				attivita.setPrezzo_unitario(rs.getDouble("prezzo_unitario"));
				attivita.setStato(rs.getBoolean("stato"));
				
				listaAttivita.add(attivita);
			}
		}
		return listaAttivita;
		
	}
		
	
	
	public boolean doUpdatePrezzo(int id_attivita, double prezzo) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET prezzo_unitario = ? WHERE id_attivita = ?";
		
		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setDouble(1, prezzo);
			ps.setInt(2, id_attivita);
			
			int rows = ps.executeUpdate();
			return rows > 0; 
		}
		
	}
	
	public boolean doUpdateDescrizione(int id_attivita, String descr) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET descrizione = ? WHERE id_attivita = ?";
		
		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setString(1, descr);
			ps.setInt(2, id_attivita);
			
			int rows = ps.executeUpdate();
			return rows > 0;
		}
	}
	
	public ArrayList<AttivitaBean> doRetrieveAttive() throws SQLException{
		ArrayList<AttivitaBean> listaAttive = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE stato = true";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				AttivitaBean attivita = new AttivitaBean();
				
				attivita.setId_attivita(rs.getInt("id_attivita"));
				attivita.setTitolo(rs.getString("titolo"));
				attivita.setProvider(rs.getString("provider"));
				attivita.setDescrizione(rs.getString("descrizione"));
				attivita.setCategoria(rs.getString("categoria"));
				attivita.setDurata(rs.getString("durata"));
				attivita.setCapacita_massima(rs.getInt("capacita_massima"));
				attivita.setCitta(rs.getString("citta"));
				attivita.setPrezzo_unitario(rs.getDouble("prezzo_unitario"));
				attivita.setStato(rs.getBoolean("stato"));
				
				listaAttive.add(attivita);
			}
		}
		return listaAttive;
		
	}
	
	public ArrayList<String> doRetrieveAllCategorie() throws SQLException{
		ArrayList<String> categorie = new ArrayList<>();
		String sql = "SELECT DISTINCT categoria FROM " + TABLE_NAME;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				String nomeCategoria = rs.getString("categoria");
				if (nomeCategoria != null) {
					categorie.add(nomeCategoria);
				}
			}
		}
		return categorie;
	}
}
