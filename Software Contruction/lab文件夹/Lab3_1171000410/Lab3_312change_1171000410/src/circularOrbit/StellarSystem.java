package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import centralObject.Stellar;
import physicalObject.Planet;
import track.Track;

public class StellarSystem extends WithPosition<Stellar, Planet> implements CircularOrbit<Stellar, Planet> {

	// Abstraction function:
	// represents StellarSystem inherited from WithPosition<Stellar, Planet>
	// Central object is Stellar and the orbital object is Planet

	/** Constructor */
	public StellarSystem() {
	}

	/** Get name of StellarSystem */
	@Override
	public String getSystemName() {
		return "StellarSystem";
	}

	/** Read file and create StellarSystem */
	@Override
	public void readFile(File file) {
		try {
			InputStreamReader readin = new InputStreamReader(new FileInputStream(file));
			BufferedReader bf = new BufferedReader(readin);
			String line;

			String p = "(\\w+) ::= <(.+)>";

			while ((line = bf.readLine()) != null) {
				Pattern pattern = Pattern.compile(p); // 构造正则表达式
				Matcher mentioned = pattern.matcher(line); // 匹配正则表达式

				if (mentioned.find()) {

					String[] args = mentioned.group(2).split("[\\s+ ,]");
					if (args.length == 3) {
						Stellar stellar = new Stellar(args[0], translateData(args[1]), translateData(args[2]));
						addCenterObject(stellar);

					} else {

						Planet planet = new Planet(args[0], args[1], args[2], translateData(args[3]),
								translateData(args[4]), translateData(args[5]),translateData(args[6]),translateData(args[7]), args[8], Double.parseDouble(args[9]));
						setObjectSitha(planet, Double.parseDouble(args[9]));

						Track track = new Track(translateData(args[4]),translateData(args[5]),translateData(args[6]));
						addTrack(track);
						addTrackObject(track, planet);
					}
				}
			}
			bf.close();

			//对轨道长半轴进行排序
			for (int i = 0; i < getTracks().size(); i++) {

				for (int j = i + 1; j < getTracks().size(); j++) {
					if (getTracks().get(i).getA() > getTracks().get(j).getA()) {
						Track e = getTracks().get(i);   //中间变量 ，交换轨道
						getTracks().set(i, getTracks().get(j));
						getTracks().set(j, e);
						
						
						ArrayList<Planet> list = getT2E().get(i); //中间变量，交换轨道物体 
						getT2E().set(i, getT2E().get(j));
						getT2E().set(j, list);					
					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Translate a data from string type to double Numbers greater than 10,000
	 * according to scientific notation and Numbers less than 10000 are given
	 * directly
	 * 
	 * @param s label of the number string
	 * @return double type of the number
	 */
	public double translateData(String s) {
		String[] str = s.split("e");
		if (str.length == 1) {
			return Double.parseDouble(s);
		} else {
			double a = Double.parseDouble(str[0]);
			int b = Integer.parseInt(str[1]);

			double ret = a * Math.pow(10, b);
			return ret;
		}
	}

}
