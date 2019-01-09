
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.ECNamedCurveTable;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.ECDSASigner;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import org.bouncycastle.util.encoders.Hex;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
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

    private ECPrivateKeyParameters private_key;
    private ECPublicKeyParameters public_key;

    public Wallet() {

    }

    public Transaction generateNewTransaction(ECPublicKeyParameters to, int amount) {
        Transaction transaction = new Transaction(this.public_key, to, amount);
        transaction.signTransaction(this.private_key);
        return transaction;
    }

    public static Wallet generateNewWallet() {
        Wallet wallet = new Wallet();
        
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        X9ECParameters x9EcParams = SECNamedCurves.getByName("secp256k1");
        ECDomainParameters ecDomainParams = new ECDomainParameters(
            x9EcParams.getCurve(),
            x9EcParams.getG(),
            x9EcParams.getN(),
            x9EcParams.getH()
        );

        SecureRandom srandom = new SecureRandom();
        ECKeyGenerationParameters keyGenerationParams = new ECKeyGenerationParameters(ecDomainParams, srandom);

        keyPairGenerator.init(keyGenerationParams);
        AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();

        wallet.public_key = (ECPublicKeyParameters)keyPair.getPublic();
        wallet.private_key = (ECPrivateKeyParameters)keyPair.getPrivate();

        return wallet;
    }

    public ECPublicKeyParameters getPublicKey() {
        return this.public_key;
    }

    public String toString() {
        String wallet = "";
        wallet += Hex.toHexString(this.public_key.getQ().getEncoded(true));
        return wallet;
    }
}