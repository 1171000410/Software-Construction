package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Set;

import centralobject.CentralUser;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import physicalobject.Friend;
import relation.EeIntimacyMap;
import relation.LeIntimacyMap;

public class SocialNetworkCircleChannel implements WriteStrategy {
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void writeFile(String fileName, CircularOrbit c) {
	FileOutputStream fos = null;
	File file = new File(fileName);
	try {
	  fos = new FileOutputStream(file);
	  FileChannel fc = fos.getChannel();

	  // 创建缓冲区
	  ByteBuffer buf = ByteBuffer.allocate(0x300000);  //3M

	  CentralUser n = (CentralUser) c.getCentralObject();

	  String a;
	  a = "CentralUser ::= <" + n.getName() + "," + n.getAge() + "," + n.getGender() + ">\n\n";
	  buf.put(a.getBytes());

	  LeIntimacyMap<CentralUser, Friend> l2e = c.getL2E();
	  EeIntimacyMap<Friend> e2e = c.getE2E();

	  Set<String> fs = l2e.getValuesAndWeight(); //写入SocialTie
	  for (String s : fs) {
		a = "SocialTie ::= <" + s + ">" + "\n";
		if(buf.remaining() < 1024) {
		  buf.flip();
		  fc.write(buf);
		  buf.clear();
		}
		buf.put(a.getBytes());
	  }

	  Set<String> es = e2e.getEdgesString();
	  for (String e : es) {
		a = "SocialTie ::= <" + e + ">\n";
		if(buf.remaining() < 1024) {
		  buf.flip();
		  fc.write(buf);
		  buf.clear();
		}
		buf.put(a.getBytes());
	  }
	  buf.put(a.getBytes());

	  SocialNetworkCircle s = (SocialNetworkCircle) c;
	  Set<Friend> fSet = s.getFriends();
	  for (Friend f : fSet) {
		a = "Friend ::= <" + f.getName() + "," + f.getFriendAge() + "," + f.getFriendGender() + ">\n";
		if(buf.remaining() < 1024) {
		  buf.flip();
		  fc.write(buf);
		  buf.clear();
		}
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
