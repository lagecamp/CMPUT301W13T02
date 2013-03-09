/* ViewRecipeAcivity
 * 
 * Last Edited: March 7, 2013
 * 
 * 
 */

package ca.ualberta.team2recipefinder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * ViewRecipeActivity is an android activity for displaying information about
 * a single recipe.
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.Recipe
 */
public class ViewRecipeActivity extends Activity implements ca.ualberta.team2recipefinder.View<Recipe> {

	long recipeID = -1;
	Recipe currentRecipe = new Recipe();
		
	/**
	 * Sets up all button listeners for this activity.
	 * 
	 * @param	savedInstanceState Bundle containing the activity's previously frozen state, if there was one. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_recipe);
		
		final Controller c = RecipeFinderApplication.getController();
		boolean isLocal;
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeID = extras.getLong("recipeID");
				currentRecipe = c.getRecipe(recipeID);
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
				intent.putExtra("recipeID", recipeID);
				startActivity(intent);
			}
		});
		
		Button deleteButton = (Button) findViewById(R.id.delete_button);
		if (isLocal) {
			deleteButton.setText("Delete");
		}
		else {
			deleteButton.setMaxHeight(0);
		}
		
		deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				/* CALL DELETE FROM CONTROLLER, EXIT ACTIVITY */
				c.deleteRecipe(currentRecipe);				
				finish();
			}
		});
		
		Button rightButton = (Button) findViewById(R.id.button5);
		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				currentRecipe = c.getNextRecipe(recipeID);
				recipeID = currentRecipe.getRecipeID();
				update(currentRecipe);
			}
		});
		
		Button leftButton = (Button) findViewById(R.id.button6);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				currentRecipe = c.getPreviousRecipe(recipeID);
				recipeID = currentRecipe.getRecipeID();
				update(currentRecipe);
			}
		});
		
		update(currentRecipe);
		currentRecipe.addView(this);
	}
	
	/**
	 * Updates the current info being displayed. Use if the recipe is changed.
	 */
	public void update(Recipe model) {
		TextView recipeName = (TextView) findViewById(R.id.recipe_name);
		recipeName.setMovementMethod(new ScrollingMovementMethod());
		recipeName.setText(currentRecipe.getName());
		
		TextView procedure = (TextView) findViewById(R.id.procedure_text);
		procedure.setMovementMethod(new ScrollingMovementMethod());
		String procedureText = currentRecipe.getProcedure();
		procedure.setText(procedureText);
		
		TextView ingredients = (TextView) findViewById(R.id.ingredients_text);
		ingredients.setMovementMethod(new ScrollingMovementMethod());
		List<Ingredient> ingredientTextArray = currentRecipe.getIngredients();
		
		String ingredientText = new String();
		String nl = System.getProperty("line.separator");
		for (int i = 0; i < ingredientTextArray.size(); i++) {
			ingredientText += ingredientTextArray.get(i).toString() + nl;
		}
		ingredients.setText(ingredientText);
	}

	
	/**
	 * Removes this view from the model
	 */
    @Override
    public void onDestroy() {
        super.onDestroy();
        currentRecipe.removeView(this);
    }
}
