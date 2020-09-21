import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TexWriter {
    private FileWriter writer;
    private File outFile;
    private char[] escapeCharsShort = {'&','%','$','#','_','{','}'};
    private String outputBuffer;

    TexWriter(String path){
        fileInit(path);
        writerInit(path);
        outputBuffer="";
    }

    private void fileInit(String path) {
        try {
            System.out.println("Creating output file...");
            outFile = new File(path);
            if (outFile.createNewFile() && outFile.canWrite()) {
                System.out.println("Output file created: " + outFile.getName());
            } else {
                System.out.println("File already exists.");
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Error creating output file.");
        }
    }

    private void writerInit(String path) {
        try {
            System.out.println("Initializing writer...");
            writer = new FileWriter(path);
        } catch (IOException e) {
            System.out.println("Error initializing writer");
        }
    }

    public void writeTex(String tag) {
        if(tag.contains("\n")) {
            writeBuffer();
        }
        outputBuffer= outputBuffer+tag;
    }

    public void writeBody(String body) {
        body = escapeChars(body);
        outputBuffer = outputBuffer + body;
    }

    private void writeBuffer() {
        try {
            outputBuffer = cleanPunctuation(outputBuffer);
            writer.write(outputBuffer);
            writer.flush();
            outputBuffer="";
        } catch (IOException e) {
            System.out.println("Error writing to file. ");
            e.printStackTrace();
        }
    }

    private String escapeChars(String text) {
        if (!text.matches("\\w")) {
            text = text.replace("&gt;", ">");
            text = text.replace("\\", "\\textbackslash ");
            text = text.replace("^", "\\textasciicircum ");
            text = text.replace("~", "\\textasciitilde ");
            for (char c : escapeCharsShort) {
                text = text.replace(Character.toString(c), "\\"+c);
            }

        }
        return text;
    }

    private String cleanPunctuation(String text) {
        String out=text;
        out = out.replaceAll(" \\)", ")");
        out = out.replaceAll(" ,", ",");
        out = out.replaceAll(",\\)", ")");
        return out;

    }

    void writeHeader() {
        try {
            writer.write(
                    "\\documentclass{article}\n" +
                            "\\usepackage{indentfirst}\n" +
                            "\\usepackage[T1]{fontenc}\n" +
                            "\\begin{document}\n" +
                            "\\leftskip .25in\n" +
                            "\\parindent -.25in\n" +
                            "\\setcounter{secnumdepth}{0}\n"
            );
        } catch (IOException e) {
            System.out.println("Error writing to file. ");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.write(outputBuffer);
            writer.flush();
            writer.write("\n\n\\end{document}");
            writer.close();
        }catch (IOException e) {
            System.out.println("Error closing writer. ");
            e.printStackTrace();
        }
    }


}
