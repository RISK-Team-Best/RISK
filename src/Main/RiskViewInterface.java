package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface RiskViewInterface {

    //New game process
    int getNumberPlayerDialog();
    LinkedHashMap<String,Boolean> popGetName();
    void setTerritoryButtonTroops(String territoryName, int troops);
    void updateNewGameProcess(String mapInfoThroughContinent, String currentPlayerName);

    void paintTerritoryButtons(Player playerById, Color color);

    //prepare for draft.
    void updateDraftPrepare(Player currentPlayer, Stage currentStage, String continentBonus);
    //process draft on view
    void updateDraftProcess(Player currentPlayer, Stage currentStage);
    //end of draft
    void updateDraftFinish(Player currentPlayer);

    //prepare for attack
    void updateAttackPrepare(Player currentPlayer, Stage currentStage, ArrayList<Territory> attackTerritoriesList);

    //prepare for fortify
    void updateFortifyPrepare(ArrayList<Territory> fortifyTerritories, Player currentPlayer, Stage currentStage);

    void updateFortifyFinish(Player currentPlayer);

    void updateDeployPrepare(Player currentPlayer, Stage currentStage, int attackTroops);

    //Skip for attack
    void updateSkipAttack(Player currentPlayer);
    //Skip for fortify
    void updateSkipFortify(Player currentPlayer);

    
    void resetButtonsAndBox(ArrayList<Territory> attackTerritoriesList);

    AttackWay getAttackTroopsBox();


 

    void setContinentsLabel(String mapInfoThroughContinent);

    void updateWinAttack(Player currentPlayer);

    int getSelectedTroops();


    void enableOriginalTerritories(ArrayList<Territory> territories);

    void updateDeployFinish(Player currentPlayer);

    void onlyEnableOriginTerritory(String territoryName);

    void updateClickAttackTerritoryButton(int attackTerritoryTroops, ArrayList<Territory> defenceTerritories, String attackTerritoryName);

    void updateClickTargetTerritoryButton(String originTerritoryName, String targetTerritoryName);

    void updateCancelDefenceTerritoryButton(String originTerritoryName, ArrayList<Territory> defenceTerritories);

    void updateClickFortifyButton(int fortifyTroops, ArrayList<Territory> fortifiedTerritory, String originTerritoryName);

    void updateCancelFortifyTerritoryButton(String originTerritoryName, ArrayList<Territory> fortifiedTerritory);

    void updateAIDraft(Player currentPlayer, String continentBonusString, Territory draftTerritory, int draftTroops);

    void setStatusLabel(String statusLabel);

    void updateAIAttack(Player currentPlayer, Territory tempAttackTerritory, Territory tempDefenceTerritory);

    void disableAllCommandButtons();
}
