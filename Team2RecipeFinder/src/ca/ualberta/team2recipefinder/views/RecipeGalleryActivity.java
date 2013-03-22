package ca.ualberta.team2recipefinder.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.view.View;
import ca.ualberta.team2recipefinder.R;

public class RecipeGalleryActivity extends Activity{
	private Gallery gallery;
	private ImageView img;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);	
		
		gallery = (Gallery) findViewById(R.id.gallery);
		ImageView img = (ImageView) findViewById(R.id.img);
		
		try {
			gallery.setAdapter(new ImageAdapter());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private class ImageAdapter extends BaseAdapter {
			@Override
			public View getView(int position,
					View convertView, ViewGroup parent)
			{
	
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount()
			{

				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position)
			{

				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position)
			{

				// TODO Auto-generated method stub
				return 0;
			}

	}
}