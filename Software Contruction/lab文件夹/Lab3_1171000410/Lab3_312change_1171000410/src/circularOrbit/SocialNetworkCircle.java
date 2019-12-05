package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applications.BuildRelation;
import centralObject.CentralUser;
import physicalObject.Friend;

public class SocialNetworkCircle extends WithoutPosition<CentralUser, Friend>
		implements CircularOrbit<CentralUser, Friend> {

	private Set<Friend> friendSet = new HashSet<Friend>();

	// Abstraction function:
	// represents SocialNetworkCircle inherited from WithoutPosition<CentralUser,
	// Friend>
	// Central object is CentralUser and the orbital object is Friend
	// Representation invariant:
	// friendSet is the set of friends
	//
	// Safety from rep exposure:
	// All fields are private and final
	// friendSet is mutable type, so operations use defensive copies and
	// immutable wrappers to avoid sharing the rep's objects to clients

	/** Constructor */
	public SocialNetworkCircle() {
	}

	/** Get name of SocialNetworkCircle */
	@Override
	public String getSystemName() {
		return "SocialNetworkCircle";
	}

	public Set<Friend> getFriends() {
		return Collections.unmodifiableSet(friendSet);
	}

	/** Read file and create SocialNetworkCircle */
	@Override
	public void readFile(File file) {
		try {
			StringBuffer buffer = new StringBuffer();
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = bf.readLine()) != null) { // 使用readLine方法，一次读一行
				buffer.append(s.trim());
			}

			String fileStr = buffer.toString();
			String regex = "\\s+";
			String newFlieStr = fileStr.replaceAll(regex, ""); // 去掉空格
			String[] args = { "(CentralUser)::=<(.{1,20})>", "(SocialTie)::=<(.{1,30})>", "(Friend)::=<(.{1,20})>" };

			Pattern pattern1 = Pattern.compile(args[0]); // 构造正则表达式
			Matcher mentioned1 = pattern1.matcher(newFlieStr); // 匹配CentralUser

			if (mentioned1.find()) {
				String[] str = mentioned1.group(2).split(",");
				CentralUser centralUser = new CentralUser(str[0], Integer.parseInt(str[1]), str[2]);
				addCenterObject(centralUser);
			}

			Pattern pattern2 = Pattern.compile(args[2]); // 构造正则表达式
			Matcher mentioned2 = pattern2.matcher(newFlieStr); // 匹配Friend
			while (mentioned2.find()) {
				String[] str = mentioned2.group(2).split(",");
				Friend friend = new Friend(str[0], Integer.parseInt(str[1]), str[2]);
				friendSet.add(friend);
			}

			Pattern pattern3 = Pattern.compile(args[1]); // 构造正则表达式
			Matcher mentioned3 = pattern3.matcher(newFlieStr); // 匹配SocialTie
			while (mentioned3.find()) {
				String[] str = mentioned3.group(2).split(",");
				Friend friend1;
				Friend friend2;
				if (getCentralObject().getName().equals(str[0])) {
					friend1 = findFriend(str[1]);
					addCenterTrackObjectsRelation(getCentralObject(), friend1, Float.parseFloat(str[2]));
				}

				else if (getCentralObject().getName().equals(str[1])) {
					// DO NOTHING
				}

				else { // 添加轨道物体的关系
					friend1 = findFriend(str[0]);
					friend2 = findFriend(str[1]);
					addTrackObjectsRelation(friend1, friend2, Float.parseFloat(str[2]));
				}
			}

			bf.close();

			BuildRelation b = new BuildRelation();  //第一次建立轨道系统
			b.buildRelation(this, friendSet);

			for (int i = 0; i < getT2E().size(); i++) {  //删除外层轨道到内层轨道的映射
				ArrayList<Friend> list1 = getT2E().get(i);  
				for (Friend f1 : list1) {  //内层用户
					for (int j = i + 1; j < getT2E().size(); j++) {
						ArrayList<Friend> list2 = getT2E().get(j);
						for(Friend f2 :list2) {  //外层用户
							getE2E().remove(f2, f1);		
						}
					}
				}
			}
			
			getTracks().clear();   //清除轨道，为了重新建造轨道
			getT2E().clear();
			
			BuildRelation b1 = new BuildRelation();   //第二次构建轨道
			b1.buildRelation(this, friendSet);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get a friend from the friendSet by name
	 * 
	 * @param name label of specified friend name
	 * @return friend who owns the name
	 */
	public Friend findFriend(String name) {
		for (Friend f : friendSet) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
}