package MapEditer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

/**
 * Class MapEditer can make custom map and generate txt file
 * @author Guanqun Dong
 * @version 1.0
 */
public class MapEditer {
    private JFrame jFrame ;
    //new button size
    private int width=100;
    private int height=100;
    //get location
    private EditerController controller;

    private JPanel maparea;
    //enable disable button
    private boolean removeButton=false;
    private boolean createButton=false;

    public MapEditer()
    {
        //initial Editer
        controller = new EditerController(this);
        jFrame = new JFrame("Map Editer");
        jFrame.setSize(1024,768);
        JSplitPane splitPane= new JSplitPane();
        jFrame.add(splitPane);
        maparea = new JPanel();
        splitPane.setLeftComponent(maparea);
        splitPane.setDividerLocation(768);
        JPanel controlarea = new JPanel();
        splitPane.setRightComponent(controlarea);
        //layouts
        controlarea.setLayout(new BoxLayout(controlarea,BoxLayout.Y_AXIS));
        maparea.setLayout(null);

        //map area Listener
        maparea.addMouseListener(controller);

        //size 1
        JButton creatTerritory = new JButton("creatTerritory");
        controlarea.add(creatTerritory);
        creatTerritory.addActionListener(e -> {
            createButton = !createButton;
        });

        //size 1
        JButton size1 = new JButton("sizes1");
        controlarea.add(size1);
        size1.addActionListener(e -> {
            width=50;
            height=50;
        });

        //size 2
        JButton size2 = new JButton("sizes2");
        controlarea.add(size2);
        size2.addActionListener(e -> {
            width=100;
            height=100;
        });

        //size 3
        JButton size3 = new JButton("sizes3");
        controlarea.add(size3);
        size3.addActionListener(e -> {
            width=150;
            height=150;
        });

        //custom Size
        JButton customsize = new JButton("custom size");
        controlarea.add(customsize);
        JLabel customsizewidth = new JLabel("custom size width");
        controlarea.add(customsizewidth);
        JTextField customSize1 = new JTextField();
        controlarea.add(customSize1);
        JLabel customsizeheight = new JLabel("custom size height");
        controlarea.add(customsizeheight);
        JTextField customSize2 = new JTextField();
        controlarea.add(customSize2);
        customsize.addActionListener(e -> {
            try {
                width = Integer.valueOf(customSize1.getText());
                height = Integer.valueOf(customSize2.getText());
            }catch (Exception error)
            {
                System.out.println("Invalid entry");
            }
        });


        //remove
        JButton remove = new JButton("remove");
        controlarea.add(remove);
        remove.addActionListener(e -> {
            removeButton=!removeButton;
        });

        //generate Map
        JButton generateMap = new JButton("generate Map");
        controlarea.add(generateMap);
        generateMap.addActionListener(e -> {
            generate();
        });


        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * write existing Buttons to txt file
     */
    private void generate() {
        try {
            FileWriter writer = new FileWriter(new File("mapInfo.txt"));
            Component[] components = maparea.getComponents();
            for (Component c : components)
            {
                if(c instanceof JButton)
                {
                    JButton jb = (JButton)c;
                    writer.write(jb.getText()+" "+c.getX()+" "+c.getY()+" "+c.getWidth()+" "+c.getHeight()+"\n");
                }

            }
            writer.close();
        }catch (Exception e)
        {
            System.out.println("Error");
        }

    }


    /**
     * create a New Button base on mouse position
     * if this is an existing Button and remove is enabled,remove this button
     */
    public void createNewButton() {
        if(createButton){
            JButton temp = new JButton();
            Point p = maparea.getMousePosition();
            temp.setBounds(p.x,p.y,width,height);
            maparea.add(temp);
            temp.repaint();
            temp.addActionListener(e ->
                    {
                        if(removeButton){
                            maparea.remove(temp);
                            maparea.repaint();
                        }
                        else {temp.setText(JOptionPane.showInputDialog("Give it a name"));}
                    }
            );
        }
    }
    public static void main(String[] args) {
        new MapEditer();
    }
}
