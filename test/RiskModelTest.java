
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RiskModelTest {
    private RiskModel riskModel;
    private Territory country1,country2;
    private Player player1,player2;
    private String country1Name,country2Name;



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





    }


    @Test
    public void testDraft() {

        int originalTroops = country1.getTroops();
        player1.gainTroopsFromTerritory();
        int originalPlayerTroops = player1.getTroops();
        int moveTroops = 3;
        riskModel.draft(player1,country1Name,moveTroops);
        assertEquals((originalTroops + moveTroops),country1.getTroops());
        assertEquals((originalPlayerTroops - moveTroops),player1.getTroops());

    }

    @Test
    public void testbattle() {
        //Territory attackCountry = riskModel.getAttackTerritoriesList(player1).get(0);
        //Territory defenceCountry = riskModel.getDefenceTerritories(player1,attackCountry).get(0);
        //boolean battleWin = riskModel.battle(attackCountry,defenceCountry,AttackWay.BLITZ);
        //assertEquals(battleWin);

    }


    @Test
    public void testCompareDices() {

    }

    @Test
    public void testDeployTroops() {

        //assume the player1 win the battle and test the troop movement and the change of holder name
        Territory attackCountry = riskModel.getAttackTerritoriesList(player1).get(0);
        Territory defenceCountry = riskModel.getDefenceTerritories(player1,attackCountry).get(0);
        int movetroops = attackCountry.getTroops()-1;
        int originalDefenceTroops = defenceCountry.getTroops();
        //int originalAttackTroops = attackCountry.getTroops();
        riskModel.deployTroops(attackCountry,defenceCountry,movetroops);
        assertEquals(1,attackCountry.getTroops());
        assertEquals(originalDefenceTroops+movetroops,defenceCountry.getTroops());
        //assertEquals(originalAttackTroops - movetroops,attackCountry.getTroops());
        assertEquals(attackCountry.getHolder().getName(),defenceCountry.getHolder().getName());

    }


    @Test
    public void testFortify() {
        Territory fortifycounrty = riskModel.getFortifyTerritories(player1).get(0);
        Territory fortifiedcountry = riskModel.getFortifiedTerritory(fortifycounrty,player1).get(0);
        int movetroops = fortifycounrty.getTroops()-1;
        int origianltroops = fortifiedcountry.getTroops();
        riskModel.fortify(fortifycounrty,fortifiedcountry,movetroops);
        assertEquals(1,fortifycounrty.getTroops());
        assertEquals(origianltroops+movetroops,fortifiedcountry.getTroops());
    }



    //@Test
    //public void testCheckWinner() {
    //}




}


