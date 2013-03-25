package ca.ualberta.team2recipefinder.views;

import java.lang.reflect.Field;  
import java.util.ArrayList;  
import java.util.List;

import ca.ualberta.team2recipefinder.R;
import ca.ualberta.team2recipefinder.controller.Controller;
import ca.ualberta.team2recipefinder.controller.RecipeFinderApplication;
import ca.ualberta.team2recipefinder.model.Recipe;

import android.app.Activity;  
import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.os.Bundle;  
import android.view.*;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
  
public class RecipeGalleryActivity extends Activity {  
	private Gallery gallery;  
	private List<Bitmap> bitmaps;
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	super.onCreate(savedInstanceState);  
	setContentView(R.layout.activity_gallery);  
	        
	Controller c = RecipeFinderApplication.getController();
	Bundle extras = getIntent().getExtras(); 
	Recipe currentRecipe = c.getRecipe(extras.getLong("recipeID"));
	bitmaps = currentRecipe.getAllPhotos();
	          
	gallery = (Gallery) findViewById(R.id.gallery);
			
	try {
			gallery.setAdapter(new ImageAdapter());
		} catch (Exception e){
			e.printStackTrace();
		}
		
        gallery.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> arg0,
					android.view.View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				setResult(arg2);
				finish();
			}  
        });  
    }  
      
    /* 
     * class ImageAdapter is used to control gallery source and operation. 
     */  
    private class ImageAdapter extends BaseAdapter{  
    	
        @Override  
        public int getCount() {  
            return bitmaps.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            return bitmaps.get(position);  
        }  
  
        @Override  
        public long getItemId(int position) {  
            return position;  
        }  
  
		@Override
		public android.view.View getView(int position, android.view.View arg1,
				ViewGroup arg2) {
			if(arg1 == null){
				arg1 = new ImageView(RecipeGalleryActivity.this);
			}
			((ImageView) arg1).setImageBitmap(bitmaps.get(position));
			
			return arg1;
		}  
          
    };  
}  