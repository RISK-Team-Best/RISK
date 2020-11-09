import java.io.IOException;

public class RiskGame {
    public static void main(String[] args) throws IOException {
        RiskModel riskModel = new RiskModel();
        RiskView riskView = new RiskView();
        RiskController riskController = new RiskController(riskModel,riskView);
    }
}
