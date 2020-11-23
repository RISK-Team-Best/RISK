package RISK;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Controller connect the model and view
 */
public class RiskController {
    private RiskModel model;
    private RiskView view;


    /**
     * @param riskModel
     * @param riskView
     * @throws IOException
     */
    public RiskController(RiskModel riskModel,RiskView riskView) throws IOException{
        this.model = riskModel;
        this.view = riskView;

        this.model.addView(this.view);

        view.addNewGameMenuListener(new NewGameMenuListener() );
        view.addDraftButtonListener(new DraftButtonListener());
        view.addAttackButtonListener(new AttackButtonListener());
        view.addDeployButtonListener(new DeployButtonListener());
        view.addFortifyButtonListener(new FortifyButtonListener());
        view.addSkipButtonListener(new SkipButtonListener());
        view.addConfirmButtonListener(new ConfirmButtonListener());
        view.addTerritoryButtonListener(new TerritoryButtonListener());



    }


    /**
     * Inner class implement NewGame function
     */
     class NewGameMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.newGameProcess();
            }
        }

    /**
     * Inner class implement Draft function
     */
    class DraftButtonListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                model.draftPrepare();
            }
        }

    /**
     * Inner class implement Attack function
     */
    public class AttackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.attackPrepare();
        }
    }


    /**
     * Inner class implement Fortify function
     */
    public class FortifyButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.fortifyPrepare();
        }
    }

    /**
     * Inner class implement Deploy function
     */
    public class DeployButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.deployPrepare();
        }
    }

    /**
     * Inner class implement Skip function
     */
    public class SkipButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.skipProcess();
        }
    }


    /**
     * Inner class implement Confirm function
     */
    public class ConfirmButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.confirmProcess();
        }
    }

    /**
     * Inner class implement Territory buttons function
     */
    public class TerritoryButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            model.territoryButtonClickProcess(e.getActionCommand(),(JButton)e.getSource());

        }
    }
}

