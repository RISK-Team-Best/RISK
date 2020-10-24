import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
        for(int i = 0; i < dicesAmount; i++){
            dices.add(new Random().nextInt(6)+1);
        }
    }

    /**
     *
     */
    public void sortDices()
    {
        Collections.sort(dices);
        Collections.reverse(dices);
    }

    /**
     *
     * @return
     */
    public int getDicesAmount() {
        return dicesAmount;
    }

    public int getDiceByIndex(int index)
    {
        sortDices();
        return dices.get(index);
    }
}
