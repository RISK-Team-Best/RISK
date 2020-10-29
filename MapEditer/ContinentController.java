package MapEditer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ContinentController catches Mouse position and ask editor to  create new Territory or remove a Continent
 * @author GuanqunDong
 * @version 1.1
 */
public class ContinentController implements MouseListener {
    private MapEditer editor;
    public ContinentController(MapEditer model)
    {
        editor = model;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Object e1 = e.getSource();
        if(e1 instanceof JPanel) {
            if(e.getClickCount()==2){((JPanel) e1).setName(JOptionPane.showInputDialog("Give it a Name"));}
            else {
                editor.createNewTerritory((JPanel) e1);
                editor.removeContinent((JPanel) e1);
            }
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
