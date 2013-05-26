package com.aeeventos.core.bean.enums;

import com.aeeventos.util.IntEnumUserType;

/**
 * @author fferrero
 *
 * Clase que permite el parseo de la clase EstadoEventoEnum
 * para que pueda ser interpretada por el .hbm.xml y se guarde como
 * Integer en la base de datos
 */

public class HibEstadoEvento extends IntEnumUserType<EstadoEventoEnum> { 
    public HibEstadoEvento() { 
        // we must give the values of the enum to the parent.
        super(EstadoEventoEnum.class, EstadoEventoEnum.values()); 
    } 
}
