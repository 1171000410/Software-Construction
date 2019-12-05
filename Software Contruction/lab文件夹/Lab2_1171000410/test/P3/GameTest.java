package P3;

import static org.junit.jupiter.api.Assertions.*;

//import java.security.Policy;

import org.junit.jupiter.api.Test;

class GameTest {

	// Testing strategy
	//	因为类之间的存在的调用关系，
	//  先主要测试Game类
	//  然后对其他类做补充
	//  合法情况，非法情况，抛出异常
	//  使用断言进行检查
	//  chess和go分开测试

	@Test
	public void testPiece() {
		final Piece piece = new Piece("white", 1, 1, 1);
		assertEquals("white", piece.getPieceName());
		assertEquals(1, piece.getPieceState());
		assertEquals(1, piece.getPieceX());
		assertEquals(1, piece.getPieceY());

//		assertTrue(piece.getPieceName() != null);
		piece.setPieceName("black");
		piece.setPieceX(2);
		piece.setPieceY(2);

		piece.remove();
		assertEquals(-1, piece.getPieceState());
		assertEquals(-1, piece.getPieceX());
		assertEquals(-1, piece.getPieceY());

	}

	@Test
	public void testGameGo() throws Exception {
		Game game = new Game(); // 新建游戏
		Board board = new Board();
		final String player1Name = "p1"; // 玩家1名称
		final String player2Name = "p2"; // 玩家2名称

		Piece[][] boardPosition = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boardPosition[i][j] = new Piece("empty", 0, -1, -1);
			}
		}
		board.setBoard(8, boardPosition);
		board.check(3, 3);
		board.check(88, 88);

		assertEquals(8, board.getBoardSize());

		final Player p1 = new Player(player1Name);
		final Player p2 = new Player(player2Name);

		game.setGameName("go");
		game.setPlayer1(p1);
		game.setPlayer2(p2);

		assertEquals("go", game.getGameName());
		assertEquals(p1, game.getGamePlayer1());
		assertEquals(p2, game.getGamePlayer2());

		final Position position = new Position(1, 1);
		final Piece piece = new Piece("white", 1, 1, 1);
		game.initByGo(player1Name, player2Name);

		game.putPiece(p1, piece, position);
		p1.addPieces(piece);
		piece.setPieceX(position.getX());
		piece.setPieceY(position.getY());
		piece.setPieceState(1);
		board.setBoardPosition(piece, position.getX(), position.getY());
		p1.addHistory(String.format("%s put a piece %s in (%d,%d)\n", p1.getPlayerName(), piece.getPieceName(),
				position.getX(), position.getY()));

		game.removePiece(p2, position);

		Player p3 = game.getGamePlayer1();
		p3.getPiece("white");
		p3.getPiece("black");

		try {
			Piece piece2 = new Piece("white", 1, 1, 1);
			game.putPiece(p1, piece2, position);
			game.putPiece(p1, piece2, position);

		} catch (Exception e) {
		}

	}

	@Test
	public void testRemovePieceException() throws Exception {
		try {
			Game game = new Game();
			game.initByGo("p1", "p2");
			Player p1 = game.getGamePlayer1();

			Position position = new Position(1, 1);
			game.putPiece(p1, new Piece("white", 1, 1, 1), position);
			game.removePiece(p1, position);
		} catch (Exception e) {
		}
	}

	@Test
	public void testRemovePieceException2() throws Exception {
		try {
			Game game = new Game();
			game.initByGo("p1", "p2");
			Player p1 = game.getGamePlayer1();
			Player p2 = game.getGamePlayer2();

			Position position = new Position(1, 1);
			game.putPiece(p1, new Piece("white", 1, 1, 1), position);

			game.removePiece(p2, new Position(3, 3));
		} catch (Exception e) {
		}
	}

	@Test
	public void testGameChess() throws Exception {
		Game game = new Game(); // 新建游戏

		final String player1Name = "p1"; // 玩家1名称
		final String player2Name = "p2"; // 玩家2名称

		game.initByChess(player1Name, player2Name);

		final Position oldPosition = new Position(1, 1);
		final Position newPosition = new Position(3, 4);

		oldPosition.equals(newPosition);
		oldPosition.equals(oldPosition);

		game.getGameBoard().getBoardPosition()[1][1].getPieceName();

		final Player p1 = game.getGamePlayer1();
		final Player p2 = game.getGamePlayer2();

		game.movePiece(p1, oldPosition, newPosition);
		game.eatPiece(p2, new Position(6, 7), newPosition);
		game.printBoard();

		game.getOccupationOfPosition(oldPosition);

		p1.getRemaining();
		p1.getHistory();
		p1.setPlayerName("pp1");

		assertEquals("pp1", p1.getPlayerName());
		assertEquals(15, p1.countQuantityOfPieceInBoard());

		try {
			final Position position1 = new Position(7, 7);
			final Position position2 = new Position(2, 7);
			game.movePiece(p1, position1, position2);
		} catch (Exception e) {
		}
	}

	@Test
	public void testMovePieceException() {
		Game game = new Game();
		game.initByChess("p1", "p2");
		Player p1 = game.getGamePlayer1();

		try {
			final Position position1 = new Position(1, 1);
			final Position position2 = new Position(1, 1);
			game.movePiece(p1, position1, position2);
		} catch (Exception e) {
		}
	}

	@Test
	public void testMovePieceException2() {
		Game game = new Game();
		game.initByChess("p1", "p2");
		Player p1 = game.getGamePlayer1();

		try {
			final Position position1 = new Position(1, 1);
			final Position position2 = new Position(4, 5);
			final Position position3 = new Position(5, 5);

			game.movePiece(p1, position1, position2);
			game.movePiece(p1, position1, position3);
		} catch (Exception e) {
		}
	}

	@Test
	public void testEatPieceException() {
		Game game = new Game();
		game.initByChess("p1", "p2");
		Player p1 = game.getGamePlayer1();

		try {
			final Position position1 = new Position(5, 5);
			final Position position2 = new Position(4, 5);
			game.eatPiece(p1, position1, position2);
		} catch (Exception e) {
		}
	}

	@Test
	public void testEatPieceException2() {
		Game game = new Game();
		game.initByChess("p1", "p2");
		Player p1 = game.getGamePlayer1();

		try {
			final Position position1 = new Position(1, 1);
			final Position position2 = new Position(1, 1);
			game.eatPiece(p1, position1, position2);
		} catch (Exception e) {
		}
	}

	@Test
	public void testEatPieceException3() {
		Game game = new Game();
		game.initByChess("p1", "p2");
		Player p1 = game.getGamePlayer1();

		try {
			final Position position1 = new Position(7, 7);
			final Position position2 = new Position(1, 1);
			game.eatPiece(p1, position1, position2);
		} catch (Exception e) {
		}
	}

	@Test
	public void testBoardException1() throws Exception {
		try {
			Game game = new Game();
			game.initByChess("p1", "p2");
			Board board = game.getGameBoard();

			board.getBoardPiece(20, 20);
		} catch (

		Exception e) {
		}
	}

	@Test
	public void testBoardException2() throws Exception {
		try {
			Game game = new Game();
			game.initByChess("p1", "p2");
			Board board = game.getGameBoard();

			Piece piece = new Piece("white", 1, 1, 1);
			board.setBoardPosition(piece, 20, 20);
		} catch (Exception e) {
		}
	}

	@Test
	public void testBoardException3() throws Exception {
		try {
			Game game = new Game();
			game.initByChess("p1", "p2");
			Board board = game.getGameBoard();

			Piece piece = new Piece("white", 1, 1, 1);
			board.setBoardPosition(piece, 1, 1);
		} catch (Exception e) {
		}
	}

	@Test
	public void testBoardException4() throws Exception {
		try {
			Game game = new Game();
			game.initByChess("p1", "p2");
			Board board = game.getGameBoard();

			board.setBoardPositionState(20, 20, 1);
		} catch (Exception e) {
		}
	}

}
