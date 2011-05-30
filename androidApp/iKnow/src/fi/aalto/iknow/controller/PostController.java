package fi.aalto.iknow.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import fi.aalto.iknow.AppHelper;
import fi.aalto.iknow.beans.Post;

public class PostController {
	private final String IKNODE_URL = "http://jimu.cs.hut.fi/iknow/rest/iknode";
	private final String SESSION_URL = "http://jimu.cs.hut.fi/iknow/rest/session";
	
	private final String SETTING_INFOS = "SETTING_Infos";
	private final String USERNAME_KEY = "IKNOW_USERNAME";
	private final String PASSWORD_KEY = "IKNOW_PASSWORD";
	
	private Activity activity = null;
	
	public PostController(Activity activity){
		this.activity = activity;
	}
	
	public boolean postNewIknow(Post post){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(SESSION_URL);
		try{
			String username = AppHelper.getUsername(activity);
			String password = AppHelper.getPassword(activity);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username",username));
			nameValuePairs.add(new BasicNameValuePair("password",password));
			UrlEncodedFormEntity form = new UrlEncodedFormEntity(nameValuePairs);
			form.setContentEncoding(HTTP.UTF_8);
			httppost.setEntity(form);
			Log.d("TEST", "Start login");
			HttpResponse response = httpclient.execute(httppost);
			if(response!=null){
				int statusCode = response.getStatusLine().getStatusCode();
				//validation access
				if(statusCode==200){
					Log.d("TEST", "Login success");
					HttpPost iknodePost = new HttpPost(IKNODE_URL);
					List<NameValuePair> nodeValuePairs = new ArrayList<NameValuePair>();
					nodeValuePairs.add(new BasicNameValuePair("title",post.getTitle()));
					nodeValuePairs.add(new BasicNameValuePair("lat",post.getLocation().getLat()));
					nodeValuePairs.add(new BasicNameValuePair("lng",post.getLocation().getLng()));
					nodeValuePairs.add(new BasicNameValuePair("keyword",post.getKeyword()));
					nodeValuePairs.add(new BasicNameValuePair("category",""+post.getCategory().name()));
					nodeValuePairs.add(new BasicNameValuePair("picture",post.getPicture()));
					nodeValuePairs.add(new BasicNameValuePair("address",post.getAddress().getAddress()));//"Kuitinmäenkoulupolku, 02210 Espoo, Finland"));//post.getAddress().getAddress()));
					nodeValuePairs.add(new BasicNameValuePair("city",post.getAddress().getCity()));
					nodeValuePairs.add(new BasicNameValuePair("country",post.getAddress().getCountry()));
					nodeValuePairs.add(new BasicNameValuePair("content",post.getContent()));
					iknodePost.setEntity(new UrlEncodedFormEntity(nodeValuePairs));
					response = httpclient.execute(iknodePost);
					if(response!=null&&response.getStatusLine().getStatusCode()==200){
						return true;
					}else{
						Toast.makeText(activity, "Failed to post new.", Toast.LENGTH_SHORT).show();
						return false;
					}
					
				}else{
					Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show();
					return false;
				}
			}else{
				Toast.makeText(activity, "Response timeout.", Toast.LENGTH_SHORT).show();
				return false;
			}
		}catch (ClientProtocolException e) {
			Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
	        return false;
	    } catch (IOException e) {
	    	Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
	        return false;
	    }
	}
}
