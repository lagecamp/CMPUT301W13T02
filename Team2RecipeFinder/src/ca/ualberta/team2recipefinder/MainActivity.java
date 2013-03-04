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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView recipes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recipes = (ListView) findViewById(R.id.recipeList);
		/* set up all button listeners */
		
		Button addButton = (Button) findViewById(R.id.button_main_add);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
				startActivity(intent);
			}		
		});
		
		Button searchButton = (Button) findViewById(R.id.button_main_search);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
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
		
		
		refresh();
	
		
		recipes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
				Recipe r = (Recipe) recipes.getItemAtPosition(position);
				intent.putExtra("recipeID", r.getRecipeID());
				startActivity(intent);
			}
		});

	}

	
	public void refresh() {
		ListView recipes = (ListView) findViewById(R.id.recipeList);
		Controller c = RecipeFinderApplication.getController();
		List<Recipe> recipeList = c.getRecipes();
		final ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this, R.layout.list_item, recipeList);
		recipes.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		refresh();
	}
	
	
}
