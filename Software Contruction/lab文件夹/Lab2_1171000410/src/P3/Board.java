package P3;

public class Board {
	private int boardSize;  //棋盘大小
	private Piece[][] boardPosition;  //棋子在棋盘的位置
	
	// Abstraction function:
    //   Board 代表棋子所处的棋盘，boardSize代表了棋盘的边长，
	//   boardPosition代表pieces在棋盘上的位置
    // Representation invariant:
    //   Board不能映射为空 
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	
	/**Return this board's size*/
	public int getBoardSize() {
		return this.boardSize;
	}
	
	/**Return this board's position of pieces*/
	public Piece[][] getBoardPosition() {
		return this.boardPosition;
	}
	
	/**
	 * Set board parameters
	 * @param type of board
	 * @param size of board 
	 * @param position of board
	 */
	public void setBoard(int size ,Piece position[][]) {
		this.boardSize = size;
		this.boardPosition = position;
	}
	
	/**
	 * Check the validity of the size of the input data
	 * @param x representing abscissa of piece
	 * @param y representing ordinate of piece
	 * @return true if the the size of input data is reasonable , or false
	 */
	public boolean check(int x ,int y) {
		if((x>=0 && x <= this.boardSize -1) 
				&& (y >=0 && y <= this.boardSize -1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get a chess piece in a specified position
	 * @param x representing Abscissa of piece
	 * @param y representing ordinate of piece
	 * @return piece in the position
	 * @throws Exception if the input position is illegal
	 */
	public Piece getBoardPiece(int x , int y)throws Exception {
		if(this.check(x, y)) {
			return boardPosition[x][y];	
		}else {
			System.out.println("该位置超出棋盘范围！");
			throw new Exception("该位置不合法！");
		}
	}
	
	/**
	 * place the chess pieces in the designated position ,
	 * If there is a chess piece in that position, throw an exception
	 * @param piece representing chess piece to be replaced
	 * @param x representing abscissa of piece
	 * @param y representing ordinate of piece
	 * @throws Exception if the input position is illegal
	 */
	public void setBoardPosition(Piece piece, int x , int y)throws Exception{	
		if(!check(x,y)) {
			System.out.println("该位置超出棋盘范围！");
			throw new Exception("该位置不合法！");
		}
		
		if( piece != null && boardPosition[x][y].getPieceState() == 1) {
			System.out.println("该位置已经有棋子！");
			throw new Exception("该位置已经有棋子！");
		}else {
			boardPosition[x][y] = piece;
		}
	}
	
	/**
	 * Set the state of a piece in the designed place
	 * @param x representing abscissa of piece
	 * @param y representing ordinate of piece
	 * @param newState a designed state
	 * @throws Exception if the input position is illegal
	 */
	public void setBoardPositionState(int x , int y ,int newState)throws Exception{	
		if(!check(x,y)) {
			System.out.println("该位置超出棋盘范围！");
			throw new Exception("该位置不合法！");
		}
		
		boardPosition[x][y].setPieceState(newState);
	}
	
}
