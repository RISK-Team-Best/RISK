package RISK;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class XMLDOMReader {

    private Document doc;

    public XMLDOMReader(String path)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File f = new File(getClass().getClassLoader().getResource(path+".xml").getFile());
            System.out.println(f.exists());//test
            doc = builder.parse(f);


        }catch (Exception e){}

    }

    public void recoverGame(RiskModel riskModel) {
        NodeList result = doc.getElementsByTagName("RISK");
        for (int i = 0;i<result.getLength();i++)
        {
            Node node = result.item(i);
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

    }
}
