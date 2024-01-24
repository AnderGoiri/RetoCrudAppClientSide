/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class Encrypt {

    public String encrypt(String password) {
        String encryptedPassword = null;
        try {
            // Agrega el proveedor Bouncy Castle
            Security.addProvider(new BouncyCastleProvider());

            // Recupera la clave privada
            byte[] privateKeyBytes;
            try (FileInputStream fis = new FileInputStream(
                    Paths.get(System.getProperty("user.home"),
                            "\\esportshub\\security", "privateKey.der")
                            .toString()
            )) {
                privateKeyBytes = new byte[fis.available()];
                fis.read(privateKeyBytes);
            }

            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // "BC" indica el uso del proveedor Bouncy Castle
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Configura el algoritmo ECIES
            Cipher cipher = Cipher.getInstance("ECIES", "BC");

            // Configura los parámetros de la curva elíptica
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey, ecSpec);

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
