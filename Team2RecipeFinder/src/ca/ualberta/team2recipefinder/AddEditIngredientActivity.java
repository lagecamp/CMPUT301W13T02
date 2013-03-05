package ca.ualberta.team2recipefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditIngredientActivity extends Activity {
	
	private final int EDIT = 0;
	private final int ADD = 1;
	
	String ingredientType;
	Ingredient currentIngredient;
	
	int mode = ADD;
	
	TextView txtType;
	TextView txtAmount;
	TextView txtUnit;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_ingredient);
		
		txtType = (TextView) findViewById(R.id.txtType);
		txtAmount = (TextView) findViewById(R.id.txtAmount);
		txtUnit = (TextView) findViewById(R.id.txtUnit);
		
		TextView lblAddEdit = (TextView) findViewById(R.id.lblAddEdit);
		
		// get parameters for the activity
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			
			if (extras != null) {
				ingredientType = extras.getString("ingredientType");				
				currentIngredient = RecipeFinderApplication.getController().getIngredient(ingredientType);
				
				mode = EDIT;
			}
			else {
				mode = ADD;
			}
		}
		
		if (mode == EDIT) {			
			txtType.setText(currentIngredient.getType());
			txtAmount.setText(currentIngredient.getAmount().toString());
			txtUnit.setText(currentIngredient.getUnity());
			
			txtType.setEnabled(false);
			
			lblAddEdit.setText(getString(R.string.edit_ingredient));
		}
		else {
			lblAddEdit.setText(getString(R.string.add_ingredient));
		}
		
		// event for when the user has finished editing / adding
		Button btnOK = (Button) findViewById(R.id.btnOk);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// validate fields
				if (txtUnit.getText().toString().length() > 0 &&
					txtAmount.getText().toString().length() > 0 &&
					txtType.getText().toString().length() > 0 &&
					Integer.parseInt(txtAmount.getText().toString()) > 0) {
					
					Ingredient ingredient = new Ingredient(txtType.getText().toString(),
							Integer.parseInt(txtAmount.getText().toString()), txtUnit.getText().toString());
					
					if (mode == ADD) {					
						RecipeFinderApplication.getController().addIngredient(ingredient);
					}
					else {
						RecipeFinderApplication.getController().replaceIngredient(currentIngredient, ingredient);
					}
					
					finish();
				}
				else {
					Toast.makeText(AddEditIngredientActivity.this, getString(R.string.missing_fields), 
							   Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// sets the event for when the user wants to delete the record
		Button btnEdit = (Button) findViewById(R.id.btnDelete);
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RecipeFinderApplication.getController().deleteIngredient(currentIngredient);
				finish();
			}			
		});
	}
}