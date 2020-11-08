package XML.XMLWriter;

import Copy.Board;
import Copy.Continent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLDOMWriter {
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private Transformer transformer;
    public XMLDOMWriter()
    {
        try {
            builder = factory.newDocumentBuilder();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void genarateMap (Board board)
    {
        Document map = builder.newDocument();
        Element root = map.createElement("board");
        map.appendChild(root);
        for(Continent continent : board.getAllContinents())
        {
            Element continent2 = map.createElement("continent");
            root.appendChild(continent2);
            Element name = map.createElement("name");
            continent2.appendChild(name);
            Text text = map.createTextNode(continent.getName());
            name.appendChild(text);

        }
        DOMSource source = new DOMSource(map);
        Result result = new StreamResult(new File("res//mapResult.xml"));
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(source,result);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        try {
            Board map = new Board();
            XMLDOMWriter writer = new XMLDOMWriter();
            writer.genarateMap(map);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
