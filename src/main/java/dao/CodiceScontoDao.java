package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.CodiceScontoBean;


public interface CodiceScontoDao {
	
	public void doSave(CodiceScontoBean sconto) throws SQLException;
	
	public boolean doDelete(String code_id) throws SQLException;

	public CodiceScontoBean doRetrieveByKey(String code_id) throws SQLException;
	
	public ArrayList<CodiceScontoBean> doRetrieveAll() throws SQLException;
	
}
