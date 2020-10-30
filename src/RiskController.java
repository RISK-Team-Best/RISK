import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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

    }


    public class NewGameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


                model.setPlayerNum(view.getNumberPlayerDialog());
                model.addPlayersName(view.popGetName());
                model.initialGame();
                currentStage = Stage.DRAFT;
                view.setContinentsLabel(model.getMapInfoThroughContinent());
                //model.processGaming();
                view.getJButton("Draft").setEnabled(true);
//                view.getJButton("Confirm").setEnabled(true);
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
                view.setStartingTerritory(currentPlayer.getDraftTerritoriesName());
                model.checkContinent(currentPlayer);
                view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
                view.setTroopsBox(currentPlayer.getTroops());
                //view.getJButton("Skip").setEnabled(true);
                //currentStage = Stage.ATTACK;
            }
        }

    public class AttackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             Player player = model.getCurrentPlayer();


        }
    }
    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            currentStage = Stage.FORTIFY;
            view.getJButton("Fortify").setEnabled(false);
            view.getJButton("Confirm").setEnabled(true);
            currentPlayer = model.getCurrentPlayer();
            view.setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Please choose the Territory you want send troops from.");
            view.setStartingTerritory(model.checkFortifyTerritory(currentPlayer));
            String startCountry = view.getStartingTerritory();
            view.setDestinationTerritory(model.checkFortifiableTerritory(startCountry,currentPlayer));
            view.setTroopsBox(currentPlayer.getTerritoryByString(startCountry).getTroops()-1);


        }
    }
    public class DeployButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

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
            }

        }
    }

    private void draftProcess(){
        String territory = view.getStartingTerritory();
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
            currentStage = Stage.ATTACK;
        }
    }
    private void fortifyProcess(){

        String startCountry = view.getStartingTerritory();
        String destinationCountry = view.getDestinationTerritory();
        int troops = view.getSelectedTroops();
        model.fortify(startCountry,destinationCountry,currentPlayer,troops);
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

