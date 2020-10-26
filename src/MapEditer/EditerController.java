package MapEditer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * EditerController catches Mouse position and ask editor to  create new Continent
 * @author Guanqun Dong
 * @version 1.1
 */
public class EditerController implements MouseListener {
    private MapEditer editor;
    public EditerController(MapEditer model)
    {
        editor = model;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        editor.createNewContinent();

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
