package ca.ualberta.team2recipefinder;
import java.util.ArrayList;

/**
 * Class based on prof Hindles FModel for FillerCreep
 * 
 * @author cmput-301 team 2
 */
public class Model<V extends View> {
	private ArrayList<V> views;
	
	/**
	 * Default constructor
	 */ 
	public Model() {
		views = new ArrayList<V>();
	}
	
	/**
	 * Adds a new observer
	 * 
	 * @param v the observer
	 */ 
	public void addView(V v) {
		if (!views.contains(v)) {
			views.add(v);
		}
	}
	
	/**
	 * Removes an observer
	 * 
	 * @param v the observer
	 */ 
	public void removeView(V v) {
		views.remove(v);
	}
	
	/**
	 * Called by the model, notifies view that something has
	 * been changed
	 */ 
	protected void notifyViews() {
		for (V view: views) {
			view.update(this);
		}
	}
}
