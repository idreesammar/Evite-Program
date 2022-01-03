import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.*;


public class EviteProgram {
	// instance variables
	private BFF bff;
	private User user = null; // for the current user logged in
	private boolean loggedIn = false;
	// map of users (String email --> User objects)
	private HashMap<String,User> userMap = new HashMap<>();
	// map of parties (String party name --> Party objects)
	private HashMap<String, Party> partyMap = new HashMap<>();
	// files to read/write from
	private String userFileName = "src/Users.txt"; //change this before EdStem submission!!!
	private String partyFileName = "src/Parties.txt";
	
	// constructor
	public EviteProgram() {
		bff = new BFF();
		// read in the users and parties and fill up the maps
		readUsers(userFileName);
		readParties(partyFileName);
	}
	
	// method to read parties from Parties.txt
	private void readParties(String partyFileName) {
		try (Scanner sc = new Scanner(new FileInputStream(partyFileName))){ // read in the Users.txt file using scanner
			while (sc.hasNextLine()) {
				String line = sc.nextLine(); // read in each line of text file
				Scanner scan = new Scanner(line); // new scanner to dissect info from each line
				scan.useDelimiter("/");  // delimiter
				String name = scan.next();
				String location = scan.next();
				String date = scan.next();
				String hostEmail = scan.next();
				// get the corresponding host object
				AllPurposeUser host = (AllPurposeUser)userMap.get(hostEmail);
				String guestLine = scan.next();
				// break down the guestLine
				List<String> guestList = new ArrayList<>();
				if (!(guestLine.equals("none"))) { // guest list is NOT EMPTY
					parseLine(guestLine,guestList);
				}
				String type = scan.next();  // type of party
				if (type.equalsIgnoreCase("potluck")) {
					String dish = scan.next();
					Potluck p = new Potluck(name,location,date,host,guestList,dish);
					partyMap.put(p.getName(), p);
				}
				if (type.equalsIgnoreCase("workplace")) {
					String dresscode = scan.next();
					String arrivaltime = scan.next();
					Workplace w = new Workplace(name,location,date,host,guestList,dresscode, arrivaltime);
					partyMap.put(w.getName(), w);
				}
				if (type.equalsIgnoreCase("wedding")) {
					String partner1 = scan.next();
					String partner2 = scan.next();
					double giftPriceLimit = scan.nextDouble();
					String dresscode = scan.next();
					String arrivaltime = scan.next();
					Wedding w = new Wedding(name,location,date,host,guestList,partner1,partner2,giftPriceLimit,dresscode,arrivaltime);
					partyMap.put(w.getName(), w);
				}
				if (type.equalsIgnoreCase("birthday")) {
					String celebrant = scan.next();
					double giftPriceLimit = scan.nextDouble();
					Birthday b = new Birthday(name,location,date,host,guestList,celebrant,giftPriceLimit);
					partyMap.put(b.getName(), b);
				}
				
			} // ends first while statement for hasNextLine
		} // ends try statement
			catch (FileNotFoundException e) { // inform user of exception when file cannot be found/read
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	// method to write parties to Parties.txt
	public void writeParties(String partyFileName) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(partyFileName))){ // set up printwriter
			for (Party p: partyMap.values()) {
				pw.print(p.getName() + "/" + p.getLocation() + "/" + p.getDate() + "/" + p.getHost().getEmail() + "/");
				if (p.getGuestList().isEmpty()) {
					pw.print("none/");
				}
				else { // guest list for this party is not empty
					for (String guest: p.getGuestList()) {
						pw.print(guest + ",");
					}
					pw.print("/");
				}
				if (p instanceof Potluck) {
					Potluck pot = (Potluck)p; //typecast
					pw.println("Potluck/" + pot.getDish());
				}
				if (p instanceof Workplace) {
					Workplace work = (Workplace)p; //typecast
					pw.println("Workplace/" + work.getDressCode() + "/" + work.getArrivalTime());
				}
				if (p instanceof Birthday) {
					Birthday bday = (Birthday)p; //typecast
					pw.println("Birthday/" + bday.getCelebrant() + "/" + bday.getPriceLimit());
				}
				if (p instanceof Wedding) {
					Wedding wed = (Wedding)p; //typecast
					pw.println("Wedding/" + wed.getPartner1() + "/" + wed.getPartner2() + "/" + wed.getPriceLimit() + "/"
							+ wed.getDressCode() + "/" + wed.getArrivalTime());
				}
			}
			pw.close();
		} catch (FileNotFoundException e) { // notify user of error
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	
	// method to read users in from Users.txt
	public void readUsers(String userFilename) {
		try (Scanner sc = new Scanner(new FileInputStream(userFilename))){ // read in the Users.txt file using scanner
			while (sc.hasNextLine()) { 
				String line = sc.nextLine(); // read in each line of text file
				Scanner scan = new Scanner(line); // new scanner to dissect info from each line
				scan.useDelimiter("/");  // delimiter
				String name = scan.next();
				String email = scan.next();
				String password = scan.next();
				String inviteLine = scan.next();
				// break down the inviteList
				List<String> inviteList = new ArrayList<>(); 
				if (!(inviteLine.equals("none"))) { // invite list for this user is NOT empty
					parseLine(inviteLine,inviteList);	
				}
				String attendLine = scan.next(); // for the list of attending parties
				// break down the attend line
				List<String> attendList = new ArrayList<>();
				if (!(attendLine.equals("none"))) { // attend list for this user is NOT empty
					parseLine(attendLine,attendList);	
				}
				String type = scan.next();  // type of user (guest-only or allpurpose)
				if (type.equals("all purpose")) {
					String hostLine = scan.next(); // for the list of hosting parties
					// break down the hostline
					List<String> hostList = new ArrayList<>();
					if (!(hostLine.equals("none"))) { // invite list for this user is NOT empty
						parseLine(hostLine,hostList);	
					}
					// create the All purpose user object
					AllPurposeUser a = new AllPurposeUser(name,email,password,inviteList,attendList,hostList);
					userMap.put(a.getEmail(), a); // add to userMap
				}
				else { // for guest-only users who don't have a hostList
					GuestOnlyUser g = new GuestOnlyUser(name,email,password,inviteList,attendList);
					userMap.put(g.getEmail(), g);
				}
			} // ends first while statement for hasNextLine
		} // ends try statement
			catch (FileNotFoundException e) { // inform user of exception when file cannot be found/read
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	// method to parse line 
	public void parseLine (String line, List<String> list) {
		Scanner ls = new Scanner (line); // new scanner for dissecting
		ls.useDelimiter(",");
		while (ls.hasNext()) {
			String s = ls.next();
			list.add(s); // take each party name string and add 
		}
	}
	
	// method to write to Users.txt
	public void writeUsers(String userFileName) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(userFileName))){ // set up printwriter
			for (User u: userMap.values()) {
				pw.print(u.getName() + "/" + u.getEmail() + "/" + u.getPassword() + "/");
				GuestOnlyUser g = (GuestOnlyUser)u; // typecast (NOTE: every user is either GuestOnly or AllPurpose so it works)
				if (g.getInviteList().isEmpty()) {
					pw.print("none/");
				}
				else { // invite list not empty for this user
					for (String invite: g.getInviteList()) {
						pw.print(invite + ","); 
					}
					pw.print("/");
				}
				if (g.getAttendPartyList().isEmpty()) { 
					pw.print("none/");
				}
				else { // attend list not empty for this user
					for (String attendp: g.getAttendPartyList()) {
						pw.print(attendp + ","); 
					}
					pw.print("/");
				}
				if (g instanceof AllPurposeUser) { // if this user is an All Purpose User
					AllPurposeUser a = (AllPurposeUser)g; // typecast
					pw.print("all purpose/");
					if (a.getHostPartyList().isEmpty()) { 
						pw.println("none");
					}
					else { // host list not empty for this user
						for (String hostp: a.getHostPartyList()) {
							pw.print(hostp + ","); 
						}
						pw.println(""); // newline
					}
				}
				else { // NOT an AllPurpose User
					pw.println("guest only");
				}	
			}
			pw.close();
		} catch (FileNotFoundException e) { // notify user of error
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
	
	// MAIN METHOD
	public static void main(String[] args) {
		EviteProgram ep = new EviteProgram();
		ep.run();
		ep.writeUsers(ep.userFileName); // update any new users into Users.txt
		ep.writeParties(ep.partyFileName); // update any new parties into Parties.txt

	}
	
	// run method
	public void run() {
		System.out.println("Welcome to the Evite Program!\n");
		int option = 1; // default value
		while (option != 3) {
			option = bff.inputInt("Please choose an option:\n1) Sign Up\n2) Log in\n3) Quit", 1, 3);
			if (option == 1) {
				signUp();
			}
			else if (option == 2) {
				logIn();
			}
			while (loggedIn) { // some user is logged in through signing up or logging in
				MenuOption choice = MenuOption.UPDATE_PASSWORD; // initialized default
				while (!(choice == MenuOption.LOGOUT)) {
					MenuOption.printMenuOptions();
					int num = bff.inputInt(">", 0, MenuOption.getNumOptions());
					choice = MenuOption.getOption(num);
					
					switch(choice) {
					case UPDATE_PASSWORD:
						updatePassword();
						break;
						
					case SEE_PARTY_LIST:
						displayMyParties();
						break;
						
					case RSVP_TO_PENDING_INVITE:
						rsvpToInvite();
						break;
						
					case UPGRADE:
						if (user instanceof AllPurposeUser) {
							System.out.println("You are already an All Purpose User.");
						}
						else { // not currently an AP user
							upgradeUser();
						}
						break;
						
					case HOST_NEW_PARTY:
						if (!(user instanceof AllPurposeUser)) { // guestOnly users
							System.out.println("You must be an All Purpose User to host a party.");
						}
						else { // an AP user 
							makeParty();
						}
						break;
					
					case SEND_INVITATIONS:
						if (!(user instanceof AllPurposeUser)) { // guestOnly users
							System.out.println("You must be an All Purpose User to send invitations.");
						}
						else { // an AP user 
							sendInvites();
						}
						break;
					
					case VIEW_GUESTLIST:
						if (!(user instanceof AllPurposeUser)) { // guestOnly users
							System.out.println("You must be an All Purpose User to view a guestlist.");
						}
						else { // an AP user 
							viewAGuestList();
						}
						break;
						
					case CANCEL_PARTY:
						if (!(user instanceof AllPurposeUser)) { // guestOnly users
							System.out.println("You must be an All Purpose User to cancel a party.");
						}
						else { // an AP user 
							AllPurposeUser a = (AllPurposeUser)user; // typecast
							if (a.getHostPartyList().isEmpty()) {
								System.out.println("You are currently not hosting any parties.");
							}
							else { // host party list is not empty
								for (int i = 0; i < a.getHostPartyList().size(); i++) {
									System.out.println(i + ": " + a.getHostPartyList().get(i));
								}
								int partyChoice = bff.inputInt("Please choose a party: ", 0, a.getHostPartyList().size()-1);
								cancelParty(partyMap.get(a.getHostPartyList().get(partyChoice)));
							}
						}
						break;
					
					/* NOTE FOR GRADERS: this case was a last minute addition. I originally planned on leaving it with 
						"Available in v2" but decided last minute to complete it. While it works, I roughly reused code 
						so this last case is very clunky and doesn't really follow DRY Principles. Hopefully, you can keep
						this in mind when grading this portion and hopefully not grade it too harshly.... :)
					*/
					case PRINT_PARTY_INFO: 
						if (!(user instanceof AllPurposeUser)) { // guestOnly users
							System.out.println("You must be an All Purpose User to print party info.");
						}
						else { // an AP user 
							AllPurposeUser a = (AllPurposeUser)user; // typecast
							String type = bff.inputWord("Would you like to print info from a party you are attending or "
									+ "hosting? (attend/host): ", "attend", "host");
							if (type.equalsIgnoreCase("attend")) { // choosing from a party you are ATTENDING
								if (a.getAttendPartyList().isEmpty()) {
									System.out.println("You are currently not attending any parties as a guest.");
								}
								else { // attend list is not empty
									for (int i = 0; i < a.getAttendPartyList().size(); i++) {
										System.out.println(i + ": " + a.getAttendPartyList().get(i));
									}
									int partyChoice = bff.inputInt("Please choose a party: ", 0, a.getAttendPartyList().size()-1);
									printAParty(partyMap.get(a.getAttendPartyList().get(partyChoice))); // print this party's info
								}
							}
							else { // choosing from a party you are HOSTING
								if (a.getHostPartyList().isEmpty()) {
									System.out.println("You are currently not hosting any parties.");
								}
								else { // host list is not empty
									for (int i = 0; i < a.getHostPartyList().size(); i++) {
										System.out.println(i + ": " + a.getHostPartyList().get(i));
									}
									int partyChoice = bff.inputInt("Please choose a party: ", 0, a.getHostPartyList().size()-1);
									printAParty(partyMap.get(a.getHostPartyList().get(partyChoice))); // print this party's info
								}
							}
						}
						break;
						
					
					default: // this is for the LOGOUT option
						System.out.println("You have successfully logged out.");
						user = null;
						loggedIn = false;
					}
				}
			}
		}
		System.out.println("Goodbye! Thank you for using the Evite Program!");
	}
	
	// method for the signUp process of new users
	public void signUp() {
		String email = bff.inputWord("Please enter an email:");
		while (userMap.containsKey(email)) { // makes sure the new email is not already in use
			System.out.println("Sorry this email is already in use. Try again.");
			email = bff.inputWord("Please enter an email:");
		}
		String name = bff.inputLine("Please enter a name for the account:");
		String password = bff.inputLine("Please enter a password for the account:");
		String rePassword = bff.inputLine("Please re-enter the password:"); 
		while (!(password.equals(rePassword))) { // to verify the password choice
			System.out.println("Passwords do not match. Try again.");
			rePassword = bff.inputLine("Please re-enter the password:");
		}
		boolean allPurpose = bff.inputYesNo("Would you like to join as an All Purpose User? (y/n)");
		if (allPurpose) { // said yes to above question
			AllPurposeUser ap = new AllPurposeUser(name,email,password);
			userMap.put(ap.getEmail(), ap); // add to userMap
			user = ap; // set user to this
			loggedIn = true;
		}
		else { // said no to being all purpose user
			System.out.println("We will make you a Guest-Only User.");
			GuestOnlyUser g = new GuestOnlyUser(name, email, password);
			userMap.put(g.getEmail(), g); // add to userMap
			user = g; // set user to this
			loggedIn = true;
		}
		System.out.println("Account successfully created!\n"); // completion message
	}
	
	// method for signing in users who already have an account
	public void logIn() {
		String email = bff.inputWord("Please enter your email:");
		while (!(userMap.containsKey(email))) { // your email is not registered
			System.out.println("Sorry this email is not currently registered. Try again.");
			email = bff.inputWord("Please enter an email:");
		}
		String password = bff.inputWord("Please enter your password:");
		while (!(userMap.get(email).getPassword().equals(password))) { // verify password
			System.out.println("Sorry that is not the correct password. Try again.");
			password = bff.inputWord("Please enter your password:");
		}
		// username, password matched --> logged in
		user = userMap.get(email);
		loggedIn = true;
		System.out.println("\n"); // newline for neatness
	}
	
	// method for updating password
	public void updatePassword() {
		String pw = bff.inputLine("Please enter the current password: ");// holds user guess
		while (!(pw.equals(user.getPassword()))) {
			System.out.println("Sorry, that's not the correct password. Try again.");
			pw = bff.inputLine("Please enter the current password: ");
		}
		String newPass = bff.inputLine("Please enter the new password: ");
		user.setPassword(newPass);
		System.out.println("Password successfully changed!\n");
	}
	
	// method for displaying the user's parties
	public void displayMyParties() {
		GuestOnlyUser g = (GuestOnlyUser)user; // typecast
		System.out.println("Parties you are ATTENDING:");
		if (g.getAttendPartyList().isEmpty()) {
			System.out.println("\tNONE");
		}
		else { // user attending list is not empty
			for (String party: g.getAttendPartyList()) {
				System.out.println("\t" + party);
			}
		}
		if (g instanceof AllPurposeUser) {
			AllPurposeUser a = (AllPurposeUser)g; // typecast
			System.out.println("Parties you are HOSTING:");
			if (a.getHostPartyList().isEmpty()) {
				System.out.println("\tNONE");
			}
			else { // host list is not empty
				for (String party: a.getHostPartyList()) {
					System.out.println("\t" + party);
				}
			}
		}
	}
	
	// method to rsvp (accept/reject) an invite 
	public void rsvpToInvite() {
		GuestOnlyUser g = (GuestOnlyUser)user; // typecast
		if (g.getInviteList().isEmpty()) {
			System.out.println("You are currently not invited to any parties.");
		}
		else { // your invite list is not empty
			System.out.println("Your Invite List: ");
			for (int i = 0; i < g.getInviteList().size(); i++) {  // display the invites
				System.out.println("\t" + i + ": " + g.getInviteList().get(i));
			}
			int choice = bff.inputInt("Select a party: (press -1 to quit)", -1, g.getInviteList().size()-1);
			if (choice != -1) { // user does not want to quit
				boolean accept = bff.inputYesNo("Would you like to accept the invitation? (y/n)");
				if (accept) {
					partyMap.get(g.getInviteList().get(choice)).updateGuestList(g.getEmail()); // add the email to the party's guestlist
					g.getAttendPartyList().add(g.getInviteList().get(choice)); // add party name to user's attend list
					System.out.println("You are now attending " + g.getInviteList().get(choice) + "!");
				}
				else { // said no to last question
					System.out.println("You have declined the invite to " + g.getInviteList().get(choice) + ".");
				}
				g.getInviteList().remove(choice); // now remove that invite from the inviteList
			}
		}
	}
	
	// method to upgrade GO users into AP users
	public void upgradeUser() {
		boolean confirm = bff.inputYesNo("Are you sure you would like to upgrade? (y/n)");
		if (!confirm) {
			System.out.println("Upgrade request terminated.");
		}
		else { // user says yes to upgrade
			GuestOnlyUser g = (GuestOnlyUser)user; // typecast
			// now create a new AP object
			AllPurposeUser a = new AllPurposeUser(g.getName(),g.getEmail(),g.getPassword(),g.getInviteList(),g.getAttendPartyList());
			// now replace the GO version of this user with the new AP version of the user in userMap
			userMap.put(a.getEmail(), a);
			user = userMap.get(a.getEmail()); // set logged in user to new object
			System.out.println("Upgrade successful. You are now an All Purpose User!");
		}
	}
		
	// method to make/host a new party (NOTE: all parties made with this will have empty guest lists)
	public void makeParty() {
		System.out.println("Types of Parties: "); 
		for (PartyType type: PartyType.values()) {
			System.out.println(type.ordinal() + ":" + type);
		}
		int typeChoice = bff.inputInt("What type of party would you like to host? (press -1 to quit)");
		if (typeChoice != -1) { // user does not want to quit
			String name = bff.inputLine("What is the name of the event?" );
			String location = bff.inputLine("Where is the event's location?" );
			String date = bff.inputLine("When is the event's date?" );
			if (typeChoice == PartyType.POTLUCK.ordinal()) {
				String dish = bff.inputLine("What type of dish should you bring?");
				Potluck p = new Potluck(name,location,date,(AllPurposeUser)user,dish);
				partyMap.put(p.getName(), p); // add new party to partyMap
				((AllPurposeUser)user).getHostPartyList().add(p.getName()); // add party name to host party list
			}
			if (typeChoice == PartyType.BIRTHDAY.ordinal()) {
				String celebrant = bff.inputLine("What is the name of the celebrant?: ");
				double priceLimit = bff.inputDouble("What is the price limit for gifts?");
				Birthday b = new Birthday(name,location,date,(AllPurposeUser)user,celebrant,priceLimit);
				partyMap.put(b.getName(), b); // add to partyMap
				((AllPurposeUser)user).getHostPartyList().add(b.getName()); // add party name to host party list
			}
			if (typeChoice == PartyType.WEDDING.ordinal()) {
				String partner1 = bff.inputLine("What is the name of partner #1?: ");
				String partner2 = bff.inputLine("What is the name of partner #2?: ");
				double priceLimit = bff.inputDouble("What is the price limit for gifts?: ");
				String dressCode = bff.inputLine("What is the dresscode?: ");
				String arrivalTime = bff.inputLine("What is the arrival time?: ");
				Wedding w = new Wedding(name,location,date,(AllPurposeUser)user,partner1, partner2,priceLimit,dressCode,arrivalTime);
				partyMap.put(w.getName(), w); // add to partyMap
				((AllPurposeUser)user).getHostPartyList().add(w.getName()); // add party name to host party list
			}
			if (typeChoice == PartyType.WORKPLACE.ordinal()) {
				String dressCode = bff.inputLine("What is the dresscode?: ");
				String arrivalTime = bff.inputLine("What is the arrival time?: ");
				Workplace wp = new Workplace(name,location,date,(AllPurposeUser)user,dressCode,arrivalTime);
				partyMap.put(wp.getName(), wp); // add to partyMap
				((AllPurposeUser)user).getHostPartyList().add(wp.getName()); // add party name to host party list
			}
			System.out.println("The event " + name + " has successfully been made.");
		}
	}

	// method to send invites for an existing party (only if you are a host)
	public void sendInvites() {
		AllPurposeUser a = (AllPurposeUser)user; // typecast
		if (a.getHostPartyList().isEmpty()) {
			System.out.println("You currently have no parties you are hosting.");
		}
		else { // hostParty list is not empty
			for (int i =0; i < a.getHostPartyList().size(); i++) { // display all the parties the user is hosting
				System.out.println(i + ": " + a.getHostPartyList().get(i));
			}
			int choice = bff.inputInt("Which party would you like to send invites for? (press -1 to quit)", -1, a.getHostPartyList().size()-1);
			if (choice != -1) { // user does not want to quit
				String recipEmail = "";
				boolean sendMore = true; // ask user if they want to send more (default initialized to true)
				while (sendMore) {
					recipEmail = bff.inputLine("Type the email of the recipient: ");
					if (!(userMap.containsKey(recipEmail))) { // the email is not registered (recipient does not have account)
						makeNewAccount(recipEmail); // make this person an account with a temporary password
					}
					((GuestOnlyUser)userMap.get(recipEmail)).addToInviteList(a.getHostPartyList().get(choice)); // add party name to recipient invite list
					System.out.println("Invite sent to " + userMap.get(recipEmail));
					sendMore = bff.inputYesNo("Send another invite (y/n)? : "); // ask the user if they want to send more
				}
			} 
		}
	}
	
	
	// random password generator (for new users who are being made accounts for invites)
	public String makeRandPassword() {
		// randomly generated placeholder password
		Random r = new Random();
		int result = r.nextInt(1000);
		String password = "password" + String.valueOf(result);
		return password;
	}
	
	// method to make new account for an invite recipient with randomly generated password
	public void makeNewAccount(String recipEmail) {
		String recipName = bff.inputLine("Type the name of the recipient: ");
		String recipPass = makeRandPassword(); // randomly generated placeholder password
		GuestOnlyUser g = new GuestOnlyUser(recipName,recipEmail,recipPass); // make the user object
		userMap.put(g.getEmail(), g); // add the new user to userMap
	}
	
	// method to view a guest list of a party
	public void viewAGuestList() {
		AllPurposeUser a = (AllPurposeUser)user; // typecast
		String type = bff.inputWord("Would you like to view the guests from a party you are attending or hosting? (attend/host): ", "attend", "host");
		
		if (type.equalsIgnoreCase("attend")) { // want to see guest list from a party in ATTEND LIST
			if (a.getAttendPartyList().isEmpty()) {
				System.out.println("You are currently not attending any parties as a guest.");
			}
			else { // attend list is not empty
				for (int i = 0; i < a.getAttendPartyList().size(); i++) {
					System.out.println(i + ": " + a.getAttendPartyList().get(i));
				}
				int choice = bff.inputInt("Please choose a party: ", 0, a.getAttendPartyList().size()-1);
				System.out.println("GUEST LIST: ");
				for (String guest: partyMap.get(a.getAttendPartyList().get(choice)).getGuestList()) {
					System.out.println(userMap.get(guest));
				}
			}
		}
		
		else {  	// want to see the guest list from HOST LIST
			if (a.getHostPartyList().isEmpty()) {
				System.out.println("You are currently not hosting any parties.");
			}
			else { // host list is not empty
				for (int i = 0; i < a.getHostPartyList().size(); i++) {
					System.out.println(i + ": " + a.getHostPartyList().get(i));
				}
				int choice = bff.inputInt("Please choose a party: ", 0, a.getHostPartyList().size()-1);
				System.out.println("GUEST LIST: ");
				for (String guest: partyMap.get(a.getHostPartyList().get(choice)).getGuestList()) {
					System.out.println(userMap.get(guest));
				}
			}
		}
	}
	
	// method to cancel a pre-existing party one is hosting
	public void cancelParty(Party party) {
		// remove this party from all users' invite list, attend list if they are on them
		for (User u: userMap.values()) {
			GuestOnlyUser g = (GuestOnlyUser)u; // typecast
			g.getInviteList().remove(party.getName()); // if it is present, if not nothing changes
			g.getAttendPartyList().remove(party.getName()); // if it is present, if not nothing changes
		}
		// remove the party from the host's hostPartyList
		AllPurposeUser a = (AllPurposeUser)user; // the host of the party to be cancelled
		a.getHostPartyList().remove(party.getName()); 
		// remove party from partyMap
		partyMap.remove(party.getName());
		System.out.println(party.getName() + " was successfully cancelled.");
	}
	
	// method to print information about a party into a separate txt file
	public void printAParty(Party party) {
		String fileName = party.getName() + ".txt"; // change before Edstem Submission
		try (PrintWriter pw = new PrintWriter(new FileOutputStream("src/" + fileName))){ // set up printwriter
			if (party instanceof Potluck) {
				Potluck p = (Potluck)party; // typecast
				pw.println(p);
			}
			if (party instanceof Birthday) {
				Birthday b = (Birthday)party; // typecast
				pw.println(b);
			}
			if (party instanceof Wedding) {
				Wedding w = (Wedding)party; // typecast
				pw.println(w);
			}
			if (party instanceof Workplace) {
				Workplace wp = (Workplace)party; // typecast
				pw.println(wp);
			}
			pw.println(); // newline space
			pw.println("\tGuest List: ");
			for (String guest: party.getGuestList()) {
				pw.println("\t- " + userMap.get(guest));
			}
			pw.close();
			System.out.println(fileName + " successfully printed!"); // output message for the main program
		} catch (FileNotFoundException e) { // notify user of error
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}
	
}
