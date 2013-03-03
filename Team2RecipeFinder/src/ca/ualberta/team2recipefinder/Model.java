package ca.ualberta.team2recipefinder;


import java.util.ArrayList;


public class Model<V extends View>{
	private ArrayList<V> views;
	
	public Model(){
		views = new ArrayList<V>();
	}
	
	public void addView(V v){
		if(!views.contains(v))
			views.add(v);
	}
	
	public void removeView(V v){
		views.remove(v);
	}
	
	public void notifyViews(){
		for(V view: views)
			view.update(this);	
	}
}
