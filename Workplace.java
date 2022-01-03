import java.util.ArrayList;
import java.util.List;

public class Workplace extends Party implements Formal {
	// instance variables
	private String dressCode;
	private String arrivalTime;
	// constructor
	public Workplace(String name, String location, String date, AllPurposeUser host, List<String> guestList,
			String dressCode, String arrivalTime) {
		super(name, location, date, host, guestList);
		this.dressCode = dressCode;
		this.arrivalTime = arrivalTime;
	}
	// overloaded constructor with empty guest list
	public Workplace(String name, String location, String date, AllPurposeUser host,
			String dressCode, String arrivalTime) {
		this(name, location, date, host, new ArrayList<>(), dressCode, arrivalTime);	
	}
	
	// getter and setters
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

	@Override
	public String toString() {
		return super.toString() + "\n\tDress Code: " + dressCode + "\n\tArrival Time: " + arrivalTime;
	}
	
}
