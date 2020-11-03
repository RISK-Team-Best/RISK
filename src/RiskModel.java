import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * The main Game.
 */
public class   RiskModel {
    private final Scanner scanner;

    private final ArrayList<Player> players;
    private final ArrayList<Territory> allCountries;
    private final ArrayList<Continent> allContinents;
    private final HashMap<Integer, Integer> initialTroopHashMap;

    private final LinkedHashSet<Territory> neighborCountries;

    private int numberPlayers;
    private int initialTroops;

    private final Board board;
    private Board tempBoard;

    public static DefaultListModel<Territory> originTerritoryJList;
    public static DefaultListModel<Territory> targetTerritoryJList;
    public static DefaultListModel<Territory> checkFortifyTerritoryJList;
    public static DefaultListModel<Territory> checkFortiableTerritoryJList;

    private String battleStatusString;

    private Player currentPlayer;

    private final int TWO_PLAYERS_TROOPS = 50;
    private final int THREE_PLAYERS_TROOPS = 35;
    private final int FOUR_PLAYERS_TROOPS = 30;
    private final int FIVE_PLAYERS_TROOPS = 25;
    private final int SIX_PLAYERS_TROOPS = 20;

    /**
     * Instantiates a new Game.
     *
     * @throws IOException the io exception
     */
    public RiskModel() throws IOException {
        players = new ArrayList<>();
        board = new Board();
        tempBoard = new Board();
        initialTroopHashMap = new HashMap<>();
        setInitialTroopHashMap();
        allCountries = new ArrayList<>();
        allContinents = board.getAllContinents();
        scanner = new Scanner(System.in);
        neighborCountries = new LinkedHashSet<>();

        originTerritoryJList = new DefaultListModel<>();
        targetTerritoryJList = new DefaultListModel<>();
        checkFortifyTerritoryJList = new DefaultListModel<>();
        checkFortiableTerritoryJList = new DefaultListModel<>();


        //initialGame();
//        System.out.println("Alright! Let's start battling!");
//        processGaming();
    }

    /**
     * Initialize the game include add number of players, add troops to players depending on player numbers,
     * assign randomly territories with randomly troops to each player.
     */
    public void initialGame() {
        assignCountriesRandomly();
        setTroopsInitially();
        for (Player player : players) {
            checkContinent(player);
            assignTroops(player);
            player.printPlayerInfo();
        }
        for(int i = 0;i<this.numberPlayers;i++){
            players.get(i).setID(i);
        }
        currentPlayer = players.get(0);

    }

    /**
     * Processing the game includes making each player to do draft, attack and fortify stage.
     * And check whether we have the winner. If we have the winner, end game.
     */
    public void processGaming() {
        while (players.size() != 1) {
            for (Player player : players) {
                currentPlayer = player;
                //System.out.println("\nIt's "+player.getName()+"'s turn:");
//                draft(player);
//                attack(player);
                checkWinner();
                //fortify(player);
                checkContinent(player);
                player.printPlayerInfo();
            }
            for (Player player : players) {
                checkContinent(player);
                player.gainTroopsFromTerritory();
                player.printPlayerInfo();
            }
        }
    }

    public void addPlayersName(String[] playerNameList) {
        players.clear();
        for (int i = 0; i < numberPlayers; i++) {
            players.add(new Player(playerNameList[i]));
        }
    }

    public void setInitialTroopHashMap() {
        initialTroopHashMap.put(2, TWO_PLAYERS_TROOPS);
        initialTroopHashMap.put(3, THREE_PLAYERS_TROOPS);
        initialTroopHashMap.put(4, FOUR_PLAYERS_TROOPS);
        initialTroopHashMap.put(5, FIVE_PLAYERS_TROOPS);
        initialTroopHashMap.put(6, SIX_PLAYERS_TROOPS);
    }

    public void setTroopsInitially() {
        int troops = initialTroopHashMap.get(numberPlayers);
        for (Player player : players) {
            player.setTroops(troops);
        }
    }

    /**
     * Assign countries randomly.
     */
    public void assignCountriesRandomly(){
        ArrayList<Territory> tempAllCountries = tempBoard.getAllCountries();
        int averageStates = tempAllCountries.size() / numberPlayers;
        int extraStates = tempAllCountries.size() % numberPlayers;
        Random random = new Random();
        for (Player player : players) {
            for (int i = 0; i < averageStates; i++) {
                Territory tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
                tempCountry.setHolder(player);
                player.addTerritory(tempCountry);
                allCountries.add(tempCountry);
                tempAllCountries.remove(tempCountry);
            }
        }
        for (int i = 0; i < extraStates; i++) {
            Territory tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
            tempCountry.setHolder(players.get(i));
            players.get(i).addTerritory(tempCountry);
            allCountries.add(tempCountry);
            tempAllCountries.remove(tempCountry);
        }
    }

