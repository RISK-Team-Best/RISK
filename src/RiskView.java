import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;

public class RiskView extends JFrame {
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();

    private JPanel mainPanel = new JPanel();
    private JPanel graphMapPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel combinedMapPrintInfoPanel = new JPanel();

    private JTextArea printInfoArea = new JTextArea();

    private JList startingTerritory = new JList();
    private JList destinationTerritory = new JList();

    private JComboBox troopsBox = new JComboBox();

    //private JButton functionButton = new JButton();
    private JButton skipButton = new JButton("Skip");

    private JScrollPane startingTerritoryScrollPane;
    private JScrollPane destinationTerritoryScrollPane;
    private JScrollPane printInfoScrollPane;
    private JScrollPane continentInfoPane;

    private JMenuItem saveItem = new JMenuItem("Save");
    private JMenuItem loadItem = new JMenuItem("Load");
    private JMenuItem newGameItem = new JMenuItem("New");
    private JMenu fileMenu = new JMenu("File");
    private JMenuBar menuBar = new JMenuBar();

    private JButton attack = new JButton("Attack");
    private JButton draft = new JButton("Draft");
    private JButton fortify = new JButton("Fortify");
    private JButton deploy = new JButton("Deploy");

    private RiskModel model;
    private int numberPlayer = 0;

    public RiskView() throws IOException {
        super("Risk Game");

        model = new RiskModel();

//        statusLabel.setText("It's my turn.");

        //getNumberPlayerDialog();

        printInfoScrollPane = new JScrollPane(printInfoArea);
        printInfoArea.setEditable(false);
//        printInfoArea.append("Welcome to Risk Game!");

        startingTerritory.setModel(model.allCountriesJList);
        startingTerritory.setLayoutOrientation(JList.VERTICAL);
        startingTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        startingTerritoryScrollPane = new JScrollPane(startingTerritory);

        destinationTerritory.setModel(model.allContinentsJList);
        destinationTerritory.setLayoutOrientation(JList.VERTICAL);
        destinationTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        destinationTerritoryScrollPane = new JScrollPane(destinationTerritory);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("src/RiskMap.jpg"));
        graphMapPanel.add(label);

        continentInfoPane = new JScrollPane(continentsLabel);
        //continentInfoPane.add(continentsLabel);
        continentInfoPane.setPreferredSize(new Dimension(200,600));
        continentsLabel.setFont(new Font("Arial",0,12));

        combinedMapPrintInfoPanel.setLayout(new BorderLayout());
        combinedMapPrintInfoPanel.add(printInfoScrollPane,BorderLayout.WEST);
        combinedMapPrintInfoPanel.add(graphMapPanel,BorderLayout.EAST);

        operationPanel.setLayout(new BoxLayout(operationPanel,BoxLayout.Y_AXIS));
        operationPanel.add(startingTerritoryScrollPane);

        operationPanel.add(draft);
        operationPanel.add(attack);
        operationPanel.add(deploy);
        operationPanel.add(fortify);

        operationPanel.add(destinationTerritoryScrollPane);
        operationPanel.add(troopsBox);
        operationPanel.add(skipButton);
        operationPanel.setPreferredSize(new Dimension(150,600));


        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(newGameItem);
        menuBar.add(fileMenu);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(statusLabel,BorderLayout.NORTH);
        mainPanel.add(combinedMapPrintInfoPanel,BorderLayout.WEST);
        mainPanel.add(operationPanel,BorderLayout.CENTER);
        mainPanel.add(continentInfoPane,BorderLayout.EAST);


        this.setJMenuBar(menuBar);
        menuBar.setSize(50, 28);
        menuBar.setLocation(0,0);
        this.add(mainPanel);
        //mainPanel.setLocation(0,-1000);
        //mainPanel.add(menuBar);


        this.setLocation(50,50);
        this.setSize(1000,4000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }


    public int getNumberPlayerDialog(){
        int numberPlayer = 0;
        while(numberPlayer==0) {
            String input = JOptionPane.showInputDialog( "Please enter the number of players.(2-6)");
            if(!input.matches("[2-6]"))JOptionPane.showMessageDialog(null, "Invalid Input.", "Warning", JOptionPane.ERROR_MESSAGE);
            else {
                numberPlayer = Integer.parseInt(input);
            }
        }
        this.numberPlayer = numberPlayer;
        return numberPlayer;
    }


    public String[] popGetName(){
        String[] playerNameList = new String[6];
        for(int i=0 ;i<this.numberPlayer;i++){
            String name = JOptionPane.showInputDialog("Please enter Player "+ (i+1)+ " name");
            playerNameList[i] = name;
        }
         return playerNameList;
    }

    public void setContinentsLabel(String string){
        continentsLabel.setText(string);
    }

    public void addNewGameMenuListener(ActionListener e){
        newGameItem.addActionListener(e);
    }
}
