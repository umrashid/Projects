package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	Database db = new Database();
	
	public User(String userid){
		this.userid = userid;
	}
	
	public boolean userExists(){
		String userCheck = "Select * from users where userid=" + "'" +this.userid + "'";
		ResultSet rs = db.runQuery(userCheck);
		try {
			rs.last();
			if(rs.getRow() == 1){
				System.out.println("User Found");
				return true;
			}else{
				System.out.println("User Not Found");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Userexists query couldn't be run");
			return false;
		}
	}
}
