package fi.aalto.iknow;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;

public class TakePictureActivity extends Activity{
	
	SurfaceView camSurface;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//hide title
		
	}
}
