package ca.ualberta.team2recipefinder;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.R.id;
import ca.ualberta.team2recipefinder.R.layout;
import ca.ualberta.team2recipefinder.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewRecipeActivity extends Activity {

	int recipeIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_recipe);
		
		Recipe currentRecipe = new Recipe();
		Controller c = RecipeFinderApplication.getController();
		boolean isLocal;
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeIndex = extras.getInt("recipeIndex");
				currentRecipe = c.getRecipe(recipeIndex);
			}
		}
		
		
		if (currentRecipe.getOnServer()) {
			isLocal = false;
		}
		else {
			isLocal = true;
		}
		
		Button publishDownloadButton = (Button) findViewById(R.id.publish_download_button);
		if (isLocal) {
			publishDownloadButton.setText("Publish");
		}
		else {
			publishDownloadButton.setText("Download");
		}
		
		publishDownloadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//if (isLocal) {
				//	c.publishToServer(currentRecipe);
				//}
				//else {
				//	c.addToLocalList(currentRecipe);
				//}
			}		
		});
		
		Button shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setText("Share");
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/* ADD SHARING METHOD OR ACTIVITY HERE */
			}
		});
		
		Button editButton = (Button) findViewById(R.id.edit_button);
		if (isLocal) {
			editButton.setText("Edit");
		}
		else {
			editButton.setMaxHeight(0);
		}
		
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ViewRecipeActivity.this, EditRecipeActivity.class);
				intent.putExtra("recipeIndex", recipeIndex);
				startActivity(intent);
			}
		});
		
		Button deleteButton = (Button) findViewById(R.id.delete_button);
		if (isLocal) {
			editButton.setText("Delete");
		}
		else {
			editButton.setMaxHeight(0);
		}
		
		deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/* CALL DELETE FROM CONTROLLER, EXIT ACTIVITY */
			}
		});
		
		TextView procedure = (TextView) findViewById(R.id.procedure_text);
		String procedureText = currentRecipe.getProcedure();
		procedure.setText(procedureText);
		
		TextView ingredients = (TextView) findViewById(R.id.ingredients_text);
		String[] ingredientTextArray = currentRecipe.getIngredients();
		
		String ingredientText = new String();
		for (int i = 0; i < ingredientTextArray.length; i++) {
			ingredientText.concat(ingredientTextArray[i]);
		}
		ingredients.setText(ingredientText);
	}

}
