
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import org.bouncycastle.jce.spec.ECParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.KeyPair;
import java.security.Security;
import java.security.KeyPairGenerator;
import org.bouncycastle.jce.ECNamedCurveTable;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

public class Wallet {
    
    public Wallet() {
        /*ECKeyPairGenerator generator = new ECKeyPairGenerator();
        SecureRandom random = new SecureRandom();
        ECKeyGenerationParameters params = new KeyGenerationParameters(random, 256);
        generator.init(params);
        AsymmetricCipherKeyPair keypair = generator.generateKeyPair();*/
        
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime192v1");
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", "BC");
            g.initialize(ecSpec, new SecureRandom());
            KeyPair keypair = g.generateKeyPair();

            PublicKey public_key = keypair.getPublic();
            PrivateKey private_key = keypair.getPrivate();

            byte[] prik = public_key.getEncoded();
            byte[] pubk = private_key.getEncoded();

            System.out.println("Format: " + public_key.getFormat() + " " + private_key.getFormat());            
            System.out.println("Private key: " + Utils.bytesToString(prik));
            System.out.println("Public key: " + Utils.bytesToString(pubk));
            System.out.println("");

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(public_key.getEncoded());
            X509Certificate public_cert = (X509Certificate)certFactory.generateCertificate(in);

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
        catch(CertificateException exception) {
            exception.printStackTrace();
        }
    }
}