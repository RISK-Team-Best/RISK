import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GmapReader {
    private Continent c;
    public GMap loadMap(File file) {
        GMap result = new GMap();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line;
            while ((line= bf.readLine())!=null)
            {
                String[] instruction = line.split(" ");
                if(instruction[0].equals("Continent:")){
                    c = new Continent(instruction[1]);
                    ContinentPanel continentPanel= new ContinentPanel(instruction[1],instruction[2],instruction[3],instruction[4],instruction[5]);
                    c.setPanel(continentPanel);
                    result.addContinent(c);
                }
                else {
                    Territory territory = new Territory(instruction[1]);
                    TerritoryButton territoryButton = new TerritoryButton(instruction[1],instruction[2],instruction[3],instruction[4],instruction[5]);
                    territory.setTerritoryButton(territoryButton);
                    territoryButton.setTerritory(territory);
                    result.addTerritory(territory,c);
                }
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
        return  result;
    }
    public void loadNeibour(GMap map,File file)
    {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bf.readLine()) != null) {
                String[] instruction = line.split(" ");
                Territory territory1 = map.getTerritory(instruction[0]);
                Territory territory2 = map.getTerritory(instruction[1]);
                territory1.setNeighbour(territory2);
                territory2.setNeighbour(territory1);
            }
        }catch (Exception e){}
    }
}
