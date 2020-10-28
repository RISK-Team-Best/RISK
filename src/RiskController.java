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

    /*private class ItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JMenuItem menuItem = (JMenuItem) e.getSource();
            if (menuItem.getText().equals("New")) {
                System.out.println("Create a new game");

                int playerNum = view.getNumberPlayerDialog();
                model.setPlayerNum(playerNum);
                System.out.println(model.getPlayerNum());
                String[] playerNameList = view.popGetName();
                model.addPlayersName(playerNameList);
                System.out.println(model.getPlayerName());
                model.initialGame();
                view.setContinentsLabel(model.getMapInfoThroughContinent());
            }
            JMenuItem draftItem = (JMenuItem) e.getSource();
            else if (draftItem.getText().equals("Draft")){

            }
            else if
            else{}

        }
    }*/
    public class NewGameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

                int playerNum = view.getNumberPlayerDialog();
                model.setPlayerNum(playerNum);
                System.out.println(model.getPlayerNum());
                String[] playerNameList = view.popGetName();
                model.addPlayersName(playerNameList);
                System.out.println(model.getPlayerName());
                model.initialGame();
                view.setContinentsLabel(model.getMapInfoThroughContinent());
                //model.processGaming();
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

