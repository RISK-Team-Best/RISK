public class Command {
private CommandWord commandWord;
private String secondWord;
private String thirdWord;
private String fourthWord;


    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     * @param thirdWord The second word of the command.
     * @param fourthWord The second word of the command.
     */

public Command(CommandWord firstWord,String secondWord,String thirdWord,String fourthWord){
    commandWord = firstWord;
    this.secondWord = secondWord;
    this.thirdWord = thirdWord;
    this.fourthWord = fourthWord;


}
    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
public CommandWord getCommandWord(){
    return commandWord;
}
    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }
    /**
     * @return The third word of this command. Returns null if there was no
     * third word.
     */
    public String getThirdWord()
    {
        return thirdWord;
    }
    /**
     * @return The fourth word of this command. Returns null if there was no
     * third word.
     */
    public String getFourthWord()
    {
        return fourthWord;
    }
    /**
     * @return true if this command was not understood.
     */
public boolean isUnknown(){
    return (commandWord == null);
}
    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    public boolean hasThirdWord(){
        return (thirdWord != null);
    }
    public boolean hasFourthWord(){
        return (fourthWord != null);
    }
}

