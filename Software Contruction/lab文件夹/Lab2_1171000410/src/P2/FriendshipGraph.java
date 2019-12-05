package P2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//import P1.graph.*;
import P1.graph.ConcreteEdgesGraph;
import P2.Person;

public class FriendshipGraph extends ConcreteEdgesGraph<Person>{
	public void addVertex(Person p) {
		if (this.vertices().contains(p)) {    //防止点重复
			System.out.println("This name is existed!");
		}
		else {
			this.add(p);
		} 
	}
	
	public void addEdge(Person p1, Person p2) {
		if(!this.targets(p1).containsKey(p2)) { //防止边重复
			this.set(p1, p2, 1);
		}
	}
	
	public int getDistance(Person p1, Person p2) {
		Person now = p1 ;
		int distance = 0;   
		Queue<Person> queue = new LinkedList<Person>();
		ArrayList<Person> visited = new ArrayList<Person>();
		if(p1 == p2) {
			return distance;
		}
		queue.add(now);
		visited.add(now);
		while (!queue.isEmpty()) {
			now = queue.poll();   //取出队首
			distance ++;
			for(Person friend : this.targets(now).keySet()){
				if( friend == p2) {    //找到了即返回当前的距离
					return distance;
				}
				if(!visited.contains(friend)) {
					queue.add(friend);
					visited.add(friend);
				}
			}
		}
		return -1;   //找不到说明P1和P2没关系
	}
	
	public static void main(String[] args) throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
			
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(rachel);
		list.add(ross);
		list.add(ben);		
		list.add(kramer);
		for(int i=0; i <list.size();i++) {
			for(int j=i+1 ; j < list.size();j++) {
				if(list.get(i).nameSameWith(list.get(j).getName())) {
					System.out.println("Wrong name:"+list.get(i).getName());
					throw new Exception("The name is repeated!");
				}
			}
		}
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1
	}

}
