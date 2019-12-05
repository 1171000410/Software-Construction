package P3;

public class Game {
	private String gameName;  //游戏名
	private Board gameBoard = new Board(); //棋盘
	private Action gameAction = new Action();  //动作
	private Player player1;  //棋手1
	private Player player2;  //棋手2

	// Abstraction function:
    //   Game代表了一局游戏，gameBoard映射为game中的棋盘，棋盘的操作这是player1和player2.
	//   gameAction为棋手的操作
    // Representation invariant:
    //   Game不能映射为空，gameBoard不能映射为空，Action不能映射为空
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	
	/**Return this game's name*/
	public String getGameName() {
		return this.gameName;
	}

	/**Return this game's player1*/
	public Player getGamePlayer1() {
		return this.player1;
	}

	/**Return this game's player2*/
	public Player getGamePlayer2() {
		return this.player2;
	}

	/**Return this game's board*/
	public Board getGameBoard() {
		return this.gameBoard;
	}
	
	/**
	 * Set this game's player1 by player
	 * @param player is to be set
	 */
	public void setPlayer1(Player player) {
		this.player1 = player;
	}

	/**
	 * Set this game's player2 by player
	 * @param player is to be set
	 */
	public void setPlayer2(Player player) {
		this.player2 = player;
	}

	/**
	 * Set this game's gameName by name 
	 * @param name is to be set
	 */
	public void setGameName(String name) {
		this.gameName = name;
	}

	/**
	 * Call methods in action
	 * Put player's piece to the specified position ,
	 * Add player's history
	 * @param player who owns the piece
	 * @param piece is to be put
	 * @param position the place is to be put in 
	 * @throws Exception if the piece is in a wrong state
	 */
	public void putPiece(Player player, Piece piece, Position position) throws Exception {
		gameAction.putPiece(player, piece, position);
	}

	/**
	 * Call methods in action
	 * move the player's piece from the oldPosition to the newPosition
	 * @param player who owns the piece
	 * @param oldPosition where the piece stay at first
	 * @param newPosition where the piece stay in the end 
	 * @throws Exception if the piece is in a wrong state or
	 *     	 	if the oldPositon equals to the newPosition
	 */
	public void movePiece(Player player, Position oldPosition, Position newPosition) throws Exception {
		gameAction.movePiece(player, oldPosition, newPosition);
	}

	/**
	 * Call methods in action
	 * Remove the opponent's chess pieces from the position
	 * By the "player, the horizontal and vertical coordinates of a position",
	 * @param player who is raising the piece
	 * @param position where the piece
	 * @throws Exception if the piece doesn't belong to the opponent ,
	 *  		or the piece doesn't exist
	 */
	public void removePiece(Player player, Position position) throws Exception {
		gameAction.removePiece(player, position);
	}

	/**
	 * Call methods in action
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
	public void eatPiece(Player player, Position position1, Position position2) throws Exception {
		gameAction.eatPiece(player, position1, position2);
	}

	/**
	 * Get a chess piece in a specified position
	 * @param position represent coordinate of the piece
	 * @return piece in the position
	 * @throws Exception if the input position is illegal
	 */
	public Piece getOccupationOfPosition(Position position) throws Exception {
		Piece piece = gameBoard.getBoardPiece(position.getX(), position.getY());
		return piece;
	}

