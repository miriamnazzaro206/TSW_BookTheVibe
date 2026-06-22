package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.DisponibilitaBean;

public interface DisponibilitaDao {
	
	public void doSave(DisponibilitaBean disponibilita) throws SQLException;
	
	public boolean doUpdatePostiRimanenti(int attivita_id, LocalDate data_attivita, int bigliettiAcquistati) throws SQLException;
	
	public ArrayList <DisponibilitaBean> doRetrieveByKey(int id_attivita) throws SQLException;

}
