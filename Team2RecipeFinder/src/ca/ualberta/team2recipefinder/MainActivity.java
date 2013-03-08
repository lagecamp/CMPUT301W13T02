/* MainAcivity
 * 
 * Last Edited: March 7, 2013
 * 
 * 
 */

package ca.ualberta.team2recipefinder;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity is the main activity for the recipefinder application. It
 * displays the users current saved recipes and provides an interface to reach other
 * areas of the application.
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.Recipe
 */
public class MainActivity extends Activity implements ca.ualberta.team2recipefinder.View<RecipeModel> {

	ListView recipes;
		
	CheckBox cbxSearchLocally;
	CheckBox cbxSearchFromWeb;
	CheckBox cbxIngredientsKitchen;
	
	Button btnOk;
	
	TextView txtKeywords;
	
	SlidingDrawer sldSearch;
	
	@Override
	/**
	 * Sets up all listeners for this activity.
	 * 
	 * @param	savedInstanceState Bundle containing the activity's previously frozen state, if there was one. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sldSearch = (SlidingDrawer) findViewById(R.id.sldSearch);
				
		recipes = (ListView) findViewById(R.id.recipeList);
        Controller c = RecipeFinderApplication.getController();
		/* set up all button listeners */
		
		Button addButton = (Button) findViewById(R.id.button_main_add);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
				startActivity(intent);
			}		
		});
		
		Button showAllButton = (Button) findViewById(R.id.button_main_show_all);
		showAllButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Controller c = RecipeFinderApplication.getController();
				update(c.getModel());
			}		
		});
		
		Button myKitchenButton = (Button) findViewById(R.id.button_main_mykitchen);
		myKitchenButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, MyKitchenActivity.class);
				startActivity(intent);
			}		
		});
		
		
		update(c.getModel());
	
		
		recipes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
				Recipe r = (Recipe) recipes.getItemAtPosition(position);
				intent.putExtra("recipeID", r.getRecipeID());
				startActivity(intent);
			}
		});
		
        btnOk = (Button) findViewById(R.id.btnOk);

        cbxSearchFromWeb = (CheckBox) findViewById(R.id.cbxSearchFromWeb);
        cbxSearchLocally = (CheckBox) findViewById(R.id.cbxSearchLocally);
        cbxIngredientsKitchen = (CheckBox) findViewById(R.id.cbxIngredientsKitchen);
        
        txtKeywords = (TextView) findViewById(R.id.txtKeywords);
        
        // put controls in a valid state ==> at least one check box is checked
        cbxSearchLocally.setChecked(true);
        
        // set event listener for cbxSearchFromWeb
        cbxSearchFromWeb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	validateCheckBoxes(cbxSearchFromWeb);
            }
        });
        
        // set event listener for cbxSearchLocally
        cbxSearchLocally.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	validateCheckBoxes(cbxSearchLocally);
            }
        });
        
        // set event listener for OK button
        btnOk.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		String text = txtKeywords.getText().toString();
        		String[] keywords = text.split(" ");
        		
        		// error ==> user didn't insert any keyword
        		if (text.length() == 0) {
        			Toast.makeText(MainActivity.this, getString(R.string.no_keyword), 
								   Toast.LENGTH_LONG).show();
        		}
        		// input ok
        		else {
        			List<Recipe> results;
        			
        			// use ingredients from kitchen
        			if (cbxIngredientsKitchen.isChecked()) {
        				results = RecipeFinderApplication.getController().
        					searchWithIngredients(keywords, 
        										  cbxSearchLocally.isChecked(),
        										  cbxSearchFromWeb.isChecked());
        			}
        			// do the search without considering the ingredients
        			else {
        				results = RecipeFinderApplication.getController().
    						search(keywords, cbxSearchLocally.isChecked(),
    							   cbxSearchFromWeb.isChecked());
        			}
        			
        			displayResults(results);
        		}
        	}
        });
        
        c.getModel().addView(this);
	}

	@Override
	/**
	 * Called by the model, updates the view
	 * 
	 * @param	the model
	 */
	public void update(RecipeModel model) {
		ListView recipes = (ListView) findViewById(R.id.recipeList);
		Controller c = RecipeFinderApplication.getController();
		List<Recipe> recipeList = c.getRecipes();
		final ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this, R.layout.list_item, recipeList);
		recipes.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	
	 @Override
	/**
	 * Display the search options when the user clicks
	 * the magnifying glass button on the phone
	 */
	public boolean onSearchRequested() {
		// show the search if the user presses the "search" button of the phone
		sldSearch.animateOpen();
		 
	    return false;  // don't go ahead and show the search box
	}
	
	
    // make sure that at least one check box (cbxSearchLocally or cbxSearchFromWeb) is checked
    private void validateCheckBoxes(CheckBox checkBox) {
    	if (!cbxSearchFromWeb.isChecked() && !cbxSearchLocally.isChecked()) {
    		Toast.makeText(MainActivity.this, getString(R.string.no_source_search), 
					   Toast.LENGTH_LONG).show();
    		
    		checkBox.setChecked(true);
    	}
    }
    
    private void displayResults(List<Recipe> results) {						
		// uses an ArrayAdapter and displays the items
		ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this,
				  R.layout.list_item, results);
		recipes.setAdapter(adapter);
    }
    
    @Override
	/**
	 * Makes sure that the memory will be managed correctly
	 */
    public void onDestroy() {
        super.onDestroy();
        Controller c = RecipeFinderApplication.getController();
        c.getModel().removeView(this);
    }
	
}
