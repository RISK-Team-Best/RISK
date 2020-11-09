import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The View in the mvc
 */
public class RiskView extends JFrame {
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();
    private JLabel troopsLabel = new JLabel("Troops:");

    private JPanel mainPanel = new JPanel();
//    private JPanel graphMapPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel decideButtonPanel = new JPanel();

    private MapPanel graphMapPanel = new MapPanel();

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
    private ArrayList<JButton> territoryButtons = new ArrayList<>();

    private int numberPlayer = 0;

    /**
     * Initial frame
     */
    public RiskView(){
        super("Risk Game");

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

        territoryButtons.add(Alaska);
        territoryButtons.add(Alberta);
        territoryButtons.add(CentralAmerica);
        territoryButtons.add(EasternUnitedStates);
        territoryButtons.add(Greenland);
        territoryButtons.add(NorthwestTerritory);
        territoryButtons.add(Ontario);
        territoryButtons.add(Quebec);
        territoryButtons.add(WesternUnitedStates);
        territoryButtons.add(Argentina);
        territoryButtons.add(Brazil);
        territoryButtons.add(Venezuela);
        territoryButtons.add(GreatBritain);
        territoryButtons.add(Iceland);
        territoryButtons.add(NorthernEurope);
        territoryButtons.add(Scandinavia);
        territoryButtons.add(SouthernEurope);
        territoryButtons.add(Ukraine);
        territoryButtons.add(WesternEurope);
        territoryButtons.add(Congo);
        territoryButtons.add(EastAfrica);
        territoryButtons.add(Egypt);
        territoryButtons.add(Madagascar);
        territoryButtons.add(NorthAfrica);
        territoryButtons.add(SouthAfrica);
        territoryButtons.add(Afghanistan);
        territoryButtons.add(China);
        territoryButtons.add(India);
        territoryButtons.add(Irkutsk);
        territoryButtons.add(Japan);
        territoryButtons.add(Kamchatka);
        territoryButtons.add(MiddleEast);
        territoryButtons.add(Mongolia);
        territoryButtons.add(Siam);
        territoryButtons.add(Siberia);
        territoryButtons.add(Ural);
        territoryButtons.add(Yakutsk);
        territoryButtons.add(EasternAustralia);
        territoryButtons.add(Indonesia);
        territoryButtons.add(NewGuinea);
        territoryButtons.add(WesternAustralia);
        territoryButtons.add(Peru);



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

        troopsLabel.setAlignmentX(BOTTOM_ALIGNMENT);
        statusLabel.setFont(new Font("Arial",1,20));

        operationPanel.setLayout(new BoxLayout(operationPanel,BoxLayout.Y_AXIS));
        operationPanel.add(buttonPanel);
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

        Alaska.setActionCommand("Alaska");
        Alberta.setActionCommand("Alberta");
        CentralAmerica.setActionCommand("Central America");
        EasternUnitedStates.setActionCommand("Eastern United States");
        Greenland.setActionCommand("Greenland");
        NorthwestTerritory.setActionCommand("Northwest Territory");
        Ontario.setActionCommand("Ontario");
        Quebec.setActionCommand("Quebec");
        WesternUnitedStates.setActionCommand("Western United States");
        Argentina.setActionCommand("Argentina");
        Brazil.setActionCommand("Brazil");
        Venezuela.setActionCommand("Venezuela");
        GreatBritain.setActionCommand("Great Britain");
        Iceland.setActionCommand("Iceland");
        NorthernEurope.setActionCommand("Northern Europe");
        Scandinavia.setActionCommand("Scandinavia");
        SouthernEurope.setActionCommand("Southern Europe");
        Ukraine.setActionCommand("Ukraine");
        WesternEurope.setActionCommand("Western Europe");
        Congo.setActionCommand("Congo");
        EastAfrica.setActionCommand("East Africa");
        Egypt.setActionCommand("Egypt");
        Madagascar.setActionCommand("Madagascar");
        NorthAfrica.setActionCommand("North Africa");
        SouthAfrica.setActionCommand("South Africa");
        Afghanistan.setActionCommand("Afghanistan");
        China.setActionCommand("China");
        India.setActionCommand("India");
        Irkutsk.setActionCommand("Irkutsk");
        Japan.setActionCommand("Japan");
        Kamchatka.setActionCommand("Kamchatka");
        MiddleEast.setActionCommand("Middle East");
        Mongolia.setActionCommand("Mongolia");
        Siam.setActionCommand("Siam");
        Siberia.setActionCommand("Siberia");
        Ural.setActionCommand("Ural");
        Yakutsk.setActionCommand("Yakutsk");
        EasternAustralia.setActionCommand("Eastern Australia");
        Indonesia.setActionCommand("Indonesia");
        NewGuinea.setActionCommand("New Guinea");
        WesternAustralia.setActionCommand("Western Australia");
        Peru.setActionCommand("Peru");

        for(JButton button:territoryButtons){
            button.setMargin(new Insets(0,0,0,0));
            button.setFont(new Font("Arial",Font.BOLD,12));
        }
    }

    /**
     * @return the number of player
     */
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


