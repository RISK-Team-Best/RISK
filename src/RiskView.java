import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RiskView extends JFrame {
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();
    private JLabel originTerritoryLabel = new JLabel("Origin Territory:");
    private JLabel targetTerritoryLabel = new JLabel("Target Territory:");
    private JLabel troopsLabel = new JLabel("Troops:");

    private JPanel mainPanel = new JPanel();
//    private JPanel graphMapPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel decideButtonPanel = new JPanel();

    private MapPanel graphMapPanel = new MapPanel();

    private JList<Territory> startingTerritory = new JList<>();
    private JList<Territory> destinationTerritory = new JList<>();

    private JComboBox troopsBox = new JComboBox();

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
    private JButton skipButton = new JButton("Skip");
    private JButton confirmButton = new JButton("Confirm");

    private JButton Alaska = new JButton();
    private JButton Alberta = new JButton();
    private JButton CentralAmerica = new JButton();
    private JButton EasternUnitedStates = new JButton();
    private JButton Greenland = new JButton();
    private JButton NorthwestTerritory = new JButton();
    private JButton Ontario = new JButton();
    private JButton Quebec = new JButton();
    private JButton WesternUnitedStates = new JButton();
    private JButton Argentina = new JButton();
    private JButton Brazil = new JButton();
    private JButton Venezuela = new JButton();
    private JButton GreatBritain = new JButton();
    private JButton Iceland = new JButton();
    private JButton NorthernEurope = new JButton();
    private JButton Scandinavia = new JButton();
    private JButton SouthernEurope = new JButton();
    private JButton Ukraine = new JButton();
    private JButton WesternEurope = new JButton();
    private JButton Congo = new JButton();
    private JButton EastAfrica = new JButton();
    private JButton Egypt = new JButton();
    private JButton Madagascar = new JButton();
    private JButton NorthAfrica = new JButton();
    private JButton SouthAfrica = new JButton();
    private JButton Afghanistan = new JButton();
    private JButton China = new JButton();
    private JButton India = new JButton();
    private JButton Irkutsk = new JButton();
    private JButton Japan = new JButton();
    private JButton Kamchatka = new JButton();
    private JButton MiddleEast = new JButton();
    private JButton Mongolia = new JButton();
    private JButton Siam = new JButton();
    private JButton Siberia = new JButton();
    private JButton Ural = new JButton();
    private JButton Yakutsk = new JButton();
    private JButton EasternAustralia = new JButton();
    private JButton Indonesia = new JButton();
    private JButton NewGuinea = new JButton();
    private JButton WesternAustralia = new JButton();
    private JButton Peru = new JButton();

    private ArrayList<JButton> CommandButtonList = new ArrayList<>();
    private ArrayList<JButton> TerritoryButtons = new ArrayList<>();

    private int numberPlayer = 0;

    public RiskView(){
        super("Risk Game");
        /*try {
            RiskController controller= new RiskController();
        }catch (Exception e){

        }*/

        startingTerritory.setLayoutOrientation(JList.VERTICAL);
        startingTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        startingTerritoryScrollPane = new JScrollPane(startingTerritory);

        destinationTerritory.setLayoutOrientation(JList.VERTICAL);
        destinationTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        destinationTerritoryScrollPane = new JScrollPane(destinationTerritory);

        graphMapPanel.setPreferredSize(new Dimension(850,589));
        graphMapPanel.setLayout(null);

        graphMapPanel.add(Alaska);
        graphMapPanel.add(Alberta);
        graphMapPanel.add(CentralAmerica);
        graphMapPanel.add(EasternUnitedStates);
        graphMapPanel.add(Greenland);
        graphMapPanel.add(NorthwestTerritory);
        graphMapPanel.add(Ontario);
        graphMapPanel.add(Quebec);
        graphMapPanel.add(WesternUnitedStates);
        graphMapPanel.add(Argentina);
        graphMapPanel.add(Brazil);
        graphMapPanel.add(Venezuela);
        graphMapPanel.add(GreatBritain);
        graphMapPanel.add(Iceland);
        graphMapPanel.add(NorthernEurope);
        graphMapPanel.add(Scandinavia);
        graphMapPanel.add(SouthernEurope);
        graphMapPanel.add(Ukraine);
        graphMapPanel.add(WesternEurope);
        graphMapPanel.add(Congo);
        graphMapPanel.add(EastAfrica);
        graphMapPanel.add(Egypt);
        graphMapPanel.add(Madagascar);
        graphMapPanel.add(NorthAfrica);
        graphMapPanel.add(SouthAfrica);
        graphMapPanel.add(Afghanistan);
        graphMapPanel.add(China);
        graphMapPanel.add(India);
        graphMapPanel.add(Irkutsk);
        graphMapPanel.add(Japan);
        graphMapPanel.add(Kamchatka);
        graphMapPanel.add(MiddleEast);
        graphMapPanel.add(Mongolia);
        graphMapPanel.add(Siam);
        graphMapPanel.add(Siberia);
        graphMapPanel.add(Ural);
        graphMapPanel.add(Yakutsk);
        graphMapPanel.add(EasternAustralia);
        graphMapPanel.add(Indonesia);
        graphMapPanel.add(NewGuinea);
        graphMapPanel.add(WesternAustralia);
        graphMapPanel.add(Peru);




        Alaska .setBounds(50,65,30,30);
        Alberta.setBounds(135,107,30,30);
        CentralAmerica.setBounds(140,275,30,30);
        EasternUnitedStates.setBounds(160,210,30,30);
        Greenland.setBounds(280,68,30,30);
        NorthwestTerritory.setBounds(183,58,30,30);
        Ontario.setBounds(183,118,30,30);
        Quebec.setBounds(215,153,30,30);
        WesternUnitedStates.setBounds(149,170,30,30);
        Argentina.setBounds(220,440,30,30);
        Brazil.setBounds(265,380,30,30);
        Venezuela.setBounds(185,320,30,30);
        GreatBritain.setBounds(335,150,30,30);
        Iceland .setBounds(355,82,30,30);
        NorthernEurope.setBounds(385,190,30,30);
        Scandinavia.setBounds(410,80,30,30);
        SouthernEurope.setBounds(420,240,30,30);
        Ukraine.setBounds(500,120,30,30);
        WesternEurope.setBounds(365,222,30,30);
        Congo.setBounds(445,389,30,30);
        EastAfrica.setBounds(480,353,30,30);
        Egypt.setBounds(445,290,30,30);
        Madagascar.setBounds(530,450,30,30);
        NorthAfrica.setBounds(365,320,30,30);
        SouthAfrica.setBounds(455,500,30,30);
        Afghanistan.setBounds(555,222,30,30);
        China.setBounds(696,255,30,30);
        India.setBounds(580,260,30,30);
        Irkutsk.setBounds(668,110,30,30);
        Japan.setBounds(770,165,30,30);
        Kamchatka.setBounds(745,55,30,30);
        MiddleEast.setBounds(505,290,30,30);
        Mongolia.setBounds(695,166,30,30);
        Siam.setBounds(668,325,30,30);
        Siberia.setBounds(610,103,30,30);
        Ural.setBounds(575,150,30,30);
        Yakutsk.setBounds(680,45,30,30);
        EasternAustralia.setBounds(790,495,30,30);
        Indonesia.setBounds(680,420,30,30);
        NewGuinea.setBounds(760,360,30,30);
        WesternAustralia.setBounds(720,453,30,30);
        Peru.setBounds(175,360,30,30);



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
        statusLabel.setFont(new Font("Arial",1,20));

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

        this.CommandButtonList.add(draft);
        this.CommandButtonList.add(attack);
        this.CommandButtonList.add(fortify);
        this.CommandButtonList.add(deploy);
        this.CommandButtonList.add(skipButton);
        this.CommandButtonList.add(confirmButton);


        this.setLocation(50,50);
        this.setSize(1000,4000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        Alaska.setName("Alaska");
        Alberta.setName("Alberta");
        CentralAmerica.setName("CentralAmerica");
        EasternUnitedStates.setName("EasternUnitedStates");
        Greenland.setName("Greenland");
        NorthwestTerritory.setName("NorthwestTerritory");
        Ontario.setName("Ontario");
        Quebec.setName("Quebec");
        WesternUnitedStates.setName("WesternUnitedStates");
        Argentina.setName(" Argentina");
        Brazil.setName(" Brazil");
        Venezuela.setName("Venezuela");
        GreatBritain.setName("GreatBritain");
        Iceland.setName("Iceland");
        NorthernEurope.setName("NorthernEurope");
        Scandinavia.setName("Scandinavia");
        SouthernEurope.setName("SouthernEurope");
        Ukraine.setName("Ukraine");
        WesternEurope.setName("WesternEurope");
        Congo.setName("Congo");
        EastAfrica.setName("EastAfrica");
        Egypt.setName("Egypt");
        Madagascar.setName("Madagascar");
        NorthAfrica.setName("NorthAfrica");
        SouthAfrica.setName("SouthAfrica");
        Afghanistan.setName("Afghanistan");
        China.setName("China");
        India.setName("India");
        Irkutsk.setName("Irkutsk");
        Japan.setName("Japan");
        Kamchatka.setName("Kamchatka");
        MiddleEast.setName("MiddleEast");
        Mongolia.setName("Mongolia");
        Siam.setName("Siam");
        Siberia.setName("Siberia");
        Ural.setName("Ural");
        Yakutsk.setName("Yakutsk");
        EasternAustralia.setName("EasternAustralia");
        Indonesia.setName("Indonesia");
        NewGuinea.setName("NewGuinea");
        WesternAustralia.setName("WesternAustralia");
        Peru.setName("Peru");


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

        for (JButton button : CommandButtonList){
            if (button.getText() == buttonText)
                return button;
        }
        return null;
    }
    public void setStatusLabel(String str){
        statusLabel.setText(str);
    }


    public void setTroopsBox(int troops){
        troopsLabel.setText("Troops:");
        troopsBox.removeAllItems();
        for(int i=1; i<=troops; i++){
            troopsBox.addItem(i);
        }
    }

    public void setAttackTroopsBox(int troops){
        troopsLabel.setText("Way to attack:");
        troopsBox.removeAllItems();
        troopsBox.addItem(AttackWay.BLITZ);
        troopsBox.addItem(AttackWay.ONE);
        if(troops>1)troopsBox.addItem(AttackWay.TWO);
        if(troops>2)troopsBox.addItem(AttackWay.THREE);
    }

    public AttackWay getAttackTroopsBox(){
        return (AttackWay) troopsBox.getSelectedItem();
    }

    public void setStartingTerritory(DefaultListModel<Territory> listModel){
        startingTerritory.setModel(listModel);
        startingTerritory.setSelectedIndex(0);
    }

    public Territory getStartingTerritory() {
        return startingTerritory.getSelectedValue();
    }

    public void setDestinationTerritory(DefaultListModel<Territory> listModel){
        destinationTerritory.setModel(listModel);
        destinationTerritory.setSelectedIndex(0);
    }

    public Territory getDestinationTerritory() {
        return destinationTerritory.getSelectedValue();
    }
    public void clearDestinationTerritory() {
        destinationTerritory.setModel(null);
    }

    public int getSelectedTroops(){
        return (int) troopsBox.getSelectedItem();
    }

    public void addNewGameMenuListener(ActionListener newGameListener){
        newGameItem.addActionListener(newGameListener);
    }
    public void addDraftButtonListener(ActionListener draftButtonListener){
        draft.addActionListener(draftButtonListener);
    }
    public void addAttackButtonListener(ActionListener attackButtonListener){
        attack.addActionListener(attackButtonListener);
    }
    public void addFortifyButtonListener(ActionListener fortifyButtonListener){
        fortify.addActionListener(fortifyButtonListener);
    }
    public void addDeployButtonListener(ActionListener deployButtonListener){
        deploy.addActionListener(deployButtonListener);
    }
    public void addSkipButtonListener(ActionListener skipButtonListener){
        skipButton.addActionListener(skipButtonListener);
    }
    public void addConfirmButtonListener(ActionListener confirmButtonListener){
        confirmButton.addActionListener(confirmButtonListener);
    }

    public void addStartingTerritoryListListener(ListSelectionListener listSelectionListener){
        startingTerritory.addListSelectionListener(listSelectionListener);
    }



}
