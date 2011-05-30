package fi.aalto.iknow.utils;

import fi.aalto.iknow.interfaces.GPSInterface;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;



public class GPSLocation {

	private static GPSLocation mGPS = null;
	private Activity mActivity = null;
	private LocationManager mLocationManager = null;
	private GPSInterface GPSHandler = null;
	
	private final LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			if(GPSHandler!=null){
				GPSHandler.onGetLocationChangeListener(location);
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private GPSLocation(){
	}
	
	public static final GPSLocation getInstance(){
		if(mGPS == null){
			mGPS = new GPSLocation();
		}
		return mGPS;
	}
	
	/**
	 * register with location service 
	 * 
	 * @param activity
	 */
	
	public void registerLocationService(final Activity activity){
		this.mActivity = activity;

		// enable location service
		this.mLocationManager = (LocationManager) this.mActivity.getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400L, 10, locationListener);
		this.mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400L, 10, locationListener);
	}
	
	public void registerGPSHandler(GPSInterface GPSHandler){
		this.GPSHandler = GPSHandler;
	}
	
	/**
	 * unregister the service, stop the GPS
	 * 
	 */

	public void unregisterLocationService(){
		if(this.mLocationManager != null){
			this.mLocationManager.removeUpdates(locationListener);
		}
	}
	
	/**
	 * Get current location from LocationManager, default from network, if no network GPS
	 * 
	 * @return Location
	 */
	public Location getCurrentLocation(){
		Location location = this.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(location==null){
			location = this.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		return location;
	}
	
	/**
	 * Get current accurate location from GPS
	 * @return
	 */
	public Location getAccurateLocation(){
		return this.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
}

