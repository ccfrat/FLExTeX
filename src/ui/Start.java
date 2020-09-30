package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
        } catch (SAXParseException e) {
            JOptionPane.showMessageDialog(null,
                    "An error occurred while parsing the file. \n" +
                            "FLEx sometimes exports an XML file with \n" +
                            "markup declarations that give this program \n" +
                            "trouble. Try deleting the stuff in the second \n" +
                            "set of brackets, from \"<!DOCTYPE\" all \n" +
                            "the way through the following \">\" and try again.");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),
                    "An Error Occurred", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }
}
