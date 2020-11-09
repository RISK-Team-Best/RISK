/**
 * AttackWay include one,two,three, and blitz
 *
 */
public enum AttackWay {
    ONE(1),TWO(2),THREE(3),BLITZ(-1);

    /**
     * @param attackTroops
     */
    AttackWay(int attackTroops){
        this.attackTroops = attackTroops;
    }

    /**
     * @return the number of attack troops
     */
    public int getAttackTroops() {
        return attackTroops;
    }
    private int attackTroops;


}
