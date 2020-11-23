package RISK;

/**
 * Enum Stage is for record the stage of the game
 */
public enum Stage {
    DRAFT("Draft"),ATTACK("Attack"),FORTIFY("Fortify"),DEPLOY("Deploy");

    private String name;

    /**
     * Constructor for Stage
     * @param name
     */
    Stage(String name){
        this.name = name;
    }

    /**
     * This method is getting name of the Stage type
     * @return Stage's name
     */
    public String getName() {
        return name;
    }
}
