import java.util.ArrayList;
import java.util.HashMap;

public class Territory {
    private String name;
    private int troops;
    private Player holder;
    private HashMap<String,Territory> neighbour;
    private ArrayList<Territory> neighbourList;
    public boolean noTroopInteruptFlag; //user must clear this flag after use

    public Territory(String name)
    {
        this.name = name;
        troops = 0;
        neighbour = new HashMap<>();
        neighbourList = new ArrayList<>();
        noTroopInteruptFlag = false;
    }

    /**
     *
     * @param num
     */
    public void increaseTroops(int num)
    {
        troops += num;

    }

    /**
     *
     * @param num
     */
    public void decreaseTroops(int num)throws RuntimeException
    {
        troops -= num;
        if (troops<0){throw new RuntimeException("troops cannot be negative");}
        else if (troops==0){noTroopInteruptFlag = true;}

    }

    /**
     *
     * @param player
     */
    public void setHolder(Player player)
    {
        this.holder = player;
    }

    /**
     *
     * @param direction
     * @param t
     */
    public void setNeighbour(String direction,Territory t)
    {
        neighbour.put(direction,t);
        neighbourList.add(t);
    }

    /**
     *
     * @return
     */
    public Player getHolder() {
        return holder;
    }


}
