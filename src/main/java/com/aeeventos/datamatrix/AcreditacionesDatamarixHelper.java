package com.aeeventos.datamatrix;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.aeeventos.core.bean.Acreditacion;


public class AcreditacionesDatamarixHelper {

	private static final String EXTENSION_PNG = "png";
	private static final String GUION_MEDIO = "-";
	
	public static void generateDatamatrix(Acreditacion acreditacion, String contexto) throws IOException {

		String dataToEncode = acreditacion.getEvento().getNombre()
				.concat(GUION_MEDIO)
				.concat(acreditacion.getAsistente().getDni());
		
		BufferedImage img = new DatamatrixBuilder(dataToEncode).build();
		File imagen = new File(contexto);
		ImageIO.write(img, EXTENSION_PNG, imagen);
	}

}
