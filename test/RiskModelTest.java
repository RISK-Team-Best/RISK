import RISK.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for RiskModel
 */
public class RiskModelTest {
    private RiskModel riskModel;
    private Territory country1;
    private Player player1,player2;
    private String country1Name;
    private String mapName;
    private RiskView view;


    /**
     * Set up
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        riskModel = new RiskModel("OriginRiskMap");
        view = new RiskView(new Board("OriginRiskMap"));
        riskModel.addView(view);
        riskModel.setPlayerNum(2);
        riskModel.addPlayersName(new String[]{"Player_1", "Player_2"},new Boolean[]{false,false});
        riskModel.initialGame();
        riskModel.setCurrentStage(Stage.DRAFT);
        view.setStatusLabel("");


        player1 = riskModel.getCurrentPlayer();
        player2 = riskModel.getNextPlayer(player1.getID());


        country1 = player1.getTerritories().get(0);


        country1Name = country1.getName();
        mapName = "OriginRiskMap";
    }


    /**
     * test Draft() in RiskModel
     */
    @Test
    public void testDraft() {

        int originalTroops = country1.getTroops();
        player1.gainTroopsFromTerritory();
        int originalPlayerTroops = player1.getTroops();
        int moveTroops = 3;
        riskModel.draft(player1,country1Name,moveTroops);
        assertEquals((originalTroops + moveTroops),country1.getTroops());//territory troops increased
        assertEquals((originalPlayerTroops - moveTroops),player1.getTroops());//player's available troops decreased

    }

    /**
     *  test Battle() in RiskModel
     *  Test after battle, either attack country or defence country lost troops
     */
    @Test
    public void testBattle() {
        Territory attackCountry = riskModel.getAttackTerritoriesList(player1).get(0);
        Territory defenceCountry = riskModel.getDefenceTerritories(player1,attackCountry).get(0);
        int attackCountryTroops = attackCountry.getTroops();
        int defenceCountryTroops = defenceCountry.getTroops();
        riskModel.battle(attackCountry,defenceCountry,AttackWay.BLITZ);
        assertEquals(true,(attackCountryTroops > attackCountry.getTroops())||(defenceCountryTroops > defenceCountry.getTroops()));



        Territory attackCountry2 = riskModel.getAttackTerritoriesList(player2).get(0);
        Territory defenceCountry2 = riskModel.getDefenceTerritories(player2,attackCountry2).get(0);
        int attackCountry2Troops = attackCountry2.getTroops();
        int defenceCountry2Troops = defenceCountry2.getTroops();
        riskModel.battle(attackCountry2,defenceCountry2,AttackWay.ONE);
        assertEquals(true,(attackCountry2Troops > attackCountry2.getTroops())||(defenceCountry2Troops > defenceCountry2.getTroops()));


    }


    /**
     * test CompareDices() in RiskModel
     * test random number is between 0-6
     */
    @Test
    public void testCompareDices() {

        Territory attackCountry = riskModel.getAttackTerritoriesList(player1).get(0);
        Territory defenceCountry = riskModel.getDefenceTerritories(player1,attackCountry).get(0);
        Dices attackDice = new Dices(1);
        Dices defenceDice = new Dices(1);
        riskModel.compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        assertEquals(true,(attackDice.getIndexDice(0)>0)||(attackDice.getIndexDice(0)<=6));
        assertEquals(true,(defenceDice.getIndexDice(0)>0)||(defenceDice.getIndexDice(0)<=6));//test dice between 0-6

    }

    /**
     * test DeployTroops() in RiskModel
     * Assume the player1 win the battle and test the troop movement and the change of holder name
     */
    @Test
    public void testDeployTroops() {
        Territory attackCountry = riskModel.getAttackTerritoriesList(player1).get(0);
        Territory defenceCountry = riskModel.getDefenceTerritories(player1,attackCountry).get(0);
        int moveTroops = attackCountry.getTroops()-1;
        int originalDefenceTroops = defenceCountry.getTroops();
        riskModel.deployTroops(attackCountry,defenceCountry,moveTroops);
        assertEquals(1,attackCountry.getTroops());//original country troops decrease
        assertEquals(originalDefenceTroops+moveTroops,defenceCountry.getTroops());//defence country troops increase
        assertEquals(attackCountry.getHolder().getName(),defenceCountry.getHolder().getName());//holder name changed

    }


