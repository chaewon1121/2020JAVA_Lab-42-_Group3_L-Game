import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;



public class LgameClient {
	 DataOutputStream dous;
	 DataInputStream dins;
	 Socket soc;
	 String clientplayer, serverplayer, serveraddress;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new LgameClient();
	}
	
	LgameClient(){
		Scanner sc= new Scanner(System.in);
		
		System.out.print("Input server address(192.168.56.1/5000): ");   //서버 주소와 포트 입력
		serveraddress = sc.next();
		String serverinfo[] = serveraddress.split("/");                // /기준으로 나눠서 저장
		int serverport = Integer.parseInt(serverinfo[1]);
		try {
			soc = new Socket(serverinfo[0], serverport);     //ip주소와 포트번호를 넣음
			dous = new DataOutputStream (soc.getOutputStream());  //데이터 보내는 스트림
			dins = new DataInputStream(soc.getInputStream());      //데이터 읽어오는 스트림
		
			System.out.println("Connected to server!: "+serveraddress);   //연결되면 서버주소 출력
			
			System.out.print("Input yout nickname: ");             //클라이언트 닉네임 입력
			clientplayer = sc.next();
			dous.writeUTF(clientplayer);
			System.out.println("[client] Your ip: "+ soc.getLocalAddress());    //클라이언트 ip보여주기(그냥)
			System.out.println("[client] Your nickname: "+clientplayer);        //이름도 같이 보여주기
			
			
			serverplayer = dins.readUTF();                                      //서버측 닉네임 읽은 후 저장
			System.out.println("[server] Server nickname: "+serverplayer);
			
			int order= dins.readInt();    //순서 결과를 읽어드림(0또는 1)
			int myId;
			
			if(order==1) {                              //1이면 클라이언트부터 시작
				System.out.println("You first!");
				myId=1;
			}
			else{
	        	 System.out.println("You second");
	        	 myId=2;
	        }
			
			
			new GameUi(myId,true,clientplayer,serverplayer);
			/*
			 * 여기에 GUI와 게임 실행
			 */
			
			
			
			System.out.println("The end(일단 여기까지)");  //일단 여기까지
//			dins.close();
//	        dous.close();
//			soc.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}// LgameClient constructor end
	
	//=====================GameUi class============================
	class GameUi extends JFrame {
		boolean myTurn=true,isclient;             //자기 턴인지 확인    
		byte[] tempPosL = new byte[ 4];        //버튼으로 입력한 좌표 저장하는 배열
		int posPointer =0;                      //총 몇번 클릭 했는지 저장
		int myId;                               //자기 번호 (선공은 1, 후공은 2)
		String myNickname, yourNickname;        //내 닉네임과 상대닉네임 저장
		Board Board;                     //보드판
		Oblock oblock;                   //클릭 시 처리할 검은 블록 저장을 위한 변수
		BlockButton[] buttonArray = new BlockButton[16];// array for storing board button
		 //create status bar
	    JLabel statusBar;                        //실시간 상황을 돕는 라벨
	   
	    
	    //--------------Constructor------------------
	    GameUi(int yourId,boolean isclient,String myNickname,String yourNickname) {
	    	super("L-Game");
	    	myId = yourId;    //1또는 2;
	    	if(myId==2) myTurn=false;
	        this.isclient = isclient;
	        this.myNickname =myNickname;
	        this.yourNickname = yourNickname;
	        Board = new Board(new Lblock((byte)1), new Lblock((byte)2), new Oblock(3),new Oblock(4));
	        Board.printBoard();
	        
	        
	        // The Main Panel where the 2 other panels will be on
	        JPanel mainPanel = new JPanel(new BorderLayout());

	        // The textarea will be inside this panel
	        JPanel areaPanel = new JPanel(new BorderLayout());
		
		// bottompanel for status and info button
		JPanel bottomPanel = new JPanel(new BorderLayout());

	        JLabel playerinfo = new JLabel("<html>Player id: "+yourId+"<br/>Player color: " + ((yourId==1) ? "Red" : "Blue")+"<html>" );
	        playerinfo.setBorder(new LineBorder(Color.BLACK));
	        statusBar = new JLabel();//initializing statusbar
	         if(myTurn==false) changeStatus("Welcome to the L game! Please wait untill your turn");
	        else changeStatus("Welcome to the L game! Please make your move");//changing status message
	        statusBar.setFont(new Font("Arial", Font.PLAIN, 20));
	       
	        //adding surrender button
	        JButton surrenderButton = new JButton("Surrender");
	        surrenderButton.setActionCommand("Surrender");
	        surrenderButton.addActionListener(new ButtonClickListener(statusBar));
	        
	        //adding next button
	        JButton nextButton = new JButton("Next");
	        nextButton.setActionCommand("Next");
	        nextButton.addActionListener(new ButtonClickListener(statusBar));
		    
		//adding info button
	        JButton infoButton = new JButton("Info");
	    	infoButton.setActionCommand("Info");
	    	infoButton.addActionListener(new ButtonClickListener(statusBar));
	        bottomPanel.add(infoButton, BorderLayout.WEST);
	        
	        // Fill the whole space of the panel with the area
	        areaPanel.add(nextButton, BorderLayout.EAST);
	        areaPanel.add(playerinfo, BorderLayout.CENTER);
	        areaPanel.add(surrenderButton, BorderLayout.WEST);

	        // The buttons will be inside this panel
	        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 0, 0));
	        
	        for (int i = 0; i < 16; i++) { // Adding buttons
	        	BlockButton button = new BlockButton(Board.board[i/4][i%4],i/4,i%4,yourId,statusBar);// adding board button 4x4
	        	buttonArray[i] = button;
	        	
	        	buttonPanel.add(button);
	        }
	        updateBoard(Board.board);
	        
	        
	        
	        
	        // The textarea-panel should be on top of the main panel
	        mainPanel.add(areaPanel, BorderLayout.NORTH);

	        // The panel with the buttons should fill the remaining space
	        mainPanel.add(buttonPanel, BorderLayout.CENTER);
	        
		bottomPanel.add(statusBar, BorderLayout.CENTER);
	        
	        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	        getContentPane().add(mainPanel);
	        
	           
	        setSize(600, 600);
	        setResizable(false);
	        setVisible(true);
	        while(true) {
	        		if(!GetData()) {
	        			//종료 (이김);
	        			System.out.println("You win!");
	        			changeStatus("You win!");
	        			break;
	        		}
	        		if(!Board.isGameContinue((byte)myId)) {
	    				byte[][] s = {{9,9,9,9},{9,9,9,9},{9,9,9,9},{9,9,9,9}};
	    				SendData(s);	//종료(짐)
	    				System.out.println("you lose");
	    				changeStatus("You lose!");
	    				break;
	    			}
	        		//GameUi.myTurn=true;
	        		System.out.println("jjjj");
	        		updateBoard(Board.board);
	        		myTurn = true;
	        	
	        }
	        
	        
	        
	    }// GameUI constructor end
	    
	    //===================================================================
	    class ButtonClickListener implements ActionListener {     //next와 surrender 버튼 처리 클래스
	    	JLabel label;
	    	public ButtonClickListener(JLabel label) {
				// TODO Auto-generated constructor stub
	    		this.label = label;
	    		
			}
	    	public void actionPerformed (ActionEvent e) {
	    		String command = e.getActionCommand ();
	    		if (command.equals("Surrender")&&myTurn){
	    			myTurn =false;
	    			changeStatus("You surrender!");
	    			byte[][] s = {{9,9,9,9},{9,9,9,9},{9,9,9,9},{9,9,9,9}};
					SendData(s);	//종료(짐)
					posPointer=0;
//	    			JComponent comp = (JComponent) e.getSource();
//	    			  Window win = SwingUtilities.getWindowAncestor(comp);
//	    			  win.dispose();
	    		}
	    		else if(command.equals("Next")) {
	    			//have to evaluate whether input is correct
	    			if(posPointer<4) {
	    				return;
	    			}
	    			SendData(Board.board);
	    			updateBoard(Board.board);
	    			posPointer=0;//reset pospointer
	    			myTurn=false;
	    			 changeStatus("next!");
	    		}
			else if (command.equals("Info")) {
	    			try {
						new InfoUi();//open infoui window
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    		}
	    		}
	    	}
	    
	    //=================================================================================================
	    class BlockButton extends JButton implements ActionListener{            //블록 처리 클래스
	    	ImageIcon X,O;
	    	byte position;
	    	int type;
	    	JLabel label;
	    	public BlockButton(int blocktype, int y,int x, int myId,JLabel label){
	    		X=new ImageIcon("X.png");
	    		O=new ImageIcon("O.png");
	    		this.type = blocktype;
	    		this.position = (byte)(10*y +x);
	    		this.label = label;
	        	if(blocktype==1) {
	        		setBackground(Color.RED);
	        	}
	        	else if(blocktype==2) {
	        		setBackground(Color.BLUE);
	        	}
	        	else if(blocktype==3 || blocktype==4) { 
	        		setBackground(Color.BLACK);
	        		
	        	}
	        	setOpaque(true);
	            
	            this.addActionListener(this);
	    	}
	    	
	    	public void actionPerformed(ActionEvent e){
	    	//	String command = e.getActionCommand ();
	    			System.out.println(this.type+" "+myId);
	    		if (myTurn && (this.type ==myId || this.type==0) && posPointer<4) {
	    				if(getBackground()!=Color.CYAN) {
	    					setBackground(Color.CYAN);
	    	    			setIcon(O);
	    	    			tempPosL[posPointer]=(byte)this.position;
	    	    			++posPointer;
	    				
	    				if(posPointer==4) {
	    				Lblock lblock = new Lblock((byte)(myId),tempPosL);
	    				lblock.setTempInfo(tempPosL);
	    				Board.printBoard();
	    				if(!lblock.update(Board)) {		
	    					posPointer =0;
	    					label.setText("Try again");
	    				}
	    				else {
	    					label.setText("correct position!");
	    					Board.Lupdate(lblock);
	    				}
	    				updateBoard(Board.board);
	    				
	    			 }
	    				
	    			} 
	    			
	    		}
	    		else if(myTurn && (type ==3 || type==4) && posPointer==4) {
	    			if(getBackground()!=Color.GRAY) {
						setBackground(Color.GRAY);
		    			setIcon(O);    			
					}	
	    			++posPointer;
	    			System.out.println("^^ "+type+" "+this.position +"^^");
	    			oblock = new Oblock(type,this.position);
	    		}
	    		else if(myTurn && posPointer==5) {
	    			System.out.println("%%"+this.position);
	    			oblock.setTempInfo(position);
	    			if(!oblock.update(Board)) {
	    				posPointer=4;
	    				label.setText("Try again");
	    			}
	    			else {
	    				label.setText("correct position!2");
	    				
						Board.Oupdate(oblock);
						for(int i=0;i<16;i++) System.out.print(Board.board[i/4][i%4]);
						posPointer=0;
						updateBoard(Board.board);
						
						SendData(Board.board);
						myTurn = false;
	    			}
	    			
	    			updateBoard(Board.board);
	    			
	    			
	    		}
	    		
	    		
	    		System.out.println("@"+posPointer);
	    		
	    	}
	    	
	    }     //class blockButton end
	    
	    
	    void SendData(byte board[][]) {
	    	byte b[] = new byte[16];
	    	for(int i=0;i<16;i++) b[i]=board[i/4][i%4];
			try {
				dous.write(b);
			}catch(Exception e) {
				e.printStackTrace();
			}	
			
		}
	    
		 boolean GetData() {
			 byte b[] = new byte[16];
			 byte b1[][] = new byte[4][4];
			 System.out.println("start getdata");
			 
			try {
				 if(soc.getKeepAlive()) return false;
				 else if(soc.isClosed()) return false;
				dins.readFully(b, 0, 16);
				for(int i=0;i<16;i++) System.out.print("*"+b[i]);
				if(b[0]==9) {
					return false;
				}
				else {
					for(int j=0;j<16;j++) b1[j/4][j%4] =b[j];
					Board.boardUpdate(b1);
					
					}
			}catch(Exception e) {
				System.out.println("Server Connection reset");
				//e.printStackTrace();
				return false;
			}
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<4;j++) System.out.print("&"+b1[i][j]);
				System.out.println();
			}
			return true;
		}
		 
	  
	    void updateBoard( byte[][] newboard ) {
	    	
			for (int i = 0; i < 16; i++) { // Adding buttons
				buttonArray[i].type= newboard[i/4][i%4];
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
				 	case 4:
				 		buttonArray[i].setBackground(Color.BLACK);
				 }
				 }
		}
	   
	    void changeStatus(String statusmessage) {
	    	statusBar.setText(statusmessage);
	    }
	    
	    
	    public void finalize(){
		try {
		dins.close();
		dous.close();
		soc.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	} 
	    
	    
	} // class GameUi end
	

}// class LgameClient end
