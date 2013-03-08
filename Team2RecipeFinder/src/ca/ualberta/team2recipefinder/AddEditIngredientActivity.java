/* AddEditIngredientActivity
 * 
 * Last Edited: March 7, 2013
 * 
 * 
 */

package ca.ualberta.team2recipefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * AddEditIngredientActivity is an android activity for adding or
 * editing an an ingredient
 * 
 * @author cmput-301 team 2
 * @see ca.ualberta.team2recipefinder.Ingredient
 */
public class AddEditIngredientActivity extends Activity {
	
	private final int EDIT = 0;
	private final int ADD = 1;
		
	int mode = ADD;
	
	TextView txtType;
	TextView txtAmount;
	TextView txtUnit;
	
	Button btnDelete;
		
	@Override
	/**
	 * Sets up all listeners for this activity.
	 * 
	 * @param	savedInstanceState Bundle containing the activity's previously frozen state, if there was one. 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_ingredient);
		
		txtType = (TextView) findViewById(R.id.txtType);
		txtAmount = (TextView) findViewById(R.id.txtAmount);
		txtUnit = (TextView) findViewById(R.id.txtUnit);
		
		TextView lblAddEdit = (TextView) findViewById(R.id.lblAddEdit);
		
		btnDelete = (Button) findViewById(R.id.btnDelete);
		
		// get parameters for the activity
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			
			if (extras != null) {
				String screenMode = extras.getString("mode");				
								
				if (screenMode.equals("edit")) {
					mode = EDIT;
					
					txtType.setText(extras.getString("type"));
					txtAmount.setText(extras.getString("amount"));
					txtUnit.setText(extras.getString("unit"));
				}
				else {
					mode = ADD;
				}
			}
			else {
				mode = ADD;
			}
		}
		
		if (mode == EDIT) {						
			txtType.setEnabled(false);
			
			lblAddEdit.setText(getString(R.string.edit_ingredient));
		}
		else {
			btnDelete.setVisibility(View.INVISIBLE);
			
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
					Double.parseDouble(txtAmount.getText().toString()) > 0) {
					
					Ingredient ingredient = new Ingredient(txtType.getText().toString(),
							Double.parseDouble(txtAmount.getText().toString()), txtUnit.getText().toString());
					
					Intent intent = new Intent();
					intent.putExtra("result", ingredient);
					setResult(RESULT_OK, intent);
					finish();
				}
				else {
					Toast.makeText(AddEditIngredientActivity.this, getString(R.string.missing_fields), 
							   Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// sets the event for when the user wants to delete the record
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("deleted", "yes");
				setResult(RESULT_OK, intent);
				finish();
			}			
		});
	}
}