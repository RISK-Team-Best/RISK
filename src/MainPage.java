import java.util.ArrayList;
import java.util.LinkedList;

public class MainPage {
    private Game currentGame;
    private ArrayList<Player> players;
    private GMap mapselect;
    public MainPage()
    {
        players = new ArrayList<>(6);

    }



    public void createGame()
    {
        currentGame = new Game(players,mapselect);
        currentGame.initial();


    }

    /**
     *
     * @param name
     */
    public void createPlayer(String name)
    {
        Player p = new Player(name);
        players.add(p);
        p.setId(players.size()-1);

    }

    /**
     *
     * @param player
     */
    public void deletePlayer(Player player)
    {

    }

    /**
     *
     */
    public void chooseMap(GMap map)
    {
        mapselect =map;

    }

    public static void main(String[] args) {
        MainPage mainPage = new MainPage();
        mainPage.createPlayer("Player1");
        mainPage.createPlayer("Player2");
        mainPage.chooseMap(new GMap());
        mainPage.createGame();
    }


}
