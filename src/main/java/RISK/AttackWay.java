package RISK;

/**
 * AttackWay include one,two,three, and blitz
 *
 */
public enum AttackWay {
    ONE(1),TWO(2),THREE(3),BLITZ(-1);

    private int attackTroops;
    /**
     * Constructor for AttackWay, number means the troops to attack, blitz is special so use -1
     * @param attackTroops
     */
    AttackWay(int attackTroops){
        this.attackTroops = attackTroops;
    }

    /**
     * This is a getter for troops of attackWay
     * @return the number of attack troops
     */
    public int getAttackTroops() {
        return attackTroops;
    }


}
