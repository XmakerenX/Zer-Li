package user;

import java.io.Serializable;
/**
 * this class holds all the info needed for the user entity
 * @author dk198
 *
 */
public class User implements Serializable{

	public class  UserException extends Exception {

		public UserException(String message)
		{
			super(message);
		}
	}
	
	//enums:
	public enum Permissions {CUSTOMER/*0*/ , STORE_WORKER/*1*/, STORE_MANAGER/*2*/, NETWORK_WORKER/*3*/,
		NETWORK_MANAGER/*4*/,CUSTOMER_SERVICE/*5*/, CUSTOMER_SERVICE_EXPERT/*6*/, 
		CUSTOMER_SERVICE_WORKER/*7*/, SYSTEM_MANAGER /*8*/};
							
	public enum Status {REGULAR/*0*/, LOGGED_IN/*1*/, BLOCKED/*2*/};
							
	private String userName;
	private String userPassword;
	private Permissions userPermission;
	private int personID;
	private Status userStatus;
	private int unsuccessfulTries;
	//constructor for creating a new user
	public User(String userName, String userPassword, Permissions userPermission, int personID) throws UserException
	{
		setUserName(userName);
		setUserPassword(userPassword);
		setUserPermission(userPermission);
		setPersonID(personID);
		setUserStatus(Status.REGULAR);
		clearUnsuccessfulTries();
	}
	//constructor for getting a user from the database
	public User(String userName, String userPassword, User.Permissions userPermission, int personID, User.Status userStatus, int unsuccessfulTries) throws UserException
	{
		setUserName(userName);
		setUserPassword(userPassword);
		setUserPermission(userPermission);
		setPersonID(personID);
		setUserStatus(userStatus);
		this.unsuccessfulTries = unsuccessfulTries;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws UserException {
		if (userName.length() >= 4)
			this.userName = userName;
		else
			throw new UserException("Username is too short!");
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) throws UserException {
		if (userPassword.length() >= 6)
			this.userPassword = userPassword;
		else
			throw new UserException("User password is too short!");
	}

	public Permissions getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(Permissions userPermission) {
		this.userPermission = userPermission;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
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
	
	//*************************************************************************************************
    /**
     * Returns a string representation of the Replay
  	*  @return a string representation of the Replay
  	*/
//*************************************************************************************************
	public String toString()
	{
		return userName+", "+userPassword + ", " + userPermission + "," + personID + ", " + userStatus + "," +unsuccessfulTries;
	}
}
