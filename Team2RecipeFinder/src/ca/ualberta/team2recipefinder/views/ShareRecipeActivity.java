package ca.ualberta.team2recipefinder.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.controller.Controller;
import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.model.Ingredient;
import ca.ualberta.team2recipefinder.model.Recipe;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
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
	Button button_html;
	Button button_img;
	Boolean isText = true;
	Boolean showImage = true;
	String email_text;
	String email_html;
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
		
		email_text = currentRecipe.getEmailText();
		email_html = currentRecipe.getEmailHtml();
		preview_text = (TextView) findViewById(R.id.preview_text);
		preview_image = (ImageView) findViewById(R.id.preview_image);

		preview_text.setText(email_text);
		/*
		 * If the recipe contains photos,
		 * assign it to ImageView previe_image
		 */
		manipulateImage(currentRecipe.hasPhotos());
		
		
		button_ok = (Button) findViewById(R.id.ok);
		button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lxian2shell@gmail.com"});
					intent.putExtra(Intent.EXTRA_SUBJECT,"Recipe: "+currentRecipe.getName());
					
					if(showImage == true) {
						intent.setType("image/*");
						
						File img = saveBitmap(currentRecipe.getPhoto(photoIndex));
						Uri uri = Uri.fromFile(img);
						
						intent.putExtra(Intent.EXTRA_STREAM, uri);
					}
					else {
						intent.setType("message/rfc822");
					}
						
					if(isText == true) {
						intent.putExtra(Intent.EXTRA_TEXT,email_text); 
					}
					else {
						intent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(currentRecipe.getEmailHtml())); 
					}
					
					startActivity(Intent.createChooser(intent, "Select email application."));
			}
		});
		
		
		button_html = (Button) findViewById(R.id.html);
		button_html.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(isText == true) {
					isText = false;
				}
				else {
					isText = true;
				}
				onResume();
			}
		});
		
		button_img = (Button) findViewById(R.id.img);
		button_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(showImage == true) {
					showImage = false;
				}
				else {
					showImage = true;
				}
				onResume();
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
    	
		if(isText == true) {
			preview_text.setText(email_text);
			button_html.setText("Html");
		}
		else {
			preview_text.setText(Html.fromHtml(email_html));
			button_html.setText("Text");
		}
		
		manipulateImage(showImage);
		
		if(showImage == true) {
			button_img.setText("Remove Image");
		}
		else {
			button_img.setText("Show Image");
		}
    	
        super.onResume();
    }
    
    private void manipulateImage(Boolean addImage) {
    	if(addImage == false){
    		preview_image.setImageBitmap(null);
    		return ;
    	}
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

    private File saveBitmap(Bitmap bmp){
    	String dir = Environment.getExternalStorageDirectory().toString();
    	FileOutputStream fileOutputStream;
    	File file = new File(dir, "imageToSend.jpeg");
    	
    	try {
    		fileOutputStream = new FileOutputStream (file);
    		bmp.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
    		fileOutputStream.flush();
    		fileOutputStream.close();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    	return file;
    }

}