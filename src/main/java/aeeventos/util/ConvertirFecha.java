package aeeventos.util;


import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * Column decorator que transforma a el formato dd/MM/yyyy la fecha para mostrarla en la tabla
 * 
 * @author fferrero
 */
public class ConvertirFecha  implements DisplaytagColumnDecorator {


	private FastDateFormat dateFormat = FastDateFormat
			.getInstance("dd/MM/yyyy"); 


	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum media) throws DecoratorException {
		Date date = (Date) columnValue;
		return this.dateFormat.format(date);
	}
}
