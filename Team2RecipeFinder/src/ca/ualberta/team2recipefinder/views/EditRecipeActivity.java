/* EditRecipeAcivity
 * 
 * Last Edited: March 7, 2013
 * 
 * 
 */

package ca.ualberta.team2recipefinder.views;

import java.util.List;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.R.id;
import ca.ualberta.team2recipefinder.R.layout;
import ca.ualberta.team2recipefinder.R.string;
import ca.ualberta.team2recipefinder.controller.Controller;
import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.model.DuplicateIngredientException;
import ca.ualberta.team2recipefinder.model.Ingredient;
import ca.ualberta.team2recipefinder.model.Recipe;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * EditRecipeActivity is an android activity for editting a single recipe, 
 * or creating a new one.
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.model.Recipe
 */
public class EditRecipeActivity extends Activity implements ca.ualberta.team2recipefinder.views.View<Recipe> {

	EditText nameEdit;
	EditText procedureEdit;
	Recipe currentRecipe = new Recipe();
	long recipeID = -1;
	ListView ingredientList;
	
	Ingredient oldIngredient;
	
	private final int ADD_INGR_CODE = 0;
	private final int EDIT_INGR_CODE = 1;
	
	/**
	 * Sets up all button listener's for this activity and gets Strings from this
	 * activity's EditText fields.
	 * 
	 * @author cmput-301 team 2
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);
		
		nameEdit = (EditText) findViewById(R.id.edit_name);
		procedureEdit = (EditText) findViewById(R.id.edit_procedure);
		ingredientList = (ListView) findViewById(R.id.ingredient_list);
		Controller c = RecipeFinderApplication.getController();
		
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeID = extras.getLong("recipeID");
				currentRecipe = c.getRecipe(recipeID);
			}
		}
		
		Button doneButton = (Button) findViewById(R.id.button_done);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				boolean goodEntry = true;
				
				if (nameEdit.getText().toString().isEmpty() || procedureEdit.getText().toString().isEmpty()
						|| currentRecipe.getIngredients().isEmpty()) {
					AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());
					adb.setTitle("Error");
					adb.setMessage("A recipe can not have an empty name or procedure, or contain no ingredients.");
					adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					
					AlertDialog ad = adb.create();
					ad.show();
					goodEntry = false;
				}
				
				if (goodEntry) {
				
					String newName = nameEdit.getText().toString();
					String newProcedure = procedureEdit.getText().toString();
				
					currentRecipe.setName(newName);
					currentRecipe.setProcedure(newProcedure);
				
					Controller c = RecipeFinderApplication.getController();
					if (recipeID == -1) {
						c.addRecipe(currentRecipe);
					}
					else {
						c.replaceRecipe(currentRecipe, recipeID);
					}
					
					finish();
				}
			}	
		});
		
		Button addIngredient = (Button) findViewById(R.id.button_add_ingredient);
		addIngredient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String newName = nameEdit.getText().toString();
				String newProcedure = procedureEdit.getText().toString();
			
				currentRecipe.setName(newName);
				currentRecipe.setProcedure(newProcedure);
				
				Intent intent = new Intent(EditRecipeActivity.this, AddEditIngredientActivity.class);
				intent.putExtra("mode", "add");
				startActivityForResult(intent, ADD_INGR_CODE);
			}
		});
		
		ingredientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Ingredient ingredient = (Ingredient)ingredientList.getItemAtPosition(position);
                
                oldIngredient = ingredient;
                
				Intent intent = new Intent(EditRecipeActivity.this, AddEditIngredientActivity.class);
				intent.putExtra("mode", "edit");
				intent.putExtra("type", ingredient.getType());
				intent.putExtra("amount", ingredient.getAmount().toString());
				intent.putExtra("unit", ingredient.getUnit());
				startActivityForResult(intent, EDIT_INGR_CODE);
            }
        });
		
		currentRecipe.addView(this);
		this.update(currentRecipe);
	}
	
	/**
	 * Removes this View from Model.
	 */
    @Override
    public void onDestroy() {
        super.onDestroy();
        currentRecipe.removeView(this);
    }
	
    /**
     * Updates all fields with current information from the recipe object.
     */
	@Override
	public void update(Recipe model) {		
		nameEdit.setText(currentRecipe.getName());
		procedureEdit.setText(currentRecipe.getProcedure());
		
		List<Ingredient> ingredients = currentRecipe.getIngredients();
		final ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(this, R.layout.list_item, ingredients);
		ingredientList.setAdapter(adapter);
	}
	
	/**
	 * Takes the result from AddEditIngredientActivity and adds it to, or 
	 * replaces an item from this recipe's ingredient list.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == ADD_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	// add ingredient
            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
            	
            	try {
            		RecipeFinderApplication.getController().addIngredient(currentRecipe, ingredient);
				} catch (DuplicateIngredientException e) {
					Toast.makeText(EditRecipeActivity.this, getString(R.string.have_ingredient_already_recipe), 
							   Toast.LENGTH_LONG).show();
				}            	
            }
        }
        else if (requestCode == EDIT_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	if (data.getStringExtra("deleted") != null) {
            		// delete ingredient
            		RecipeFinderApplication.getController().deleteIngredient(currentRecipe, oldIngredient);
            	}
            	else {
            		// edit ingredient
	            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
	            	RecipeFinderApplication.getController().replaceIngredient(currentRecipe, oldIngredient, ingredient);
            	}
            }
        }
	}   


}
