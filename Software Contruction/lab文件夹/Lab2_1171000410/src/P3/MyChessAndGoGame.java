package P3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyChessAndGoGame {
	public Game game = new Game();  //新建游戏
	public String player1Name;  //玩家1名称
	public String player2Name;  //玩家2名称 
	public ArrayList<Player> players = new ArrayList<Player>(); //玩家链表

	/**Print menu for selection*/
	public void menu() {
		System.out.println("1.放置棋子(围棋)");
		System.out.println("2.提子(围棋)");
		System.out.println("3.移动棋子(国际象棋)");
		System.out.println("4.吃子(国际象棋)");
		System.out.println("5.查询某个位置的占用情况");
		System.out.println("6.查看棋盘");
		System.out.println("7.计算两个玩家分别在棋盘上的棋子总数");
		System.out.println("8.跳过");
		System.out.println("9.end");
	}

	public void myMain() throws Exception {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(System.in));

			while (true) { // 直到得到正确的输入
				System.out.println("请输入游戏类型:(chess or go)");
				String input = reader.readLine();
				if (input.equals("chess") || input.equals("go")) {
					game.setGameName(input);
					break;
				} else {
					System.out.println("输入游戏类型错误，请重新输入");
				}
			}

			System.out.println("请输入player1的名称:");
			player1Name = reader.readLine();
			System.out.println("请输入player2的名称:");
			player2Name = reader.readLine();

			if (game.getGameName().equals("chess")) {
				game.initByChess(player1Name, player2Name);
			} else {
				game.initByGo(player1Name, player2Name);
			}
			players.add(game.getGamePlayer1());
			players.add(game.getGamePlayer2());

			System.out.println("游戏开始！");

			int turn = 0;
			while (true) {
				System.out.printf("\n轮到%s执行：\n", players.get(turn).getPlayerName());
				menu();
				String choice = reader.readLine();
				String line = "";
				String[] items;
				boolean flag = false;
				
				if((game.getGameName().equals("chess") && (choice.equals("1")||choice.equals("1")))||
					(game.getGameName().equals("go") && (choice.equals("3")||choice.equals("4")))) {
					System.out.println("请重新输入对应棋类的操作！");
					continue;
				}
				
				switch (choice) {
				case "1":  //放置棋子(围棋)
					System.out.println("请依次输入：棋子名 横坐标 纵坐标");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					if (items.length != 3) {
						System.out.println("输入错误！请按规则重新输入：");
						continue;
					} else {
						try {
							String pieceName = items[0];
							int positionX = Integer.valueOf(items[1]); // String转为Integer
							int positionY = Integer.valueOf(items[2]);
							Position position = new Position(positionX, positionY);

							Player player = players.get(turn);
							Piece piece = player.getPiece(pieceName);
							
							if (piece == null) {
								System.out.println("未找到可放置的该棋子！请按规则重新输入：");
								continue;
							}

							game.putPiece(player, piece, position);

							turn = (turn + 1) % 2;

							System.out.println("Successful placement !");
						} catch (Exception e) {
							System.out.println("捕捉异常，请重新输入");
							continue;
						}
					}
					break;

				case "2":  //提子(围棋)
					System.out.println("请输入你要提的棋子坐标：");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					if (items.length != 2) {
						System.out.println("输入错误！请按规则重新输入：");
						continue;
					} else {
						try {
							int x = Integer.valueOf(items[0]);
							int y = Integer.valueOf(items[1]);

							Position position = new Position(x, y);

							Player player = players.get(turn);

							game.removePiece(player, position);

							turn = (turn + 1) % 2;
							System.out.println("Successful removal !");
						} catch (Exception e) {
							System.out.println("捕捉异常，请重新输入");
							continue;
						}
					}
					break;

				case "3":  //移动棋子(国际象棋)
					System.out.println("请输入需要移动的点坐标和目的地坐标：");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					if (items.length != 4) {
						System.out.println("输入错误！请按规则重新输入：");
						continue;
					} else {
						try {
							int oldX = Integer.valueOf(items[0]);
							int oldY = Integer.valueOf(items[1]);
							int newX = Integer.valueOf(items[2]);
							int newY = Integer.valueOf(items[3]);
							Position oldPosition = new Position(oldX, oldY);
							Position newPosition = new Position(newX, newY);

							Player player = players.get(turn);

							game.movePiece(player, oldPosition, newPosition);

							turn = (turn + 1) % 2;
							System.out.println("Successful movement !");
						} catch (Exception e) {
							System.out.println("捕捉异常，请重新输入");
							continue;
						}
					}
					break;

				case "4":  //4.吃子(国际象棋)
					System.out.println("请输入移动棋子坐标和被吃棋子坐标：");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					if (items.length != 4) {
						System.out.println("输入错误！请按规则重新输入：");
						continue;
					} else {
						try {
							int x1 = Integer.valueOf(items[0]);
							int y1 = Integer.valueOf(items[1]);
							int x2 = Integer.valueOf(items[2]);
							int y2 = Integer.valueOf(items[3]);
							Position oldPosition = new Position(x1, y1);
							Position newPosition = new Position(x2, y2);

							Player player = players.get(turn);

							game.eatPiece(player, oldPosition, newPosition);

							turn = (turn + 1) % 2;
							System.out.println("Successful eating !");
						} catch (Exception e) {
							System.out.println("捕捉异常，请重新输入");
							continue;
						}
					}
					break;

				case "5":
					System.out.println("请输入需查询坐标：");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					if (items.length != 2) {
						System.out.println("输入错误！请按规则重新输入：");
					} else {
						try {
							int x1 = Integer.valueOf(items[0]);
							int y1 = Integer.valueOf(items[1]);

							Position position = new Position(x1, y1);

							Piece piece = game.getOccupationOfPosition(position);
							if (piece == null || piece.getPieceState() != 1) {
								System.out.println("该位置空闲");
							} else {
								if (players.get(0).judgeOwnPiece(piece)) {
									System.out.println("棋手名:" + players.get(0).getPlayerName());
								} else {
									System.out.println("棋手名:" + players.get(1).getPlayerName());
								}
								System.out.println("棋子:" + piece.getPieceName());
							}
							System.out.println("查询成功，请继续选择执行：");
						} catch (Exception e) {
							System.out.println("捕捉异常，请重新输入");
							continue;
						}
					}
					continue;

				case "6":
					System.out.println("棋盘如下：");
					game.printBoard();
					System.out.println("请继续选择执行：");
					continue;

				case "7":
					System.out.printf("玩家%s的棋子总数为%d\n", players.get(0).getPlayerName(),
							players.get(0).countQuantityOfPieceInBoard());
					System.out.printf("玩家%s的棋子总数为%d\n", players.get(1).getPlayerName(),
							players.get(1).countQuantityOfPieceInBoard());
					System.out.println("查询成功，请继续选择执行：");
					continue;

//				case "8":  //查看玩家历史
//					Player player1 = players.get(0);
//					Player player2 = players.get(1);
//					System.out.println(player1.getPlayerName() + "操作历史如下：");
//					System.out.println(player1.getHistory());
//					System.out.println(player2.getPlayerName() + "操作历史如下：");
//					System.out.println(player2.getHistory());
//					System.out.println("查询成功，请继续选择执行：");
//					continue;

				case "8":
					System.out.println("已跳过");
					turn = (turn + 1) % 2;
					break;

				case "9":
					System.out.println("--------Game over!--------");
					Player player1 = players.get(0);
					Player player2 = players.get(1);
					System.out.println(player1.getPlayerName() + "操作历史如下：");
					System.out.println(player1.getHistory());
					System.out.println(player2.getPlayerName() + "操作历史如下：");
					System.out.println(player2.getHistory());
					flag = true;
					break;

				default:
					System.out.println("输入选项错误，请重新输入！");
					break;
				}
				if (flag) { // 选择10则退出循环
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
		
	}

	public static void main(String[] args) throws Exception {
		MyChessAndGoGame newGame = new MyChessAndGoGame();
		newGame.myMain();
	}

}
