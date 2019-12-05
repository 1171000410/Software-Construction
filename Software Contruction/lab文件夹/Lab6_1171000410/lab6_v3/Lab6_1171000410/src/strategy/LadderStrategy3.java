package strategy;

import crossing.Ladder;
import crossing.CrossingSimulator;

public class LadderStrategy3 implements Strategy {
	
	// 梯子上没有对向而行的猴子
	// 并且梯子上同向而行的猴子数不大于5个
	
	@Override
	public Ladder chooseLadder(String direction) {
		for (Ladder l : CrossingSimulator.ladderList) {
			if ((l.getLeftCrossing() == 0 && direction.startsWith("R") && l.getRightCrossing() <= 5)
					|| ((l.getRightCrossing() == 0 && direction.startsWith("L") && l.getLeftCrossing() <= 5))) {
				//没有对向而行的猴子,并且梯子上同向而行的猴子数少于5个					
				return l;
			}
		}
		return null;
	}
}
