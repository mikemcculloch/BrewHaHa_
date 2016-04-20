package brightseer.com.brewhaha.Depricated;

//import android.util.Base64;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import brightseer.com.brewhaha.Constants;

public class AesBase64Wrapper {


    public AesBase64Wrapper() {
    }


    private int flags = Base64.NO_WRAP | Base64.URL_SAFE;

    public String encryptAndEncode(String raw) {
        try {
            Cipher c = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedVal = c.doFinal(getBytes(raw));
            return getString(Base64.encode(encryptedVal, flags));
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public String decodeAndDecrypt(String encrypted) throws Exception {
        byte[] decodedValue = Base64.decode(getBytes(encrypted), flags);
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    private byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    private Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = getBytes(Constants.IV);
        c.init(mode, generateKey(), new IvParameterSpec(iv));
        return c;
    }

    private Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = Constants.PASSWORD.toCharArray();
        byte[] salt = getBytes(Constants.SALT);
//
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }
}