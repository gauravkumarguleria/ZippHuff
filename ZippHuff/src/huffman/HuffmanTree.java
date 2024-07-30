package huffman;

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class HuffmanTree {
    private HuffmanNode root;

    public HuffmanTree(Map<Byte, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode merged = new HuffmanNode((byte) 0, left.frequency + right.frequency);
            merged.left = left;
            merged.right = right;
            priorityQueue.add(merged);
        }

        root = priorityQueue.poll();
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public Map<Byte, String> buildEncodingMap() {
        Map<Byte, String> encodingMap = new HashMap<>();
        buildEncodingMapHelper(root, "", encodingMap);
        return encodingMap;
    }

    private void buildEncodingMapHelper(HuffmanNode node, String code, Map<Byte, String> encodingMap) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            encodingMap.put(node.data, code);
        }

        buildEncodingMapHelper(node.left, code + "0", encodingMap);
        buildEncodingMapHelper(node.right, code + "1", encodingMap);
    }
}
