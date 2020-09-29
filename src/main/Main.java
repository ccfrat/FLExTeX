package main;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        test();
    }


    static void test() {
            run("in.xml","out.tex");
    }

    public static void run(String inPath, String outPath) {

        try {
            String test = inPath;
            XMLReader testReader = new XMLReader(new File(test));
            TexWriter testWriter = new TexWriter(new File(outPath));
            new TagDictionary();
            testReader.setWriter(testWriter);
            testReader.processAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
