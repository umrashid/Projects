package application;

import java.sql.ResultSet;

public class NewUser extends User {
	private String password;
	private String name;
	private String email;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}
	
	public NewUser(String userid, String password, String name, String email){
		super(userid.trim());
		this.password = password.trim();
		this.name = name.trim();
		this.email = email.trim();
	}
	
	public void createUser(){
		if(userExists()){
			System.out.println("User already Exists");
		}else{
			String insertUser = String.format("INSERT INTO users VALUES('%s','%s','%s','%s')", getUserid(), getPassword(), getName(), getEmail());
			db.changeQuery(insertUser);
			System.out.println("User created: \nName: " + getName() + "\nUser ID: " + getUserid());
		}
	}
	
	
	
}
