package server.business.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    private static final Logger logger = LogManager.getLogger(PasswordHash.class.getName());

    private final static String SALT;
    private static MessageDigest md;
    static {
        SALT = "HaSa34FDSd@##";
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Hash algorithm not found", e);

        }
    }

    public static byte[] passwordHash(String pass) {
        String hash = pass + SALT;
        return md.digest(hash.getBytes());
    }

}
