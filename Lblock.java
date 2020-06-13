
public class Lblock{
	private short[] position = new short[4];
	short playerkey;				//player는 1 또는 2
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
	boolean checkAvailable(Board board) {		// 그 위치에 블럭을 놓을 수 있는지 확인 & L모양인지를 확인 & 중복임을 확인
		 boolean flag=false;
		 for(int i=0; i<4; i++) {			// 같은 위치에 L블럭을 뒀는지 판단.
			 if(board.board[pos[i]/10][pos[i]%10]!=playerkey) {	// 위치가 다르면 flag를 true로 바꿈. flag가 true면 다음 검사를 진행함
				 flag=true;
			 }
		 }
		 if(flag==false) return false;
		 
		 
		 for(int i =0;i<4;i++) {								
			 if(board.board[pos[i]/10][pos[i]%10]!=playerkey && 		// 놓을 곳에 상대의 블럭이나, O블럭이 있으면 불가
					 board.board[pos[i]/10][pos[i]%10]!=0) {
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
	boolean update(Board board) {				// 블럭을 놓을 수 있는 지를 확인 한 후에 업데이트함. 업데이트를 했으면 true 못했으면 false를 리턴함
		if(checkAvailable(board)) {
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
	void setTempInfo(short[] pos) {
		this.pos = pos;
	}
}
