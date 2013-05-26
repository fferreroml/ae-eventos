package aeeventos.core.bean.enums;

import aeeventos.util.IntEnumUserType;


/**
 * @author fferrero
 *
 * Clase que permite el parseo de la clase EstadoEventoEnum
 * para que pueda ser interpretada por el .hbm.xml y se guarde como
 * Integer en la base de datos
 */

public class HibEstadoAcreditacion extends IntEnumUserType<EstadoAcreditacionEnum> { 
    public HibEstadoAcreditacion() { 
        // we must give the values of the enum to the parent.
        super(EstadoAcreditacionEnum.class, EstadoAcreditacionEnum.values());
    } 
}
