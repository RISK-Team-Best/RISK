import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RiskController {
    private RiskModel model;
    private RiskView view;

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
    }


    public class NewGameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


                model.setPlayerNum(view.getNumberPlayerDialog());
                model.addPlayersName(view.popGetName());
                model.initialGame();
                currentStage = Stage.DRAFT;
                view.setContinentsLabel(model.getMapInfoThroughContinent());
                view.getJButton("Draft").setEnabled(true);
                view.setStatusLabel("Now it's "+model.getCurrentPlayer().getName() +"'s turn, please click \"Draft\" button to start DRAFT stage.");
                view.getJButton("Confirm").setEnabled(true);
            }

        }

    public class DraftButtonListener implements ActionListener{

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
            //Player player = model.getCurrentPlayer();
            view.getJButton("Attack").setEnabled(false);
            view.setStartingTerritory(model.setAttackTerritories(currentPlayer));
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
            currentStage=Stage.ATTACK;
            view.getJButton("Attack").setEnabled(false);
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Select territory from Origin Territory list and pick target territory in Target Territory list");
            view.setStartingTerritory(model.setAttackTerritories(currentPlayer));
        }
    }

    public class StartingTerritoryListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(currentStage==Stage.ATTACK) {
                view.setDestinationTerritory(model.setDefenceTerritories(currentPlayer, view.getStartingTerritory()));
                view.setTroopsBox(view.getStartingTerritory().getTroops());
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
            if(currentStage==Stage.FORTIFY){
                fortifyProcess();
                //only process once, then transfer to another player.
            }

        }
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
            view.getJButton("Skip").setEnabled(true);
            view.setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to start ATTACK stage.");
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
        view.getJButton("Confirm").setEnabled(true);
        view.getJButton("Skip").setEnabled(false);
        view.getJButton("Attack").setEnabled(false);
        currentStage = Stage.DRAFT;
        view.setStatusLabel(currentPlayer.getName()+ "'s turn, " + currentStage.getName() + " stage, please click \"Draft\" button to start DRAFT stage.");
    }



    public static void main(String[] args)  throws IOException{
        new RiskController();
    }
}