    /**
     *  test Fortify() in RiskModel
     *
     */
    @Test
    public void testFortify() {

        Territory fortifiedCountry = riskModel.getFortifyTerritories(player1).get(0);
        Territory fortifiedcountry = riskModel.getFortifiedTerritory(fortifiedCountry,player1).get(0);
        int moveTroops = fortifiedCountry.getTroops()-1;
        int originalTroops = fortifiedcountry.getTroops();
        riskModel.fortify(fortifiedCountry,fortifiedcountry,moveTroops);
        assertEquals(1,fortifiedCountry.getTroops());//original country troops decrease
        assertEquals(originalTroops+moveTroops,fortifiedcountry.getTroops());
        //destination country troops increase


        Territory fortifiedCountry2 = riskModel.getFortifyTerritories(player2).get(0);
        Territory fortifiedcountry2 = riskModel.getFortifiedTerritory(fortifiedCountry2,player2).get(0);
        int moveCountry2troops = fortifiedCountry2.getTroops()-1;
        int originalCountry2troops = fortifiedcountry2.getTroops();
        riskModel.fortify(fortifiedCountry2,fortifiedcountry2,moveCountry2troops);
        assertEquals(1,fortifiedCountry2.getTroops());//original country troops decrease
        assertEquals(originalCountry2troops+moveCountry2troops,fortifiedcountry2.getTroops());
        //destination country troops increase
    }

    @Test
    public void testHandleSaveAndLoadFileName() throws Exception {

        String filename = "testFile";
        riskModel.handleSaveByFileName(filename,mapName);
        view.dispose();

        XMLHandler handler = new XMLHandler(mapName);
        String fileName = filename + "_" + this.mapName;
        handler.importXMLFileByName(fileName);

        RiskModel testModel1 = handler.getModel();
        testModel1.reload();

        //test current stage
        assertEquals(Stage.DRAFT,riskModel.getCurrentStage());

        //test all territory successful load
        assertEquals(testModel1.getAllCountries().size(),riskModel.getAllCountries().size());

        assertEquals(testModel1.getCurrentPlayer().getTerritoriesString(),riskModel.getCurrentPlayer().getTerritoriesString());
        assertEquals(testModel1.getCurrentPlayer().getTroops(),riskModel.getCurrentPlayer().getTroops());

        assertEquals(testModel1.getNextPlayer(0).getTerritoriesString(),riskModel.getNextPlayer(0).getTerritoriesString());
        assertEquals(testModel1.getNextPlayer(0).getTroops(),riskModel.getNextPlayer(0).getTroops());

        //test player1 territory troops
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(0).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(0).getTroops());
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(1).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(1).getTroops());
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(2).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(2).getTroops());
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(3).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(3).getTroops());
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(4).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(4).getTroops());
        assertEquals(testModel1.getCurrentPlayer().getTerritories().get(5).getTroops(),riskModel.getCurrentPlayer().getTerritories().get(5).getTroops());


        //test player2 territory troops
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(0).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(0).getTroops());
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(1).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(1).getTroops());
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(2).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(2).getTroops());
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(3).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(3).getTroops());
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(4).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(4).getTroops());
        assertEquals(testModel1.getNextPlayer(0).getTerritories().get(5).getTroops(),riskModel.getNextPlayer(0).getTerritories().get(5).getTroops());

        //test player is correct
        assertEquals(2,testModel1.getNumberPlayers());//test player num saved and load successful
        assertEquals(0,testModel1.getCurrentPlayer().getID());//test player1 ID is 0
        assertEquals(1,testModel1.getNextPlayer(0).getID());//test player2 ID is 1
        assertEquals("Player_1",testModel1.getCurrentPlayer().getName());//test current player
        assertEquals("Player_2",testModel1.getNextPlayer(0).getName());//test current player


    }

    @Test
    public void testInvalidLoad() throws Exception {
        String invalid1 = "Invalid1";
        RiskModel testModel2 = new RiskModel(invalid1);
        assertEquals(true,testModel2.invalidMap());


        String invalid2 = "Invalid2";
        RiskModel testModel3 = new RiskModel(invalid2);
        assertEquals(true,testModel3.invalidMap());

        //test load Map1
        String map1 = "Map1";
        RiskModel testModel4 = new RiskModel(map1);

        testModel4.setPlayerNum(2);
        testModel4.addPlayersName(new String[]{"Player_3", "Player_4"},new Boolean[]{false,false});
        testModel4.initialGame();


        assertEquals(false,testModel4.invalidMap());
        assertEquals(41,testModel4.getAllCountries().size());//test Map1 territory all load to current model

        //test load Map2
        String map2 = "Map2";
        RiskModel testModel5 = new RiskModel(map2);

        testModel5.setPlayerNum(2);
        testModel5.addPlayersName(new String[]{"Player_5", "Player_6"},new Boolean[]{false,false});
        testModel5.initialGame();


        assertEquals(false,testModel5.invalidMap());
        assertEquals(39,testModel5.getAllCountries().size());//test Map1 territory all load to current model

    }

}


