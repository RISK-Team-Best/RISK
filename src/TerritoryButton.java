import javax.swing.*;
import java.awt.*;

public class TerritoryButton extends JButton {
    private Territory territory;
    public TerritoryButton(String name,int xAxis,int yAxis,int width,int height)
    {
        setText(name);
        setBounds(xAxis,yAxis,width,height);
        repaint();
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public void update()
    {
        Player holder = territory.getHolder();
        if(isEnabled())
        {
            Color color = Player.getPlayerColorSelected(holder);
            setText(territory.getName()+" "+territory.getTroops());
            setBackground(color);
            repaint();
        }
        else
        {
            Color color = Player.getPlayerColor(holder);
            setText(territory.getName()+" "+territory.getTroops());
            setBackground(color);
            repaint();
        }
    }
}
