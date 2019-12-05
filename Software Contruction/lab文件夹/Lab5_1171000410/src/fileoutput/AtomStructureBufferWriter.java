package fileoutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import centralobject.Nucleus;
import circularorbit.CircularOrbit;
import physicalobject.Electron;

public class AtomStructureBufferWriter implements WriteStrategy {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			Nucleus n = (Nucleus) c.getCentralObject();

			int trackNum = c.getTracks().size();
			writer.write("ElementName ::= " + n.getName() + "\n");
			writer.write("NumberOfTracks ::= " + trackNum + "\n");
			writer.write("NumberOfElectron ::= ");

			ArrayList<ArrayList<Electron>> t2e = c.getT2E();
			for(int i = 0 ;i < trackNum ; i++) {
				writer.write((i+1)+"/"+t2e.get(i).size()+";");				
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
