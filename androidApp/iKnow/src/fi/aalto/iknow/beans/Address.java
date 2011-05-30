package fi.aalto.iknow.beans;

public class Address {
	private int addressID;
	private String address = "";
	private String city = "";
	private String country = "";
	
	public Address(String address, String city, String country){
		this.address = address;
		this.city = city;
		this.country = country;
	}
	
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
