public enum Stage {
    DRAFT("Draft"),ATTACK("Attack"),FORTIFY("Fortify"),DEPLOY("Deploy");

    private String name;
    Stage(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
