package fileinput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AllBufferedReader implements ReadStrategy {

	@Override
	public String readFile(File file) {
		StringBuffer buffer = new StringBuffer();
		try {
			long startTime = System.currentTimeMillis();
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = bf.readLine()) != null) { // 使用readLine方法，一次读一行
				buffer.append(s.trim()+"\n");
			}
			bf.close(); //结束文件读取	
			long finishTime = System.currentTimeMillis();
            long totalTime = finishTime - startTime;
            System.out.println("BufferReader:" + totalTime + "ms");

		} catch (IOException e) {
			System.out.println("文件读取错误!");
		}
		
		String fileStr = buffer.toString();
		return fileStr;
	}

}
