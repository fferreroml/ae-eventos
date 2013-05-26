package com.aeeventos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConvertirMD5 {
	
	
	/**
     * Computes the MD5 sum of a <tt>String</tt>. This method computes the MD5
     * sum of a string and returns a string with the sum. The returned string
     * has the same format of the one returned by the posgrges' md5 function.
     * 
     * @param s
     *            The string to compute the md5 sum.
     * @return A string with the md5 sum of <tt>s</tt>
     * @throws NoSuchAlgorithmException
     *             if the MD5 algorithm is not available in the caller's
     *             environment.
     * @see Postgres' md5 function at
     *      http://www.postgresql.org/docs/8.0/static/functions
     *      -string.html#FUNCTIONS-STRING-OTHER
     */
    public static String stringToMD5(String s) throws NoSuchAlgorithmException {
	String md5String = "";

	MessageDigest md = null;

	md = MessageDigest.getInstance("MD5");

	byte[] md5Bytes = md.digest(s.getBytes());

	/*
	 * The md5Bytes array contains the md5 representacion of the `s'
	 * parameter. We want the same representation as the one obtained using
	 * the md5() function from postgresql 8.0 This loop implements such
	 * conversion.
	 */
	for (int i = 0; i < md5Bytes.length; i++) {
	    /*
	     * md5Bytes[i] is a byte, hence, signed. We need to see it as an
	     * unsigned. The following trick works, but if you found a better
	     * one, feel free to use it.
	     */
	    int unsignedByte = md5Bytes[i] & 0xFF;

	    md5String += Integer.toHexString(unsignedByte);
	}

	return md5String;
    }
    
    public static void main(String args[]) {
    	try {
			System.out.println(ConvertirMD5.stringToMD5("12345"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
