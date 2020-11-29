package RISK;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class Player store the information of the player's territory and calculate the troops each turn.
 *
 */
public class Player implements Serializable {
    protected String name;
    protected int troops;
    protected HashMap<String,Territory> territories;
    protected HashMap<String,Continent> continents;
    protected int id;
//    private boolean AI;
    protected final int leastBonusTroops = 3;
    protected final int bonusUnits = 3;
    protected RiskModel model;

    /**
     * Instantiates a new Player.
     *
     * @param name the player's name
     */
    public Player(String name,RiskModel model){
        this.name = name;
        territories = new HashMap<>();
        continents = new HashMap<>();
        this.model = model;
//        this.AI = AI;
    }

    /**
     * This method is setting troops.
     *
     * @param troops the troops
     */
    public void setTroops(int troops){
        this.troops = troops;
    }

    /**
     * This method is getting name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method is getting troops.
     *
     * @return the troops
     */
    public int getTroops() {
        return troops;
    }

    /**
     * This method is getting all territories of this player.
     *
     * @return the territories of this player in an arraylist
     */
    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(territories.values());
    }

    /**
     * Get territories string array list.
     *
     * @return the array list
     */
    public ArrayList<String> getTerritoriesString(){
        ArrayList<String> territoriesString = new ArrayList<>(territories.keySet());
        return territoriesString;
    }

    /**
     * This method is getting all continents of this player.
     *
     * @return the continents of this player in array list
     */
    public ArrayList<Continent> getContinents() {
        return new ArrayList<>(continents.values());
    }

    /**
     * This method is adding territory which belongs to this player.
     *
     * @param territory the territory
     */
    public void addTerritory(Territory territory){
        territories.put(territory.getName(),territory);
    }

    /**
     * This method is adding continent which belongs to this player.
     *
     * @param continent the continent
     */
    public void addContinent(Continent continent){
        continents.put(continent.getName(),continent);
    }

    /**
     * This method is removing territory.
     *
     * @param territory the territory
     */
    public void removeTerritory(Territory territory){
        territories.remove(territory.getName());
    }

    /**
     * This method is removing continent.
     *
     * @param continent the continent
     */
    public void removeContinent(Continent continent){
        continents.remove(continent.getName());
    }

    /**
     * This method is increasing the troops.
     *
     * @param increase the increase of troops
     */
    public void increaseTroop(int increase){
        troops += increase;
    }

    /**
     * This method is decreasing the troops.
     *
     * @param decrease the decrease of troops
     */
    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    /**
     * This methods is checking this player has continent(s) or not.
     *
     * @return the boolean, true if have continent.
     */
    public boolean haveContinent(){
        return !continents.isEmpty();
    }

    /**
     * This method is checking territory by string boolean. Check player has this country or not.
     *
     * @param countryName the country name
     * @return whether player has the territory by name, true if contains
     */
    public boolean checkTerritoryByString(String countryName){
        return territories.containsKey(countryName);
    }

    /**
     * This method is getting territory by string territory.
     *
     * @param str the str
     * @return the territory found by its name;
     */
    public Territory getTerritoryByString(String str){
        return territories.get(str);
    }

    /**
     * This method is printing player info include name, troops on hand, number of territories and continents.
     */
    public void printPlayerInfo(){
        System.out.println("The player "+this.name+" has "+getTroops()+" troops and has "+territories.size()+" territories: ");
        for (Territory territory:getTerritories()){
            System.out.println(territory.shortDescription());
        }
        System.out.println();
        if(!continents.isEmpty()){
            System.out.println("And the continents: ");
            for(Continent continent:getContinents()){
                System.out.println(continent.getName()+", ");
            }
        }
    }

    /**
     * This method is gaining troops from territories.
     * At least gain three troops, the number of gained troops is number of territories / 3. Always round down
     */
    public void gainTroopsFromTerritory(){
        int bonus = this.getTerritories().size()/bonusUnits;
        if(bonus < leastBonusTroops){bonus = leastBonusTroops;}
        System.out.println("Player " + this.getName()+" has "+this.getTerritories().size()+" territories, add "+bonus+" troops.");
        this.increaseTroop(bonus);
        addContinentBonus();
    }

    /**
     * This methos is adding continent bonus. Each continent will have different bonus troops, depending on different maps and rules.
     */
    public void addContinentBonus(){
        if(!getContinents().isEmpty()){
            System.out.println("Player " + this.getName()+" has continents: ");
            for(Continent continent:this.getContinents()){
                this.increaseTroop(continent.getBonusTroops());
                System.out.println(continent.getName()+", add "+continent.getBonusTroops()+" troops.");
            }
        }
    }


    /**
     * The method is a setter for player id
     *
     * @param i set the id of the player
     */
    public void setID(int i){
        this.id = i;
    }

    /**
     * This method is a getter for player id
     *
     * @return the id of the player
     */
    public int getID(){
        return this.id;
    }

    /**
     * Model handle the draft preparation and update the view
     */
    public void draftPrepare(){
        model.setCurrentStage(Stage.DRAFT);
        model.checkContinent(this);
        this.gainTroopsFromTerritory();

        String continentBonus= model.getContinentBonusString();
        for(RiskViewInterface view: model.getViewList()){
            view.updateDraftPrepare(this,model.getCurrentStage(),continentBonus);
        }
    }

    /**
     * Model handle the attack preparation and update the view
     */
    public void attackPrepare(){
        model.setCurrentStage(Stage.ATTACK);
        model.setOriginTerritoryButtonPressed(true);
        for(RiskViewInterface view: model.getViewList()){
            view.updateAttackPrepare(this,model.getCurrentStage(),model.getAttackTerritoriesList(this));
        }
    }

    /**
     * Model handle the fortify preparation and update the view
     */
    public void fortifyPrepare(){
        model.setCurrentStage(Stage.FORTIFY);
        for(RiskViewInterface view: model.getViewList()) {
            view.updateFortifyPrepare(model.getFortifyTerritories(this),this,model.getCurrentStage());
        }
    }

    /**
     * Model handle the deploy preparation and update the view
     */
    public void deployPrepare(){
        model.setCurrentStage(Stage.DEPLOY);
        for(RiskViewInterface view: model.getViewList()){
            view.updateDeployPrepare(this,model.getCurrentStage(),model.getAttackTerritory().getTroops()-1);
        }
    }

    /**
     * Model do the attack process and update the view
     */
    public void attackProcess() {
        String originTerritoryName = model.getOriginTerritoryName();
        String targetTerritoryName = model.getTargetTerritoryName();
        if(originTerritoryName.isEmpty() || targetTerritoryName.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please ensure you have selected both territories!","Incomplete Selection on Territories",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Territory attackTerritory = model.getTerritoryByString(originTerritoryName);
        Territory defenceTerritory = model.getTerritoryByString(targetTerritoryName);
        model.setAttackTerritory(attackTerritory);
        model.setDefenceTerritory(defenceTerritory);
        AttackWay attackWay = null;
        for(RiskViewInterface view: model.getViewList()) {
            attackWay = view.getAttackTroopsBox();
        }
        boolean gainTerritory = model.battle(attackTerritory,defenceTerritory,attackWay);
        attackProcessResult(attackTerritory,defenceTerritory,gainTerritory);
    }

    protected void attackProcessResult(Territory attackTerritory, Territory defenceTerritory,boolean gainTerritory){
        for(RiskViewInterface view: model.getViewList()) {
            view.setTerritoryButtonTroops(attackTerritory.getName(), attackTerritory.getTroops());
            view.setTerritoryButtonTroops(defenceTerritory.getName(), defenceTerritory.getTroops());
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            if(gainTerritory) {
                JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"You conquered "+defenceTerritory.getName()+"!");
                view.updateWinAttack(this);
                return;
            }
        }
        JOptionPane.showMessageDialog(null,model.getBattleStatusString()+"You didn't conquered "+defenceTerritory.getName()+".");
        model.resetButtonsAndBoxProcedure();
        model.checkContinent(this);
    }

    protected void deployProcess(String originTerritoryName,String targetTerritoryName,int moveTroops){
        for(RiskViewInterface view: model.getViewList()) {
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(originTerritoryName, getTerritoryByString(originTerritoryName).getTroops());
            view.setTerritoryButtonTroops(targetTerritoryName, getTerritoryByString(targetTerritoryName).getTroops());
            model.resetButtonsAndBoxProcedure();
            view.updateDeployFinish(this);
        }
        JOptionPane.showMessageDialog(null,this.name+" deployed "+moveTroops+" troops from "+originTerritoryName+" to "+targetTerritoryName);
        model.setCurrentStage(Stage.ATTACK);
        model.checkWinner();
    }


    protected void fortifyProcessResult(Territory startCountry,Territory destinationCountry,int moveTroops){
        for(RiskViewInterface view: model.getViewList()) {
            model.setCurrentPlayer(model.getNextPlayer(this.getID()));
            JOptionPane.showMessageDialog(null,model.getCurrentPlayer().getName() +" moved "+ moveTroops+ " troops from "+startCountry.getName()+" to "+destinationCountry.getName()+".");
            view.setContinentsLabel(model.getMapInfoThroughContinent());
            view.setTerritoryButtonTroops(startCountry.getName(), startCountry.getTroops());
            view.setTerritoryButtonTroops(destinationCountry.getName(), destinationCountry.getTroops());
            model.resetButtonsAndBoxProcedure();
            view.updateFortifyFinish(this);
        }
    }

}
