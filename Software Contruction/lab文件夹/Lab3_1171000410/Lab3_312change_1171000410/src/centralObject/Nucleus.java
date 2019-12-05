package centralObject;

public class Nucleus extends CentralObject{
	
	private int p;   //质子数
	private int n;   //中子数
	
	/**Constructor*/
	public Nucleus(String name ,int p ,int n) {
		super(name);
		this.p = p;
		this.n = n;
	}	
	
	public int getP() {
		return p;
	}
	
	public int getN() {
		return n;
	}
	
}
