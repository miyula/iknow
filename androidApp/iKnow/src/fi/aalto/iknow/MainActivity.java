package fi.aalto.iknow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button postNewButton = (Button) this.findViewById(R.id.button_post_new);
        Button viewMapButton = (Button) this.findViewById(R.id.button_view_map);
        
        OnClickListener postNewListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent toPostNewActivityIntent = new Intent(MainActivity.this, PostNewActivity.class);
				startActivity(toPostNewActivityIntent);				
			}
        };
        postNewButton.setOnClickListener(postNewListener);
        
        OnClickListener viewMapListener = new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		//TODO display a map view
        		Toast.makeText(MainActivity.this, "This will be implemented later...", Toast.LENGTH_SHORT).show();		
			}
        };
        viewMapButton.setOnClickListener(viewMapListener);
	}
}
