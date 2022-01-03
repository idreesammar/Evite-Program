import java.util.ArrayList;
import java.util.List;

public class Wedding extends Party implements Formal, GiftRequired {
	// instance variables
	private String partner1;
	private String partner2;
	private double priceLimit; // for the gift one needs to bring
	private String dressCode;
	private String arrivalTime;
	// constructor
	public Wedding(String name, String location, String date, AllPurposeUser host, List<String> guestList,
			String partner1, String partner2, double priceLimit, String dressCode, String arrivalTime) {
		super(name, location, date, host, guestList);
		this.partner1 = partner1;
		this.partner2 = partner2;
		this.priceLimit = priceLimit;
		this.dressCode = dressCode;
		this.arrivalTime = arrivalTime;
	}
	// overloaded constructor for new weddings with empty guest lists
	public Wedding(String name, String location, String date, AllPurposeUser host, 
			String partner1, String partner2, double priceLimit, String dressCode, String arrivalTime) {
		this(name, location, date, host, new ArrayList<>(), partner1, partner2, priceLimit, dressCode, arrivalTime);
		
	}
	// getters and setters
	/**
	 * @return the priceLimit
	 */
	public double getPriceLimit() {
		return priceLimit;
	}
	/**
	 * @param priceLimit the priceLimit to set
	 */
	public void setPriceLimit(double priceLimit) {
		this.priceLimit = priceLimit;
	}
	/**
	 * @return the dressCode
	 */
	public String getDressCode() {
		return dressCode;
	}
	/**
	 * @param dressCode the dressCode to set
	 */
	public void setDressCode(String dressCode) {
		this.dressCode = dressCode;
	}
	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the partner1
	 */
	public String getPartner1() {
		return partner1;
	}
	/**
	 * @return the partner2
	 */
	public String getPartner2() {
		return partner2;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\n\tGift Price Limit: $" + priceLimit 
				+ "\n\tDress Code: " + dressCode + "\n\tArrival Time: " + arrivalTime;
	}
	
	

}
