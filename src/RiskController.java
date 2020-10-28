import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RiskController {
    private RiskModel model;
    private RiskView view;

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
                view.setContinentsLabel(model.getMapInfoThroughContinent());
                //model.processGaming();
                view.getJButton("Draft").setEnabled(true);
                view.getJButton("Confirm").setEnabled(true);
                view.setStatusLabel("Now it's "+model.getCurrentPlayer().getName() +"'s turn, please click \"Draft\" button to start DRAFT stage.");
            }

        }

    public class DraftButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }

    public class AttackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

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
            if (model.getCurrentStage()== Stage.ATTACK){
                view.getJButton("Attack").setEnabled(false);
                view.getJButton("Deploy").setEnabled(false);
                view.getJButton("Draft").setEnabled(false);
                view.getJButton("Fortify").setEnabled(true);
            }


            if(model.getCurrentStage() == Stage.FORTIFY){
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

        }
    }


    public static void main(String[] args)  throws IOException{
        new RiskController();
    }
}

