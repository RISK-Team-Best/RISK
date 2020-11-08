package MapEditorV3;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;

public class MapEditorV3 extends JFrame {
    private final MapEditorV3Model model = new MapEditorV3Model();
    private final JInternalFrame internalFrame = new JInternalFrame("Map Area");
    private final JFileChooser fileChooser = new JFileChooser();
    private final JLabel label = new JLabel();
    private final JScrollPane scrollPane = new JScrollPane(label);
    private final MapEditorV3Controller controller;

    public MapEditorV3()
    {
        super("MapEditor Version 3");

        fileChooser.setCurrentDirectory(new File("res"));
        controller = new MapEditorV3Controller(model);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem openMap = new JMenuItem("Open Map");
        file.add(openMap);
        openMap.addActionListener(e -> {
            int state = fileChooser.showOpenDialog(null);
            if (state == JFileChooser.APPROVE_OPTION){
                File f = fileChooser.getSelectedFile();
                String path = f.getAbsolutePath();
                label.setIcon(new ImageIcon(path));
            }

        });


        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(1366);
        this.add(splitPane);

        internalFrame.setSize(1366,800);
        internalFrame.setVisible(true);
        splitPane.setLeftComponent(internalFrame);
        internalFrame.add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        label.addMouseListener(controller);


        JPanel controlArea = new JPanel();
        splitPane.setRightComponent(controlArea);
        controlArea.setLayout(new BoxLayout(controlArea,BoxLayout.PAGE_AXIS));

        JList<String> continents = new JList<>();
        continents.setModel(model);
        JScrollPane scrollPane= new JScrollPane(continents);
        controlArea.add(scrollPane);
        JButton addContinent = new JButton("addContinent");
        addContinent.addActionListener(e -> {
            String s = JOptionPane.showInputDialog("Continent Name");
            model.addElement(s);
        });
        controlArea.add(addContinent);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920,800);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MapEditorV3();
    }
}
