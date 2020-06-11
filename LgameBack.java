import java.util.Arrays;
import java.util.Scanner;



/* LgameProject*
 *  
 * 1.0.0 * 
 * 
 * 6.10 * 
 * 
 * */




/* main function - �Լ� ����
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
		Board = new Board(Lblock1, Lblock2, Oblock3,Oblock4);	//ù�ܰ�
		
		Scanner scan = new Scanner(System.in);
		
		while(Board.isGameContinue(turn)){	// �� ���� �� �ִ� �������� �Ǵ���.
			Board.printBoard();
			boolean flag=true;
			System.out.println(turn+"�� ���Դϴ�.");
			do {							//L�� ��ġ �Է¹ޱ�
				System.out.println("yx�� ���·� 4���� ��ǥ(0~3)�� �Է����ּ���");
				for(int i =0 ; i<4;i++) {
					tempPosL[i] = scan.nextShort();
				}
				Arrays.sort(tempPosL);
				
				if(turn == 1) {
					Lblock1.setTempInfo(Board.getBoard(),tempPosL);
					flag = !Lblock1.update(); //������Ʈ�� �ϸ� flag�� false�� �ٲپ� ������ Ż��
				}
				else {
					Lblock2.setTempInfo(Board.getBoard(),tempPosL);
					flag = !Lblock2.update(); //������Ʈ�� �ϸ� flag�� false�� �ٲپ� ������ Ż��
				}
			}while(flag);	//������Ʈ �Ǹ� ������, �ƴϸ� �������
			Board.Lupdate(Lblock1);		//L�� ���¾�����Ʈ
			Board.Lupdate(Lblock2);
			Board.printBoard();
			flag = true;
			do {							//O�� ��ġ �Է¹ޱ�
				System.out.println("type(3,4)���� ���� ��ġ�� �Է����ּ���");
				int type = scan.nextInt();
				short pos = scan.nextShort();
				
				if(type == 3) {
					Oblock3.setTempInfo(Board.getBoard(),pos);
					flag = !Oblock3.update();	//������Ʈ�� �ϸ� flag�� false�� �ٲپ� ������ Ż��
				}
				else {
					Oblock4.setTempInfo(Board.getBoard(),pos);
					flag = !Oblock4.update();	//������Ʈ�� �ϸ� flag�� false�� �ٲپ� ������ Ż��
				}
			}while(flag);
			Board.Oupdate(Oblock3);		//O�� ���¾�����Ʈ
			Board.Oupdate(Oblock4);
			
			if(turn == 1) turn =2;
			else turn =1;
		}
		if(turn==1)System.out.println("player 2�� �¸��Դϴ�.");
		else System.out.println("player 1�� �¸��Դϴ�.");
		scan.close();
	}
	
}


class Board{			// 0: ��� 1,2:�÷��̾� ��� 3,4: O��
	private short[][] board = new short[4][4];	// ���� ���� ����
	Board(Lblock L1, Lblock L2, Oblock O3,Oblock O4) {
		this.Lupdate(L1);
		this.Lupdate(L2);
		this.Oupdate(O3);
		this.Oupdate(O4);
	};
	void Lupdate(Lblock l) {				//L���� ���� ���¸� �����Ŵ
		short[] temp =l.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==l.playerkey) {	// �������� ����
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<4;i++) {			//������ ����
			
			board[temp[i]/10][temp[i]%10]=l.playerkey;
		}
	}
	void Oupdate(Oblock o) {
		short temp =o.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==o.type) {	// �������� ����
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<2;i++) {			//������ ����
			board[temp/10][temp%10]=(short)o.type;
		}
	}
	boolean isGameContinue(short playerkey) {	// �� ���� ������ true, ������ false��
		for(int i =0; i<4;i++) {
			for(int j =0; j<2;j++) {				//���� ������ ���� �������� ��. ���� ���ڿ� ���� üũ 
				if((board[i][j]==0||board[i][j]==playerkey)		//�������ڷ� ���� �� �ִ°��� �ִ��� Ȯ��
						&&(board[i][j+1]==0||board[i][j+1]==playerkey)
						&&(board[i][j+2]==0||board[i][j+2]==playerkey)) {
					if(i==0) {		//���η� ù°���ΰ��
						if((board[i+1][j]==0 || board[i+1][j]==playerkey 
								||board[i+1][j+2]==0 || board[i+1][j+2]==playerkey)
								&&!(board[i][j]==playerkey && board[i+1][j+2]==playerkey)
								&&!(board[i+1][j]==playerkey && board[i][j+2]==playerkey)) {
							return true;
						}
					}
					else if(i ==3) {                 				//���� �Ʒ����� ��
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
			for(int i =0; i<2;i++) {				//�� ������ ������, ���η� ���ڸ� üũ
				if((board[i][j]==0||board[i][j]==playerkey)		//�������ڷ� ���� �� �ִ°��� �ִ��� Ȯ��
						&&(board[i+1][j]==0||board[i+1][j]==playerkey)
						&&(board[i+2][j]==0||board[i+2][j]==playerkey)) {
					if(j==0) {										//���� ����
						System.out.println("check");
						if(board[i][j+1]==0 || board[i][j+1]==playerkey 
								||board[i+2][j+1]==0 || board[i+2][j+1]==playerkey
								&&!(board[i][j]==playerkey && board[i+2][j+1]==playerkey)
								&&!(board[i][j+1]==playerkey && board[i+2][j]==playerkey)) {
							return true;
						}
					}
					else if(j==3) {                 				//���� ������ ��
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
	short playerkey;				//player�� 1 �Ǵ� 2
	private short[][] board;
	private short[] pos;
	Lblock(short playerkey) {
		this.playerkey= playerkey;	
		if(playerkey==1) {			// �����Ҷ� ������
			position[0] = 01;
			position[1] = 02;
			position[2] = 12;
			position[3] = 22;
		}
		else {						// �����Ҷ� ������ 2
			position[0] = 11;
			position[1] = 21;
			position[2] = 31;
			position[3] = 32;
		}
	}
	boolean checkAvailable() {		// �� ��ġ�� ���� ���� �� �ִ��� Ȯ�� & L��������� Ȯ�� & �ߺ����� Ȯ��
		 boolean flag=false;
		 for(int i=0; i<4; i++) {			// ���� ��ġ�� L���� �״��� �Ǵ�.
			 if(board[pos[i]/10][pos[i]%10]!=playerkey) {	// ��ġ�� �ٸ��� flag�� true�� �ٲ�. flag�� true�� ���� �˻縦 ������
				 flag=true;
			 }
		 }
		 if(flag==false) return false;
		 
		 
		 for(int i =0;i<4;i++) {								
			 if(board[pos[i]/10][pos[i]%10]!=playerkey && 		// ���� ���� ����� ���̳�, O���� ������ �Ұ�
					 board[pos[i]/10][pos[i]%10]!=0) {
				 return false;
			 }
		 }
		 
		 if(pos[0]+1==pos[1]&&pos[1]+10==pos[2]&&pos[2]+10==pos[3]) {}		//���η� ���� �߿����� �Ʒ��ʿ� ���� ���� ��� 
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
	boolean update() {				// ���� ���� �� �ִ� ���� Ȯ�� �� �Ŀ� ������Ʈ��. ������Ʈ�� ������ true �������� false�� ������
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
	Oblock(int type){						// ���۽� ����
		this.type = type;
		if(type==3) {		//type�� 3 �� 4�� �ɰ���
			position=00;
		}
		else position = 33;
	}
	
	
	boolean checkAvailable() {
		if(pos == position) return true;	//���� O�� ��, �̵����� ������
		if(board[pos/10][pos%10]!=0) return false;	// �� ������θ� �̵��� ����.
		return true;
	}
	
	
	boolean update() {				// ���� ���� �� �ִ� ���� Ȯ�� �� �Ŀ� ������Ʈ��. ������Ʈ�� ������ true �������� false�� ������
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