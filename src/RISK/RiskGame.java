package RISK;

/**
 * User Interface so that user can run this class to start the game
 */
public class RiskGame {
    public static void main(String[] args) throws Exception {
        RiskModel riskModel = new RiskModel();
        RiskView riskView = new RiskView(new Board("OriginRiskMap"));
        RiskController riskController = new RiskController(riskModel,riskView);
    }
}