	/**
	 * Initialize the chess's gameBoard
	 * Set up the gameBoard, set the position of the gameBoard,
	 * set pieces' coordinates states and names 
	 * add the name of the player and the gameBoard they own.
	 * @param name1 of player1
	 * @param name2 of player2
	 */
	public void initByChess(String name1, String name2) {
		setGameName("chess"); // 设置棋类型

		final Player p1 = new Player(name1);
		final Player p2 = new Player(name2);
		setPlayer1(p1);
		setPlayer2(p2);

		Piece[][] boardPosition = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardPosition[i][j] = new Piece("empty", 0, -1, -1);
			}
		}
		final int boardSize = 8;

		final Piece[] piece = new Piece[32]; // 设置棋盘上的pieces
		piece[0] = new Piece("king", 1, 3, 0);
		piece[1] = new Piece("queen", 1, 4, 0);
		piece[2] = new Piece("rook", 1, 0, 0);
		piece[3] = new Piece("rook", 1, 7, 0);
		piece[4] = new Piece("bishop", 1, 2, 0);
		piece[5] = new Piece("bishop", 1, 5, 0);
		piece[6] = new Piece("knight", 1, 1, 0);
		piece[7] = new Piece("knight", 1, 6, 0);
		for (int i = 8; i <= 15; i++) {
			piece[i] = new Piece("pawn", 1, i - 8, 1);
		}

		piece[16] = new Piece("king", 1, 3, 7);
		piece[17] = new Piece("queen", 1, 4, 7);
		piece[18] = new Piece("rook", 1, 0, 7);
		piece[19] = new Piece("rook", 1, 7, 7);
		piece[20] = new Piece("bishop", 1, 2, 7);
		piece[21] = new Piece("bishop", 1, 5, 7);
		piece[22] = new Piece("knight", 1, 1, 7);
		piece[23] = new Piece("knight", 1, 6, 7);
		for (int i = 24; i <= 31; i++) {
			piece[i] = new Piece("pawn", 1, i - 24, 6);
		}

		for (int i = 0; i <= 31; i++) { // 定义棋盘position
			int x = piece[i].getPieceX();
			int y = piece[i].getPieceY();
			boardPosition[x][y] = piece[i];
		}

		gameBoard.setBoard( boardSize, boardPosition);

		this.gameAction.setChessBoard(gameBoard);

		for (int i = 0; i <= 15; i++) { // 增加玩家棋子
			p1.addPieces(piece[i]);
		}
		for (int i = 16; i <= 31; i++) {
			p2.addPieces(piece[i]);
		}
	}

	/**
	 * Initialize the go's gameBoard
	 * Set up the gameBoard, set the position of the gameBoard, 
	 * set pieces' coordinates states and names 
	 * add the name of the player and the gameBoard they own.
	 * @param name1 of player1
	 * @param name2 of player2
	 */
	public void initByGo(String name1, String name2) {
		setGameName("go"); // 设置棋类型

		final Player p1 = new Player(name1);
		final Player p2 = new Player(name2);
		setPlayer1(p1);
		setPlayer2(p2);

		final Piece[][] boardPosition = new Piece[9][9];
		final int boardSize = 9;

		Piece[] piece = new Piece[1000]; // 设置棋盘pieces
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boardPosition[i][j] = new Piece("empty", 0, -1, -1);
			}
		}

		for (int i = 0; i <= 499; i++) { // 增加玩家棋子
			piece[i] = new Piece("white", 0, -1, -1);
			p1.addPieces(piece[i]);
		}
		for (int i = 500; i <= 999; i++) {
			piece[i] = new Piece("black", 0, -1, -1);
			p2.addPieces(piece[i]);
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Piece p = new Piece("", 0, -1, -1);
				boardPosition[i][j] = p;
			}
		}

		gameBoard.setBoard(boardSize, boardPosition);

		this.gameAction.setChessBoard(gameBoard);
	}

	/**
	 * Print the gameBoard in a matrix format 
	 * print "empty" in an empty position
	 */
	public void printBoard() {
		for (int i = gameBoard.getBoardSize() - 1; i >= 0; i--) {
			for (int j = 0; j < gameBoard.getBoardSize(); j++) {
				if (gameBoard.getBoardPosition()[j][i].getPieceState() != 1) {
					System.out.print("empty ");
				} else {
					System.out.print(gameBoard.getBoardPosition()[j][i].getPieceName() + " ");
				}
			}
			System.out.println("");
		}
	}

}
