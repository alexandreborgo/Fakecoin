
import java.io.UnsupportedEncodingException;

public class Fakecoin {
    public static void main(String[] argc) {
        Fakecoin fakecoin = new Fakecoin();
    }

    public Fakecoin() {
        try {
            System.out.println("Test of SHA-3 256:");
            System.out.println("Hash of \"\" \tis " + SHA3.stringToHash(""));
            System.out.println("Hash of \"abc\" \tis " + SHA3.stringToHash("abc"));
            System.out.println("");

            System.out.println("Hash Chain:");
            String h0 = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";            
            String h1 = SHA3.stringToHash(h0 + "a");
            String h2 = SHA3.stringToHash(h1 + "b");
            System.out.println("h0: " + h0);
            System.out.println("h1: " + h1);
            System.out.println("h2: " + h2);
            System.out.println("");

            System.out.println("Hash Chain with Proof of Work:");
            Block b0 = new Block("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
            Block b1 = new Block("a", b0);
            b1.calculateHash();
            Block b2 = new Block("b", b1);
            b2.calculateHash();

            System.out.println(b0);
            System.out.println(b1);
            System.out.println(b2);
            System.out.println("");
        }
        catch(UnsupportedEncodingException exception) {
            System.out.println("Error: message isn't encoded in UTF-8.");
        }        
    }
}