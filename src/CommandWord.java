public enum CommandWord {
    ATTACK,SKIP,QUIT,FORTIFY,DRAFT,PASS,UNKNOWN;

    private String commandString;


    public String toString()
    {
        return commandString;
    }
}
