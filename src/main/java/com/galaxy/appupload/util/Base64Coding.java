package com.galaxy.appupload.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加密解密
 * @author liuli
 *
 */
@SuppressWarnings("restriction")
public class Base64Coding {
	  private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();    
	  private static BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
	        public Base64Coding() {}   
	        /**
	         * 加密
	         * @param s
	         * @return
	         */
	        public static String encode (String s)   
	        {    
	            return encoder.encode(s.getBytes());   
	        }    
	  /**
	   * 解密
	   * @param s
	   * @return
	   */
	  public static String decode(String s) {
		try {
			byte[] temp = decoder.decodeBuffer(s);
			return new String(temp);
		} catch (IOException ioe) {
			System.out.println("解密失败");
		}
		return s;

	}   
	  /**
	   * 将对象转成String
	   * @param obj
	   * @return
	   * @throws IOException
	   */
	  public static String objectToString(Object obj) throws IOException {
		 
		   if (obj == null)
		    return null;
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   BufferedOutputStream zipOut = new BufferedOutputStream(baos);
		   ObjectOutputStream os = new ObjectOutputStream(zipOut);
		   os.writeObject(obj);
		   os.flush();
		   os.close();
		   zipOut.close();
		   return encoder.encode(baos.toByteArray());
		}
	  /**
	   * 将String转成对象
	   * @param strObj
	   * @return
	   * @throws IOException
	   * @throws ClassNotFoundException
	   */
	  public static Object stringToObject(String strObj) throws IOException, ClassNotFoundException {
		   if (strObj == null)
		    return null;
		   byte byteArr[] = decoder.decodeBuffer(strObj);
		   ByteArrayInputStream baIn = new ByteArrayInputStream(byteArr);
		   ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(baIn));
		   return objIn.readObject();
		}
	  
	  //main方法
	  public static void main(String[] args)
	  {
		  //Base64Coding base = new Base64Coding();
		  //String a = base.encode("Hello World");
		  //String b = base.decode("Y2hlbmtl");
		  //System.out.println(b);
		  //System.out.println(b);
	  }
}
