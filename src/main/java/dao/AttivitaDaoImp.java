package dao;

import java.sql.Connection;
import java.sql.Statement;
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

	public int doSaveAndReturnId(AttivitaBean attivita) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " (titolo, provider, descrizione, categoria, durata, capacita_massima, citta, prezzo_unitario, stato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

			try (ResultSet keys = ps.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
		}
		return 0;
	}
	
		public boolean doDelete(int idAttivita) throws SQLException{
			String sql = "UPDATE " + TABLE_NAME + " SET stato = false WHERE id = ?";
			
			try (Connection con = ds.getConnection(); 
				 PreparedStatement ps = con.prepareStatement(sql)) {	
				
				ps.setInt(1, idAttivita);
				int rows = ps.executeUpdate();
				return rows > 0;
			}
		}

	public AttivitaBean doRetrieveByKey(int idAttivita) throws SQLException{
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
		AttivitaBean attivita = null;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, idAttivita);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					attivita = new AttivitaBean();
					
					mapAttivita(rs, attivita);
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
				
				mapAttivita(rs, attivita);
				
				listaAttivita.add(attivita);
			}
		}
		return listaAttivita;
		
	}
		
	
	
	public boolean doUpdatePrezzo(int id_attivita, double prezzo) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET prezzo_unitario = ? WHERE id = ?";
		
		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setDouble(1, prezzo);
			ps.setInt(2, id_attivita);
			
			int rows = ps.executeUpdate();
			return rows > 0; 
		}
		
	}
	
	public boolean doUpdateDescrizione(int id_attivita, String descr) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET descrizione = ? WHERE id = ?";
		
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
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE stato = true ORDER BY titolo";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				AttivitaBean attivita = new AttivitaBean();
				
				mapAttivita(rs, attivita);
				
				listaAttive.add(attivita);
			}
		}
		return listaAttive;
		
	}
	
	public ArrayList<String> doRetrieveAllCategorie() throws SQLException{
		ArrayList<String> categorie = new ArrayList<>();
		String sql = "SELECT DISTINCT categoria FROM " + TABLE_NAME + " WHERE stato = true ORDER BY categoria";

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

	public ArrayList<String> doRetrieveAllCitta() throws SQLException {
		ArrayList<String> citta = new ArrayList<>();
		String sql = "SELECT DISTINCT citta FROM " + TABLE_NAME + " WHERE stato = true ORDER BY citta";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String nomeCitta = rs.getString("citta");
				if (nomeCitta != null) {
					citta.add(nomeCitta);
				}
			}
		}
		return citta;
	}

	public ArrayList<AttivitaBean> doRetrieveAttiveByCategoria(String categoria) throws SQLException {
		return doRetrieveAttiveBy("categoria", categoria);
	}

	public ArrayList<AttivitaBean> doRetrieveAttiveByCitta(String citta) throws SQLException {
		return doRetrieveAttiveBy("citta", citta);
	}

	private ArrayList<AttivitaBean> doRetrieveAttiveBy(String column, String value) throws SQLException {
		ArrayList<AttivitaBean> lista = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE stato = true AND " + column + " = ? ORDER BY titolo";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, value);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					AttivitaBean attivita = new AttivitaBean();
					mapAttivita(rs, attivita);
					lista.add(attivita);
				}
			}
		}
		return lista;
	}

	public boolean doUpdateStato(int idAttivita, boolean stato) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET stato = ? WHERE id = ?";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setBoolean(1, stato);
			ps.setInt(2, idAttivita);
			return ps.executeUpdate() > 0;
		}
	}

	private void mapAttivita(ResultSet rs, AttivitaBean attivita) throws SQLException {
		attivita.setId_attivita(rs.getInt("id"));
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
