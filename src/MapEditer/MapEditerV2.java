package MapEditer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;

public class MapEditerV2 extends JFrame {
    private JLabel map;
    private FileWriter writer;
    public MapEditerV2()
    {
        map = new JLabel();
        map.setIcon(new ImageIcon(getClass().getClassLoader().getResource("RiskMap2.png")));
        add(map);
        map.setLayout(null);
        map.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p   = new Point(map.getMousePosition());
                System.out.println(p.x);
                System.out.println(p.y);
                try {
                    JButton jb = new JButton();
                    jb.setBounds(p.x-15,p.y-15,30,30);
                    jb.setText(JOptionPane.showInputDialog("name"));
                    map.add(jb);
                    jb.repaint();
                    writer = new FileWriter(new File("test.txt"), true);
                    writer.write(jb.getText()+","+p.x + "," + p.y+"\n");
                    writer.close();


                }catch (Exception e1){}

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
        });
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(830,700);

    }

    public static void main(String[] args) {
        try {
            System.out.println(MapEditerV2.class);
            System.out.println(MapEditerV2.class.getClassLoader());
            System.out.println(MapEditerV2.class.getClassLoader().getResources("axe1a.png"));
        }catch (Exception e){}
    }
}
