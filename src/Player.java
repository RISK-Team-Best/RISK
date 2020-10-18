public class Player {
    private String name;
    private int troops;
    private int occupied;

    /**
     *
     * @param name
     */
    public Player(String name)
    {
        this.name = name;
    }

    /**
     *
     * @param num
     */
    public void increasetroops(int num)
    {
        troops+=num;
    }

    /**
     *
     * @param num
     */
    public void decreasetroops(int num)
    {
        troops -= num;
    }

    /**
     *
     * @param num
     */
    public void increaseOccupied(int num)
    {

    }

    /**
     *
     * @param num
     */
    public void decreaseOccupied(int num)
    {

    }
}
