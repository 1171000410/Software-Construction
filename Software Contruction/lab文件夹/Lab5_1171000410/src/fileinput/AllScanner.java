package fileinput;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AllScanner implements ReadStrategy {
	@Override
	public String readFile(File file) {
		Scanner in;
		StringBuffer a = new StringBuffer();
		try {
			long startTime = System.currentTimeMillis();
			in = new Scanner(file);
			while(in.hasNextLine()) {
				a.append(in.nextLine()+"\n");
			}
			long finishTime = System.currentTimeMillis();
            long totalTime = finishTime - startTime;
            System.out.println("Scanner:" + totalTime + "ms");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return a.toString();	
		
	}
}
