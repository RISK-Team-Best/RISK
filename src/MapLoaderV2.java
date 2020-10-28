import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MapLoaderV2 {
    private File mapFile;
    private File neighbourFile;
    private File continentFile;
    //private ImageIcon mapIcon;
    private static final int buttonSize = 30;
    public MapLoaderV2()
    {
        mapFile = new File(getClass().getClassLoader().getResource("TerritoryLocation.txt").getFile());
        neighbourFile = new File(getClass().getClassLoader().getResource("Neighbour.txt").getFile());
        continentFile = new File(getClass().getClassLoader().getResource("Continent.txt").getFile());
        //mapIcon = new ImageIcon(getClass().getClassLoader().getResource("RiskMap2.png"));
    }

    public GMap BuildMap()
    {
        GMap result = new GMap();
        try {//load all territories
            BufferedReader bf = new BufferedReader(new FileReader(mapFile));
            String line;
            while ((line = bf.readLine()) != null)
            {
                String[] instruction = line.split(",");
                Territory t = new Territory(instruction[0]);
                TerritoryButton territoryButton = new TerritoryButton
                        (instruction[0],Integer.valueOf(instruction[1])-buttonSize,
                                Integer.valueOf(instruction[2])-buttonSize/2,buttonSize*2,buttonSize);
                t.setTerritoryButton(territoryButton);
                territoryButton.setFont(new Font("Arial", Font.BOLD, 20));
                territoryButton.setTerritory(t);
                result.getTerritoryHashMap().put(t.getName(),t);
            }

            //load all continents
            bf = new BufferedReader(new FileReader(continentFile));
            String line2;
            while ((line2 = bf.readLine()) != null)
            {
                String[] instruction = line2.split(",");
                Continent c = new Continent(instruction[0]);
                c.setBonusTroops(Integer.valueOf(instruction[1]));
                result.addContinent(c);
                for(int i = 2;i<instruction.length;i++)
                {
                    result.addTerritory(result.getTerritory(instruction[i]),c);
                }
            }

            //linked all neighbour
            bf = new BufferedReader(new FileReader(neighbourFile));
            String line3;
            while ((line3 = bf.readLine()) != null) {
                String[] instruction = line3.split(",");
                for (int i = 1;i<instruction.length;i++)
                {
                    result.getTerritory(instruction[0]).setNeighbour(result.getTerritory(instruction[i]));
                }
            }



        }catch (Exception e){
            System.out.println(e);
        }
        return result;
    }

    public void testGui(GMap map)
    {
        JFrame jf = new JFrame();
        jf.setSize(1024,768);
        for(Territory t : map.getTerritoryArrayList())
        {
            jf.add(t.getTerritoryButton());

        }
        jf.setLayout(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MapLoaderV2 mapLoaderV2 = new MapLoaderV2();
        GMap map = mapLoaderV2.BuildMap();
        mapLoaderV2.testGui(map);

    }
}
