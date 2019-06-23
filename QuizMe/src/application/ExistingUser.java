package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExistingUser extends User {
	
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}
	
	public ExistingUser(String userid, String password){
		super(userid);
		this.password = password.trim();
	}
	
	public boolean verifyPassword(){
		if(userExists()){
			String verify = "Select * from users where userid=" + "'" + getUserid() + "'";
			ResultSet rs = db.runQuery(verify);
			try {
				rs.next();
				System.out.println("Password: " + rs.getString("password"));
				
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Verify Password query couldn't be run");
				return false;
			}
		}else{
			System.out.println("User doesn't Exist so please make an account");
			return false;
		}
	}
}
