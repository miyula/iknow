package fi.aalto.iknow;

import fi.aalto.iknow.controller.AccountController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SaveAccountActivity extends Activity {
	
	private EditText usernameEdit = null;
	private EditText passwordEdit = null;
	private TextView statusText = null;
	private AccountController accountController = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.save_account);
        
        usernameEdit = (EditText) this.findViewById(R.id.edittext_username);
        usernameEdit.setText(AppHelper.getUsername(SaveAccountActivity.this));
        passwordEdit = (EditText) this.findViewById(R.id.edittext_password);
        passwordEdit.setText(AppHelper.getPassword(SaveAccountActivity.this));
        statusText = (TextView) this.findViewById(R.id.textview_save_account_status);
        
        accountController = new AccountController(SaveAccountActivity.this);
        
        Button saveAccountButton = (Button) this.findViewById(R.id.button_save_account);
        Button registerNewButton = (Button) this.findViewById(R.id.button_new_account);
        
        OnClickListener saveAccountListener = new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Toast.makeText(SaveAccountActivity.this, "Save account info...", Toast.LENGTH_SHORT).show();
				saveAccount();			
			}
        	
        };
        saveAccountButton.setOnClickListener(saveAccountListener);
        
    }
	
	/**
	 * validate account info from server and save to mobile device
	 */
	private void saveAccount(){
		String username = usernameEdit.getText().toString();
		String password = passwordEdit.getText().toString();
		boolean validateAccount = this.accountController.validateAccount(username, password);
		if(validateAccount){
			this.accountController.saveAccount(username, password);
			Intent toMainIntent = new Intent(SaveAccountActivity.this, MainActivity.class);
			startActivity(toMainIntent);
			finish();	
		}else{
			statusText.setText("Can not access the account validation");
		}
	}
}
