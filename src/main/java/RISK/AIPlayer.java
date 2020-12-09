package RISK;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Ai player.
 */
public class AIPlayer extends Player{
    /**
     * Instantiates a new Player.
     *
     * @param name  the player's name
     * @param model the model
     */
    public AIPlayer(String name, RiskModel model) {
        super(name, model);
    }

    /**
     * Draft process for AIPlayer
     */
    @Override
    public void draftPrepare(){
        model.checkContinent(this);
        gainTroopsFromTerritory();
        ArrayList<Territory> maxTroopsAttackTerritoryList;
        if(model.getAttackTerritoriesList(this).size()==0){
            maxTroopsAttackTerritoryList=getTerritories();//in case player will have one troops in each territory
        }
        else {
            maxTroopsAttackTerritoryList = model.getMaxTroopsAttackTerritoryList();
        }
        Territory draftTerritory = maxTroopsAttackTerritoryList.get(new Random().nextInt(maxTroopsAttackTerritoryList.size()));
        int draftTroops = getTroops();
        for(RiskViewInterface view:model.getViewList()){
            view.updateAIDraft(this,model.getContinentBonusString(),draftTerritory);
            JOptionPane.showMessageDialog(null,this.name +" sent "+ draftTroops+ " troops to "+draftTerritory.getName());
            model.draft(this,draftTerritory.getName(),draftTroops);
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(draftTerritory.getName(),draftTerritory.getTroops());
            model.paintTerritoryButtons(view);
        }
        model.setCurrentStage(Stage.DRAFTEND);
    }

    /**
     * Attack process for AIPlayer
     */
    @Override
    public void attackPrepare(){
        if(model.getAttackTerritoriesList(this).size()==0){
            JOptionPane.showMessageDialog(null,this.name+" has no available territory to attack, skip Attack stage.");
            return;
        }
        ArrayList<Territory> maxTroopsAttackTerritoryList = model.getMaxTroopsAttackTerritoryList();
        Territory tempAttackTerritory = maxTroopsAttackTerritoryList.get(new Random().nextInt(maxTroopsAttackTerritoryList.size()));
        model.setAttackTerritory(tempAttackTerritory);
        model.setOriginTerritoryButtonPressed(false);
        model.setOriginTerritoryName(tempAttackTerritory.getName());
        ArrayList<Territory> minTroopsDefenceTerritoryList = model.getMinTroopsDefenceTerritory(tempAttackTerritory);
        Territory tempDefenceTerritory = minTroopsDefenceTerritoryList.get(new Random().nextInt(minTroopsDefenceTerritoryList.size()));
        model.setDefenceTerritory(tempDefenceTerritory);
        model.setTargetTerritoryButtonPressed(false);
        model.setTargetTerritoryName(tempDefenceTerritory.getName());
        boolean gainTerritory = model.battle(tempAttackTerritory,tempDefenceTerritory,AttackWay.BLITZ);

        for(RiskViewInterface view: model.getViewList()) {
            view.updateAIAttack(this,tempAttackTerritory,tempDefenceTerritory);
            view.setTerritoryButtonTroops(tempAttackTerritory.getName(), tempAttackTerritory.getTroops());
            view.setTerritoryButtonTroops(tempDefenceTerritory.getName(), tempDefenceTerritory.getTroops());
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            if(gainTerritory) {
                JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"You conquered "+tempDefenceTerritory.getName()+"!");
                view.setStatusLabel(tempAttackTerritory.getName()+" wins the war and conquered " +tempDefenceTerritory.getName() +" !");
                view.updateAIWinAttack();
                model.setCurrentStage(Stage.ATTACKTODEPLOY);
                return;
            }
            view.enableButton("Fortify");
        }
        model.resetButtonsAndBoxProcedure();
        JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"You didn't conquered "+tempDefenceTerritory.getName()+".");
        model.checkContinent(this);
        model.setCurrentStage(Stage.ATTACKEND);
    }

    /**
     * Deploy process for AIPlayer
     */
    @Override
    public void deployPrepare(){
        Territory attackTerritory = model.getAttackTerritory();
        Territory defenceTerritory = model.getDefenceTerritory();
        int troops = new Random().nextInt(attackTerritory.getTroops()-1)+1;
        model.deployTroops(attackTerritory, defenceTerritory, troops);
        for(RiskViewInterface view:model.getViewList()){
            String statusLabel = attackTerritory.getName() + " deploy "+troops+" troops to "+defenceTerritory.getName();
            view.updateAIDeploy(statusLabel);
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(attackTerritory.getName(), getTerritoryByString(attackTerritory.getName()).getTroops());
            view.setTerritoryButtonTroops(defenceTerritory.getName(), getTerritoryByString(defenceTerritory.getName()).getTroops());
            JOptionPane.showMessageDialog(null,this.name+" deployed "+troops+" troops from "+attackTerritory.getName()+" to "+defenceTerritory.getName());
            model.resetButtonsAndBoxProcedure();
            view.disableAllTerritoryButton();
        }
        model.setCurrentStage(Stage.DRAFTEND);
    }

    @Override
    public void fortifyPrepare(){
        ArrayList<Territory> tempFortifyTerritories = model.getFortifyTerritories(this);
        if(tempFortifyTerritories.size()==0){
            model.setCurrentPlayer(model.getNextPlayer(this.getID()));
            for(RiskViewInterface view: model.getViewList()) {
                view.updateFortifyFinish(this);
            }
            return;
        }
        Territory tempFortifyTerritory = tempFortifyTerritories.get(new Random().nextInt(tempFortifyTerritories.size()));
        ArrayList<Territory> tempFortifiedTerritories = model.getFortifiedTerritory(tempFortifyTerritory,this);
        Territory tempFortifiedTerritory = tempFortifiedTerritories.get(new Random().nextInt(tempFortifiedTerritories.size()));
        for(RiskViewInterface view: model.getViewList()){
            view.updateAIFortify(this,tempFortifyTerritory.getName(),tempFortifiedTerritory.getName());
        }
        int troops = new Random().nextInt(tempFortifyTerritory.getTroops()-1)+1;
        model.fortify(tempFortifyTerritory,tempFortifiedTerritory,troops);
        fortifyProcessResult(tempFortifyTerritory,tempFortifiedTerritory,troops);
        model.setCurrentStage(Stage.FORTIFYEND);
    }


}
