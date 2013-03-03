package ca.ualberta.team2recipefinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class EditRecipeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);
		
		int recipeIndex;
		Recipe currentRecipe = new Recipe();
		Controller c = RecipeFinderApplication.getController();
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeIndex = extras.getInt("recipeIndex");
				currentRecipe = c.getRecipe(recipeIndex);
			}
		}
		
		EditText nameEdit = (EditText) findViewById(R.id.edit_name);
		nameEdit.setText(currentRecipe.getName());
		
		
	}


}
