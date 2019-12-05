package fileoutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import centralobject.CentralUser;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import physicalobject.Friend;
import relation.EeIntimacyMap;
import relation.LeIntimacyMap;

public class SocialNetworkCircleOutputStream implements WriteStrategy {
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {
			File file = new File(fileName);
			FileOutputStream out = new FileOutputStream(file);

			CentralUser n = (CentralUser) c.getCentralObject();
			String a;
			a = "CentralUser ::= <" + n.getName() + "," + n.getAge() +","
					+n.getGender()+">\n\n";
			out.write(a.getBytes());
			
			LeIntimacyMap<CentralUser, Friend> l2e = c.getL2E();
			EeIntimacyMap<Friend> e2e = c.getE2E();
			
			Set<String> fs = l2e.getValuesAndWeight();	 //–¥»ÎSocialTie
			for(String s : fs) {
				a = "SocialTie ::= <"+s+">"+"\n";
				out.write(a.getBytes());	
			}
			
			Set<String> es = e2e.getEdgesString(); 
			for(String e :es) {
				a = "SocialTie ::= <"+e+">\n";
				out.write(a.getBytes());
			}
			out.write("\n".getBytes());
			
			SocialNetworkCircle s = (SocialNetworkCircle) c;
			Set<Friend> fSet = s.getFriends();
			for(Friend f : fSet) {
				a = "Friend ::= <"+f.getName()+","+f.getFriendAge()+","+f.getFriendGender()+">\n";
				out.write(a.getBytes());
			}
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
