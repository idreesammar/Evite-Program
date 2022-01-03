import java.util.*;

public abstract class Party {
	// instance variables
	private String name;
	private String location;
	private String date;
	private AllPurposeUser host;
	private List<String> guestList; // holds the email of each guest
	// constructor 
	public Party(String name, String location, String date, AllPurposeUser host, List<String> guestList) {
		this.name = name;
		this.location = location;
		this.date = date;
		this.host = host;
		this.guestList = guestList;
	}
	
	// accessor and mutators
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the host
	 */
	public AllPurposeUser getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(AllPurposeUser host) {
		this.host = host;
	}
	/**
	 * @return the guestList
	 */
	public List<String> getGuestList() {
		return guestList;
	}
	public void updateGuestList(String guestEmail) {
		guestList.add(guestEmail);
	}
	@Override
	public String toString() {
		return name + "\n\tLocation: " + location + "\n\tDate: " + date + "\n\tHost: " + host;
	}
	
	
}
