package huffman;

public class HuffmanNode {
    int frequency;
    byte data;

    char character;
    HuffmanNode left, right;

    public HuffmanNode(byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

}
