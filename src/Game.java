import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

public class Game {
    private String gameName;
    private GMap gameMap;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerID=0;
    //初始状态为draft
    private int currentStage;
    //private State currentState = State.DRAFT;
    private Parser parser;
    private Scanner scanner;

    public static final int  RECRUIT=0;
    public static final int  ATTACK=1;
    public static final int  DEFEND=2;
    public static final int  ENDGAME=-1;
    public enum State {
        DRAFT,ATTACK,FORTIFY,SKIP,PASS,QUIT;
    }

    /**
     *
     */
    public  Game(ArrayList<Player> players,GMap map)
    {
        this.players = players;
        parser = new Parser();//读取用户的输入
        currentPlayer =players.get(0);
        gameMap = map;
        //currentPlayer = new Player();//第一个玩家
        //players = new LinkedList<>();//玩家列表设置成循环列表
        initial();


    }
    public void initial(){
        int initArmy;
        int playernumber = players.size();
        if (playernumber==2){initArmy=50;}
        else if (playernumber==3){initArmy=35;}
        else if (playernumber==4){initArmy=30;}
        else if (playernumber==5){initArmy=25;}
        else if (playernumber==6){initArmy=20;}
        else{initArmy=0;}
        ArrayList<Territory> territoriescopy1 = new ArrayList<>();
        for(Territory t : gameMap.getTerritoryArrayList()){territoriescopy1.add(t);}
        Random random = new Random();
        int round = Math.floorDiv(territoriescopy1.size(),playernumber);
        int randomresult;
        for (Player p : players)
        {
            for(int i =0;i<round;i++){
                randomresult = random.nextInt(territoriescopy1.size());
                Territory t = territoriescopy1.get(randomresult);
                t.setHolder(p);
                t.increaseTroops(1);
                p.getTerritoryArrayList().add(t);
                territoriescopy1.remove(t);
            }
            p.increasetroops(initArmy-round);
        }
        for (int count = 0;count<territoriescopy1.size();count++){
            Player rest = players.get(count);
            Territory temp = territoriescopy1.get(count);
            temp.setHolder(rest);
            temp.increaseTroops(1);
            rest.getTerritoryArrayList().add(temp);
            rest.decreasetroops(1);
        }
        for(Player p : players)
        {
            ArrayList<Territory> playerHoldTerritorys=p.getTerritoryArrayList();
            while(p.getTroops()>0)
            {
                Territory temp = playerHoldTerritorys.get(random.nextInt(playerHoldTerritorys.size()));
                temp.increaseTroops(1);
                p.decreasetroops(1);
            }
        }
        /*int test=0;
        int test2 = 0;
        int test3 =0;
        int test4 =0;
        int test5 =0;
        for (Territory t :gameMap.getTerritoryArrayList()){
            if(t.getHolder()==players.get(0)){
                test=test+t.getTroops();
                System.out.println(t.getHolder().toString()+test);
            }
            else if (t.getHolder()==players.get(1)){
                test2+=t.getTroops();
                System.out.println(t.getHolder().toString()+test2);
            }
            else if (t.getHolder()==players.get(2)){
                test3+=t.getTroops();
                System.out.println(t.getHolder().toString()+test3);
            }
            else if (t.getHolder()==players.get(3)) {
                test5 += t.getTroops();
                System.out.println(t.getHolder().toString() + test5);
            }
            else {System.out.println("Error");}
            test4++;
        }
        System.out.println(test4);*/
        play();

    }
    public void play(){
        System.out.println("Welcome to RISK");
        //Scanner sc = new Scanner(System.in);
        currentPlayer.increasetroops(currentPlayer.getTerritoryArrayList().size()/3);

        boolean finished = false;
        boolean stillHaveTroops;
        System.out.println(currentPlayer.printPlayerinfo());
        while (!finished){

            //String[] testString1 = sc.nextLine().split(" ");
            String[] instrction = parser.getInstrction();

            String firstCommand = instrction[0];
            if(currentStage==RECRUIT)
            {
                if (firstCommand.equals("draft"))
                {
                    stillHaveTroops=draft(currentPlayer,gameMap.getTerritory(instrction[1]),Integer.valueOf(instrction[2]));
                    if (stillHaveTroops){}
                    else {changeState();}
                }

            }
            if(currentStage==ATTACK)
            {
                if (firstCommand.equals("attack"))
                attack(instrction[1],instrction[2]);

            }
            if(currentStage==DEFEND)
            {
                changeState();
            }


           // Command command = parser.getCommand();
           // finished = processCommand(parser.getCommand());
        }
        System.out.println("Thank you for playing, Bye");//quit the game
    }
    /*private boolean processCommand(Command command){

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
                    currentPlayer = getNextPlayer();//当前玩家变成下一个玩家
                    currentState = State.DRAFT;
                }
                else
                    System.out.println("Please finish current state");


            case QUIT:
                wantToQuit = true;
                break;
            case UNKNOWN:
                break;
        }
            return wantToQuit;//return value to play to quit the game
    }*/


