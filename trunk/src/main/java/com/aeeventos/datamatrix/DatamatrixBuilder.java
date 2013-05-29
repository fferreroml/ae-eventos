package com.aeeventos.datamatrix;

import java.awt.image.BufferedImage;
import java.util.Properties;
import com.java4less.rdatamatrix.*;

public class DatamatrixBuilder {

	private String dataToEncote;
	private boolean isProcessTilde;
	private int moduleSize;
	private int margin;
	private String encoding;
	private String format;
	private Properties properties;

	/**
	 * Constructor para el builder de datamatris
	 * 
	 * @param dataToEncote
	 *            el texto a encodear
	 */
	public DatamatrixBuilder(String dataToEncote) {
		this.dataToEncote = dataToEncote;
		this.isProcessTilde = true;
		this.moduleSize = 10;
		this.margin = 8;
		this.encoding = "BASE256";
		this.format = null;
		this.properties = new Properties();

	}

	/**
	 * Propiedad a setear en el builder de datamatrix
	 * 
	 * @param theKey
	 *            key de la propiedad a modificar
	 * @param theValue
	 *            valor de la propiedad a modificar
	 * @return
	 */
	public DatamatrixBuilder withProperty(String theKey, String theValue) {
		this.properties.setProperty(theKey, theValue);
		return this;
	}

	/**
	 * Asignar directamtente el properties
	 * 
	 * @param properties a asignar
	 *            
	 * @return
	 */
	public DatamatrixBuilder withProperties(Properties properties) {
		this.properties = properties;
		return this;
	}

	/**
	 * Modificar el tamaño del modulo
	 * 
	 * @param moduleSize
	 *            a modificar por defecto es 10
	 * @return
	 */
	public DatamatrixBuilder withModuleSize(int moduleSize) {
		this.moduleSize = moduleSize;
		return this;
	}

	/**
	 * Modificar el margen del evento
	 * 
	 * @param margin
	 *            a modificar por defecto es 8
	 * @return
	 */
	public DatamatrixBuilder withMargin(int margin) {
		this.margin = margin;
		return this;
	}

	/**
	 * Modificar el encoding
	 * @param encoding a modificar por defecto es base256
	 * @return
	 */
	public DatamatrixBuilder withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	/**
	 * Modificar el formato de la datamatrix
	 * @param format por defecto es nulo
	 * @return
	 */
	public DatamatrixBuilder withFormat(String format) {
		this.format = format;
		return this;
	}


	public BufferedImage build() {

		return (BufferedImage) new RDataMatrixFacade().createBarcodeImage(
				this.dataToEncote, null, this.isProcessTilde, this.moduleSize,
				this.margin, this.encoding, this.format, this.properties);
	}

}

