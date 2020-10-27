import javafx.geometry.Orientation;

import javax.swing.*;
import java.util.*;

public class Game {
    private String gameName;//reserved for save and load game
    private GMap gameMap;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerID;
    private int currentStage;
    boolean stillHaveTroops;
    private GameController gameController;//reserved
    private GameView view;
    private boolean unFinishedDeploy = false;

    //game constants
    public static final int  RECRUIT=0;
    public static final int  ATTACK=1;
    public static final int  DEFEND=2;
    public static final int  ENDGAME=-1;
    public static final int  TROOP_FOR_2_PEOPLE=50;
    public static final int  TROOP_FOR_3_PEOPLE=35;
    public static final int  TROOP_FOR_4_PEOPLE=3;
    public static final int  TROOP_FOR_5_PEOPLE=25;
    public static final int  TROOP_FOR_6_PEOPLE=20;
    //For Button pressed
    private Territory territoryCommand1; //the territory for the fist Button pressed
    private Territory territoryCommand2; //the territory for the second Button pressed

    /**
     * Constructor for new Game
     * @param players All players set from Mainpage
     * @param map Map chosen form Mainpage
     */
    public  Game(ArrayList<Player> players,GMap map)
    {
        this.players = players;
        gameMap = map;
        gameController= new GameController(this);//reserved
        view = new GameView(this,gameController);
        initial();
    }

    public GMap getGameMap() {
        return gameMap;
    }


    /**
     * if any Territory Button is pressed
     * @param source the source Territory Button
     */
    public void handleButtonPressed(TerritoryButton source) {
        if (territoryCommand1==null){
            territoryCommand1 = source.getTerritory();
            System.out.println("debug first territory"+territoryCommand1);//debug first territory
        }else if (territoryCommand2==null)
        {
            territoryCommand2 = source.getTerritory();
            System.out.println("debug second territory"+territoryCommand1);//debug second territory
        }
        else{
            System.out.println("debug forget to clear territorycommand");
        }

        if(currentStage==RECRUIT) {
            try {
                int howmany = Integer.valueOf(JOptionPane.showInputDialog("how many to draft"));//need to change to mouse control later
                stillHaveTroops=draft(currentPlayer,territoryCommand1,howmany);
                if (stillHaveTroops){}//do nothing
                else {changeState();}
                territoryCommand1=null;//clear first command for next draft
                return;
            }catch (Exception e)
            {
                System.out.println(e);
                return;
            }
        }

        if(currentStage==ATTACK)
        {
            if (territoryCommand2==null) { //if the attack country is chosen but target not chosen
                view.getInfoLabel().setText("Please Press the second button");
                disableAllButtons();
                enableEnemyTerritoryButton(territoryCommand1);
                return;
            }
            else {
                attack(territoryCommand1,territoryCommand2);
                //clear both command for next attack
                territoryCommand1=null;
                territoryCommand2=null;
                //refresh availble attack country
                if(unFinishedDeploy){return;}
                disableAllButtons();
                enablePlayersButtonCanAttack();
                return;
            }
        }

    }

    private void enableEnemyTerritoryButton(Territory attackFrom) {
        for (Territory neighOfThis:attackFrom.getNeighbourList()) {
            if(neighOfThis.getHolder()!= currentPlayer){
                neighOfThis.getTerritoryButton().setEnabled(true);
                neighOfThis.getTerritoryButton().update();
            }
        }
    }

