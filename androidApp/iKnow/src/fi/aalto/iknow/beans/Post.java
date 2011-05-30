package fi.aalto.iknow.beans;

public class Post {
	
	public enum Category{
		Restaurant(1), Hotel(2), Library(3), Hospital(4), Apartment(5),Common(6);
		Category(int id){
			this.id = id;
		}
		public int getCategoryId(){
			return this.id;
		}
		public String getCategoryValue(){
			return this.name();
		}
		private int id;
	}
	
	private int post_id;
	private LocationBean location;
	private Address address;
	private String title;
	private String keyword = "";
	private Category category;
	private String picture = "";
	private String content = "";
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int postId) {
		post_id = postId;
	}
	public LocationBean getLocation() {
		return location;
	}
	public void setLocation(LocationBean location) {
		this.location = location;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
