package crossing;

public class Ladder {
	private int leftCrossing = 0; //monkeys from the left direction
	private int rightCrossing = 0; //monkeys from the right direction
	private int[] pedals = new int[CrossingSimulator.h + 10];; //pedals free or occupied
	private final int id; //number of ladder

	// Abstraction function:
	//   represents an ladder with specific id
	//   leftCrossing and rightCrossing recorded the number of monkeys on each side
	//   pedals respond to the occupancy of the position on the ladder
	// Representation invariant:
	//   id > 0 and must be immutable
	//   leftCrossing > 0
	//   rightCrossing > 0
	//   pedals can only store data which is 0 or 1
	// Safety from rep exposure:
	//   All fields are private and id is final
	//   use immutable data type
	//   as for mutable type , operations use defensive copies
	
	// Thread safety argument:
	//	 all accesses to text happen within Ladder methods ,
	//	 which are all guarded by Ladder's lock .
	//   as for the id modified by private and final
	//   which is an immutable data type .
	
	public Ladder(int ladderID) {
		id = ladderID;
	}

	//getters
	public synchronized int getLeftCrossing() {
		return leftCrossing;
	}

	public synchronized int getRightCrossing() {
		return rightCrossing;
	}

	public synchronized int[] getPedals() {
		return pedals;
	}

	public int getID() {
		return id;
	}

	//setters
	public synchronized void setLeftCrossing(int l) {
		leftCrossing = l;
	}

	public synchronized void setRightCrossing(int r) {
		rightCrossing = r;
	}

	public synchronized void upLadder(Monkey monkey) {
		final double t1 = System.currentTimeMillis();
		if(monkey.getDirection().startsWith("L")) {
		System.out.println("UpLadder=> monkey: " + monkey.getID() + " " + monkey.getDirection());
		}else {
			System.out.println("                                                    "+
					"UpLadder->monkey: " + monkey.getID() + " " + monkey.getDirection());
		}
		final double t2 = System.currentTimeMillis();
		CrossingSimulator.extraTime += t2-t1;
		if (monkey.getDirection().startsWith("L")) {
			leftCrossing++;
		} else {
			rightCrossing++;
		}
		monkey.setCounted();
	}

	public void cross(Monkey monkey) {
		final double t1 = System.currentTimeMillis();
		if (monkey.getPosition() != 0) {
			CrossingSimulator.log.info("Monkey-ID:" + monkey.getID() + " Position ladder-ID:"
					+ monkey.getLadder().getID() + " pedel-ID:" + monkey.getPosition() + " V:"+monkey.getV()+" direction:"
					+ monkey.getDirection() + " from birth:" + (monkey.getNow() - monkey.getProduceTimer()) + "s");
		}
		final double t2 = System.currentTimeMillis();
		CrossingSimulator.extraTime += t2-t1;
		
		final int pedal = choosePedal(monkey);
		pedals[monkey.getPosition()] = 0; //离开原位置
		monkey.setPosition(pedal);
		pedals[pedal] = 1; //占用新位置	

		int t = monkey.getNow();
		monkey.setNow(t + 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block	
		}

	}

	public synchronized void exit(Monkey monkey) {
		final double t1 = System.currentTimeMillis();
		if(monkey.getDirection().startsWith("R")) {
		System.out.println("Exit=> monkey: " + monkey.getID() + " " + monkey.getDirection());
		}else {
			System.out.println("                                                    "+
					"Exit->monkey: " + monkey.getID() + " " + monkey.getDirection());
		}
		
		CrossingSimulator.log.info("Monkey-ID:" + monkey.getID() + "        Arrived at " + monkey.getDirection().charAt(3)
				+ "                                " + " from birth:" + (monkey.getNow() - monkey.getProduceTimer())
				+ "s");
		final double t2 = System.currentTimeMillis();
		CrossingSimulator.extraTime += t2-t1;
		
		if (monkey.getDirection().startsWith("L")) {
			leftCrossing--;
		} else {
			rightCrossing--;
		}
		pedals[monkey.getPosition()] = 0;

	}

	private int choosePedal(Monkey monkey) {
		final int now = monkey.getPosition();
		final int mv = monkey.getV();
		int flag = 0;
		int nearest = 0; //最近的有猴子的位置

		synchronized (pedals) {

		if (now + mv <= CrossingSimulator.h - 1) { //还在梯子上
			for (int i = mv; i > 0; i--) {
				if (pedals[now + i] == 1) {
					flag = 1; //前面有猴子
					nearest = now + i;
				}
			}
			if (flag == 0) { //前面没猴子
				return now + mv;
			} else {
				monkey.setV(nearest - now);
				return nearest - 1;
			}

		} else { //可能超出梯子
			for (int j = CrossingSimulator.h - 1; j > now; j--) {
				if (pedals[j] == 1) {
					flag = 1;
					nearest = j;
				}

			}
			if (flag == 0) { //前面没猴子
				return now + mv;
			} else {
				return nearest - 1;
			}

		}
		}
	}
}
