package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.RecensioneBean;

public interface RecensioneDao {
	
	public void doSave(RecensioneBean recensione) throws SQLException;

	public  ArrayList <RecensioneBean> doRetrieveByKey(int idAttivita) throws SQLException;

}
