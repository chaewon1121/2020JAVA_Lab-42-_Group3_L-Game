
public class Oblock{
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
