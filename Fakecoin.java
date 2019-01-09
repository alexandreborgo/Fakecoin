
import java.io.UnsupportedEncodingException;

public class Fakecoin {
    public static void main(String[] argc) {
        Fakecoin fakecoin = new Fakecoin();
    }

    public Fakecoin() {
        try {
            System.out.println("******************** Test of SHA-3 256 ********************");
            System.out.println("Hash of \"\" \tis " + SHA3.stringToHash(""));
            System.out.println("Hash of \"abc\" \tis " + SHA3.stringToHash("abc"));
            System.out.println("");


            
            System.out.println("******************** Hash Chain ********************");
            String h0 = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
            String h1 = SHA3.stringToHash(h0 + "a");
            String h2 = SHA3.stringToHash(h1 + "b");
            System.out.println("h0: " + h0);
            System.out.println("h1: " + h1);
            System.out.println("h2: " + h2);
            System.out.println("");
            System.out.println("");


            System.out.println("******************** Hash Chain with Proof of Work ********************");
            Block b0 = new Block("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
            System.out.println(b0);
            System.out.println("");
            Block b1 = new Block("a", b0);
            b1.calculateHash();
            System.out.println(b1);
            System.out.println("");
            Block b2 = new Block("b", b1);
            b2.calculateHash();
            System.out.println(b2);
            System.out.println("");
            System.out.println("");


            System.out.println("******************** Wallet generation ********************");
            Wallet w1 = Wallet.generateNewWallet();
            Wallet w2 = Wallet.generateNewWallet();
            Wallet w3 = Wallet.generateNewWallet();

            System.out.println("Wallet 1 : " + w1);
            System.out.println("Wallet 2 : " + w2);
            System.out.println("Wallet 3 : " + w3);
            System.out.println("");
            System.out.println("");


            System.out.println("******************** Blockchain of transactions ********************");
            
            Block block_0 = new Block("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
            System.out.println(block_0);
            System.out.println("");
            System.out.println("");

            Transaction t1 = new Transaction(w1.getPublicKey(), 10);
            System.out.println("Add new transaction: " + t1);
            System.out.println("");
            Block block_1 = new Block(t1.toString(), block_0);
            block_1.calculateHash();
            System.out.println(block_1);
            System.out.println("");
            System.out.println("");
            
            Transaction t2 = w1.generateNewTransaction(w2.getPublicKey(), 5); 
            System.out.println("Add new transaction: " + t2);
            System.out.println("");
            Block block_2 = new Block(t2 + " || " + t2.getSignature(), block_1);
            block_2.calculateHash();
            System.out.println(block_2);
            Utils.isValidTransaction(t2);
            System.out.println("");
            System.out.println("");
            
            Transaction t3 = w1.generateNewTransaction(w3.getPublicKey(), 5);
            System.out.println("Add new transaction: " + t3);
            System.out.println("");
            Block block_3 = new Block(t3 + " || " + t3.getSignature(), block_2);
            block_3.calculateHash();
            System.out.println(block_3);
            Utils.isValidTransaction(t3);
            System.out.println("");
            System.out.println("");

            System.out.println("******************** Check blockchain validity ********************");
            
            System.out.println("Checking 1st block...");
            if(!block_0.getHash().equals(("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"))) {
                System.out.println("1st block is not valid");
                System.exit(0);
            }
            System.out.println("1st block is valid");

            int random;
            String transaction;
            String hash;
            String calculatedHash;

            System.out.println("Checking 2nd block...");
            random = block_1.getRandom();
            transaction = block_1.getTransaction();
            hash = block_1.getHash();
            calculatedHash = SHA3.stringToHash(block_0.getHash() + transaction + String.valueOf(random));
            if(!hash.equals(calculatedHash) && Utils.isFakecoinHash(hash)) {
                System.out.println("2nd block is not valid");
                System.exit(0);
            }
            System.out.println("2nd block is valid");

            System.out.println("Checking 3rd block...");
            random = block_2.getRandom();
            transaction = block_2.getTransaction();
            hash = block_2.getHash();
            calculatedHash = SHA3.stringToHash(block_1.getHash() + transaction + String.valueOf(random));
            if(!hash.equals(calculatedHash) && Utils.isFakecoinHash(hash)) {
                System.out.println("3rd block is not valid");
                System.exit(0);
            }
            System.out.println("3rd block is valid");

            System.out.println("Checking 4th block...");
            random = block_3.getRandom();
            transaction = block_3.getTransaction();
            hash = block_3.getHash();
            calculatedHash = SHA3.stringToHash(block_2.getHash() + transaction + String.valueOf(random));
            if(!hash.equals(calculatedHash) && Utils.isFakecoinHash(hash)) {
                System.out.println("4th block is not valid");
                System.exit(0);
            }
            System.out.println("4th block is valid");

            
        }
        catch(UnsupportedEncodingException exception) {
            System.out.println("Error: message isn't encoded in UTF-8.");
        }        
    }
}