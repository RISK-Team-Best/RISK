package RISK;

import java.util.ArrayList;

public class GameComponentFactory {
    public static Player createPlayer(String name, String troops, String ID,
                                      String isAI, ArrayList<Territory> ownTerritory,ArrayList<Continent> ownContinents,RiskModel model) {
        if(isAI.equals("true"))
        {
            AIPlayer newPlayer = new AIPlayer(name,model);
            configPlayer(troops, ID, ownTerritory, ownContinents, newPlayer);
            return newPlayer;
        }else if (isAI.equals("false"))
        {
            Player newPlayer = new Player(name,model);
            configPlayer(troops, ID, ownTerritory, ownContinents, newPlayer);
            return newPlayer;
        }else {
            System.out.println("invalid info");
            return null;
        }
    }

    private static void configPlayer(String troops, String ID, ArrayList<Territory> ownTerritory, ArrayList<Continent> ownContinents, Player newPlayer) {
        newPlayer.setTroops(Integer.parseInt(troops));
        newPlayer.setID(Integer.parseInt(ID));
        for(Territory t : ownTerritory)
        {
            newPlayer.addTerritory(t);
        }
        for(Continent c : ownContinents)
        {
            newPlayer.addContinent(c);
        }
    }

    public static Territory createTerritory(String name,String troops,Player holder,RiskModel model){
        Territory newTerritory = model.getTerritoryByString(name);
        newTerritory.setTroops(Integer.parseInt(troops));
        newTerritory.setHolder(holder);
        return newTerritory;

    }

    public static Continent createContinent(String name,String bonus,RiskModel model){
        return null;
    }
}
