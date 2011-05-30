package fi.aalto.iknow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InitialActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check account info from mobile
        String username = AppHelper.getUsername(this);
        String password = AppHelper.getPassword(this);
        if(username==null||username.equals("")||password==null||username.equals("")){
        	//ask user to input username and password
        	Intent toSaveAccountIntent = new Intent(InitialActivity.this, SaveAccountActivity.class);
        	startActivity(toSaveAccountIntent);
        	this.finish();
        }else{
        	Intent toMainIntent = new Intent(InitialActivity.this, MainActivity.class);
        	startActivity(toMainIntent);
        	this.finish();
        }
        
        
    }
}