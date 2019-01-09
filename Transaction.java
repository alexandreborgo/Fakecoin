import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

public class Transaction {
    ECPublicKeyParameters from;
    ECPublicKeyParameters to;
    int amount = 0;
    BigInteger[] signature;
    boolean donation = false;

    public Transaction(ECPublicKeyParameters from, ECPublicKeyParameters to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Transaction(ECPublicKeyParameters to, int amount) {
        this.to = to;
        this.amount = amount;
        this.donation = true;
    }

    public void setSignature(BigInteger[] signature) {
        this.signature = signature;
    }

    public String getSignature() {
        if(donation)
            return "";
        else
            return this.signature[0] + " " + this.signature[1];
    }

    public String toString() {
        String transaction = "";
        if(donation)
            transaction = Hex.toHexString(this.to.getQ().getEncoded(true)) + " receives " + this.amount + " fakecoin";    
        else    
            transaction = Hex.toHexString(this.from.getQ().getEncoded(true)) + " sends " + this.amount + " fakecoin to " + Hex.toHexString(this.to.getQ().getEncoded(true));        
        return transaction;
    }

    public void signTransaction(ECPrivateKeyParameters private_key) {        
        try {
            ECDSASigner signer = new ECDSASigner();
            signer.init(true, private_key); // true = signing
            this.signature = signer.generateSignature(this.toString().getBytes());
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean verifyTransaction() {
        ECDSASigner verifier = new ECDSASigner();
        verifier.init(false, this.from); // false = not signing
        return verifier.verifySignature(this.toString().getBytes(), this.signature[0], this.signature[1]);
    }
}