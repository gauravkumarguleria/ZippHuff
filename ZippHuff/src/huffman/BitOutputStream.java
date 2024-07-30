package huffman;

import java.io.*;

public class BitOutputStream implements Closeable {
    private OutputStream output;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream out) {
        output = out;
        currentByte = 0;
        numBitsFilled = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        currentByte = (currentByte << 1) | (bit ? 1 : 0);
        numBitsFilled++;
        if (numBitsFilled == 8) {
            output.write(currentByte);
            numBitsFilled = 0;
        }
    }

    public void writeByte(byte b) throws IOException {
        if (numBitsFilled == 0) {
            output.write(b);
        } else {
            throw new IOException("BitOutputStream is not byte-aligned");
        }
    }

    public void writeInt(int value) throws IOException {
        writeByte((byte) (value >>> 24));
        writeByte((byte) (value >>> 16));
        writeByte((byte) (value >>> 8));
        writeByte((byte) value);
    }

    @Override
    public void close() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(false);
        }
        output.close();
    }
}
