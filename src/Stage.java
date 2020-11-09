/**
 * Enum Stage is for record the stage of the game
 */
public enum Stage {
    DRAFT("Draft"),ATTACK("Attack"),FORTIFY("Fortify"),DEPLOY("Deploy");

    private String name;

    /**
     * @param name
     */
    Stage(String name){
        this.name = name;
    }

    /**
     * @return Stage's name
     */
    public String getName() {
        return name;
    }
}
