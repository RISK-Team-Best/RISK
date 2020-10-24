import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private int troops;
    private int occupied;
    private int id;
    private HashMap<String,Territory> territoryList;
    private ArrayList<Territory> territoryArrayList;

    /**
     *
     * @param name
     */
    public Player(String name)
    {
        territoryList = new HashMap<>();
        this.name = name;
        territoryArrayList = new ArrayList<>();
    }

    public HashMap<String, Territory> getTerritoryList() {
        return territoryList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param num
     */
    public void increasetroops(int num)
    {
        troops+=num;
    }

    /**
     *
     * @param num
     */
    public void decreasetroops(int num)
    {
        troops -= num;
    }

    /**
     *
     * @param num
     */
    public void increaseOccupied(int num)
    {
        occupied+=num;

    }

    /**
     *
     * @param num
     */
    public void decreaseOccupied(int num)
    {

    }
    /**
     *
     * @return how many troops in this turn's draft
     * getTroop（）返回当前可用troop的值
     * 初始状态是每一次玩家draft时可用的征兵数
     * 随着每一次玩家执行draft（）方法，getTroop（）返回的数量逐次减少
     * 直到返回值为0， 玩家该轮draft结束，进入attack
     */
    public int getTroops(){
        return troops;
    }
    /**
     *
     * @return Territory that the player own with the String name
     */
    /*public Territory getTerritory(String name){
        return Territory;
    }*/

    public String getName(){
        return this.name;
    }
    public void removeTerritory(Territory name){

    }
    public void addTerritory(Territory name){

    }
    public boolean hasTerritory(GMap map,String name){
        if (map.getTerritoryHashMap().get(name).getHolder()==this){return true;}
        else {return false;}

    }


    public ArrayList<Territory> getTerritoryArrayList() {
        return territoryArrayList;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
