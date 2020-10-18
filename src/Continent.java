import java.util.ArrayList;

public class Continent {
    private String name;
    private int bonusTroops;
    private Player holder;
    private ArrayList<Territory> territories;
    private static final String E = "east";
    private static final String W = "west";
    private static final String N = "north";
    private static final String S = "SOUTH";
    private static final String NE = "northeast";
    private static final String NW = "northwest";
    private static final String SE = "southeast";
    private static final String SW = "southwest";

    /**
     *
     * @param name
     * @param bonus
     */
    public Continent(String name,int bonus)
    {
        this.name = name;
        bonusTroops = bonus;
        territories = new ArrayList<>();

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
        territories.add(territory);

    }
    public void createContinents()
    {
        createAustralia();



    }

    public void createAustralia()
    {
        Territory westernAustralia = new Territory("westernAustralia");
        Territory easternAustralia = new Territory("easternAustralia");
        Territory newGuinea = new Territory("newGuinea");
        Territory indonesia = new Territory("indonesia");

        addTerritory(westernAustralia);
        addTerritory(easternAustralia);
        addTerritory(newGuinea);
        addTerritory(indonesia);

        westernAustralia.setNeighbour(E,easternAustralia);
        westernAustralia.setNeighbour(N,indonesia);
        westernAustralia.setNeighbour(NE,newGuinea);
        easternAustralia.setNeighbour(W, westernAustralia);
        easternAustralia.setNeighbour(N, newGuinea);
        newGuinea.setNeighbour(W,indonesia);
        newGuinea.setNeighbour(SW,westernAustralia);
        newGuinea.setNeighbour(S,easternAustralia);
        indonesia.setNeighbour(S,easternAustralia);
        indonesia.setNeighbour(E,newGuinea);

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

        Argentina.setNeighbour(N,Peru);
        Argentina.setNeighbour(NE,Brazil);
        Peru.setNeighbour(E,Brazil);
        Peru.setNeighbour(N,Venezuela);
        Peru.setNeighbour(S,Argentina);
        Brazil.setNeighbour(W,Peru);
        Brazil.setNeighbour(NW,Venezuela);
        Brazil.setNeighbour(SW,Argentina);
        Venezuela.setNeighbour(SW,Peru);
        Venezuela.setNeighbour(SE,Brazil);


    }
    public void createNorthAmerica()
    {
        Territory Alaska= new Territory("Alaska");
        Territory NorthwestTerritory = new Territory("NorthwestTerritory");
        Territory Alberta = new Territory("Alberta");
        Territory Ontario = new Territory("Ontario");
        Territory Quebec = new Territory("Quebec");
        Territory WesternUnitedStates = new Territory("WesternUnitedStates");
        Territory EasternUnitedStates = new Territory("EasternUnitedStates");
        Territory CentralAmerica = new Territory("CentralAmerica");
        Territory Greenland = new Territory("Greenland");

        addTerritory(Alaska);
        addTerritory(NorthwestTerritory);
        addTerritory(Alberta);
        addTerritory(Ontario);
        addTerritory(Quebec);
        addTerritory(WesternUnitedStates);
        addTerritory(EasternUnitedStates);
        addTerritory(CentralAmerica);
        addTerritory(Greenland);

    }
}
