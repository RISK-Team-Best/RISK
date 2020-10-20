import java.util.HashMap;
public class CommandWords {
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("skip", CommandWord.SKIP);
        validCommands.put("draft", CommandWord.DRAFT);// draft A 12
        validCommands.put("attack", CommandWord.ATTACK);// attack A to B
        validCommands.put("fortify", CommandWord.FORTIFY);//fortify A to B
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("pass", CommandWord.PASS);

    }

    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null)
            return command;
        else
            return CommandWord.UNKNOWN;
    }
    /**
     * Check whether a given String is a valid command word.
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    public void Look() {
        for(String command: validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

}
