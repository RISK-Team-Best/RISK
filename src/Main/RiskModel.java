package Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * The main Game.
 */
public class   RiskModel {

    private final List<RiskViewInterface> viewList;

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

    private Stage currentStage;

    private final int TWO_PLAYERS_TROOPS = 50;
    private final int THREE_PLAYERS_TROOPS = 35;
    private final int FOUR_PLAYERS_TROOPS = 30;
    private final int FIVE_PLAYERS_TROOPS = 25;
    private final int SIX_PLAYERS_TROOPS = 20;

    private HashMap<Integer, Color> colorIDHashMap = new HashMap<>();

    private boolean originTerritoryButtonPressed = true;
    private boolean targetTerritoryButtonPressed = true;

    private Territory attackTerritory;
    private Territory defenceTerritory;

    private String originTerritoryName = "";
    private String targetTerritoryName = "";

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
        viewList = new ArrayList<>();

        colorIDHashMap.put(0,Color.MAGENTA);
        colorIDHashMap.put(1,Color.CYAN);
        colorIDHashMap.put(2,Color.GREEN);
        colorIDHashMap.put(3,Color.PINK);
        colorIDHashMap.put(4,Color.WHITE);
        colorIDHashMap.put(5,Color.LIGHT_GRAY);
    }

    public void addView(RiskViewInterface viewInterface){
        viewList.add(viewInterface);
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
     * Add player names into the game
     */
    public void addPlayersName(String[] playerNameList,Boolean[] AITypeList) {
        players.clear();
        for (int i = 0; i < numberPlayers; i++) {
            players.add(new Player(playerNameList[i],AITypeList[i]));
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
     * Get the player's territories which are available to attack
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
     * Get the enemy's territories which are surrounded this player's this territory
     *
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
     * @return Whether player conquered the target territory.
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
     * @return Whether player conquered the target territory.
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
     * Get the player's available territories for fortify
     *
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
     * Get the territories connect to the fortifyCountry and can be fortified
     *
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
     * Check whether the territory has the enemy neighbors.
     *
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
     * If only any player lost all territories. Prompt to show his failure.
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
     * Update any player has the continent or lose
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
     * Set the number of players
     *
     */
    public void setPlayerNum(int playerNum){
        this.numberPlayers = playerNum;
    }

    /**
     * Getter of playerNum
     * @return the number of players
     */
    public int getPlayerNum(){
        return this.numberPlayers;
    }

    /**
     * Getter for allCountries in arraylist
     * @return all territory list
     */
    public ArrayList<Territory> getAllCountries() {
        return allCountries;
    }

    /**
     * getter of CurrentPlayer
     * @return the player that currently doing the action
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Get the next player
     * @param currentPlayerID
     * @return the next player
     */
    public Player getNextPlayer(int currentPlayerID){
        int nextPlayerID = (currentPlayerID+1)%numberPlayers;
        if(!playerIDHashMap.containsKey(nextPlayerID))return getNextPlayer(nextPlayerID);
        return playerIDHashMap.get(nextPlayerID);
    }

    /**
     * setter for Current player
     *
     * @param player set the current Player
     */
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    /**
     * The Continent and Territory info. Write it in html and print out on ContinentInfo Panel
     *
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
     * Get the territory's reference by territory's name
     *
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
     * Get player through ID
     *
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
     * Getter for battleStatusString, used in GUI.
     *
     * @return the status of the battle
     */
    public String getBattleStatusString() {
        return battleStatusString;
    }

    public void newGameProcess(){
        currentStage = Stage.DRAFT;

        int playerNum = 0;
        for(RiskViewInterface view: viewList){
            playerNum = view.getNumberPlayerDialog();
        }
        setPlayerNum(playerNum);


        String[] playerNameList = new String[0];
        Boolean[] AITypeList = new Boolean[0];
        for(RiskViewInterface view: viewList){
            LinkedHashMap<String,Boolean> namesAndType = view.popGetName();
            playerNameList = namesAndType.keySet().toArray(new String[0]);
            AITypeList = namesAndType.values().toArray(new Boolean[0]);
        }
        addPlayersName(playerNameList,AITypeList);
        initialGame();
        for(RiskViewInterface view: viewList){
            for(Territory territory:getAllCountries()){
                view.setTerritoryButtonTroops(territory.getName(),territory.getTroops());
            }
        }
        for(RiskViewInterface view: viewList){
            view.updateNewGameProcess(getMapInfoThroughContinent(),getCurrentPlayer().getName());
            paintTerritoryButtons(view);
        }
    }

    public void draftPrepare(){
        if(currentPlayer.isAI()){
            AIProcess();
            return;
        }
        currentStage =Stage.DRAFT;
        checkContinent(currentPlayer);
        currentPlayer.gainTroopsFromTerritory();
        String continentBonus="";
        if(currentPlayer.haveContinent()) {
            continentBonus = " With ";
            for(Continent continent:currentPlayer.getContinents()){
                continentBonus+="--"+continent.getName();
            }
        }
        for(RiskViewInterface view: viewList){
            view.updateDraftPrepare(currentPlayer,currentStage,continentBonus);
        }
    }

    public void attackPrepare(){
        currentStage = Stage.ATTACK;
        originTerritoryButtonPressed = true;
        for(RiskViewInterface view: viewList){
            view.updateAttackPrepare(currentPlayer,currentStage,getAttackTerritoriesList(currentPlayer));
        }
    }

    public void fortifyPrepare(){
        currentStage = Stage.FORTIFY;
        for(RiskViewInterface view: viewList) {
            view.updateFortifyPrepare(getFortifyTerritories(currentPlayer),currentPlayer,currentStage);
        }
    }

    public void deployPrepare(){
        currentStage = Stage.DEPLOY;
        for(RiskViewInterface view: viewList){
            view.updateDeployPrepare(currentPlayer,currentStage,attackTerritory.getTroops()-1);
        }
    }

    public void skipProcess(){
        if(currentStage==Stage.ATTACK){
            resetButtonsAndBoxProcedure();
            for(RiskViewInterface view: viewList){
                view.updateSkipAttack(currentPlayer);
            }
        }
        if(currentStage==Stage.FORTIFY){
            resetButtonsAndBoxProcedure();
            currentPlayer = getNextPlayer(currentPlayer.getID());
            for(RiskViewInterface view: viewList){
                view.updateSkipFortify(currentPlayer);
            }
        }
    }

    public void confirmProcess(){
        if(currentStage==Stage.DRAFT){
            draftProcess();
        }

        if(currentStage==Stage.ATTACK){
            attackProcess();
        }

        if(currentStage==Stage.FORTIFY){
            fortifyProcess();
            resetButtonsAndBoxProcedure();
        }

        if(currentStage==Stage.DEPLOY){
            deployTroopsProcess();
            checkWinner();
        }
    }

    public void resetButtonsAndBoxProcedure(){
        if(!originTerritoryName.equals("")){
            originTerritoryButtonPressed = true;
            originTerritoryName = "";
        }
        if(!targetTerritoryName.equals("")){
            targetTerritoryButtonPressed = true;
            targetTerritoryName = "";
        }
        for(RiskViewInterface view: viewList) {
            paintTerritoryButtons(view);
            view.resetButtonsAndBox(getAttackTerritoriesList(currentPlayer));
        }

    }

    private void draftProcess(){
        if(originTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select One territory!", "No Territory Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for(RiskViewInterface view: viewList) {
            draft(currentPlayer, originTerritoryName, view.getSelectedTroops());
            view.setContinentsLabel(getMapInfoThroughContinent());
            view.updateDraftProcess(currentPlayer,currentStage);
            view.setTerritoryButtonTroops(originTerritoryName,getTerritoryByString(originTerritoryName).getTroops());
            paintTerritoryButtons(view);
            if(currentPlayer.getTroops()==0){
                view.updateDraftFinish(currentPlayer);
                return;
            }
            view.enableOriginalTerritories(currentPlayer.getTerritories());
        }
        originTerritoryName="";
        originTerritoryButtonPressed = true;
    }

    private void attackProcess() {
        if(originTerritoryName.equals("") || targetTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        attackTerritory = getTerritoryByString(originTerritoryName);
        defenceTerritory = getTerritoryByString(targetTerritoryName);
        AttackWay attackWay = null;
        for(RiskViewInterface view: viewList) {
            attackWay = view.getAttackTroopsBox();
        }
        boolean gainTerritory = battle(attackTerritory,defenceTerritory,attackWay);
        for(RiskViewInterface view: viewList) {
            view.setTerritoryButtonTroops(originTerritoryName, attackTerritory.getTroops());
            view.setTerritoryButtonTroops(targetTerritoryName, defenceTerritory.getTroops());
            view.setContinentsLabel(getMapInfoThroughContinent());
            if(gainTerritory) {
                JOptionPane.showMessageDialog(null,getBattleStatusString()+"/nYou conquered "+defenceTerritory.getName()+"!");
                view.updateWinAttack(currentPlayer);
                return;
            }
        }
        JOptionPane.showMessageDialog(null,getBattleStatusString()+"/nYou didn't conquered "+defenceTerritory.getName()+".");
        resetButtonsAndBoxProcedure();
        checkContinent(currentPlayer);
    }

    public void fortifyProcess(){
        if(originTerritoryName.equals("") || targetTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Territory startCountry = getTerritoryByString(originTerritoryName);
        Territory destinationCountry = getTerritoryByString(targetTerritoryName);
        int troops = 0;
        for(RiskViewInterface view: viewList) {
            troops = view.getSelectedTroops();
        }
        fortify(startCountry,destinationCountry,troops);
        currentPlayer = getNextPlayer(currentPlayer.getID());
        for(RiskViewInterface view: viewList) {
            view.setContinentsLabel(getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(originTerritoryName, startCountry.getTroops());
            view.setTerritoryButtonTroops(targetTerritoryName, destinationCountry.getTroops());
            view.updateFortifyFinish(currentPlayer);
        }
    }

    public void deployTroopsProcess(){
        for(RiskViewInterface view: viewList) {
            deployTroops(attackTerritory, defenceTerritory, view.getSelectedTroops());
            view.setContinentsLabel(getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(originTerritoryName, getTerritoryByString(originTerritoryName).getTroops());
            view.setTerritoryButtonTroops(targetTerritoryName, getTerritoryByString(targetTerritoryName).getTroops());
            resetButtonsAndBoxProcedure();
            view.updateDeployFinish(currentPlayer);
        }
        currentStage = Stage.ATTACK;
    }

    public void territoryButtonClickProcess(String territoryName,JButton button){
        if(currentStage==Stage.DRAFT){
            if(originTerritoryButtonPressed){
                originTerritoryName = territoryName;
                for(RiskViewInterface view: viewList) {
                    view.onlyEnableOriginTerritory(territoryName);
                }
                button.setBackground(Color.RED);
            }else{
                originTerritoryName = "";
                for(RiskViewInterface view: viewList) {
                    view.enableOriginalTerritories(currentPlayer.getTerritories());
                    paintTerritoryButtons(view);
                }
            }
            originTerritoryButtonPressed = !originTerritoryButtonPressed;
        }
        if(currentStage==Stage.ATTACK){
            if (originTerritoryButtonPressed) {
                originTerritoryName = territoryName;
                button.setBackground(Color.RED);
                for(RiskViewInterface view: viewList) {
                    view.updateClickAttackTerritoryButton(getTerritoryByString(originTerritoryName).getTroops(),getDefenceTerritories(currentPlayer, getTerritoryByString(originTerritoryName)), originTerritoryName);
                }
                originTerritoryButtonPressed =! originTerritoryButtonPressed;
            }else if(territoryName.equals(originTerritoryName)){
                originTerritoryName = "";
                if(!targetTerritoryName.equals("")){
                    targetTerritoryButtonPressed = true;
                }
                for(RiskViewInterface view: viewList) {
                    view.resetButtonsAndBox(getAttackTerritoriesList(currentPlayer));
                    paintTerritoryButtons(view);
                }
                originTerritoryButtonPressed =! originTerritoryButtonPressed;
            }else if(targetTerritoryButtonPressed){
                targetTerritoryName = territoryName;
                button.setBackground(Color.ORANGE);
                for(RiskViewInterface view: viewList) {
                    view.updateClickTargetTerritoryButton(originTerritoryName,targetTerritoryName);
                }
                targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
            }else if(!targetTerritoryButtonPressed){
                button.setBackground(colorIDHashMap.get(getTerritoryByString(targetTerritoryName).getHolder().getID()));
                targetTerritoryName = "";
                for(RiskViewInterface view: viewList) {
                    view.updateCancelDefenceTerritoryButton(originTerritoryName,getDefenceTerritories(currentPlayer, getTerritoryByString(originTerritoryName)));
                }
                targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
            }
        }
        if(currentStage==Stage.FORTIFY){
            if (originTerritoryButtonPressed) {
                originTerritoryName = territoryName;
                button.setBackground(Color.RED);
                for(RiskViewInterface view:viewList) {
                    view.updateClickFortifyButton(getTerritoryByString(originTerritoryName).getTroops() - 1,getFortifiedTerritory(getTerritoryByString(originTerritoryName), currentPlayer), originTerritoryName);
                }
                originTerritoryButtonPressed =! originTerritoryButtonPressed;
            }else if(territoryName.equals(originTerritoryName)){
                originTerritoryName = "";
                if(!targetTerritoryName.equals("")){
                    targetTerritoryButtonPressed = true;
                }
                for(RiskViewInterface view:viewList) {
                    paintTerritoryButtons(view);
                    view.resetButtonsAndBox(getFortifyTerritories(currentPlayer));
                }
                originTerritoryButtonPressed =! originTerritoryButtonPressed;
            }else if(targetTerritoryButtonPressed){
                targetTerritoryName = territoryName;
                button.setBackground(Color.ORANGE);
                for(RiskViewInterface view: viewList) {
                    view.updateClickTargetTerritoryButton(originTerritoryName,targetTerritoryName);
                }
                targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
            }else if(!targetTerritoryButtonPressed){
                button.setBackground(colorIDHashMap.get(getTerritoryByString(targetTerritoryName).getHolder().getID()));
                targetTerritoryName = "";
                for(RiskViewInterface view: viewList) {
                    view.updateCancelFortifyTerritoryButton(originTerritoryName,getFortifiedTerritory(getTerritoryByString(originTerritoryName),currentPlayer));
                }
                targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
            }
        }
    }

    public void paintTerritoryButtons(RiskViewInterface view){
        for(int id=0; id<numberPlayers;id++){
            view.paintTerritoryButtons(getPlayerById(id),colorIDHashMap.get(id));
        }
    }

    public void AIProcess(){
        AIDraftProcess();
        AIAttackProcess();
        AIFortifyProcess();
    }

    public void AIDraftProcess(){

    }

    public void AIAttackProcess(){

    }

    public void AIFortifyProcess(){

    }
}
