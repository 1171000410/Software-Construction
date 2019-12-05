package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import centralobject.Stellar;
import circularorbit.CircularOrbit;
import physicalobject.Planet;

public class StellarSystemOutputStream implements WriteStrategy {
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {
			File file = new File(fileName);
			FileOutputStream out = new FileOutputStream(file);

			Stellar n = (Stellar) c.getCentralObject();

			String a;
			String b;
			a = "Stellar ::= <"+n.getName()+","+n.getStellarRadius()
			+","+n.getStellarQuality()+">\n";
			out.write(a.getBytes());
			
			ArrayList<ArrayList<Planet>> t2e = c.getT2E();
			int tn = t2e.size();
			for(int i = 0 ; i< tn  ; i++) {
				Planet p = t2e.get(i).get(0);
				b = "Planet ::= <"+p.getName() + "," + p.getState() +","
						+ p.getColar() +"," +p.getPlanetRadius() +"," 
						+ p.getTrackRadius() + ","+ p.getSpeed() + ","
						+p.getOrientation() + "," +p.getSitha() + ">\n";
				out.write(b.getBytes());
				
			}
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
