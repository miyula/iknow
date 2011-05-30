package fi.aalto.iknow.beans;

public class LocationBean {
	private int locationID;
	private String lat;
	private String lng;
	
	public LocationBean(String lat, String lng){
		this.lat = lat;
		this.lng = lng;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
}
