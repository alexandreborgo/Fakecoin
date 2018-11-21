
public class Block {
    String value;
    String hash;
    String previousBlockHash;
    int random;

    public Block() {
        this.value = "";
        this.previousBlockHash = "";
        this.random = 0;
        this.hash = "";
    }

    public Block(String hash) {
        this.hash = hash;
        this.random = 0;
        this.previousBlockHash = "";
        this.value = "";
    }

    public Block(String value, String previousBlockHash) {
        this.value = value;
        this.previousBlockHash = previousBlockHash;
        this.random = 0;
        this.hash = "";
    }
}