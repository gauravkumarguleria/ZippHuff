package gui;

import huffman.HuffmanCompressor;
import huffman.HuffmanDecompressor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HuffmanGUI {
    private JFrame frame;
    private JTextField inputFileField;
    private JTextField outputFileField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new HuffmanGUI().createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Huffman Compressor/Decompressor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(null);

        JLabel inputFileLabel = new JLabel("Input File:");
        inputFileLabel.setBounds(10, 20, 80, 25);
        frame.add(inputFileLabel);

        inputFileField = new JTextField();
        inputFileField.setBounds(100, 20, 200, 25);
        frame.add(inputFileField);

        JButton browseInputButton = new JButton("Browse");
        browseInputButton.setBounds(310, 20, 80, 25);
        frame.add(browseInputButton);
        browseInputButton.addActionListener(new BrowseButtonListener(inputFileField));

        JLabel outputFileLabel = new JLabel("Output File:");
        outputFileLabel.setBounds(10, 60, 80, 25);
        frame.add(outputFileLabel);

        outputFileField = new JTextField();
        outputFileField.setBounds(100, 60, 200, 25);
        frame.add(outputFileField);

        JButton browseOutputButton = new JButton("Browse");
        browseOutputButton.setBounds(310, 60, 80, 25);
        frame.add(browseOutputButton);
        browseOutputButton.addActionListener(new BrowseButtonListener(outputFileField));

        JButton compressButton = new JButton("Compress");
        compressButton.setBounds(50, 100, 120, 25);
        frame.add(compressButton);
        compressButton.addActionListener(new CompressButtonListener());

        JButton decompressButton = new JButton("Decompress");
        decompressButton.setBounds(200, 100, 120, 25);
        frame.add(decompressButton);
        decompressButton.addActionListener(new DecompressButtonListener());

        frame.setVisible(true);
    }

    private class BrowseButtonListener implements ActionListener {
        private JTextField textField;

        public BrowseButtonListener(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                textField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class CompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File inputFile = new File(inputFileField.getText());
            File outputFile = new File(outputFileField.getText());
            try {
                HuffmanCompressor.compress(inputFile, outputFile);
                JOptionPane.showMessageDialog(frame, "File compressed successfully!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred during compression.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DecompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File inputFile = new File(inputFileField.getText());
            File outputFile = new File(outputFileField.getText());
            try {
                HuffmanDecompressor.decompress(inputFile, outputFile);
                JOptionPane.showMessageDialog(frame, "File decompressed successfully!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred during decompression.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
