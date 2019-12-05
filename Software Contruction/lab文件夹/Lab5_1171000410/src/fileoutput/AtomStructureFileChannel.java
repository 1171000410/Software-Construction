package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import centralobject.Nucleus;
import circularorbit.CircularOrbit;
import physicalobject.Electron;

public class AtomStructureFileChannel implements WriteStrategy {
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		FileOutputStream fos = null;
		File file = new File(fileName);
		try {
			fos = new FileOutputStream(file);
			FileChannel fc = fos.getChannel();

			// 创建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);

			Nucleus n = (Nucleus) c.getCentralObject();
			int trackNum = c.getTracks().size();
			String a = "ElementName ::= " + n.getName() + "\n";
			String b = "NumberOfTracks ::= " + trackNum + "\n";
			String d = "NumberOfElectron ::= ";

			// 将数据装入缓冲区
			buf.put(a.getBytes());
			buf.put(b.getBytes());
			buf.put(d.getBytes());

			ArrayList<ArrayList<Electron>> t2e = c.getT2E();
			for (int i = 0; i < trackNum; i++) {
				a = (i + 1) + "/" + t2e.get(i).size() + ";";
				buf.put(a.getBytes());
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
