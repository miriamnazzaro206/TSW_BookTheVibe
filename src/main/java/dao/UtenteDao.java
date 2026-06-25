package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.UtenteBean;


public interface UtenteDao {
	
	public void doSave(UtenteBean utente) throws SQLException;
	
	public boolean doDelete(int idUtente) throws SQLException;

	public UtenteBean doRetrieveByKey(int idUtente) throws SQLException;
	
	public ArrayList<UtenteBean> doRetrieveAll() throws SQLException;
	
	public UtenteBean doRetrieveByEmailAndPassword(String email, String password) throws SQLException;
	
	public boolean doUpdate(UtenteBean utente) throws SQLException;
	
}
