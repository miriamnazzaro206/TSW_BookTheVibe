package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.PrenotazioneBean;

public class PrenotazioneDaoImp implements PrenotazioneDao{
	private static final String TABLE_NAME = "prenotazione";
	
	private DataSource ds = null;

	public PrenotazioneDaoImp(DataSource ds) {
		this.ds = ds;
	}

	public void doSave(PrenotazioneBean prenotazione) throws SQLException{
		String sql = "INSERT INTO " + TABLE_NAME + " (utente_id, codice_sconto_id, attivita_id, data_evento, data_prenotazione, prezzo_tot, stato_pagamento, num_prenotati) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, prenotazione.getUtente_id());
			ps.setString(2, prenotazione.getCodice_sconto_id()); // Può essere null se non c'è sconto
			ps.setInt(3, prenotazione.getAttivita_id());
			
			if (prenotazione.getData_evento() != null) {
				ps.setDate(4, java.sql.Date.valueOf(prenotazione.getData_evento()));
			} else {
				ps.setDate(4, null);
			}
			
			if (prenotazione.getData_prenotazione() != null) {
				ps.setDate(5, java.sql.Date.valueOf(prenotazione.getData_prenotazione()));
			} else {
				ps.setDate(5, null);
			}
			
			ps.setDouble(6, prenotazione.getPrezzo_tot());
			ps.setString(7, prenotazione.getStato_pagamento());
			ps.setInt(8, prenotazione.getNum_prenotati());

			ps.executeUpdate();
		}
	}
	
	public PrenotazioneBean doRetrieveByKey(int idPrenotazione) throws SQLException{
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id_prenotazione = ?";
		PrenotazioneBean prenotazione = null;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, idPrenotazione);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					prenotazione = new PrenotazioneBean();
					
					prenotazione.setId_prenotazione(rs.getInt("id_prenotazione"));
					prenotazione.setUtente_id(rs.getInt("utente_id"));
					prenotazione.setCodice_sconto_id(rs.getString("codice_sconto_id"));
					prenotazione.setAttivita_id(rs.getInt("attivita_id"));
					
					java.sql.Date dbDataEvento = rs.getDate("data_evento");
					if (dbDataEvento != null) {
						prenotazione.setData_evento(dbDataEvento.toLocalDate());
					}
					
					java.sql.Date dbDataPrenotazione = rs.getDate("data_prenotazione");
					if (dbDataPrenotazione != null) {
						prenotazione.setData_prenotazione(dbDataPrenotazione.toLocalDate());
					}
					
					prenotazione.setPrezzo_tot(rs.getDouble("prezzo_tot"));
					prenotazione.setStato_pagamento(rs.getString("stato_pagamento"));
					prenotazione.setNum_prenotati(rs.getInt("num_prenotati"));
				}
			}
		}
		return prenotazione;
	}
	
	public ArrayList<PrenotazioneBean> doRetrieveByUtenteId(int idUtente) throws SQLException{
		ArrayList<PrenotazioneBean> listaPrenotazioni = new ArrayList<>();
	    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE utente_id = ?";

	    try (Connection con = ds.getConnection(); 
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, idUtente);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                PrenotazioneBean prenotazione = new PrenotazioneBean();
	                
	                prenotazione.setId_prenotazione(rs.getInt("id_prenotazione"));
	                prenotazione.setUtente_id(rs.getInt("utente_id"));
	                prenotazione.setCodice_sconto_id(rs.getString("codice_sconto_id"));
	                prenotazione.setAttivita_id(rs.getInt("attivita_id"));
	                
	                java.sql.Date dbDataEvento = rs.getDate("data_evento");
	                if (dbDataEvento != null) {
	                    prenotazione.setData_evento(dbDataEvento.toLocalDate());
	                }
	                
	                java.sql.Date dbDataPrenotazione = rs.getDate("data_prenotazione");
	                if (dbDataPrenotazione != null) {
	                    prenotazione.setData_prenotazione(dbDataPrenotazione.toLocalDate());
	                }
	                
	                prenotazione.setPrezzo_tot(rs.getDouble("prezzo_tot"));
	                prenotazione.setStato_pagamento(rs.getString("stato_pagamento"));
	                prenotazione.setNum_prenotati(rs.getInt("num_prenotati"));
	                
	                listaPrenotazioni.add(prenotazione);
	            }
	        }
	    }
	    return listaPrenotazioni;
	}
	
	public ArrayList<PrenotazioneBean> doRetrieveCompreseTraDate(LocalDate dataX, LocalDate dataY) throws SQLException{
		ArrayList<PrenotazioneBean> listaPrenotazioni = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE data_prenotazione BETWEEN ? AND ?";

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setDate(1, java.sql.Date.valueOf(dataX));
			ps.setDate(2, java.sql.Date.valueOf(dataY));
			
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					PrenotazioneBean prenotazione = new PrenotazioneBean();
					
					prenotazione.setId_prenotazione(rs.getInt("id_prenotazione"));
					prenotazione.setUtente_id(rs.getInt("utente_id"));
					prenotazione.setCodice_sconto_id(rs.getString("codice_sconto_id"));
					prenotazione.setAttivita_id(rs.getInt("attivita_id"));
					
					java.sql.Date dbDataEvento = rs.getDate("data_evento");
					if (dbDataEvento != null) {
						prenotazione.setData_evento(dbDataEvento.toLocalDate());
					}
					
					java.sql.Date dbDataPrenotazione = rs.getDate("data_prenotazione");
					if (dbDataPrenotazione != null) {
						prenotazione.setData_prenotazione(dbDataPrenotazione.toLocalDate());
					}
					
					prenotazione.setPrezzo_tot(rs.getDouble("prezzo_tot"));
					prenotazione.setStato_pagamento(rs.getString("stato_pagamento"));
					prenotazione.setNum_prenotati(rs.getInt("num_prenotati"));
					
					listaPrenotazioni.add(prenotazione);
				}
			}
		}
		return listaPrenotazioni;
	}
	
	public ArrayList<PrenotazioneBean> doRetrieveAll() throws SQLException{
		ArrayList<PrenotazioneBean> listaPrenotazioni = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME;

		try (Connection con = ds.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				PrenotazioneBean prenotazione = new PrenotazioneBean();
				
				prenotazione.setId_prenotazione(rs.getInt("id_prenotazione"));
				prenotazione.setUtente_id(rs.getInt("utente_id"));
				prenotazione.setCodice_sconto_id(rs.getString("codice_sconto_id"));
				prenotazione.setAttivita_id(rs.getInt("attivita_id"));
				
				java.sql.Date dbDataEvento = rs.getDate("data_evento");
				if (dbDataEvento != null) {
					prenotazione.setData_evento(dbDataEvento.toLocalDate());
				}
				
				java.sql.Date dbDataPrenotazione = rs.getDate("data_prenotazione");
				if (dbDataPrenotazione != null) {
					prenotazione.setData_prenotazione(dbDataPrenotazione.toLocalDate());
				}
				
				prenotazione.setPrezzo_tot(rs.getDouble("prezzo_tot"));
				prenotazione.setStato_pagamento(rs.getString("stato_pagamento"));
				prenotazione.setNum_prenotati(rs.getInt("num_prenotati"));
				
				listaPrenotazioni.add(prenotazione);
			}
		}
		return listaPrenotazioni;
	}
	


}
