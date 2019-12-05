package legality;

import java.util.ArrayList;
import java.util.List;

import centralObject.Stellar;
import circularOrbit.CircularOrbit;
import physicalObject.Planet;
import track.Track;

public class Legal_StellarSystem {

	public int judgeLegality(CircularOrbit<Stellar, Planet> c) {
		int flag = 1 ;
		if(c.getCentralObject() == null) {
			System.out.println("中心点没有恒星");
			flag = 0;
		}
		
		ArrayList<ArrayList<Planet>> T2E = c.getT2E();
		List<Track> tracks = c.getTracks();
		
		for(int i = 0 ;i< T2E .size() ; i++) {
			if(T2E.get(i).size() == 0) {
				System.out.printf("轨道%d没有行星\n", i+1);
				flag = 0;
			}
			if(T2E.get(i).size() >1) {
				System.out.printf("轨道%d多于一个行星\n", i+1);
				flag = 0;
			}
		}
		
		for(int i = 0 ; i <tracks.size() ; i++) {	
			for(int j =i+1 ; j<tracks.size() ; j++) {
				double trackDistance = tracks.get(j).getA() - tracks.get(i).getA();
				double planetRadiusSum = T2E.get(i).get(0).getPlanetRadius() + T2E.get(j).get(0).getPlanetRadius();
				if(trackDistance < planetRadiusSum) {
					System.out.println("相邻轨道的长半轴之差小于两颗相应行星的半径之和！");
					flag = 0;
				}
				
			}
		}		
		
		return flag;
	}
	
}
