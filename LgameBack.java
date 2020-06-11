import java.util.Arrays;
import java.util.Scanner;



/* LgameProject*
 *  
 * 1.0.0 * 
 * 
 * 6.10 * 
 * 
 * */




/* main function - 함수 실행
 *
 * 
*/
public class Lgame {
	public static void main(String[] args) {
		LgameControler controler = new LgameControler();
	}
}

class LgameControler{
	private Board Board;
	private Lblock Lblock1;
	private Lblock Lblock2;
	private Oblock Oblock3;
	private Oblock Oblock4;
	private short turn=1;
	private short[] tempPosL = new short[4];

	
	LgameControler(){
		this.run();
	}
	void run() {
		Lblock1 = new Lblock((short)1);
		Lblock2 = new Lblock((short)2);
		Oblock3 = new Oblock(3);
		Oblock4 = new Oblock(4);
		Board = new Board(Lblock1, Lblock2, Oblock3,Oblock4);	//첫단계
		
		Scanner scan = new Scanner(System.in);
		
		while(Board.isGameContinue(turn)){	// 더 놓을 수 있는 상태인지 판단함.
			Board.printBoard();
			boolean flag=true;
			System.out.println(turn+"의 턴입니다.");
			do {							//L블럭 위치 입력받기
				System.out.println("yx의 형태로 4개의 좌표(0~3)를 입력해주세요");
				for(int i =0 ; i<4;i++) {
					tempPosL[i] = scan.nextShort();
				}
				Arrays.sort(tempPosL);
				
				if(turn == 1) {
					Lblock1.setTempInfo(Board.getBoard(),tempPosL);
					flag = !Lblock1.update(); //업데이트를 하면 flag를 false로 바꾸어 루프문 탈출
				}
				else {
					Lblock2.setTempInfo(Board.getBoard(),tempPosL);
					flag = !Lblock2.update(); //업데이트를 하면 flag를 false로 바꾸어 루프문 탈출
				}
			}while(flag);	//업데이트 되면 나가기, 아니면 루프계속
			Board.Lupdate(Lblock1);		//L블럭 상태업데이트
			Board.Lupdate(Lblock2);
			Board.printBoard();
			flag = true;
			do {							//O블럭 위치 입력받기
				System.out.println("type(3,4)값과 놓을 위치를 입력해주세요");
				int type = scan.nextInt();
				short pos = scan.nextShort();
				
				if(type == 3) {
					Oblock3.setTempInfo(Board.getBoard(),pos);
					flag = !Oblock3.update();	//업데이트를 하면 flag를 false로 바꾸어 루프문 탈출
				}
				else {
					Oblock4.setTempInfo(Board.getBoard(),pos);
					flag = !Oblock4.update();	//업데이트를 하면 flag를 false로 바꾸어 루프문 탈출
				}
			}while(flag);
			Board.Oupdate(Oblock3);		//O블럭 상태업데이트
			Board.Oupdate(Oblock4);
			
			if(turn == 1) turn =2;
			else turn =1;
		}
		if(turn==1)System.out.println("player 2의 승리입니다.");
		else System.out.println("player 1의 승리입니다.");
		scan.close();
	}
	
}


