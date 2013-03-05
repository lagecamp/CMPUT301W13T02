package ca.ualberta.team2recipefinder;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyKitchenActivity extends Activity implements ca.ualberta.team2recipefinder.View<IngredientList> {

	ListView listResults;
	
	TextView txtKeywords;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kitchen);
		
		listResults = (ListView) findViewById(R.id.listResults);
		txtKeywords = (TextView) findViewById(R.id.txtKeywords);
 		
		// sets up the event for when the user wants to add an ingredient
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View arg0) {
				Intent intent = new Intent(MyKitchenActivity.this, AddEditIngredientActivity.class);
                startActivity(intent);
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
        		
        		// error ==> user didn't insert any keyword
        		if (text.length() == 0) {
        			Toast.makeText(MyKitchenActivity.this, getString(R.string.no_keyword), 
								   Toast.LENGTH_LONG).show();
        		}
        		// input ok
        		else {
        			displayResults(RecipeFinderApplication.getController().searchIngredient(text));
        		}
			}
		});
			
        listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Ingredient ingredient = (Ingredient)listResults.getItemAtPosition(position);
                
				Intent intent = new Intent(MyKitchenActivity.this, AddEditIngredientActivity.class);
				intent.putExtra("ingredientType", ingredient.getType());
				startActivity(intent);
            }
        });
        
		RecipeFinderApplication.getMyKitchen().addView(this);
		this.update(RecipeFinderApplication.getMyKitchen());
	}
		
    @Override
    public void onDestroy() {
        super.onDestroy();
        RecipeFinderApplication.getMyKitchen().removeView(this);
    }
	
	@Override
	public void update(IngredientList model) {
		List<Ingredient> ingredients = RecipeFinderApplication.getController().getIngredients();
		this.displayResults(ingredients);
	}
	
	private void displayResults(List<Ingredient> ingredients) {
		// uses an ArrayAdapter and displays the items
		ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(this,
				  R.layout.list_item, ingredients);
		listResults.setAdapter(adapter); 
	}
}
