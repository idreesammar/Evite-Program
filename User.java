
public class User {
	// instance variables
	private String name;
	private String email;
	private String password;
	// constructor
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	// getter and setters
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	// toString method
	@Override
	public String toString() {
		return name + " (" + email + ")";
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
}
