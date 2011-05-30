package fi.aalto.iknow.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public final class HttpClientService {
	private static HttpClient httpclient;
	static{
		httpclient = new DefaultHttpClient();
	}
	public static HttpClient getHttpClient(){
		if(httpclient==null){
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}
	public static String getHttpGetResponseResult(HttpGet request) throws Exception{
		HttpResponse response = httpclient.execute(request);
		InputStream instream = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		instream.close();
		return sb.toString();
	}
}
