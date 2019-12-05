package crossing;

import strategy.Strategy;

public class Monkey extends Thread {
	private final int id; //num
	private final String direction; //L or R
	private int v; //speed
	private int position = 0; //position in the ladder
	private final int produceTimer; //timer to record produceTime
	private int now; //timer to record the elapsed time

	private boolean finished = false; //Whether it has passed the ladder

	private Strategy ladderStratrgy; //strategy of choosing ladder
	private Ladder ladder = null; //Ladder being used
	
    // Abstraction function:
    //   represents an monkey with different attributes 
	//   such as ID, direction, velocity , position.
	//   each monkey has its ladder 
	//   the production and crossing time and status are recorded
    // Representation invariant:
	//   id > 0 and must be immutable
    //   direction is a non-null data and must be immutable
    //   v > 0 
    //   produceTimer > 0 and must be immutable
	//   now > produceTimer
    //   finished is initially false
	//   ladder is a non-null data
    // Safety from rep exposure:
    //   most fields are private and final
	//   use immutable data type
	//   as for mutable type , operations use defensive copies

	/**constructor.*/
	public Monkey(int produceTimer, int id, String direction, int v) {
		this.produceTimer = produceTimer;
		this.now = produceTimer;
		this.id = id;
		this.direction = direction;
		this.v = v;
	}

	//getters
	public int getID() {
		return id;
	}

	public String getDirection() {
		return direction;
	}

	public int getV() {
		return v;
	}

	public int getPosition() {
		return position;
	}

	public int getProduceTimer() {
		return produceTimer;
	}

	public int getNow() {
		return now;
	}

	public Ladder getLadder() {
		return ladder;
	}

	//setters
	public void setLadderStategy(Strategy strategy) {
		ladderStratrgy = strategy;
	}

	//	public void setLadder(Ladder l) {
	//		ladder = l;
	//	}

	public void setPosition(int p) {
		position = p;
	}

	public void setFinished() {
		finished = true;
	}

	public void setNow(int t) {
		now = t;
	}
	
	public void setV(int v1) {
		v = v1;
	}

	@Override
	public void run() {

		while (ladder == null) {
			double t1 = System.currentTimeMillis();
			CrossingSimulator.log.info("Monkey-ID:" + this.id + "   Waiting  " + "                                  "
					+ "  from birth:" + (this.now - this.produceTimer) + "s");
			double t2 = System.currentTimeMillis();
			CrossingSimulator.extraTime += t2-t1;
			
			ladder = this.ladderStratrgy.chooseLadder(this.direction);  //选梯子
			if (ladder == null) {
				try {
					this.now++;
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		ladder.upLadder(this);  //上梯子
		while (!finished) {
			ladder.cross(this);  //Cross

			if (this.position >= CrossingSimulator.h) {
				finished = true;
				ladder.exit(this);  //下梯子
			}

		}
	}
}