    /**
     * @return the player's name string list
     */
    public String[] popGetName(){
        String[] playerNameList = new String[6];
        List<String> list = Arrays.asList(playerNameList);
        for(int i=0 ;i<this.numberPlayer;i++){
            String name = JOptionPane.showInputDialog("Please enter Player "+ (i+1)+ " name");
            if(name==null||list.contains(name)||name.equals("")){
                JOptionPane.showMessageDialog(null,"Cannot have repeat player's name! Or no name!","Name Repetition",JOptionPane.ERROR_MESSAGE);
                i--;
                continue;
            }
            playerNameList[i] = name;
        }
         return playerNameList;
    }

    /**
     * @param string
     */
    public void setContinentsLabel(String string){
        continentsLabel.setText(string);
    }

    /**
     * @param buttonText
     * @return the JButton that contain the buttonText
     */
    public JButton getJButton(String buttonText) {

        for (JButton button : CommandButtonList){
            if (button.getText() == buttonText)
                return button;
        }
        return null;
    }

    /**
     * @param str set the text in status label
     */
    public void setStatusLabel(String str){
        statusLabel.setText(str);
    }


    /**
     * @param troops set the numbers in troops box
     */
    public void setTroopsBox(int troops){
        troopsLabel.setText("Troops:");
        troopsBox.removeAllItems();
        for(int i=1; i<=troops; i++){
            troopsBox.addItem(i);
        }
    }

    /**
     * @param troops set the way to attack:one,two,three, and blitz
     */
    public void setAttackTroopsBox(int troops){
        troopsLabel.setText("Way to attack:");
        troopsBox.removeAllItems();
        troopsBox.addItem(AttackWay.BLITZ);
        troopsBox.addItem(AttackWay.ONE);
        if(troops>2)troopsBox.addItem(AttackWay.TWO);
        if(troops>3)troopsBox.addItem(AttackWay.THREE);
    }

    /**
     * clear the troop box
     */
    public void clearTroopsBox(){
        troopsBox.removeAllItems();
    }

    /**
     * @return the troop number in troops box that selected
     */
    public AttackWay getAttackTroopsBox(){
        return (AttackWay) troopsBox.getSelectedItem();
    }


    /**
     * @param territoryName
     * @param troops
     */
    public void setTerritoryButtonTroops(String territoryName, int troops){
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName))button.setText(String.valueOf(troops));
        }
    }

    /**
     * Disable all territory buttons
     */
    public void disableAllTerritoryButton(){
        for(JButton button:territoryButtons){
            button.setEnabled(false);
        }
    }

    /**
     * @param territories
     */
    public void enableOriginalTerritories(ArrayList<Territory> territories){
        for(Territory territory:territories){
            for(JButton button:territoryButtons){
                if(button.getActionCommand().equals(territory.getName())){
                    button.setEnabled(true);
                    break;
                }
            }
        }
    }

    /**
     * @param territoryName
     */
    public void onlyEnableOriginTerritory(String territoryName){
        disableAllTerritoryButton();
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName)){
                button.setEnabled(true);
                return;
            }
        }
    }

    /**
     * @param territoryName
     */
    public void enableTerritoryButton(String territoryName){
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName)){
                button.setEnabled(true);
                return;
            }
        }
    }

    /**
     * @param territories
     * @param originTerritory
     */
    public void enableTargetTerritories(ArrayList<Territory>territories, String originTerritory){
        onlyEnableOriginTerritory(originTerritory);
        for(Territory territory:territories){
            for(JButton button:territoryButtons){
                if(button.getActionCommand().equals(territory.getName())){
                    button.setEnabled(true);
                    break;
                }
            }
        }
    }

    public JButton getTerritoryButtonByString(String territoryName){
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName))return button;
        }
        return null;
    }

    /** paint the color for each player
     * @param player
     * @param color
     */
    public void paintTerritoryButtons(Player player,Color color){
        for(Territory territory:player.getTerritories()){
            for(JButton button:territoryButtons){
                if(territory.getName().equals(button.getActionCommand())){
                    button.setBackground(color);
                    continue;
                }
            }
        }
    }

    /**
     * @return the troops number that be selected
     */
    public int getSelectedTroops(){
        return (int) troopsBox.getSelectedItem();
    }

    /**
     * @param newGameListener
     */
    public void addNewGameMenuListener(ActionListener newGameListener){
        newGameItem.addActionListener(newGameListener);
    }

    /**
     * @param draftButtonListener
     */
    public void addDraftButtonListener(ActionListener draftButtonListener){
        draft.addActionListener(draftButtonListener);
    }

    /**
     * @param attackButtonListener
     */
    public void addAttackButtonListener(ActionListener attackButtonListener){
        attack.addActionListener(attackButtonListener);
    }

    /**
     * @param fortifyButtonListener
     */
    public void addFortifyButtonListener(ActionListener fortifyButtonListener){
        fortify.addActionListener(fortifyButtonListener);
    }

    /**
     * @param deployButtonListener
     */
    public void addDeployButtonListener(ActionListener deployButtonListener){
        deploy.addActionListener(deployButtonListener);
    }

    /**
     * @param skipButtonListener
     */
    public void addSkipButtonListener(ActionListener skipButtonListener){
        skipButton.addActionListener(skipButtonListener);
    }

    /**
     * @param confirmButtonListener
     */
    public void addConfirmButtonListener(ActionListener confirmButtonListener){
        confirmButton.addActionListener(confirmButtonListener);
    }


    /**
     * @param territoryButtonListener
     */
    public void addTerritoryButtonListener(ActionListener territoryButtonListener){
        for(JButton button:territoryButtons){
            button.addActionListener(territoryButtonListener);
        }
    }


}
