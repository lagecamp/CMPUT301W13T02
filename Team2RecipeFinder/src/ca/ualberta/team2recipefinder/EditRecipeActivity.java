package ca.ualberta.team2recipefinder;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditRecipeActivity extends Activity implements ca.ualberta.team2recipefinder.View<Recipe> {

	EditText nameEdit;
	EditText procedureEdit;
	Recipe currentRecipe = new Recipe();
	long recipeID = -1;
	ListView ingredientList;
	
	Ingredient oldIngredient;
	
	private final int ADD_INGR_CODE = 0;
	private final int EDIT_INGR_CODE = 1;
	
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
		});
		
		Button addIngredient = (Button) findViewById(R.id.button_add_ingredient);
		addIngredient.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
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
	
    @Override
    public void onDestroy() {
        super.onDestroy();
        currentRecipe.removeView(this);
    }
	
	@Override
	public void update(Recipe model) {		
		nameEdit.setText(currentRecipe.getName());
		procedureEdit.setText(currentRecipe.getProcedure());
		
		List<Ingredient> ingredients = currentRecipe.getIngredients();
		final ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(this, R.layout.list_item, ingredients);
		ingredientList.setAdapter(adapter);
	}
	
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == ADD_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
            	RecipeFinderApplication.getController().addIngredient(currentRecipe, ingredient);
            }
        }
        else if (requestCode == EDIT_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	if (data.getStringExtra("deleted") != null) {
            		RecipeFinderApplication.getController().deleteIngredient(currentRecipe, oldIngredient);
            	}
            	else {
	            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
	            	RecipeFinderApplication.getController().replaceIngredient(currentRecipe, oldIngredient, ingredient);
            	}
            }
        }
	}
    


}
