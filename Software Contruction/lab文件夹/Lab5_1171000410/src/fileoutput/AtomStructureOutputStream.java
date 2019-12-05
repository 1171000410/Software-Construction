package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import centralobject.Nucleus;
import circularorbit.CircularOrbit;
import physicalobject.Electron;

public class AtomStructureOutputStream implements WriteStrategy {
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {
			File file = new File(fileName);
			FileOutputStream out = new FileOutputStream(file);

			Nucleus n = (Nucleus) c.getCentralObject();

			int trackNum = c.getTracks().size();
			String a = "ElementName ::= " + n.getName() + "\n";
			String b = "NumberOfTracks ::= " + trackNum + "\n";
			String d = "NumberOfElectron ::= ";
			
			out.write(a.getBytes());
			out.write(b.getBytes());
			out.write(d.getBytes());

			ArrayList<ArrayList<Electron>> t2e = c.getT2E();
			for(int i = 0 ;i < trackNum ; i++) {
				a = (i+1)+"/"+t2e.get(i).size()+";";
				out.write(a.getBytes());				
			}
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}
