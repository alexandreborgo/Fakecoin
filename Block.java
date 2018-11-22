
import java.io.UnsupportedEncodingException;

public class Block {
    String transactions;        // transactions inside this block
    String hash;                // hash of this block
    Block previous;             // hash of the previous block
    int random;                 // the random number used to generate the hash of this block
    int id;                     // unique identifier of this block

    public Block() {
        this.transactions = "";
        this.previous = null;
        this.random = 0;
        this.hash = "";
        this.id = 0;
    }

    /* Used to add the first block of the chain */
    public Block(String hash) {
        this.hash = hash;
        this.random = 0;
        this.previous = null;
        this.transactions = "";
        this.id = 0;
    }

    /* Used to add a block */
    public Block(String transactions, Block previous) {
        this.transactions = transactions;
        this.previous = previous;
        this.id = this.previous.getId()+1;
        this.random = 0;
        this.hash = "";
    }

    /* Calculate the Fakecoin hash of this block */
    public void calculateHash() throws UnsupportedEncodingException {
        do {
            this.hash = SHA3.stringToHash(this.previous.getHash() + this.transactions + String.valueOf(this.random));
            this.random++;
        } while(!Utils.isFakecoinHash(hash));
    }

    public String getHash() {
        return this.hash;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String tostring = "";
        tostring += "Block n°" + this.id + "\n";
        tostring += "Hash: " + this.hash + "\n";
        tostring += "Random: " + this.random + "\n";
        return tostring;
    }
}