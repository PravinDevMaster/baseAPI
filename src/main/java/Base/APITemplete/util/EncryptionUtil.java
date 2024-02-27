package Base.APITemplete.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Properties;


public class EncryptionUtil {
    private static String SECRET_KEY;
    private static  String SALT;
    private static final String PROPERTIES_FILE = "application.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = EncryptionUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(input);

            SECRET_KEY = properties.getProperty("secret.key");
            SALT = properties.getProperty("secret.salt");
        } catch (Exception e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }
    public static String encrypt(String strToEncrypt) {
        try {
            SecretKey secretKey = generateSecretKey();

            // Generate a random IV for each encryption
            byte[] iv = generateIV();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            // Prepend the IV to the ciphertext for later use during decryption
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            SecretKey secretKey = generateSecretKey();

            byte[] combined = Base64.getDecoder().decode(strToDecrypt);

            // Extract the IV from the combined data
            byte[] iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, iv.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] decryptedBytes = cipher.doFinal(combined, iv.length, combined.length - iv.length);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    private static SecretKey generateSecretKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static byte[] generateIV() {
        // Generate a rando m IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
