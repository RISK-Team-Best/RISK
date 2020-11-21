package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The type Board which read the files and create map informations.
 *
 * @Author: Guanquan Dong, 101093918
 *
 */
public class Board {

    private HashMap<String,Territory> countryHashMap;
    private HashMap<String,Continent> continentHashMap;
    private HashMap<String,ArrayList<Territory>> neighbors;

    /**
     * Instantiates a new Board. And get all information of a map through reading the input file
     *
     * @throws IOException the io exception
     */
    public Board()throws IOException{
        readCountryFile();
        readContinentFile();
        readNeighborFile();
    }

    /**
     * Read country file.
     *
     * @throws IOException the io exception that cannot read URL and file
     */
    public void readCountryFile() throws IOException {
        //URL countryFile = new URL("http://m.uploadedit.com/busd/1603223939868.txt");
        countryHashMap = new HashMap<>();
        BufferedReader scanner = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("country.txt")));
        String line;
        while((line = scanner.readLine())!=null){
            String str = line;
            countryHashMap.put(str,new Territory(str));
        }
        scanner.close();
    }

    /**
     * Read continent file.
     *
     * @throws IOException the io exception that cannot read URL and file
     */
    public void readContinentFile() throws IOException {
        continentHashMap = new HashMap<>();
        BufferedReader scanner = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Continent.txt")));
        String line;
        while((line = scanner.readLine())!=null){
            String[] array = line.split(",");
            ArrayList<Territory> tempCountries = new ArrayList<>();
            for(int i = 2; i< array.length;i++){
                tempCountries.add(new Territory(array[i]));
            }
            continentHashMap.put(array[0],new Continent(array[0],Integer.parseInt(array[1]),tempCountries));
        }
        scanner.close();
    }

    /**
     * Read neighbor file.
     *
     * @throws IOException the io exception that cannot read URL and file
     */
    public void readNeighborFile() throws IOException {
        neighbors = new HashMap<>();
        //URL neighborFile = new URL("http://m.uploadedit.com/busd/1603224004897.txt");
        //Scanner scanner = new Scanner(neighborFile.openStream());
        BufferedReader scanner = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Neighbour.txt")));
        String line;
        while((line = scanner.readLine())!=null){
            String[] array = line.split(",");
            ArrayList<Territory> neighborCountries =new ArrayList<>();
            for(int i=1; i< array.length;i++){
                neighborCountries.add(new Territory(array[i]));
            }
            neighbors.put(array[0],neighborCountries);
        }
        scanner.close();
    }

    /**
     * Gets all countries.
     *
     * @return the all countries in array list
     */
    public ArrayList<Territory> getAllCountries() {
        return new ArrayList<>(countryHashMap.values());
    }

    /**
     * Get all continents array list.
     *
     * @return array list of all continents
     */
    public ArrayList<Continent> getAllContinents(){
        return new ArrayList<>(continentHashMap.values());
    }

    /**
     * Get all neighbors in an array list of a territory through territory's name.
     *
     * @param countryName the territory's name
     * @return the array list of all neighbors of this territory.
     */
    public ArrayList<Territory> getAllNeighbors(String countryName){
        return neighbors.get(countryName);
    }


}