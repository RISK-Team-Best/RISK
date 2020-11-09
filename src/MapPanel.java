import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * MapPanel class for add map on GUI
 */
public class MapPanel extends JPanel{

    private BufferedImage image;


    /**
     * Initial method
     */
    public MapPanel() {
        try {
            image = ImageIO.read(new File("src/RiskMap.jpg"));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    /**
     * @param g add image to the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}