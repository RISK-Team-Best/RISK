package RISK;

import java.util.ArrayList;

/*
 * The class Continent store the territories which in the same continent
 *
 *
 * @Author: Guanqun Dong, 101093918
 */
public class Continent {
    private String name;
    private int bonusTroops;
    private ArrayList<Territory> members;

    /**
     * Instantiates a new Continent.
     *
     * @param name        the continent name
     * @param bonusTroops the bonus troops
     * @param members     the member territories of continents
     */
    public Continent(String name, int bonusTroops, ArrayList<Territory> members){
        this.name = name;
        this.bonusTroops = bonusTroops;
        this.members = members;
    }

    /**
     * Gets continent name.
     *
     * @return the name of continent
     */
    public String getName() {
        return name;
    }

    /**
     * Gets bonus troops.
     *
     * @return the bonus troops
     */
    public int getBonusTroops() {
        return bonusTroops;
    }

    /**
     * Member territory of continent in string array list.
     *
     * @return the memberString which is an Array List
     */
    public ArrayList<String> memberString(){
        ArrayList<String> memberString = new ArrayList<>();
        for(Territory territory:members){
            memberString.add(territory.getName());
        }
        return memberString;
    }

    /**
     * This method get the member of territory
     * @param territory
     * @return a null
     */
    public Territory getMemberTerritory(Territory territory){
        for(Territory territory1:members){
            if(territory.getName().equals(territory1.getName()))return territory1;
        }
        return null;
    }

    /**
     * This method is using an ArrayList to get Members
     * @return the territory list in this continent
     */
    public ArrayList<Territory> getMembers() {
        return members;
    }
}
