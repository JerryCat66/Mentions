package com.byth.lifesaver.encrypt;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Android DES加密
 * 
 * 
 * @author liutao
 */
public class DesEncrypt {
	
	/**
	 * 加密
	 * 
	 * @param key
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
    public static String encryptAES(String key, String cleartext) throws Exception {      
        byte[] rawKey = getRawKey(key.getBytes("UTF-8"));    
        
        byte[] result = encryptAES(rawKey, cleartext.getBytes("UTF-8"));
        
        return new String(Base64.encode(result, 0));
    }      
    
    /**
     * 解密
     * 
     * @param key
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String decryptAES(String key, String encrypted) throws Exception {      
    	byte[] rawKey = getRawKey(key.getBytes("UTF-8"));  
    	
    	byte[] enc = Base64.decode(encrypted, 0);
    	
    	byte[] result = decryptAES(rawKey, enc);
    	
    	return new String(result, "UTF-8");      
    }
    
    private static byte[] encryptAES(byte[] raw, byte[] clear) throws Exception {      
    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      
    	Cipher cipher = Cipher.getInstance("AES");      
    	cipher.init(Cipher.ENCRYPT_MODE, skeySpec);   
      
    	byte[] encrypted = cipher.doFinal(clear);
      
    	return encrypted;    
    }      
    
    private static byte[] decryptAES(byte[] raw, byte[] encrypted) throws Exception {      
    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
       
    	Cipher cipher = Cipher.getInstance("AES");     
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
       
    	byte[] decrypted = cipher.doFinal(encrypted);
       
    	return decrypted;      
    }
     
    private static byte[] getRawKey(byte[] seed) throws Exception {      
        KeyGenerator kgen = KeyGenerator.getInstance("AES");      

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");      
        sr.setSeed(seed);      
        
        kgen.init(128, sr); // 192 and 256 bits may not be available      
        
        SecretKey skey = kgen.generateKey();      
        
        byte[] raw = skey.getEncoded();      
        
        return raw;      
    }      
    
}