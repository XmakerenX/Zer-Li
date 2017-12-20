package user;

public class User {

	public class  UserException extends Exception {

		public UserException(String message)
		{
			super(message);
		}
	}
	
	public enum Permissions {CUSTOMER, STORE_WORKER, STORE_MANAGER, NETWORK_WORKER,NETWORK_MANAGER, SYSTEM_MANAGER, 
							CUSTOMER_SERVICE, CUSTOMER_SERVICE_EXPERT, CUSTOMER_SERVICE_WORKER};
							
	public enum Status {REGULAR, LOGGED_IN,BLOCKED};
							
	private String userName;
	private String userPassword;
	private Permissions userPermission;
	private String personID;
	private Status userStatus;
	private int unsuccessfulTries;
	
	User(String userName, String userPassword, Permissions userPermission, String personID) throws UserException
	{
		setUserName(userName);
		setUserPassword(userPassword);
		setUserPermission(userPermission);
		setPersonID(personID);
		setUserStatus(Status.REGULAR);
		clearUnsuccessfulTries();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws UserException {
		if (userName.length() >= 4)
			this.userName = userName;
		else
			throw new UserException("username is too shrot");
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) throws UserException {
		if (userPassword.length() >= 6)
			this.userPassword = userPassword;
		else
			throw new UserException("user password is too shrot");
	}

	public Permissions getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(Permissions userPermission) {
		this.userPermission = userPermission;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public int getUnsuccessfulTries() {
		return unsuccessfulTries;
	}

	public void incUnsuccessfulTries() {
			unsuccessfulTries++;
			if (unsuccessfulTries == 3)
				setUserStatus(Status.BLOCKED);
				
	}
	
	public void clearUnsuccessfulTries()
	{
		unsuccessfulTries = 0;
	}
	
}