class Board{			// 0: 빈곳 1,2:플레이어 블록 3,4: O블럭
	private short[][] board = new short[4][4];	// 보드 상태 저장
	Board(Lblock L1, Lblock L2, Oblock O3,Oblock O4) {
		this.Lupdate(L1);
		this.Lupdate(L2);
		this.Oupdate(O3);
		this.Oupdate(O4);
	};
	void Lupdate(Lblock l) {				//L블럭에 대한 상태를 저장시킴
		short[] temp =l.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==l.playerkey) {	// 기존정보 삭제
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<4;i++) {			//새정보 저장
			
			board[temp[i]/10][temp[i]%10]=l.playerkey;
		}
	}
	void Oupdate(Oblock o) {
		short temp =o.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==o.type) {	// 기존정보 삭제
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<2;i++) {			//새정보 저장
			board[temp/10][temp%10]=(short)o.type;
		}
	}
	boolean isGameContinue(short playerkey) {	// 둘 곳이 있으면 true, 없으면 false리
		for(int i =0; i<4;i++) {
			for(int j =0; j<2;j++) {				//왼쪽 두줄을 시작 지점으로 봄. 가로 일자에 대한 체크 
				if((board[i][j]==0||board[i][j]==playerkey)		//가로일자로 놓을 수 있는곳이 있는지 확인
						&&(board[i][j+1]==0||board[i][j+1]==playerkey)
						&&(board[i][j+2]==0||board[i][j+2]==playerkey)) {
					if(i==0) {		//가로로 첫째줄인경우
						if((board[i+1][j]==0 || board[i+1][j]==playerkey 
								||board[i+1][j+2]==0 || board[i+1][j+2]==playerkey)
								&&!(board[i][j]==playerkey && board[i+1][j+2]==playerkey)
								&&!(board[i+1][j]==playerkey && board[i][j+2]==playerkey)) {
							return true;
						}
					}
					else if(i ==3) {                 				//제일 아래줄일 때
						if((board[i-1][j]==0 || board[i-1][j]==playerkey 
								||board[i-1][j+2]==0 || board[i-1][j+2]==playerkey)
								&&!(board[i][j]==playerkey && board[i-1][j+2]==playerkey)
								&&!(board[i-1][j]==playerkey && board[i][j+2]==playerkey)
								) {
							return true;
						}
					}
					else {
						if(((board[i-1][j]==0 || board[i-1][j]==playerkey 
								||board[i-1][j+2]==0 || board[i-1][j+2]==playerkey)
								||(board[i+1][j]==0 || board[i+1][j]==playerkey 
								||board[i+1][j+2]==0 || board[i+1][j+2]==playerkey))
								&&!(board[i][j]==playerkey && board[i+1][j+2]==playerkey)
								&&!(board[i][j]==playerkey && board[i-1][j+2]==playerkey)
								&&!(board[i][j+2]==playerkey && board[i+1][j]==playerkey)
								&&!(board[i][j+2]==playerkey && board[i-1][j]==playerkey)
								){
							return true;
						}
					}
				}
			}
		}
		for(int j =0; j<4;j++) {
			for(int i =0; i<2;i++) {				//윗 두줄이 시작점, 세로로 일자를 체크
				if((board[i][j]==0||board[i][j]==playerkey)		//세로일자로 놓을 수 있는곳이 있는지 확인
						&&(board[i+1][j]==0||board[i+1][j]==playerkey)
						&&(board[i+2][j]==0||board[i+2][j]==playerkey)) {
					if(j==0) {										//제일 왼쪽
						System.out.println("check");
						if(board[i][j+1]==0 || board[i][j+1]==playerkey 
								||board[i+2][j+1]==0 || board[i+2][j+1]==playerkey
								&&!(board[i][j]==playerkey && board[i+2][j+1]==playerkey)
								&&!(board[i][j+1]==playerkey && board[i+2][j]==playerkey)) {
							return true;
						}
					}
					else if(j==3) {                 				//제일 오른쪽 줄
						if(board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey
								&&!(board[i][j]==playerkey && board[i+2][j-1]==playerkey)
								&&!(board[i][j-1]==playerkey && board[i+2][j]==playerkey)) {
							return true;
						}
					}
					else {
						if(board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey
								||board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey
								&&!(board[i][j]==playerkey && board[i+2][j+1]==playerkey)
								&&!(board[i][j]==playerkey && board[i+2][j-1]==playerkey)
								&&!(board[i+2][j]==playerkey && board[i][j+1]==playerkey)
								&&!(board[i+2][j]==playerkey && board[i][j-1]==playerkey)) {
							return true;
						}
					}
				}
			}
		}
		return false;
		
	}
	
	short[][] getBoard(){
		return board;
	}
	void printBoard() {
		System.out.println("----------------------------");
		for(int i =0; i<4;i++) {
			for(int j=0;j<4;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println("----------------------------");
	}
}

abstract class block{
	abstract boolean checkAvailable();
	abstract boolean update();
}
class Lblock extends block{
	private short[] position = new short[4];
	short playerkey;				//player는 1 또는 2
	private short[][] board;
	private short[] pos;
	Lblock(short playerkey) {
		this.playerkey= playerkey;	
		if(playerkey==1) {			// 시작할때 포지션
			position[0] = 01;
			position[1] = 02;
			position[2] = 12;
			position[3] = 22;
		}
		else {						// 시작할때 포지션 2
			position[0] = 11;
			position[1] = 21;
			position[2] = 31;
			position[3] = 32;
		}
	}
	boolean checkAvailable() {		// 그 위치에 블럭을 놓을 수 있는지 확인 & L모양인지를 확인 & 중복임을 확인
		 boolean flag=false;
		 for(int i=0; i<4; i++) {			// 같은 위치에 L블럭을 뒀는지 판단.
			 if(board[pos[i]/10][pos[i]%10]!=playerkey) {	// 위치가 다르면 flag를 true로 바꿈. flag가 true면 다음 검사를 진행함
				 flag=true;
			 }
		 }
		 if(flag==false) return false;
		 
		 
		 for(int i =0;i<4;i++) {								
			 if(board[pos[i]/10][pos[i]%10]!=playerkey && 		// 놓을 곳에 상대의 블럭이나, O블럭이 있으면 불가
					 board[pos[i]/10][pos[i]%10]!=0) {
				 return false;
			 }
		 }
		 
		 if(pos[0]+1==pos[1]&&pos[1]+10==pos[2]&&pos[2]+10==pos[3]) {}		//세로로 길쭉 중에서도 아래쪽에 블럭이 붙은 모양 
		 else if(pos[0]+1==pos[1]&&pos[0]+10==pos[2]&&pos[2]+10==pos[3]) {}
		 else if(pos[0]+10==pos[1]&&pos[1]+1==pos[2]&&pos[2]+1==pos[3]) {}
		 else if(pos[0]+10==pos[3]&&pos[1]+1==pos[2]&&pos[2]+1==pos[3]) {}
		 else if(pos[0]+1==pos[1]&&pos[1]+1==pos[2]&&pos[0]+10==pos[3]) {}
		 else if(pos[0]+1==pos[1]&&pos[1]+1==pos[2]&&pos[2]+10==pos[3]) {}
		 else if(pos[0]+10==pos[1]&&pos[1]+10==pos[3]&&pos[2]+1==pos[3]) {}
		 else if(pos[0]+10==pos[1]&&pos[1]+10==pos[2]&&pos[2]+1==pos[3]) {}
		 else return false;
		 
		 return true;
	}
	boolean update() {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
		if(checkAvailable()) {
			for(int i=0;i<4;i++) {
				this.position[i]=pos[i];
			}
			return true;
		}
		else return false;
	}
	short[] getPosition() {
		return position;
	}
	void setTempInfo(short[][] board,short[] pos) {
		this.board = board;
		this.pos = pos;
	}
}
class Oblock extends block{
	private short position;
	private short[][] board;
	int type;
	private short pos;
	Oblock(int type){						// 시작시 정보
		this.type = type;
		if(type==3) {		//type은 3 과 4가 될것임
			position=00;
		}
		else position = 33;
	}
	
	
	boolean checkAvailable() {
		if(pos == position) return true;	//같은 O블럭 즉, 이동하지 않을때
		if(board[pos/10][pos%10]!=0) return false;	// 빈 블록으로만 이동할 것임.
		return true;
	}
	
	
	boolean update() {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
		if(checkAvailable()) {
			position=pos;
			return true;
		}
		return false;
	}
	short getPosition() {
		return position;
	}
	void setTempInfo(short[][] board, short pos) {
		this.board = board;
		this.pos = pos;
	}
}