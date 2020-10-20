import java.util.ArrayList;
import java.util.HashMap;

public class Continent {
    private String name;
    private int bonusTroops;
    private Player holder;
    private HashMap<String,Territory> territories;

    /**
     *
     * @param name
     * @param bonus
     */
    public Continent(String name,int bonus)
    {
        this.name = name;
        bonusTroops = bonus;
        territories = new HashMap<>();

    }

    public Player getHolder() {
        return holder;
    }

    public int getBonusTroops() {
        return bonusTroops;
    }

    public HashMap<String, Territory> getTerritories() {
        return territories;
    }

    /**
     *
     * @param holder
     */
    public void setHolder(Player holder) {
        this.holder = holder;
    }

    /**
     *
     * @param territory
     */
    public void addTerritory(Territory territory)
    {
        territories.put(territory.getName(),territory);

    }

    public void createAustralia()
    {
        Territory westernAustralia = new Territory("WesternAustralia");
        Territory easternAustralia = new Territory("EasternAustralia");
        Territory newGuinea = new Territory("NewGuinea");
        Territory indonesia = new Territory("Indonesia");

        addTerritory(westernAustralia);
        addTerritory(easternAustralia);
        addTerritory(newGuinea);
        addTerritory(indonesia);

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

    }

    public void createAsia()
    {
        Territory Kamchatka=new Territory("Kamchatka");
        addTerritory(Kamchatka);


        Territory Japan=new Territory("Japan");
        addTerritory(Japan);

        Territory Yakutsk=new Territory("Yakutsk");
        addTerritory(Yakutsk);

        Territory Irkutsk=new Territory("Irkutsk");
        addTerritory(Irkutsk);

        Territory Mongolia=new Territory("Mongolia");
        addTerritory(Mongolia);

        Territory China=new Territory("China");
        addTerritory(China);

        Territory Siam=new Territory("Siam");
        addTerritory(Siam);

        Territory India=new Territory("India");
        addTerritory(India);

        Territory Afghanistan=new Territory("Afghanistan");
        addTerritory(Afghanistan);

        Territory Ural=new Territory("Ural");
        addTerritory(Ural);

        Territory Siberia=new Territory("Siberia");
        addTerritory(Siberia);

        Territory MiddleEast=new Territory("MiddleEast");
        addTerritory(MiddleEast);

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


    }

    public void createEurope()
    {
        Territory Ukraine=new Territory("Ukraine");
        addTerritory(Ukraine);

        Territory Scandinavia=new Territory("Scandinavia");
        addTerritory(Scandinavia);

        Territory NorthernEurope=new Territory("NorthernEurope");
        addTerritory(NorthernEurope);

        Territory SouthernEurope=new Territory("SouthernEurope");
        addTerritory(SouthernEurope);

        Territory WesternEurope=new Territory("WesternEurope");
        addTerritory(WesternEurope);

        Territory GreatBritain=new Territory("GreatBritain");
        addTerritory(GreatBritain);

        Territory Iceland=new Territory("Iceland");
        addTerritory(Iceland);

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



    }

    public void createAfrica()
    {
        Territory Madagascar= new Territory("Madagascar");
        addTerritory(Madagascar);

        Territory SouthAfrica = new Territory("SouthAfrica");
        addTerritory(SouthAfrica);

        Territory Congo = new Territory("Congo");
        addTerritory(Congo);

        Territory EastAfrica = new Territory("EastAfrica");
        addTerritory(EastAfrica);

        Territory NorthAfrica = new Territory("NorthAfrica");
        addTerritory(NorthAfrica);

        Territory Egypt = new Territory("Egypt");
        addTerritory(Egypt);

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

    }

    public void createSouthAmerica()
    {
        Territory Argentina= new Territory("Argentina");
        Territory Brazil = new Territory("Brazil");
        Territory Peru = new Territory("Peru");
        Territory Venezuela = new Territory("Venezuela");

        addTerritory(Argentina);
        addTerritory(Brazil);
        addTerritory(Peru);
        addTerritory(Venezuela);

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


    }
    public void createNorthAmerica()
    {
        Territory Alaska= new Territory("Alaska");
        addTerritory(Alaska);

        Territory NorthwestTerritory = new Territory("NorthwestTerritory");
        addTerritory(NorthwestTerritory);

        Territory Alberta = new Territory("Alberta");
        addTerritory(Alberta);

        Territory Ontario = new Territory("Ontario");
        addTerritory(Ontario);

        Territory Quebec = new Territory("Quebec");
        addTerritory(Quebec);

        Territory WesternUnitedStates = new Territory("WesternUnitedStates");
        addTerritory(WesternUnitedStates);

        Territory EasternUnitedStates = new Territory("EasternUnitedStates");
        addTerritory(EasternUnitedStates);

        Territory CentralAmerica = new Territory("CentralAmerica");
        addTerritory(CentralAmerica);

        Territory Greenland = new Territory("Greenland");
        addTerritory(Greenland);

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


    }

    public String getName() {
        return name;
    }
}
