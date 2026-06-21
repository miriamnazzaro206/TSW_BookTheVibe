package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.CodiceScontoBean;

public class CodiceScontoDaoImp implements CodiceScontoDao {
	
	private DataSource ds = null;

    public CodiceScontoDaoImp (DataSource ds) {
    	this.ds = ds;
    }
    @Override
    public void doSave(CodiceScontoBean sconto) throws SQLException {
        String query = "INSERT INTO codice_sconto (code_id, percentuale, stato) VALUES (?, ?, ?)";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, sconto.getCode_id());
            ps.setDouble(2, sconto.getPercentuale());
            ps.setBoolean(3, sconto.getStato());

            ps.executeUpdate();
        }
    }

    // 2. CANCELLAZIONE LOGICA (SOFT DELETE)
    @Override
    public boolean doDelete(String code_id) throws SQLException {
        // Invece di DELETE FROM, facciamo un UPDATE dello stato
        String query = "UPDATE codice_sconto SET stato = ? WHERE code_id = ?";
        int righeModificate = 0;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setBoolean(1, false); // Impostiamo lo stato a false (inattivo)
            ps.setString(2, code_id); // Indichiamo quale codice modificare

            righeModificate = ps.executeUpdate();
        }
        
        // Se ha modificato almeno 1 riga, significa che l'ha trovato e "cancellato" con successo quindi restituira true
        return righeModificate > 0;
    }

    @Override
    public CodiceScontoBean doRetrieveByKey(String code_id) throws SQLException {
        String query = "SELECT * FROM codice_sconto WHERE code_id = ?";
        CodiceScontoBean sconto = null;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, code_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                sconto = new CodiceScontoBean();
                sconto.setCode_id(rs.getString("code_id"));
                sconto.setPercentuale(rs.getDouble("percentuale"));
                sconto.setStato(rs.getBoolean("stato"));
            }
        }
        return sconto;
    }

    @Override
    public ArrayList<CodiceScontoBean> doRetrieveAll() throws SQLException {
        String query = "SELECT * FROM codice_sconto";
        ArrayList<CodiceScontoBean> listaSconti = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CodiceScontoBean sconto = new CodiceScontoBean();
                sconto.setCode_id(rs.getString("code_id"));
                sconto.setPercentuale(rs.getDouble("percentuale"));
                sconto.setStato(rs.getBoolean("stato"));
                
                listaSconti.add(sconto);
            }
        }
        return listaSconti;
    }
}