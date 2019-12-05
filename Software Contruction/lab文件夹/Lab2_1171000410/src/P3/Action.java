package P3;

public class Action {
	private Board chessBoard = new Board();
	
	// Abstraction function:
    //   Action映射为棋手的动作，chessBoard是一个Board的对象
    // Representation invariant:
    //   Action和Board不能映射为空
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	
//	/**Return this action's board*/
//	public Board getChessBoard() {
//		return this.chessBoard;
//	}
	
	/**
	 * Set board by designed board
	 * @param board which is to be set
	 */
	public void setChessBoard(Board board) {
		this.chessBoard = board;
	}
	
	/**
	 * Put player's piece to the specified position ,
	 * Add player's history
	 * @param player who owns the piece
	 * @param piece is to be put
	 * @param position the place is to be put in 
	 * @throws Exception if the piece is in a wrong state
	 */
	public void putPiece(Player player , Piece piece , Position position)throws Exception {
		final Piece oldPiece = chessBoard.getBoardPiece(position.getX(), position.getY());
		if(oldPiece.getPieceState() != 1) {
			player.addPieces(piece);
			
			piece.setPieceX(position.getX());
			piece.setPieceY(position.getY());
			piece.setPieceState(1);
			
			this.chessBoard.setBoardPosition(piece, position.getX(), position.getY());
			
				player.addHistory(String.format("%s put a piece %s in (%d,%d)\n", 
						player.getPlayerName(),piece.getPieceName(),position.getX(),position.getY()));
		}else {
			System.out.println("该位置已有棋子，请重新输入");
			throw new Exception("该落子位置发生错误！");
		}

	}
	
	/**
	 * move the player's piece from the oldPosition to the newPosition
	 * @param player who owns the piece
	 * @param oldPosition where the piece stay at first
	 * @param newPosition where the piece stay in the end 
	 * @throws Exception if the piece is in a wrong state or
	 *     	 	if the oldPositon equals to the newPosition
	 */
	public void movePiece(Player player , Position oldPosition , Position newPosition)throws Exception {
		final Piece old_piece = chessBoard.getBoardPiece(oldPosition.getX(), oldPosition.getY());
		Piece piece = new Piece(old_piece.getPieceName(), old_piece.getPieceState(),
				old_piece.getPieceX(), old_piece.getPieceY());
		
		if(! player.judgeOwnPiece(old_piece) ){
			System.out.println("所移动的为对手棋子！");
			
			throw new Exception("所移动的为对手棋子！");
		}
		if(piece.getPieceState() == 1) {
			if(oldPosition.getX() == newPosition.getX() && oldPosition.getY()
					== newPosition.getY()) {
				System.out.println("移动的起始和目的位置相同！");
				throw new Exception("移动的起始和目的位置相同！");
			}
		chessBoard.setBoardPosition(piece, newPosition.getX(), newPosition.getY());
		chessBoard.setBoardPositionState(oldPosition.getX(), oldPosition.getY(), 0);
		
		piece.setPieceX(newPosition.getX()); //设置棋子位置
		piece.setPieceY(newPosition.getY());
		
		player.addPieces(piece);
		player.addHistory(String.format("%s move piece %s from (%d,%d) to (%d,%d)\n", 
					player.getPlayerName(),piece.getPieceName(),oldPosition.getX(),oldPosition.getY(),
					newPosition.getX(),newPosition.getY()));
		}else {
			System.out.println("该棋子不存在！");
			throw new Exception("该棋子不存在！");
		}
	}
	
	/**
	 * Remove the opponent's chess pieces from the position
	 * By the "player, the horizontal and vertical coordinates of a position",
	 * @param player who is raising the piece
	 * @param position where the piece
	 * @throws Exception if the piece doesn't belong to the opponent ,
	 *  		or the piece doesn't exist
	 */
	public void removePiece(Player player , Position position) throws Exception {
		final Piece piece = chessBoard.getBoardPiece(position.getX(), position.getY());
		if(player.judgeOwnPiece(piece)) {
			System.out.println("所提棋子非对手棋子！");
			throw new Exception("所提棋子非对手棋子！");
		}
		
		if(piece.getPieceState() == 1) {
			chessBoard.setBoardPositionState(position.getX(), position.getY(), -1);
			piece.remove();	
			
			player.addHistory(String.format("%s remove piece %s in (%d,%d)\n", 
					player.getPlayerName(),piece.getPieceName(),position.getX(),position.getY()));
		}else {
			System.out.println("该棋子不存在！");
			throw new Exception("该棋子不存在！");
		}
	}
	
	/**
	 * Given the "player, two positions, horizontal and vertical coordinates",
	 * move the chess pieces in the first position to the second position, 
	 * and remove the original chess pieces from the chessboard in the second position.
	 * @param player who try to eat pieces
	 * @param position1 where active piece is
	 * @param position2 where passive piece is
	 * @throws Exception if the designated position is beyond the scope of the chessboard, 
	 *  the first position has no chessboard, the second position has no chessboard,
	 *  the two positions are the same, 
	 *  the chessboard in the first position is not its own chessboard, 
	 *  the chessboard in the second position is not the counterpart chessboard
	 */
	public void eatPiece(Player player , Position position1 , Position position2) throws Exception {
		final Piece piece1 = chessBoard.getBoardPiece(position1.getX(), position1.getY());
		final Piece piece2 = chessBoard.getBoardPiece(position2.getX(), position2.getY());
		final Piece newPiece = new Piece(piece1.getPieceName(), piece1.getPieceState(),
				piece1.getPieceX(), piece1.getPieceY());
		
		if(piece1.getPieceState() != 1 || piece2.getPieceState() != 1) {
			System.out.println("棋子不存在！");
			throw new Exception("棋子不存在！");
		}
		if(position1.getX() == position2.getX() && position1.getY()
				== position2.getY()) {
			System.out.println("吃子的起始和目的位置相同！");
			throw new Exception("吃子的起始和目的位置相同！");
		}
		
		if(player.judgeOwnPiece(piece2) || !player.judgeOwnPiece(piece1)) {
			System.out.println("棋子归属不正确！");
			throw new Exception("棋子归属不正确！");
		}

		chessBoard.setBoardPositionState(position1.getX(), position1.getY(), 0);
		chessBoard.setBoardPositionState(position2.getX(), position2.getY(), -1);
		chessBoard.setBoardPosition(newPiece, position2.getX(), position2.getY());
		
		piece2.remove();
		piece1.setPieceX(position2.getX()); //设置棋子位置
		piece1.setPieceY(position2.getY());
		player.addPieces(newPiece);
		
		player.addHistory(String.format("%s eat piece %s in (%d,%d) by piece %s in (%d,%d)\n", 
					player.getPlayerName(),piece2.getPieceName(),position2.getX(),position2.getY(),
					piece1.getPieceName(),position1.getX(),position1.getY()));
	}
	
	
}
