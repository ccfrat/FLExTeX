package ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class InputFileSelect extends JFileChooser {

    public InputFileSelect() {
        this.setFileFilter(new XmlFilter());
        this.setAcceptAllFileFilterUsed(false);
    }

    class XmlFilter extends FileFilter {
        @Override
        public boolean accept(File f) {
            if (f.getName().toLowerCase().endsWith(".xml")) return true;
            else return f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "FLEx Dictionary (*.xml)";
        }
    }

}