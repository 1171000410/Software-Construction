package strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import crossing.Ladder;
import crossing.CrossingSimulator;

public class LadderStrategy2 implements Strategy {

	// 优先选择推进速度最快的梯子
	// 综合考虑梯子上猴子数量和和我最近猴子的距离
	// 将每个梯子映射到一个分数
	// 选择分数最大的梯子
	
	@Override
	public Ladder chooseLadder(String direction) {
		for (Ladder l : CrossingSimulator.ladderList) {
			if (l.getLeftCrossing() == 0 && l.getRightCrossing() == 0) { //梯子上没有猴子直接返回
				return l;
			}
		}

		Map<Integer, Ladder> priorityMap = new HashMap<Integer, Ladder>();  //每个梯子映射一个积分
		int monkeyQuantities, point = 0, nearest = CrossingSimulator.h;
		for (Ladder l : CrossingSimulator.ladderList) {
			if ((l.getLeftCrossing() == 0 && direction.startsWith("R"))
					|| ((l.getRightCrossing() == 0 && direction.startsWith("L")))) { //没有对向而行的猴子

				monkeyQuantities = l.getLeftCrossing() + l.getRightCrossing();
				for (int i = 0; i < CrossingSimulator.h; i++) {
					if (l.getPedals()[i] == 1) {
						nearest = i;
					}
				}
				point = nearest - 2 * monkeyQuantities; //根据关系自己设置权值
				priorityMap.put(point, l);

			}
		}

		if (priorityMap.keySet().size() == 0) {
			return null;
		}
		int maxPoint = Collections.max(priorityMap.keySet());
		if (maxPoint < -3) {
			return null;
		}
		return priorityMap.get(maxPoint);

	}
}
