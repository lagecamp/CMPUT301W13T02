package ca.ualberta.team2recipefinder.views;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.controller.Controller;
import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.model.Recipe;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShareRecipeActivity extends Activity {

	TextView preview;
	Button button_ok;
	String email_text;
	Recipe currentRecipe;
	long recipeID;
	
	protected void onCreate(Bundle savedInstanceState) {
		final Controller c = RecipeFinderApplication.getController();

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				recipeID = extras.getLong("recipeID");
				currentRecipe = c.getRecipe(recipeID);
			}
		}
		
		email_text = "Name: " + currentRecipe.getName()
				+"\nAuthor: "+ currentRecipe.getAuthor()
				+"\nProcedure: "+ currentRecipe.getProcedure();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		preview = (TextView) findViewById(R.id.preview);
		
		preview.setText(email_text);
		
		
		button_ok = (Button) findViewById(R.id.ok);
		button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("message/rfc822");
					intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lxian2shell@gmail.com"});
					intent.putExtra(Intent.EXTRA_SUBJECT,currentRecipe.getName());
					intent.putExtra(Intent.EXTRA_TEXT,email_text); 
					startActivity(Intent.createChooser(intent, "Select email application."));
			}
		});
		
		
	}
}