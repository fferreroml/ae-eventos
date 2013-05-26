package aeeventos.core.bean.enums;

import aeeventos.util.IntEnumUserType;

/**
 * @author fferrero
 *
 *Clase que permite el parseo de la clase RolEnum
 *para que pueda ser interpretada por el .hbm.xml y se guarde como
 * Integer en la base de datos
 */


public class HibRol extends IntEnumUserType<RolEnum> { 
    public HibRol() { 
        // we must give the values of the enum to the parent.
        super(RolEnum.class,RolEnum.values()); 
    } 
}
