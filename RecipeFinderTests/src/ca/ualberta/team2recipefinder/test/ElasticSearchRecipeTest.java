package ca.ualberta.team2recipefinder.test;

import android.graphics.Bitmap;
import ca.ualberta.team2recipefinder.model.ElasticSearchRecipe;
import ca.ualberta.team2recipefinder.model.Recipe;
import junit.framework.TestCase;

public class ElasticSearchRecipeTest extends TestCase {
	
	public void TestToRecipe() {
		Recipe recipe = new Recipe();
		recipe.setName("Testing");
		ElasticSearchRecipe e = new ElasticSearchRecipe(recipe);
		Recipe newRecipe = e.toRecipe("TestingID");
		assertTrue("To Recipe", (newRecipe.getName() == "Testing" && newRecipe.getServerId() == "TestingID"));
	}
	
}