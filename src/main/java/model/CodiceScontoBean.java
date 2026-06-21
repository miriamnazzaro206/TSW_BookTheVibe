package model;

import java.io.Serializable;

public class CodiceScontoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code_id;
	private double percentuale;
	
	private boolean stato = true;
	
	public CodiceScontoBean() {
		
	}

	public CodiceScontoBean(String code_id, double percentuale, boolean stato) {
		super();
		this.code_id = code_id;
		this.percentuale = percentuale;
		this.stato = stato;
	}

	public String getCode_id() {
		return code_id;
	}

	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}

	public double getPercentuale() {
		return percentuale;
	}

	public void setPercentuale(double percentuale) {
		this.percentuale = percentuale;
	}

	public boolean getStato() {
		return stato;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "CodiceScontoBean [code_id=" + code_id + ", percentuale=" + percentuale + ", stato=" + stato + "]";
	}

	
	
	
	
}
