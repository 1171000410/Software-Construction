package crossing;

import java.awt.Font;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import strategy.LadderStrategy1;
import strategy.LadderStrategy2;
import strategy.LadderStrategy3;
import strategy.Strategy;

public class MonkeyGenerator {

	public void produce(int n, int h, int t, int monkeyQuantity, int k, int mv , int s) {

		JFrame f = new JFrame("Go ! Go ! Monkeys !");
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
		} catch (IOException e) {
			System.err.println("不能创建LoopedStreams：" + e);
			System.exit(1);
		}
		consoleTextArea.setFont(new Font("Consolas", Font.BOLD, 32)); //设置字体
		f.getContentPane().add(new JScrollPane(consoleTextArea), java.awt.BorderLayout.CENTER);
		f.setBounds(50, 50, 1500, 950);
		f.setVisible(true);
		f.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.exit(0);
			}
		});

		System.out.println("Parameter configuration:");
		System.out.println("Number of ladders     n = " + n);
		System.out.println("Intervals             t = " + t);
		System.out.println("number of monkeys     N = " + monkeyQuantity);
		System.out.println("monkeys per second    k = " + k);
		System.out.println("Maximum speed	      MV = " + mv);
		System.out.println("LadderStrategy:       ls = "+ s);
		System.out.println("\n\n");
		System.out.println("                                     Ladder\n");
		System.out.println("Left Side                                               " + "  Right Side");
		for (int i = 0; i < n; i++) { //new ladders
			Ladder l = new Ladder(i + 1);
			CrossingSimulator.ladderList.add(l);
		}

		String[] randomDirections = { "L->R", "R->L" };
		Strategy l1 = new LadderStrategy1();
		Strategy l2 = new LadderStrategy2();
		Strategy l3 = new LadderStrategy3();
		Strategy[] randomStrategies = { l1, l2, l3 };

		double startTime = System.currentTimeMillis();
		try {
			final int times = monkeyQuantity / k; //产生的次数
			for (int i = 0; i < times; i++) {
				for (int j = k * i; j < k * (i + 1); j++) {

					Random random = new Random(); //随机返回
					int rd = random.nextInt(2);
					int rv = random.nextInt(mv) + 1;
//					int rs = random.nextInt(3);
					Monkey m = new Monkey(i * t, j + 1, randomDirections[rd], rv);
					m.setLadderStategy(randomStrategies[s-1]);

					CrossingSimulator.monkeyList.add(m);
					m.start();
				}

				Thread.sleep(t * 1000); //间隔的时间
			}

			if (monkeyQuantity % k != 0) {
				for (int q = k * times; q < monkeyQuantity; q++) {
					Random random = new Random(); //随机返回
					int rd = random.nextInt(2);
					int rv = random.nextInt(mv) + 1;
//					int rs = random.nextInt(3);
					Monkey m = new Monkey(times * t, q + 1, randomDirections[rd], rv);
					m.setLadderStategy(randomStrategies[s-1]);

					CrossingSimulator.monkeyList.add(m);
					m.start();
				}
			}

			for (int i = 0; i < CrossingSimulator.monkeyList.size(); i++) {
				CrossingSimulator.monkeyList.get(i).join(); //等待进程结束
			}
			double finishTime = System.currentTimeMillis();
			double totalTime = finishTime - startTime - CrossingSimulator.extraTime;
			System.out.println("finishTime - startTime:" + (finishTime - startTime) + "ms");
			System.out.println("extraTime:"+CrossingSimulator.extraTime+"ms");
			System.out.println("totalTime:"+totalTime+"ms");
			System.out.println("Throughput rate:   " + CrossingSimulator.monkeyList.size() * 1000 / totalTime);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//计算公平性
		double fairness = 0;
		for (int i = 0; i < monkeyQuantity; i++) {
			for (int j = i + 1; j < monkeyQuantity; j++) {
				if ((CrossingSimulator.monkeyList.get(i).getProduceTimer()
						- CrossingSimulator.monkeyList.get(j).getProduceTimer())
						* (CrossingSimulator.monkeyList.get(i).getNow()
								- CrossingSimulator.monkeyList.get(j).getNow()) >= 0) {
					fairness++;
				} else {
					fairness--;
				}
			}
		}
		double res = 2 * fairness / monkeyQuantity / (monkeyQuantity - 1);
		System.out.println("Fairness:           " + res);
	}
}
