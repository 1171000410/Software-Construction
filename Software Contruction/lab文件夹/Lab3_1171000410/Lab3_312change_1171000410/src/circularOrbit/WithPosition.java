package circularOrbit;
import java.util.HashMap;

import java.util.Map;

public class WithPosition <L,E> extends ConcreteCircularOrbit<L,E>{
	
	// Abstraction function:
    //   represents system with fixed position of the object
	// Representation invariant:
	//	 objectSitha is mapping between objects and their sitha
	// Safety from rep exposure:
    //   All fields are private and final
	
	private Map<E, Double> objectSitha= new HashMap<E , Double>();  //轨道物体的角度
	
	/**
	 * Get object's sitha
	 * @param object label of a planet
	 * @return offset sitha
	 */
	public Double getSitha(E object) {  
		return objectSitha.get(object);  //获得特定物体的角度
	}
	
	/**
	 * Set the sitha of specified object
	 * actually only for StellarSystem 
	 * @param object label of a planet
	 * @param sitha label of offset position
	 */
	public void setObjectSitha(E object ,Double sitha) {
		objectSitha.put(object, sitha);
	}
	
	/**
	 * Move object from the current position to the position 
	 * corresponding to the new sitha angle
	 * @param object label of current object
	 * @param sitha label of target angle
	 */
	public void move(E object, double sitha) {
		objectSitha.remove(object);
		setObjectSitha(object, sitha);
	}
	
}
