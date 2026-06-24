package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.RecensioneBean;

public class RecensioneDaoImp implements RecensioneDao {

    private DataSource ds;

    public RecensioneDaoImp(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void doSave(RecensioneBean recensione) throws SQLException {
        String query = "INSERT INTO recensione (attivita_id, utente_id, punteggio, testo, data_recensione) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, recensione.getAttivita_id());
            ps.setInt(2, recensione.getUtente_id());
            ps.setInt(3, recensione.getPunteggio());
            ps.setString(4, recensione.getTesto());
            
            //Usiamo setObject per passare direttamente l'oggetto data
            ps.setObject(5, recensione.getData_recensione());

            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<RecensioneBean> doRetrieveByKey(int idAttivita) throws SQLException {
  
        String query = "SELECT * FROM recensione WHERE attivita_id = ?";
        ArrayList<RecensioneBean> listaRecensioni = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idAttivita);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
           
                RecensioneBean recensione = new RecensioneBean();
                
                recensione.setAttivita_id(rs.getInt("attivita_id"));
                recensione.setUtente_id(rs.getInt("utente_id"));
                recensione.setPunteggio(rs.getInt("punteggio"));
                recensione.setTesto(rs.getString("testo"));
                
                recensione.setData_recensione(rs.getObject("data_recensione", LocalDate.class));
               
                listaRecensioni.add(recensione);
            }
        }
        
        return listaRecensioni;
    }

    @Override
    public RecensioneBean doRetrieveByAttivitaAndUtente(int idAttivita, int idUtente) throws SQLException {
        String query = "SELECT * FROM recensione WHERE attivita_id = ? AND utente_id = ?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idAttivita);
            ps.setInt(2, idUtente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RecensioneBean recensione = new RecensioneBean();
                recensione.setAttivita_id(rs.getInt("attivita_id"));
                recensione.setUtente_id(rs.getInt("utente_id"));
                recensione.setPunteggio(rs.getInt("punteggio"));
                recensione.setTesto(rs.getString("testo"));
                recensione.setData_recensione(rs.getObject("data_recensione", LocalDate.class));
                return recensione;
            }
        }
        return null;
    }
}
