package edu.hw8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PasswordCracker {

    private static final String PNF = "Password not found";

    private PasswordCracker() {
    }

    private static final Logger LOGGER = Logger.getLogger(QuoteServer.class.getName());

    private static Map<String, String> passwordDatabase = new HashMap<>();
    private static final int MAX_PASSWORD_LENGTH = 4;

    static {
        passwordDatabase.put("a.v.petrov", "81dc9bdb52d04dc20036dbd8313ed055"); // 1234
        passwordDatabase.put("v.v.belov", "2bfbed69c9ddd84f97def85b47e59a30");  // 539b
        passwordDatabase.put("a.s.ivanov", "8f7d15a8d28ec153835ef4bfc428d5e4"); // vd
        passwordDatabase.put("k.p.maslov", "4e4d6c332b6fe62a63afe56171fd3725"); // haha
        passwordDatabase.put("a.p.maslov", "236eb95c2806c6c711a77068c8af5c70"); // lamb
        passwordDatabase.put("b.p.maslov", "dce7c4174ce9323904a934a486c41288"); // lock
        passwordDatabase.put("c.p.maslov", "3a22c9ea9a3039d180e0a514a5a3b619"); // lego
        passwordDatabase.put("d.p.maslov", "33c9b363aa666f43190fa83fce543646"); // pair
        passwordDatabase.put("s.p.maslov", "2a402dd1f1d3ac729528eabdfd2650d0"); // tear
        passwordDatabase.put("q.p.maslov", "1068c6e4c8051cfd4e9ea8072e3189e2"); // 410
        passwordDatabase.put("w.p.maslov", "9ad1361f841f9df6a862e3f5fa5f3a2c"); // 872s
        passwordDatabase.put("e.p.maslov", "82d82ab4faba8935b109f9df46807ee1"); // pol7
        passwordDatabase.put("r.p.maslov", "c892ba238c98835d4d53a3faed43ee52"); // oppo
        passwordDatabase.put("y.p.maslov", "c4ca4238a0b923820dcc509a6f75849b"); // 1
    }

    static void singleThreadedCrackPasswords() {
        // Implementing Single Threaded Password Recovery
        for (Map.Entry<String, String> entry : passwordDatabase.entrySet()) {
            String username = entry.getKey();
            String hashedPassword = entry.getValue();
            String crackedPassword = nextPassword(hashedPassword, 0);
            LOGGER.info("Username: " + username + ", Password: " + crackedPassword);
        }
    }

    private static String nextPassword(String hashedPassword, int length) {
        // We assume that passwords consist only of letters of the Latin alphabet and numbers
        char[] possibleCharacters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        return generatePassword(hashedPassword, possibleCharacters, "", length);
    }

    private static String generatePassword(
        String hashedPassword, char[] possibleCharacters,
        String currentPassword, int length
    ) {
        if (length == MAX_PASSWORD_LENGTH) {
            return PNF;
        }

        for (char possibleChar : possibleCharacters) {
            String passwordAttempt = currentPassword + possibleChar;
            String generatedHash = md5(passwordAttempt);
            if (hashedPassword.equals(generatedHash)) {
                return passwordAttempt;
            }

            String result = generatePassword(hashedPassword, possibleCharacters,
                passwordAttempt, length + 1
            );
            if (!result.equals(PNF)) {
                return result;
            }
        }

        return PNF;
    }

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred: NoSuchAlgorithmException", e);
            return null;
        }
    }
}
