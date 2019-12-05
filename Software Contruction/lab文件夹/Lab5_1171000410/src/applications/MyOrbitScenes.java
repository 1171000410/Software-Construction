package applications;

import apis.CircularOrbitapis;
import centralobject.CentralUser;
import centralobject.Nucleus;
import centralobject.Stellar;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import circularorbit.StellarSystem;
import fileinput.AllBufferedReader;
import fileinput.AllInputStream;
import fileinput.AllScanner;
import fileinput.ReadStrategy;
import fileoutput.AtomStructureBufferWriter;
import fileoutput.AtomStructureFileChannel;
import fileoutput.AtomStructureOutputStream;
import fileoutput.SocialNetworkCircleBufferWriter;
import fileoutput.SocialNetworkCircleChannel;
import fileoutput.SocialNetworkCircleOutputStream;
import fileoutput.StellarSystemBufferWriter;
import fileoutput.StellarSystemFileChannel;
import fileoutput.StellarSystemOutputStream;
import fileoutput.WriteStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
//import legality.LegalSocialNetworkCircle;
//import legality.LegalStellarSystem;
import physicalobject.Electron;
import physicalobject.Friend;
import physicalobject.Planet;
import relation.EeIntimacyMap;
import track.Track;

public class MyOrbitScenes {

  private BufferedReader reader = null;
  public static final Logger log = Logger.getLogger(MyOrbitScenes.class.getSimpleName());
  private ArrayList<String> queryLogList = new ArrayList<String>();

