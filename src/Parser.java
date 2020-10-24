import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;
    private String [] instruction;// source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        instruction = new String[4];

    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to three words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()){
                    word3 = tokenizer.next(); //get third word
                    if(tokenizer.hasNext()){
                        word4 = tokenizer.next();}//get fourth word
                }// note: we just ignore the rest of the input line.
            }
        }

         return new Command(commands.getCommandWord(word1), word2, word3, word4);

    }
    public String[] getInstrction(){

        System.out.print("> ");
        this.instruction =  reader.nextLine().split(" ");
        return instruction;

        //for(String str : instruction){
            //instruction.add(str);
        //}
        //this.instruction = reader.nextLine().split(" ");
       // return instruction;
    }
    public static void main(String[] args){
         Parser parser = new Parser();
         Command command = parser.getCommand();
         System.out.println(command.getCommandWord());
         System.out.println(command.getSecondWord());
         System.out.println(command.getThirdWord());
         System.out.println(command.getFourthWord());


        //Command command1 = new Command(CommandWord.ATTACK,"a","b","3");
        //System.out.print(command1.getCommandWord());

    }
    //public void showcommand() {
       // commands.Look();
    //}
}
