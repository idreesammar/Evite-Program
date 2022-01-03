import java.util.ArrayList;
import java.util.List;

public class Birthday extends Party implements GiftRequired {
	// instance variables
	private String celebrant;
	private double priceLimit; // for the gift one needs to bring
	
	// constructor
	public Birthday(String name, String location, String date, AllPurposeUser host, List<String> guestList,
			String celebrant, double priceLimit) {
		super(name, location, date, host, guestList);
		this.celebrant = celebrant;
		this.priceLimit = priceLimit;
	}
	// overloaded constructors for new birthdays with empty guest list
	public Birthday(String name, String location, String date, AllPurposeUser host,
			String celebrant, double priceLimit) {
		this(name, location, date, host, new ArrayList<>(), celebrant, priceLimit);
	}
	// getters and setters
	/**
	 * @return the celebrant
	 */
	public String getCelebrant() {
		return celebrant;
	}
	/**
	 * @param celebrant the celebrant to set
	 */
	public void setCelebrant(String celebrant) {
		this.celebrant = celebrant;
	}
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
	
	@Override
	public String toString() {
		return super.toString() + "\n\tGift Price Limit: $" + priceLimit;
	}

}
