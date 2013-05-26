package com.aeeventos.mailing.modelo;

public class DatamatrixEmail extends Email {
	
	private String datamatrixPath;

	public DatamatrixEmail() {
		this.setTemplate("invitacion.vm");
	}
	
	public String getDatamatrixPath() {
		return datamatrixPath;
	}

	public void setDatamatrixPath(String datamatrixPath) {
		this.datamatrixPath = datamatrixPath;
	}

}
