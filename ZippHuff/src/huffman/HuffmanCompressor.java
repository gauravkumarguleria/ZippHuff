package huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompressor {
    public static void compress(File inputFile, File outputFile) throws IOException {
        byte[] fileBytes = readFile(inputFile);
        Map<Byte, Integer> frequencyMap = buildFrequencyMap(fileBytes);
        HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
        Map<Byte, String> encodingMap = huffmanTree.buildEncodingMap();
        
        BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream(outputFile));
        writeHeader(bitOutputStream, encodingMap);
        writeCompressedData(bitOutputStream, fileBytes, encodingMap);
        bitOutputStream.close();
    }

    private static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        fis.read(fileBytes);
        fis.close();
        return fileBytes;
    }

    private static Map<Byte, Integer> buildFrequencyMap(byte[] fileBytes) {
        Map<Byte, Integer> frequencyMap = new HashMap<>();
        for (byte b : fileBytes) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }
        return frequencyMap;
    }

    private static void writeHeader(BitOutputStream bitOutputStream, Map<Byte, String> encodingMap) throws IOException {
        bitOutputStream.writeInt(encodingMap.size());
        for (Map.Entry<Byte, String> entry : encodingMap.entrySet()) {
            bitOutputStream.writeByte(entry.getKey());
            bitOutputStream.writeInt(entry.getValue().length());
            for (char c : entry.getValue().toCharArray()) {
                bitOutputStream.writeBit(c == '1');
            }
        }
    }

    private static void writeCompressedData(BitOutputStream bitOutputStream, byte[] fileBytes, Map<Byte, String> encodingMap) throws IOException {
        for (byte b : fileBytes) {
            String code = encodingMap.get(b);
            for (char c : code.toCharArray()) {
                bitOutputStream.writeBit(c == '1');
            }
        }
    }
}
