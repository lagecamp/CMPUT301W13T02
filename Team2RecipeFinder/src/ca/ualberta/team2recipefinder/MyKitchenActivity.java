/* MyKithcenActivity
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MyKitchenActivity is an android activity for managing
 * the ingredients the user has
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.Ingredient
 */
public class MyKitchenActivity extends Activity implements ca.ualberta.team2recipefinder.View<MyKitchen> {

	ListView listResults;
	
	TextView txtKeywords;
	
	SlidingDrawer sldSearch;
	
	Ingredient oldIngredient;
	
	private final int ADD_INGR_CODE = 0;
	private final int EDIT_INGR_CODE = 1;
			
	/**
	 * Sets up all listeners for this activity.
	 * 
	 * @param	savedInstanceState Bundle containing the activity's previously frozen state, if there was one. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kitchen);
		
		sldSearch = (SlidingDrawer) findViewById(R.id.sldSearch);
		
		listResults = (ListView) findViewById(R.id.listResults);
		txtKeywords = (TextView) findViewById(R.id.txtKeywords);
 		
		// sets up the event for when the user wants to add an ingredient
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View arg0) {
				Intent intent = new Intent(MyKitchenActivity.this, AddEditIngredientActivity.class);
				intent.putExtra("mode", "add");
				startActivityForResult(intent, ADD_INGR_CODE);
			}			
		});
		
		// sets up the event for when the user wants to show all records
		Button btnShowAll = (Button) findViewById(R.id.btnShowAll);
		btnShowAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View arg0) {
				update(RecipeFinderApplication.getMyKitchen());
			}			
		});
		
		// sets up the event for when the user wants to search for something
		Button btnOk = (Button) findViewById(R.id.btnOk);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View arg0) {
        		String text = txtKeywords.getText().toString();
        		String[] keywords = text.split(" ");
        		
        		// error ==> user didn't insert any keyword
        		if (text.length() == 0) {
        			Toast.makeText(MyKitchenActivity.this, getString(R.string.no_keyword), 
								   Toast.LENGTH_LONG).show();
        		}
        		// input ok
        		else {
        			displayResults(RecipeFinderApplication.getController().searchIngredient(keywords));
        		}
			}
		});
			
        listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Ingredient ingredient = (Ingredient)listResults.getItemAtPosition(position);
                
                oldIngredient = ingredient;
                
				Intent intent = new Intent(MyKitchenActivity.this, AddEditIngredientActivity.class);
				intent.putExtra("mode", "edit");
				intent.putExtra("type", ingredient.getType());
				intent.putExtra("amount", ingredient.getAmount().toString());
				intent.putExtra("unit", ingredient.getUnit());
				startActivityForResult(intent, EDIT_INGR_CODE);
            }
        });
        
		RecipeFinderApplication.getMyKitchen().addView(this);
		this.update(RecipeFinderApplication.getMyKitchen());
	}
		    
	/**
	 * Makes sure that the memory will be managed correctly
	 */
	@Override
    public void onDestroy() {
        super.onDestroy();
        RecipeFinderApplication.getMyKitchen().removeView(this);
    }
    
	
	/**
	 * Display the search options when the user clicks
	 * the magnifying glass button on the phone
	 */
    @Override
	public boolean onSearchRequested() {
    	// show the search if the user presses the "search" button of the phone
		sldSearch.animateOpen();
		 
	    return false;  // don't go ahead and show the search box
	}	
	
	/**
	 * Called by the model, updates the view
	 * 
	 * @param	model the model
	 */
    @Override
	public void update(MyKitchen model) {
		List<Ingredient> ingredients = RecipeFinderApplication.getController().getIngredients();
		this.displayResults(ingredients);
	}
	
	private void displayResults(List<Ingredient> ingredients) {
		// uses an ArrayAdapter and displays the items
		ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(this,
				  R.layout.list_item, ingredients);
		listResults.setAdapter(adapter); 
	}
	
	/**
	 * Takes the result from AddEditIngredientActivity and adds it to, or 
	 * replaces an item from MyKitchen
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == ADD_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	// add new ingredient
            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
            	
            	try {
					RecipeFinderApplication.getController().addIngredient(ingredient);
				} catch (DuplicateIngredientException e) {
					Toast.makeText(MyKitchenActivity.this, getString(R.string.have_ingredient_already_kitchen), 
							   Toast.LENGTH_LONG).show();
				}
            }
        }
        else if (requestCode == EDIT_INGR_CODE) {
            if (resultCode == RESULT_OK) {
            	if (data.getStringExtra("deleted") != null) {
            		// deletes ingredient
            		RecipeFinderApplication.getController().deleteIngredient(oldIngredient);
            	}
            	else {
            		// edit ingredient
	            	Ingredient ingredient = (Ingredient) data.getSerializableExtra("result");
	            	RecipeFinderApplication.getController().replaceIngredient(oldIngredient, ingredient);
            	}
            }
        }
	}
}
