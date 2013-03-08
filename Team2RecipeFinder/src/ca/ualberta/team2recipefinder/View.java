package ca.ualberta.team2recipefinder;

/**
 * Interface based on prof Hindles interface FView for FillerCreep
 * 
 * @author cmput-301 team 2
 */
public interface View<M extends Model>{
	
	/**
	 * Called by the model when something gets updated
	 * 
	 * @param Model the model that called the method
	 */
	public void update(M Model);
}