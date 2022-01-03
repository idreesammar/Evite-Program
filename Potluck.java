import java.util.ArrayList;
import java.util.List;

public class Potluck extends Party {
	// instance variables
	private String dish;
	// constructor
	public Potluck(String name, String location, String date, AllPurposeUser host, List<String> guestList,
			String dish) {
		super(name, location, date, host, guestList);
		this.dish = dish;
	}
	// overloaded constructor for new potlucks that have empty guest list
	public Potluck(String name, String location, String date, AllPurposeUser host,
			String dish) {
		this(name, location, date, host, new ArrayList<>(), dish);
	}
	// getter and setters
	/**
	 * @return the dish
	 */
	public String getDish() {
		return dish;
	}
	/**
	 * @param dish the dish to set
	 */
	public void setDish(String dish) {
		this.dish = dish;
	}
	@Override
	public String toString() {
		return super.toString() + "\n\tDish: " + dish;
	}
	
	
}
