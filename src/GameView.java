import javax.swing.*;

public class GameView extends JFrame {
    private Game model;
    private JSplitPane splitPane;
    private JLabel infoLabel;
    private GameController controller;
    private JPanel mapArea;
    public GameView(Game model,GameController controller)
    {
        super("RISK");
        this.model = model;
        this.controller = controller;
        setSize(1024,768);
        splitPane= new JSplitPane();
        add(splitPane);
        mapArea = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mapArea);
        splitPane.setLeftComponent(scrollPane);
        splitPane.setDividerLocation(768);
        JPanel controlArea = new JPanel();
        splitPane.setRightComponent(controlArea);
        //layouts
        controlArea.setLayout(new BoxLayout(controlArea,BoxLayout.Y_AXIS));
        mapArea.setLayout(null);

        for (Continent continent : model.getGameMap().getContinentArrayList())
        {
            ContinentPanel continentPanel = continent.getPanel();
            mapArea.add(continentPanel);
            continentPanel.setLayout(null);
            for(Territory territory : continent.getTerritoryArrayList())
            {
                TerritoryButton territoryButton = territory.getTerritoryButton();
                continent.getPanel().add(territoryButton);
                territoryButton.update();
                territoryButton.addActionListener(e -> {
                    model.handleButtonPressed(territoryButton);
                });

            }
        }
        //control area settings
        infoLabel = new JLabel();
        controlArea.add(infoLabel);
        JButton skip = new JButton("skip");
        controlArea.add(skip);
        skip.addActionListener(controller);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        infoLabel.setText("Welcome to RISK");
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public JPanel getMapArea() {
        return mapArea;
    }
}
