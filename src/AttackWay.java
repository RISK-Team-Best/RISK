public enum AttackWay {
    ONE(1),TWO(2),THREE(3),BLITZ(-1);

    private int attackTroops;

    AttackWay(int attackTroops){
        this.attackTroops = attackTroops;
    }

    public int getAttackTroops() {
        return attackTroops;
    }
}
