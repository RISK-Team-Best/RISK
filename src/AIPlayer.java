//public class AIPlayer extends Player{
//    private String decision;
//    private Board board;
//    /**
//     * Instantiates a new Player.
//     *
//     * @param name the player's name
//     */
//    public AIPlayer(String name,Board board) {
//        super(name);
//        this.board = board;
//    }
//
//    public void processDecision()
//    {
//        decision = decision;
//    }
//
//    public void draftDecision()
//    {
//        decision =  "draft "+maxTerritory()[0].getName()+ " "+getTroops();
//        processDecision();
//    }
//
//    public void attackDecision()
//    {
//        Territory[] temp = maxTerritory();
//        decision =  "attack "+temp[0].getName()+ " "+temp[1];
//        processDecision();
//    }
//
//    public void fortifyDecision()
//    {
//        decision =  "fortify "+maxTerritory()[2].getName()+ "???";
//        processDecision();
//    }
//
//    public Territory[] maxTerritory ()
//    {
//        int max = 0;
//        int temp = 0;
//        int min = 0;
//        Territory[] maxT = new Territory[3];
//        maxT[0] = getTerritories().get(0);//兵力差最大的
//        maxT[2] = getTerritories().get(0);//兵力差最小的
//        for (Territory t : getTerritories())
//        {
//            for (Territory nei : board.getAllNeighbors(t.getName()))
//            {
//                temp = t.getTroops()-nei.getTroops();
//                if (temp>=max && nei.getHolder()!=this)
//                {
//                    max = temp;
//                    maxT[0] = t;
//                    maxT[1] = nei;//兵力差最大的敌国
//                }
//
//                if (temp<=min && nei.getHolder()!=this)
//                {
//                    min = temp;
//                    maxT[2] = t;
//                }
//            }
//
//        }return maxT;
//
//    }
//}
