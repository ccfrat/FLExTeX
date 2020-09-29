package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Start extends Application {

    public void start(Stage stage) {
        JFrame start = new DefaultWindow();
        start.setLocationRelativeTo(null);
        start.setVisible(true);
    }

    static void run(File in, File out) {
        try {
            main.XMLReader testReader = new main.XMLReader(in);
            main.TexWriter testWriter = new TexWriter(out);
            new TagDictionary();
            testReader.setWriter(testWriter);
            testReader.processAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
