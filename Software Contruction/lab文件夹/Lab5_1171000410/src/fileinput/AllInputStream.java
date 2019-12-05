package fileinput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllInputStream implements ReadStrategy {
	@Override
	public String readFile(File file) {
		FileInputStream in;
		byte[] fileContent = null;
		try {
			long startTime = System.currentTimeMillis();
			in = new FileInputStream(file);
			fileContent = new byte[in.available()];

			in.read(fileContent);
			in.close();
			long finishTime = System.currentTimeMillis();
            long totalTime = finishTime - startTime;
            System.out.println("InputStream:" + totalTime + "ms");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(fileContent);

	}
}
