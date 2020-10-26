import javax.swing.*;

public class ContinentPanel extends JPanel {
    public ContinentPanel(String name,String xAxis,String yAxis,String width,String height)
    {
        setName(name);
        setBounds(Integer.valueOf(xAxis),Integer.valueOf(yAxis),Integer.valueOf(width),Integer.valueOf(height));
        repaint();
    }
}
