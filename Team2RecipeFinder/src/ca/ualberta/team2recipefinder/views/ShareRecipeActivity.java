package ca.ualberta.team2recipefinder.views;

import java.util.List;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.controller.Controller;
import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.model.Ingredient;
import ca.ualberta.team2recipefinder.model.Recipe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareRecipeActivity extends Activity {

	TextView preview_text;
	ImageView preview_image;
	Button button_ok;
	String email_text;
	Recipe currentRecipe;
	
	long recipeID;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		final Controller c = RecipeFinderApplication.getController();

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeID = extras.getLong("recipeID");
				currentRecipe = c.getRecipe(recipeID);
			}
		}
		
		email_text = getEmailText();
		preview_text = (TextView) findViewById(R.id.preview_text);
		preview_image = (ImageView) findViewById(R.id.preview_image);
		
		//if(currentRecipe.hasPhotos() == true) {
			preview_image.setImageDrawable(currentRecipe.getPhoto(0));
			
			/*
			preview_image.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView parent, View v, int position, long id) {
				Intent intent1 = new Intent(this, RecipeGallaryActivity.class);
				intent1.putExtra("photos", currentRecipe.getAllPhotos());
				startActivityForResult(intent1, position);
             }

         });
         */
			preview_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent1 = new Intent(ShareRecipeActivity.this, RecipeGalleryActivity.class);
					intent1.putExtra("photos", recipeID);
					startActivity(intent1);
				}
			});
		//}
		
		
		preview_text.setText(email_text);
		
		
		button_ok = (Button) findViewById(R.id.ok);
		button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("message/rfc822");
					intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lxian2shell@gmail.com"});
					intent.putExtra(Intent.EXTRA_SUBJECT,"Recipe: "+currentRecipe.getName());
					intent.putExtra(Intent.EXTRA_TEXT,email_text); 
					startActivity(Intent.createChooser(intent, "Select email application."));
			}
		});
		
		
	}
	
	private String getEmailText() {
		String str;
		str = "Recipe: " + currentRecipe.getName() + "\n" ;
		str += "Author: " + currentRecipe.getAuthor() + "\n" ;
		str += "Ingredients: \n";
		List<Ingredient> ingredientList = currentRecipe.getIngredients();
		for(Ingredient ingredient : ingredientList ) {
			str += ingredient.toString() + "\n";
		}
		str += "Procedure: \n";
		str += currentRecipe.getProcedure();
		List<String> comments = currentRecipe.getAllComments();
		int idx=0;
		for(String comment : comments) {
			str += comments.get(idx) + "\n";
		}
		return str;
	}
}