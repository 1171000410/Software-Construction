package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import physicalObject.*;
//import centralObject.*;

import track.*;

import centralObject.Nucleus;
import physicalObject.Electron;

public class AtomStructure extends WithoutPosition<Nucleus, Electron> implements CircularOrbit<Nucleus, Electron> {

	// Abstraction function:
    //   represents AtomStructure inherited from WithoutPosition<Nucleus, Electron>
	//  Central object is Nucleus and the orbital object is Electron

	/**Constructor*/
	public AtomStructure() {
	}

	/**Get name of AtomStructure*/
	@Override
	public String getSystemName() {
		return "AtomStructure";
	}

	/**Read file and create AtomStructure*/
	@Override
	public void readFile(File file) {
		try {
			StringBuffer buffer = new StringBuffer();
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = bf.readLine()) != null) { // 使用readLine方法，一次读一行
				buffer.append(s.trim());
			}

			String fileStr = buffer.toString();
			String regex = "\\s+";
			String newFlieStr = fileStr.replaceAll(regex, ""); // 去掉空格

			String[] args = { "(ElementName::)(=\\D*)", 
					"(NumberOfTracks)(\\D*)(\\d+)", 
					"(\\d+)(/)(\\d+)" };

			for (int i = 0; i < args.length; i++) {
				Pattern pattern = Pattern.compile(args[i]); // 构造正则表达式
				Matcher mentioned = pattern.matcher(newFlieStr); // 匹配正则表达式

				boolean just = mentioned.find();   //必须设置一个标志保存第一次find结果

				if (just && i == 0) {
					String match = new String(mentioned.group(2).toString());
					match = match.substring(1);
					String del = "NumberOfTracks::=" ;
					String ii[] = match.split(del);
					match = ii[0];
					Nucleus nucleus = new Nucleus(match);
					addCenterObject(nucleus);
				}

				if (just && i == 1) {
					Integer numOfTracks = new Integer(mentioned.group(3));
					
					for (int j = 0; j < numOfTracks; j++) {
						Track t = new Track(i);
						addTrack(t);
					}
				}

				if (just && i == 2) {
					do {
						Integer trackNumber = new Integer(mentioned.group(1)); // 轨道序号
						Integer numOfElectron = new Integer(mentioned.group(3)); // 轨道电子数

						Track t = getTracks().get(trackNumber - 1);

						for (int k = 0; k < numOfElectron; k++) {
							Electron e = new Electron("electron");
							addTrackObject(t, e);
						}
					} while (mentioned.find());
				}

			}

			bf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
