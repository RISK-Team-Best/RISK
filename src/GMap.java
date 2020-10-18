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

    }


}
