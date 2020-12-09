# RISK

## Team Members:
-Guanqun Dong                                                          
-Jiawei Ma  
-Jiatong Han  
-Tiantian Lin  

## Introduction:
This team project is to reproduce a simplified version of the classic strategy game RISK.    
For the simple version of RISK, we skip the initial phase where players take turns to choose countries and place armies. Instead, we will start to draw a random map, in which the countries and armies have been set up for players.Also, this project skip the phase of drawing cards and using them to receive more armies.  

### Milestone 1:  
A text-based playable version of the game, the players could use console to play the game. The console can print out the state of the map,for example:The outcome of an attack, whose turn it is to play, the elimination of a player, etc.     

### Milestone 2:     
A GUI-based version, now adding the View and the Controller of the game. Display in a JFrame, and user input is via the mouse. 

### Milestone 3:
Add features: bonus army placement, fortifying phases, and AI player

### Milestone 4:

Add feature: load new maps (Seaport,Alcatraz, Invalid_Isolated_Territory,Invalid_Isolated_Groups). Save/load feature based on each Map.
Notes: Both Invalid maps are using Seaport map. In Invalid_Isolated_Territory, we created one new isolated territory and make it has no connection with others. And in Invalid_Isolated_Groups, we disconnect the connection between ships and the island.(You can check neighborCountry of each territory in neighborList in both xml files)




#### Instruction:    
Use cmd  to run this program: java -jar RiskGame.jar      
(Please note that this project is designed and compiled in JDK 14, if encounter Java version issue, please install JDK14)    
Use IntelliJ to run this program: click RiskGame.java > Run    
(The RiskGame.java includes the static main method to run RISK)    
  
In the game:   

1.Click File->New to create a new game<br>
2.Enter the number of player(2 to 6)<br>
3.Enter players' names on the text box(There already have some default names), please note that the players name can't be the same (or it will give error prompt and wait until you have valid input).Then choose the player is AI or not.<br>     
Then the map will automatically generate territories and troops for you.<br>                                                              
4.Click draft button（This step cannot skip):<br>           
&emsp;(1)choose the territory button on the map that you want send troops to(click again to cancel and reselect)            
&emsp;(2)choose the number of troops you want to send to in the troop box on the right of the map                                     
&emsp;(3)once you choose both territory and troops, click confirm to process<br>   
5.Click attack button（This step could choose to skip, if you click skip, then move to fortify):<br>         
&emsp;(1)choose the territory button on the map that you want to launch attack(click the territory button again to cancel and reselect)        
&emsp;(2)choose the territory button on the map that you want to attack(click the territory button again to cancel and reselect) &emsp;(3)choose the attack way in the attackWay box on the right of the map to attack (one/two/three/blitz)                           
&emsp;(4)once you choose the territories and attackWay, click confirm to process                                                       
&emsp;(5)the game will pop the battle result.If you win, click deploy to deploy troops.If you lose, choose another territory button on the map to launch attack or click skip to move to fortify                                                                     
&emsp;(6)Deploy(only you win in the battle):choose the number of troops that you want to send to the territory that you just win,then click confirm to process<br>      
6.Click fortify button (This step could choose to skip, if you click skip, then move to next player):     <br>          
&emsp;(1)choose the territory button you want move troops from (click again to cancel and reselect)         
&emsp;(2)choose the territory button you want move troops to(click again to cancel and reselect)          
&emsp;(3)choose the number of troops you want to send to destination territory on the troop Box    
&emsp;(4)once you choose the territories and troops number, click confirm to process<br>       
7.After the steps above, the program will automatically pass to next player until one of the player owns all the territories<br><br>
8.Once there is a player lost all territory or there is a player win the game, the Frame will pop window to show the result<br><br>
9.If you need start again, please click File->New to start a new game<br><br>
10.Click "Load New Map" button in menu, the prompt with JCombo box will allow you to select the map you want to play. The program will also reject invalid map(isolated territory/territories)<br><br>
11.Click save button and type the name you want your file called (For example: testFile). The program will generate an XML file (for example: testFile_MapName.xml) on current path of program.<br><br>
12.Click load button and type the name you saved (For example: testFile), the program will find the full name of the XML file (for example: testFile_MapName.xml) on current path of program.<br><br>

Note for AI player: AI player will pop result window in each stage:Draft,Attack(Skip when there is no territory can attack),Deploy(If win in the battle),Fortify(Skip when there is no territory can send troops). It will also pass to next player after finished its round.
User clicks the "OK" button to allow AI player enter the next stage.<br>

