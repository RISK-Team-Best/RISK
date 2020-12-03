package RISK;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The interface Risk view interface.
 */
public interface RiskViewInterface {

    /**
     * Gets number player dialog.
     *
     * @return the number player dialog
     */
//New game process
    int getNumberPlayerDialog();

    /**
     * Pop get name linked hash map.
     *
     * @return the linked hash map
     */
    LinkedHashMap<String,Boolean> popGetName();

    /**
     * Sets territory button troops.
     *
     * @param territoryName the territory name
     * @param troops        the troops
     */
    void setTerritoryButtonTroops(String territoryName, int troops);

    /**
     * Update new game process.
     *
     * @param mapInfoThroughContinent the map info through continent
     * @param currentPlayerName       the current player name
     */
    void updateNewGameProcess(String mapInfoThroughContinent, String currentPlayerName);

    /**
     * Paint territory buttons.
     *
     * @param playerById the player by id
     * @param color      the color
     */
    void paintTerritoryButtons(Player playerById, Color color);

    /**
     * Update draft prepare.
     *
     * @param currentPlayer  the current player
     * @param currentStage   the current stage
     * @param continentBonus the continent bonus
     */
//prepare for draft.
    void updateDraftPrepare(Player currentPlayer, Stage currentStage, String continentBonus);

    /**
     * Update draft process.
     *
     * @param currentPlayer the current player
     * @param currentStage  the current stage
     */
//process draft on view
    void updateDraftProcess(Player currentPlayer, Stage currentStage);

    /**
     * Update draft finish.
     *
     * @param currentPlayer the current player
     */
//end of draft
    void updateDraftFinish(Player currentPlayer);

    /**
     * Update attack prepare.
     *
     * @param currentPlayer         the current player
     * @param currentStage          the current stage
     * @param attackTerritoriesList the attack territories list
     */
//prepare for attack
    void updateAttackPrepare(Player currentPlayer, Stage currentStage, ArrayList<Territory> attackTerritoriesList);

    /**
     * Update fortify prepare.
     *
     * @param fortifyTerritories the fortify territories
     * @param currentPlayer      the current player
     * @param currentStage       the current stage
     */
//prepare for fortify
    void updateFortifyPrepare(ArrayList<Territory> fortifyTerritories, Player currentPlayer, Stage currentStage);

    /**
     * Update fortify finish.
     *
     * @param currentPlayer the current player
     */
    void updateFortifyFinish(Player currentPlayer);

    /**
     * Update deploy prepare.
     *
     * @param currentPlayer the current player
     * @param currentStage  the current stage
     * @param attackTroops  the attack troops
     */
    void updateDeployPrepare(Player currentPlayer, Stage currentStage, int attackTroops);

    /**
     * Update skip attack.
     *
     * @param currentPlayer the current player
     */
    void updateSkipAttack(Player currentPlayer);

    /**
     * Update skip fortify.
     *
     * @param currentPlayer the current player
     */
    void updateSkipFortify(Player currentPlayer);


    /**
     * Reset buttons and box.
     *
     * @param attackTerritoriesList the attack territories list
     */
    void resetButtonsAndBox(ArrayList<Territory> attackTerritoriesList);

    /**
     * Gets attack troops box.
     *
     * @return the attack troops box
     */
    AttackWay getAttackTroopsBox();


    /**
     * Sets continents label.
     *
     * @param mapInfoThroughContinent the map info through continent
     */
    void setContinentsLabel(String mapInfoThroughContinent);

    /**
     * Update win attack.
     *
     * @param currentPlayer the current player
     */
    void updateWinAttack(Player currentPlayer);

    /**
     * Gets selected troops.
     *
     * @return the selected troops
     */
    int getSelectedTroops();


    /**
     * Enable original territories.
     *
     * @param territories the territories
     */
    void enableOriginalTerritories(ArrayList<Territory> territories);


    /**
     * Update deploy finish.
     *
     * @param currentPlayer the current player
     */
    void updateDeployFinish(Player currentPlayer);

    /**
     * Update click attack territory button.
     *
     * @param attackTerritoryTroops the attack territory troops
     * @param defenceTerritories    the defence territories
     * @param attackTerritoryName   the attack territory name
     */
    void updateClickAttackTerritoryButton(int attackTerritoryTroops, ArrayList<Territory> defenceTerritories, String attackTerritoryName);

    /**
     * Update click target territory button.
     *
     * @param originTerritoryName the origin territory name
     * @param targetTerritoryName the target territory name
     */
    void updateClickTargetTerritoryButton(String originTerritoryName, String targetTerritoryName);

    /**
     * Update cancel defence territory button.
     *
     * @param originTerritoryName the origin territory name
     * @param defenceTerritories  the defence territories
     */
    void updateCancelDefenceTerritoryButton(String originTerritoryName, ArrayList<Territory> defenceTerritories);

    /**
     * Update click fortify button.
     *
     * @param fortifyTroops       the fortify troops
     * @param fortifiedTerritory  the fortified territory
     * @param originTerritoryName the origin territory name
     */
    void updateClickFortifyButton(int fortifyTroops, ArrayList<Territory> fortifiedTerritory, String originTerritoryName);

    /**
     * Update cancel fortify territory button.
     *
     * @param originTerritoryName the origin territory name
     * @param fortifiedTerritory  the fortified territory
     */
    void updateCancelFortifyTerritoryButton(String originTerritoryName, ArrayList<Territory> fortifiedTerritory);

    /**
     * Update ai draft.
     *
     * @param currentPlayer        the current player
     * @param continentBonusString the continent bonus string
     * @param draftTerritory       the draft territory
     */
    void updateAIDraft(Player currentPlayer, String continentBonusString, Territory draftTerritory);

    /**
     * Sets status label.
     *
     * @param statusLabel the status label
     */
    void setStatusLabel(String statusLabel);

    /**
     * Update ai attack.
     *
     * @param currentPlayer        the current player
     * @param tempAttackTerritory  the temp attack territory
     * @param tempDefenceTerritory the temp defence territory
     */
    void updateAIAttack(Player currentPlayer, Territory tempAttackTerritory, Territory tempDefenceTerritory);

    /**
     * Disable all command buttons.
     */
    void disableAllCommandButtons();

    /**
     * Update ai fortify.
     *
     * @param currentPlayer          the current player
     * @param fortifyTerritoryName   the fortify territory name
     * @param fortifiedTerritoryName the fortified territory name
     */
    void updateAIFortify(Player currentPlayer, String fortifyTerritoryName, String fortifiedTerritoryName);

    String getFileName();

    void updateDraftCountryClick(String territoryName);

    void updateAIDeploy(String statuslabel);

    void updateAIWinAttack();

    void disableAllTerritoryButton();

    void enableButton(String buttonName);

    String getStatusLabel();


    void paintOriginAndTargetTerritory(boolean originTerritoryButtonPressed, boolean targetTerritoryButtonPressed, String originTerritoryName, String targetTerritoryName);

    void setAttackTroopsBox(int attackTerritoryTroops);
}
