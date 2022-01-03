import java.util.*;


public class GuestOnlyUser extends User {
	// instance variables
	private List<String> inviteList;
	private List<String> attendPartyList;
	// default constructor
	public GuestOnlyUser(String name, String email, String password, List<String> inviteList,
			List<String> attendPartyList) {
		super(name, email, password);
		this.inviteList = inviteList;
		this.attendPartyList = attendPartyList;
	}
	// overloaded constructor
	public GuestOnlyUser(String name, String email, String password) {
		this(name, email, password,new ArrayList<>(),new ArrayList<>());
		
	}
	/**
	 * @return the inviteList
	 */
	public List<String> getInviteList() {
		return inviteList;
	}
	/**
	 * @param inviteList the inviteList to set
	 */
	public void updateInviteList(String partyName) {
		inviteList.remove(partyName);
	}
	/**
	 * @return the attendPartyList
	 */
	public void addToInviteList(String partyName) {
		inviteList.add(partyName);
	}
	public List<String> getAttendPartyList() {
		return attendPartyList;
	}
	/**
	 * @param attendPartyList the attendPartyList to set
	 */
	public void updateAttendPartyList(String party) {
		attendPartyList.add(party);
	}
	
	
	
	
	
	
	
}