    /**
     * Check whether the player has continent.
     *
     * @param player the player to be checked continent
     */
    public void checkContinent(Player player) {
        for (Continent continent : allContinents) {
            if (player.getTerritoriesString().containsAll(continent.memberString())) {
                player.addContinent(continent);
            }
        }
        for (Continent continent : player.getContinents()) {
            if (!player.getTerritoriesString().containsAll(continent.memberString())) {
                player.removeContinent(continent);
            }
        }
    }

    /**
     * Assign random troops initially to all territories of this player.
     *
     * @param player the player that assign troops to all territories  randomly
     */
    public void assignTroops(Player player) {
        int averageTroopsEachState = initialTroops / player.getTerritories().size();
        for (Territory territory : player.getTerritories()) {
            int signedTroops = (int) (Math.random() * averageTroopsEachState + 1);
            territory.setTroops(signedTroops);
            player.decreaseTroops(signedTroops);
        }
        Random random = new Random();
        int leftTroops = player.getTroops();
        for (int i = 0; i < leftTroops; i++) {
            Territory tempTerritory = player.getTerritories().get(random.nextInt(player.getTerritories().size()));
            tempTerritory.increaseTroops(1);
            player.decreaseTroops(1);
        }
    }


    /**
     * Draft stage.
     * Assign troops in player to his/her territories until the player has no troops in hand.
     *
     * @param player the player process draft stage
     */

    public void draft(Player player, String territory, int troops) {
        player.getTerritoryByString(territory).increaseTroops(troops);
        player.decreaseTroops(troops);
    }


    /**
     * Attack turn.
     * Print out the message that player can attack and process attack stage.
     *
     * @param player the player in attack stage
     */
//    public void attack(Player player) {
//        System.out.println("\nIt's ATTACK stage, you have these territory can attack:");
//        printAttackableInfo(player);
//        attackStage(player);
//    }

    /**
     * Attack stage.
     * Attack from one of player's territory to enemy's territory (Can skip this stage any time)
     *
     * @param player the player in attack stage
     */
//    public void attackStage(Player player) {
//        String attackCountryString;
//        String defenceCountryString;
//        Territory attackCountry;
//        Territory defenceCountry;
//        Outterloop:
//        do {
//            while (true) {
//                System.out.println("Please enter your territory that you want to launch an offense. (Enter \"skip\" to skip this stag)");
//                attackCountryString = scanner.nextLine();
//                if (attackCountryString.equals("skip")) break Outterloop;
//                else if (!player.checkTerritoryByString(attackCountryString)) {
//                    System.out.println("Please enter your own territory's name...");
//                    continue;
//                } else if (player.getTerritoryByString(attackCountryString).getTroops() == 1)
//                    System.out.println("You have to leave at least one troop to guard this city!");
//                attackCountry = player.getTerritoryByString(attackCountryString);
//                for (Territory neighbor : board.getAllNeighbors(attackCountry.getName())) {
//                    for (Player player1 : players) {
//                        if (player1.checkTerritoryByString(neighbor.getName()) && (!player1.getName().equals(player.getName()))) {
//                            System.out.println(player1.getTerritoryByString(neighbor.getName()).shortDescription());
//                        }
//                    }
//                }
//                System.out.println("Which territory do you want to attack?");
//                defenceCountryString = scanner.nextLine();
//                if (!defenceCountryString.equals(attackCountryString)) break;
//            }
//
//            for (Player player1 : players) {
//                if (player1.checkTerritoryByString(defenceCountryString)) {
//                    defenceCountry = player1.getTerritoryByString(defenceCountryString);
//                    battle(attackCountry, defenceCountry);
//                    break;
//                }
//            }
//            printAttackableInfo(player);
//        } while (player.checkAbilityToAttack());
//    }

    /**
     * Print ability to attack info.
     *
     * @param player the player to check info of his/her territories
//     */
//    public void printAttackableInfo(Player player) {
//        for (Territory country : player.getTerritories()) {
//            if (country.getTroops() == 1) continue;
//            System.out.print(country.shortDescription() + "--");
//            for (Territory neighbor : board.getAllNeighbors(country.getName())) {
//                for (Player player1 : players) {
//                    if (player1.checkTerritoryByString(neighbor.getName()) && (!player1.getName().equals(player.getName()))) {
//                        System.out.print(player1.getTerritoryByString(neighbor.getName()).shortDescription() + " ");
//                    }
//                }
//            }
//            System.out.println();
//        }
//    }

