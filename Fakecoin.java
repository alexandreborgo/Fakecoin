
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
            System.out.println(b0);
            Block b1 = new Block("a", b0);
            b1.calculateHash();
            System.out.println(b1);
            Block b2 = new Block("b", b1);
            b2.calculateHash();
            System.out.println(b2);
            System.out.println("");

            Wallet w1 = Wallet.generateNewWallet();
            Wallet w2 = Wallet.generateNewWallet();

            System.out.println(w1);
            System.out.println(w2);
            
            Transaction t1 = w1.generateNewTransaction(w2.getPublicKey(), 10);
            
            if(Transaction.verifyTransaction(t1.getTransaction(), t1.getSignature())) {
                System.out.println("Transaction is valid.");
            }
            else {
                System.out.println("Transaction is invalid.");
            }
        }
        catch(UnsupportedEncodingException exception) {
            System.out.println("Error: message isn't encoded in UTF-8.");
        }        
    }
}