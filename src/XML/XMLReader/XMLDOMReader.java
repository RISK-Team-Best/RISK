package XML.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import Main.*;

public class XMLDOMReader {
    public XMLDOMReader()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File f = new File(getClass().getClassLoader().getResource("XML/test.xml").getFile());
            System.out.println(f.exists());
            Document doc = builder.parse(f);
            NodeList booklist = doc.getElementsByTagName("book");
            for (int i = 0;i<booklist.getLength();i++)
            {
                Node node = booklist.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;
                    String category = element.getAttribute("category");
                    NodeList info = element.getChildNodes();
                    System.out.println(element.getTextContent());
                    for(int j = 0;j<info.getLength();j++)
                    {
                        Element element1 = (Element)info.item(j);
                        //System.out.println(element1.getTagName()+ category + element.getTextContent());
                        System.out.println(element1.getTextContent());
                    }

                }
            }
        }catch (Exception e){}

    }

    public static void main(String[] args) {
        try{
        Board map = new Board();
        new XMLDOMReader();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
