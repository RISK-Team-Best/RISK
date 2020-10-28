import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

public class RiskView extends JFrame {
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();
    private JLabel originTerritoryLabel = new JLabel("Origin Territory:");
    private JLabel targetTerritoryLabel = new JLabel("Target Territory:");
    private JLabel troopsLabel = new JLabel("Troops:");

    private JPanel mainPanel = new JPanel();
    private JPanel graphMapPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel decideButtonPanel = new JPanel();

    private JList startingTerritory = new JList();
    private JList destinationTerritory = new JList();

    private JComboBox troopsBox = new JComboBox();

    //private JButton functionButton = new JButton();
    private JButton skipButton = new JButton("Skip");
    private JButton confirmButton = new JButton("Confirm");

    private JScrollPane startingTerritoryScrollPane;
    private JScrollPane destinationTerritoryScrollPane;
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

    private ArrayList<JButton> JButtonList = new ArrayList<JButton>();

    private int numberPlayer = 0;

    public RiskView(){
        super("Risk Game");

//        statusLabel.setText("It's my turn.");

        startingTerritory.setLayoutOrientation(JList.VERTICAL);
        startingTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        startingTerritoryScrollPane = new JScrollPane(startingTerritory);

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

        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.add(draft);
        buttonPanel.add(attack);
        buttonPanel.add(deploy);
        buttonPanel.add(fortify);

        decideButtonPanel.setLayout(new GridLayout(1,2));
        decideButtonPanel.add(confirmButton);
        decideButtonPanel.add(skipButton);

        originTerritoryLabel.setAlignmentX(BOTTOM_ALIGNMENT);
        targetTerritoryLabel.setAlignmentX(BOTTOM_ALIGNMENT);
        troopsLabel.setAlignmentX(BOTTOM_ALIGNMENT);
        statusLabel.setFont(new Font("Arial",1,12));

        operationPanel.setLayout(new BoxLayout(operationPanel,BoxLayout.Y_AXIS));
        operationPanel.add(buttonPanel);
        operationPanel.add(originTerritoryLabel);
        operationPanel.add(startingTerritoryScrollPane);
        operationPanel.add(targetTerritoryLabel);
        operationPanel.add(destinationTerritoryScrollPane);
        operationPanel.add(troopsLabel);
        operationPanel.add(troopsBox);

        operationPanel.add(decideButtonPanel);
        operationPanel.setPreferredSize(new Dimension(200,600));


        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(newGameItem);
        menuBar.add(fileMenu);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(statusLabel,BorderLayout.NORTH);
        mainPanel.add(graphMapPanel,BorderLayout.WEST);
        mainPanel.add(operationPanel,BorderLayout.CENTER);
        mainPanel.add(continentInfoPane,BorderLayout.EAST);

         attack.setEnabled(false);
         draft.setEnabled(false);
         fortify.setEnabled(false);
         deploy.setEnabled(false);
         skipButton.setEnabled(false);
         confirmButton.setEnabled(false);

        statusLabel.setText("To start a new game: File -> New.");
        this.setJMenuBar(menuBar);
        menuBar.setSize(50, 28);
        this.add(mainPanel);

        this.JButtonList.add(draft);
        this.JButtonList.add(attack);
        this.JButtonList.add(fortify);
        this.JButtonList.add(deploy);
        this.JButtonList.add(skipButton);
        this.JButtonList.add(confirmButton);


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
    public JButton getJButton(String buttonText) {

        for (JButton button : JButtonList){
            if (button.getText() == buttonText)
                return button;
        }
        return null;
    }
    public void setStatusLabel(String str){
        statusLabel.setText(str);
    }


    public void addNewGameMenuListener(ActionListener e){
        newGameItem.addActionListener(e);
    }
    public void addDraftButtonListener(ActionListener e){
        draft.addActionListener(e);
    }
    public void addAttackButtonListener(ActionListener e){
        attack.addActionListener(e);
    }
    public void addFortifyButtonListener(ActionListener e){
        fortify.addActionListener(e);
    }
    public void addDeployButtonListener(ActionListener e){
        deploy.addActionListener(e);
    }
    public void addSkipButtonListener(ActionListener e){
        skipButton.addActionListener(e);
    }
    public void addConfirmButtonListener(ActionListener e){
        skipButton.addActionListener(e);
    }
}