    public DefaultListModel<Territory> setAttackTerritories(Player player){
        originTerritoryJList.clear();
        for(Territory territory:player.getTerritories()){
            if(territory.getTroops()>1&&checkAttackableNeighbors(territory,player))originTerritoryJList.addElement(territory);
        }
        return originTerritoryJList;
    }

    public DefaultListModel<Territory> setDefenceTerritories(Player player,Territory territory){
        if(territory!= null) {
            targetTerritoryJList.clear();
            for (Territory neighbor : board.getAllNeighbors(territory.getName())) {
                for(Territory territory1:allCountries) {
                    if(territory1.getName().equals(neighbor.getName())&&(!territory1.getHolder().equals(player))) {
                        targetTerritoryJList.addElement(territory1);
                    }
                }
            }
        }
        return targetTerritoryJList;
    }


    /**
     * Battle.
     * User can use one die, two dices, three dices or blitz to attack.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public boolean battle(Territory attackCountry, Territory defenceCountry,AttackWay attackWay) {
//        if (attackCountry.getHolder() == defenceCountry.getHolder()) {
//            System.out.println("You cannot attack yourself!!");
//            return;
//        }
//        int defenceTroops;
//        int attackTroops;
//        if (defenceCountry.getTroops() >= 2) defenceTroops = 2;
//        else defenceTroops = 1;
//        loop:
//        while (true) {
//            System.out.println("How many troops(which type) do you want to attack? (one/two/three/blitz/finish)");
//            String type = scanner.nextLine();
//            switch (type) {
//                case "one":
//                    attackTroops = 1;
//                    break loop;
//                case "two":
//                    attackTroops = 2;
//                    break loop;
//                case "three":
//                    attackTroops = 3;
//                    break loop;
//                case "blitz":
//                    blitz(attackCountry, defenceCountry);
//                    return;
//                case "finish":
//                    return;
//                default:
//                    System.out.println("Please re-select your option without typos!");
//                    break;
//            }
//        }
//        if (attackTroops > attackCountry.getTroops()) {
//            System.out.println("Sorry, you only have " + attackCountry.getTroops() + " troops in this country");
//            attackTroops = attackCountry.getTroops();
//        }
        battleStatusString = "";
        if(attackWay.equals(AttackWay.BLITZ)) return blitz(attackCountry,defenceCountry);
        int defenceTroops = 1;
        if (defenceCountry.getTroops() >= 2) defenceTroops = 2;
        Dices attackDice = new Dices(attackWay.getAttackTroops());
        Dices defenceDice = new Dices(defenceTroops);
        compareDices(attackDice, defenceDice, attackCountry, defenceCountry);
        if(defenceCountry.getTroops()==0)return true;
        return false;
    }

    /**
     * Blitz.
     * Directly get the results of battle.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public boolean blitz(Territory attackCountry, Territory defenceCountry) {
        while ((attackCountry.getTroops() > 1) && (defenceCountry.getTroops() > 0)) {
            int attack = attackCountry.getTroops();
            int defence = defenceCountry.getTroops();
            if (attack > 3) attack = 3;
            if (defence > 2) defence = 2;
            Dices attackDice = new Dices(attack);
            Dices defenceDice = new Dices(defence);
            compareDices(attackDice, defenceDice, attackCountry, defenceCountry);
        }
        if (defenceCountry.getTroops() == 0) {
//            deployTroops(attackCountry, defenceCountry);
            return true;
        }
        return false;
    }

    /**
     * Compare dices to determine which territory wins this battle.
     *
     * @param attackDice     the attack dice
     * @param defenceDice    the defence dice
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void compareDices(Dices attackDice, Dices defenceDice, Territory attackCountry, Territory defenceCountry) {
        attackDice.diceRolling();
        defenceDice.diceRolling();
        int minDiceAmount = Math.min(attackDice.getDicesAmount(), defenceDice.getDicesAmount());
        for (int i = 0; i < minDiceAmount; i++) {
            if (attackDice.getIndexDice(i) > defenceDice.getIndexDice(i)) {
                defenceCountry.decreaseTroops(1);
                battleStatusString += attackCountry.getName() + " rolling " + attackDice.getIndexDice(i) + ", and " + defenceCountry.getName() + " rolling " + defenceDice.getIndexDice(i) + ".\n";
                battleStatusString += defenceCountry.getName() + " lost 1 troop.\n";
            } else {
                attackCountry.decreaseTroops(1);
                battleStatusString += attackCountry.getName() + " rolling " + attackDice.getIndexDice(i) + ", and " + defenceCountry.getName() + " rolling " + defenceDice.getIndexDice(i) + ".\n";
                battleStatusString += attackCountry.getName() + " lost 1 troop.\n";
            }
        }
    }

    /**
     * Deploy troops to the defeated territory after the attack player wins the battle.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void deployTroops(Territory attackCountry, Territory defenceCountry,int deployTroops) {
        attackCountry.decreaseTroops(deployTroops);
        defenceCountry.getHolder().removeTerritory(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addTerritory(defenceCountry);
        defenceCountry.increaseTroops(deployTroops);
    }

    /**
     * Fortify stage.
     * Move troops from one territory to the connect territories.
     *
     * @param player the player
     */
    public DefaultListModel<Territory> setFortifyTerritory(Player player) {

        for (Territory country : player.getTerritories()) {
            if (country.getTroops() > 1) {
                this.checkFortifyTerritoryJList.addElement(country);
            }
        }
        return checkFortifyTerritoryJList;
    }
    public DefaultListModel<Territory> setFortifiableTerritory(Territory fortifyCountry, Player player){
        neighborCountries.clear();
        checkFortiableTerritoryJList.clear();
        neighborCountries.add(fortifyCountry);
        addNeighborCountries(fortifyCountry, player);
        neighborCountries.remove(fortifyCountry);
        for (Territory country : neighborCountries) {
            checkFortiableTerritoryJList.addElement(country);
        }
        return checkFortiableTerritoryJList;
    }
    public void fortify(Territory fortifyCountry, Territory fortifiedCountry,int troop) {
        fortifyCountry.decreaseTroops(troop);
        fortifiedCountry.increaseTroops(troop);
        //currentPlayer = this.getNextPlayer();
}


