package ca.ualberta.team2recipefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddEditIngredientActivity extends Activity {
	
	private final int EDIT = 0;
	private final int ADD = 1;
	
	long ingredientID;
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
				ingredientID = extras.getLong("ingredientID");
				// need to add an ID to ingredient
				// mode EDIT will be done later
				// currentIngredient = RecipeFinderApplication.getController().get(ingredientID);
				mode = EDIT;
			}
			else {
				mode = ADD;
			}
		}
		
		if (mode == EDIT) {
			// complete this code later
			
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
					
					if (mode == ADD) {
						Ingredient ingredient = new Ingredient(txtType.getText().toString(),
								Integer.parseInt(txtAmount.getText().toString()), txtUnit.getText().toString());
						
						RecipeFinderApplication.getController().addIngredient(ingredient);
						
						finish();
					}
					else {
						// edit mode
					}
				}
			}			
		});
	}
}