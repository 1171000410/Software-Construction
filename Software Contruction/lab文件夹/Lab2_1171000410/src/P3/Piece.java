package P3;

public class Piece {
	private String pieceName; //棋子名称
	private int pieceState;  //0未放置，1已放置。-1被remove
	private int pieceX;    //横坐标
	private int pieceY;    //纵坐标
	
	// Abstraction function:
    //   Piece代表棋盘上的棋子 ，pieceName代表棋子的名称，pieceState代表棋子的放置状态，
	//   pieceX代表棋子的横坐标，pieceY代表棋子的纵坐标
    // Representation invariant:
    //   piece不能映射为空
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	
	/**Constructor*/
	public Piece(String name,int state, int x ,int y) {
		this.pieceName = name;
		this.pieceState = state;
		this.pieceX = x;
		this.pieceY = y;
	}
	
	/**Return this piece's name*/
	public String getPieceName() {
		return this.pieceName;
	}
	
	/**Return this piece's state*/
	public int getPieceState() {
		return this.pieceState;
	}
	
	/**Return this piece's abscissa*/
	public int getPieceX() {
		return this.pieceX;
	}
	
	/**Return this piece's ordinate*/
	public int getPieceY() {
		return this.pieceY;
	}
	
	/**
	 * Set this piece's name
	 * @param name which is to be set
	 */
	public void setPieceName(String name) {
		assert name != null;
		this.pieceName = name;
	}
	
	/**
	 * Set this piece's state
	 * @param state which is to be set
	 */
	public void setPieceState(int state) {
		this.pieceState = state;
	}
	
	/**
	 * Set this piece's abscissa
	 * @param x which is to be set
	 */
	public void setPieceX(int x) {
		this.pieceX = x;
	}
	
	/**
	 * Set this piece's ordinate
	 * @param y which is to be set
	 */
	public void setPieceY(int y) {
		this.pieceY = y;
	}
	
	/**
	 * Remove this piece from a board
	 * Set the state , abscissa and ordinate to -1
	 */
	public void remove() {
		this.setPieceState(-1);
		this.setPieceX(-1);
		this.setPieceY(-1);
	}
	
}
