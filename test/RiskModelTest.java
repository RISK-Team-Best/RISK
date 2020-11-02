//import org.junit.Before;
//import org.testng.annotations.Test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RiskModelTest {
    private RiskModel riskModel;
    private Territory country1,country2;
    private Player player1,player2;
    private String country1Name,country2Name;
    private int moveTroop = 0;
    private int country1Troops,country2Troops;


    @Before
    public void setUp() throws Exception {
        riskModel = new RiskModel();
        riskModel.setPlayerNum(2);
        riskModel.addPlayersName(new String[]{"Player_1", "Player_2"});
        riskModel.initialGame();


        player1 = riskModel.getCurrentPlayer();
        player2 = riskModel.getNextPlayer();

        country1 = player1.getTerritories().get(0);
        country2 = player2.getTerritories().get(0);

        country1Name = country1.getName();
        country2Name = country2.getName();

        country1Troops = country1.getTroops();
        country2Troops = country1.getTroops();


        moveTroop = 3;


    }


    @Test
    public void testProcessGaming() {
    }

    @Test
    public void testAddPlayersName() {
    }

    @Test
    public void testSetInitialTroopHashMap() {
    }

    @Test
    public void testSetTroopsInitially() {
    }

    @Test
    public void testAssignCountriesRandomly() {
    }

    @Test
    public void testCheckContinent() {
    }

    @Test
    public void testAssignTroops() {
    }

    @Test
    public void testDraft() {

        int originalTroops = country1.getTroops();
        player1.gainTroopsFromTerritory();
        int originalPlayerTroops = player1.getTroops();
        riskModel.draft(player1,country1Name,moveTroop);
        assertEquals((originalTroops + moveTroop),country1.getTroops());
        assertEquals((originalPlayerTroops - moveTroop),player1.getTroops());

    }

    @Test
    public void testAttack() {
    }

    @Test
    public void testAttackStage() {
    }

    @Test
    public void testPrintAttackableInfo() {
    }

    @Test
    public void testSetAttackTerritories() {
    }

    @Test
    public void testSetDefenceTerritories() {
    }

    @Test
    public void testBattle() {
    }

    @Test
    public void testBlitz() {
    }

    @Test
    public void testCompareDices() {
    }

    @Test
    public void testDeployTroops() {
    }

    @Test
    public void testSetFortifyTerritory() {
    }

    @Test
    public void testSetFortifiableTerritory() {
    }

    @Test
    public void testFortify() {
    }

    @Test
    public void testAddNeighborCountries() {
    }

    @Test
    public void testCheckWinner() {
    }

    @Test
    public void testUpdateContinentListInfo() {
    }

    @Test
    public void testSetPlayerNum() {
    }

    @Test
    public void testGetPlayerNum() {
    }

    @Test
    public void testGetAllContinents() {
    }

    @Test
    public void testGetCurrentPlayer() {
    }

    @Test
    public void testGetNextPlayer() {
    }

    @Test
    public void testSetCurrentPlayer() {
    }

    @Test
    public void testGetMapInfoThroughContinent() {
    }

    @Test
    public void testGetTerritoryByString() {
    }
}