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
        view.addActionListener(new ItemListener());

    }

    private class ItemListener implements ActionListener {

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

            }

        }
    }
    public static void main(String[] args)  throws IOException{
        new RiskController();
    }
}

