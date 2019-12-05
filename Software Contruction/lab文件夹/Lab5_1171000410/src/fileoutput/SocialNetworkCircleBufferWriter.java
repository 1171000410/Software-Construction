package fileoutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import centralobject.CentralUser;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import physicalobject.Friend;
import relation.EeIntimacyMap;
import relation.LeIntimacyMap;

public class SocialNetworkCircleBufferWriter implements WriteStrategy {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeFile(String fileName, CircularOrbit c) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			CentralUser n = (CentralUser) c.getCentralObject();

			writer.write("CentralUser ::= <" + n.getName() + "," + n.getAge() +","
			+n.getGender()+">\n\n");
			
			LeIntimacyMap<CentralUser, Friend> l2e = c.getL2E();
			EeIntimacyMap<Friend> e2e = c.getE2E();
			
			Set<String> fs = l2e.getValuesAndWeight();	 //–¥»ÎSocialTie
			for(String s : fs) {
				writer.write("SocialTie ::= <"+s+">"+"\n");	
			}
			
			Set<String> es = e2e.getEdgesString(); 
			for(String e :es) {
				writer.write("SocialTie ::= <"+e+">\n");
			}
			writer.write("\n");
			
			SocialNetworkCircle s = (SocialNetworkCircle) c;
			Set<Friend> fSet = s.getFriends();
			for(Friend f : fSet) {
				writer.write("Friend ::= <"+f.getName()+","+f.getFriendAge()+","+f.getFriendGender()+">\n");
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
