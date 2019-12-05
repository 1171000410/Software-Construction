package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import centralobject.Stellar;
import circularorbit.CircularOrbit;
import physicalobject.Planet;

public class StellarSystemFileChannel implements WriteStrategy {
  @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		FileOutputStream fos = null;
		File file = new File(fileName);
		try {
			fos = new FileOutputStream(file);
			FileChannel fc = fos.getChannel();

			// 创建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(0x300000);	  //3M

			Stellar n = (Stellar) c.getCentralObject();

			String a;
			String b;
			a = "Stellar ::= <"+n.getName()+","+n.getStellarRadius()
			+","+n.getStellarQuality()+">\n";
			buf.put(a.getBytes());
			
			ArrayList<ArrayList<Planet>> t2e = c.getT2E();
			int tn = t2e.size();
			for(int i = 0 ; i< tn  ; i++) {
				Planet p = t2e.get(i).get(0);
				b = "Planet ::= <"+p.getName() + "," + p.getState() +","
						+ p.getColar() +"," +p.getPlanetRadius() +"," 
						+ p.getTrackRadius() + ","+ p.getSpeed() + ","
						+p.getOrientation() + "," +p.getSitha() + ">\n";
				if(buf.remaining() < 1024) {
				  buf.flip();
				  fc.write(buf);
				  buf.clear();
				}
				
				buf.put(b.getBytes());
			
				
			}

			// 反转缓冲区(limit设置为position,position设置为0,mark设置为-1)
			buf.flip();

			// 将缓冲区中的数据写入到通道
			fc.write(buf);

			// 写完将缓冲区还原(position设置为0,limit设置为capacity,mark设置为-1)
			buf.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
