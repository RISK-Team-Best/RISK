import java.util.ArrayList;
import java.util.HashMap;

public class Continent {
    private String name;
    private int bonusTroops;
    private Player holder;
    private HashMap<String,Territory> territories;
    private ArrayList<Territory> territoryArrayList;
    private ContinentPanel panel;


    public Continent(String name)
    {
        this.name = name;
        //bonusTroops = bonus;
        territories = new HashMap<>();
        territoryArrayList = new ArrayList<>();

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
        territoryArrayList.add(territory);

    }

    public String getName() {
        return name;
    }

    public ContinentPanel getPanel() {
        return panel;
    }

    public void setPanel(ContinentPanel panel) {
        this.panel = panel;
    }

    public ArrayList<Territory> getTerritoryArrayList() {
        return territoryArrayList;
    }
}
