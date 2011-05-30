package fi.aalto.iknow;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import fi.aalto.iknow.beans.Address;
import fi.aalto.iknow.beans.LocationBean;
import fi.aalto.iknow.beans.Post;
import fi.aalto.iknow.controller.PostController;
import fi.aalto.iknow.interfaces.GPSInterface;
import fi.aalto.iknow.utils.GPSLocation;
import fi.aalto.iknow.utils.HttpClientService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostNewActivity extends Activity implements GPSInterface{
	
	private static String MAP_WEB_SERVICE_URL = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	
	private EditText titleText = null;
	private EditText contentText = null;
	private EditText addressText = null;
//	private EditText latText = null;
//	private EditText lngText = null;
	private Spinner categorySpinner = null;
	private Spinner areaSpinner = null;
	private TextView locationText = null;
	private GPSLocation gps = null;
	private String formCheckResult = "";
	private Post newPost = new Post();
	private String photo_path = "";
	
	private ImageView photoView = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
        
        ImageView locationButton = (ImageView) this.findViewById(R.id.image_button_location);
        
        ImageView cameraButton = (ImageView) this.findViewById(R.id.image_button_camera);
        OnClickListener takePicListener = new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		Date date = new Date();
        		Timestamp now = new Timestamp(date.getTime()); 
        		//create and set new picture path
        		photo_path = Environment.getExternalStorageDirectory() + "/images/"+now+".jpg";
        		File file = new File(photo_path);
        	    Uri outputFileUri = Uri.fromFile(file);

        	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        	    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        	    
        	    startActivityForResult(intent,0);

			}
        };
        cameraButton.setOnClickListener(takePicListener);
        
        ImageView tagsButton = (ImageView) this.findViewById(R.id.image_button_tags);
        
        Button sendButton = (Button) this.findViewById(R.id.button_new_post_send);
        
        photoView = (ImageView) this.findViewById(R.id.image_view_photo);
        
//        picture_path = Environment.getExternalStorageDirectory() + "/images/new_post_photo.jpg";
//        
//        titleText = (EditText) this.findViewById(R.id.edittext_post_title);
//        
////        latText = (EditText) this.findViewById(R.id.edittext_post_lat);
////        lngText = (EditText) this.findViewById(R.id.edittext_post_lng);
//        contentText = (EditText) this.findViewById(R.id.edittext_post_content);
//        
//        locationText = (TextView) this.findViewById(R.id.textview_location_value);
//        addressText = (EditText) this.findViewById(R.id.edittext_post_address);
//        //start GPS service
//        gps = GPSLocation.getInstance();
//        gps.registerGPSHandler(PostNewActivity.this);
//        gps.registerLocationService(PostNewActivity.this);
//        locationText.setText("Getting GPS...");
//        
//        
//        
//        categorySpinner = (Spinner) this.findViewById(R.id.spinner_post_category);
//        ArrayList<String> categoryList = new ArrayList<String>();
//        categoryList.add(Post.Category.Hospital.name());
//        categoryList.add(Post.Category.Hotel.name());
//        categoryList.add(Post.Category.Library.name());
//        categoryList.add(Post.Category.Restaurant.name());
//        categoryList.add(Post.Category.Apartment.name());
//        categoryList.add(Post.Category.Common.name());
//        categorySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryList));
//        
//        areaSpinner = (Spinner) this.findViewById(R.id.spinner_post_area);
//        ArrayList<String> areaList = new ArrayList<String>();
//        areaList.add("Helsinki");
//        areaList.add("Espoo");
//        areaSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,areaList));
//        
//        Button postItButton = (Button) this.findViewById(R.id.button_new_post_it);
//        OnClickListener postItListener = new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				sendNewPost();
//			}
//        	
//        };
//        postItButton.setOnClickListener(postItListener);
//        
//        Button takePicButton = (Button) this.findViewById(R.id.button_post_take_picture);
//        OnClickListener takePicListener = new OnClickListener(){
//        	@Override
//			public void onClick(View v) {
//        		File file = new File( picture_path );
//        	    Uri outputFileUri = Uri.fromFile( file );
//
//        	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
//        	    intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
//        	    
//        	    startActivityForResult( intent, 0 );
//
//			}
//        };
//        takePicButton.setOnClickListener(takePicListener);
        
	}


	@Override
	public void onGetLocationChangeListener(Location location) {
		String lat = ""+location.getLatitude();
		String lng = ""+location.getLongitude();
		
		locationText.setText(lat+", "+lng);
		LocationBean l = new LocationBean(lat,lng);
		newPost.setLocation(l);
		//close GPS service
		if(gps!=null){
			gps.unregisterLocationService();
		}
		
		//fetch address from Google map web services
        HttpGet request = new HttpGet();
        try {
			//URI like:http://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=true
        	request.setURI(new URI(MAP_WEB_SERVICE_URL+lat+","+lng+"&sensor=true"));			
			String result = HttpClientService.getHttpGetResponseResult(request);	
			JSONObject jsonObj = new JSONObject(result);
			String status = jsonObj.get("status").toString();
			if(status.equals("OK")){
				JSONObject resultObj = jsonObj.getJSONArray("results").getJSONObject(0);
				String address = resultObj.getString("formatted_address");
				if(address!=null){
					this.addressText.setText(address);
					if(address.contains("Espoo,")){
						this.areaSpinner.setSelection(1);
					}						
				}				
			}else{
				Toast.makeText(PostNewActivity.this, status, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(PostNewActivity.this, "Failed to get address", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public void onDestroy(){
		//close GPS service
		if(gps!=null){
			gps.unregisterLocationService();
		}
		super.onDestroy();
	}
	
	private boolean checkPostForm(){

		formCheckResult = "";
		//check title
		String title = this.titleText.getText().toString().trim();
		if(title!=null&&!title.equals("")){
			this.newPost.setTitle(title);
		}else{
			formCheckResult = "Title is required.";
			return false;
		}
		
		//check if location is got
		if(newPost.getLocation()==null){
			formCheckResult = "Can't get your location! Please check the GPS settings on your mobile.";
			return false;
		}
		
		//get address
		String area = (String) this.areaSpinner.getSelectedItem();
		String addressStr = this.addressText.getText().toString().trim();
		Address address = new Address(addressStr,area,"Finland");
		newPost.setAddress(address);
		
		//get category selected value
		String category = (String) this.categorySpinner.getSelectedItem();
		newPost.setCategory(Post.Category.valueOf(category));
		
		//check content
		String content = this.contentText.getText().toString().trim();
		if(content!=null&&!content.equals("")){
			this.newPost.setContent(content);
		}else{
			formCheckResult = "Don't post an empty content.";
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    Log.i( "iknow", "resultCode: " + resultCode );
	    switch( resultCode )
	    {
	    	case 0:
	    		Log.i( "iknow", "User cancelled" );
	    		break;

	    	case -1://back from camera
	    		showPhoto();
	    		break;
	    }
	}
	
	/**
	 * Display photo on ImageView
	 */
	private void showPhoto(){
		BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 4;

	    Bitmap bitmap = BitmapFactory.decodeFile( photo_path, options );
	    photoView.setImageBitmap(bitmap);

	}
	
	/**
	 * post new to iKnow server side
	 */
	private void sendNewPost(){
		if(checkPostForm()){
			PostController pcontroller = new PostController(PostNewActivity.this);
			
			if(pcontroller.postNewIknow(newPost)){
				this.finish();
			}
		}else{
			Toast.makeText(PostNewActivity.this, formCheckResult, Toast.LENGTH_SHORT).show();
				
		}
	}
	
}
