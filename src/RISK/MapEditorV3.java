package RISK;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class MapEditorV3 extends JFrame {
    private static final int SIZE = 30;
    private final MapEditorV3Model model = new MapEditorV3Model(this);
    private final JInternalFrame internalFrame = new JInternalFrame("Map Area");
    private final JFileChooser fileChooser = new JFileChooser();
    private final JLabel label = new JLabel();
    private final JScrollPane scrollPane = new JScrollPane(label);
    private final MapEditorV3Controller controller;
    private HashMap<String,JButton> buttons = new HashMap<>();

    public MapEditorV3(){
        //initial important parts
        super("MapEditor Version 3");
        fileChooser.setCurrentDirectory(new File("res"));
        controller = new MapEditorV3Controller(model);

        //menu
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem openMap = new JMenuItem("Open Map");
        file.add(openMap);
        openMap.addActionListener(e -> {
            //file chooser
            int state = fileChooser.showOpenDialog(null);
            if (state == JFileChooser.APPROVE_OPTION){
                File f = fileChooser.getSelectedFile();
                String path = f.getAbsolutePath();
                label.setIcon(new ImageIcon(path));
            }

        });


        //main components
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(1366);
        this.add(splitPane);
        //map area
        internalFrame.setSize(1366,800);
        internalFrame.setVisible(true);
        splitPane.setLeftComponent(internalFrame);
        internalFrame.add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        label.addMouseListener(controller);


        //control area
        JPanel controlArea = new JPanel();
        splitPane.setRightComponent(controlArea);
        controlArea.setLayout(new BoxLayout(controlArea,BoxLayout.PAGE_AXIS));

        //List 1
        JList<String> continents = new JList<>();
        continents.setModel(model);
        model.setView(continents);
        continents.addListSelectionListener(e -> {
            model.changeSubmodel();
        });
        JScrollPane scrollPane= new JScrollPane(continents);
        controlArea.add(scrollPane);

        //button 1
        JButton addContinent = new JButton("addContinent");
        addContinent.addActionListener(e -> {
            String s = JOptionPane.showInputDialog("Continent Name");
            model.addContinent(s);
        });
        controlArea.add(addContinent);

        //List2
        JList<String> territories = new JList<>();
        model.setSubView(territories);
        JScrollPane scrollPane2= new JScrollPane(territories);
        controlArea.add(scrollPane2);

        //button 2
        JButton removeTerritory = new JButton("Remove Territory");
        removeTerritory.addActionListener(e -> {
            model.removeTerritory();
        });
        controlArea.add(removeTerritory);



        //button 3
        JButton writeMap = new JButton("Generate Map");
        writeMap.addActionListener(e -> {
            model.generateBoard();
        });
        controlArea.add(writeMap);


        //frame settings
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920,800);
        setVisible(true);

    }

    public static void main(String[] args) {
        new MapEditorV3();
    }

    public void addButton(String name,int x, int y) {
        JButton button = new JButton();
        button.setBounds(x-SIZE/2,y-SIZE/2,SIZE*2,SIZE);
        label.add(button);
        button.setText(name);
        buttons.put(name,button);
        button.repaint();
    }

    public void removeButton(String s) {
        label.remove(buttons.get(s));
        label.repaint();
    }
}
