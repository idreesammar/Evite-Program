import java.util.*;

public class AllPurposeUser extends GuestOnlyUser {
	// instance variables
	private List<String> hostPartyList;
	// constructor
	public AllPurposeUser(String name, String email, String password, List<String> inviteList,
			List<String> attendPartyList, List<String> hostPartyList) {
		super(name, email, password, inviteList, attendPartyList);
		this.hostPartyList = hostPartyList;
	}
	// overloaded constructor (helpful when upgrading GO users to AP users in main program)
	public AllPurposeUser(String name, String email, String password, List<String> inviteList,
			List<String> attendPartyList) {
		this(name, email, password, inviteList, attendPartyList, new ArrayList<>());
	}
	// overloaded constructor (helpful when creating new AP users during signUP in main program)
		public AllPurposeUser(String name, String email, String password) {
			this(name, email, password, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		}
	/**
	 * @return the hostPartyList
	 */
	public List<String> getHostPartyList() {
		return hostPartyList;
	}
	/**
	 * @param hostPartyList the hostPartyList to set
	 */
	public void updateHostPartyList(String party) {
		hostPartyList.add(party);
	}
	
	
	
}
