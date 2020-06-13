import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;


class Player{
	String nickname;
	int color;
	int id;
	Player(String nickname, int yourId){
		this.nickname = nickname;
		this.color = this.id = yourId;
		
	}
}


public class GameUi extends JFrame {
	static boolean myTurn;
	LgameControler Lgamecontroler;
	private short[] tempPosL = new short[4];
	int posPointer =0;
	int yourId;
	Board Board;
	BlockButton[] buttonArray = new BlockButton[16];// array for storing board button
	 //create status bar
    JLabel statusBar;
    
    GameUi(int yourId) {
    	super("L-Game");
    	this.yourId = yourId;
        Player player1 = new Player("player1",yourId);
        Board = new Board(new Lblock((short)1), new Lblock((short)2), new Oblock(3),new Oblock(4));
       
        Lgamecontroler = new LgameControler();
        // The Main Panel where the 2 other panels will be on
        JPanel mainPanel = new JPanel(new BorderLayout());

        // The textarea will be inside this panel
        JPanel areaPanel = new JPanel(new BorderLayout());

        JLabel playerinfo = new JLabel("<html>Player id: "+player1.id+"<br/>Player color: " + ((player1.color==1) ? "Red" : "Blue")+"<html>" );
        playerinfo.setBorder(new LineBorder(Color.BLACK));
        statusBar = new JLabel();//initializing statusbar
        changeStatus("Welcome to the L game! Please make your move");//changing status message
        statusBar.setFont(new Font("Arial", Font.PLAIN, 20));
        
        //adding surrender button
        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.setActionCommand("Surrender");
        surrenderButton.addActionListener(new ButtonClickListener(statusBar));
        
        //adding next button
        JButton nextButton = new JButton("Next");
        nextButton.setActionCommand("Next");
        nextButton.addActionListener(new ButtonClickListener(statusBar));
        
        // Fill the whole space of the panel with the area
        areaPanel.add(nextButton, BorderLayout.EAST);
        areaPanel.add(playerinfo, BorderLayout.CENTER);
        areaPanel.add(surrenderButton, BorderLayout.WEST);
        int[][] board = {{3,1,1,0},{0,2,1,0},{0,2,1,0},{0,2,2,3}};

        // The buttons will be inside this panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 0, 0));
        
        for (int i = 0; i < 16; i++) { // Adding buttons
        	BlockButton button = new BlockButton(board[i/4][i%4],i/4,i%4,player1.color);// adding board button 4x4
        	buttonArray[i] = button;
        	buttonPanel.add(button);
        }
        
        
        
        
        
        // The textarea-panel should be on top of the main panel
        mainPanel.add(areaPanel, BorderLayout.NORTH);

        // The panel with the buttons should fill the remaining space
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        
        setSize(600, 600);
        setResizable(false);
        setVisible(true);
    }
    
    //===================================================================
    class ButtonClickListener implements ActionListener {     //next와 surrender 버튼 처리 클래스
    	JLabel label;
    	public ButtonClickListener(JLabel label) {
			// TODO Auto-generated constructor stub
    		this.label = label;
    		
		}
    	public void actionPerformed (ActionEvent e) {
    		String command = e.getActionCommand ();
    		if (command.equals("Surrender")){
    			label.setText("You surreder!");
    			
    			JComponent comp = (JComponent) e.getSource();
    			  Window win = SwingUtilities.getWindowAncestor(comp);
    			  win.dispose();
    		}
    		else if(command.equals("Next")) {
    			//have to evaluate whether input is correct
    			if(posPointer<4) {
    				return;
    			}
    			posPointer=0;//reset pospointer
    			 int[][] newboard = {{3,1,1,1},{0,2,0,1},{0,2,0,0},{0,2,2,3}};//try to update to newboard - this is just a test
    			 
    			 updateBoard(newboard);
    			 label.setText("You change position!");
    		}
    		}
    	}
    
    //=================================================================================================
    class BlockButton extends JButton implements ActionListener{            //블록 처리 클래스
    	ImageIcon X,O;
    	
    	public BlockButton(int blocktype, int y,int x, int color){
    		X=new ImageIcon("X.png");
    		O=new ImageIcon("O.png");
        	if(blocktype==1) {
        		setBackground(Color.RED);
        	}
        	else if(blocktype==2) {
        		setBackground(Color.BLUE);
        	}
        	else if(blocktype==3) { 
        		setBackground(Color.BLACK);
        	}
        	if(color==blocktype||0==blocktype) {
        		setActionCommand("1"+y+x);
        		System.out.println(getActionCommand());
        	}
        	else
        		setActionCommand("noposition");
        	setOpaque(true);
            
            this.addActionListener(this);
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		String command = e.getActionCommand ();
  
    		if (GameUi.myTurn ==true) {
    			if(posPointer==4) {
    				
    			}
    			if(posPointer<4) {
    				if(getBackground()!=Color.CYAN) {
    					setBackground(Color.CYAN);
    	    			setIcon(O);
    	    			tempPosL[posPointer]=Short.parseShort(command.substring(1));
    	    			++posPointer;
    				}
    			}
    		}
    		else if(command.equals("noposition")) {
    			
    		}
    	}
    	
    }
    void updateBoard( int[][] newboard ) {
    	//Lblock Lblock1,Lblock Lblock2,Oblock Oblock1,Oblock Oblock2
    	//setTempInfo(short[][] board, short pos)
    	//boolean update() {
    	//short[] getPosition() 
		for (int i = 0; i < 16; i++) { // Adding buttons
			 buttonArray[i].setIcon(null);
			 buttonArray[i].setBackground(Color.WHITE);
			 switch(newboard[i/4][i%4]) {
			 	case 1:
			 		buttonArray[i].setBackground(Color.RED);
			 		break;
			 	case 2:
			 		buttonArray[i].setBackground(Color.BLUE);
			 		break;
			 	case 3:
			 		buttonArray[i].setBackground(Color.BLACK);
			 }
			 }
	}
  
    
    void changeStatus(String statusmessage) {
    	statusBar.setText(statusmessage);
    }
}
