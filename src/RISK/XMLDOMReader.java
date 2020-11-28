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
            File f = new File(path);
            doc = builder.parse(f);


        }catch (Exception e){}

    }

    public void recoverGame(RiskModel riskModel) {
        NodeList players = doc.getElementsByTagName("Player");//get all Players
        for (int i = 0;i<players.getLength();i++)
        {
            Node node = players.item(i);//Player i
            Element player = (Element) node;
            String id = player.getAttribute("ID");
            NodeList name = player.getElementsByTagName("name");//other information
            String s = name.item(0).getTextContent();
            System.out.println(s);

        }
    }


}
