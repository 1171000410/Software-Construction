package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import centralObject.CentralUser;
import centralObject.Nucleus;
import centralObject.Stellar;
import circularOrbit.AtomStructure;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircle;
import circularOrbit.StellarSystem;
import legality.Legal_SocialNetworkCircle;
import legality.Legal_StellarSystem;
import physicalObject.Electron;
import physicalObject.Friend;
import physicalObject.Planet;
import relation.EE_IntimacyMap;
import track.Track;
import APIS.*;

public class MyOrbitScenes {

	BufferedReader reader = null;

	public static void main(String[] args) {
		MyOrbitScenes newOrbit = new MyOrbitScenes();
		try {
			newOrbit.myMain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void myMain() throws Exception {
//		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			boolean flag = true;

			while (flag) {
				mainMenu();
				String choice = reader.readLine();
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

				default:
					System.out.println("输入选项错误，请重新输入！");
				}

			}
			System.out.println("模拟结束！");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
	}

	public void simulateAtomStrcture() {
		CircularOrbit<Nucleus, Electron> ast = new AtomStructure();
		String fileName = "txt/AtomicStructure.txt";
		File file = new File(fileName);
		ast.readFile(file);
		System.out.println("已读取文件，生成原子结构模型：");
		String line;
		String[] items;

		boolean flag = false;

		try {
			while (true) {
				menuAtomStrcture();
				String choice = reader.readLine();
				switch (choice) {
				case "1":
					CircularOrbitHelper2.visualizes(ast);

					break;
				case "2":
					System.out.println("请输入需要添加的轨道半径");
					line = reader.readLine();
					Track t1 = new Track(Double.parseDouble(line));
					ast.addTrack(t1);
					System.out.println("已成功添加该轨道");
					break;

				case "3":
					System.out.println("请输入该轨道级数");
					line = reader.readLine();
					Track t2 = ast.getTracks().get(Integer.parseInt(line) - 1);
					Electron e = new Electron("electron");
					ast.addTrackObject(t2, e);
					System.out.println("已成功为该轨道添加电子");
					break;

				case "4":
					System.out.println("请输入需要删除的轨道级数");
					line = reader.readLine();
					Track t3 = ast.getTracks().get(Integer.parseInt(line) - 1);
					ast.removeTrack(t3);
					System.out.println("已成功删除该轨道");
					break;

				case "5":
					System.out.println("请输入该轨道级数");
					line = reader.readLine();
					Track t4 = ast.getTracks().get(Integer.parseInt(line) - 1);

					ArrayList<Electron> list = ast.getTrackObject(t4);
					if (list.size() != 0) {
						list.remove(0);
						System.out.println("已成功从该轨道删除电子");
					} else {
						System.out.println("该轨道为空！");
					}
					break;

				case "6":
					CircularOrbitAPIS<Nucleus, Electron> c1 = new CircularOrbitAPIS<Nucleus, Electron>();
					double entropy = c1.getObjectDistributionEntropy(ast);
					System.out.printf("该原子结构模型各轨道物体分布的熵值为 %f\n", entropy);
					break;

				case "7":
					System.out.println("请分别输入原轨道和目标轨道级数：");
					line = reader.readLine();
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

					break;

				case "8":
					AtomStructure a2 = new AtomStructure();
					String fileName2 = "txt/AtomicStructure2.txt";
					File file2 = new File(fileName2);
					a2.readFile(file2);
					
					CircularOrbitAPIS<Nucleus, Electron> c = new CircularOrbitAPIS<Nucleus, Electron>();
					c.getDifference(ast, a2);
					
					break;
					
				case "9":
					flag = true;
					break;

				default:
					System.out.println("输入选项错误，请重新输入！");
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

	public void simulateStellarSystem() {
		CircularOrbit<Stellar, Planet> ste = new StellarSystem();
		String fileName = "txt/StellarSystem.txt";
		File file = new File(fileName);
		ste.readFile(file);
		System.out.println("已读取文件，生成行星运动模型：");
		Legal_StellarSystem myJudge = new Legal_StellarSystem();
		if(myJudge.judgeLegality(ste) == 0) {
			System.out.println("出现异常！");
		}
		String line;
		String[] items;

		boolean flag = false;

		try {
			while (true) {
				menuStellarSystem();
				String choice = reader.readLine();
				switch (choice) {
				case "1":
					DrawMovingOrbit.visualizes(ste);

					break;
				case "2":
					System.out.println("请输入需要添加的轨道半径");
					line = reader.readLine();
					Track t1 = new Track(translateData(line));
					ste.addTrack(t1);
					System.out.println("已成功添加该轨道");
					break;

				case "3":
					System.out.println("请输入该轨道半径");
					line = reader.readLine();
					Track t2 = ste.findTrack(translateData(line));
					System.out.println("请输入行星<名称,形态,颜色,行星半径,轨道半径,公转,速度,公转方向,初始位置的角度>");

					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符

					Planet planet = new Planet(items[0], items[1], items[2], translateData(items[3]),
							translateData(items[4]), translateData(items[5]), items[6], translateData(items[7]));
					ste.addTrackObject(t2, planet);
					System.out.println("已成功为该轨道添加行星");
					break;

				case "4":
					System.out.println("请输入需要删除的轨道半径");
					line = reader.readLine();
					Track t3 = ste.findTrack(translateData(line));
					ste.removeTrack(t3);
					System.out.println("已成功删除该轨道");
					break;

				case "5":
					System.out.println("请输入该轨道半径");
					line = reader.readLine();
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
					} else {
						System.out.println("未找到该行星！");
					}
					break;

				case "6":
					CircularOrbitAPIS<Stellar, Planet> c2 = new CircularOrbitAPIS<Stellar, Planet>();
					double entropy = c2.getObjectDistributionEntropy(ste);
					System.out.printf("该原子结构模型各轨道物体分布的熵值为 %f\n", entropy);
					break;

				case "7":
					System.out.println("请输入经历的天数");
					String timeStr = reader.readLine();

					double time = Double.parseDouble(timeStr); // 输入时间(天数)
					double cycle; // 周期
					double turns; // 圈数
					double sitha; // 角度

					ArrayList<ArrayList<Planet>> T2E = ste.getT2E();
					for (int m = 0; m < T2E.size(); m++) {
						ArrayList<Planet> planetList = T2E.get(m);
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
						}

					}
					break;

				case "8":
					System.out.println("请输入行星的名称");
					String pName = reader.readLine();
					ArrayList<ArrayList<Planet>> T2E1 = ste.getT2E(); // 物体所在轨道
					int a, b;
					for (a = 0; a < T2E1.size(); a++) {
						ArrayList<Planet> pList = T2E1.get(a);
						for (b = 0; b < pList.size(); b++) {
							if (pList.get(b).getName().equals(pName)) {
								System.out.printf("%s与恒星的距离为%fkm\n", pName, pList.get(b).getTrackRadius());
							}
						}
					}
					break;

				case "9":
					System.out.println("请输入分别输入两个行星的名称");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					Planet p1 = null, p2 = null;

					ArrayList<ArrayList<Planet>> T2E2 = ste.getT2E(); // 物体所在轨道
					for (a = 0; a < T2E2.size(); a++) {
						ArrayList<Planet> planetList = T2E2.get(a);
						for (b = 0; b < planetList.size(); b++) {
							if (planetList.get(b).getName().equals(items[0])) {
								p1 = planetList.get(b);
							}
							if (planetList.get(b).getName().equals(items[1])) {
								p2 = planetList.get(b);
							}
						}
					}

					CircularOrbitAPIS<Stellar, Planet> c3 = new CircularOrbitAPIS<Stellar, Planet>();
					double distance = c3.getPhysicalDistance(ste, p1, p2);
					System.out.printf("%s和%s之间的物理距离为%fkm\n", items[0], items[1], distance);
					break;

				case "10":
					CircularOrbit<Stellar, Planet> ste2 = new StellarSystem();
					String fileName2 = "txt/StellarSystem2.txt";
					File file2 = new File(fileName2);
					ste2.readFile(file2);
					
					CircularOrbitAPIS<Stellar, Planet> c = new CircularOrbitAPIS<Stellar, Planet>();
					c.getDifference(ste, ste2);
					break;
					
				case "11":
					flag = true;
					break;
					
				default:
					System.out.println("输入选项错误，请重新输入！");
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

	public void simulateSocialNetwork() {
		CircularOrbit<CentralUser, Friend> soc = new SocialNetworkCircle();
		String fileName = "txt/SocialNetworkCircle.txt";
		File file = new File(fileName);
		soc.readFile(file);
		System.out.println("已读取文件，生成社交网络模型：");
		Legal_SocialNetworkCircle myJudge = new Legal_SocialNetworkCircle();
		if(myJudge.judgeLegality(soc) == 0) {
			System.out.println("出现异常！");
		}
		String line;
		String[] items;

		boolean flag = false;

		try {
			while (true) {
				menuSocialNetwork();
				String choice = reader.readLine();
				switch (choice) {
				case "1":
					CircularOrbitHelper.visualizes(soc);

					break;
				case "2":

					Track t2 = new Track(1);
					soc.addTrack(t2);
					System.out.println("添加一条空轨道");
					break;

				case "3":
					System.out.println("请输入用户的姓名，年龄，性别");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					Friend f1 = new Friend(items[0], Integer.parseInt(items[1]), items[2]);
					System.out.println("请输入该用户与中心用户SocialTie");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f1, Float.parseFloat(items[2]));
					ArrayList<ArrayList<Friend>> T2E = soc.getT2E();
					T2E.get(0).add(f1);
					System.out.println("请输入该用户与其他用户SocialTie");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					Friend f2 = new Friend("", 1, "F");
					for (int i = 0; i < T2E.size(); i++) {
						for (Friend f : T2E.get(i)) {
							if (f.getName().equals(items[1])) {
								f2 = f;
								break;
							}
						}
					}
					soc.addTrackObjectsRelation(f1, f2, Float.parseFloat(items[2]));
					break;

				case "4":
					System.out.println("请输入需要删除的轨道级数");
					line = reader.readLine();
					Track t3 = soc.getTracks().get(Integer.parseInt(line) - 1);
					soc.removeTrack(t3);
					System.out.println("已成功删除该轨道");
					break;

				case "5":
					System.out.println("请输入删除的用户名");
					String friendName = reader.readLine();

					Friend f3 = new Friend("", 1, "F");
					ArrayList<ArrayList<Friend>> T2E3 = soc.getT2E();
					int i, j = 0;
					for (i = 0; i < T2E3.size(); i++) {
						for (Friend f : T2E3.get(i)) {
							if (f.getName().equals(friendName)) {
								f3 = f;
								j = i;
								break;
							}
						}
					}

					T2E3.get(j).remove(f3);
					break;

				case "6":
					CircularOrbitAPIS<CentralUser, Friend> c3 = new CircularOrbitAPIS<CentralUser, Friend>();
					double entropy = c3.getObjectDistributionEntropy(soc);
					System.out.printf("该社交网络模型各轨道物体分布的熵值为 %f\n", entropy);
					break;

				case "7":
					ArrayList<ArrayList<Friend>> T2E4 = soc.getT2E();
					for (int ii = 0; ii < T2E4.size(); ii++) {
						System.out.println("轨道" + (ii + 1) + "的用户：");
						for (Friend f : T2E4.get(ii)) {
							System.out.println(f.getName());
						}
					}
					break;

				case "8":
					ArrayList<ArrayList<Friend>> T2E5 = soc.getT2E();
					System.out.println("轨道1的好友如下：");
					for (Friend f : T2E5.get(0)) {
						System.out.println(f.getName());
					}
					System.out.println("请选择好友的名称");
					String friendName1 = reader.readLine();

					Friend f4 = new Friend("", 1, "F");
					ArrayList<ArrayList<Friend>> T2E6 = soc.getT2E();
					int i1;
					for (i1 = 0; i1 < T2E6.size(); i1++) {
						for (Friend f : T2E6.get(i1)) {
							if (f.getName().equals(friendName1)) {
								f4 = f;
								break;
							}
						}
					}

					EE_IntimacyMap<Friend> E2E = soc.getE2E();

					int counter = E2E.getNumOfClosers(f4, 0.5);
					System.out.printf("%s的信息扩散度为%d\n", friendName1, counter);
					break;

				case "9":
					System.out.println("请输入需要添加的SocialTie");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					String name1 = items[0];
					String name2 = items[1];
					String intimacy = items[2];

					ArrayList<ArrayList<Friend>> T2E7 = soc.getT2E();
					Friend f10 = new Friend("", 1, "F");
					Friend f20 = new Friend("", 1, "F");

					if (name1.equals(soc.getCentralObject().getName())) {
						for (int q = 0; q < T2E7.size(); q++) {
							for (Friend f : T2E7.get(q)) {
								if (f.getName().equals(name2)) {
									f10 = f;
								}
							}
						}
						for(ArrayList<Friend> list : soc.getT2E()) { //从原轨道删除
							for(Friend friend : list)
								if(friend.equals(f10)) {
									list.remove(f10);
									break;
								}
						}
	
						soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f10, Float.parseFloat(intimacy));
					}
					else if (name2.equals(soc.getCentralObject().getName())) {
						for (int q = 0; q < T2E7.size(); q++) {
							for (Friend f : T2E7.get(q)) {
								if (f.getName().equals(name1)) {
									f10 = f;
								}
							}
						}
						for(ArrayList<Friend> list : soc.getT2E()) { //从原轨道删除
							for(Friend friend : list)
								if(friend.equals(f10)) {
									list.remove(f10);
									break;
								}
						}
						
						soc.addCenterTrackObjectsRelation(soc.getCentralObject(), f10, Float.parseFloat(intimacy));
					}

					else {
						for (int q = 0; q < T2E7.size(); q++) {
							for (Friend f : T2E7.get(q)) {
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
					for(ArrayList<Friend> list : soc.getT2E()) { //从原轨道删除
						for(Friend friend : list) {
							friendSet.add(friend);
						}
							
					}
					soc.getTracks().clear();   //清除轨道，为了重新建造轨道
					soc.getT2E().clear();
					
					BuildRelation b = new BuildRelation();
					b.buildRelation(soc, friendSet);
					
					
					break;

		
				case "10":
					System.out.println("请输入需要删掉的人物关系");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					String n1 = items[0];
					String n2 = items[1];

					ArrayList<ArrayList<Friend>> T2E8 = soc.getT2E();
					Friend ff = new Friend("", 1, "F");
					Friend fr = new Friend("", 1, "F");

					if (n1.equals(soc.getCentralObject().getName())) {
						for (int q = 0; q < T2E8.size(); q++) {
							for (Friend f : T2E8.get(q)) {
								if (f.getName().equals(n2)) {
									ff = f;
								}
							}
						}

						soc.removeCenterTrackObjectsRelation(soc.getCentralObject(), ff);
					}
					else if (n2.equals(soc.getCentralObject().getName())) {
						for (int q = 0; q < T2E8.size(); q++) {
							for (Friend f : T2E8.get(q)) {
								if (f.getName().equals(n1)) {
									ff = f;
								}
							}
						}
						soc.removeCenterTrackObjectsRelation(soc.getCentralObject(), ff);
					}

					else {
						for (int q = 0; q < T2E8.size(); q++) {
							for (Friend f : T2E8.get(q)) {
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
					for(ArrayList<Friend> list : soc.getT2E()) { //从原轨道删除
						for(Friend friend : list) {
							friendSet1.add(friend);
						}
							
					}
					soc.getTracks().clear();   //清除轨道，为了重新建造轨道
					soc.getT2E().clear();
					
					BuildRelation b1 = new BuildRelation();
					b1.buildRelation(soc, friendSet1);
					
					break;
					
				case "11":
					System.out.println("请输入分别输入两个用户的名称");
					line = reader.readLine();
					items = line.split("\\s"); // 分开空白字符
					Friend fr1 = null , fr2 = null;

					ArrayList<ArrayList<Friend>> T2E10 = soc.getT2E(); // 物体所在轨道
					for (int a = 0; a < T2E10.size(); a++) {
						ArrayList<Friend> friendList = T2E10.get(a);
						for (int bb = 0; bb < friendList.size(); bb++) {
							if (friendList.get(bb).getName().equals(items[0])) {
								fr1 = friendList.get(bb);
							}
							if (friendList.get(bb).getName().equals(items[1])) {
								fr2 = friendList.get(bb);
							}
						}
					}

					CircularOrbitAPIS<CentralUser , Friend> c4 = new CircularOrbitAPIS<CentralUser , Friend>();
					int logicalDistance = c4.getLogicalDistance(soc, fr1, fr2);
					System.out.printf("用户%s和用户%s之间的逻辑距离为%d\n", items[0], items[1], logicalDistance);
					break;
					
				case "12":	
					CircularOrbit<CentralUser, Friend> soc2 = new SocialNetworkCircle();
					String fileName2 = "txt/SocialNetworkCircle2.txt";
					File file2 = new File(fileName2);
					soc2.readFile(file2);
					
					CircularOrbitAPIS<CentralUser, Friend> c = new CircularOrbitAPIS<CentralUser, Friend>();
					c.getDifference(soc, soc2);
					break;
					
				case "13":
					flag = true;
					break;

				default:
					System.out.println("输入选项错误，请重新输入！");
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

	/** Print menu for selection */
	public void mainMenu() {
		System.out.println("请从以下的具体应用中选择一项：");
		System.out.println("1.AtmoStrcture");
		System.out.println("2.StellarSystem");
		System.out.println("3.SocialNetwork");
	}

	/** Print menu for AtmoStrcture */
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
		System.out.println("9.end");
	}

	/** Print menu for StellarSystem */
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
		System.out.println("11.end");
	}

	/** Print menu for AtmoStrcture */
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
		System.out.println("13.end");
	}

	/**
	 * Translate a data from string type to double
	 * Numbers greater than 10,000 according to scientific notation
	 * and Numbers less than 10000 are given directly
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
}
