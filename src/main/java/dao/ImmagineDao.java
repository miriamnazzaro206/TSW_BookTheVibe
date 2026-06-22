package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.ImmagineBean;

public interface ImmagineDao {
	
	public void doSave(ImmagineBean immagine) throws SQLException;
	
	public boolean doDelete(int id_img) throws SQLException;

	public ImmagineBean doRetrieveByKey(int id_img) throws SQLException;
	
	public ArrayList<ImmagineBean> doRetrieveAll() throws SQLException;

}
