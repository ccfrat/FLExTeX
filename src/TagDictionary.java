import java.util.HashMap;

public class TagDictionary {
    static HashMap<String, TexTags> tags;

    TagDictionary() {
        tags = new HashMap<>();
        //new word
        tags.put("mainheadword", new TexTags("\n\\par\n\\textbf{", "} "));
        //letter heading
        tags.put("letHead", new TexTags("\n\\newpage\\section{", "}"));
        //part of speech
        tags.put("partofspeech", new TexTags("\\textit{", "} "));
        //example/quote
        tags.put("example", new TexTags("\\textit{", "} "));
        //numbered entry for different uses
        tags.put("sensenumber", new TexTags("\\textbf{", ")} "));
        //subentry/derivative - no indent
        tags.put("subentry mainentrysubentry", new TexTags("\n\\\\\n"," "));
        //alternate form type
        tags.put("complexformtype", new TexTags("\\textit{", "} "));
        //bolded word with entry
        tags.put("headword", new TexTags("\\textbf{", "} "));
        //other entry types - abbreviation, derivative
        tags.put("variantentrytype", new TexTags("\\textit{", "} "));
        //subentry without definition
        tags.put("minorentryvariant", new TexTags("\n\\par\n", ""));
        //parenthetical reference to other entry at start of definition
        tags.put("variantformentrybackrefs", new TexTags("(", ") "));
        tags.put("variantformentrybackref", new TexTags("", ", "));
        //definition
        tags.put("definitionorgloss", new TexTags("", " "));
    }

    public static TexTags parseXML(String xml) {
        String key = shortXML(xml);
        if (tags.containsKey(key)) {
            return tags.get(key);
        } else return new TexTags();
    }

    public static void numberedSections() {
        tags.replace("letHead", new TexTags("\n\\section{", "}"));
    }

    public static void unnumberedSections() {
        tags.replace("letHead", new TexTags("\n\\section*{", "}"));
    }

    public static void sectionPageBreaks() {
        tags.replace("letHead", new TexTags("\n\\newpage\\section{", "}"));
    }

    public static void sectionNoPageBreaks() {
        tags.replace("letHead", new TexTags("\n\\section{", "}"));

    }

    private static String shortXML(String xml) {
        String out = xml;
        if (xml.contains("\"")) {
            int firstQuote = xml.indexOf('\"') + 1;
            int secondQuote = xml.indexOf('\"', firstQuote + 1);
            int firstIndex = xml.indexOf("<") + 1;
            out = xml.substring(firstIndex, secondQuote+1);
        } else if(xml.contains("<")){
            out = xml.substring(xml.indexOf('<')+1, xml.indexOf('>'));
        }
        return out;
    }

    static boolean isTag(String text) {
        if(!text.contains("<")) return false;
        int finalChar = text.length()-1;
        if(!(text.charAt(finalChar)=='\"' || text.charAt(finalChar)=='?')) return false;
        else return true;
    }

    static boolean isEndTag(String text) {
        if (!isTag(text)) return false;
        if (text.charAt(1) != '/') return false;
        return true;
    }
}