    /**
     * Add all connected countries.
     * Used recursion to visit all connected territories and those which holder is this player to HashSet.
     *
     * @param territory the territory
     * @param player    the player
     */
    public void addNeighborCountries(Territory territory, Player player){
        int size = neighborCountries.size();
        for(Territory neighbor: board.getAllNeighbors(territory.getName())){
            for(Player player1:players){
                if(player1.checkTerritoryByString(neighbor.getName())&&(player1.getName().equals(player.getName()))){
                    neighborCountries.add(player1.getTerritoryByString(neighbor.getName()));
                    if(neighborCountries.size()!=size) {
                        addNeighborCountries(player1.getTerritoryByString(neighbor.getName()),player);
                    }
                    else break;
                }
            }
        }
    }

    public boolean checkAttackableNeighbors(Territory territory, Player player){
        for(Territory neighbor : board.getAllNeighbors(territory.getName())){
            for(Player player1:players){
                if(player1.checkTerritoryByString(neighbor.getName())&&(!player1.getName().equals(player.getName()))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check winner and print out congratulation message.
     */
    public void checkWinner(){
        for(Player player:players){
            if(player.getTerritories().size()==0){
                System.out.println(player.getName()+" fails...");
                players.remove(player);
            }
        }
        if(players.size()==1){
            System.out.println("Congratulation, we have the winner: "+players.get(0).getName());
            System.exit(-1);
        }
    }

    public void updateContinentListInfo(){
        for(Continent continent:allContinents){
            for(Player player:players){
                for(Territory territory:player.getTerritories()){
                    if(continent.memberString().contains(territory.getName())){
                        continent.getMemberTerritory(territory).setHolder(territory.getHolder());
                        continent.getMemberTerritory(territory).setTroops(territory.getTroops());
                    }
                }
            }
        }

    }
    public void setPlayerNum(int num){
        this.numberPlayers = num;
    }
    public int getPlayerNum(){
        return this.numberPlayers;
    }
    public ArrayList<Continent> getAllContinents() {
        return allContinents;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public Player getNextPlayer(){
        int i = (currentPlayer.getID()+1) % numberPlayers;
        return players.get(i);
    }
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }
    public String getMapInfoThroughContinent(){
        updateContinentListInfo();
        String str = "<html> TERRITORY-HOLDER-TROOPS<br><br>";
        for(Continent continent: allContinents){
            str+=continent.getName()+":<br>";
            for(Territory territory:continent.getMembers()){
                str+=territory.getName()+"-"+territory.getHolder().getName()+"-"+territory.getTroops()+" <br>";
            }
            str+="<br>";
        }
        str+="</html>";
        return  str;
    }

    public Territory getTerritoryByString(String territoryName){
        for(Territory territory:allCountries){
            if(territory.getName().equals(territoryName))return territory;
        }
        return null;
    }

    public String getBattleStatusString() {
        return battleStatusString;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        RiskModel model = new RiskModel();

    }
}