  /**main.*/
  public static void main(String[] args) {
	MyOrbitScenes newOrbit = new MyOrbitScenes();
	try {
	  // 文件控制器
	  FileHandler fileHandler = new FileHandler("log/info.log", true);
	  fileHandler.setFormatter(new MyFormatter());
	  log.setUseParentHandlers(false); // 让logger信息不在控制台输出
	  log.addHandler(fileHandler);

	  newOrbit.myMain();
	  System.gc();
	  fileHandler.close();
	} catch (Exception e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }

  /**myMain.*/
  public void myMain() throws Exception {

	try {
	  reader = new BufferedReader(new InputStreamReader(System.in));
	  boolean flag = true;

	  while (flag) {
		mainMenu();
		String choice = reader.readLine();
		if (choice == null) {
		  break;
		}
		switch (choice) {
		case "1":
		  simulateAtomStrcture();
		  flag = false;
		  break;

		case "2":
		  simulateStellarSystem();
		  flag = false;
		  break;

		case "3":
		  simulateSocialNetwork();
		  flag = false;
		  break;

		case "4":
		  query();
		  break;

		case "5":
		  //SEVERE 2019-05-18 00:00:00 2019-05-19 23:59:59 AtomStructure readFile exception
		  flag = false;
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  break;
		}

	  }
	  System.out.println("模拟结束！");
	  log.info("模拟结束！");

	} catch (IOException e) {
	  e.printStackTrace();
	} finally {
	  reader.close();
	}
  }

  /**simulateAtomStrcture.*/
  public void simulateAtomStrcture() {
	CircularOrbit<Nucleus, Electron> ast = new AtomStructure();

	String fileName = "";
	// txt/AtomicStructure_Exception.txt
	do {
	  try {
		System.out.println("请输入读取的AtomicStructure文件路径:");
		fileName = reader.readLine();
		if (fileName == null) {
		  System.out.println("输入为空！");
		  break;
		}
		ast = new AtomStructure();
		log.info("已读取文件" + fileName);

		printReadMenu();
		String read = reader.readLine();
		File file = new File(fileName);

		System.out.println("AtomicStructure读取文件时间：");
		switch (read) {
		case "1":
		  ReadStrategy r1 = new AllBufferedReader();
		  ast.readFile(r1.readFile(file));
		  break;

		case "2":
		  ReadStrategy r2 = new AllInputStream();
		  ast.readFile(r2.readFile(file));
		  break;

		case "3":
		  ReadStrategy r3 = new AllScanner();
		  ast.readFile(r3.readFile(file));
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  break;
		}

	  } catch (IOException e) {
		e.printStackTrace();
	  }
	} while (ast.printException() == 1);

	System.out.println("已读取文件，生成原子结构模型：");
	String line;
	String[] items;

	boolean flag = false;

	try {
	  while (true) {
		menuAtomStrcture();
		String choice = reader.readLine();
		if (choice == null) {
		  break;
		}
		switch (choice) {
		case "1":
		  CircularOrbitHelper2.visualizes(ast);

		  break;
		case "2":
		  System.out.println("请输入需要添加的轨道半径");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t1 = new Track(Double.parseDouble(line));
		  ast.addTrack(t1);
		  System.out.println("已成功添加该轨道");
		  log.info("已成功添加轨道");
		  
		  break;

		case "3":
		  System.out.println("请输入该轨道级数");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t2 = ast.getTracks().get(Integer.parseInt(line) - 1);
		  Electron e = new Electron("electron");
		  ast.addTrackObject(t2, e);
		  System.out.println("已成功为该轨道添加电子");
		  log.info("已成功为轨道" + line + "添加电子");
		  break;

		case "4":
		  System.out.println("请输入需要删除的轨道级数");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t3 = ast.getTracks().get(Integer.parseInt(line) - 1);
		  ast.removeTrack(t3);
		  System.out.println("已成功删除该轨道");
		  log.info("删除" + line + "轨道");
		  break;

		case "5":
		  System.out.println("请输入该轨道级数");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t4 = ast.getTracks().get(Integer.parseInt(line) - 1);

		  ArrayList<Electron> list = ast.getTrackObject(t4);
		  if (list.size() != 0) {
			list.remove(0);
			log.info("已成功从轨道" + line + "删除电子");
		  } else {
			System.out.println("该轨道为空！");
		  }
		  break;

		case "6":
		  CircularOrbitapis<Nucleus, Electron> c1 = new CircularOrbitapis<Nucleus, Electron>();
		  double entropy = c1.getObjectDistributionEntropy(ast);
		  System.out.printf("该原子结构模型各轨道物体分布的熵值为 %f\n", entropy);
		  log.info("该原子结构模型各轨道物体分布的熵值为 " + entropy);
		  break;

		case "7":
		  System.out.println("请分别输入原轨道和目标轨道级数：");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  int layer1 = Integer.valueOf(items[0]);
		  int layer2 = Integer.valueOf(items[1]);

		  Track t5 = ast.getTracks().get(layer1 - 1);
		  ArrayList<Electron> list1 = ast.getTrackObject(t5);
		  if (list1.size() != 0) {
			list1.remove(0);
		  } else {
			System.out.println("原该轨道为空！请重新输入");
			break;
		  }

		  Track t6 = ast.getTracks().get(layer2 - 1);
		  Electron e2 = new Electron("electron");
		  ast.addTrackObject(t6, e2);
		  System.out.println("已成功实现电子跃迁！");
		  log.info("已成功实现电子跃迁！");
		  break;

		case "8":
		  AtomStructure a2 = new AtomStructure();
		  String fileName2 = "txt/AtomicStructure2.txt";
		  File file2 = new File(fileName2);
		  ReadStrategy r2 = new AllBufferedReader();
		  a2.readFile(r2.readFile(file2));

		  CircularOrbitapis<Nucleus, Electron> c = new CircularOrbitapis<Nucleus, Electron>();
		  c.getDifference(ast, a2);

		  break;

		case "9":
		  printWriteMenu();
		  line = reader.readLine();
		  long startTime = System.currentTimeMillis();
		  switch (line) {
		  case "1":
			WriteStrategy w = new AtomStructureBufferWriter();
			w.writeFile("write_txt/AtomStructureWrite.txt", ast);
			//          	 	   ast.write(w);
			System.out.println("成功写入文件，AtomStructureBufferWriter时间为：");
			break;

		  case "2":
			WriteStrategy w2 = new AtomStructureOutputStream();
			w2.writeFile("write_txt/AtomStructureWrite.txt", ast);
			//       	 	  	 ast.write(w2);
			System.out.println("成功写入文件，AtomStructureOutputStream时间为：");

			break;

		  case "3":
			WriteStrategy w3 = new AtomStructureFileChannel();
			w3.writeFile("write_txt/AtomStructureWrite.txt", ast);
			//       	 	  	 ast.write(w3);
			System.out.println("成功写入文件，AtomStructureFileChannel时间为：");

			break;

		  default:
			System.out.println("输入选项错误！");
			break;
		  }

		  long finishTime = System.currentTimeMillis();
		  long totalTime = finishTime - startTime;
		  System.out.println(totalTime + "ms");

		  break;

		case "10":
		  flag = true;
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  log.info("输入选项错误，请重新输入！");
		  break;
		}
		if (flag) {
		  break;
		}

	  }

	} catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }

  /**simulateStellarSystem.*/
  public void simulateStellarSystem() {
	CircularOrbit<Stellar, Planet> ste = new StellarSystem();

	String fileName = "";
	// txt/StellarSystem_Exception.txt
//	do {
	  try {
		System.out.println("请输入读取的StellarSystem文件路径:");
		fileName = reader.readLine();
		if (fileName == null) {
		  System.out.println("输入为空！");
//		  break;
		}
		ste = new StellarSystem();
		log.info("已读取文件" + fileName);

		printReadMenu();
		String read = reader.readLine();
		File file = new File(fileName);
		System.out.println("StellarSystem读取文件时间：");
		switch (read) {
		case "1":
		  ReadStrategy r1 = new AllBufferedReader();
		  ste.readFile(r1.readFile(file));
		  break;

		case "2":
		  ReadStrategy r2 = new AllInputStream();
		  ste.readFile(r2.readFile(file));
		  break;

		case "3":
		  ReadStrategy r3 = new AllScanner();
		  ste.readFile(r3.readFile(file));
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  break;
		}
	  } catch (IOException e) {
		e.printStackTrace();
	  }
//	} while (ste.printException() == 1);

	System.out.println("已读取文件，生成行星运动模型：");
	//log.info("已读取文件"+fileName+"生成行星运动模型：");
//	LegalStellarSystem myJudge = new LegalStellarSystem();
//	if (myJudge.judgeLegality(ste) == 0) {
//	  System.out.println("出现异常！");
//	}
	String line;
	String[] items;

	boolean flag = false;

	try {
	  while (true) {
		menuStellarSystem();
		String choice = reader.readLine();
		if (choice == null) {
		  break;
		}
		switch (choice) {
		case "1":
		  DrawMovingOrbit.visualizes(ste);

		  break;
		case "2":
		  System.out.println("请输入需要添加的轨道半径");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t1 = new Track(translateData(line));
		  ste.addTrack(t1);
		  System.out.println("已成功添加该轨道");
		  log.info("已成功添加轨道");

		  break;

		case "3":
		  System.out.println("请输入该轨道半径");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入");
			break;
		  }
		  final Track t2 = ste.findTrack(translateData(line));
		  System.out.println("请输入行星<名称,形态,颜色,行星半径,轨道半径,公转,速度,公转方向,初始位置的角度>");

		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符

		  Planet planet = new Planet(items[0], items[1], items[2], translateData(items[3]), translateData(items[4]),
			  translateData(items[5]), items[6], translateData(items[7]));
		  ste.addTrackObject(t2, planet);
		  System.out.println("已成功为该轨道添加行星");
		  log.info("已成功为半径" + line + "的轨道添加" + items[0] + "行星");
		  break;

		case "4":
		  System.out.println("请输入需要删除的轨道半径");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t3 = ste.findTrack(translateData(line));
		  ste.removeTrack(t3);
		  System.out.println("已成功删除该轨道");
		  log.info("已成功删除半径为" + line + "的轨道");
		  break;

		case "5":
		  System.out.println("请输入该轨道半径");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t4 = ste.findTrack(translateData(line));
		  ArrayList<Planet> list = ste.getTrackObject(t4);

		  System.out.println("请输入删除的行星名");
		  String planetName = reader.readLine();

		  int i;
		  for (i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(planetName)) {
			  break;
			}
		  }

		  if (i < list.size()) {
			list.remove(i);
			System.out.println("已成功从轨道删除该行星");
			log.info("已成功从半径为" + line + "的轨道删除行星" + planetName);
		  } else {
			System.out.println("未找到该行星！");
			log.info("未找到该行星！");
		  }
		  break;

		case "6":
		  CircularOrbitapis<Stellar, Planet> c2 = new CircularOrbitapis<Stellar, Planet>();
		  double entropy = c2.getObjectDistributionEntropy(ste);
		  System.out.printf("该行星运动模型各轨道物体分布的熵值为 %f\n", entropy);
		  log.info("该行星运动模型各轨道物体分布的熵值为" + entropy);
		  break;

		case "7":
		  System.out.println("请输入经历的天数");
		  String timeStr = reader.readLine();

		  if (timeStr == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  double time = Double.parseDouble(timeStr); // 输入时间(天数)
		  double cycle; // 周期
		  double turns; // 圈数
		  double sitha; // 角度

		  ArrayList<ArrayList<Planet>> t2e = ste.getT2E();
		  for (int m = 0; m < t2e.size(); m++) {
			ArrayList<Planet> planetList = t2e.get(m);
			for (Planet p : planetList) {
			  cycle = 2 * Math.PI * p.getTrackRadius() / p.getSpeed(); // 秒
			  cycle = cycle / (3600 * 24); // 天
			  turns = time / cycle;

			  if (p.getOrientation().equals("CCW")) { // 逆时针度数增加
				sitha = (p.getSitha() + turns * 360) % 360;
			  } else {
				sitha = (p.getSitha() - turns * 360) % 360;
				if (sitha < 0) {
				  sitha += 360;
				}
			  }
			  System.out.printf("%f天后%s的轨道半径为%f，角度为%f\n", time, p.getName(), p.getTrackRadius(), sitha);
			  //			  String b = String.format("%f天后%s的轨道半径为%f，角度为%f\n", time, p.getName(), p.getTrackRadius(), sitha);
			}

		  }
		  break;

		case "8":
		  System.out.println("请输入行星的名称");
		  String pname = reader.readLine();
		  ArrayList<ArrayList<Planet>> t2e1 = ste.getT2E(); // 物体所在轨道
		  int a;
		  int b;
		  for (a = 0; a < t2e1.size(); a++) {
			ArrayList<Planet> plist = t2e1.get(a);
			for (b = 0; b < plist.size(); b++) {
			  if (plist.get(b).getName().equals(pname)) {
				System.out.printf("%s与恒星的距离为%fkm\n", pname, plist.get(b).getTrackRadius());
				String ss = String.format("%s与恒星的距离为%fkm\n", pname, plist.get(b).getTrackRadius());
				log.info(ss);
			  }
			}
		  }
		  break;

		case "9":
		  System.out.println("请输入分别输入两个行星的名称");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  Planet p1 = null;
		  Planet p2 = null;

		  ArrayList<ArrayList<Planet>> t2e2 = ste.getT2E(); // 物体所在轨道
		  for (a = 0; a < t2e2.size(); a++) {
			ArrayList<Planet> planetList = t2e2.get(a);
			for (b = 0; b < planetList.size(); b++) {
			  if (planetList.get(b).getName().equals(items[0])) {
				p1 = planetList.get(b);
			  }
			  if (planetList.get(b).getName().equals(items[1])) {
				p2 = planetList.get(b);
			  }
			}
		  }

		  CircularOrbitapis<Stellar, Planet> c3 = new CircularOrbitapis<Stellar, Planet>();
		  double distance = c3.getPhysicalDistance(ste, p1, p2);
		  System.out.printf("%s和%s之间的物理距离为%fkm\n", items[0], items[1], distance);
		  String sk = String.format("%s和%s之间的物理距离为%fkm\n", items[0], items[1], distance);
		  log.info(sk);
		  break;

		case "10":
		  CircularOrbit<Stellar, Planet> ste2 = new StellarSystem();
		  String fileName2 = "txt/StellarSystem2.txt";
		  File file2 = new File(fileName2);
		  ReadStrategy r2 = new AllBufferedReader();
		  ste2.readFile(r2.readFile(file2));

		  CircularOrbitapis<Stellar, Planet> c = new CircularOrbitapis<Stellar, Planet>();
		  c.getDifference(ste, ste2);
		  break;

		case "11":
		  printWriteMenu();
		  line = reader.readLine();
		  long startTime = System.currentTimeMillis();
		  switch (line) {
		  case "1":
			WriteStrategy w = new StellarSystemBufferWriter();
			w.writeFile("write_txt/StellarSystemWrite.txt", ste);
			//        	 	   ste.write(w);
			System.out.println("成功写入文件，StellarSystemBufferWriter时间为：");
			break;

		  case "2":
			WriteStrategy w2 = new StellarSystemOutputStream();
			w2.writeFile("write_txt/StellarSystemWrite.txt", ste);
			//     	 	  	 ste.write(w2);
			System.out.println("成功写入文件，StellarSystemOutputStream时间为：");

			break;

		  case "3":
			WriteStrategy w3 = new StellarSystemFileChannel();
			w3.writeFile("write_txt/StellarSystemWrite.txt", ste);
			//     	 	  	 ste.write(w3);
			System.out.println("成功写入文件，StellarSystemChannel时间为：");

			break;

		  default:
			System.out.println("输入选项错误！");
			break;
		  }

		  long finishTime = System.currentTimeMillis();
		  long totalTime = finishTime - startTime;
		  System.out.println(totalTime + "ms");

		  break;

		case "12":
		  flag = true;
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  log.info("输入选项错误，请重新输入！");
		  break;
		}
		if (flag) {
		  break;
		}

	  }

	} catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
  }

