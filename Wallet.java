
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
import java.security.Signature;
import java.security.SignatureException;
import java.security.InvalidKeyException;

import java.util.Base64;

public class Wallet {

    /*
        generating ec private key with openssl :    openssl ecparam -name prime256v1 -genkey -out prikey.pem -noout
        generating public key from private :        openssl ec -in prikey.pem -pubout -out pubkey.pem

        sign a message :     openssl dgst -sha256 -sign prikey.pem -out sign.txt message.txt
        verify a sign :     openssl dgst -sha256 -verify pubkey.pem -signature sign.txt message.txt
    */
    
    private PrivateKey private_key;
    private PublicKey public_key;

    public Wallet() {
        /*
        try {
            String message = "Hello, world!";
        }
        catch(InvalidKeyException exception) {
            exception.printStackTrace();
        }
        catch(SignatureException exception) {
            exception.printStackTrace();
        }*/
    }

    public Transaction generateNewTransaction(PublicKey to, int amount) {
        Transaction transaction = new Transaction(this.public_key, to, amount);
        String t = transaction.getTransaction();
        
        try {
            Signature sign = Signature.getInstance("ECDSA", new BouncyCastleProvider());
            sign.initSign(this.private_key);
            sign.update(t.getBytes());
            transaction.setSignature(sign.sign());
        }
        catch(NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        catch(InvalidKeyException exception) {
            exception.printStackTrace();
        }
        catch(SignatureException exception) {
            exception.printStackTrace();
        }

        return transaction;
    }

    public static Wallet generateNewWallet() {
        try {
            Wallet wallet = new Wallet();
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", new BouncyCastleProvider());
            g.initialize(ecSpec, new SecureRandom());
            KeyPair keypair = g.generateKeyPair();

            wallet.public_key = keypair.getPublic();
            wallet.private_key = keypair.getPrivate();

            return wallet;
        }
        catch(InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
        }
        catch(NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public PublicKey getPublicKey() {
        return this.public_key;
    }

    public String toString() {
        String wallet = "";
        wallet += "Private key " + this.private_key.getAlgorithm() + " (" + this.private_key.getFormat() + "): " + Hex.toHexString(private_key.getEncoded()) + "\n";
        wallet += "Public key " + this.public_key.getAlgorithm() + " (" + this.public_key.getFormat() + "): " + Hex.toHexString(public_key.getEncoded()) + "\n";
        wallet += "\n";
        return wallet;
    }
}