Note for Save/Load feature: Cannot be used directly in JAR file. Need extract the program.



#### Deliverables:   
readme file         
source code                 
test code               
UML diagrams: Class Diagram, Sequence Diagram           
documentation           


#### Contribution: 
Guanqun Dong &emsp;:Board.java, refactor, packaging using maven, QA, MapEditor, All things in ToolsAndTries package.<br>
Jiatong Han &emsp;:RiskView.java, Alcatraz map(Continent part), UML, javadoc<br>
Jiawei Ma &emsp;:All classes in RISK package, refactor, debug, Sequence Diagram, Readme, Alcatraz map(Neighbor Countries part)<br>
Tiantian Lin &emsp;:AIPlayer.java, Player.java, XMLHandler.java,RiskView.java, RiskController.java, JUnit test, whole Seaport part (include graph), Alcatraz map(Graph and country part)<br>



### Design Decisions: 
1.  (1)For MVC part, we separate the model, view and controller as much separate as possible. So that it could be easy for TA and prof to mark and easy for us to maintain for the future. We divided Model,View and Controller into three different classes. The model class need process the data in the backend. View class provides the front-end of the program and interact with users.
    (2)We also refactored MVC which got deduction in the Milestone2. Added view as listener and announce view to update once we are processing to next stage.

2. (1)For GUI part, we don't want a lot of prompt messages to appear when processing the game, including warning messages, operation steps messages, information messages and so on. So we create the operation panel with several JButtons and one JComboBox. Player can use the operation panel to set command. 

   (2)At first, we don't want to use buttons to instead territories, because it's so ugly and hard to maintain(For Milestone4, we may change another map). So we had two JLists as the choosing place for origin and target territories. It is also corresponds to the labs we took for MVC. We also placed a graph map only as a reference, and all info of the map showed on the info panel (the most right side of the GUI). However, Prof Babak wants the buttons which we don't like, so we combined the two ideas: replace JLists with JButtons. 

   (3)<i>Finally, we placed the buttons on the graph map and make button shows the troops on the territory. It will only enable the available buttons for different steps, different requirement. We also want the button shows to the different players. So we added the color on buttons according to player's ID. Also, the player's id used on change to next player. The Info panel still shows the whole map's info (Continent-Territory-Holder-troops).</i>
   
   (4)AI will show message through prompt. In addition, the territory button will also display the color for each stage. i.e. Red is the selected origin territory. Orange is the selected target territory. Same color and process as human players.

3. Fortify part, use recursion method to visit all available territory that can be fortified.

4. Save/load feature:<br><br>
    &emsp;&emsp;(1)Save: Use SAX to generate XML file. The file contains all needed info (Map's image name, Players info include Name, ID, Territories, Continents; model's current player and current stage.). Then file name format is "customFileName+_+mapName.xml"<br><br>
    &emsp;&emsp;(2)Load: Determine the mapName through the full file name. And use SAX parser to parse saved XML file. And retore all model and view info through model's info includes currentStage, currentPlayer. Through different currentStage, the view could load to saved progress.<br>

### Data Structure: 
<b>Type -- Purpose -- Classes </b><br>
HashMap -- Store a pair of information and prevent duplication -- Player, Board, RiskController, RiskModel<br>
HashMap with ArrayList in value -- Store a key and store a bunch of info related to the Key -- Board<br>
Enum -- Prevent Typo and easy to get through IntelliJ's suggestion. -- AttackWay, Stage<br>
ArrayList -- Instantiate of List to store same type info -- Continent, Dices, RiskModel<br>
LinkedHashSet -- Store one type of info, prevent duplication and can be trace in order -- RiskModel<br>
LinkedHashMap -- Store the corresponding player's name and type. Also, can iterate to get each player in sequence.-- PlayerSettingDialog


### Known Issue:     
NULL                                                                                                                   

### Changes:        
1.We've refactor the code in Controller, Model and added viewInterface as listener to avoid smelly                                                  
2.We've added a View Interface to implement different update view methods                                     
3.We've added default names for user to choose when add players                                                               
4.We've fixed the error that MAC OS don't show the button color<br>
5.Make Stage more specific. Take ATTACK stage as example, it specified to DRAFTEND, ATTACK, and ATTACKEND stages.<br>
6.Use SAX parser (in XMLHandler class) to create and parse the XML file for save/load feature.<br>
7.Creating Board from reading text files to parse and handle new map xml files.<br>





