package circularOrbit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import relation.EE_IntimacyMap;
import relation.LE_IntimacyMap;
import track.Track;

public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

	private L centralObj;   //中心物体
	private List<Track> tracks = new ArrayList<Track>();  //轨道
	private ArrayList<ArrayList<E>> T2E = new ArrayList<ArrayList<E>>(); // 物体所在轨道
	private LE_IntimacyMap<L, E> L2E = new LE_IntimacyMap<L, E>(); // 中心映射轨道物体
	private EE_IntimacyMap<E> E2E = new EE_IntimacyMap<E>(); // 轨道物体间的映射

	// Abstraction function:
    //   represents a multi-track system where objects are in orbit
	//   central objects and orbital objects are mapped
	//   and there is a mapping between orbital objects.
    // Representation invariant:
    //   centralObj is the central object of the system if it isn't null
    //   tracks is the list of tracks
	//   E2E is mapping between orbital objects
	//   T2E is track and object mapping
	//   L2E is central object and orbit objects mapping
    // Safety from rep exposure:
    //   All fields are private and final
    //   tracks , T2E , L2E , E2E are mutable types, so operations use defensive copies and
    //   immutable wrappers to avoid sharing the rep's objects to clients
	
	@Override
	public L getCentralObject() {
		return centralObj;
	}

	@Override
	public List<Track> getTracks() {
		return tracks;
	}

	@Override
	public Track findTrack(double a) {
		for(Track t : tracks) {
			if(t.getA() == a) {
				return t;
			}
		}
		return null;
	}

	@Override
	public String getSystemName() {
		return "ConcreteCircularOrbit";
	}

	@Override
	public LE_IntimacyMap<L, E> getL2E() {
		return L2E;
	}

	@Override
	public EE_IntimacyMap<E> getE2E() {
		return E2E;
	}

	@Override
	public ArrayList<ArrayList<E>> getT2E() {
		return T2E;
	}

	@Override
	public void addTrack(Track track) {
		tracks.add(track);

		ArrayList<E> objs = new ArrayList<E>(); // 添加轨道物体
		T2E.add(objs);
	}

	@Override
	public void removeTrack(Track t) {
		int i = 0;
		for (i = 0; i < tracks.size(); i++) { // 获得track的序号
			if (tracks.get(i).equals(t)) {
				tracks.remove(t);
				break;
			}
		}

//		if (i == tracks.size()) {
//			System.out.println("该轨道不存在！");
//			return;
//		}

		ArrayList<E> list = T2E.get(i);

		for (E trackObj : list) {
			L2E.remove(centralObj, trackObj); // 删除中心物体和轨道物体的映射

			for (int j = 0; j < T2E.size(); j++) {
				ArrayList<E> trackObjList = T2E.get(j); // 遍历所有物体
				for (E target : trackObjList) {
					E2E.remove(trackObj, target); // 只要找到边就删除该映射
					E2E.remove(target, trackObj);

				}
			}
		}

		T2E.remove(i); // 删除轨道
	}

	@Override
	public void addCenterObject(L c) {
		centralObj = c;
	}

	@Override
	public void addTrackObject(Track track, E obj) {
		int i = 0;
		for (i = 0; i < tracks.size(); i++) { // 获得track的序号
			if (tracks.get(i).equals(track)) {
				break;
			}
		}

		ArrayList<E> list = T2E.get(i);

		list.add(obj);
	}

	@Override
	public void removeTrackObject(Track track, E obj) {
		int i = 0;
		for (i = 0; i < tracks.size(); i++) { // 获得track的序号
			if (tracks.get(i).equals(track)) {
				break;
			}
		}

		ArrayList<E> list = T2E.get(i);

		if (list.contains(obj)) {
			list.remove(obj);
		} else {
			System.out.println("轨道不存在该物体！");
		}
	}

	@Override
	public void addCenterTrackObjectsRelation(L c, E obj, float intimacy) {
		L2E.put(c, obj, intimacy);

	}

	@Override
	public void addTrackObjectsRelation(E obj1, E obj2, float intimacy) {
		E2E.put(obj1, obj2, intimacy);

	}

	@Override
	public void removeCenterTrackObjectsRelation(L c, E obj) {
		L2E.remove(c, obj);
	}

	@Override
	public void removeTrackObjectsRelation(E obj1, E obj2) {
		E2E.remove(obj1, obj2);
	}
	
	@Override
	public ArrayList<E> getTrackObject(Track track){
		int i = 0;
		for (i = 0; i < tracks.size(); i++) { // 获得track的序号
			if (tracks.get(i).equals(track)) {
				break;
			}
		}

		ArrayList<E> list = T2E.get(i);

		return list;
	}
	
	
	@Override
	public void readFile(File file) {
	}

}
