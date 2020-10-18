import java.util.ArrayList;

public class Game {
    private String gameName;
    private GMap gameMap;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentStage;
    public static final int  RECRUIT=0;
    public static final int  ATTACK=1;
    public static final int  DEFEND=2;
    public static final int  ENDGAME=-1;

    /**
     *
     */
    public void init()
    {

    }

    /**
     *
     * @param Armynum
     * @param territory
     */
    public void draft(int Armynum,Territory territory)
    {

    }

    /**
     *
     * @param territory
     */
    public void attack(Territory territory)
    {

    }

    /**
     *
     * @param territory
     * @param Armynum
     */
    public void fortify(Territory territory,int Armynum)
    {

    }

    /**
     *
     */
    public void endGame()
    {

    }


}
