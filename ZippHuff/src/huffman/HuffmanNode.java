package huffman;

public class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left, right;

    HuffmanNode(char character, int frequency){
        this.character = character;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    HuffmanNode(int frequency){
        this.frequency = frequency;
        this.left = this.right = null;
    }
}
