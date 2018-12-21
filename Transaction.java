import java.lang.invoke.StringConcatFactory;
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
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

public class Transaction {
    ECPublicKeyParameters from;
    ECPublicKeyParameters to;
    int amount = 0;
    byte[] signature;

    public Transaction(ECPublicKeyParameters from, ECPublicKeyParameters to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public String getTransaction() {
        String transaction = "";
        //transaction = Hex.toHexString(this.from.getEncoded()) + " " + Hex.toHexString(this.to.getEncoded()) + " " + this.amount;
        return transaction;
    }

    public String toString() {
        String transaction = "";
        //transaction = Hex.toHexString(this.from.getEncoded()) + " " + Hex.toHexString(this.to.getEncoded()) + " " + this.amount + " " + this.signature != null ? Hex.toHexString(this.signature) : "";
        return transaction;
    }

    public static boolean verifyTransaction(String transaction, byte[] arrby) {
        /*String[] tsplit = transaction.split(" ");
        String from = tsplit[0];
        String string3 = tsplit[1];
        String string4 = tsplit[2];
        byte[] signatureBytes = new BigInteger(from, 16).toByteArray();
        
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
            AsymmetricKeyParameter publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(signatureBytes));
            Signature signature = Signature.getInstance("ECDSA", new BouncyCastleProvider());
            signature.initVerify(publicKey);
            signature.update(transaction.getBytes());
            if (signature.verify(arrby)) {
                return true;
            }
            return false;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            invalidKeySpecException.printStackTrace();
        }
        catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
        }
        catch (SignatureException signatureException) {
            signatureException.printStackTrace();
        }*/
        return false;
    }
}