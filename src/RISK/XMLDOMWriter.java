package RISK;


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

public class XMLDOMWriter implements SavingStrategy{
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
    /**
     * Create empty board
     *
     * @return the all board in document
     */
    public Document createEmptyBoard()
    {
        Document board = documentBuilder.newDocument();
        return board;
    }

    /**
     * Generate the map with document
     *
     */
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
    /**
     * Generate the map
     *
     * @param board in Board
     *
     */
    public void generateMap(Board board) {
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

    /**
     * Save the game
     *
     * @param model in risk model
     * @param path in String
     *
     */
    public void saveGame(RiskModel model,String path) {
        Document game = documentBuilder.newDocument();
        Element root = game.createElement("RISK");
        game.appendChild(root);
        Element currentStage = game.createElement("currentStage");
        currentStage.setTextContent(model.getCurrentStage().getName());
        root.appendChild(currentStage);
        for(Player p : model.getPlayers())
        {
            Element player = game.createElement("Player");
            player.setAttribute("name",p.getName());
            player.setAttribute("ID",String.valueOf(p.getID()));
            player.setAttribute("troops",String.valueOf(p.getTroops()));
            player.setAttribute("isAI",String.valueOf(p instanceof AIPlayer));
            for (Continent c : p.getContinents())
            {
                Element ownContinent = game.createElement("ownContinent");
                ownContinent.setAttribute("continentName",c.getName());
                ownContinent.setAttribute("Bonus",String.valueOf(c.getBonusTroops()));
                player.appendChild(ownContinent);
            }
            for (Territory t : p.getTerritories())
            {
                Element ownTerritory = game.createElement("ownTerritory");
                ownTerritory.setAttribute("territoryName",t.getName());
                ownTerritory.setAttribute("troops",String.valueOf(t.getTroops()));
                player.appendChild(ownTerritory);
            }

            root.appendChild(player);

        }
        Element currentPlayer = game.createElement("currentPlayer");
        currentPlayer.setTextContent(model.getCurrentPlayer().getName());
        root.appendChild(currentPlayer);
        DOMSource source = new DOMSource(game);
        Result result = new StreamResult(new File(path));
        try {
            transformer.transform(source,result);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    /**
     * main program
     *
     */
    public static void main(String[] args) {
        try {
            Board map = new Board("OriginRiskMap");
            XMLDOMWriter writer = new XMLDOMWriter();
            writer.generateMap(map);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
