package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Flow;


public class DefaultWindow extends JFrame {

    JFileChooser inputChooser;
    JFileChooser outputChooser;
    JTextField inPath;
    JTextField outPath;


    public DefaultWindow() {
        init();
    }

    private void init() {
        this.setTitle("FLExTeX");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        JPanel mainPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel inLabel = new JLabel("Input File:");
        inLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel outLabel = new JLabel("Output File:");
        outLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


        inPath = new JTextField(15);
        outPath = new JTextField(15);

        JButton inSelectButton = new JButton("Browse");
        inSelectButton.addActionListener(e -> inputFileSelect(e));
        JButton outSelectButton = new JButton("Browse");
        outSelectButton.addActionListener(e -> outputFileSelect(e));
        JButton runButton = new JButton("Go!");
        JButton cancelButton = new JButton("Cancel");

        inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        inputPanel.add(inLabel);
        inputPanel.add(inPath);
        inputPanel.add(inSelectButton);
        inputPanel.setBorder(new EmptyBorder(10, 10, 5, 10));

        outputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        outputPanel.add(outLabel);
        outputPanel.add(outPath);
        outputPanel.add(outSelectButton);
        outputPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        runButton.addActionListener(e -> runClicked(e));
        buttonPanel.add(runButton);
        cancelButton.addActionListener(e -> cancelClicked(e));
        buttonPanel.add(cancelButton);
        buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);

        this.pack();
        this.setResizable(false);

        inputChooser = new InputFileSelect();
        outputChooser = new OutputFileSelect();

    }

    void inputFileSelect(ActionEvent e) {
        inputChooser.showOpenDialog(this);
        File in = inputChooser.getSelectedFile();
        if (in != null) {
            inPath.setText(in.getPath());
        }
    }

    void outputFileSelect(ActionEvent e) {
        outputChooser.showSaveDialog(this);
        File out = outputChooser.getSelectedFile();
        if (out != null) {
            outPath.setText(out.getPath());
        }
    }

    public void runClicked(ActionEvent e) {
        if (inPath.getText().isEmpty() || outPath.getText().isEmpty()) {
            //popup lol idk
        } else {
            String in = inPath.getText();
            String out = outPath.getText();
            Start.run(new File(in), new File(out));
        }
    }

    public void cancelClicked(ActionEvent e) {
        System.exit(0);
    }
}
