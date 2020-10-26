import javax.swing.*;
import java.awt.*;

public class TerritoryButton extends JButton {
    private Territory territory;
    public TerritoryButton(String name,String xAxis,String yAxis,String width,String height)
    {
        setText(name);
        setBounds(Integer.valueOf(xAxis),Integer.valueOf(yAxis),Integer.valueOf(width),Integer.valueOf(height));
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
