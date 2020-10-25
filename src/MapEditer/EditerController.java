package MapEditer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * EditerController catches Mouse position and ask editor to  create new button
 */
public class EditerController implements MouseListener {
    private MapEditer editor;
    public EditerController(MapEditer model)
    {
        editor = model;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        editor.createNewButton();

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
