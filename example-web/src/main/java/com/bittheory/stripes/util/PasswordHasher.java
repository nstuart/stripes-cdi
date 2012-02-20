/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Ideas taken from <a
 * href="https://www.owasp.org/index.php/Hashing_Java">OWASP</a> site.
 *
 * @author Nick Stuart
 */
public class PasswordHasher {

    /**
     * Encrypts the password given to be stored in the database.
     * 
     * @param password
     * @param salt
     * @return 
     */
    public String encrypt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(base64ToBytes(salt));
            byte[] enc = digest.digest(password.getBytes("UTF-8"));
            for (int i = 0; i < 100; i++) {
                digest.reset();
                enc = digest.digest(enc);
            }
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(enc);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 
     * @return Random SALT value to store with a user when generating a new
     * password.
     */
    public String getRandomSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] rand = new byte[8];
            random.nextBytes(rand);
            return bytesToBase64(rand);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private byte[] base64ToBytes(String str) {
        BASE64Decoder dec = new BASE64Decoder();
        try {
            return dec.decodeBuffer(str);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String bytesToBase64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }
}
