import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RiskController {
    private RiskModel model;
    private RiskView view;
    private Territory attackTerritory;
    private Territory defenceTerritory;

    private Player currentPlayer;
    private Stage currentStage;

    private String originTerritoryName = "";
    private String targetTerritoryName = "";

    private boolean originTerritoryButtonPressed = true;
    private boolean targetTerritoryButtonPressed = true;

    public RiskController() throws IOException{
        this.model = new RiskModel();
        this.view = new RiskView();

        view.addNewGameMenuListener(new NewGameMenuListener() );
        view.addDraftButtonListener(new DraftButtonListener());
        view.addAttackButtonListener(new AttackButtonListener());
        view.addDeployButtonListener(new DeployButtonListener());
        view.addFortifyButtonListener(new FortifyButtonListener());
        view.addSkipButtonListener(new SkipButtonListener());
        view.addConfirmButtonListener(new ConfirmButtonListener());
        view.addTerritoryButtonListener(new TerritoryButtonListener());
    }

     class NewGameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


                model.setPlayerNum(view.getNumberPlayerDialog());
                model.addPlayersName(view.popGetName());
                model.initialGame();
                currentStage = Stage.DRAFT;
                for(Territory territory:model.getAllCountries()){
                    view.setTerritoryButtonTroops(territory.getName(),territory.getTroops());
                }
                view.setContinentsLabel(model.getMapInfoThroughContinent());
                view.getJButton("Draft").setEnabled(true);
                view.setStatusLabel("Now it's "+model.getCurrentPlayer().getName() +"'s turn, please click \"Draft\" button to start DRAFT stage.");
                view.getJButton("Confirm").setEnabled(true);
                view.disableAllTerritoryButton();
            }

        }

    class DraftButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJButton("Draft").setEnabled(false);
                currentPlayer = model.getCurrentPlayer();
                //System.out.println(currentPlayer.getName()+currentPlayer.getTerritories()); test
                model.getCurrentPlayer().gainTroopsFromTerritory();
                view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
                view.setTroopsBox(currentPlayer.getTroops());
                view.enableOriginalTerritories(currentPlayer.getTerritories());
                //view.getJButton("Skip").setEnabled(true);
                //currentStage = Stage.ATTACK;
            }
        }

    public class AttackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.ATTACK;
            originTerritoryButtonPressed = true;
            view.getJButton("Attack").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.getJButton("Skip").setEnabled(true);
            view.enableOriginalTerritories(model.getAttackTerritoriesList(currentPlayer));
