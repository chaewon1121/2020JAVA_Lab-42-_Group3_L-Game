# 2020-1-JAVA_Lab42_Group 3
- [Member](#about_team)
- [Our Project](#our_project)
- [How to play](#Install)
- [Class Introduction](#class)
- [Our Github](http://github.com/ht0324/2020JAVA_Lab-42-_Group3_L-Game)

<br>

## <div id="about_team">
  
## Member
Name  | Role
------------- | -------------
박동민(팀장) | Game Logic
김광원 | Server Client
김훈태 | GUI


<br>
<div id="our_project">

## Our Prject
We decided to make a game called L-game. Our goal is to be multiplayer with 2 players. We worked in three parts: socket programming, GUI, and Game Logic.
<br>

### What is L-game?
- https://en.wikipedia.org/wiki/L_game  
<br>
<p align="center">
<img src="https://user-images.githubusercontent.com/63694834/84597799-404d5900-aea1-11ea-9c2b-536c627bfaac.png" width="40%">
</p>
<br>
The L game is a two-player game played on a board of 4×4 squares. Each player has a 3×2 L-shaped piece, and there are two 1×1 neutral pieces. 

On each turn, a player must first move their L piece, and then may optionally move one of the neutral pieces. The game is won by leaving the opponent unable to move their L piece to a new position.

Pieces may not overlap or cover other pieces. On moving the L piece, it is picked up and then placed in empty squares anywhere on the board. It may be rotated or even flipped over in doing so; the only rule is that it must end in a different position from the position it started—thus covering at least one square it did not previously cover. To move a neutral piece, a player simply picks it up then places it in an empty square anywhere on the board.

<br>

- [L-game_Tutorial_youtube](http://youtu.be/ccwwy4sy_R4?t=6)

<br>

<div id="Install">  
  
## How to play
This game is Server-player vs Client-player. So, one has to run LgameServer.java and the other has to run LgameClient.java
### Eclipse
- Clone or download this repository in default package.
 
#### Server
  - Run LgameServer.java and input nickname you want.
  - If client is connected, game runs automatically.
   <img src ="https://user-images.githubusercontent.com/63694834/84598436-ae941a80-aea5-11ea-8587-26f44e1b5f8f.png">
  
  <br>

#### Client
  - Run LgameClient.java and intput server ipaddress, port(ex: 192.168.1.1/5000)
  - If connected, input nickname you want. Then, game runs automatically.
  <img src = "https://user-images.githubusercontent.com/63694834/84598589-d172fe80-aea6-11ea-8b70-baa5c666485b.png">
  <br>
  
  - Enjoy the game! 
  
  <br>
  
<div id="class">
  
 ## Class Introduction  
 ### LgameServer
 <pre>
 <code>
  public LgameServer{
      ...
      main(){...}      //start LgameServer class
      LgameServer(){...}   //Server setting and start GameUi
      class GameUi extends JFrame{
          GameUi(){...}  //start game. get and send Data
          class ButtonClickListener implements ActionListener{...}          //button click listener
          class BlockButton extends JButton implements ActionListener{...}   //block click listener
          void SendData(){...}
          boolean GetData(){...}
          void updateBoard(){...}   //update GUI
          finalize(){...}
          ...
      }
      int DesideOrder(){...}    //Deside game order
      ...
}
</code>
</pre>


### LgameClient
 <pre>
 <code>
 public LgameClient{
      ...
      main(){...}      //start LgameCLient class
      LgameCLient(){...}   //Server setting and start GameUi
      class GameUi extends JFrame{
          GameUi(){...}  //start game. get and send Data
          class ButtonClickListener implements ActionListener{...}          //button click listener
          class BlockButton extends JButton implements ActionListener{...}   //block click listener
          void SendData(){...}
          boolean GetData(){...}
          void updateBoard(){...}   //update GUI
          finalize(){...}
          ...
      }
      ...
}
</code>
</pre>


### Board
<pre>
<code>
public class Board{
    ...
    Board(){...}  //Initalize board
    void Lupdate(){...}  //Update L block
    void Oupdate(){...}  //Update O block
    boolean isGameContinue(){...}  //Victory judgment
    void boardUpdate(){...}  //Board update
    void printBoard(){...}   //Print board
    ...
}
</code>
</pre>


### Lblock
<pre>
<code>
public class Lblock{
    ...
    Lblock(){...}  //Initalize L block
    boolean update(){...} //Return true if updatable 
    void setTempInfo(){...}  //Save temporary coordinates
    ...
}
</code>
</pre>


### Oblock
<pre>
<code>
public class Oblock{
    ...
    Oblock(){...} //Initalize L block
    boolean update(){...} //Return true if updatable 
    void setTempInfo(){...} //Save temporary coordinates
    ...
}
</code>
</pre>


### InfoUi
<pre>
<code>
public class InfoUi extends JFrame{
    ...   //Help options during the game
}
</code>
</pre>
<br>
