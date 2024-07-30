package huffman;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class HuffmanTree {
    private HuffmanNode root;
 
    public HuffmanTree(Map<Byte,Integer> frequencyMap){
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        

    }
}
