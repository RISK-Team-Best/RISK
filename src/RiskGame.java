import java.io.IOException;

/**
 * User Interface so that user can run this class to start the game
 */
public class RiskGame {
    public static void main(String[] args) throws IOException {
        RiskModel riskModel = new RiskModel();
        RiskView riskView = new RiskView();
        RiskController riskController = new RiskController(riskModel,riskView);
    }
}
