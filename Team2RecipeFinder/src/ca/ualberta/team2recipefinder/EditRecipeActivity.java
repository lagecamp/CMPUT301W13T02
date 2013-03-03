package ca.ualberta.team2recipefinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditRecipeActivity extends Activity {

	EditText nameEdit;
	EditText procedureEdit;
	Recipe currentRecipe = new Recipe();
	int recipeIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);
		
		Controller c = RecipeFinderApplication.getController();
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeIndex = extras.getInt("recipeIndex");
				currentRecipe = c.getRecipe(recipeIndex);
			}
		}
		
		nameEdit = (EditText) findViewById(R.id.edit_name);
		nameEdit.setText(currentRecipe.getName());
		
		procedureEdit = (EditText) findViewById(R.id.edit_procedure);
		procedureEdit.setText(currentRecipe.getProcedure());
		
		Button doneButton = (Button) findViewById(R.id.button_done);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				currentRecipe.setName(nameEdit.getText().toString());
				currentRecipe.setProcedure(procedureEdit.getText().toString());
				
				Controller c = RecipeFinderApplication.getController();
				if (recipeIndex == -1) {
					c.addRecipe(currentRecipe);
				}
				else {
					c.replaceRecipe(currentRecipe, recipeIndex);
				}
			}		
		});
	}


}
