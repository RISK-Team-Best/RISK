import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private String gameName;
    private GMap gameMap;
    private LinkedList<Player> players;
    private Player currentPlayer;
    //private int currentStage;
    private State currentState;
    private Parser parser;

    //public static final int  RECRUIT=0;
   // public static final int  ATTACK=1;
    //public static final int  DEFEND=2;
    //public static final int  ENDGAME=-1;
    public enum State {
        DRAFT,ATTACK,FORTIFY,SKIP,PASS,QUIT;
    }

    /**
     *
     */
    public  Game()
    {   parser = new Parser();//读取用户的输入
        currentPlayer = new Player();//第一个玩家
        players = new LinkedList<>();//玩家列表设置成循环列表
        currentState = State.DRAFT;//初始状态为draft



    }
    public void initial(){

    }
    public void play(){
        System.out.println("Welcome to RISK");

        boolean finished = false;
        while (!finished){
            Command command = parser.getCommand();
            finished = processCommand(parser.getCommand());
        }
        System.out.println("Thank you for playing, Bye");//quit the game
    }
    private boolean processCommand(Command command){

        boolean wantToQuit = false;
        CommandWord commandWord = command.getCommandWord();

        switch(commandWord){
            case SKIP:

                if(currentState == State.ATTACK)
                    currentState = State.FORTIFY;
                if(currentState == State.FORTIFY)
                    currentState = State.PASS;
                if(currentState == State.DRAFT)
                    System.out.println("Please finish current state");
                break;

            case DRAFT:

                if(currentState == State.DRAFT)//只有当前状态和玩家输入的command一致才能进入draft
                                               //初始化默认状态为draft，所以当玩家输入command draft
                                               //的时候 才能进行draft（）
                    draft(command);
                else
                    System.out.println("Please finish current state");

                break;


            case ATTACK:

                if(currentState == State.ATTACK)
                attack(command);
                else
                    System.out.println("Please finish current state");
                break;

            case FORTIFY:

                if(currentState == State.FORTIFY)
                fortify(command);
                else
                    System.out.println("Please finish current state");
                break;

            case PASS:

                if(currentState == State.PASS)
                {
                    currentPlayer = players.getNext();//当前玩家变成下一个玩家
                    currentState = State.DRAFT;
                }
                else
                    System.out.println("Please finish current state");


            case QUIT:
                wantToQuit = true;
                break;
        }
            return wantToQuit;//return value to play to quit the game
    }

    /**
     *
     * @param Armynum
     * @param territory
     */
    public void draft(Command command)//需要更新Player.getTroop()方法

    {
        String countryName = command.getSecondWord();
        int armyNum = Integer.parseInt(command.getThirdWord());

        System.out.println(currentState + "You have" + player.getTroops()+
                " troops to add to any of your territory.");


        if (armyNum <= currentPlayer.getTroops())
        {
            currentPlayer.getTerritory(TerritoryName).increaseTroops(armyNum);
            currentPlayer.decreasetroops(armyNum);//decrease the number of troop
                                                  // that available to draft

        }

        else
            System.out.println("You don't have enough troops");

        if(currentPlayer.getTroops() == 0){//保证所有available troop在draft步骤中使用完才能进入下一个阶段
            currentState = State.ATTACK;
        }
        else
        {   currentState = State.DRAFT;
            System.out.println("You have "+ currentPlayer.getTroops()+"could draft.");}


    }

    /**
     *
     * @param territory
     */
    public void attack(Command command)

    {  String attackCountryName = command.getSecondWord();
       String defenceCountryName = command.getThirdWord();
      //2 territory.holder change
        //territory.troopnum change
    }

    /**
     *
     * @param territory
     * @param Armynum
     */
    public void fortify(Command command)
    {    String departCountryName = command.getSecondWord();
         String destinationCountryName = command.getThirdWord();
         int moveTroopNum = Integer.parseInt(command.getFourthWord());

    }


    public Player getNextPlayer(){//get next player

    }


}
