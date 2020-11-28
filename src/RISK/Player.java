package RISK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The class Player store the information of the player's territory and calculate the troops each turn.
 *
 * @Author: Tiantian Lin, 101095243
 */
public class Player implements Serializable {
    private String name;
    private int troops;
    private HashMap<String,Territory> territories;
    private HashMap<String,Continent> continents;
    private int id;
    private boolean AI;
    private final int leastBonusTroops = 3;
    private final int bonusUnits = 3;

    /**
     * Instantiates a new Player.
     *
     * @param name the player's name
     * @param AI   the ai
     */
    public Player(String name,boolean AI){
        this.name = name;
        territories = new HashMap<>();
        continents = new HashMap<>();
        this.AI = AI;
    }

    /**
     * This method is setting troops.
     *
     * @param troops the troops
     */
    public void setTroops(int troops){
        this.troops = troops;
    }

    /**
     * This method is getting name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is getting troops.
     *
     * @return the troops
     */
    public int getTroops() {
        return troops;
    }

    /**
     * This method is getting all territories of this player.
     *
     * @return the territories of this player in an arraylist
     */
    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(territories.values());
    }

    /**
     * Get territories string array list.
     *
     * @return the array list
     */
    public ArrayList<String> getTerritoriesString(){
        ArrayList<String> territoriesString = new ArrayList<>(territories.keySet());
        return territoriesString;
    }

    /**
     * This method is getting all continents of this player.
     *
     * @return the continents of this player in array list
     */
    public ArrayList<Continent> getContinents() {
        return new ArrayList<>(continents.values());
    }

    /**
     * This method is adding territory which belongs to this player.
     *
     * @param territory the territory
     */
    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    /**
     * This method is adding continent which belongs to this player.
     *
     * @param continent the continent
     */
    public void addContinent(Continent continent){
        continents.put(continent.getName(),continent);
    }

    /**
     * This method is removing territory.
     *
     * @param territory the territory
     */
    public void removeTerritory(Territory territory){
        territories.remove(territory.getName());
    }

    /**
     * This method is removing continent.
     *
     * @param continent the continent
     */
    public void removeContinent(Continent continent){
        continents.remove(continent.getName());
    }

    /**
     * This method is increasing the troops.
     *
     * @param increase the increase of troops
     */
    public void increaseTroop(int increase){
        troops += increase;
    }

    /**
     * This method is decreasing the troops.
     *
     * @param decrease the decrease of troops
     */
    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    /**
     * This methods is checking this player has continent(s) or not.
     *
     * @return the boolean, true if have continent.
     */
    public boolean haveContinent(){
        return !continents.isEmpty();
    }

    /**
     * This method is checking territory by string boolean. Check player has this country or not.
     *
     * @param countryName the country name
     * @return whether player has the territory by name, true if contains
     */
    public boolean checkTerritoryByString(String countryName){
        return territories.containsKey(countryName);
    }

    /**
     * This method is getting territory by string territory.
     *
     * @param str the str
     * @return the territory found by its name;
     */
    public Territory getTerritoryByString(String str){
        return territories.get(str);
    }

    /**
     * This method is printing player info include name, troops on hand, number of territories and continents.
     */
    public void printPlayerInfo(){
        System.out.println("The player "+this.name+" has "+getTroops()+" troops and has "+territories.size()+" territories: ");
        for (Territory territory:getTerritories()){
            System.out.println(territory.shortDescription());
        }
        System.out.println();
        if(!continents.isEmpty()){
            System.out.println("And the continents: ");
            for(Continent continent:getContinents()){
                System.out.println(continent.getName()+", ");
            }
        }
    }

    /**
     * This method is gaining troops from territories.
     * At least gain three troops, the number of gained troops is number of territories / 3. Always round down
     */
    public void gainTroopsFromTerritory(){
        int bonus = this.getTerritories().size()/bonusUnits;
        if(bonus < leastBonusTroops){bonus = leastBonusTroops;}
        System.out.println("Player " + this.getName()+" has "+this.getTerritories().size()+" territories, add "+bonus+" troops.");
        this.increaseTroop(bonus);
        addContinentBonus();
    }

    /**
     * This methos is adding continent bonus. Each continent will have different bonus troops, depending on different maps and rules.
     */
    public void addContinentBonus(){
        if(!getContinents().isEmpty()){
            System.out.println("Player " + this.getName()+" has continents: ");
            for(Continent continent:this.getContinents()){
                this.increaseTroop(continent.getBonusTroops());
                System.out.println(continent.getName()+", add "+continent.getBonusTroops()+" troops.");
            }
        }
    }


    /**
     * The method is a setter for player id
     *
     * @param i set the id of the player
     */
    public void setID(int i){
        this.id = i;
    }

    /**
     * This method is a getter for player id
     *
     * @return the id of the player
     */
    public int getID(){
        return this.id;
    }


    /**
     * Determine this player is AI or not
     *
     * @return the boolean
     */
    public boolean isAI(){
        return this.AI;
     }


}
