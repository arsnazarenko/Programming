package server.business.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class PasswordHash {
    private final static String SALT;
    private static MessageDigest md;
    static {
        SALT = "HaSa34FDSd@##";
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] passwordHash(String pass) {
        String hash = pass + SALT;
        return md.digest(hash.getBytes());
    }

}
