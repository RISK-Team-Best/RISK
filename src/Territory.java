import java.util.ArrayList;
import java.util.HashMap;

public class Territory {
    private String name;
    private int troops;
    private Player holder;
    private HashMap<String,Territory> neighbour;
    private ArrayList<Territory> neighbourList;

    /**
     *
     * @param num
     */
    public void increaseTroops(int num)
    {

    }

    /**
     *
     * @param num
     */
    public void decreaseTroops(int num)
    {

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
     * @param neighbour
     */
    public void setNeighbour(String direction,Territory neighbour)
    {

    }

    /**
     *
     * @return
     */
    public Player getHolder() {
        return holder;
    }


}
