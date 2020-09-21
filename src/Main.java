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
            XMLReader testReader = new XMLReader(test);
            TexWriter testWriter = new TexWriter(outPath);
            new TagDictionary();
            testReader.setWriter(testWriter);
            testReader.processAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
