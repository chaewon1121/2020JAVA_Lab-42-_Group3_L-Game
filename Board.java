
public class Board{			// 0: 빈곳 1,2:플레이어 블록 3,4: O블럭
	byte[][] board = new byte[4][4];	// 보드 상태 저장
	Board(Lblock L1, Lblock L2, Oblock O3,Oblock O4) {
		this.Lupdate(L1);
		this.Lupdate(L2);
		this.Oupdate(O3);
		this.Oupdate(O4);
	};
	void Lupdate(Lblock l) {				//L블럭에 대한 상태를 저장시킴
		byte[] temp =l.getPosition();
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
				//새정보 저장
			board[temp/10][temp%10]=(byte)o.type;
		
	}
	boolean isGameContinue(byte playerkey) {	// 둘 곳이 있으면 true, 없으면 false리
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
	
	byte[][] getBoard(){
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
	
	void boardUpdate(byte[][] b) {
	      for(int i = 0; i<4;i++) {
	         for(int j = 0; j<4;j++) {
	            this.board[i][j] = b[i][j];
	         }
	      }
	   }

	
}
