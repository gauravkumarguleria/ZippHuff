package huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecompressor {
    public static void decompress(File inputFile, File outputFile) throws IOException {
        BitInputStream bitInputStream = new BitInputStream(new FileInputStream(inputFile));
        Map<String, Byte> decodingMap = readHeader(bitInputStream);
        writeDecompressedData(bitInputStream, decodingMap, outputFile);
        bitInputStream.close();
    }

    private static Map<String, Byte> readHeader(BitInputStream bitInputStream) throws IOException {
        Map<String, Byte> decodingMap = new HashMap<>();
        int mapSize = bitInputStream.readInt();
        for (int i = 0; i < mapSize; i++) {
            byte b = bitInputStream.readByte();
            int codeLength = bitInputStream.readInt();
            StringBuilder code = new StringBuilder();
            for (int j = 0; j < codeLength; j++) {
                code.append(bitInputStream.readBit() ? '1' : '0');
            }
            decodingMap.put(code.toString(), b);
        }
        return decodingMap;
    }

    private static void writeDecompressedData(BitInputStream bitInputStream, Map<String, Byte> decodingMap, File outputFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);
        StringBuilder code = new StringBuilder();
        while (bitInputStream.hasNext()) {
            code.append(bitInputStream.readBit() ? '1' : '0');
            if (decodingMap.containsKey(code.toString())) {
                fos.write(decodingMap.get(code.toString()));
                code.setLength(0);
            }
        }
        fos.close();
    }
}
