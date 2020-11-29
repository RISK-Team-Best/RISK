package RISK;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class XMLDOMReader implements LoadingStrategy{

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

    public void recoverGame(RiskModel riskModel) {//break down to small methods
        readPlayer(riskModel);
        readTerritories(riskModel);
        readStage(riskModel);


    }

    private void readStage(RiskModel riskModel) {
        NodeList stage = doc.getElementsByTagName("Stage");
        String s = stage.item(0).getTextContent();
        riskModel.setCurrentStage(Stage.valueOf(s));
    }

    private void readTerritories(RiskModel riskModel) {
        NodeList territories = doc.getElementsByTagName("Territory");//get all Players
        for (int i = 0;i<territories.getLength();i++)
        {
            Node node = territories.item(i);//Player i
            Element territory = (Element) node;
            //String territoryId = territory.getAttribute("ID");
            NodeList name = territory.getElementsByTagName("name");//other information
            String n = name.item(0).getTextContent();
            NodeList holder = territory.getElementsByTagName("holder");//other information
            String h = holder.item(0).getTextContent();
            NodeList troops = territory.getElementsByTagName("troops");//other information
            String t = troops.item(0).getTextContent();

            riskModel.ImportTerritory(n,Integer.parseInt(t),h);

        }
    }

    private void readPlayer(RiskModel riskModel) {
        NodeList players = doc.getElementsByTagName("Player");//get all Players
        for (int i = 0;i<players.getLength();i++)
        {
            Node node = players.item(i);//Player i
            Element player = (Element) node;
            String id = player.getAttribute("ID");
            NodeList name = player.getElementsByTagName("name");//other information
            String n = name.item(0).getTextContent();
            NodeList ai = player.getElementsByTagName("AI");//other information
            String a = ai.item(0).getTextContent();
            NodeList troops = player.getElementsByTagName("troops");//other information
            String t = troops.item(0).getTextContent();
            NodeList ownTerritory = player.getElementsByTagName("ownTerritory");//other information
            String o = ownTerritory.item(0).getTextContent();

            riskModel.ImportPlayer(n,Boolean.valueOf(a),Integer.parseInt(t),Integer.parseInt(id),o);

        }
    }


}
