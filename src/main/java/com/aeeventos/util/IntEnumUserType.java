package com.aeeventos.util;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

import org.hibernate.usertype.UserType;


/**
 * Clase generica que permite mapear una enum a una columna INTEGER  en la BD.
 * 
 * @author fferrero
 * @version 1
 */
public class IntEnumUserType<E extends Enum<E>> implements UserType { 
    private Class<E> clazz = null;
    private E[] theEnumValues;
    
    /**
     * @param c la clase de la enum.
     * @param e los valores de la enumeracion (resultado de la invocacion de .values()).
     */
    protected IntEnumUserType(Class<E> c, E[] e) { 
        this.clazz = c; 
        this.theEnumValues = e;
    } 
 
    private static final int[] SQL_TYPES = {Types.INTEGER};
    
    /**
     * Simple mapeo a integer
     */
    public int[] sqlTypes() { 
        return SQL_TYPES; 
    } 
 
    @SuppressWarnings("unchecked")
	public Class returnedClass() { 
        return clazz; 
    } 

    /**
     * Obtencion del INTEGER de la base de datos.
     * Se itera el .values() para encontrar la correspondencia con el entero correcto.
     * 
     */
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) 
        throws HibernateException, SQLException { 
        final int val = resultSet.getShort(names[0]);
        E result = null;
        if (!resultSet.wasNull()) {
            try {
                for(int i=0; i < theEnumValues.length && result == null; i++) {
                    if (theEnumValues[i].ordinal() == val) {
                        result = theEnumValues[i];
                    }
                }
            } catch (SecurityException e) {
                result = null;
            } catch (IllegalArgumentException e) {
                result = null;
            }
        } 
        return result; 
    } 
 
    /**
     * set de INTEGER en la BD basadps en el enum.ordinal() value, Hay que prestar
     * atencion ya que puede cambiar
     */
    @SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement preparedStatement, 
      Object value, int index) throws HibernateException, SQLException { 
        if (null == value) { 
            preparedStatement.setNull(index, Types.INTEGER); 
        } else { 
            preparedStatement.setInt(index, ((Enum)value).ordinal()); 
        } 
    } 
 
    public Object deepCopy(Object value) throws HibernateException{ 
        return value; 
    } 
 
    public boolean isMutable() { 
        return false; 
    } 
 
    public Object assemble(Serializable cached, Object owner)
        throws HibernateException {
         return cached;
    } 

    public Serializable disassemble(Object value) throws HibernateException { 
        return (Serializable)value; 
    } 
 
    public Object replace(Object original, Object target, Object owner) throws HibernateException { 
        return original; 
    } 
    public int hashCode(Object x) throws HibernateException { 
        return x.hashCode(); 
    } 
    public boolean equals(Object x, Object y) throws HibernateException { 
        if (x == y) 
            return true; 
        if (null == x || null == y) 
            return false; 
        return x.equals(y); 
    } 
} 