    public boolean draft(Player who,  Territory to, int howMany)//需要更新Player.getTroop()方法

    {
        if(to.getHolder()==who){
            if (who.getTroops()>=howMany){
                to.increaseTroops(howMany);
                who.decreasetroops(howMany);
                System.out.println("You have moved "+howMany+" to "+to.getName());
            }
            else {System.out.println("not enough troops");}
        }
        else {System.out.println("not your land");}

        if (who.getTroops()>0){return true;}
        else {return false;}
        //example draft Ottawa 5
        /*String countryName = command.getSecondWord();
        int armyNum = Integer.parseInt(command.getThirdWord());

        System.out.println(currentState + "You have" + currentPlayer.getTroops()+
                " troops to add to any of your territory.");


        if (armyNum <= currentPlayer.getTroops())
        {
            gameMap.getTerritory(countryName).increaseTroops(armyNum);
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
            System.out.println("You have "+ currentPlayer.getTroops()+"could draft.");}*/


    }


    public void attack(String attack,String defence)

    {  String attackCountryName = attack;
       String defenceCountryName = defence;


       Territory attackCountry = this.gameMap.getTerritory(attackCountryName);
       Territory defenceCountry = this.gameMap.getTerritory(defenceCountryName);

        int defenceTroops;
        int attackTroops = 1;

        if(defenceCountry.getTroops()>=2)
             defenceTroops=2;

        else defenceTroops=1;


        System.out.println("How many troops do you want to attack? (one/two/three/blitz/cancel)");
        Scanner sc = new Scanner(System.in);
        String type = sc.nextLine();
        switch (type){
            case "one": attackTroops = 1; break;
            case "two": attackTroops = 2; break;
            case "three": attackTroops =3; break;
            case "blitz": blitz(attackCountry,defenceCountry);
                return;
            case "cancel": return;
        }

        if(attackTroops > attackCountry.getTroops()){
            System.out.println("Sorry, you only have "+attackCountry.getTroops()+" troops in this country");
            attackTroops = attackCountry.getTroops();
        }
        Dice attackDice = new Dice(attackTroops);
        Dice defenceDice = new Dice(defenceTroops);
        compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        
        if(defenceCountry.getTroops()==0){
            OccupiedCountry(attackCountry,defenceCountry);
        }

    }

    public void blitz(Territory attackCountry, Territory defenceCountry) {

        while((attackCountry.getTroops()>1)&&(defenceCountry.getTroops()>0)) {

            int attack = attackCountry.getTroops();
            int defence = defenceCountry.getTroops();
            if(attack>3)
                attack=3;
            if(defence>2)
                defence=2;
            Dice attackDice = new Dice(attack);
            Dice defenceDice = new Dice(defence);

            compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        }
        if(defenceCountry.getTroops()==0){
            OccupiedCountry(attackCountry,defenceCountry);
        }
        else if(attackCountry.getTroops() == 1){
            System.out.println("Your attack failed\n");}
    }

