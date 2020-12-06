package RISK;

/**
 * Enum Stage is for record the stage of the game
 */
public enum Stage {
    DRAFT("Draft"),ATTACK("Attack"),FORTIFY("Fortify"),ATTACKTODEPLOY("Attack_To_Deploy"),DEPLOY("Deploy"),DRAFTEND("Draft_End"),ATTACKEND("Attack_End"),FORTIFYEND("Fortify_End");

    private String name;

    /**
     * Constructor for Stage
     *
     * @param name
     */
    Stage(String name){
        this.name = name;
    }

    /**
     * This method is getting name of the Stage type
     *
     * @return Stage's name
     */
    public String getName() {
        return name;
    }

    public static Stage fromString(String text){
        for(Stage stage:Stage.values()){
            if(stage.name.equalsIgnoreCase(text)){
                return stage;
            }
        }
        return null;
    }
}
