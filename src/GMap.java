import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GMap {
    private String mapName;
    private HashMap<String,Continent> continents;
    private ArrayList<Continent> continentArrayList;
    private HashMap<String,Territory> territoryHashMap;
    private ArrayList<Territory> territoryArrayList;

    /**
     *
     */
    public GMap()
    {
        continents = new HashMap<>();
        territoryHashMap = new HashMap<>();
        territoryArrayList = new ArrayList<>();
        continentArrayList = new ArrayList<>();

    }


    public ArrayList<Territory> getTerritoryArrayList() {
        return territoryArrayList;
    }

    public HashMap<String, Territory> getTerritoryHashMap() {
        return territoryHashMap;
    }

    public HashMap<String, Continent> getContinents() {
        return continents;
    }


    public void addTerritory(Territory territory, Continent continent)
    {
        //territoryHashMap.put(territory.getName(),territory);
        continent.addTerritory(territory);
        territoryArrayList.add(territory);

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
        continentArrayList.add(continent);
    }

    /**
     *
     */
    public void createMap()
    {
        //create Continents
        Continent australia  = new Continent("Astralia");
        addContinent(australia);
        Continent asia  = new Continent("Asia");
        addContinent(asia);
        Continent europe  = new Continent("Europe");
        addContinent(europe);
        Continent africa  = new Continent("Africa");
        addContinent(africa);
        Continent northAmerica  = new Continent("NorthAmerica");
        addContinent(northAmerica);
        Continent southAmerica  = new Continent("southAmeria");
        addContinent(southAmerica);

        //create Australia
        Territory westernAustralia = new Territory("WesternAustralia");
        Territory easternAustralia = new Territory("EasternAustralia");
        Territory newGuinea = new Territory("NewGuinea");
        Territory indonesia = new Territory("Indonesia");

        addTerritory(westernAustralia,australia);
        addTerritory(easternAustralia,australia);
        addTerritory(newGuinea,australia);
        addTerritory(indonesia,australia);

        westernAustralia.setNeighbour(easternAustralia);
        westernAustralia.setNeighbour(indonesia);
        westernAustralia.setNeighbour(newGuinea);
        easternAustralia.setNeighbour(westernAustralia);
        easternAustralia.setNeighbour(newGuinea);
        newGuinea.setNeighbour(indonesia);
        newGuinea.setNeighbour(westernAustralia);
        newGuinea.setNeighbour(easternAustralia);
        indonesia.setNeighbour(easternAustralia);
        indonesia.setNeighbour(newGuinea);

        //create Asia
        Territory Kamchatka=new Territory("Kamchatka");
        addTerritory(Kamchatka,asia);

        Territory Japan=new Territory("Japan");
        addTerritory(Japan,asia);

        Territory Yakutsk=new Territory("Yakutsk");
        addTerritory(Yakutsk,asia);

        Territory Irkutsk=new Territory("Irkutsk");
        addTerritory(Irkutsk,asia);

        Territory Mongolia=new Territory("Mongolia");
        addTerritory(Mongolia,asia);

        Territory China=new Territory("China");
        addTerritory(China,asia);

        Territory Siam=new Territory("Siam");
        addTerritory(Siam,asia);

        Territory India=new Territory("India");
        addTerritory(India,asia);

        Territory Afghanistan=new Territory("Afghanistan");
        addTerritory(Afghanistan,asia);

        Territory Ural=new Territory("Ural");
        addTerritory(Ural,asia);

        Territory Siberia=new Territory("Siberia");
        addTerritory(Siberia,asia);

        Territory MiddleEast=new Territory("MiddleEast");
        addTerritory(MiddleEast,asia);

        Ural.setNeighbour(Siberia);
        Ural.setNeighbour(China);
        Ural.setNeighbour(Afghanistan);
        Siberia.setNeighbour(Ural);
        Siberia.setNeighbour(Yakutsk);
        Siberia.setNeighbour(Irkutsk);
        Siberia.setNeighbour(Mongolia);
        Siberia.setNeighbour(China);
        Siberia.setNeighbour(Afghanistan);
        Yakutsk.setNeighbour(Siberia);
        Yakutsk.setNeighbour(Kamchatka);
        Yakutsk.setNeighbour(Irkutsk);
        Kamchatka.setNeighbour(Yakutsk);
        Kamchatka.setNeighbour(Irkutsk);
        Kamchatka.setNeighbour(Mongolia);
        Kamchatka.setNeighbour(Japan);
        Irkutsk.setNeighbour(Siberia);
        Irkutsk.setNeighbour(Yakutsk);
        Irkutsk.setNeighbour(Kamchatka);
        Irkutsk.setNeighbour(Mongolia);
        Mongolia.setNeighbour(Siberia);
        Mongolia.setNeighbour(Irkutsk);
        Mongolia.setNeighbour(Kamchatka);
        Mongolia.setNeighbour(Japan);
        Mongolia.setNeighbour(China);
        Japan.setNeighbour(Mongolia);
        Japan.setNeighbour(Kamchatka);
        Afghanistan.setNeighbour(Ural);
        Afghanistan.setNeighbour(MiddleEast);
        Afghanistan.setNeighbour(China);
        Afghanistan.setNeighbour(India);
        China.setNeighbour(Afghanistan);
        China.setNeighbour(Ural);
        China.setNeighbour(Siberia);
        China.setNeighbour(Mongolia);
        China.setNeighbour(Siam);
        China.setNeighbour(India);
        MiddleEast.setNeighbour(Afghanistan);
        MiddleEast.setNeighbour(India);
        India.setNeighbour(MiddleEast);
        India.setNeighbour(Afghanistan);
        India.setNeighbour(China);
        India.setNeighbour(Siam);
        Siam.setNeighbour(India);
        Siam.setNeighbour(China);

        //create Europe
        Territory Ukraine=new Territory("Ukraine");
        addTerritory(Ukraine,europe);

        Territory Scandinavia=new Territory("Scandinavia");
        addTerritory(Scandinavia,europe);

        Territory NorthernEurope=new Territory("NorthernEurope");
        addTerritory(NorthernEurope,europe);

        Territory SouthernEurope=new Territory("SouthernEurope");
        addTerritory(SouthernEurope,europe);

        Territory WesternEurope=new Territory("WesternEurope");
        addTerritory(WesternEurope,europe);

        Territory GreatBritain=new Territory("GreatBritain");
        addTerritory(GreatBritain,europe);

        Territory Iceland=new Territory("Iceland");
        addTerritory(Iceland,europe);

        Iceland.setNeighbour(GreatBritain);
        Iceland.setNeighbour(Scandinavia);
        GreatBritain.setNeighbour(Iceland);
        GreatBritain.setNeighbour(Scandinavia);
        GreatBritain.setNeighbour(NorthernEurope);
        GreatBritain.setNeighbour(WesternEurope);
        Scandinavia.setNeighbour(Iceland);
        Scandinavia.setNeighbour(GreatBritain);
        Scandinavia.setNeighbour(NorthernEurope);
        Scandinavia.setNeighbour(Ukraine);
        NorthernEurope.setNeighbour(Scandinavia);
        NorthernEurope.setNeighbour(Ukraine);
        NorthernEurope.setNeighbour(SouthernEurope);
        NorthernEurope.setNeighbour(WesternEurope);
        NorthernEurope.setNeighbour(GreatBritain);
        Ukraine.setNeighbour(Scandinavia);
        Ukraine.setNeighbour(NorthernEurope);
        Ukraine.setNeighbour(SouthernEurope);
        WesternEurope.setNeighbour(GreatBritain);
        WesternEurope.setNeighbour(NorthernEurope);
        WesternEurope.setNeighbour(SouthernEurope);
        SouthernEurope.setNeighbour(WesternEurope);
        SouthernEurope.setNeighbour(NorthernEurope);
        SouthernEurope.setNeighbour(Ukraine);

        //create Africa
        Territory Madagascar= new Territory("Madagascar");
        addTerritory(Madagascar,africa);

        Territory SouthAfrica = new Territory("SouthAfrica");
        addTerritory(SouthAfrica,africa);

        Territory Congo = new Territory("Congo");
        addTerritory(Congo,africa);

        Territory EastAfrica = new Territory("EastAfrica");
        addTerritory(EastAfrica,africa);

        Territory NorthAfrica = new Territory("NorthAfrica");
        addTerritory(NorthAfrica,africa);

        Territory Egypt = new Territory("Egypt");
        addTerritory(Egypt,africa);

        NorthAfrica.setNeighbour(Egypt);
        NorthAfrica.setNeighbour(EastAfrica);
        NorthAfrica.setNeighbour(Congo);
        Egypt.setNeighbour(NorthAfrica);
        Egypt.setNeighbour(EastAfrica);
        EastAfrica.setNeighbour(NorthAfrica);
        EastAfrica.setNeighbour(Egypt);
        EastAfrica.setNeighbour(Congo);
        EastAfrica.setNeighbour(SouthAfrica);
        EastAfrica.setNeighbour(Madagascar);
        Congo.setNeighbour(NorthAfrica);
        Congo.setNeighbour(EastAfrica);
        Congo.setNeighbour(SouthAfrica);
        SouthAfrica.setNeighbour(Congo);
        SouthAfrica.setNeighbour(EastAfrica);
        SouthAfrica.setNeighbour(Madagascar);
        Madagascar.setNeighbour(EastAfrica);
        Madagascar.setNeighbour(SouthAfrica);

        //create South America
        Territory Argentina= new Territory("Argentina");
        Territory Brazil = new Territory("Brazil");
        Territory Peru = new Territory("Peru");
        Territory Venezuela = new Territory("Venezuela");

        addTerritory(Argentina,southAmerica);
        addTerritory(Brazil,southAmerica);
        addTerritory(Peru,southAmerica);
        addTerritory(Venezuela,southAmerica);

        Argentina.setNeighbour(Peru);
        Argentina.setNeighbour(Brazil);
        Peru.setNeighbour(Brazil);
        Peru.setNeighbour(Venezuela);
        Peru.setNeighbour(Argentina);
        Brazil.setNeighbour(Peru);
        Brazil.setNeighbour(Venezuela);
        Brazil.setNeighbour(Argentina);
        Venezuela.setNeighbour(Peru);
        Venezuela.setNeighbour(Brazil);

        //create North America
        Territory Alaska= new Territory("Alaska");
        addTerritory(Alaska,northAmerica);

        Territory NorthwestTerritory = new Territory("NorthwestTerritory");
        addTerritory(NorthwestTerritory,northAmerica);

        Territory Alberta = new Territory("Alberta");
        addTerritory(Alberta,northAmerica);

        Territory Ontario = new Territory("Ontario");
        addTerritory(Ontario,northAmerica);

        Territory Quebec = new Territory("Quebec");
        addTerritory(Quebec,northAmerica);

        Territory WesternUnitedStates = new Territory("WesternUnitedStates");
        addTerritory(WesternUnitedStates,northAmerica);

        Territory EasternUnitedStates = new Territory("EasternUnitedStates");
        addTerritory(EasternUnitedStates,northAmerica);

        Territory CentralAmerica = new Territory("CentralAmerica");
        addTerritory(CentralAmerica,northAmerica);

        Territory Greenland = new Territory("Greenland");
        addTerritory(Greenland,northAmerica);

        Alaska.setNeighbour(NorthwestTerritory);
        Alaska.setNeighbour(Alberta);
        NorthwestTerritory.setNeighbour(Alaska);
        NorthwestTerritory.setNeighbour(Alberta);
        NorthwestTerritory.setNeighbour(Ontario);
        NorthwestTerritory.setNeighbour(Greenland);
        Alberta.setNeighbour(Alaska);
        Alberta.setNeighbour(NorthwestTerritory);
        Alberta.setNeighbour(Ontario);
        Alberta.setNeighbour(WesternUnitedStates);
        Ontario.setNeighbour(NorthwestTerritory);
        Ontario.setNeighbour(Greenland);
        Ontario.setNeighbour(Quebec);
        Ontario.setNeighbour(EasternUnitedStates);
        Ontario.setNeighbour(WesternUnitedStates);
        Ontario.setNeighbour(Alberta);
        Quebec.setNeighbour(Ontario);
        Quebec.setNeighbour(Greenland);
        Quebec.setNeighbour(EasternUnitedStates);
        Greenland.setNeighbour(NorthwestTerritory);
        Greenland.setNeighbour(Ontario);
        Greenland.setNeighbour(Quebec);
        WesternUnitedStates.setNeighbour(Alberta);
        WesternUnitedStates.setNeighbour(Ontario);
        WesternUnitedStates.setNeighbour(EasternUnitedStates);
        WesternUnitedStates.setNeighbour(CentralAmerica);
        EasternUnitedStates.setNeighbour(Ontario);
        EasternUnitedStates.setNeighbour(Quebec);
        EasternUnitedStates.setNeighbour(WesternUnitedStates);
        EasternUnitedStates.setNeighbour(CentralAmerica);
        CentralAmerica.setNeighbour(WesternUnitedStates);
        CentralAmerica.setNeighbour(EasternUnitedStates);



        linkContinents(Brazil,NorthAfrica);
        linkContinents(Venezuela,CentralAmerica);

        linkContinents(Egypt,SouthernEurope);
        linkContinents(Egypt,MiddleEast);
        linkContinents(EastAfrica,MiddleEast);
        linkContinents(NorthAfrica,WesternEurope);


        linkContinents(Siam,indonesia);
        linkContinents(Kamchatka,Alaska);
        linkContinents(Ural,Ukraine);
        linkContinents(Afghanistan,Ukraine);

        linkContinents(Iceland,Greenland);

    }
    public Territory getTerritory(String countryName){return territoryHashMap.get(countryName);}

    public void linkContinents(Territory territoryA,Territory territoryB)
    {
        territoryA.setNeighbour(territoryB);
        territoryB.setNeighbour(territoryA);
    }


    public ArrayList<Continent> getContinentArrayList() {
        return continentArrayList;
    }
}
