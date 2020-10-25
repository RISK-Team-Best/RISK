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

#### Instruction:    
Use cmd  to run this program: java -jar RISK.jar      
(Please note that this project is designed and compiled in JDK 14, if encounter Java version issue, please install JDK14)    
Use IntelliJ to run this program: click Game.java > Run    
(The Game.java includes the static main method to run RISK)    
  
In the game:   

1.enter the number of player(2 to 6)<br>     
2.enter players'name<br>      
3.draft（This step cannot skip):      
&emsp;(1)enter the territory name             
&emsp;(2)enter the number of troops that want to draft in this territory<br>                
4.attack（This step could choose skip):        
&emsp;(1)enter the territory you want to launch attack          
&emsp;(2)enter the territory be attacked           
&emsp;(3)choose troops number to attack (one/two/three/blitz/finish)<br>    
5.fortify(This step could choose skip):              
&emsp;(1)enter the territory you want move troops from         
&emsp;(2)enter the territory you want move troops to(choose 'back' to re-select original territory)          
&emsp;(3)choose the number of troops you want to send to destination territory<br>    
6.After the steps above, the program will automatically pass to next player until one of the player owns all of the territory<br>  

#### Deliverables:   
readme file         
code (source + executable in a jar file)       
UML diagrams: Class Diagram, Sequence Diagram           
documentation.     

#### Contribution:          
Guanqun Dong:Board.class,Continent.class.        
Jiawei Ma:Game.java,Sequence Diagram.           
Jiatong Han:Territory.class.      
Tiantian Lin:UML Class Diagram, Dices.java,Player.java.       






