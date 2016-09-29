package Graph;

public class Node {
	String id;
	String label;
	String Country;
	Double Longitude;
	Double Latitude;
	
	
	
	public Node(String id, String label, String country, Double longitude,
			Double latitude) {
		super();
		this.id = id;
		this.label = label;
		Country = country;
		Longitude = longitude;
		Latitude = latitude;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	
	
}