    private void compareDices(Dice attackDice, Dice defenceDice, Territory attackCountry, Territory defenceCountry) {
        attackDice.diceRolling();
        defenceDice.diceRolling();
        int minDiceAmount = Math.min(attackDice.getDicesAmount(),defenceDice.getDicesAmount());
        for(int i=0;i<minDiceAmount;i++){
            if(attackDice.getDiceByIndex(i)>defenceDice.getDiceByIndex(i)){
                defenceCountry.decreaseTroops(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getDiceByIndex(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getDiceByIndex(i)+".");
                System.out.println(defenceCountry.getName()+" lose one troop by rolling.\n");
            }else{
                attackCountry.decreaseTroops(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getDiceByIndex(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getDiceByIndex(i)+".");
                System.out.println(attackCountry.getName()+" lose one troop by rolling.\n");
            }
        }
    }

    public void OccupiedCountry(Territory attackCountry, Territory defenceCountry) {
        int deployTroops;
        do {
            System.out.println(attackCountry.getHolder().getName() + "attacked successfully! Currently you have "+attackCountry.getTroops()+ " in your original country.");
            System.out.println(" How many troops deploy to " + defenceCountry.getName());
            deployTroops = scanner.nextInt();
            scanner.nextLine();
            if(deployTroops > attackCountry.getTroops()){
                System.out.println("You don't have so much troops, try it again.");
            }else if(deployTroops == attackCountry.getTroops()){
                System.out.println("You have to leave at least one troop in your original country.");
            }else{
                break;
            }
        }while(true);
        attackCountry.decreaseTroops(deployTroops);
        defenceCountry.getHolder().removeTerritory(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addTerritory(defenceCountry);
        defenceCountry.increaseTroops(deployTroops);
    }


    public void fortify(Command command)

    {
        HashMap<String,Territory> territoryHashMap = gameMap.getTerritoryHashMap();
        String departCountryName = command.getSecondWord();
         String destinationCountryName = command.getThirdWord();
         int moveTroopNum = Integer.parseInt(command.getFourthWord());


        if (currentPlayer.hasTerritory(gameMap,departCountryName) || currentPlayer.hasTerritory(gameMap,destinationCountryName) ) {
            Territory departCountry = territoryHashMap.get(departCountryName);
            Territory destinationCountry = territoryHashMap.get(destinationCountryName);

        } else {
            System.out.println("Please check your input of territory's name.");
        }

        /*while(true) {//need modify.

            addNeighborCountries(fortifyCountry, player);
            neighborCountries.remove(fortifyCountry);
            System.out.println("You have these territories connect to " + fortifyCountry.getName());
            for (Country country : neighborCountries) {
                System.out.println(country.shortDescription() + " ");
            }
            do {
                System.out.println("Which territory do you want to be fortified?");
                String fortifiedString = scanner.nextLine();
                if (fortifiedString.equals(fortifyCountryString)) break;
                else if (player.checkCountryByString(fortifiedString)) {
                    fortified = player.getCountryByString(fortifiedString);
                    break outerloop;
                } else System.out.println("Please check your input of territory's name.");
            } while (true);
        }

        do {
            System.out.println("How many troops do you want to fortify?");
            int troop = scanner.nextInt();
            scanner.nextLine();
            if (troop > fortifyCountry.getArmies())
                System.out.println("You don't have so much troops in this country..");
            else if (troop > (fortifyCountry.getArmies() - 1))
                System.out.println("Please leave at least one troop to defence");
            else {
                fortifyCountry.decreaseArmies(troop);
                fortified.increaseArmies(troop);
                break;
            }
        } while (true);*/


    }

    public void changeState()
    {
        if(currentStage==DEFEND)
        {
            currentPlayerID=(currentPlayerID+1)%players.size();
            currentStage = (currentStage+1)%3;
            currentPlayer = players.get(currentPlayerID);
            currentPlayer.increasetroops(currentPlayer.getTerritoryArrayList().size()/3);
            System.out.println(currentPlayer.printPlayerinfo());

        }
        else {currentStage = (currentStage+1)%3;}
        System.out.println("Now " + currentPlayer.getName() + "'s turn, current stage: "+ currentStage);
    }


    /*public Player getNextPlayer(){//get next player
        return players.get((currentPlayer.getId()+1)%players.size());

    }*/



}
