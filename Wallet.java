
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;

import org.bouncycastle.util.encoders.Hex;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.security.KeyPair;
import java.security.Security;
import java.security.SecureRandom;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;

import java.util.Base64;

public class Wallet {

    /*
        generating ec private key with openssl :    openssl ecparam -name prime256v1 -genkey -out prikey.pem -noout
        generating public key from private :        openssl ec -in prikey.pem -pubout -out pubkey.pem

        sign a message :     openssl dgst -sha256 -sign prikey.pem -out sign.txt message.txt
        verify a sign :     openssl dgst -sha256 -verify pubkey.pem -signature sign.txt message.txt
    */
    
    public String name;

    public Wallet(String name) {
        this.name = name;
        
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecSpec, new SecureRandom());
            KeyPair keypair = g.generateKeyPair();

            PublicKey public_key = keypair.getPublic();
            PrivateKey private_key = keypair.getPrivate();
            
            System.out.println("Private key " + private_key.getAlgorithm() + " (" + private_key.getFormat() + "): " + Hex.toHexString(private_key.getEncoded()));
            System.out.println("Public key " + public_key.getAlgorithm() + " (" + public_key.getFormat() + "): " + Hex.toHexString(public_key.getEncoded()));
            System.out.println("");

            FileOutputStream fos = new FileOutputStream("prikey.pem");
            fos.write("-----BEGIN PRIVATE KEY-----".getBytes());
            fos.write(Base64.getMimeEncoder().encodeToString(private_key.getEncoded()).getBytes());
            fos.write("-----END PRIVATE KEY-----".getBytes());
            fos.close();

            fos = new FileOutputStream("pubkey.pem");
            fos.write("-----BEGIN PUBLIC KEY-----\n".getBytes());
            fos.write(Base64.getMimeEncoder().encodeToString(public_key.getEncoded()).getBytes());
            fos.write("\n-----END PUBLIC KEY-----\n".getBytes());
            fos.close();
        }
        catch(InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
        }
        catch(NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        catch(NoSuchProviderException exception) {
            exception.printStackTrace();
        }
        catch(FileNotFoundException exception) {
            exception.printStackTrace();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}