//            view.setDestinationTerritory(model.setDefenceTerritories(currentPlayer,view.getStartingTerritory()));
//            view.setAttackTroopsBox(view.getStartingTerritory().getTroops());
            currentStage=Stage.ATTACK;
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Select territory from Origin Territory list and pick target territory in Target Territory list");
        }
    }


    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.FORTIFY;
            view.getJButton("Fortify").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.enableOriginalTerritories(model.getFortifyTerritories(currentPlayer));
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Please choose the Territory you want send troops from.");
//            view.setDestinationTerritory(model.setFortifiableTerritory(view.getStartingTerritory(),currentPlayer));
//            view.setTroopsBox(view.getStartingTerritory().getTroops()-1);


        }
    }
    public class DeployButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.DEPLOY;
            view.getJButton("Deploy").setEnabled(false);
            view.getJButton("Attack").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Set troops to the new earned territory.");
            view.setTroopsBox(attackTerritory.getTroops()-1);
        }
    }

    public class SkipButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStage == Stage.ATTACK){
                view.getJButton("Attack").setEnabled(false);
                view.getJButton("Deploy").setEnabled(false);
                view.getJButton("Draft").setEnabled(false);
                view.getJButton("Fortify").setEnabled(true);
                resetButtonsAndBoxProcedure();
            }


            if(currentStage == Stage.FORTIFY){
                view.getJButton("Fortify").setEnabled(false);
                view.getJButton("Attack").setEnabled(false);
                view.getJButton("Deploy").setEnabled(false);
                view.getJButton("Skip").setEnabled(false);
                view.getJButton("Draft").setEnabled(true);

            }







        }
    }


    public class ConfirmButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentStage==Stage.DRAFT){
                draftProcess();
            }

            if(currentStage==Stage.ATTACK){
                attackProcess();
            }

            if(currentStage==Stage.FORTIFY){
                fortifyProcess();
                //only process once, then transfer to another player.
                currentStage = null;
            }

            if(currentStage==Stage.DEPLOY){
                deployTroopsProcess(attackTerritory,defenceTerritory);
            }

        }
    }

    public class TerritoryButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            if(currentStage==Stage.DRAFT){
                if(originTerritoryButtonPressed){
                    originTerritoryName = e.getActionCommand();
                    view.onlyEnableOriginTerritory(e.getActionCommand());
                    ((JButton)e.getSource()).setBackground(Color.RED);
                }else{
                    originTerritoryName = "";
                    view.enableOriginalTerritories(currentPlayer.getTerritories());
                    ((JButton)e.getSource()).setBackground(null);
                }
                originTerritoryButtonPressed = !originTerritoryButtonPressed;
            }
            if(currentStage==Stage.ATTACK){
                if (originTerritoryButtonPressed) {
                    originTerritoryName = e.getActionCommand();
                    ((JButton)e.getSource()).setBackground(Color.RED);
                    view.setAttackTroopsBox(model.getTerritoryByString(originTerritoryName).getTroops());
                    view.enableTargetTerritories(model.getDefenceTerritories(currentPlayer,model.getTerritoryByString(originTerritoryName)),originTerritoryName);
                    originTerritoryButtonPressed =! originTerritoryButtonPressed;
                }else if(e.getActionCommand().equals(originTerritoryName)){
                    originTerritoryName = "";
                    if(targetTerritoryName!=""){
                        view.getTerritoryButtonByString(targetTerritoryName).setBackground(null);
                        targetTerritoryButtonPressed = true;
                    }
                    view.clearTroopsBox();
                    ((JButton)e.getSource()).setBackground(null);
                    view.disableAllTerritoryButton();
                    view.enableOriginalTerritories(model.getAttackTerritoriesList(currentPlayer));
                    originTerritoryButtonPressed =! originTerritoryButtonPressed;
                }else if(targetTerritoryButtonPressed){
                    targetTerritoryName = e.getActionCommand();
                    ((JButton)e.getSource()).setBackground(Color.ORANGE);
                    view.disableAllTerritoryButton();
                    view.enableTerritoryButton(originTerritoryName);
                    view.enableTerritoryButton(targetTerritoryName);
                    targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
                }else if(!targetTerritoryButtonPressed){
                    targetTerritoryName = "";
                    ((JButton)e.getSource()).setBackground(null);
                    view.onlyEnableOriginTerritory(originTerritoryName);
                    view.enableTargetTerritories(model.getDefenceTerritories(currentPlayer,model.getTerritoryByString(originTerritoryName)),originTerritoryName);
                    targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
                }
            }
            if(currentStage==Stage.FORTIFY){
                if (originTerritoryButtonPressed) {
                    originTerritoryName = e.getActionCommand();
                    ((JButton)e.getSource()).setBackground(Color.RED);
                    view.setTroopsBox(model.getTerritoryByString(originTerritoryName).getTroops()-1);
                    view.enableTargetTerritories(model.getFortifiedTerritory(model.getTerritoryByString(originTerritoryName),currentPlayer),originTerritoryName);
                    originTerritoryButtonPressed =! originTerritoryButtonPressed;
                }else if(e.getActionCommand().equals(originTerritoryName)){
                    originTerritoryName = "";
                    if(targetTerritoryName!=""){
                        view.getTerritoryButtonByString(targetTerritoryName).setBackground(null);
                        targetTerritoryButtonPressed = true;
                    }
                    view.clearTroopsBox();
                    ((JButton)e.getSource()).setBackground(null);
                    view.disableAllTerritoryButton();
                    view.enableOriginalTerritories(model.getFortifyTerritories(currentPlayer));
                    originTerritoryButtonPressed =! originTerritoryButtonPressed;
                }else if(targetTerritoryButtonPressed){
                    targetTerritoryName = e.getActionCommand();
                    ((JButton)e.getSource()).setBackground(Color.ORANGE);
                    view.disableAllTerritoryButton();
                    view.enableTerritoryButton(originTerritoryName);
                    view.enableTerritoryButton(targetTerritoryName);
                    targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
                }else if(!targetTerritoryButtonPressed){
                    targetTerritoryName = "";
                    ((JButton)e.getSource()).setBackground(null);
                    view.onlyEnableOriginTerritory(originTerritoryName);
                    view.enableTargetTerritories(model.getFortifiedTerritory(model.getTerritoryByString(originTerritoryName),currentPlayer),originTerritoryName);
                    targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
                }
            }
        }
    }

    private void attackProcess() {
        if(originTerritoryName==""||targetTerritoryName=="") {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        attackTerritory = model.getTerritoryByString(originTerritoryName);
        defenceTerritory = model.getTerritoryByString(targetTerritoryName);
        AttackWay attackWay = view.getAttackTroopsBox();
        boolean winBattle = model.battle(attackTerritory,defenceTerritory,attackWay);
        view.setTerritoryButtonTroops(originTerritoryName,attackTerritory.getTroops());
        view.setTerritoryButtonTroops(targetTerritoryName,defenceTerritory.getTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        if(winBattle){
            JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"\nCongratulation, you win this battle!");
            view.getJButton("Deploy").setEnabled(true);
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Skip").setEnabled(false);
            view.disableAllTerritoryButton();
            view.setStatusLabel(currentPlayer.getName()+" wins the battle! Press \"Deploy\" button to deploy troops.");
            return;
        }
        JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"\nUnfortunately, you lose this battle...");
        resetButtonsAndBoxProcedure();
    }

    private void deployTroopsProcess(Territory attackTerritory, Territory defenceTerritory){
        model.deployTroops(attackTerritory,defenceTerritory,view.getSelectedTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        view.setTerritoryButtonTroops(originTerritoryName,model.getTerritoryByString(originTerritoryName).getTroops());
        view.setTerritoryButtonTroops(targetTerritoryName,model.getTerritoryByString(targetTerritoryName).getTroops());
        resetButtonsAndBoxProcedure();
        view.getJButton("Attack").setEnabled(true);
        view.getJButton("Confirm").setEnabled(false);
        view.getJButton("Skip").setEnabled(true);
        view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to continue ATTACK stage OR \"Skip\" button to skip to Fortify Stage.");
    }

    private void resetButtonsAndBoxProcedure(){
        if(originTerritoryName!="") {
            view.getTerritoryButtonByString(originTerritoryName).setBackground(null);
            originTerritoryButtonPressed = true;
            originTerritoryName = "";
        }
        if(targetTerritoryName!="") {
            view.getTerritoryButtonByString(targetTerritoryName).setBackground(null);
            targetTerritoryButtonPressed = true;
            targetTerritoryName = "";
        }
        view.clearTroopsBox();
        view.disableAllTerritoryButton();
        view.enableOriginalTerritories(model.getAttackTerritoriesList(currentPlayer));
    }

    private void draftProcess(){
        if(originTerritoryName=="") {
            JOptionPane.showMessageDialog(null, "Please select One territory!", "No Territory Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.draft(currentPlayer, originTerritoryName, view.getSelectedTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
        view.setTroopsBox(currentPlayer.getTroops());
        view.setTerritoryButtonTroops(originTerritoryName,model.getTerritoryByString(originTerritoryName).getTroops());
        view.getTerritoryButtonByString(originTerritoryName).setBackground(null);
        if(currentPlayer.getTroops()==0){
            view.disableAllTerritoryButton();
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Attack").setEnabled(true);
            view.getJButton("Skip").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to start ATTACK stage.");
            currentStage = null;
            return;
        }
        view.enableOriginalTerritories(currentPlayer.getTerritories());
        originTerritoryName="";
        originTerritoryButtonPressed = true;
    }

    private void fortifyProcess(){
        if(originTerritoryName==""||targetTerritoryName=="") {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Territory startCountry = model.getTerritoryByString(originTerritoryName);
        Territory destinationCountry = model.getTerritoryByString(targetTerritoryName);
        int troops = view.getSelectedTroops();
        model.fortify(startCountry,destinationCountry,troops);
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        view.setTerritoryButtonTroops(originTerritoryName,startCountry.getTroops());
        view.setTerritoryButtonTroops(targetTerritoryName,destinationCountry.getTroops());
        resetButtonsAndBoxProcedure();
        currentPlayer = model.getNextPlayer();
        model.setCurrentPlayer(currentPlayer);
        view.getJButton("Draft").setEnabled(true);
        view.getJButton("Confirm").setEnabled(false);
        view.getJButton("Skip").setEnabled(false);
        view.getJButton("Attack").setEnabled(false);
        currentStage = Stage.DRAFT;
        view.setStatusLabel(currentPlayer.getName()+ "'s turn, " + currentStage.getName() + " stage, please click \"Draft\" button to start DRAFT stage.");
    }

    private DefaultListModel<Territory> getTempStartingTerritoryList(Territory startingTerritory){
        DefaultListModel<Territory> tempStartingTerritory = new DefaultListModel<>();
        tempStartingTerritory.addElement(startingTerritory);
        return tempStartingTerritory;
    }

    private DefaultListModel<Territory> getTempTargetTerritoryList(Territory targetTerritory){
        DefaultListModel<Territory> tempTargetTerritory = new DefaultListModel<>();
        tempTargetTerritory.addElement(targetTerritory);
        return tempTargetTerritory;
    }



    public static void main(String[] args)  throws IOException{
        new RiskController();
    }
}

