
public class Oblock{
	private byte position;
	int type;
	private byte pos;
	Oblock(int type){						// 시작시 정보
		this.type = type;
		if(type==3) {		//type은 3 과 4가 될것임
			position=00;
		}
		else position = 33;
	}
	Oblock(int type, byte pos){
		this.type = type;
		position =pos;
	}
	
	boolean checkAvailable(Board board) {
		if(pos == position) return true;	//같은 O블럭 즉, 이동하지 않을때
		if(board.board[pos/10][pos%10]!=0) return false;	// 빈 블록으로만 이동할 것임.
		return true;
	}
	
	
	boolean update(Board board) {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
		if(checkAvailable(board)) {
			position=pos;
			return true;
		}
		return false;
	}
	byte getPosition() {
		return position;
	}
	void setTempInfo(byte pos) {
		this.pos = pos;
	}
}
