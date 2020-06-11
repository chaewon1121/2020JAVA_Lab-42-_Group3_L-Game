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
		
	}

}

class Board{			// 0: ��� 1,2:�÷��̾� ��� 3: O��
	private short[][] board = new short[4][4];	// ���� ���� ����
	Board() {};
	void update(Lblock l) {				//L���� ���� ���¸� �����Ŵ
		short[] temp =l.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==l.playerkey) {	// �������� ����
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<4;i++) {			//������ ����
			board[temp[i]%10][temp[i]/10]=l.playerkey;
		}
	}
	void update(Oblock o) {
		short[] temp =o.getPosition();
		for(int i=0;i<2;i++) {
			for(int j =0;j<2;j++) {
				if(board[i][j]==3) {	// �������� ����
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<2;i++) {			//������ ����
			board[temp[i]%10][temp[i]/10]=3;
		}
	}
	boolean isGameOver(short playerkey) {
		boolean flag = false;
		for(int i =0; i<4;i++) {
			for(int j =0; j<2;j++) {				//���� ������ ���� �������� ��. ���� ���ڿ� ���� üũ 
				if((board[i][j]==0||board[i][j]==playerkey)&&		//�������ڷ� ���� �� �ִ°��� �ִ��� Ȯ��
						(board[i][j+2]==0||board[i][j+2]==playerkey)) {
					if(i==0) {		//���η� ù°���ΰ��
						if(board[i+1][j]==0 || board[i+1][j]==playerkey 
								||board[i+1][j+2]==0 || board[i+1][j+2]==playerkey) {
							flag = true;
						}
					}
					else if(i ==3) {                 				//���� �Ʒ����� ��
						if(board[i-1][j]==0 || board[i-1][j]==playerkey 
								||board[i-1][j+2]==0 || board[i-1][j+2]==playerkey) {
							flag = true;
						}
					}
					else {
						if(board[i-1][j]==0 || board[i-1][j]==playerkey 
								||board[i-1][j+2]==0 || board[i-1][j+2]==playerkey
								||board[i-1][j]==0 || board[i-1][j]==playerkey 
								||board[i-1][j+2]==0 || board[i-1][j+2]==playerkey) {
							flag = true;
						}
					}
				}
			}
		}
		for(int j =0; j<4;j++) {
			for(int i =0; i<2;i++) {				//�� ������ ������, ���η� ���ڸ� üũ
				if((board[i][j]==0||board[i][j]==playerkey)&&		//�������ڷ� ���� �� �ִ°��� �ִ��� Ȯ��
						(board[i+2][j]==0||board[i+2][j]==playerkey)) {
					if(j==0) {		//���η� ù°���ΰ��
						if(board[i][j+1]==0 || board[i][j+1]==playerkey 
								||board[i+2][j+1]==0 || board[i+2][j+1]==playerkey) {
							flag = true;
						}
					}
					else if(j==3) {                 				//���� �Ʒ����� ��
						if(board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey) {
							flag = true;
						}
					}
					else {
						if(board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey
								||board[i][j-1]==0 || board[i][j-1]==playerkey 
								||board[i+2][j-1]==0 || board[i+2][j-1]==playerkey) {
							flag = true;
						}
					}
				}
			}
		}
		return flag;
		
	}
}

abstract class block{
	abstract boolean checkAvailable();
	abstract boolean update();
}
class Lblock extends block{
	private short[] position = new short[4];
	short playerkey;				//player�� 1 �Ǵ� 2
	Lblock(short playerkey) {
		this.playerkey= playerkey;	
		if(playerkey==1) {			// �����Ҷ� ������
			position[0] = 10;
			position[1] = 20;
			position[2] = 21;
			position[3] = 22;
		}
		else {						// �����Ҷ� ������ 2
			position[0] = 11;
			position[1] = 12;
			position[2] = 13;
			position[3] = 23;
		}
	}
	boolean checkAvailable(short[][] board,short[] pos) {		// �� ��ġ�� ���� ���� �� �ִ��� Ȯ�� & L��������� Ȯ��
		 for(int i =0;i<4;i++) {								
			 if(board[pos[i]%10][pos[i]/10]!=playerkey && 		// ���� ���� ����� ���̳�, O���� ������ �Ұ�
					 board[pos[i]%10][pos[i]/10]!=0) {
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
	boolean update(short[][] board,short[] pos) {				// ���� ���� �� �ִ� ���� Ȯ�� �� �Ŀ� ������Ʈ��. ������Ʈ�� ������ true �������� false�� ������
		if(checkAvailable(board, pos)) {
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
}
class Oblock extends block{
	private short[] position = new short[2];
	Oblock(){						// ���۽� ����
		position[0]=00;
		position[1]=33;
	}
	boolean checkAvailable(short[][] board,int key, short pos) {
		if(pos == position[key]) return true;	//���� O�� ��, �̵����� ������
		if(board[pos%10][pos/10]!=0) return false;	// �� ������θ� �̵��� ����.
		
		return true;
	}
	boolean update(short[][] board, int key, short pos) {				// ���� ���� �� �ִ� ���� Ȯ�� �� �Ŀ� ������Ʈ��. ������Ʈ�� ������ true �������� false�� ������
		if(checkAvailable(board, key, pos)) {
			position[key]=pos;
			return true;
		}
		return false;
	}
	short[] getPosition() {
		return position;
	}
}