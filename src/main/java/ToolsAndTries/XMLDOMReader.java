package ToolsAndTries;

import RISK.Continent;
import RISK.RiskModel;
import RISK.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;


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

    /**
     * Recover the game
     *
     * @param riskModel in Risk Model
     */
    public void recoverGame(RiskModel riskModel) {//break down to small methods
        readStage(riskModel);
        readPlayer(riskModel);
        readTerritories(riskModel);
    }

    /**
     * Read the stage
     *
     * @param riskModel in Risk Model
     */
    private void readStage(RiskModel riskModel) {
        NodeList stage = doc.getElementsByTagName("currentStage");
        String s = stage.item(0).getTextContent();
        riskModel.setCurrentStage(Stage.valueOf(s));
    }

    /**
     * Read the territories
     *
     * @param riskModel in Risk Model
     */
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

    /**
     * Read the player
     *
     * @param riskModel in Risk Model
     */
    private void readPlayer(RiskModel riskModel) {
        NodeList players = doc.getElementsByTagName("Player");//get all Players
        for (int i = 0;i<players.getLength();i++)
        {
            Node PlayerNode = players.item(i);//Player i
            Element player = (Element) PlayerNode;
            String id = player.getAttribute("ID");
            String isAI = player.getAttribute("isAI");
            String troops = player.getAttribute("troops");
            ArrayList<Continent> ownContinents = new ArrayList<>();
            NodeList continents = doc.getElementsByTagName("ownContinent");
            for(int j = 0;j<continents.getLength();j++)
            {
                Node continentNode = continents.item(j);
                Element continent = (Element) continentNode;
                String continentName = continent.getAttribute("continentName");
                String bonus = continent.getAttribute("Bonus");

            }
        }
    }


}
