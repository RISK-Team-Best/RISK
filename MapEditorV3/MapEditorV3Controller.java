package MapEditorV3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapEditorV3Controller implements MouseListener {
    private MapEditorV3Model model;
    public MapEditorV3Controller(MapEditorV3Model model)
    {
        this.model = model;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent component = (JComponent) e.getSource();
        Point p = component.getMousePosition();
        model.addTerritory(p.x,p.y);
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
