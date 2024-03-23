package com.custom.helper;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;

import com.custom.annotation.Encrypt;
import com.custom.model.StudentInfo;

public class EncryptionDecryptionHelper 
{

	private static final String SECRET_KEY = "YourSecretKey";
    private static final String SALT = "YourSalt";

    public static void encrypt(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Encrypt.class)) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value != null && value instanceof String) {
                        String encryptedValue = encryptValue((String) value);
                        field.set(obj, encryptedValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Encrypt.class)) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value != null && value instanceof String) {
                        String decryptedValue = decryptValue((String) value);
                        field.set(obj, decryptedValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String encryptValue(String value) {
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryptValue(String value) {
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Cipher getCipher(int cipherMode) throws Exception {
        SecretKey secretKey = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16];
        cipher.init(cipherMode, secretKey, new IvParameterSpec(iv));
        return cipher;
    }

    private static SecretKey getSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
	
}
