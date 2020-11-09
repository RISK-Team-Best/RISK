import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

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

    private HashMap<Integer,Color> colorIDHashMap = new HashMap<>();

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

        colorIDHashMap.put(0,Color.MAGENTA);
        colorIDHashMap.put(1,Color.CYAN);
        colorIDHashMap.put(2,Color.GREEN);
        colorIDHashMap.put(3,Color.PINK);
        colorIDHashMap.put(4,Color.WHITE);
        colorIDHashMap.put(5,Color.LIGHT_GRAY);

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
                view.disableAllTerritoryButton();
                paintTerritoryButtons();
            }

        }

    class DraftButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJButton("Confirm").setEnabled(true);
                currentStage = Stage.DRAFT;
                view.getJButton("Draft").setEnabled(false);
                currentPlayer = model.getCurrentPlayer();
                model.getCurrentPlayer().gainTroopsFromTerritory();
                view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
                view.setTroopsBox(currentPlayer.getTroops());
                view.enableOriginalTerritories(currentPlayer.getTerritories());
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
            currentStage=Stage.ATTACK;
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Click enabled territory button and pick surround available target territory button.");
        }
    }


    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.FORTIFY;
            view.getJButton("Skip").setEnabled(true);
            view.getJButton("Fortify").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.enableOriginalTerritories(model.getFortifyTerritories(currentPlayer));
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Please choose the two Territories you want send troops from and to.");
        }
    }
    public class DeployButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.DEPLOY;
            view.getJButton("Deploy").setEnabled(false);
            view.getJButton("Attack").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.getJButton("Skip").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Set troops to the new earned territory.");
            view.setTroopsBox(attackTerritory.getTroops()-1);
        }
    }

    public class SkipButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStage == Stage.ATTACK){
                view.getJButton("Attack").setEnabled(false);
                view.getJButton("Fortify").setEnabled(true);
                view.getJButton("Confirm").setEnabled(false);
                view.getJButton("Skip").setEnabled(false);
                resetButtonsAndBoxProcedure();
                view.setStatusLabel(currentPlayer.getName() + "'s turn, Click \"Fortify\" button to start Fortify stage.");
            }


            if(currentStage == Stage.FORTIFY){
                view.getJButton("Fortify").setEnabled(false);
                view.getJButton("Skip").setEnabled(false);
                view.getJButton("Draft").setEnabled(true);
                view.getJButton("Confirm").setEnabled(false);
                resetButtonsAndBoxProcedure();
                currentPlayer = model.getNextPlayer(currentPlayer.getID());
                model.setCurrentPlayer(currentPlayer);
                view.setStatusLabel(currentPlayer.getName()+ "'s turn, please click \"Draft\" button to start DRAFT stage.");
                view.disableAllTerritoryButton();
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
                model.checkContinent(currentPlayer);
            }

            if(currentStage==Stage.FORTIFY){
                fortifyProcess();
                resetButtonsAndBoxProcedure();
                currentPlayer = model.getNextPlayer(currentPlayer.getID());
                model.setCurrentPlayer(currentPlayer);
                view.setStatusLabel(currentPlayer.getName()+ "'s turn, please click \"Draft\" button to start DRAFT stage.");
                view.disableAllTerritoryButton();
            }

            if(currentStage==Stage.DEPLOY){
                deployTroopsProcess(attackTerritory,defenceTerritory);
                model.checkWinner();
                view.disableAllTerritoryButton();
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
                    ((JButton)e.getSource()).setBackground(colorIDHashMap.get(currentPlayer.getID()));
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
                    if(!targetTerritoryName.equals("")){
//                        view.getTerritoryButtonByString(targetTerritoryName).setBackground(colorIDHashMap.get(model.getTerritoryByString(targetTerritoryName).getHolder().getID()));
                        targetTerritoryButtonPressed = true;
                    }
                    view.clearTroopsBox();
//                    ((JButton)e.getSource()).setBackground(colorIDHashMap.get(currentPlayer.getID()));
                    paintTerritoryButtons();
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
                    paintTerritoryButtons();
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
                    if(!targetTerritoryName.equals("")){
                        targetTerritoryButtonPressed = true;
                    }
                    view.clearTroopsBox();
                    paintTerritoryButtons();
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
                    ((JButton)e.getSource()).setBackground(colorIDHashMap.get(model.getTerritoryByString(targetTerritoryName).getHolder().getID()));
                    targetTerritoryName = "";
                    view.onlyEnableOriginTerritory(originTerritoryName);
                    view.enableTargetTerritories(model.getFortifiedTerritory(model.getTerritoryByString(originTerritoryName),currentPlayer),originTerritoryName);
                    targetTerritoryButtonPressed = !targetTerritoryButtonPressed;
                }
            }
        }
    }

    private void attackProcess() {
        if(originTerritoryName.equals("") || targetTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        attackTerritory = model.getTerritoryByString(originTerritoryName);
        defenceTerritory = model.getTerritoryByString(targetTerritoryName);
        AttackWay attackWay = view.getAttackTroopsBox();
        boolean gainTerritory = model.battle(attackTerritory,defenceTerritory,attackWay);
        view.setTerritoryButtonTroops(originTerritoryName,attackTerritory.getTroops());
        view.setTerritoryButtonTroops(targetTerritoryName,defenceTerritory.getTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        if(gainTerritory){
            JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"/nYou conquered "+defenceTerritory.getName()+"!");
            view.getJButton("Deploy").setEnabled(true);
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Skip").setEnabled(false);
            view.disableAllTerritoryButton();
            view.setStatusLabel(currentPlayer.getName()+" wins the battle! Press \"Deploy\" button to deploy troops.");
            view.clearTroopsBox();
            return;
        }
        JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"/nYou didn't conquered "+defenceTerritory.getName()+".");
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
        currentStage = Stage.ATTACK;
    }

    private void resetButtonsAndBoxProcedure(){
        if(!originTerritoryName.equals("")) {
            originTerritoryButtonPressed = true;
            originTerritoryName = "";
        }
        if(!targetTerritoryName.equals("")) {
            targetTerritoryButtonPressed = true;
            targetTerritoryName = "";
        }
        paintTerritoryButtons();
        view.clearTroopsBox();
        view.disableAllTerritoryButton();
        view.enableOriginalTerritories(model.getAttackTerritoriesList(currentPlayer));
    }

    private void draftProcess(){
        if(originTerritoryName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please select One territory!", "No Territory Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.draft(currentPlayer, originTerritoryName, view.getSelectedTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        String continentBonus="";
        if(currentPlayer.haveContinent()) {
            continentBonus = "With ";
            for(Continent continent:currentPlayer.getContinents()){
                continentBonus+=continent.getName()+": "+continent.getBonusTroops()+" troops.";
            }
        }
        view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent."+continentBonus);
        view.setTroopsBox(currentPlayer.getTroops());
        view.setTerritoryButtonTroops(originTerritoryName,model.getTerritoryByString(originTerritoryName).getTroops());
        paintTerritoryButtons();
        if(currentPlayer.getTroops()==0){
            view.disableAllTerritoryButton();
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Attack").setEnabled(true);
            view.getJButton("Skip").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to start ATTACK stage.");
            return;
        }
        view.enableOriginalTerritories(currentPlayer.getTerritories());
        originTerritoryName="";
        originTerritoryButtonPressed = true;
    }

    private void fortifyProcess(){
        if(originTerritoryName.equals("") || targetTerritoryName.equals("")) {
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
        view.getJButton("Draft").setEnabled(true);
        view.getJButton("Confirm").setEnabled(false);
        view.getJButton("Skip").setEnabled(false);
        view.getJButton("Attack").setEnabled(false);
    }

    private void paintTerritoryButtons(){
        for(int id = 0; id<model.getPlayerNum();id++){
            view.paintTerritoryButtons(model.getPlayerById(id),colorIDHashMap.get(id));
        }
    }

    public static void main(String[] args)  throws IOException{
        new RiskController();
    }
}

