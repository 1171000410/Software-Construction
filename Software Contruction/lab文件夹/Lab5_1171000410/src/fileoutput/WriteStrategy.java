package fileoutput;

import circularorbit.CircularOrbit;

public interface WriteStrategy {
	
	@SuppressWarnings("rawtypes")
	public void writeFile(String fileName,CircularOrbit c);

}
