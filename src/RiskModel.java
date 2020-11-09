import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * The main Game.
 */
public class   RiskModel {

    private final ArrayList<Player> players;
    private final ArrayList<Territory> allCountries;
    private final ArrayList<Continent> allContinents;
    private final ArrayList<Territory> originTerritory = new ArrayList<>();
    private final ArrayList<Territory> targetTerritory = new ArrayList<>();
    private final ArrayList<Territory> fortifyTerritory = new ArrayList<>();
    private final ArrayList<Territory> fortifiedTerritory = new ArrayList<>();

    private final HashMap<Integer, Integer> initialTroopHashMap;
    private final HashMap<Integer, Player> playerIDHashMap;

    private final LinkedHashSet<Territory> neighborCountries;

    private int numberPlayers;

    private final Board board;
    private final Board tempBoard;

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
        playerIDHashMap = new HashMap<>();
        setInitialTroopHashMap();
        allCountries = new ArrayList<>();
        allContinents = board.getAllContinents();
        neighborCountries = new LinkedHashSet<>();
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
            playerIDHashMap.put(i,players.get(i));
        }
        currentPlayer = players.get(0);

    }

    /**
     * @param playerNameList
     */
    public void addPlayersName(String[] playerNameList) {
        players.clear();
        for (int i = 0; i < numberPlayers; i++) {
            players.add(new Player(playerNameList[i]));
        }
    }

    /**
     * set initial troops based on player number
     */
    public void setInitialTroopHashMap() {
        initialTroopHashMap.put(2, TWO_PLAYERS_TROOPS);
        initialTroopHashMap.put(3, THREE_PLAYERS_TROOPS);
        initialTroopHashMap.put(4, FOUR_PLAYERS_TROOPS);
        initialTroopHashMap.put(5, FIVE_PLAYERS_TROOPS);
        initialTroopHashMap.put(6, SIX_PLAYERS_TROOPS);
    }

    /**
     * assign the troops randomly for each player
     */
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
        int averageTroopsEachState = initialTroopHashMap.get(numberPlayers) / player.getTerritories().size();
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
     * @param player
     * @return the attack territory list
     */
    public ArrayList<Territory> getAttackTerritoriesList(Player player){
        originTerritory.clear();
        for(Territory territory:player.getTerritories()){
            if(territory.getTroops()>1&&checkAttackableNeighbors(territory,player))originTerritory.add(territory);
        }
        return originTerritory;
    }

    /**
     * @param player
     * @param territory
     * @return the defence territory list
     */
    public ArrayList<Territory> getDefenceTerritories(Player player,Territory territory){
        targetTerritory.clear();
        for (Territory neighbor : board.getAllNeighbors(territory.getName())) {
            for(Territory territory1:allCountries) {
                if(territory1.getName().equals(neighbor.getName())&&(!territory1.getHolder().equals(player))) {
                    targetTerritory.add(territory1);
                }
            }
        }
        return targetTerritory;
    }

    /**
     * Battle.
     * User can use one die, two dices, three dices or blitz to attack.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public boolean battle(Territory attackCountry, Territory defenceCountry,AttackWay attackWay) {
        battleStatusString = "";
        if(attackWay.equals(AttackWay.BLITZ)) return blitz(attackCountry,defenceCountry);
        int defenceTroops = 1;
        if (defenceCountry.getTroops() >= 2) defenceTroops = 2;
        Dices attackDice = new Dices(attackWay.getAttackTroops());
        Dices defenceDice = new Dices(defenceTroops);
        compareDices(attackDice, defenceDice, attackCountry, defenceCountry);
        return defenceCountry.getTroops() == 0;
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
            int attack = attackCountry.getTroops()-1;
            int defence = defenceCountry.getTroops();
            if (attack > 3) attack = 3;
            if (defence > 2) defence = 2;
            Dices attackDice = new Dices(attack);
            Dices defenceDice = new Dices(defence);
            compareDices(attackDice, defenceDice, attackCountry, defenceCountry);
        }
        return defenceCountry.getTroops() == 0;
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
        int attackLostTroops=0;
        int defenceLostTroops=0;
        battleStatusString += attackCountry.getName()+" rolls: ";
        for(int i = 0; i<attackDice.getDicesAmount();i++){
            battleStatusString += attackDice.getIndexDice(i)+", ";
        }
        battleStatusString+="\n"+defenceCountry.getName()+" rolls: ";
        for(int i = 0; i<defenceDice.getDicesAmount();i++){
            battleStatusString += defenceDice.getIndexDice(i)+", ";
        }
        for (int i = 0; i < minDiceAmount; i++) {
            if (attackDice.getIndexDice(i) > defenceDice.getIndexDice(i)) {
                defenceCountry.decreaseTroops(1);
                defenceLostTroops++;
            } else {
                attackCountry.decreaseTroops(1);
                attackLostTroops++;
            }
        }
        battleStatusString += "\n"+attackCountry.getName()+" lost "+attackLostTroops+" troops.\n"+defenceCountry.getName()+" lost "+defenceLostTroops+" troops.\n\n";
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
     * @param player
     * @return the fortify territory list
     */
    public ArrayList<Territory> getFortifyTerritories(Player player){
        fortifyTerritory.clear();
        for (Territory country : player.getTerritories()) {
            for(Territory territory:board.getAllNeighbors(country.getName())){
                if((getTerritoryByString(territory.getName()).getHolder().equals(country.getHolder()))&&(country.getTroops()>1)&&(!fortifyTerritory.contains(country))){
                    this.fortifyTerritory.add(country);
                }
            }
        }
        return fortifyTerritory;
    }

    /**
     * @param fortifyCountry
     * @param player
     * @return the fortified territory list
     */
    public ArrayList<Territory> getFortifiedTerritory(Territory fortifyCountry, Player player){
        fortifiedTerritory.clear();
        neighborCountries.clear();
        neighborCountries.add(fortifyCountry);
        addNeighborCountries(fortifyCountry, player);
        neighborCountries.remove(fortifyCountry);
        for (Territory country : neighborCountries) {
            fortifiedTerritory.add(country);
        }
        return fortifiedTerritory;
    }


    /**
     * The fortify move troops from fortifyCountry to fortifiedCountry
     * @param fortifyCountry
     * @param fortifiedCountry
     * @param troop
     */
    public void fortify(Territory fortifyCountry, Territory fortifiedCountry,int troop) {
        fortifyCountry.decreaseTroops(troop);
        fortifiedCountry.increaseTroops(troop);
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
                    neighborCountries.add(getTerritoryByString(neighbor.getName()));
                    if(neighborCountries.size()!=size) {
                        addNeighborCountries(getTerritoryByString(neighbor.getName()),player);
                    }
                    else break;
                }
            }
        }
    }

    /**
     * @param territory
     * @param player
     * @return ture if the territory has attackable neighbors
     *         false if the territory has no attackable neighbors
     */
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
            if(player.getTerritories().isEmpty()){
                JOptionPane.showMessageDialog(null, player.getName()+" fails...");
                players.remove(player);
                playerIDHashMap.remove(player.getID());
                break;
            }
        }
        if(playerIDHashMap.size()==1){
            JOptionPane.showMessageDialog(null, "Congratulation, we have the winner: "+players.get(0).getName());
            System.exit(-1);
        }
    }

    /**
     * Update the info in GUI
     */
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

    /**
     * @param num set the player number
     */
    public void setPlayerNum(int num){
        this.numberPlayers = num;
    }

    /**
     * @return the number of players
     */
    public int getPlayerNum(){
        return this.numberPlayers;
    }

    /**
     * @return all territory list
     */
    public ArrayList<Territory> getAllCountries() {
        return allCountries;
    }

    /**
     * @return the player that currently doing the action
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * @param currentPlayerID
     * @return the next player
     */
    public Player getNextPlayer(int currentPlayerID){
        int nextPlayerID = (currentPlayerID+1)%numberPlayers;
        if(!playerIDHashMap.containsKey(nextPlayerID))return getNextPlayer(nextPlayerID);
        return playerIDHashMap.get(nextPlayerID);
    }

    /**
     * @param player set the current Player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    /**
     * @return the map info
     */
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

    /**
     * @param territoryName
     * @return the territory that have the territoryName
     */
    public Territory getTerritoryByString(String territoryName){
        for(Territory territory:allCountries){
            if(territory.getName().equals(territoryName))return territory;
        }
        return null;
    }

    /**
     * @param id
     * @return player match the id
     */
    public Player getPlayerById(int id){
        for(Player player:players){
            if(player.getID()==id)return player;
        }
        return null;
    }

    /**
     * @return the status of the battle
     */
    public String getBattleStatusString() {
        return battleStatusString;
    }
}
