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
		
	}

}

class Board{			// 0: 빈곳 1,2:플레이어 블록 3: O블럭
	private short[][] board = new short[4][4];	// 보드 상태 저장
	Board() {};
	void update(Lblock l) {				//L블럭에 대한 상태를 저장시킴
		short[] temp =l.getPosition();
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==l.playerkey) {	// 기존정보 삭제
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<4;i++) {			//새정보 저장
			board[temp[i]%10][temp[i]/10]=l.playerkey;
		}
	}
	void update(Oblock o) {
		short[] temp =o.getPosition();
		for(int i=0;i<2;i++) {
			for(int j =0;j<2;j++) {
				if(board[i][j]==3) {	// 기존정보 삭제
					board[i][j]=0;
				}
			}
		}
		for(int i=0;i<2;i++) {			//새정보 저장
			board[temp[i]%10][temp[i]/10]=3;
		}
	}
	boolean isGameOver(short playerkey) {
		boolean flag = false;
		for(int i =0; i<4;i++) {
			for(int j =0; j<2;j++) {				//왼쪽 두줄을 시작 지점으로 봄. 가로 일자에 대한 체크 
				if((board[i][j]==0||board[i][j]==playerkey)&&		//가로일자로 놓을 수 있는곳이 있는지 확인
						(board[i][j+2]==0||board[i][j+2]==playerkey)) {
					if(i==0) {		//가로로 첫째줄인경우
						if(board[i+1][j]==0 || board[i+1][j]==playerkey 
								||board[i+1][j+2]==0 || board[i+1][j+2]==playerkey) {
							flag = true;
						}
					}
					else if(i ==3) {                 				//제일 아래줄일 때
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
			for(int i =0; i<2;i++) {				//윗 두줄이 시작점, 세로로 일자를 체크
				if((board[i][j]==0||board[i][j]==playerkey)&&		//세로일자로 놓을 수 있는곳이 있는지 확인
						(board[i+2][j]==0||board[i+2][j]==playerkey)) {
					if(j==0) {		//세로로 첫째줄인경우
						if(board[i][j+1]==0 || board[i][j+1]==playerkey 
								||board[i+2][j+1]==0 || board[i+2][j+1]==playerkey) {
							flag = true;
						}
					}
					else if(j==3) {                 				//제일 아래줄일 때
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
	short playerkey;				//player는 1 또는 2
	Lblock(short playerkey) {
		this.playerkey= playerkey;	
		if(playerkey==1) {			// 시작할때 포지션
			position[0] = 10;
			position[1] = 20;
			position[2] = 21;
			position[3] = 22;
		}
		else {						// 시작할때 포지션 2
			position[0] = 11;
			position[1] = 12;
			position[2] = 13;
			position[3] = 23;
		}
	}
	boolean checkAvailable(short[][] board,short[] pos) {		// 그 위치에 블럭을 놓을 수 있는지 확인 & L모양인지를 확인
		 for(int i =0;i<4;i++) {								
			 if(board[pos[i]%10][pos[i]/10]!=playerkey && 		// 놓을 곳에 상대의 블럭이나, O블럭이 있으면 불가
					 board[pos[i]%10][pos[i]/10]!=0) {
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
	boolean update(short[][] board,short[] pos) {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
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
	Oblock(){						// 시작시 정보
		position[0]=00;
		position[1]=33;
	}
	boolean checkAvailable(short[][] board,int key, short pos) {
		if(pos == position[key]) return true;	//같은 O블럭 즉, 이동하지 않을때
		if(board[pos%10][pos/10]!=0) return false;	// 빈 블록으로만 이동할 것임.
		
		return true;
	}
	boolean update(short[][] board, int key, short pos) {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
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