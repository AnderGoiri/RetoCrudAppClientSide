package security;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Encrypt {

    public String encrypt(String password) {
        String encryptedPassword = null;
        try {
            // Add Bouncy Castle provider
            Security.addProvider(new BouncyCastleProvider());

            byte[] publicKeyBytes;
            try (FileInputStream fis = new FileInputStream(
                    System.getProperty("user.home") + "\\esportshub\\security\\publicKey.der")) {
                publicKeyBytes = new byte[fis.available()];
                fis.read(publicKeyBytes);
            }

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Configura el algoritmo ECIES
            Cipher cipher = Cipher.getInstance("ECIES");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // Convierte la contraseña a bytes y encripta
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            // Codifica los bytes encriptados a Base64
            encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
}
