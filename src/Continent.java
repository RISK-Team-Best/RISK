import java.util.ArrayList;

public class Continent {
    private String name;
    private int bonusTroops;
    private Player holder;
    private ArrayList<Territory> territories;
    private static final String EAST = "east";
    private static final String WEST = "west";
    private static final String NORTH = "north";
    private static final String SOUTH = "SOUTH";
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
        westernAustralia.setNeighbour(EAST,easternAustralia);
        westernAustralia.setNeighbour(NORTH,indonesia);
        westernAustralia.setNeighbour(NE,newGuinea);
        easternAustralia.setNeighbour(WEST, westernAustralia);
        easternAustralia.setNeighbour(NORTH, newGuinea);
        newGuinea.setNeighbour(WEST,indonesia);
        newGuinea.setNeighbour(SW,westernAustralia);
        newGuinea.setNeighbour(SOUTH,easternAustralia);
        indonesia.setNeighbour(SOUTH,easternAustralia);
        indonesia.setNeighbour(EAST,newGuinea);

    }

    public void createAsia()
    {

    }

    public void createEurope()
    {

    }

    public void createAfrica()
    {

    }

    public void createSouthAmerica()
    {

    }
    public void createNorthAmerica()
    {

    }

    public String getName() {
        return name;
    }
}
