package security;

import exceptions.EncryptionException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * The Encrypt class provides a method to encrypt a password using the ECIES
 * algorithm with a public key. The encrypted password is returned in Base64
 * encoding format. Bouncy Castle provider is used for cryptographic operations.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Encrypt {

    private static final Logger LOGGER = Logger.getLogger(Encrypt.class.getName());

    /**
     * Encrypts the given password using the ECIES algorithm with a public key.
     *
     * @param password The password to be encrypted.
     * @return The encrypted password in Base64 encoding format, or null if
     * encryption fails.
     * @throws EncryptionException If an error occurs during encryption.
     */
    public String encrypt(String password) throws EncryptionException {
        String encryptedPassword = null;
        try {
            // Add Bouncy Castle provider
            Security.addProvider(new BouncyCastleProvider());

            byte[] publicKeyBytes;
            try (InputStream fis = getClass().getClassLoader().getResourceAsStream("security/publicKey.der")) {
                if (fis != null) {
                    publicKeyBytes = new byte[fis.available()];
                    fis.read(publicKeyBytes);
                } else {
                    throw new EncryptionException("Public key file not found");
                }
            } catch (IOException e) {
                throw new EncryptionException(e.getMessage());
            }

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Configure the ECIES algorithm
            Cipher cipher = Cipher.getInstance("ECIES");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Convert the password to bytes and encrypt
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            // Encode the encrypted bytes to Base64
            encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while encrypting password", e);
            throw new EncryptionException(e.getMessage());
        }
        return encryptedPassword;
    }
}
