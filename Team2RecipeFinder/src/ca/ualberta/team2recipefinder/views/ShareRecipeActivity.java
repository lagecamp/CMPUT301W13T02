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

/**
 * Provide users a preview of the shared recipe in e-mail
 * Enable users to change the photos to send
 * and change the text format of recipe
 * @author cmput-301 team 2
 *
 */
public class ShareRecipeActivity extends Activity {

	TextView preview_text;
	ImageView preview_image;
	Button button_ok;
	String email_text;
	Recipe currentRecipe;
	
	long recipeID;
	int photoIndex = 0;
	
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

		/*
		 * If the recipe contains photos,
		 * assign it to ImageView previe_image
		 */
		if(currentRecipe.hasPhotos() == true) {
		preview_image.setImageBitmap(currentRecipe.getPhoto(photoIndex));
			
			/*
			 * Enters the gallery of the photos once clicks on the image
			 */
			preview_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent1 = new Intent(ShareRecipeActivity.this, RecipeGalleryActivity.class);
					intent1.putExtra("recipeID", recipeID);
					startActivityForResult(intent1, photoIndex);
				}

			});
		}
		
		
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
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		photoIndex = resultCode;
	}
    @Override
    protected void onResume() {
    	if(currentRecipe.hasPhotos() == true) {
			preview_image.setImageBitmap(currentRecipe.getPhoto(photoIndex));
    	}
        super.onResume();
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