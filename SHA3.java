
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256;
import java.io.UnsupportedEncodingException;

public class SHA3 {

    DigestSHA3 sha3_256 = null;

    public SHA3() {
        this.sha3_256 = new Digest256();
    }

    public void update(byte[] bytes) {
        this.sha3_256.update(bytes);
    }

    public void updateString(String message) throws UnsupportedEncodingException {
        this.update(message.getBytes());
    }

    public byte[] digest() {
        return this.sha3_256.digest();
    }

    public String digestToString() {
        return Utils.bytesToString(this.digest());
    }

    public static String stringToHash(String message) throws UnsupportedEncodingException {
        SHA3 sha3 = new SHA3();
        sha3.updateString(message);
        return sha3.digestToString();
    }
}