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

    public String getName() {
        return name;
    }
}