    public void initial(){
        currentStage = ENDGAME;
        currentPlayerID=-1;
        int initArmy;
        int playernumber = players.size();
        if (playernumber==2){initArmy=TROOP_FOR_2_PEOPLE;}
        else if (playernumber==3){initArmy=TROOP_FOR_3_PEOPLE;}
        else if (playernumber==4){initArmy=TROOP_FOR_4_PEOPLE;}
        else if (playernumber==5){initArmy=TROOP_FOR_5_PEOPLE;}
        else if (playernumber==6){initArmy=TROOP_FOR_6_PEOPLE;}
        else{
            initArmy=0;
            System.out.println("Unsupported number");
        }
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
                p.getTerritoryLinkedList().add(t);
                territoriescopy1.remove(t);
            }
            p.increaseUnassignedTroops(initArmy-round);
        }
        for (int count = 0;count<territoriescopy1.size();count++){
            Player rest = players.get(count);
            Territory temp = territoriescopy1.get(count);
            temp.setHolder(rest);
            temp.increaseTroops(1);
            rest.getTerritoryLinkedList().add(temp);
            rest.decreaseUnassignedTroops(1);
        }
        for(Player p : players)
        {
            LinkedList<Territory> playerHoldTerritorys=p.getTerritoryLinkedList();
            while(p.getUnAssignedTroops()>0)
            {
                Territory temp = playerHoldTerritorys.get(random.nextInt(playerHoldTerritorys.size()));
                temp.increaseTroops(1);
                p.decreaseUnassignedTroops(1);
            }
        }
        changeState();

    }



    private void disableAllButtons() {
        //close all button before start
        for(Territory territory:gameMap.getTerritoryArrayList())
        {
            territory.getTerritoryButton().setEnabled(false);
            territory.getTerritoryButton().update();
        }
    }

    /**
     * for intermediate step
     */
    private void disableAllButtonsWithoutUpdate() {
        //close all button before start
        for(Territory territory:gameMap.getTerritoryArrayList())
        {
            territory.getTerritoryButton().setEnabled(false);
        }
    }

    public void skipStage() {
        if(currentStage!=RECRUIT)
        {
            changeState();
        }
        else {view.getInfoLabel().setText("you can not skip draft stage");}
    }

    public boolean draft(Player who,  Territory to, int howMany)

    {
        if (who.getUnAssignedTroops()>=howMany){
            to.increaseTroops(howMany);
            who.decreaseUnassignedTroops(howMany);
            view.getInfoLabel().setText("You have moved "+howMany+" to "+to.getName());
        }
        else {System.out.println("not enough troops");}


        if (who.getUnAssignedTroops()>0){return true;}
        else {return false;}
    }



    public void attack(Territory attackCountry,Territory defenceCountry)
    {
        blitz(attackCountry,defenceCountry);
        /*
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
        }*/

    }


    public void blitz(Territory attackCountry, Territory defenceCountry) {
        int attack;
        int defence;
        while((attackCountry.getTroops()>1)&&(defenceCountry.getTroops()>0)) {
            attack = attackCountry.getTroops()-1;
            defence = defenceCountry.getTroops();
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
        defenceCountry.getHolder().removeTerritory(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addTerritory(defenceCountry);
        defenceCountry.getTerritoryButton().update();
            System.out.println(attackCountry.getHolder().getName() + "attacked successfully! Currently you have "+attackCountry.getTroops()+ " in your original country.");
            System.out.println(" How many troops deploy to " + defenceCountry.getName());
            if(attackCountry.getTroops()<4){
                System.out.println(attackCountry.getTroops());
                System.out.println(defenceCountry.getTroops());
                attackCountry.decreaseTroops(attackCountry.getTroops()-1);
                defenceCountry.increaseTroops(attackCountry.getTroops()-1);
                System.out.println(attackCountry.getTroops());
                System.out.println(defenceCountry.getTroops());
                unFinishedDeploy=false;
                disableAllButtons();
                enablePlayersButtonCanAttack();
                return;
            }

            JFrame temp = new JFrame();
            //slider setting
            JSlider deploy = new JSlider(JSlider.HORIZONTAL,3,attackCountry.getTroops()-1,attackCountry.getTroops()-1);
            deploy.setMajorTickSpacing(1);
            deploy.setPaintLabels(true);
            temp.add(deploy);
            //confirm Button
            JButton confirm = new JButton("OK");
            temp.add(confirm);
            confirm.addActionListener(e ->
            {
                attackCountry.decreaseTroops(deploy.getValue());
                defenceCountry.increaseTroops(deploy.getValue());
                unFinishedDeploy = false;
                view.setEnabled(true);
                temp.dispose();
                disableAllButtons();
                enablePlayersButtonCanAttack();
            });


            temp.setSize(300,100);
            temp.setVisible(true);
            view.setEnabled(false);
        unFinishedDeploy = true;




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
        disableAllButtons();
        currentStage = (currentStage+1)%3;//change State
        if(currentStage==RECRUIT)
        {
            currentPlayerID=(currentPlayerID+1)%players.size();//change Player
            currentPlayer = players.get(currentPlayerID);
            giveCurrentPlayerTroops();
            view.getInfoLabel().setText(currentPlayer.printPlayerinfo());
            enablePlayersButton();
            view.getInfoLabel().setText("now "+currentPlayer.getName()+"'s turn, stage Recruit");

        }
        else if(currentStage==ATTACK)
        {
            enablePlayersButtonWithTroopsBiggerthanOne();
            view.getInfoLabel().setText("now "+currentPlayer.getName()+"'s turn, stage Attack");
        }
        else {
            enablePlayersButtonWithTroopsBiggerthanOne();
            view.getInfoLabel().setText("now "+currentPlayer.getName()+"'s turn, stage Defend");
        }
    }

    private void enablePlayersButtonWithTroopsBiggerthanOne() {
        for (Territory territory:currentPlayer.getTerritoryLinkedList())
        {
            if(territory.getTroops()>1) {
                territory.getTerritoryButton().setEnabled(true);
                territory.getTerritoryButton().update();
            }
        }
    }

    private void enablePlayersButtonCanAttack() {
        for (Territory territory:currentPlayer.getTerritoryLinkedList())
        {
            if((territory.getTroops()>1) && (!allSelfcountrySurronding(territory))) {
                territory.getTerritoryButton().setEnabled(true);
                territory.getTerritoryButton().update();
            }
        }
    }

    public boolean  allSelfcountrySurronding(Territory territory)
    {
        for(Territory neibour: territory.getNeighbourList())
        {
            if(neibour.getHolder()!=territory.getHolder()){return false;}
        }
        return  true;
    }

    private void giveCurrentPlayerTroops() {
        int landnum = currentPlayer.getTerritoryLinkedList().size();
        int num = Math.floorDiv(landnum,3);
        if (num <3){num =3;}
        currentPlayer.increaseUnassignedTroops(num);
        view.getInfoLabel().setText("you own "+landnum+"Territories and you got "+num+"troops");
    }

    private void enablePlayersButton() {
        for (Territory territory:currentPlayer.getTerritoryLinkedList())
        {
            territory.getTerritoryButton().setEnabled(true);
            territory.getTerritoryButton().update();
        }
    }

}

