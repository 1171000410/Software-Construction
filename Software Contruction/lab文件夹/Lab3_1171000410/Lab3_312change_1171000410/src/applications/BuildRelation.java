package applications;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Set;

import centralObject.CentralUser;
import circularOrbit.CircularOrbit;
import physicalObject.Friend;
import relation.EE_IntimacyMap;
import relation.LE_IntimacyMap;
import track.Track;

public class BuildRelation {
	
	/**
	 * Build relationship of a SocialNetworkCircle ,
	 * add tracks according to the relation in the system ,
	 * to increase reusability
	 * @param c label of SocialNetworkCircle
	 * @param friendSet set of all friends
	 */
	public void buildRelation(CircularOrbit<CentralUser, Friend> c ,Set<Friend> friendSet) {
	
		final LE_IntimacyMap<CentralUser, Friend> L2E = c.getL2E();
		Track track0 = new Track(1,1,1);
		c.addTrack(track0);

		ArrayList<Friend> visited = new ArrayList<Friend>(); // 访问过的朋友集合

		Set<Friend> firstTrackFriends = L2E.getValues();

		for (Friend f : firstTrackFriends) { // 第一轨道的朋友
			c.addTrackObject(track0, f);
			visited.add(f);
		}

		while (visited.size() < friendSet.size()) { // 全部添加后循环终止
			ListIterator<Friend> list = visited.listIterator(); // 为了遍历时添加
			Track track = new Track(1,1,1);
			c.addTrack(track);
			while (list.hasNext()) {
				Friend f1 = (Friend) list.next();
				for (Friend f2 : friendSet) {
					if (!visited.contains(f2) && getDistance(f1, f2,c) == 1) {
						list.add(f2);
						c.addTrackObject(track, f2);
					}
				}

			}
			int i;
			for (i = 0; i < c.getTracks().size(); i++) {   //获得track索引
				if (c.getTracks().get(i).equals(track)) {
					break;
				}
			}
			if (c.getTrackObject(track).size() == 0) {  //空轨道直接删除，后面不读了
				c.getTracks().remove(track);
				c.getT2E().remove(i);
				break;
			}
		}
		
		
	}
	
	/**
	 * Get distance between two friend
	 * in order to determine track relationship
	 * @param e1 label of one friend
	 * @param e2 label of another friend
	 * @return logical distance of two person
	 */
	public int getDistance(Friend e1, Friend e2 ,CircularOrbit<CentralUser, Friend> c) {

		final EE_IntimacyMap<Friend> E2E = c.getE2E();

		Friend now = e1, friend = e1;
		int i = 0, distance = 0;
		Queue<Friend> queue = new LinkedList<Friend>();
		ArrayList<Friend> visited = new ArrayList<Friend>();
		if (e1 == e2) {
			return distance;
		}
		queue.add(now);
		visited.add(now);
		while (!queue.isEmpty()) {
			now = queue.poll();
			distance++;
			while (i < E2E.getAdjacentObjects(now).size()) {
				friend = E2E.getAdjacentObjects(now).get(i);
				if (friend == e2) { // 找到了即返回当前的距离
					return distance;
				}
				if (!visited.contains(friend)) {
					queue.add(friend);
					visited.add(friend);
				}
				i++;
			}
			i = 0;
		}
		return -1; // 找不到说明e1和e2没关系

	}
}
