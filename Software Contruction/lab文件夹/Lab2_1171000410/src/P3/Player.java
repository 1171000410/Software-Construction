package P3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;  //棋手名
	private Set<Piece> remaining = new HashSet<Piece>();  //玩家剩余棋子
	private String history = new String(); //操作历史
		
    // Abstraction function:
    //   Player映射操作pieces的玩家，playerName为玩家的名称，
	//  remaining代表玩家在棋盘上的棋子，history的字符串为玩家历史
    // Representation invariant:
    //   Player不能映射为空
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	//	  使用Collections.unmodifiableSet不可变类型
	
	/**Constructor*/
	public Player(String p) {
		this.playerName = p;
	}
	
	/**Return this player's name*/
	public String getPlayerName() {
		return this.playerName;
	}
	
	/**Return this player's remaining pieces*/
	public Set<Piece> getRemaining(){
		return Collections.unmodifiableSet(remaining);   //immutable type
	}
	
	/**Return this player's history*/
	public String getHistory() {
		return this.history;
	}
	
	/**
	 * Set this player's name
	 * @param name which is to be set
	 */
	public void setPlayerName(String name) {
		assert name != null;
		this.playerName = name;
	}
	
	/**
	 * Get a piece by the pieceName from the remaining
	 * Only for putPiece of go
	 * @param pieceName which is to be searched
	 * @return piece if it's found , or null
	 */
	public Piece getPiece(String pieceName){
		Piece piece;
		for(Piece p : remaining) {
			if(p.getPieceName().equals(pieceName) && p.getPieceState() == 0) {
				piece = p;
				return piece;
			}
		}
		return null;
	}
	
	/**
	 * Add pieces to a player 
	 * @param piece which is to be added
	 * @return true if the player does not have this piece yet , or false
	 */
	public boolean addPieces(Piece piece) {
		if(remaining.contains(piece)) {
			return false;
		}else {
			remaining.add(piece);
			return true;
		}
	}
	
	/**
	 * Add a history to the player's operation history
	 * @param oneHistory a non-null which is to be added
	 */
	public void addHistory(String oneHistory) {
		assert oneHistory != null;
		this.history = this.history.concat(oneHistory + "\n");
	}
	
	/**
	 * Judge whether this player own the piece
	 * @param piece which is to be judge
	 * @return true if the player own the piece , or false
	 */
	public boolean judgeOwnPiece(Piece piece) {
		if(remaining.contains(piece)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Count the quantity of the pieces in the board
	 * @return the amount of pieces
	 */
	public int countQuantityOfPieceInBoard() {
		int num = 0;
		for(Piece p : this.getRemaining()) {
			if(p.getPieceState() == 1) {
				num ++;
			}
		}
		return num;
	}
}