
import java.io.UnsupportedEncodingException;

public class Fakecoin {
    public static void main(String[] argc) {
        Fakecoin fakecoin = new Fakecoin();
    }

    public Fakecoin() {

        try {
            System.out.println("Hash of \"\" \tis " + SHA3.stringToHash(""));
            System.out.println("Hash of \"abc\" \tis " + SHA3.stringToHash("abc"));

            String h0 = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";            
            String h1 = SHA3.stringToHash(h0 + "a");
            String h2 = SHA3.stringToHash(h1 + "b");

            System.out.println("h0: " + h0);
            System.out.println("h1: " + h1);
            System.out.println("h2: " + h2);

            Block b0 = new Block("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
            Block b1 = new Block();
        }
        catch(UnsupportedEncodingException exception) {
            System.out.println("Error: message isn't encoded in UTF-8.");
        }
        
    }

    public boolean isFakecoinHash(String hash) {        
        if(hash.charAt(hash.length()-1) == '0' && hash.charAt(hash.length()-2) == '0') {
            return true;
        }
        return false;
    }

    public String calculateNextBlock(String lastBlockHash, String nextBlock) {
        int r = 0;
        String hash = "";
        do {
            hash = SHA3.stringToHash(lastBlockHash + nextBlock + String.valueOf(r));
            r++;
        } while(this.isFakecoinHash(hash));
        return hash;
    }
}