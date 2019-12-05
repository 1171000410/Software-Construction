package fileoutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import centralobject.Stellar;
import circularorbit.CircularOrbit;
import physicalobject.Planet;

public class StellarSystemBufferWriter implements WriteStrategy {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			Stellar n = (Stellar) c.getCentralObject();

			writer.write("Stellar ::= <"+n.getName()+","+n.getStellarRadius()
			+","+n.getStellarQuality()+">\n");
			
			ArrayList<ArrayList<Planet>> t2e = c.getT2E();
			int tn = t2e.size();
			for(int i = 0 ; i< tn  ; i++) {
				Planet p = t2e.get(i).get(0);
				writer.write("Planet ::= <"+p.getName() + "," + p.getState() +","
				+ p.getColar() +"," +p.getPlanetRadius() +"," 
				+ p.getTrackRadius() + ","+ p.getSpeed() + ","
				+p.getOrientation() + "," +p.getSitha() + ">\n");
				
			}

			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
