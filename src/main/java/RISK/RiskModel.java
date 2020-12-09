package RISK;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

/**
 * The main Game.
 */
public class   RiskModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<RiskViewInterface> viewList;

    private ArrayList<Player> players;
    private ArrayList<Territory> allCountries;
    private ArrayList<Continent> allContinents;
    private final ArrayList<Territory> originTerritory = new ArrayList<>();
    private final ArrayList<Territory> targetTerritory = new ArrayList<>();
    private final ArrayList<Territory> fortifyTerritory = new ArrayList<>();
    private final ArrayList<Territory> fortifiedTerritory = new ArrayList<>();

    private final HashMap<Integer, Integer> initialTroopHashMap;
    private final HashMap<Integer, Player> playerIDHashMap;

    private final LinkedHashSet<Territory> neighborCountries;
    private final LinkedHashSet<String> tempNeighborCountries;

    private int numberPlayers;

    private Board board;
    private Board tempBoard;

    private String battleStatusString;
    private String statusLabel;

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
    private String mapName;

    /**
     * Instantiates a new Game.
     *
     * @param mapName the map name
     * @throws Exception the exception
     */
    public RiskModel(String mapName) throws Exception {
        this.mapName = mapName;
        players = new ArrayList<>();
        board = new Board(mapName);
        tempBoard = new Board(mapName);
        initialTroopHashMap = new HashMap<>();
        playerIDHashMap = new HashMap<>();
        setInitialTroopHashMap();
        allCountries = new ArrayList<>();
        allContinents = board.getAllContinents();
        neighborCountries = new LinkedHashSet<>();
        tempNeighborCountries = new LinkedHashSet<>();
        viewList = new ArrayList<>();

        colorIDHashMap.put(0, Color.MAGENTA);
        colorIDHashMap.put(1, Color.CYAN);
        colorIDHashMap.put(2, Color.GREEN);
        colorIDHashMap.put(3, Color.PINK);
        colorIDHashMap.put(4, Color.WHITE);
        colorIDHashMap.put(5, Color.LIGHT_GRAY);
    }

    /**
     * Invalid map boolean.
     *
     * @return the boolean
     */
    public boolean invalidMap(){
        for(Territory territory:board.getAllCountries()){
            tempNeighborCountries.clear();
            addAllNeighborCountries(territory);
            if(tempNeighborCountries.size()!=board.getAllCountries().size()){
                return true;
            }
        }
        return false;
    }

    /**
     * Add all neighbor countries.
     *
     * @param territory the territory
     */
    public void addAllNeighborCountries(Territory territory){
        int size = tempNeighborCountries.size();
        for(Territory neighbor : board.getAllNeighbors(territory.getName())){
            tempNeighborCountries.add(neighbor.getName());
            if(tempNeighborCountries.size()!=size){
                addAllNeighborCountries(neighbor);
            }else continue;
        }
    }

    /**
     * Sets board.
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets all continents.
     *
     * @param allContinents the all continents
     */
    public void setAllContinents(ArrayList<Continent> allContinents) {
        this.allContinents = allContinents;
    }

    /**
     * Add view.
     *
     * @param viewInterface the view interface
     */
    public void addView(RiskViewInterface viewInterface) {
        viewList.add(viewInterface);
    }

    /**
     * This method is initializing the game include add number of players, add troops to players depending on player numbers,
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
        for (int i = 0; i < this.numberPlayers; i++) {
            players.get(i).setID(i);
            playerIDHashMap.put(i, players.get(i));
        }
        currentPlayer = players.get(0);

    }

    /**
     * Gets map name.
     *
     * @return the map name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * This method is adding player names into the game
     *
     * @param playerNameList the player name list
     * @param AITypeList     the ai type list
     */
    public void addPlayersName(String[] playerNameList, Boolean[] AITypeList) {
        players.clear();
        for (int i = 0; i < numberPlayers; i++) {
            if (AITypeList[i]) players.add(new AIPlayer(playerNameList[i], this));
            else players.add(new Player(playerNameList[i], this));
        }
    }

    /**
     * Import players.
     *
     * @param players the players
     */
    public void importPlayers(ArrayList<Player> players) {
        this.players.clear();
        this.playerIDHashMap.clear();

        this.players = players;
        for (Player player : players) {
            playerIDHashMap.put(player.getID(), player);
        }
    }

    /**
     * This method is setting initial troops based on player number
     */
    public void setInitialTroopHashMap() {
        initialTroopHashMap.put(2, TWO_PLAYERS_TROOPS);
        initialTroopHashMap.put(3, THREE_PLAYERS_TROOPS);
        initialTroopHashMap.put(4, FOUR_PLAYERS_TROOPS);
        initialTroopHashMap.put(5, FIVE_PLAYERS_TROOPS);
        initialTroopHashMap.put(6, SIX_PLAYERS_TROOPS);
    }

    /**
     * This method is assigning the troops randomly for each player
     */
    public void setTroopsInitially() {
        int troops = initialTroopHashMap.get(numberPlayers);
        for (Player player : players) {
            player.setTroops(troops);
        }
    }

    /**
     * This method is assigning countries randomly.
     */
    public void assignCountriesRandomly() {
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
     * This method is checking whether the player has continent.
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
     * This method is assigning random troops initially to all territories of this player.
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
     * This method is for Draft stage.
     * Assign troops in player to his/her territories until the player has no troops in hand.
     *
     * @param player    the player process draft stage
     * @param territory the territory
     * @param troops    the troops
     */
    public void draft(Player player, String territory, int troops) {
        player.getTerritoryByString(territory).increaseTroops(troops);
        player.decreaseTroops(troops);
    }


    /**
     * This method is getting the player's territories which are available to attack
     *
     * @param player the player
     * @return the attack territory list
     */
    public ArrayList<Territory> getAttackTerritoriesList(Player player) {
        originTerritory.clear();
        for (Territory territory : player.getTerritories()) {
            if (territory.getTroops() > 1 && checkAttackableNeighbors(territory, player))
                originTerritory.add(territory);
        }
        return originTerritory;
    }

    /**
     * This method is getting the enemy's territories which are surrounded this player's this territory
     *
     * @param player    the player
     * @param territory the territory
     * @return the defence territory list
     */
    public ArrayList<Territory> getDefenceTerritories(Player player, Territory territory) {
        targetTerritory.clear();
        for (Territory neighbor : board.getAllNeighbors(territory.getName())) {
            for (Territory territory1 : allCountries) {
                if (territory1.getName().equals(neighbor.getName()) && (!territory1.getHolder().equals(player))) {
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
     * @param attackWay      the attack way
     * @return Whether player conquered the target territory.
     */
    public boolean battle(Territory attackCountry, Territory defenceCountry, AttackWay attackWay) {
        battleStatusString = "";
        if (attackWay.equals(AttackWay.BLITZ)) return blitz(attackCountry, defenceCountry);
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
            int attack = attackCountry.getTroops() - 1;
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
        int attackLostTroops = 0;
        int defenceLostTroops = 0;
        battleStatusString += attackCountry.getName() + " rolls: ";
        for (int i = 0; i < attackDice.getDicesAmount(); i++) {
            battleStatusString += attackDice.getIndexDice(i) + ", ";
        }
        battleStatusString += "\n" + defenceCountry.getName() + " rolls: ";
        for (int i = 0; i < defenceDice.getDicesAmount(); i++) {
            battleStatusString += defenceDice.getIndexDice(i) + ", ";
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
        battleStatusString += "\n" + attackCountry.getName() + " lost " + attackLostTroops + " troops.\n" + defenceCountry.getName() + " lost " + defenceLostTroops + " troops.\n\n";
    }

    /**
     * This method is deploying troops to the defeated territory after the attack player wins the battle.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     * @param deployTroops   the deploy troops
     */
    public void deployTroops(Territory attackCountry, Territory defenceCountry, int deployTroops) {
        attackCountry.decreaseTroops(deployTroops);
        defenceCountry.getHolder().removeTerritory(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addTerritory(defenceCountry);
        defenceCountry.increaseTroops(deployTroops);
    }


    /**
     * This method is getting the player's available territories for fortify
     *
     * @param player the player
     * @return the fortify territory list
     */
    public ArrayList<Territory> getFortifyTerritories(Player player) {
        fortifyTerritory.clear();
        for (Territory country : player.getTerritories()) {
            for (Territory territory : board.getAllNeighbors(country.getName())) {
                if ((getTerritoryByString(territory.getName()).getHolder().equals(country.getHolder())) && (country.getTroops() > 1) && (!fortifyTerritory.contains(country))) {
                    this.fortifyTerritory.add(country);
                }
            }
        }
        return fortifyTerritory;
    }

    /**
     * This method is getting the territories connect to the fortifyCountry and can be fortified
     *
     * @param fortifyCountry the fortify country
     * @param player         the player
     * @return the fortified territory list
     */
    public ArrayList<Territory> getFortifiedTerritory(Territory fortifyCountry, Player player) {
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
     *
     * @param fortifyCountry   the fortify country
     * @param fortifiedCountry the fortified country
     * @param troop            the troop
     */
    public void fortify(Territory fortifyCountry, Territory fortifiedCountry, int troop) {
        fortifyCountry.decreaseTroops(troop);
        fortifiedCountry.increaseTroops(troop);
    }


    /**
     * This method is adding all connected countries.
     * Used recursion to visit all connected territories and those which holder is this player to HashSet.
     *
     * @param territory the territory
     * @param player    the player
     */
    public void addNeighborCountries(Territory territory, Player player) {
        int size = neighborCountries.size();
        for (Territory neighbor : board.getAllNeighbors(territory.getName())) {
            for (Player player1 : players) {
                if (player1.checkTerritoryByString(neighbor.getName()) && (player1.getName().equals(player.getName()))) {
                    neighborCountries.add(getTerritoryByString(neighbor.getName()));
                    if (neighborCountries.size() != size) {
                        addNeighborCountries(getTerritoryByString(neighbor.getName()), player);
                    } else break;
                }
            }
        }
    }

    /**
     * This method is checking whether the territory has the enemy neighbors.
     *
     * @param territory the territory
     * @param player    the player
     * @return ture if the territory has attackable neighbors false if the territory has no attackable neighbors
     */
    public boolean checkAttackableNeighbors(Territory territory, Player player) {
        for (Territory neighbor : board.getAllNeighbors(territory.getName())) {
            for (Player player1 : players) {
                if (player1.checkTerritoryByString(neighbor.getName()) && (!player1.getName().equals(player.getName()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is checking winner and print out congratulation message.
     * If only any player lost all territories. Prompt to show his failure.
     */
    public void checkWinner() {
        for (Player player : players) {
            if (player.getTerritories().isEmpty()) {
                JOptionPane.showMessageDialog(null, player.getName() + " fails...");
                players.remove(player);
                playerIDHashMap.remove(player.getID());
                break;
            }
        }
        if (playerIDHashMap.size() == 1) {
            JOptionPane.showMessageDialog(null, "Congratulation, we have the winner: " + players.get(0).getName());
            System.exit(-1);
        }
    }

    /**
     * This method is for updating any player has the continent or lose
     */
    public void updateContinentListInfo() {
        for (Continent continent : allContinents) {
            for (Player player : players) {
                for (Territory territory : player.getTerritories()) {
                    if (continent.memberString().contains(territory.getName())) {
                        continent.getMemberTerritory(territory).setHolder(territory.getHolder());
                        continent.getMemberTerritory(territory).setTroops(territory.getTroops());
                    }
                }
            }
        }

    }

    /**
     * This method is setting the number of players
     *
     * @param playerNum the player num
     */
    public void setPlayerNum(int playerNum) {
        this.numberPlayers = playerNum;
    }


    /**
     * Getter for allCountries in arraylist
     *
     * @return all territory list
     */
    public ArrayList<Territory> getAllCountries() {
        return allCountries;
    }

    /**
     * getter of CurrentPlayer
     *
     * @return the player that currently doing the action
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the next player
     *
     * @param currentPlayerID the current player id
     * @return the next player
     */
    public Player getNextPlayer(int currentPlayerID) {
        int nextPlayerID = (currentPlayerID + 1) % numberPlayers;
        if (!playerIDHashMap.containsKey(nextPlayerID)) return getNextPlayer(nextPlayerID);
        return playerIDHashMap.get(nextPlayerID);
    }


    /**
     * The Continent and Territory info. Write it in html and print out on ContinentInfo Panel
     *
     * @return the map info
     */
    public String getMapInfoThroughContinent() {
        updateContinentListInfo();
        String str = "<html> TERRITORY-HOLDER-TROOPS<br><br>";
        for (Continent continent : allContinents) {
            str += continent.getName() + ":<br>";
            for (Territory territory : continent.getMembers()) {
                str += territory.getName() + "-" + territory.getHolder().getName() + "-" + territory.getTroops() + " <br>";
            }
            str += "<br>";
        }
        str += "</html>";
        return str;
    }

    /**
     * Get the territory's reference by territory's name
     *
     * @param territoryName the territory name
     * @return the territory that have the territoryName
     */
    public Territory getTerritoryByString(String territoryName) {
        for (Territory territory : allCountries) {
            if (territory.getName().equals(territoryName)) return territory;
        }
        return null;
    }

    /**
     * Get player through ID
     *
     * @param id the id
     * @return player match the id
     */
    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getID() == id) return player;
        }
        return null;
    }

    /**
     * Get all Players
     *
     * @return all Players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for battleStatusString, used in GUI.
     *
     * @return the status of the battle
     */
    public String getBattleStatusString() {
        return battleStatusString;
    }

    /**
     * Model handle a new game process and update the view
     */
    public void newGameProcess() {
        currentStage = Stage.FORTIFYEND;

        int playerNum = 0;
        for (RiskViewInterface view : viewList) {
            playerNum = view.getNumberPlayerDialog();
        }
        setPlayerNum(playerNum);


        String[] playerNameList = new String[0];
        Boolean[] AITypeList = new Boolean[0];
        for (RiskViewInterface view : viewList) {
            LinkedHashMap<String, Boolean> namesAndType = view.popGetName();
            playerNameList = namesAndType.keySet().toArray(new String[0]);
            AITypeList = namesAndType.values().toArray(new Boolean[0]);
        }
        addPlayersName(playerNameList, AITypeList);
        initialGame();
        for (RiskViewInterface view : viewList) {
            for (Territory territory : getAllCountries()) {
                view.setTerritoryButtonTroops(territory.getName(), territory.getTroops());
            }
            view.updateNewGameProcess(getMapInfoThroughContinent(), getCurrentPlayer().getName());
            paintTerritoryButtons(view);
        }

    }

    /**
     * Model handle the draft preparation and update the view
     */
    public void draftPrepare() {
        currentPlayer.draftPrepare();
    }

    /**
     * Gets continent bonus string.
     *
     * @return the list of the Continent name if the player own continents
     */
    public String getContinentBonusString() {
        String continentBonus = "";
        if (currentPlayer.haveContinent()) {
            continentBonus = " With ";
            for (Continent continent : currentPlayer.getContinents()) {
                continentBonus += "--" + continent.getName();
            }
        }
        return continentBonus;
    }

    /**
     * Model handle the attack preparation and update the view
     */
    public void attackPrepare() {
        currentPlayer.attackPrepare();
    }

    /**
     * Model handle the fortify preparation and update the view
     */
    public void fortifyPrepare() {
        currentPlayer.fortifyPrepare();
    }

    /**
     * Model handle the deploy preparation and update the view
     */
    public void deployPrepare() {
        currentPlayer.deployPrepare();
    }

    /**
     * This method is modeling to do the skip process and update the view
     */
    public void skipProcess() {
        if (currentStage == Stage.ATTACK || currentStage == Stage.DRAFTEND) {
            resetButtonsAndBoxProcedure();
            for (RiskViewInterface view : viewList) {
                view.updateSkipAttack(currentPlayer);
            }
            currentStage = Stage.ATTACKEND;
        }
        if (currentStage == Stage.FORTIFY) {
            resetButtonsAndBoxProcedure();
            currentPlayer = getNextPlayer(currentPlayer.getID());
            for (RiskViewInterface view : viewList) {
                view.updateSkipFortify(currentPlayer);
            }
            currentStage = Stage.FORTIFYEND;
        }
    }

    /**
     * Model handle confirm process and update the view
     */
    public void confirmProcess() {
        if (currentStage == Stage.DRAFT) {
            draftProcess();
            if (currentPlayer.getTroops() == 0) {
                currentStage = Stage.DRAFTEND;
            }
        }

        if (currentStage == Stage.ATTACK) {
            attackProcess();
        }

        if (currentStage == Stage.FORTIFY) {
            fortifyProcess();
        }

        if (currentStage == Stage.DEPLOY) {
            deployTroopsProcess();
            currentStage = Stage.DRAFTEND;
        }
    }

    /**
     * This method is updating the view when user click the button on the map(reset)
     */
    public void resetButtonsAndBoxProcedure() {
        if (!originTerritoryName.equals("")) {
            originTerritoryButtonPressed = true;
            originTerritoryName = "";
        }
        if (!targetTerritoryName.equals("")) {
            targetTerritoryButtonPressed = true;
            targetTerritoryName = "";
        }
        for (RiskViewInterface view : viewList) {
            paintTerritoryButtons(view);
            view.resetButtonsAndBox(getAttackTerritoriesList(currentPlayer));
        }
        attackTerritory = null;
        defenceTerritory = null;

    }

    /**
     * Model do the draft process and update the view
     */
    private void draftProcess() {
        if (originTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select One territory!", "No Territory Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (RiskViewInterface view : viewList) {
            draft(currentPlayer, originTerritoryName, view.getSelectedTroops());
            view.setContinentsLabel(getMapInfoThroughContinent());
            view.updateDraftProcess(currentPlayer, currentStage);
            view.setTerritoryButtonTroops(originTerritoryName, getTerritoryByString(originTerritoryName).getTroops());
            paintTerritoryButtons(view);
            if (currentPlayer.getTroops() == 0) {
                view.updateDraftFinish(currentPlayer);
                return;
            }
            view.enableOriginalTerritories(currentPlayer.getTerritories());
        }
        originTerritoryName = "";
        originTerritoryButtonPressed = true;
    }

    /**
     * Model do the attack process and update the view
     */
    private void attackProcess() {
        currentPlayer.attackProcess();
    }

    /**
     * Model do the fortify process and update the view
     */
    public void fortifyProcess() {
        if (originTerritoryName.isEmpty() || targetTerritoryName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please ensure you have selected both territories!", "Incomplete Selection on Territories", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int moveTroops = 0;
        for (RiskViewInterface view : viewList) {
            moveTroops = view.getSelectedTroops();
            fortify(getTerritoryByString(originTerritoryName), getTerritoryByString(targetTerritoryName), moveTroops);
        }
        currentPlayer.fortifyProcessResult(getTerritoryByString(originTerritoryName), getTerritoryByString(targetTerritoryName), moveTroops);
        currentStage = Stage.FORTIFYEND;
    }

    /**
     * Model do the deploy process and update the view
     */
    public void deployTroopsProcess() {
        int moveTroops = 0;
        for (RiskViewInterface view : viewList) {
            moveTroops = view.getSelectedTroops();
            deployTroops(attackTerritory, defenceTerritory, moveTroops);
        }
        currentPlayer.deployProcess(attackTerritory.getName(), defenceTerritory.getName(), moveTroops);
    }

    /**
     * This method update the view when user click the button on the map
     *
     * @param territoryName the territory name
     */
    public void territoryButtonClickProcess(String territoryName) {
        if (currentStage == Stage.DRAFT) {
            draftStageTerritoryButtonAction(territoryName);
        }
        if (currentStage == Stage.ATTACK) {
            attackStageTerritoryButtonAction(territoryName);
        }
        if (currentStage == Stage.FORTIFY) {
            fortifyStageTerritoryButtonAction(territoryName);
        }
    }

    /**
     * Draft stage territory button action.
     *
     * @param territoryName the territory name
     */
    public void draftStageTerritoryButtonAction(String territoryName) {
        if (originTerritoryButtonPressed) {
            originTerritoryName = territoryName;
            for (RiskViewInterface view : viewList) {
                view.updateDraftCountryClick(territoryName);
            }
        } else {
            originTerritoryName = "";
            for (RiskViewInterface view : viewList) {
                view.enableOriginalTerritories(currentPlayer.getTerritories());
                paintTerritoryButtons(view);
            }
        }
        originTerritoryButtonPressed = !originTerritoryButtonPressed;
    }

    /**
     * Attack stage territory button action.
     *
     * @param territoryName the territory name
     */
    public void attackStageTerritoryButtonAction(String territoryName) {
        if (originTerritoryButtonPressed) {
            originTerritoryName = territoryName;
            for (RiskViewInterface view : viewList) {
                view.updateClickAttackTerritoryButton(getTerritoryByString(originTerritoryName).getTroops(), getDefenceTerritories(currentPlayer, getTerritoryByString(originTerritoryName)), originTerritoryName);
            }
            originTerritoryButtonPressed = !originTerritoryButtonPressed;
        } else if (territoryName.equals(originTerritoryName)) {
            originTerritoryName = "";
            if (!targetTerritoryName.equals("")) {
                targetTerritoryButtonPressed = true;
            }
            for (RiskViewInterface view : viewList) {
                view.resetButtonsAndBox(getAttackTerritoriesList(currentPlayer));
                paintTerritoryButtons(view);
            }
            originTerritoryButtonPressed = !originTerritoryButtonPressed;
        } else if (targetTerritoryButtonPressed) {
            targetTerritoryName = territoryName;
            for (RiskViewInterface view : viewList) {
                view.updateClickTargetTerritoryButton(originTerritoryName, targetTerritoryName);
            }
            targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
        } else if (!targetTerritoryButtonPressed) {
            targetTerritoryName = "";
            for (RiskViewInterface view : viewList) {
                paintTerritoryButtons(view);
                view.updateCancelDefenceTerritoryButton(originTerritoryName, getDefenceTerritories(currentPlayer, getTerritoryByString(originTerritoryName)));
            }
            targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
        }
    }

    /**
     * Fortify stage territory button action.
     *
     * @param territoryName the territory name
     */
    public void fortifyStageTerritoryButtonAction(String territoryName) {
        if (originTerritoryButtonPressed) {
            originTerritoryName = territoryName;
            for (RiskViewInterface view : viewList) {
                view.updateClickFortifyButton(getTerritoryByString(originTerritoryName).getTroops() - 1, getFortifiedTerritory(getTerritoryByString(originTerritoryName), currentPlayer), originTerritoryName);
            }
            originTerritoryButtonPressed = !originTerritoryButtonPressed;
        } else if (territoryName.equals(originTerritoryName)) {
            originTerritoryName = "";
            if (!targetTerritoryName.equals("")) {
                targetTerritoryButtonPressed = true;
            }
            for (RiskViewInterface view : viewList) {
                paintTerritoryButtons(view);
                view.resetButtonsAndBox(getFortifyTerritories(currentPlayer));
            }
            originTerritoryButtonPressed = !originTerritoryButtonPressed;
        } else if (targetTerritoryButtonPressed) {
            targetTerritoryName = territoryName;
            for (RiskViewInterface view : viewList) {
                view.updateClickTargetTerritoryButton(originTerritoryName, targetTerritoryName);
            }
            targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
        } else if (!targetTerritoryButtonPressed) {
            targetTerritoryName = "";
            for (RiskViewInterface view : viewList) {
                paintTerritoryButtons(view);
                view.updateCancelFortifyTerritoryButton(originTerritoryName, getFortifiedTerritory(getTerritoryByString(originTerritoryName), currentPlayer));
            }
            targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
        }
    }

    /**
     * Paint territory buttons.
     *
     * @param view Update the map view using after each action
     */
    public void paintTerritoryButtons(RiskViewInterface view) {
        for (int id = 0; id < numberPlayers; id++) {
            if (!playerIDHashMap.containsKey(id)) continue;
            view.paintTerritoryButtons(getPlayerById(id), colorIDHashMap.get(id));
        }
    }


    /**
     * Gets max troops attack territory list.
     *
     * @return the territory list that have the max num of troops
     */
    public ArrayList<Territory> getMaxTroopsAttackTerritoryList() {
        ArrayList<Territory> maxTroopsTerritories = new ArrayList<>();
        ArrayList<Territory> attackTerritories = getAttackTerritoriesList(currentPlayer);
        int maxTroops = getMaxTroopsInTerritoryList(attackTerritories);
        for (Territory territory : attackTerritories) {
            if (territory.getTroops() == maxTroops) {
                maxTroopsTerritories.add(territory);
            }
        }
        return maxTroopsTerritories;
    }

    /**
     * Gets max troops in territory list.
     *
     * @param territories the territories
     * @return the max num of troops in one territory list
     */
    public int getMaxTroopsInTerritoryList(ArrayList<Territory> territories) {
        int maxTroops = territories.get(0).getTroops();
        for (Territory territory : territories) {
            if (territory.getTroops() > maxTroops) {
                maxTroops = territory.getTroops();
            }
        }
        return maxTroops;
    }

    /**
     * Gets min troops defence territory.
     *
     * @param attackTerritory the attack territory
     * @return the territory list that has the minimum num of troops
     */
    public ArrayList<Territory> getMinTroopsDefenceTerritory(Territory attackTerritory) {
        ArrayList<Territory> minTroopsTerritories = new ArrayList<>();
        ArrayList<Territory> defenceTerritories = getDefenceTerritories(currentPlayer, attackTerritory);
        int minTroops = getMinTroopsInTerritoryList(defenceTerritories);
        for (Territory territory : defenceTerritories) {
            if (territory.getTroops() == minTroops) {
                minTroopsTerritories.add(territory);
            }
        }
        return minTroopsTerritories;
    }

    /**
     * Gets min troops in territory list.
     *
     * @param territories the territories
     * @return get the minimum num of troops in one territory list
     */
    public int getMinTroopsInTerritoryList(ArrayList<Territory> territories) {
        int minTroops = territories.get(0).getTroops();
        for (Territory territory : territories) {
            if (territory.getTroops() < minTroops) {
                minTroops = territory.getTroops();
            }
        }
        return minTroops;
    }

    /**
     * Gets number players.
     *
     * @return the number players
     */
    public int getNumberPlayers() {
        return this.numberPlayers;
    }

    /**
     * Gets current stage.
     *
     * @return the current stage
     */
    public Stage getCurrentStage() {
        return currentStage;
    }

    /**
     * Import player.
     *
     * @param name         the name
     * @param troops       the troops
     * @param ID           the id
     * @param ownTerritory the own territory
     */
    public void ImportPlayer(String name, int troops, int ID, String ownTerritory) {
        Player player = new Player(name, this);
        player.setID(ID);
        player.setTroops(troops);


        String[] territory = ownTerritory.split(",");
        for (String str : territory) {
            Territory t = new Territory(str);
            t.setHolder(player);
            player.addTerritory(t);

        }

        for (Player p : this.players) {
            if (!(p.getName().equals(name))) {
                this.players.add(player);
            }
        }


    }

    /**
     * Import territory.
     *
     * @param name       the name
     * @param troops     the troops
     * @param holderName the holder name
     */
    public void ImportTerritory(String name, int troops, String holderName) {
        Territory territory = new Territory(name);
        territory.setTroops(troops);
        for (Player p : this.players) {
            if (p.getName().equals(holderName)) {
                territory.setHolder(p);
            }
        }
        this.allCountries.add(territory);
    }

    /**
     * Sets current stage.
     *
     * @param stage the stage
     */
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    /**
     * Gets attack territory.
     *
     * @return the attack territory
     */
    public Territory getAttackTerritory() {
        return attackTerritory;
    }

    /**
     * Gets defence territory.
     *
     * @return the defence territory
     */
    public Territory getDefenceTerritory() {
        return defenceTerritory;
    }

    /**
     * Gets origin territory name.
     *
     * @return the origin territory name
     */
    public String getOriginTerritoryName() {
        return originTerritoryName;
    }

    /**
     * Gets target territory name.
     *
     * @return the target territory name
     */
    public String getTargetTerritoryName() {
        return targetTerritoryName;
    }

    /**
     * Gets view list.
     *
     * @return the view list
     */
    public List<RiskViewInterface> getViewList() {
        return viewList;
    }

    /**
     * Sets origin territory button pressed.
     *
     * @param originTerritoryButtonPressed the origin territory button pressed
     */
    public void setOriginTerritoryButtonPressed(boolean originTerritoryButtonPressed) {
        this.originTerritoryButtonPressed = originTerritoryButtonPressed;
    }

    /**
     * Sets target territory button pressed.
     *
     * @param targetTerritoryButtonPressed the target territory button pressed
     */
    public void setTargetTerritoryButtonPressed(boolean targetTerritoryButtonPressed) {
        this.targetTerritoryButtonPressed = targetTerritoryButtonPressed;
    }

    /**
     * Sets origin territory name.
     *
     * @param originTerritoryName the origin territory name
     */
    public void setOriginTerritoryName(String originTerritoryName) {
        this.originTerritoryName = originTerritoryName;
    }

    /**
     * Sets target territory name.
     *
     * @param targetTerritoryName the target territory name
     */
    public void setTargetTerritoryName(String targetTerritoryName) {
        this.targetTerritoryName = targetTerritoryName;
    }

    /**
     * Sets attack territory.
     *
     * @param attackTerritory the attack territory
     */
    public void setAttackTerritory(Territory attackTerritory) {
        this.attackTerritory = attackTerritory;
    }

    /**
     * Sets defence territory.
     *
     * @param defenceTerritory the defence territory
     */
    public void setDefenceTerritory(Territory defenceTerritory) {
        this.defenceTerritory = defenceTerritory;
    }

    /**
     * Sets current player.
     *
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get file name through view string.
     *
     * @return the string
     */
    public String getFileNameThroughView(){
        for(RiskViewInterface view:viewList){
            return view.getFileName();
        }
        return "";
    }

    /**
     * Get new map name string.
     *
     * @return the string
     */
    public String getNewMapName(){
        return new LoadNewMapDialog().getMapName();
    }

    /**
     * Gets player by name.
     *
     * @param name the name
     * @return the player by name
     */
    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }

    /**
     * Save process.
     */
    public void saveProcess() {
        if (currentStage == null) {
            JOptionPane.showMessageDialog(null, "You have to start game first!", "No need to save", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (RiskViewInterface view : viewList) {
            String fileName = view.getFileName();
            if (fileName == null || fileName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the file name you want to save! Try to save it again.", "Empty file name", JOptionPane.ERROR_MESSAGE);
                return;
            }
            handleSaveByFileName(fileName,mapName);
        }
    }

    /**
     * Handle save by file name.
     *
     * @param fileName the file name
     * @param mapName  the map name
     */
    public void handleSaveByFileName(String fileName,String mapName){
        try{
            XMLHandler handler = new XMLHandler(mapName);
            handler.setModel(this);
            handler.toXMLFile(fileName + "_" + this.mapName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload process for view.
     */
    public void reload() {
        this.allCountries.clear();
        setPlayerNum(players.size());

        for (Player player : players) {
            for (Territory territory : player.getTerritories()) {
                allCountries.add(territory);
            }
        }
        for (RiskViewInterface view : viewList) {
            for (Territory territory : allCountries) {
                view.setTerritoryButtonTroops(territory.getName(), territory.getTroops());
            }
            paintTerritoryButtons(view);
            view.setStatusLabel(statusLabel);
            view.setContinentsLabel(getMapInfoThroughContinent());
            view.disableAllTerritoryButton();
        }
        reloadStageProcess();
    }

    /**
     * Reload stage process depends on different stage.
     */
    public void reloadStageProcess() {
        if (currentStage == Stage.DRAFT) {
            reloadDraftProcess();
        }
        if (currentStage == Stage.DRAFTEND) {
            reloadDraftEndProcess();
        }
        if (currentStage == Stage.ATTACK) {
            reloadAttackProcess();
        }
        if (currentStage == Stage.ATTACKTODEPLOY) {
            reloadAttackToDeployProcess();
        }
        if (currentStage == Stage.DEPLOY) {
            reloadDeploy();
        }
        if (currentStage == Stage.ATTACKEND) {
            reloadAttackEnd();
        }
        if (currentStage == Stage.FORTIFY) {
            reloadFortify();
        }
        if (currentStage == Stage.FORTIFYEND) {
            reloadFortifyEnd();
        }
    }

    /**
     * Reload Draft View
     */
    private void reloadDraftProcess() {
        for (RiskViewInterface view : viewList) {
            view.updateDraftPrepare(currentPlayer, currentStage, getContinentBonusString());
            if (!originTerritoryButtonPressed) {
                view.updateDraftCountryClick(originTerritoryName);
            }
        }
    }
    /**
     * Reload Draft finished View
     */
    private void reloadDraftEndProcess() {
        for (RiskViewInterface view : viewList) {
            view.disableAllCommandButtons();
            view.updateDraftFinish(currentPlayer);
        }
    }
    /**
     * Reload attack View
     */
    private void reloadAttackProcess() {
        for (RiskViewInterface view : viewList) {
            if (originTerritoryButtonPressed && targetTerritoryButtonPressed) {
                view.resetButtonsAndBox(getAttackTerritoriesList(currentPlayer));
            } else if ((!originTerritoryButtonPressed) && targetTerritoryButtonPressed) {
                view.updateClickAttackTerritoryButton(getTerritoryByString(originTerritoryName).getTroops(), getDefenceTerritories(currentPlayer, getTerritoryByString(originTerritoryName)), originTerritoryName);
            } else if ((!originTerritoryButtonPressed) && (!targetTerritoryButtonPressed)) {
                view.setAttackTroopsBox(getTerritoryByString(originTerritoryName).getTroops());
                view.updateClickTargetTerritoryButton(originTerritoryName, targetTerritoryName);
            }
            view.paintOriginAndTargetTerritory(originTerritoryButtonPressed, targetTerritoryButtonPressed, originTerritoryName, targetTerritoryName);
            view.disableAllCommandButtons();
            view.enableButton("Confirm");
            view.enableButton("Skip");
        }
    }
    /**
     * Reload attack to deploy View (before clicking deploy button)
     */
    private void reloadAttackToDeployProcess() {
        setAttackTerritory(getTerritoryByString(originTerritoryName));
        setDefenceTerritory(getTerritoryByString(targetTerritoryName));
        for (RiskViewInterface view : viewList) {
            view.disableAllCommandButtons();
            view.updateWinAttack(currentPlayer);
            view.paintOriginAndTargetTerritory(originTerritoryButtonPressed, targetTerritoryButtonPressed, originTerritoryName, targetTerritoryName);
        }
    }
    /**
     * Reload deploy View
     */
    private void reloadDeploy() {
        attackTerritory = getTerritoryByString(originTerritoryName);
        defenceTerritory = getTerritoryByString(targetTerritoryName);
        for (RiskViewInterface view : viewList) {
            view.disableAllCommandButtons();
            view.paintOriginAndTargetTerritory(originTerritoryButtonPressed, targetTerritoryButtonPressed, originTerritoryName, targetTerritoryName);
            view.updateDeployPrepare(currentPlayer, currentStage, getTerritoryByString(originTerritoryName).getTroops());
        }
    }
    /**
     * Reload attack finished View
     */
    private void reloadAttackEnd() {
        resetButtonsAndBoxProcedure();
        for (RiskViewInterface view : viewList) {
            view.disableAllCommandButtons();
            view.updateSkipAttack(currentPlayer);
        }
    }
    /**
     * Reload fortify View
     */
    private void reloadFortify() {
        for (RiskViewInterface view : viewList) {
            if (originTerritoryButtonPressed && targetTerritoryButtonPressed) {
                view.resetButtonsAndBox(getFortifyTerritories(currentPlayer));
            } else if (!originTerritoryButtonPressed) {
                view.updateClickFortifyButton(getTerritoryByString(originTerritoryName).getTroops() - 1, getFortifiedTerritory(getTerritoryByString(originTerritoryName), currentPlayer), originTerritoryName);
                if (!targetTerritoryButtonPressed) {
                    view.updateClickTargetTerritoryButton(originTerritoryName, targetTerritoryName);
                }
            }
            view.paintOriginAndTargetTerritory(originTerritoryButtonPressed, targetTerritoryButtonPressed, originTerritoryName, targetTerritoryName);
            view.disableAllCommandButtons();
            view.enableButton("Confirm");
            view.enableButton("Skip");
        }
    }

    /**
     * Reload Fortify finished View
     */
    private void reloadFortifyEnd() {
        resetButtonsAndBoxProcedure();
        for (RiskViewInterface view : viewList) {
            view.updateFortifyFinish(currentPlayer);
        }
    }

    /**
     * Is origin territory button pressed boolean.
     *
     * @return the boolean
     */
    public boolean isOriginTerritoryButtonPressed() {
        return originTerritoryButtonPressed;
    }

    /**
     * Is target territory button pressed boolean.
     *
     * @return the boolean
     */
    public boolean isTargetTerritoryButtonPressed() {
        return targetTerritoryButtonPressed;
    }

    /**
     * Gets status string.
     *
     * @return the status string
     */
    public String getStatusString() {
        for (RiskViewInterface view : viewList) {
            return view.getStatusLabel();
        }
        return null;
    }

    /**
     * Sets status string.
     *
     * @param statusLabel the status label
     */
    public void setStatusString(String statusLabel) {
        this.statusLabel = statusLabel;
    }


}
