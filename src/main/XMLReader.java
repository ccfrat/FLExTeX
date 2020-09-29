package main;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader extends DefaultHandler {
    private Scanner reader;
    private boolean ready;
    private TexWriter writer;
    private Stack<String> endTags = new Stack<>();
    private SAXParserFactory factory;
    private SAXParser parser;
    private File input;


    public XMLReader(File in) throws IOException, ParserConfigurationException, SAXException {
        readerInit(in);
    }

    private boolean readerInit(File in) throws IOException, ParserConfigurationException, SAXException {

        this.input = in;
        if (input.canRead()) {
            System.out.println("File opened: " + input.getName());
            factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();

            ready = true;
            return true;
        } else {
            throw new IOException();
        }
    }

    public void setWriter(TexWriter w) {
        this.writer = w;
    }

    public void processAll() throws SAXException, IOException {
        parser.parse(input, this);
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            writer.writeHeader();
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        try {
            String xmlVal = atts.getValue(0);
            if (xmlVal == null) xmlVal = "";
            TexTags texTag = TagDictionary.parseXML(xmlVal);
            writer.writeTex(texTag.getBegin());
            endTags.push(texTag.getEnd());
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            throw new SAXException(e);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        writer.writeBody((new String(ch, start, length).trim()));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            writer.writeTex(endTags.pop());
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            throw new SAXException(e);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Error closing file.");
            throw new SAXException(e);
        }
    }

    public boolean isReady() {
        return ready;
    }
}