package crossing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import crossing.MyFormatter;

public class CrossingSimulator {
	public static int h; //length of ladders
	public static int n; //quantity of ladders
	public static ArrayList<Monkey> monkeyList = new ArrayList<Monkey>(); //list to store monkeys
	public static ArrayList<Ladder> ladderList = new ArrayList<Ladder>(); //list to store ladders
	public static final Logger log = Logger.getLogger(CrossingSimulator.class.getSimpleName());
	public static double extraTime = 0;  //time spent on GUI and log
	//	public static PrintStream ps = System.out;
	
    // Abstraction function:
    //   represents a simulator to produce and start monkeys in the ladders
	//   record logs and extra time
    // Representation invariant:
    //   h is initialized to 20 and immutable
	//   1 < n < 10
	//   2 < monkeyList.size() < 1000
    //   1 < ladderList.size() < 10
	//   log is not null
	//   extratime > 0
    // Safety from rep exposure:
    //   monkeyList and ladderList are mutable lists, so operation make defensive
    //   copies and use immutable views to avoid sharing the rep
    //   as for a mutable type, operations use defensive copies 
    //   h and log is final

	public static void main(String[] args) {

		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(System.in));
		int t = 0; //产生猴子的间隔时间
		int k = 0; //一次产生多少猴子
		int monkeyQuantity = 0; //猴子总数
		int mv = 0; //猴子最大速度
		int s = 0 ; //策略
		int flag = 1;
		while (flag == 1) {
			try {
				System.out.println("请输入初始化参数n(1~10):");
				n = Integer.parseInt(reader.readLine());
				h = 20;
				System.out.println("请输入初始化参数t(1~5):");
				t = Integer.parseInt(reader.readLine());
				System.out.println("请输入初始化参数N(2~1000):");
				monkeyQuantity = Integer.parseInt(reader.readLine());
				System.out.println("请输入初始化参数k(1~50):");
				k = Integer.parseInt(reader.readLine());
				System.out.println("请输入初始化参数MV(5~10):");
				mv = Integer.parseInt(reader.readLine());
				System.out.println("请输入猴子选梯子的策略(1~3):");
				s = Integer.parseInt(reader.readLine());
				flag = 0;
			} catch (Exception e1) {
				System.out.println("初始化参数输入错误，请重新输入！");
				flag = 1;
			}
		}

		try {
			// 文件控制器
			FileHandler fileHandler = new FileHandler("log/monkeysLog.log");
			fileHandler.setFormatter(new MyFormatter());
			log.setUseParentHandlers(false); // 让logger信息不在控制台输出
			log.addHandler(fileHandler);

			MonkeyGenerator gen = new MonkeyGenerator();
			gen.produce(n, h, t, monkeyQuantity, k, mv,s);
			
			fileHandler.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
