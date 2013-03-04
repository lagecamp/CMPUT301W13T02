package ca.ualberta.team2recipefinder;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SearchActivity extends Activity {
	
	CheckBox cbxSearchLocally;
	CheckBox cbxSearchFromWeb;
	CheckBox cbxIngredientsKitchen;
	
	Button btnOk;
	
	TextView txtKeywords;
	
	ListView listResults;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        listResults = (ListView) findViewById(R.id.listResults);
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
        			Toast.makeText(SearchActivity.this, getString(R.string.no_keyword), 
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
        
        listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Recipe recipe = (Recipe)listResults.getItemAtPosition(position);
                
				Intent intent = new Intent(SearchActivity.this, ViewRecipeActivity.class);
				intent.putExtra("recipeID", recipe.getRecipeID());
				startActivity(intent);    
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return true;
    }
    
    // make sure that at least one check box (cbxSearchLocally or cbxSearchFromWeb) is checked
    private void validateCheckBoxes(CheckBox checkBox) {
    	if (!cbxSearchFromWeb.isChecked() && !cbxSearchLocally.isChecked()) {
    		Toast.makeText(SearchActivity.this, getString(R.string.no_source_search), 
					   Toast.LENGTH_LONG).show();
    		
    		checkBox.setChecked(true);
    	}
    }
    
    private void displayResults(List<Recipe> results) {						
		// uses an ArrayAdapter and displays the items
		ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this,
				  android.R.layout.select_dialog_multichoice, android.R.id.text1, results);
		listResults.setAdapter(adapter);
    }
    
}
