package RISK;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.imageio.ImageIO;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

///**
// * The type Board which read the files and create map informations.
// *
// * @Author: Guanquan Dong, 101093918
// *
// */


public class Board extends DefaultHandler {
    private HashMap<String,Territory> countryHashMap;
    private HashMap<String,Continent> continentHashMap;
    private HashMap<String,ArrayList<Territory>> neighbors;
    private HashMap<String,ArrayList<Integer>> coordinates;

    private Continent tempContinent;
    private ArrayList<Territory> neighborTerritoryList;
    private ArrayList<Territory> tempContinentMemberList;
    private ArrayList<Integer> coordinateList;
    private String tempMainTerritoryInNeighbor;

    private BufferedImage image;

    private int x;
    private int y;

    private String continentName;
    private int continentBonus;

    private boolean territory = false;
    private boolean memberTerritory = false;
    private boolean neighborTerritory = false;

    public Board(String fileName) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(fileName + ".xml"), this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equalsIgnoreCase("boardInfo")) {
            try {
                image = ImageIO.read(getClass().getResource(attributes.getValue("imagePath")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(qName.equalsIgnoreCase("territoriesList")){
            countryHashMap = new HashMap<>();
            coordinates = new HashMap<>();
        }
        if(qName.equalsIgnoreCase("territory")){
            x = Integer.parseInt(attributes.getValue("x"));
            y = Integer.parseInt(attributes.getValue("y"));
            coordinateList = new ArrayList<>();
            coordinateList.add(x);
            coordinateList.add(y);
            territory = true;
        }
        if(qName.equalsIgnoreCase("continentList")) continentHashMap = new HashMap<>();
        if(qName.equalsIgnoreCase("Continent")){
            tempContinentMemberList = new ArrayList<>();
            continentName = attributes.getValue("name");
            continentBonus = Integer.parseInt(attributes.getValue("bonus"));
        }
        if(qName.equalsIgnoreCase("MemberTerritory")) memberTerritory = true;
        if(qName.equalsIgnoreCase("NeighborList")) {
            neighbors = new HashMap<>();
        }
        if(qName.equalsIgnoreCase("MainTerritory")){
            neighborTerritoryList = new ArrayList<>();
            tempMainTerritoryInNeighbor = attributes.getValue("name");
        }
        if(qName.equalsIgnoreCase("neighborTerritory")) neighborTerritory = true;

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("Continent")){
            tempContinent = new Continent(continentName,continentBonus,tempContinentMemberList);
            continentHashMap.put(tempContinent.getName(),tempContinent);
        }
        if(qName.equalsIgnoreCase("MainTerritory")){
            neighbors.put(tempMainTerritoryInNeighbor,neighborTerritoryList);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String info = new String(ch,start,length);
        if(territory){
            Territory tempTerritory = new Territory(info);
            countryHashMap.put(info,tempTerritory);
            coordinates.put(info,coordinateList);
            territory = false;
        }
        if(memberTerritory){
            tempContinentMemberList.add(new Territory(info));
            memberTerritory = false;
        }
        if(neighborTerritory){
            neighborTerritoryList.add(new Territory(info));
            neighborTerritory = false;
        }
    }

    /**
     * Gets all countries.
     *
     * @return the all countries in array list
     */
    public ArrayList<Territory> getAllCountries() {
        return new ArrayList<>(countryHashMap.values());
    }

    /**
     * Get all continents array list.
     *
     * @return array list of all continents
     */
    public ArrayList<Continent> getAllContinents(){
        return new ArrayList<>(continentHashMap.values());
    }

    /**
     * Get all neighbors in an array list of a territory through territory's name.
     *
     * @param countryName the territory's name
     * @return the array list of all neighbors of this territory.
     */
    public ArrayList<Territory> getAllNeighbors(String countryName){
        return neighbors.get(countryName);
    }

    /**
     * Get all neighbors in an array list of a territory through territory's name.
     *
     * @param name
     * @return the name of the continent .
     */
    public Continent getContinentByName(String name){
        return continentHashMap.get(name);
    }

    public HashMap<String, ArrayList<Integer>> getCoordinates() {
        return coordinates;
    }

    public BufferedImage getImage() {
        return image;
    }
}