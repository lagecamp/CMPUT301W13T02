package ca.ualberta.team2recipefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SearchActivity extends Activity {
	
	CheckBox cbxSearchLocally;
	CheckBox cbxSearchFromWeb;
	CheckBox cbxIngredientsKitchen;
	
	Button btnOk;
	
	TextView txtKeywords;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        btnOk = (Button) findViewById(R.id.btnOk);

        cbxSearchFromWeb = (CheckBox) findViewById(R.id.cbxSearchFromWeb);
        cbxSearchLocally = (CheckBox) findViewById(R.id.cbxSearchLocally);
        cbxIngredientsKitchen = (CheckBox) findViewById(R.id.cbxIngredientsKitchen);
        
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
        			// use ingredients from kitchen
        			if (cbxIngredientsKitchen.isChecked()) {
        				RecipeFinderApplication.getController().
        					searchWithIngredients(keywords, 
        										  cbxSearchLocally.isChecked(),
        										  cbxSearchFromWeb.isChecked());
        			}
        			// do the search without considering the ingredients
        			else {
        				RecipeFinderApplication.getController().
    						search(keywords, cbxSearchLocally.isChecked(),
    							   cbxSearchFromWeb.isChecked());
        			}
        		}
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
    		checkBox.setChecked(true);
    	}
    }
    
}
