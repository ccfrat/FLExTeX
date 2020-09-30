package ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class OutputFileSelect extends JFileChooser {

    public OutputFileSelect() {
        this.setFileFilter(new TexFilter());
        this.setAcceptAllFileFilterUsed(false);
        this.setFileSelectionMode(FILES_ONLY);
    }

    @Override
    public void approveSelection() {
        if (getDialogType() == SAVE_DIALOG) {
            File file = getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".tex")) {
                file = new File(file.getPath() + ".tex");
                setSelectedFile(file);
            }

            if (file.exists()) {
                int result = JOptionPane.showConfirmDialog(this,
                        "This file already exists. OK to overwrite?");
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.NO_OPTION:
                        super.cancelSelection();
                        setSelectedFile(null);
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        super.cancelSelection();
                        setSelectedFile(null);
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        super.cancelSelection();
                        setSelectedFile(null);
                        return;
                }
            } else {
                super.approveSelection();
            }
        }
    }

    class TexFilter extends FileFilter {
        @Override
        public boolean accept(File f) {
            if(f.getName().toLowerCase().endsWith(".tex")) return true;
            else return f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "LaTeX file (*.tex)";
        }
    }
}
