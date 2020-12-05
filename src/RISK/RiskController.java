package RISK;

import org.xml.sax.SAXException;

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
    private String mapName = "OriginRiskMap";


    /**
     * @param riskModel
     * @param riskView
     * @throws IOException
     */
    public RiskController(RiskModel riskModel,RiskView riskView) throws IOException{
        this.model = riskModel;
        this.view = riskView;
        this.model.addView(this.view);
        registerListeners();
    }

    private void registerListeners(){
        view.addNewGameMenuListener(new NewGameMenuListener() );
        view.addDraftButtonListener(new DraftButtonListener());
        view.addAttackButtonListener(new AttackButtonListener());
        view.addDeployButtonListener(new DeployButtonListener());
        view.addFortifyButtonListener(new FortifyButtonListener());
        view.addSkipButtonListener(new SkipButtonListener());
        view.addConfirmButtonListener(new ConfirmButtonListener());
        view.addTerritoryButtonListener(new TerritoryButtonListener());
        view.addSaveButtonListner(new SaveButtonListener());
        view.addImportButtonListner(new LoadButtonListener());
        view.addNewMapButtonListener(new NewMapListener());
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

    public class NewMapListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mapName = new LoadNewMapDialog().getMapName();
                model = new RiskModel(mapName);
                view.dispose();
                view = new RiskView(new Board(mapName));
                model.addView(view);
                registerListeners();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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
            model.territoryButtonClickProcess(e.getActionCommand());
        }
    }

    public class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.saveProcess();
//            SavingStrategy strategy = new XMLDOMWriter();
//            strategy.saveGame(model,"testFile.xml");

        }
    }
    public class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            XMLHandler handler = null;
            try {
                 handler = new XMLHandler(mapName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try{
                String fileName = view.getFileName()+"_"+model.getMapName();
                handler.importXMLFileByName(fileName);
            }
            catch (Exception fileNotFoundException){
                JOptionPane.showMessageDialog(null,"You haven't save any file under this map. Please check and try again.","File Not Found",JOptionPane.ERROR_MESSAGE);
                return;
            }

            model = handler.getModel();
            model.addView(view);
            model.reload();
            //System.out.print(model.getCurrentStage());

        }

    }

    }


