package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.user;






//import beans.user;

public class Bdd {

	private Connection connexion;
	

	 private void loadDatabase() {
	        // Chargement du driver
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	        }

	        try {
	            connexion = DriverManager.getConnection("jdbc:mysql://localhost/covid", "root", "");
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		public boolean admin (String login, String password){
			Statement statement=null;
			ResultSet rs=null;
			 loadDatabase();
			 boolean trouve =false;
				try {
					statement = connexion.createStatement();
					rs = statement.executeQuery("SELECT * FROM users where login='"+login+"' and password='"+password+"'");
					
				while(rs.next()) {
					if(rs.getString("login")!="" && rs.getString("password")!=""){
						trouve=true;
					}else{
						trouve=false;
					    
					}
				}
			connexion.close();	 
			 
		}catch(SQLException e){
			e.printStackTrace();
		}
				return trouve;
		}
		
		
		
		
		// retourner la liste des utilisateurs 
	

		
		public List<user> getusers(){
		List<user> users = new ArrayList<user>();
		Statement statement = null;
		ResultSet rs = null;
		
		loadDatabase();
		
		try {
			statement = connexion.createStatement();
			rs = statement.executeQuery("SELECT * FROM users");
			
		while(rs.next()) {
				String login = rs.getString("login");
				String lastname = rs.getString("lastname");
				String firstname = rs.getString("firstname");
				String birth = rs.getString("birth");
			//	int hascovid = rs.getInt("hascovid");
				int isatrisk = rs.getInt("isatrisk");
			
				
				user user  = new user();
				user.setuserlogin(login);
				user.setlastname(lastname);
				user.setfirstname(firstname);
				user.setbirth(birth);
			//	user.setcovid(hascovid);
				user.setrisk(isatrisk);
				
				
				users.add(user);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException ignore) {
            }
		}
		
		return users;
	}
	
	
	
	

	 
	
	
	

		 
	 
}