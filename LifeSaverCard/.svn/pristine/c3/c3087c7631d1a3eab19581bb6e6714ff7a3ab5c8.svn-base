package com.byth.lifesaver.encrypt;


import android.os.Build;
import android.util.Base64;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class DESUtils {
    private static Key key;
    private static String KEY_STR = "mykey";


    static {
        try {
            SecureRandom secureRandom;
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            //安卓4.2版本以上获取SecureRandom方式改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            } else {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            }
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对字符串进行加密，返回BASE64的加密字符串
     * <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getEncryptString(String str) {
        try {
            byte[] strBytes = str.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return Base64.encodeToString(encryptStrBytes, Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 对BASE64加密字符串进行解密
     * <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDecryptString(String str) {
        try {
            byte[] strBytes = Base64.decode(str, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return new String(encryptStrBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}