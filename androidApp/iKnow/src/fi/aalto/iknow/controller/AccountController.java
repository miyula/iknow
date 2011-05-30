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

import fi.aalto.iknow.SaveAccountActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AccountController {
	private final String SESSION_URL = "http://jimu.cs.hut.fi/iknow/rest/session";
	
	private final String SETTING_INFOS = "SETTING_Infos";
	private final String USERNAME_KEY = "IKNOW_USERNAME";
	private final String PASSWORD_KEY = "IKNOW_PASSWORD";
	
	private Activity activity = null;
	
	public AccountController(final Activity activity){
		this.activity = activity;
	}
	
	public boolean validateAccount(String username, String password){
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(SESSION_URL);
		try{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username",username));
			nameValuePairs.add(new BasicNameValuePair("password",password));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			if(response!=null){
				int statusCode = response.getStatusLine().getStatusCode();
				//validation access
				if(statusCode==200){
					return true;
				}else{
					Toast.makeText(activity, "Error code "+statusCode, Toast.LENGTH_SHORT).show();
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
	public void saveAccount(String username, String password){
		SharedPreferences settings = activity.getSharedPreferences(SETTING_INFOS, 0);
		if(username!=null&&!username.equals("")){
			settings.edit().putString(USERNAME_KEY, username).commit();
		}
		if(password!=null&&!password.equals("")){
			settings.edit().putString(PASSWORD_KEY, password).commit();
		}
	} 
}
