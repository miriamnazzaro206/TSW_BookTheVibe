package model;

import java.io.Serializable;
import java.util.Arrays;

public class ImmagineBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id_img;
	private int attivita_id;
	private String formato;
	private byte[] dati_immagine;
	private String testo_alternativo;
	
	public ImmagineBean () {
		
	}
	

	public ImmagineBean(int id_img, int attivita_id, String formato, byte[] dati_immagine, String testo_alternativo) {
		super();
		this.id_img = id_img;
		this.attivita_id = attivita_id;
		this.formato = formato;
		this.dati_immagine = dati_immagine;
		this.testo_alternativo = testo_alternativo;
	}



	public int getId_img() {
		return id_img;
	}

	public void setId_img(int id_img) {
		this.id_img = id_img;
	}

	public int getAttivita_id() {
		return attivita_id;
	}

	public void setAttivita_id(int attivita_id) {
		this.attivita_id = attivita_id;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public byte[] getDati_immagine() {
		return dati_immagine;
	}

	public void setDati_immagine(byte[] dati_immagine) {
		this.dati_immagine = dati_immagine;
	}

	public String getTesto_alternativo() {
		return testo_alternativo;
	}

	public void setTesto_alternativo(String testo_alternativo) {
		this.testo_alternativo = testo_alternativo;
	}


	@Override
	public String toString() {
		return "ImmagineBean [id_img=" + id_img + ", attivita_id=" + attivita_id + ", formato=" + formato
				+ ", dati_immagine=" + Arrays.toString(dati_immagine) + ", testo_alternativo=" + testo_alternativo
				+ "]";
	}
	
	
	
	
	
	

}
