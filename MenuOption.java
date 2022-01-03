
public enum MenuOption {

	UPDATE_PASSWORD("Update your current password"),
	SEE_PARTY_LIST("See all the parties you are attending and/or hosting"),
	RSVP_TO_PENDING_INVITE("Accept or Decline any pending invites"),
	UPGRADE("Upgrade to an All Purpose User"),
	HOST_NEW_PARTY("Create a new party to host (AP Users Only)"),
	SEND_INVITATIONS("Send invitations for any parties you are hosting (AP Users Only)"),
	VIEW_GUESTLIST("View the guestlist to a party (AP Users Only)"),
	CANCEL_PARTY("Cancel a party you are hosting (AP Users Only)"),
	PRINT_PARTY_INFO("Print a receipt of a party's info (AP Users Only)"),
	LOGOUT("Logout");
	
	private String description;
	private MenuOption(String description){
		this.description = description;
	}
	
	public String getDisplayString(){
		return this.description;
	}
	public static int getNumOptions() {
		return MenuOption.values().length-1;
	}
	
	public static MenuOption getOption(int num) {
		return MenuOption.values()[num];
	}
	public static String getMenuOptions() {
		String prompt = "*****\tITP 265 Evite Program Menu\t*****";

		for(MenuOption m : MenuOption.values()){ //array from the enum
			prompt += "\n" + (m.ordinal()) + ": " + m.getDisplayString();
		}
		prompt+="\n**********************************************\n";
		return prompt;
	}
	public static void printMenuOptions() {
		String prompt = getMenuOptions();
		System.out.println(prompt);
	}
}

// used HW 8 Menu enum for reference on this
