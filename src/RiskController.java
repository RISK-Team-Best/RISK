import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        view.addStartingTerritoryListListener(new StartingTerritoryListener());
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
            }

        }

    class DraftButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJButton("Draft").setEnabled(false);
                currentPlayer = model.getCurrentPlayer();
                //System.out.println(currentPlayer.getName()+currentPlayer.getTerritories()); test
                model.getCurrentPlayer().gainTroopsFromTerritory();
                view.setStartingTerritory(currentPlayer.getTerritoriesList());
                view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
                view.setTroopsBox(currentPlayer.getTroops());
                //view.getJButton("Skip").setEnabled(true);
                //currentStage = Stage.ATTACK;
            }
        }

    public class AttackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = null;
            view.getJButton("Attack").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.setStartingTerritory(model.setAttackTerritories(currentPlayer));
            view.setDestinationTerritory(model.setDefenceTerritories(currentPlayer,view.getStartingTerritory()));
            view.setAttackTroopsBox(view.getStartingTerritory().getTroops());
            currentStage=Stage.ATTACK;
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Select territory from Origin Territory list and pick target territory in Target Territory list");
        }
    }


    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = null;
            view.getJButton("Fortify").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.setStartingTerritory(model.setFortifyTerritory(currentPlayer));
            currentStage = Stage.FORTIFY;
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Please choose the Territory you want send troops from.");
            view.setDestinationTerritory(model.setFortifiableTerritory(view.getStartingTerritory(),currentPlayer));
            view.setTroopsBox(view.getStartingTerritory().getTroops()-1);


        }
    }
    public class DeployButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.DEPLOY;
            view.getJButton("Attack").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Set troops to the new earned territory.");
            view.setTroopsBox(attackTerritory.getTroops()-1);
        }
    }

    public class StartingTerritoryListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(currentStage == Stage.ATTACK) {
                view.setDestinationTerritory(model.setDefenceTerritories(currentPlayer, view.getStartingTerritory()));
                view.setAttackTroopsBox(view.getStartingTerritory().getTroops()-1);
            }
            if(currentStage == Stage.FORTIFY){
                view.setDestinationTerritory(model.setFortifiableTerritory(view.getStartingTerritory(),currentPlayer));
                view.setTroopsBox(view.getStartingTerritory().getTroops()-1);
            }
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

        }
    }

    private void attackProcess() {
        attackTerritory = view.getStartingTerritory();
        defenceTerritory = view.getDestinationTerritory();
        AttackWay attackWay = view.getAttackTroopsBox();
        boolean winBattle = model.battle(attackTerritory,defenceTerritory,attackWay);
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        if(winBattle){
            JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"\nCongratulation, you win this battle!");
            view.getJButton("Deploy").setEnabled(true);
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Skip").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName()+" wins the battle! Press \"Deploy\" button to deploy troops.");
            currentStage = null;
            view.setStartingTerritory(getTempStartingTerritoryList(attackTerritory));
            view.setDestinationTerritory(getTempTargetTerritoryList(defenceTerritory));
            return;
        }
        JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"\nUnfortunately, you lose this battle...");
        currentStage = null;
        view.setStartingTerritory(model.setAttackTerritories(currentPlayer));
        view.setDestinationTerritory(model.setDefenceTerritories(currentPlayer,view.getStartingTerritory()));
        currentStage = Stage.ATTACK;
    }

    private void deployTroopsProcess(Territory attackTerritory, Territory defenceTerritory){
        model.deployTroops(attackTerritory,defenceTerritory,view.getSelectedTroops());
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        currentStage = Stage.ATTACK;
        view.getJButton("Deploy").setEnabled(false);
        view.getJButton("Attack").setEnabled(true);
        view.getJButton("Confirm").setEnabled(false);
        view.getJButton("Skip").setEnabled(true);
        view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to continue ATTACK stage OR \"Skip\" button to skip to Fortify Stage.");
    }

    private void draftProcess(){
        String territory = view.getStartingTerritory().getName();
        int troops = view.getSelectedTroops();
        model.draft(currentPlayer, territory, troops);
        view.setContinentsLabel(model.getMapInfoThroughContinent());
        view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
        view.setTroopsBox(currentPlayer.getTroops());
        if(currentPlayer.getTroops()==0){
            view.getJButton("Confirm").setEnabled(false);
            view.getJButton("Attack").setEnabled(true);
            view.getJButton("Skip").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to start ATTACK stage.");
            currentStage = null;
            return;
        }
    }

    private void fortifyProcess(){
        Territory startCountry = view.getStartingTerritory();
        Territory destinationCountry = view.getDestinationTerritory();
        int troops = view.getSelectedTroops();
        if(destinationCountry==null) {
            JOptionPane.showMessageDialog(null, startCountry.getName()+" doesn't have any neighbors under your control!", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.fortify(startCountry,destinationCountry,troops);
        view.setContinentsLabel(model.getMapInfoThroughContinent());
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

