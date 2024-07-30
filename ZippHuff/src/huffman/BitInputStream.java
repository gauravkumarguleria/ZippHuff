package huffman;

import java.io.*;

public class BitInputStream implements Closeable {
    private InputStream input;
    private int nextBits;
    private int numBitsRemaining;
    private boolean isEndOfStream;

    public BitInputStream(InputStream in) {
        input = in;
        numBitsRemaining = 0;
        isEndOfStream = false;
    }

    public boolean readBit() throws IOException {
        if (isEndOfStream)
            throw new EOFException("End of stream");

        if (numBitsRemaining == 0) {
            nextBits = input.read();
            if (nextBits == -1) {
                isEndOfStream = true;
                throw new EOFException("End of stream");
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return ((nextBits >> numBitsRemaining) & 1) != 0;
    }

    public boolean hasNext() {
        return !isEndOfStream;
    }

    public byte readByte() throws IOException {
        if (numBitsRemaining == 0) {
            return (byte) input.read();
        }
        throw new IOException("BitInputStream is not byte-aligned");
    }

    public int readInt() throws IOException {
        return (readByte() << 24) | ((readByte() & 0xFF) << 16) | ((readByte() & 0xFF) << 8) | (readByte() & 0xFF);
    }

    @Override
    public void close() throws IOException {
        input.close();
    }
}
