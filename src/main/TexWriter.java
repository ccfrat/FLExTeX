package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TexWriter {
    private FileWriter writer;
    private char[] escapeCharsShort = {'&', '%', '$', '#', '_', '{', '}'};
    private String outputBuffer;

    public TexWriter(File out) throws IOException {
        writerInit(out.getPath());
        outputBuffer = "";
    }

    private void writerInit(String path) throws IOException {
        System.out.println("Initializing writer...");
        writer = new FileWriter(path);

    }

    public void writeTex(String tag) throws IOException {
        if (tag.contains("\n")) {
            writeBuffer();
        }
        outputBuffer = outputBuffer + tag;
    }

    public void writeBody(String body) {
        body = escapeChars(body);
        outputBuffer = outputBuffer + body;
    }

    private void writeBuffer() throws IOException {
        outputBuffer = cleanPunctuation(outputBuffer);
        writer.write(outputBuffer);
        writer.flush();
        outputBuffer = "";

    }

    private String escapeChars(String text) {
        if (!text.matches("\\w")) {
            text = text.replace("&gt;", ">");
            text = text.replace("\\", "\\textbackslash ");
            text = text.replace("^", "\\textasciicircum ");
            text = text.replace("~", "\\textasciitilde ");
            for (char c : escapeCharsShort) {
                text = text.replace(Character.toString(c), "\\" + c);
            }

        }
        return text;
    }

    private String cleanPunctuation(String text) {
        String out = text;
        out = out.replaceAll(" \\)", ")");
        out = out.replaceAll(" ,", ",");
        out = out.replaceAll(",\\)", ")");
        return out;

    }

    void writeHeader() throws IOException {

        writer.write(
                "\\documentclass{article}\n" +
                        "\\usepackage{indentfirst}\n" +
                        "\\usepackage[T1]{fontenc}\n" +
                        "\\begin{document}\n" +
                        "\\leftskip .25in\n" +
                        "\\parindent -.25in\n" +
                        "\\setcounter{secnumdepth}{0}\n"
        );

    }

    public void close() throws IOException {
        writer.write(outputBuffer);
        writer.flush();
        writer.write("\n\n\\end{document}");
        writer.close();
    }
}