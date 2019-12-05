package strategy;

import java.util.ArrayList;
import java.util.Random;

import crossing.CrossingSimulator;
import crossing.Ladder;

public class LadderStrategy1 implements Strategy {

	@Override
	public Ladder chooseLadder(String direction) {
		ArrayList<Ladder> list = new ArrayList<Ladder>();
		for (Ladder l : CrossingSimulator.ladderList) {
			if (l.getLeftCrossing() == 0 && l.getRightCrossing() == 0) { //梯子上没有猴子
				list.add(l);
			}
		}

		if (list.size() != 0) {
			Random random = new Random(); //随机返回
			int n = random.nextInt(list.size());
			return list.get(n);
		}
		list.clear();

		for (Ladder l : CrossingSimulator.ladderList) {
			if ((l.getLeftCrossing() == 0 && direction.startsWith("R"))
					|| ((l.getRightCrossing() == 0 && direction.startsWith("L")))) { //没有对向而行的猴子
				list.add(l);
			}
		}

		if (list.size() != 0) {
			Random random = new Random(); //随机返回
			int n = random.nextInt(list.size());
			return list.get(n);
		}
		return null;
	}

}
