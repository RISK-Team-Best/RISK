package XML.XMLWriter;

import Copy.Board;
import Copy.Continent;
import Copy.Territory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    private DocumentBuilder documentBuilder;
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private Transformer transformer;
    public XMLDOMWriter()
    {
        try {
            documentBuilder = factory.newDocumentBuilder();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public Document createEmptyBoard()
    {
        Document board = documentBuilder.newDocument();
        return board;
    }

    public void generateMapWithDoc(Document doc)
    {
        DOMSource source = new DOMSource(doc);
        Result result = new StreamResult(new File("res//mapResult.xml"));
        try {
            transformer.transform(source,result);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void genarateMap (Board board)
    {
        Document map = documentBuilder.newDocument();
        Element root = map.createElement("board");
        map.appendChild(root);
        for(Continent continent : board.getAllContinents())
        {
            Element continent2 = map.createElement("continent");
            continent2.setAttribute("name",continent.getName());
            root.appendChild(continent2);
            for (Territory t : continent.getMembers())
            {
                Element territory = map.createElement("territory");
                territory.setAttribute("name",t.getName());
                continent2.appendChild(territory);
            }

        }
        DOMSource source = new DOMSource(map);
        Result result = new StreamResult(new File("res//mapResult.xml"));
        try {
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
