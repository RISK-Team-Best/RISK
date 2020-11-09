public enum AttackWay {
    ONE(1),TWO(2),THREE(3),BLITZ(-1);
    AttackWay(int attackTroops){
        this.attackTroops = attackTroops;
    }

    public int getAttackTroops() {
        return attackTroops;
    }
    private int attackTroops;


}
