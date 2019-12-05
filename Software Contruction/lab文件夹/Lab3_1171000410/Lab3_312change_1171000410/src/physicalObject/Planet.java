package physicalObject;

public class Planet extends PhysicalObject{

	private final String state;    //状态
	private final String colar;    //颜色
	private final double planetRadius;    //行星半径
	private final double trackA;   //轨道半长轴
	private final double trackB;   //轨道半短轴
	private final double trackC;   //轨道焦点
	
	private final double speed;    //公转速度
	private final String orientation;   //公转方向
	private final double sitha;    //初始角度
	
	// Abstraction function:
	//  represents orbital object in the StellarSystem
	// Representation invariant:
	//	state is the planet's state
	//  colar is the planet's color
	//  planetRadius is the planet's radius
	//  trackRadius is the radius of planet's track
	//  speed is the planet's speed
	//  orientation is the planet's orientation
	//  sitha is the planet's sitha
	// Safety from rep exposure:
	//   All fields are private and final	
	
	/**Constructor*/
	public Planet(String name , String state , String colar ,double planetRadius ,
			double a ,double b ,double c , double speed , String orientation , double sitha) {
		super(name);
		this.state = state;
		this.colar = colar;
		this.planetRadius = planetRadius;
		this.trackA = a;
		this.trackB = b;
		this.trackC = c;
		
		this.speed = speed;
		this.orientation = orientation;
		this.sitha = sitha;	
	}

	/**Get string label of a planet's state*/
	public String getState() {
		return state;
	}
	
	/**Get string label of a planet's color*/
	public String getColar() {
		return colar;
	}
	
	/**Get double label of a planet's planetRadius*/
	public double getPlanetRadius() {
		return planetRadius;
	}
	
	/**Get double label of a planet's trackRadius*/
	public double getTrackA() {
		return trackA;
	}
	
	/**Get double label of a planet's trackRadius*/
	public double getTrackB() {
		return trackB;
	}
	
	/**Get double label of a planet's trackRadius*/
	public double getTrackC() {
		return trackC;
	}
	
	/**Get double label of a planet's speed*/
	public double getSpeed() {
		return speed;
	}
	
	/**Get string label of a planet's orientation*/
	public String getOrientation() {
		return orientation;
	}
	
	/**Get double label of a planet's sitha*/
	public double getSitha() {
		return sitha;
	}	
}
