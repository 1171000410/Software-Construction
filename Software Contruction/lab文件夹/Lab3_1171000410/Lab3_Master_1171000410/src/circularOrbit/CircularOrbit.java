package circularOrbit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import relation.EE_IntimacyMap;
import relation.LE_IntimacyMap;
import track.*;

public interface CircularOrbit <L,E>{
	
	/**
	 * Create an empty CircularOrbit
	 * @return a new empty circular orbit
	 */
	public static <L,E> CircularOrbit<L,E> empty(){
		return new ConcreteCircularOrbit<>();
	}
	
	/**
	 * Get central object of the orbit
	 * @return label of central object
	 */
	public L getCentralObject();
	
	/**
	 * Find track from tracks by the radius 
	 * @param r label of track radius
	 * @return label of target track
	 */
	public Track findTrack(double r);
	
	/**
	 * Get the track list
	 * @return label of track list
	 */
	public List<Track> getTracks(); 
	
	/**
	 * Get the relation of tracks and objects in tracks 
	 * @return label of nested list
	 */
	public ArrayList<ArrayList<E>> getT2E();
	
	/**
	 * Get the relation of central object and track objects
	 * @return label of LE_Intimacy map
	 */
	public LE_IntimacyMap<L, E> getL2E();
	
	/**
	 * Get the relation in track objects
	 * @return label of EE_Intimacy map
	 */
	public EE_IntimacyMap<E> getE2E();
	
	/**
	 * Add one track in the orbit system
	 * @param track label of the one to be added
	 */
	public void addTrack(Track track);
	
	/**
	 * Remove one track in the orbit system
	 * @param t label of the one to be removed
	 */
	public void removeTrack(Track t);
	
	/**
	 * Add central object in the orbit system
	 * @param c label of the one to be added
	 */
	public void addCenterObject(L c);
	
	/**
	 * Add object in the track
	 * @param t label of track
	 * @param obj label of orbiter to be added
	 */
	public void addTrackObject(Track t , E obj);
	
	/**
	 * Remove object in the track
	 * @param track label of track
	 * @param obj label of orbiter to be removed
	 */
	public void removeTrackObject(Track track, E obj);
	
	/**
	 * Add a relation between central object and track object
	 * @param c label of center
	 * @param obj label of track object
	 * @param intimacy label of closeness of relationship
	 */
	public void addCenterTrackObjectsRelation(L c , E obj ,float intimacy);
	
	/**
	 * Add a relation between two objects in the track
	 * @param obj1 label of one object
	 * @param obj2 label of another object
	 * @param intimacy label of closeness of relationship
	 */
	public void addTrackObjectsRelation(E obj1 , E obj2 ,float intimacy);
	
	/**
	 * Remove a relation between central object and track object
	 * @param obj1 label of one object
	 * @param obj2 label of another object
	 */
	public void removeCenterTrackObjectsRelation(L c, E obj);
	
	/**
	 * Remove a relation between two objects in the track
	 * @param obj1 label of one object
	 * @param obj2 label of another object
	 */
	public void removeTrackObjectsRelation(E obj1, E obj2);
	
	/**
	 * Get the name of the whole system
	 * @return label of the system name string
	 */
	public String getSystemName();
	
	/**
	 * Get all objects in a track
	 * @param t label of the target track
	 * @return track object list
	 */
	public ArrayList<E> getTrackObject(Track t);
	
	/**
	 * Read file from the txt
	 * @param file label of file to be read
	 */
	public void readFile(File file);
	
	
}