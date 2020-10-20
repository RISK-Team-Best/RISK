import java.util.ArrayList;

public class Dice {
    private ArrayList<Integer> dices;
    private int dicesAmount;

    /**
     *
     * @param dicesAmount
     */
    public Dice(int dicesAmount)
    {   dices = new ArrayList<Integer>();
        this.dicesAmount = dicesAmount;


    }

    /**
     *
     */
    public void diceRolling()
    {
       int number = (int)(1+Math.random()*(6-1+1));



    }

    /**
     *
     */
    public void sortDices()
    {

    }

    /**
     *
     * @return
     */
    public int getDicesAmount() {
        return dicesAmount;
    }

    public int getDiceByIndex()
    {
        return -1;
    }
}
