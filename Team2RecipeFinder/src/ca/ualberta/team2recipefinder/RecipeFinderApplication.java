package ca.ualberta.team2recipefinder;

import android.app.Application;
import android.content.Context;

//Application, where the important singletons are (as suggested by professor Abram Hindle)
public class RecipeFinderApplication extends Application {
	// Singleton
	transient private static Controller controller = null;

	public static Controller getController() {
		if (controller == null) {
			controller = new Controller(/*getModel()*/);
		}
		return controller;
    }
}