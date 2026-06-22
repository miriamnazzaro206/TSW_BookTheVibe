package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.ImmagineBean;

public class ImmagineDaoImp implements ImmagineDao {

    private DataSource ds;

    public ImmagineDaoImp(DataSource ds) {
        this.ds = ds;
    }

    // 1. SALVATAGGIO (CREATE)
    @Override
    public void doSave(ImmagineBean immagine) throws SQLException {
        // Query di inserimento escludento id_img perche nel db è autoincrement
        String query = "INSERT INTO immagine (formato, immagine, testo_alternativo, attivita_id) VALUES (?, ?, ?, ?)";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, immagine.getFormato());
            ps.setBytes(2, immagine.getDati_immagine()); 
            ps.setString(3, immagine.getTesto_alternativo());
            ps.setInt(4, immagine.getAttivita_id());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(int id_img) throws SQLException {
        String query = "DELETE FROM immagine WHERE id_img = ?";
        int righeModificate = 0;

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id_img);
            righeModificate = ps.executeUpdate();
        }
        
        return righeModificate > 0;
    }

    // 3. RICERCA IMMAGINI PER SINGOLA ATTIVITA'
    @Override
    public ArrayList<ImmagineBean> doRetrieveByActivityId(int attivita_id) throws SQLException {
        String query = "SELECT * FROM immagine WHERE attivita_id = ?";
        ArrayList<ImmagineBean> listaImmagini = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, attivita_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
         
                ImmagineBean img = new ImmagineBean();
                
                img.setId_img(rs.getInt("id_img"));
                img.setAttivita_id(rs.getInt("attivita_id"));
                img.setFormato(rs.getString("formato"));
                
                img.setDati_immagine(rs.getBytes("immagine")); 
                img.setTesto_alternativo(rs.getString("testo_alternativo"));
                
                listaImmagini.add(img);
            }
        }
        return listaImmagini;
    }
}