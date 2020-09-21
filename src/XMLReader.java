import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader extends DefaultHandler{
    private Scanner reader;
    private boolean ready;
    private TexWriter writer;
    private Stack<String> endTags = new Stack<>();
    private SAXParserFactory factory;
    private SAXParser parser;
    private File input;



    public XMLReader(String path) throws FileNotFoundException {
        if (path.contains(".xml")) {
            readerInit(path);
        } else {
            System.out.println("This is not an .xml file");
        }

    }

    private boolean readerInit(String path) {
        try {
            input = new File(path);
            if (input.canRead()) {
                System.out.println("File opened: " + input.getName());
                factory=SAXParserFactory.newInstance();
                parser=factory.newSAXParser();

                ready = true;
                return true;
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            System.out.println("Error opening input file.");
            e.printStackTrace();
        }
        return false;
    }

    public void setWriter(TexWriter w) {
        this.writer = w;
    }

    void processAll() {
        try {
            parser.parse(input, this);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() {
        writer.writeHeader();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        String xmlVal = atts.getValue(0);
        if(xmlVal==null) xmlVal="";
        TexTags texTag = TagDictionary.parseXML(xmlVal);
        writer.writeTex(texTag.getBegin());
        endTags.push(texTag.getEnd());
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        writer.writeBody((new String(ch,start,length).trim()));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        writer.writeTex(endTags.pop());
    }

    @Override public void endDocument() {
        writer.close();
    }


        void process() {
        String next = readNext();
        if (!TagDictionary.isTag(next)) {
            writer.writeBody(next);
            writer.writeTex(endTags.pop());
        } else if (TagDictionary.isEndTag(next)) {
            writer.writeTex(endTags.pop());
        } else if (TagDictionary.isTag(next)) {
            TexTags translated = TagDictionary.parseXML(next);
            writer.writeTex(translated.getBegin());
            endTags.push(translated.getEnd());
        }
    }



    private String readNext() {
        String next;
        if (reader.hasNext()) {
            next = reader.next();
            if (TagDictionary.isTag(next)) {
                next = next.concat(">");
            } else {
                next = next.substring(0, next.indexOf('<'));
            }
        } else {
            next = null;
            ready = false;
        }
        return next;
    }

    public boolean isReady() {
        return ready;

    }
}