  /**simulateSocialNetwork.*/
  public void simulateSocialNetwork() {
	CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();

	String fileName = "";
	// txt/SocialNetworkCircle_Exception.txt
//	do {
	  try {
		System.out.println("请输入读取的SocialNetworkCircle文件路径:");
		fileName = reader.readLine();
		if (fileName == null) {
		  System.out.println("输入为空！");
//		  break;
		}
		soc = new SocialNetworkCircle();
		log.info("已读取文件" + fileName);

		printReadMenu();
		String read = reader.readLine();
		File file = new File(fileName);
		System.out.println("SocialNetworkCircle读取文件时间：");
		switch (read) {
		case "1":
		  ReadStrategy r1 = new AllBufferedReader();
		  soc.readFile(r1.readFile(file));
		  break;

		case "2":
		  ReadStrategy r2 = new AllInputStream();
		  soc.readFile(r2.readFile(file));
		  break;

		case "3":
		  ReadStrategy r3 = new AllScanner();
		  soc.readFile(r3.readFile(file));
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  break;
		}

	  } catch (IOException e) {
		e.printStackTrace();
	  }
//	} while (soc.printException() == 1);

	System.out.println("已读取文件，生成社交网络模型：");
	//  log.info("已读取文件"+fileName+"生成社交网络模型：");
//	LegalSocialNetworkCircle myJudge = new LegalSocialNetworkCircle();
//	if (myJudge.judgeLegality(soc) == 0) {
//	  System.out.println("出现异常！");
//	}
	String line;
	String[] items;

	boolean flag = false;

	try {
	  while (true) {
		menuSocialNetwork();
		String choice = reader.readLine();
		if (choice == null) {
		  System.out.println("请正确输入！");
		  break;
		}
		switch (choice) {
		case "1":
		  CircularOrbitHelper.visualizes(soc);

		  break;
		case "2":
		  Track t2 = new Track(1);
		  soc.addTrack(t2);
		  System.out.println("成功添加一条空轨道");
		  log.info("成功添加一条空轨道");
		  break;

		case "3":
		  System.out.println("请输入用户的姓名，年龄，性别");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  final Friend f1 = new Friend(items[0], Integer.parseInt(items[1]), items[2]);
		  System.out.println("请输入该用户与中心用户SocialTie");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f1, Float.parseFloat(items[2]));
		  ArrayList<ArrayList<Friend>> t2e = soc.getT2E();
		  t2e.get(0).add(f1);
		  System.out.println("请输入该用户与其他用户SocialTie");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  Friend f2 = new Friend("", 1, "F");
		  for (int i = 0; i < t2e.size(); i++) {
			for (Friend f : t2e.get(i)) {
			  if (f.getName().equals(items[1])) {
				f2 = f;
				break;
			  }
			}
		  }
		  soc.addTrackObjectsRelation(f1, f2, Float.parseFloat(items[2]));
		  log.info("成功添加" + items[0] + "与其他人的SocialTie");
		  break;

		case "4":
		  System.out.println("请输入需要删除的轨道级数");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Track t3 = soc.getTracks().get(Integer.parseInt(line) - 1);
		  soc.removeTrack(t3);
		  System.out.println("已成功删除该轨道");
		  log.info("已成功删除轨道" + line);
		  break;

		case "5":
		  System.out.println("请输入删除的用户名");
		  String friendName = reader.readLine();
		  if (friendName == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  Friend f3 = new Friend("", 1, "F");
		  ArrayList<ArrayList<Friend>> t2e3 = soc.getT2E();
		  int i = 0;
		  int j = 0;
		  for (i = 0; i < t2e3.size(); i++) {
			for (Friend f : t2e3.get(i)) {
			  if (f.getName().equals(friendName)) {
				f3 = f;
				j = i;
				break;
			  }
			}
		  }

		  t2e3.get(j).remove(f3);
		  System.out.println("已成功删除" + friendName);
		  log.info("已成功删除" + friendName);
		  break;

		case "6":
		  CircularOrbitapis<CentralUser, Friend> c3 = new CircularOrbitapis<CentralUser, Friend>();
		  double entropy = c3.getObjectDistributionEntropy(soc);
		  System.out.printf("该社交网络模型各轨道物体分布的熵值为 %f\n", entropy);
		  log.info("该社交网络模型各轨道物体分布的熵值为" + entropy);
		  break;

		case "7":
		  ArrayList<ArrayList<Friend>> t2e4 = soc.getT2E();
		  for (int ii = 0; ii < t2e4.size(); ii++) {
			System.out.println("轨道" + (ii + 1) + "的用户：");
			for (Friend f : t2e4.get(ii)) {
			  System.out.println(f.getName());
			}
		  }
		  break;

		case "8":
		  ArrayList<ArrayList<Friend>> t2e5 = soc.getT2E();
		  System.out.println("轨道1的好友如下：");
		  for (Friend f : t2e5.get(0)) {
			System.out.println(f.getName());
		  }
		  System.out.println("请选择好友的名称");
		  String friendName1 = reader.readLine();

		  Friend f4 = new Friend("", 1, "F");
		  ArrayList<ArrayList<Friend>> t2e6 = soc.getT2E();
		  int i1;
		  for (i1 = 0; i1 < t2e6.size(); i1++) {
			for (Friend f : t2e6.get(i1)) {
			  if (f.getName().equals(friendName1)) {
				f4 = f;
				break;
			  }
			}
		  }

		  EeIntimacyMap<Friend> e2e = soc.getE2E();

		  int counter = e2e.getNumOfClosers(f4, 0.5);
		  System.out.printf("%s的信息扩散度为%d\n", friendName1, counter);
		  log.info(friendName1 + "的信息扩散度为" + counter);
		  break;

		case "9":
		  System.out.println("请输入需要添加的SocialTie");

		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  String name1 = items[0];
		  String name2 = items[1];
		  String intimacy = items[2];

		  ArrayList<ArrayList<Friend>> t2e7 = soc.getT2E();
		  Friend f10 = new Friend("", 1, "F");
		  Friend f20 = new Friend("", 1, "F");

		  if (name1.equals(soc.getCentralObject().getName())) {
			for (int q = 0; q < t2e7.size(); q++) {
			  for (Friend f : t2e7.get(q)) {
				if (f.getName().equals(name2)) {
				  f10 = f;
				}
			  }
			}
			for (ArrayList<Friend> list : soc.getT2E()) { // 从原轨道删除
			  for (Friend friend : list) {
				if (friend.equals(f10)) {
				  list.remove(f10);
				  break;
				}
			  }
			}

			soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f10, Float.parseFloat(intimacy));
		  } else if (name2.equals(soc.getCentralObject().getName())) {
			for (int q = 0; q < t2e7.size(); q++) {
			  for (Friend f : t2e7.get(q)) {
				if (f.getName().equals(name1)) {
				  f10 = f;
				}
			  }
			}
			for (ArrayList<Friend> list : soc.getT2E()) { // 从原轨道删除
			  for (Friend friend : list) {
				if (friend.equals(f10)) {
				  list.remove(f10);
				  break;
				}
			  }
			}

			soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f10, Float.parseFloat(intimacy));
		  } else {
			for (int q = 0; q < t2e7.size(); q++) {
			  for (Friend f : t2e7.get(q)) {
				if (f.getName().equals(name1)) {
				  f10 = f;
				}
				if (f.getName().equals(name2)) {
				  f20 = f;
				}
			  }
			}
			soc.addTrackObjectsRelation(f10, f20, Float.parseFloat(intimacy));

		  }
		  Set<Friend> friendSet = new HashSet<Friend>();
		  for (ArrayList<Friend> list : soc.getT2E()) { // 从原轨道删除
			for (Friend friend : list) {
			  friendSet.add(friend);
			}

		  }
		  soc.getTracks().clear(); // 清除轨道，为了重新建造轨道
		  soc.getT2E().clear();

		  BuildRelation b = new BuildRelation();
		  b.buildRelation(soc, friendSet);

		  log.info("已成功添加SocialTie" + line);
		  break;

		case "10":
		  System.out.println("请输入需要删掉的人物关系");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  String n1 = items[0];
		  String n2 = items[1];

		  ArrayList<ArrayList<Friend>> t2e8 = soc.getT2E();
		  Friend ff = new Friend("", 1, "F");
		  Friend fr = new Friend("", 1, "F");

		  if (n1.equals(soc.getCentralObject().getName())) {
			for (int q = 0; q < t2e8.size(); q++) {
			  for (Friend f : t2e8.get(q)) {
				if (f.getName().equals(n2)) {
				  ff = f;
				}
			  }
			}

			soc.removeCenterTrackObjectsRelation(soc.getCentralObject(), ff);
		  } else if (n2.equals(soc.getCentralObject().getName())) {
			for (int q = 0; q < t2e8.size(); q++) {
			  for (Friend f : t2e8.get(q)) {
				if (f.getName().equals(n1)) {
				  ff = f;
				}
			  }
			}
			soc.removeCenterTrackObjectsRelation(soc.getCentralObject(), ff);
		  } else {
			for (int q = 0; q < t2e8.size(); q++) {
			  for (Friend f : t2e8.get(q)) {
				if (f.getName().equals(n1)) {
				  ff = f;
				}
				if (f.getName().equals(n2)) {
				  fr = f;
				}
			  }
			}
			soc.removeTrackObjectsRelation(ff, fr);
		  }

		  Set<Friend> friendSet1 = new HashSet<Friend>();
		  for (ArrayList<Friend> list : soc.getT2E()) { // 从原轨道删除
			for (Friend friend : list) {
			  friendSet1.add(friend);
			}

		  }
		  soc.getTracks().clear(); // 清除轨道，为了重新建造轨道
		  soc.getT2E().clear();

		  BuildRelation b1 = new BuildRelation();
		  b1.buildRelation(soc, friendSet1);

		  log.info("已成功删除人物关系" + line);
		  break;

		case "11":
		  System.out.println("请输入分别输入两个用户的名称");
		  line = reader.readLine();
		  if (line == null) {
			System.out.println("请正确输入！");
			break;
		  }
		  items = line.split("\\s"); // 分开空白字符
		  Friend fr1 = null;
		  Friend fr2 = null;

		  ArrayList<ArrayList<Friend>> t2e10 = soc.getT2E(); // 物体所在轨道
		  for (int a = 0; a < t2e10.size(); a++) {
			ArrayList<Friend> friendList = t2e10.get(a);
			for (int bb = 0; bb < friendList.size(); bb++) {
			  if (friendList.get(bb).getName().equals(items[0])) {
				fr1 = friendList.get(bb);
			  }
			  if (friendList.get(bb).getName().equals(items[1])) {
				fr2 = friendList.get(bb);
			  }
			}
		  }

		  CircularOrbitapis<CentralUser, Friend> c4 = new CircularOrbitapis<CentralUser, Friend>();
		  int logicalDistance = c4.getLogicalDistance(soc, fr1, fr2);
		  System.out.printf("用户%s和用户%s之间的逻辑距离为%d\n", items[0], items[1], logicalDistance);
		  String ls = String.format("用户%s和用户%s之间的逻辑距离为%d\n", items[0], items[1], logicalDistance);
		  log.info(ls);
		  break;

		case "12":
		  CircularOrbit<CentralUser, Friend> soc2 = new SocialNetworkCircle();
		  String fileName2 = "txt/SocialNetworkCircle2.txt";
		  File file2 = new File(fileName2);

		  ReadStrategy r2 = new AllBufferedReader();
		  soc2.readFile(r2.readFile(file2));

		  CircularOrbitapis<CentralUser, Friend> c = new CircularOrbitapis<CentralUser, Friend>();
		  c.getDifference(soc, soc2);
		  break;

		case "13":
		  printWriteMenu();
		  line = reader.readLine();
		  long startTime = System.currentTimeMillis();
		  switch (line) {
		  case "1":
			WriteStrategy w = new SocialNetworkCircleBufferWriter();
			w.writeFile("write_txt/SocialNetworkCircleWrite.txt", soc);
			//        	 	   soc.write(w);
			System.out.println("成功写入文件，StellarSystemBufferWriter时间为：");
			break;

		  case "2":
			WriteStrategy w2 = new SocialNetworkCircleOutputStream();
			w2.writeFile("write_txt/SocialNetworkCircleWrite.txt", soc);
			//     	 	  	 soc.write(w2);
			System.out.println("成功写入文件，SocialNetworkCircleOutputStream时间为：");

			break;

		  case "3":
			WriteStrategy w3 = new SocialNetworkCircleChannel();
			w3.writeFile("write_txt/SocialNetworkCircleWrite.txt", soc);
			//     	 	  	 soc.write(w3);
			System.out.println("成功写入文件，SocialNetworkCircleChannel时间为：");

			break;

		  default:
			System.out.println("输入选项错误！");
			break;
		  }

		  long finishTime = System.currentTimeMillis();
		  long totalTime = finishTime - startTime;
		  System.out.println(totalTime + "ms");

		  break;

		case "14":
		  flag = true;
		  break;

		default:
		  System.out.println("输入选项错误，请重新输入！");
		  log.info("输入选项错误，请重新输入！");
		  break;
		}
		if (flag) {
		  break;
		}
	  }
	} catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}

  }

  /** Print menu for selection. */
  public void mainMenu() {
	System.out.println("请从以下的具体应用中选择一项：");
	System.out.println("1.AtomStrcture");
	System.out.println("2.StellarSystem");
	System.out.println("3.SocialNetwork");
	System.out.println("4.Log query");
	System.out.println("5.Exit");
  }

  /** Print menu for AtmoStrcture. */
  public void menuAtomStrcture() {
	System.out.println("请从以下的具体功能中选择一项：");
	System.out.println("1.在GUI上可视化展示原子结构模型");
	System.out.println("2.增加轨道");
	System.out.println("3.向特定轨道上增加电子");
	System.out.println("4.删除轨道");
	System.out.println("5.从特定轨道删除电子");
	System.out.println("6.计算原子结构模型各轨道物体分布的熵值");
	System.out.println("7.模拟电子跃迁");
	System.out.println("8.比较两个原子结构模型的不同");
	System.out.println("9.写入文件");
	System.out.println("10.end");
  }

  /** Print menu for StellarSystem. */
  public void menuStellarSystem() {
	System.out.println("请从以下的具体功能中选择一项：");
	System.out.println("1.可视化模拟行星运动");
	System.out.println("2.增加轨道");
	System.out.println("3.向特定轨道上增加物体");
	System.out.println("4.删除轨道");
	System.out.println("5.从特定轨道删除物体");
	System.out.println("6.计算行星运动模型各轨道物体分布的熵值");
	System.out.println("7.展示特定时刻行星的位置");
	System.out.println("8.计算恒星与某行星的物理距离");
	System.out.println("9.计算两颗行星的物理距离");
	System.out.println("10.比较两个行星运动模型的不同");
	System.out.println("11.写入文件");
	System.out.println("12.end");
  }

  /** Print menu for AtmoStrcture. */
  public void menuSocialNetwork() {
	System.out.println("请从以下的具体功能中选择一项：");
	System.out.println("1.在GUI上可视化展示社交网络模型");
	System.out.println("2.增加轨道");
	System.out.println("3.向特定轨道上增加用户");
	System.out.println("4.删除轨道");
	System.out.println("5.从特定轨道删除用户");
	System.out.println("6.计算社交网络模型各轨道物体分布的熵值");
	System.out.println("7.判断每个用户是哪个轨道上的");
	System.out.println("8.计算某个处于第一条轨道上好友的\"信息扩散度\"");
	System.out.println("9.增加一条社交关系");
	System.out.println("10.删除一条社交关系");
	System.out.println("11.计算两个轨道上用户之间的逻辑距离");
	System.out.println("12.比较两个社交网络模型的不同");
	System.out.println("13.写入文件");
	System.out.println("14.end");
  }

  /**
   * Translate a data from string type to double Numbers greater than 10,000.
   * according to scientific notation and Numbers less than 10000 are given
   * directly
   * 
   * @param s label of the number string
   * @return double type of the number
   */
  public double translateData(String s) {
	String[] str = s.split("e");
	if (str.length == 1) {
	  return Double.parseDouble(s);
	} else {
	  double a = Double.parseDouble(str[0]);
	  int b = Integer.parseInt(str[1]);

	  double ret = a * Math.pow(10, b);

	  return ret;
	}
  }

  /**
   * Query the log through the user's input.
   */
  public void query() {
	System.out.println("请输入过滤条件：");
	System.out.println("格式<类型，开始时间，结束时间，类，方法，操作类型>");
	String filterCondition = null;
	try {
	  filterCondition = reader.readLine();
	} catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}
	if (filterCondition == null) {
	  System.out.println("请正确输入！");
	  return;
	}
	String[] filterItems = filterCondition.split("\\s"); // 分开空白字符

	try {
	  String fileName = "log/info.log";
	  File file = new File(fileName);
	  InputStreamReader readin = null;

	  readin = new InputStreamReader(new FileInputStream(file));

	  BufferedReader bf = new BufferedReader(readin);
	  String line;
	  while ((line = bf.readLine()) != null) {
		String[] logItems = line.split("\\s"); // 分开空白字符
		String strDateBegin = filterItems[1] + " " + filterItems[2];
		String strDateEnd = filterItems[3] + " " + filterItems[4];
		String strDate = logItems[1] + " " + logItems[2];

		if (logItems[0].contains(filterItems[0]) && isInDate(strDate, strDateBegin, strDateEnd)
			&& logItems[4].contains(filterItems[5]) && logItems[5].contains(filterItems[6])
			&& logItems[6].contains(filterItems[7])) {
		  queryLogList.add(line);
		}
	  }
	  bf.close();
	} catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	}

	System.out.println("查询结果如下：");
	String[] res;
	for (String s : queryLogList) {
	  res = s.split("\\s");
	  System.out.println("[类型]" + "   " + res[0]);
	  System.out.println("[时间]" + "   " + res[1] + " " + res[2]);
	  System.out.println("[类]" + "   " + res[4]);
	  System.out.println("[方法]" + "   " + res[5]);
	  System.out.println("[操作]" + "   " + res[6]);
	  System.out.println();
	}

  }

  /**
   * Judge if a strDate is within the time period.
   * 
   * @param strDate      moment to be judged
   * @param strDateBegin the beginning of the time period
   * @param strDateEnd   the end of the time period
   * @return true if a strDate is within the time period , else false
   */
  public boolean isInDate(String strDate, String strDateBegin, String strDateEnd) {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date nowTime = null;
	Date beginTime = null;
	Date endTime = null;
	try {
	  nowTime = sdf.parse(strDate);
	  beginTime = sdf.parse(strDateBegin);
	  endTime = sdf.parse(strDateEnd);
	} catch (Exception e) {
	  e.printStackTrace();
	}
	Calendar date = Calendar.getInstance();
	if (nowTime == null || beginTime == null || endTime == null) {
	  return false;
	}

	date.setTime(nowTime);
	//设置开始时间
	Calendar begin = Calendar.getInstance();
	begin.setTime(beginTime);
	//设置结束时间
	Calendar end = Calendar.getInstance();
	end.setTime(endTime);

	if (date.after(begin) && date.before(end)) {
	  return true;
	} else {
	  return false;
	}
  }

  public void printReadMenu() {
	System.out.println("请从以下读文件的策略中选择一项：");
	System.out.println("1.BufferReader");
	System.out.println("2.InputStream");
	System.out.println("3.Scanner");
  }

  public void printWriteMenu() {
	System.out.println("请从以下写文件的策略中选择一项：");
	System.out.println("1.BufferWriter");
	System.out.println("2.OutputStream");
	System.out.println("3.FileChannel");
  }

}
