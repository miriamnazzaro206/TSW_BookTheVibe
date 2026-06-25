package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.AttivitaBean;

public interface AttivitaDao {
	
	public void doSave(AttivitaBean attivita) throws SQLException;
	
	public boolean doDelete(int idAttivita) throws SQLException;

	public AttivitaBean doRetrieveByKey(int idAttivita) throws SQLException;
	
	public ArrayList<AttivitaBean> doRetrieveAll() throws SQLException;
	
	public boolean doUpdatePrezzo(int id_attivita, double prezzo) throws SQLException; 
	
	public boolean doUpdateDescrizione(int id_attivita, String descr) throws SQLException;
	
	public ArrayList<AttivitaBean> doRetrieveAttive() throws SQLException;
	
	public ArrayList<String> doRetrieveAllCategorie() throws SQLException;

	public ArrayList<String> doRetrieveAllCitta() throws SQLException;

	public ArrayList<AttivitaBean> doRetrieveAttiveByCategoria(String categoria) throws SQLException;

	public ArrayList<AttivitaBean> doRetrieveAttiveByCitta(String citta) throws SQLException;

	public boolean doUpdateStato(int idAttivita, boolean stato) throws SQLException;

	public int doSaveAndReturnId(AttivitaBean attivita) throws SQLException;
	
}

