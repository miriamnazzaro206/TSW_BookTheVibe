package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.PrenotazioneBean;


public interface PrenotazioneDao {
	
	public void doSave(PrenotazioneBean prenotazione) throws SQLException;
	
	public PrenotazioneBean doRetrieveByKey(int idPrenotazione) throws SQLException;
	
	public ArrayList<PrenotazioneBean> doRetrieveByUtenteId(int idUtente) throws SQLException;
	
	public ArrayList<PrenotazioneBean> doRetrieveCompreseTraDate(LocalDate dataX, LocalDate dataY) throws SQLException;
	
	public ArrayList<PrenotazioneBean> doRetrieveAll() throws SQLException;
	
}
