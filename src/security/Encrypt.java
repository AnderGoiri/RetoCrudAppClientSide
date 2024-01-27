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

            byte[] publicKeyBytes;
            try (FileInputStream fis = new FileInputStream(
                    Paths.get(System.getProperty("user.home"),
                            "\\esportshub\\security", "publicKey.der")
                            .toString()
            )) {
                publicKeyBytes = new byte[fis.available()];
                fis.read(publicKeyBytes);
            }

            // Convierte los bytes de la clave privada en un objeto PrivateKey
            KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
            PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(publicKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(publicKeySpec);

            // Configura el algoritmo ECIES
            Cipher cipher = Cipher.getInstance("ECIES", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

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