package P3;

public class Position {
	private int x;  //横坐标
	private int y;  //纵坐标
	
	// Abstraction function:
    //   Position代表棋子在棋盘上的位置，x代表位置的横坐标，y代表位置的纵坐标
    // Representation invariant:
    //   position不能映射为空
    //
    // Safety from rep exposure:
    //   所有fields都是 private and final
    //   使用immutable数据类型
	
	/**Constructor*/
	public Position(int positionX, int positionY) {
		this.x = positionX;
		this.y = positionY;	
	}

	/**Return this position's abscissa*/
	public int getX() {
		return this.x;
	}
	
	/**Return this position's ordinate*/
	public int getY() {
		return this.y;
	}
	
	/**
	 * Judge whether two positions are equal
	 * @param that position
	 * @return true if the two positions are equal; or false
	 */
	@Override
	public boolean equals(Object that) {  //判断两个位置是否相等
		if(that instanceof Position) {
			if(this.getX() == ((Position)that).getX() && this.getY() == ((Position)that).getY()) {
				return true;
			}
		}
		return false;
	}
}
