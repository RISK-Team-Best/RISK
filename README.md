# RISK

## Team Members:
-Guanqun Dong                                                          
-Jiawei Ma  
-Jiatong Han  
-Tiantian Lin  

## Introduction:
This team project is to reproduce a simplified version of the classic strategy game RISK.    
For the simple version of RISK, we skip the initial phase where players take turns to choose countries and place armies. Instead, we start will a random map which the countires and armies are already set up for players.Also, this project skip the phase of drawing cards and using them to receive more armies.  

### Milestone 1:  
A text-based playable version of the game, the players could use console to play the game. The console can print out the state of the map,for example:The outcome of an attack, whose turn it is to play, the elimination of a player, etc.     

### Milestone 2:     
A GUI-based version, now adding the View and the Controller of the game. Display in a JFrame, and user input is via the mouse. 


#### Instruction:    
Use cmd  to run this program: java -jar RiskGame.jar      
(Please note that this project is designed and compiled in JDK 14, if encounter Java version issue, please install JDK14)    
Use IntelliJ to run this program: click RiskGame.java > Run    
(The RiskGame.java includes the static main method to run RISK)    
  
In the game:   

1.Click File->New to create a new game<br>
2.Enter the number of player(2 to 6)<br>
3.Enter players'name, please note that the players name can't be the same, or will pop window to enter again<br>     
Then the map will automatically generate territories and troops for you.<br>                                                              
4.Click draft button（This step cannot skip):<br>           
&emsp;(1)choose the territory button on the map that you want send troops to(click again to cancel and reselect)            
&emsp;(2)choose the number of troops you want to send to in the troop box on the right of the map                                     
&emsp;(3)once you choose both territory and troops, click confirm to process<br>   
5.Click attack button（This step could choose skip, if you click skip, then move to fortify):<br>         
&emsp;(1)choose the territory button on the map that you want to launch attack(click the territory button again to cancel and reselect)        
&emsp;(2)choose the territory button on the map that you want to attack(click the territory button again to cancel and reselect) &emsp;(3)choose the attackway in the attackWay box on the right of the map to attack (one/two/three/blitz)                           
&emsp;(4)once you choose the territories and attackWay, click confirm to process                                                       
&emsp;(5)the game will pop the battle result.If you win, click deploy to deploy troops.If you lose, choose another territory button on the map to launch attack or click skip to move to fortify                                                                     
&emsp;(6)Deploy(only you win in the battle):choose the number of troops that you want to send to the territory that you just win,then click confirm to process<br>      
6.Click fortify button (This step could choose skip, if you click skip, then move to next player):     <br>          
&emsp;(1)choose the territory button you want move troops from (click again to cancel and reselect)         
&emsp;(2)choose the territory button you want move troops to(click again to cancel and reselect)          
&emsp;(3)choose the number of troops you want to send to destination territory on the troopbox    
&emsp;(4)once you choose the territories and troops number, click confirm to process<br>       
7.After the steps above, the program will automatically pass to next player until one of the player owns all of the territory<br>
8.Once there is a player lost all territory or there is a player win the game, the Frame will pop window to show the result<br>
9.If you need start again, please click File->New to start a new game<br>

#### Deliverables:   
readme file         
source code                 
test code               
UML diagrams: Class Diagram, Sequence Diagram           
documentation           


#### Contribution: 
Board.java,Continent.java:Guanqun Dong                                  
RiskModel.java:Jiawei Ma                                 
RiskView.java:Guanqun Dong,Jiawei Ma,Jiatong Han,Tiantian Lin                                              
RiskController.java:Jiawei Ma,Tiantian Lin                                    
RiskGame.java,MapPanel.java,Sequence Diagram:Jiawei Ma                                                    
Territory.java,AttackWay.java,Stage.java:Jiatong Han                                                                     
Dices.java,Player.java,RiskModelTest.java,UML Class Diagram:Tiantian Lin                                                 



### Design Decisions:    
### Data Structure:                 
### Known Issue:     
Currently no known issue.               


### Changes:        
1.We've updated the method to assign troops to players, using a hashmap instead to map the player amount to troup amounts.                              
2.We've updated the method to read txt file from remote URL to local resource.                                
3.We've added a new attribute: ID to each player for assign color and easy for model to get next player.                            
4.We've added two enum class: Stage and AttackWay.                              

### Roadmap Ahead:                  
Currently, our team already finished part of the requirement in M3：bonus army placement and troops movement phase.We are still working on "AI Player" and part of M4.Some of the java files relate to XML have already upload on Github,these code related to the M4 Save/Load feature. Please only have a look at the src file at this point.              





