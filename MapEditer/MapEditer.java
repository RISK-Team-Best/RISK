package MapEditer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

/**
 * Class MapEditer can make custom map and generate txt file
 * @author Guanqun Dong
 * @version 1.1
 */
public class MapEditer {
    private JFrame jFrame ;
    //new button size
    private int width=100;
    private int height=100;
    //get location
    private EditerController controller;
    private ContinentController continentController;

    private JPanel maparea;
    //enable disable button
    private boolean removeLand=false;
    private int createLand=0;

    public MapEditer()
    {
        //initial Editer
        controller = new EditerController(this);
        continentController = new ContinentController(this);
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

        JLabel mode = new JLabel("Not Creating");
        controlarea.add(mode);

        //creatTerritory
        JButton creatTerritory = new JButton("creatTerritory");
        controlarea.add(creatTerritory);
        creatTerritory.addActionListener(e -> {
            createLand = 1;
            mode.setText("Territory Mode");
        });

        JButton creatContinent = new JButton("creatContinent");
        controlarea.add(creatContinent);
        creatContinent.addActionListener(e -> {
            createLand = 2;
            mode.setText("Continent Mode");
        });

        JButton notcreate = new JButton("notcreate");
        controlarea.add(notcreate);
        notcreate.addActionListener(e -> {
            createLand = 0;
            mode.setText("Not Creating");
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


        JLabel removelabel = new JLabel(String.valueOf(removeLand));
        controlarea.add(removelabel);
        //remove
        JButton remove = new JButton("remove Land");
        controlarea.add(remove);
        remove.addActionListener(e -> {
            removeLand=!removeLand;
            removelabel.setText(String.valueOf(removeLand));
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
     * write existing Lands to txt file
     */
    private void generate() {
        try {
            FileWriter writer = new FileWriter(new File("mapInfo.txt"));
            Component[] components = maparea.getComponents();
            for (Component continent : components)
            {
                if(continent instanceof JPanel)
                {
                    JPanel continent2 = (JPanel) continent;
                    writer.write("Continent: "+continent2.getName()+" "+continent2.getX()+" "+continent2.getY()+" "+continent2.getWidth()+" "+continent2.getHeight()+"\n");
                    Component[] Territorys = continent2.getComponents();
                    for(Component territory : Territorys)
                    {
                        JButton t = (JButton)territory;
                        writer.write("Territory: "+t.getText()+" "+t.getX()+" "+t.getY()+" "+t.getWidth()+" "+t.getHeight()+"\n");

                    }
                }

            }
            writer.close();
        }catch (Exception e)
        {
            System.out.println("Error");
        }

    }


    /**
     * create a Territory Button base on mouse position
     * if this is an existing Button and remove is enabled,remove this button
     */
    public void createNewTerritory(JPanel continent) {
        if(createLand==1){
            JButton temp = new JButton();
            Point p = continent.getMousePosition();
            temp.setBounds(p.x,p.y,width,height);
            continent.add(temp);
            temp.repaint();
            continent.repaint();
            temp.addActionListener(e ->
                    {
                        if(removeLand){
                            continent.remove(temp);
                            continent.repaint();
                        }
                        else {temp.setText(JOptionPane.showInputDialog("Give it a name"));}
                    }
            );
        }
    }

    /**
     * Create a new Continent base on mouse position
     *
     */
    public void createNewContinent() {
        if(createLand==2){
            JPanel temp = new JPanel();
            Point p = maparea.getMousePosition();
            temp.setBounds(p.x,p.y,width,height);
            temp.setLayout(null);
            maparea.add(temp);
            temp.repaint();
            temp.setBackground(new Color(255,0,0));
            temp.addMouseListener(continentController);
        }
    }

    /**
     * remove continent when remove is on and in Continent Mode
     * @param e1 clicked Continent
     */
    public void removeContinent(JPanel e1) {
        if(removeLand && createLand==2){
            maparea.remove(e1);
            maparea.repaint();
        }
    }

    public static void main(String[] args) {
        new MapEditer();
    }
}
