package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.DisponibilitaBean;

public class DisponibilitaDaoImp implements DisponibilitaDao{
	private static final String TABLE_NAME = "disponibilita";
	
	private DataSource ds = null;

	public DisponibilitaDaoImp(DataSource ds){
		this.ds = ds;
	}
	
	
	
	public void doSave(DisponibilitaBean disponibilita) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " (attivita_id, data_evento, posti_rimanenti) VALUES (?, ?, ?)";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, disponibilita.getAttivita_id());
			
			if (disponibilita.getData_evento() != null) {
				ps.setDate(2, java.sql.Date.valueOf(disponibilita.getData_evento()));
			} else {
				ps.setDate(2, null);
			}
			
			ps.setInt(3, disponibilita.getPosti_rimanenti());

			ps.executeUpdate();
		}
		
	}
	
	public boolean doUpdatePostiRimanenti(int attivita_id, LocalDate data_attivita, int bigliettiAcquistati) throws SQLException{
		String sql = "UPDATE " + TABLE_NAME + " SET posti_rimanenti = posti_rimanenti - ? WHERE attivita_id = ? AND data_evento = ?";
		
		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, bigliettiAcquistati);
			ps.setInt(2, attivita_id);
			ps.setDate(3, java.sql.Date.valueOf(data_attivita));
			
			int rows = ps.executeUpdate();
			return rows > 0;
		}
		
	}

	public boolean doUpsert(DisponibilitaBean disponibilita) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME
				+ " (attivita_id, data_evento, posti_rimanenti) VALUES (?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE posti_rimanenti = VALUES(posti_rimanenti)";

		try (Connection con = ds.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, disponibilita.getAttivita_id());
			ps.setDate(2, java.sql.Date.valueOf(disponibilita.getData_evento()));
			ps.setInt(3, disponibilita.getPosti_rimanenti());
			return ps.executeUpdate() > 0;
		}
	}
	
	public ArrayList<DisponibilitaBean> doRetrieveByKey(int id_attivita) throws SQLException{
		ArrayList<DisponibilitaBean> listaDisponibilita = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE attivita_id = ?";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, id_attivita);
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					DisponibilitaBean disponibilita = new DisponibilitaBean();
					
					disponibilita.setAttivita_id(rs.getInt("attivita_id"));
					
					java.sql.Date dbData = rs.getDate("data_evento");
					if (dbData != null) {
						disponibilita.setData_evento(dbData.toLocalDate());
					}
					
					disponibilita.setPosti_rimanenti(rs.getInt("posti_rimanenti"));
					
					listaDisponibilita.add(disponibilita);
				}
			}
		}
		return listaDisponibilita;	
	}
	

}

