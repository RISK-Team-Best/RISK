package XML.XMLReader;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLDOMReader {
    public XMLDOMReader()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("test.xml");
        }catch (Exception e){}

    }

    public static void main(String[] args) {
        new XMLDOMReader();
    }
}
