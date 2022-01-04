package models;

public class User {

	private int code;
	private String id;
	private String name;
	private String password;
	private String lastName;
	private UserState userState;
	private Position position;

	public User(String id, String name, String password, String lastName, UserState userState, Position position) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.lastName = lastName;
		this.userState = userState;
		this.position = position;
	}

	public String[] dataAsVector() {
		String[] data = { id, name, lastName, userState.toString(), position.toString() };
		return data;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getLastName() {
		return lastName;
	}

	public String getId() {
		return id;
	}

	public UserState getUserState() {
		return userState;
	}

	public Position getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(String lastName) {
		this.lastName = lastName;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public void setType(Position position) {
		this.position = position;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
