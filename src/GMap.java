import java.util.ArrayList;
import java.util.HashMap;

public class GMap {
    private String mapName;
    private HashMap<String,Continent> continents;

    /**
     *
     */
    public GMap()
    {
        continents = new HashMap<>();
        createMap();

    }

    /**
     *
     * @param mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     *
     */

    public void addContinent(Continent continent)
    {
        continents.put(continent.getName(),continent);
    }

    /**
     *
     */
    public void createMap()
    {
        Continent australia  = new Continent("Astralia",2);
        australia.createAustralia();
        addContinent(australia);
        Continent asia  = new Continent("Asia",7);
        asia.createAsia();
        addContinent(asia);
        Continent europe  = new Continent("Europe",5);
        europe.createEurope();
        addContinent(europe);
        Continent africa  = new Continent("Africa",3);
        africa.createAfrica();
        addContinent(africa);
        Continent northAmerica  = new Continent("NorthAmerica",5);
        northAmerica.createNorthAmerica();
        addContinent(northAmerica);
        Continent southAmerica  = new Continent("southAmeria",2);
        southAmerica.createSouthAmerica();
        addContinent(southAmerica);

        linkContinents(southAmerica,"Brazil",africa,"NorthAfrica");
        linkContinents(southAmerica,"Venezuela",northAmerica,"CentralAmerica");

        linkContinents(africa,"Egypt",europe,"SouthernEurope");
        linkContinents(africa,"Egypt",asia,"MiddleEast");
        linkContinents(africa,"EastAfrica",asia,"MiddleEast");
        linkContinents(africa,"NorthAfrica",europe,"WesternEurope");


        linkContinents(asia,"Siam",australia,"Indonesia");
        linkContinents(asia,"Kamchatka",northAmerica,"Alaska");
        linkContinents(asia,"Ural",europe,"Ukraine");
        linkContinents(asia,"Afghanistan",europe,"Ukraine");

        linkContinents(europe,"Iceland",northAmerica,"Greenland");

    }

    public void linkContinents(Continent continentA, String territoryA,Continent continentB,String territoryB)
    {
        Territory temp1 = continentA.getTerritories().get(territoryA);
        Territory temp2 = continentB.getTerritories().get(territoryB);
        temp1.setNeighbour(temp2);
        temp2.setNeighbour(temp1);
    }


}
