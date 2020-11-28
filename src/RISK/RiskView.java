package RISK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The View in the mvc
 */
public class RiskView extends JFrame implements RiskViewInterface{
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();
    private JLabel troopsLabel = new JLabel("Troops:");

    private JPanel mainPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel decideButtonPanel = new JPanel();

    private MapPanel graphMapPanel = new MapPanel();

    private JComboBox troopsBox = new JComboBox();

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

    private ArrayList<JButton> commandButtonList = new ArrayList<>();
    private ArrayList<JButton> territoryButtons = new ArrayList<>();

    private int numberPlayer = 0;

    private PlayerSettingDialog playerSettingDialog;

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

        this.commandButtonList.add(draft);
        this.commandButtonList.add(attack);
        this.commandButtonList.add(fortify);
        this.commandButtonList.add(deploy);
        this.commandButtonList.add(skipButton);
        this.commandButtonList.add(confirmButton);


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
            button.setOpaque(true);
            //Windows default border for JButton. Set it to make color in enabled button visible
            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
    }

    /**
     * This method is prompting to get the number of players.
     * @return the number of player
     */
    @Override
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
     * This method is prompting to get each player's name
     * @return the player's name string list
     */
    @Override
    public LinkedHashMap<String,Boolean> popGetName(){
        playerSettingDialog = new PlayerSettingDialog(this.numberPlayer);

        return playerSettingDialog.getFinalNamesAndIsAI();
    }

    /**
     * This method is a Setter for continentsLabel
     * @param string
     */
    public void setContinentsLabel(String string){
        continentsLabel.setText(string);
    }

    @Override
    public void updateWinAttack(Player currentPlayer) {
        deploy.setEnabled(true);
        confirmButton.setEnabled(false);
        skipButton.setEnabled(false);
        disableAllTerritoryButton();
        setStatusLabel(currentPlayer.getName()+" wins the battle! Press \"Deploy\" button to deploy troops.");
        clearTroopsBox();
    }

    /**
     *This method is getting the operating JButton
     *
     * @param buttonText the button text
     * @return the JButton that contain the buttonText
     */
    public JButton getJButton(String buttonText) {

        for (JButton button : commandButtonList){
            if (button.getText() == buttonText)
                return button;
        }
        return null;
    }

    /**
     * This method is setting status Label
     * @param str set the text in status label
     */
    @Override
    public void setStatusLabel(String str){
        statusLabel.setText(str);
    }

    @Override
    public void updateAIAttack(Player currentPlayer, Territory tempAttackTerritory, Territory tempDefenceTerritory) {
        setStatusLabel(currentPlayer.getName()+"'s turn, Attack Stage. "+tempAttackTerritory.getName()+" attacks "+tempDefenceTerritory.getName()+".");
        getTerritoryButtonByString(tempAttackTerritory.getName()).setBackground(Color.RED);
        getTerritoryButtonByString(tempDefenceTerritory.getName()).setBackground(Color.ORANGE);
    }

    @Override
    public void disableAllCommandButtons() {
        for(JButton button:commandButtonList){
            button.setEnabled(false);
        }
    }

    @Override
    public void updateAIFortify(Player currentPlayer,String fortifyTerritoryName, String fortifiedTerritoryName) {
        getTerritoryButtonByString(fortifyTerritoryName).setBackground(Color.RED);
        getTerritoryButtonByString(fortifiedTerritoryName).setBackground(Color.ORANGE);
        setStatusLabel(currentPlayer.getName()+"'s turn, Fortify Stage. Move troops from "+fortifyTerritoryName+" to "+fortifiedTerritoryName+".");
    }

    @Override
    public void updateDraftCountryClick(String territoryName) {
        onlyEnableOriginTerritory(territoryName);
        getTerritoryButtonByString(territoryName).setBackground(Color.RED);
    }


    /**
     * This method is setting the JComboBox with troops available in different stage.
     *
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
     * This method is setting the JComboBox with ways to attack.
     *
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
     * This method is clearing the troop box
     */
    public void clearTroopsBox(){
        troopsBox.removeAllItems();
    }

    /**
     * This method is getting the attackway for attack stage in JComboBox
     * @return the troop number in troops box that selected
     */
    @Override
    public AttackWay getAttackTroopsBox(){
        return (AttackWay) troopsBox.getSelectedItem();
    }




    /**
     * This method is setting the troops onto the territory button.
     * @param territoryName
     * @param troops
     */
    public void setTerritoryButtonTroops(String territoryName, int troops){
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName))button.setText(String.valueOf(troops));
        }
    }

    @Override
    public void updateNewGameProcess(String mapInfoThroughContinent, String currentPlayerName) {
        setContinentsLabel(mapInfoThroughContinent);
        draft.setEnabled(true);
        setStatusLabel("Now it's "+ currentPlayerName +"'s turn, please click \"Draft\" button to start DRAFT stage.");
        disableAllTerritoryButton();
    }

    /**
     * This method is for Disable all territory buttons
     */
    public void disableAllTerritoryButton(){
        for(JButton button:territoryButtons){
            button.setEnabled(false);
        }
    }

    /**
     *This methos is making the original territories button enabled
     * @param territories
     */
    @Override
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

    @Override
    public void updateDeployFinish(Player currentPlayer) {
        getJButton("Attack").setEnabled(true);
        getJButton("Confirm").setEnabled(false);
        getJButton("Skip").setEnabled(true);
        setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to continue ATTACK stage OR \"Skip\" button to skip to Fortify Stage.");
        disableAllTerritoryButton();
    }

    @Override
    public void updateFortifyFinish(Player currentPlayer) {
        draft.setEnabled(true);
        confirmButton.setEnabled(false);
        skipButton.setEnabled(false);
        attack.setEnabled(false);
        setStatusLabel(currentPlayer.getName()+ "'s turn, please click \"Draft\" button to start DRAFT stage.");
        disableAllTerritoryButton();
    }

    /**
     * Only enable one territory button and disable all others
     * @param territoryName
     */
    @Override
    public void onlyEnableOriginTerritory(String territoryName){
        disableAllTerritoryButton();
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName)){
                button.setEnabled(true);
                return;
            }
        }
    }

    @Override
    public void updateClickAttackTerritoryButton(int attackTerritoryTroops, ArrayList<Territory> defenceTerritories, String attackTerritoryName) {
        getTerritoryButtonByString(attackTerritoryName).setBackground(Color.RED);
        setAttackTroopsBox(attackTerritoryTroops);
        enableTargetTerritories(defenceTerritories, attackTerritoryName);
    }

    @Override
    public void updateClickTargetTerritoryButton(String originTerritoryName, String targetTerritoryName) {
        getTerritoryButtonByString(targetTerritoryName).setBackground(Color.ORANGE);
        disableAllTerritoryButton();
        enableTerritoryButton(originTerritoryName);
        enableTerritoryButton(targetTerritoryName);
    }

    @Override
    public void updateCancelDefenceTerritoryButton(String originTerritoryName, ArrayList<Territory> defenceTerritories) {
        getTerritoryButtonByString(originTerritoryName).setBackground(Color.RED);
        onlyEnableOriginTerritory(originTerritoryName);
        enableTargetTerritories(defenceTerritories, originTerritoryName);
    }

    @Override
    public void updateClickFortifyButton(int fortifyTroops, ArrayList<Territory> fortifiedTerritory, String originTerritoryName) {
        getTerritoryButtonByString(originTerritoryName).setBackground(Color.RED);
        setTroopsBox(fortifyTroops);
        enableTargetTerritories(fortifiedTerritory,originTerritoryName);
    }

    @Override
    public void updateCancelFortifyTerritoryButton(String originTerritoryName, ArrayList<Territory> fortifiedTerritory) {
        getTerritoryButtonByString(originTerritoryName).setBackground(Color.RED);
        onlyEnableOriginTerritory(originTerritoryName);
        enableTargetTerritories(fortifiedTerritory,originTerritoryName);
    }

    @Override
    public void updateAIDraft(Player currentPlayer, String continentBonusString, Territory draftTerritory) {
        setStatusLabel(currentPlayer.getName() + "'s turn, Draft stage. You have " + currentPlayer.getTroops() + " troops can be sent."+continentBonusString);
        getTerritoryButtonByString(draftTerritory.getName()).setBackground(Color.RED);
    }

    /**
     * This method is for enable one territory button without disable others
     *
     * @param territoryName the territory name
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
     * Enable the target territories buttons
     *
     * @param territories     the territories
     * @param originTerritory the origin territory
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

    /**
     * Get territory button by string j button.
     *
     * @param territoryName the territory name
     * @return the j button
     */
    public JButton getTerritoryButtonByString(String territoryName){
        for(JButton button:territoryButtons){
            if(button.getActionCommand().equals(territoryName))return button;
        }
        return null;
    }

    /**
     * paint the territory's button for the player
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

    @Override
    public void updateDraftPrepare(Player currentPlayer, Stage currentStage, String continentBonus) {
        confirmButton.setEnabled(true);
        draft.setEnabled(false);
        setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent."+continentBonus);
        setTroopsBox(currentPlayer.getTroops());
        enableOriginalTerritories(currentPlayer.getTerritories());
    }

    @Override
    public void updateAttackPrepare(Player currentPlayer, Stage currentStage, ArrayList<Territory> attackTerritoriesList) {
        attack.setEnabled(false);
        confirmButton.setEnabled(true);
        skipButton.setEnabled(true);
        enableOriginalTerritories(attackTerritoriesList);
        setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Click enabled territory button and pick surround available target territory button.");
    }

    @Override
    public void updateFortifyPrepare(ArrayList<Territory> fortifyTerritories, Player currentPlayer, Stage currentStage) {
        disableAllTerritoryButton();
        skipButton.setEnabled(true);
        fortify.setEnabled(false);
        confirmButton.setEnabled(true);
        enableOriginalTerritories(fortifyTerritories);
        setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Please choose the two Territories you want send troops from and to.");
    }

    @Override
    public void updateDeployPrepare(Player currentPlayer, Stage currentStage, int attackTroops) {
        deploy.setEnabled(false);
        attack.setEnabled(false);
        confirmButton.setEnabled(true);
        skipButton.setEnabled(false);
        setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. Set troops to the new earned territory.");
        setTroopsBox(attackTroops);
    }

    @Override
    public void updateSkipAttack(Player currentPlayer) {
        attack.setEnabled(false);
        fortify.setEnabled(true);
        confirmButton.setEnabled(false);
        skipButton.setEnabled(false);
        disableAllTerritoryButton();
        setStatusLabel(currentPlayer.getName() + "'s turn, Click \"Fortify\" button to start Fortify stage.");
    }

    @Override
    public void updateSkipFortify(Player currentPlayer) {
        fortify.setEnabled(false);
        skipButton.setEnabled(false);
        draft.setEnabled(true);
        confirmButton.setEnabled(false);
        setStatusLabel(currentPlayer.getName()+ "'s turn, please click \"Draft\" button to start DRAFT stage.");
        disableAllTerritoryButton();
    }

    @Override
    public void resetButtonsAndBox(ArrayList<Territory> attackTerritoriesList) {
        clearTroopsBox();
        disableAllTerritoryButton();
        enableOriginalTerritories(attackTerritoriesList);
    }

    /**
     * Get the selected troops in JComboBox and cast into int
     * @return the troops number that be selected
     */
    @Override
    public int getSelectedTroops(){
        return (int) troopsBox.getSelectedItem();
    }

    @Override
    public void updateDraftProcess(Player currentPlayer, Stage currentStage) {
        setStatusLabel(currentPlayer.getName() + "'s turn, " + currentStage.getName() + " stage. You have " + currentPlayer.getTroops() + " troops can be sent.");
        setTroopsBox(currentPlayer.getTroops());
    }

    @Override
    public void updateDraftFinish(Player currentPlayer) {
        disableAllTerritoryButton();
        confirmButton.setEnabled(false);
        attack.setEnabled(true);
        skipButton.setEnabled(false);
        setStatusLabel(currentPlayer.getName() +"'s turn, please click \"Attack\" button to start ATTACK stage.");

    }
    public String getFileName(){
        String file = JOptionPane.showInputDialog( "Please enter the file name");
        return file;

    }

    /**
     * Add new action listener for newGame button
     *
     * @param newGameListener the new game listener
     */
    public void addNewGameMenuListener(ActionListener newGameListener){
        newGameItem.addActionListener(newGameListener);
    }

    /**
     * Add new action listener for draft button
     *
     * @param draftButtonListener the draft button listener
     */
    public void addDraftButtonListener(ActionListener draftButtonListener){
        draft.addActionListener(draftButtonListener);
    }

    /**
     * Add new action listener for attack button
     *
     * @param attackButtonListener the attack button listener
     */
    public void addAttackButtonListener(ActionListener attackButtonListener){
        attack.addActionListener(attackButtonListener);
    }

    /**
    /**
     * Add new action listener for fortify button
     * @param fortifyButtonListener
     */
    public void addFortifyButtonListener(ActionListener fortifyButtonListener){
        fortify.addActionListener(fortifyButtonListener);
    }

    /**
     * Add new action listener for deploy button
     * @param deployButtonListener
     */
    public void addDeployButtonListener(ActionListener deployButtonListener){
        deploy.addActionListener(deployButtonListener);
    }

    /**
     * Add new action listener for skip button
     * @param skipButtonListener
     */
    public void addSkipButtonListener(ActionListener skipButtonListener){
        skipButton.addActionListener(skipButtonListener);
    }

    /**
     * Add new action listener for confirm button
     * @param confirmButtonListener
     */
    public void addConfirmButtonListener(ActionListener confirmButtonListener){
        confirmButton.addActionListener(confirmButtonListener);
    }


    /**
     * Add new action listener for territory Button
     * @param territoryButtonListener
     */
    public void addTerritoryButtonListener(ActionListener territoryButtonListener){
        for(JButton button:territoryButtons){
            button.addActionListener(territoryButtonListener);
        }
    }
    public void addSaveButtonListner(ActionListener saveButtonListner){
        saveItem.addActionListener(saveButtonListner);
    }
    public void addImportButtonListner(ActionListener importButtonListner){
        saveItem.addActionListener(importButtonListner);
    }




}


