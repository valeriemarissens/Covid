package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.activite;
import beans.user;



public class Bdd {

	private Connection connexion;
	

	private void loadDatabase() {
	   
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	    }
	    catch (ClassNotFoundException e) {}
	
	    try {
	        connexion = DriverManager.getConnection("jdbc:mysql://localhost/covid", "root", "");
	        
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
	 
	public boolean admin (String login, String password){
		Statement statement;
		ResultSet rs;
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

				boolean hascovid = rs.getBoolean("hascovid");
				boolean isatrisk = rs.getBoolean("isatrisk");

			
				user user  = new user();
				user.setuserlogin(login);
				user.setlastname(lastname);
				user.setfirstname(firstname);
				user.setbirth(birth);
				user.setcovid(hascovid);

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
	 public user getuser(String login) {
	 user user = new user();
	 loadDatabase();
	 try {
		 PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM users WHERE login=?");
		 preparedStatement.setString(1, login);
		 ResultSet rs = preparedStatement.executeQuery();
		if(rs.next()) {
			
			
		
			user.setuserlogin(rs.getString("login"));
			user.setlastname(rs.getString("lastname"));
			user.setfirstname(rs.getString("firstname"));
			user.setbirth(rs.getString("birth"));
			user.setcovid(rs.getBoolean("hascovid"));

			user.setrisk(rs.getBoolean("isatrisk"));	
			
			
		}
		
		
	} catch (Exception e) {
	}
	 return user;
	 
}
	 public void modifieruser(user user) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE users SET  lastname=? ,firstname=? ,birth=? , hascovid=?  WHERE login=?");
			
		
			preparedStatement.setString(1, user.getlastname());
			preparedStatement.setString(2, user.getfirstname());
			preparedStatement.setString(3, user.getbirth());
			preparedStatement.setString(4, user.getcovid());
			preparedStatement.setString(5, user.getlogin());
		
			
			
			preparedStatement.executeUpdate();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 

	 public void supprimeruser(String login) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM users WHERE login=?");
		
			preparedStatement.setString(1, login);
			preparedStatement.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

	 
	 public void ajouteruser(user user) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO users(login,password, firstname, lastname, birth) VALUES(?,?, ?, ?, ?)");
			preparedStatement.setString(1, user.getlogin());
			preparedStatement.setString(2, user.getpassword());
			preparedStatement.setString(3, user.getfirstname());
			preparedStatement.setString(4, user.getlastname());
			preparedStatement.setString(5, user.getbirth());
		
		
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 

	 public List<activite> getactivities(){
			List<activite> activities = new ArrayList<activite>();
			Statement statement = null;
			ResultSet rs = null;
			
			loadDatabase();
			
			try {
				statement = connexion.createStatement();
				rs = statement.executeQuery("SELECT * FROM activities");
				
			while(rs.next()) {
					int id= rs.getInt("idActivity");
					String date = rs.getString("dateActivity");
					String heuredebut = rs.getString("starthour");
					String heurefin = rs.getString("endhour");
					String name = rs.getString("name");
				    int lieu=rs.getInt("idPlace");
				    
				   
				activite activite = new activite();
					activite.setid(id);
					activite.setdate(date);
					activite.sethdebut(heuredebut);
					activite.sethfin(heurefin);
					activite.setname(name);
				   activite.setlieu(lieu);
					
					
					activities.add(activite);
					
					
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
			
			return activities;
		}	 
	 
	 
	 public void ajouteractivity(activite activite ) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO activities(idActivity,dateActivity,startHour, endHour, name) VALUES(?,?, ?, ?, ?)");
			preparedStatement.setInt(1,activite.getid());
			preparedStatement.setString(2, activite.getdate());
			preparedStatement.setString(3, activite.gethdebut());
			preparedStatement.setString(4, activite.gethfin());
			preparedStatement.setString(5, activite.getname());
		
		
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 

	 public void supprimeractivitie(int id) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM activities WHERE idActivity=?");
		
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 public activite getactivite(int id) {
		 activite  activite = new activite();
		 loadDatabase();
		 try {
			 PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM activities WHERE idActivity=?");
			 preparedStatement.setInt(1, id);
			 ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				
				
			activite.setid(rs.getInt("idActivity"));
			activite.setdate(rs.getString("dateActivity"));
			activite.sethdebut(rs.getString("startHour"));
		    activite.sethfin(rs.getString("endHour"));
		    activite.setname(rs.getString("name"));
		    
				
				
			}
			
			
		} catch (Exception e) {
		}
		 return  activite;
	 }
		 public void modifieractivite(activite activite) {
			 loadDatabase();
			 try {
				PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE activities SET  dateActivity=? ,startHour=? ,endHour=? , name=?  WHERE idActivity=?");
				
			
				preparedStatement.setString(1, activite.getdate());
				preparedStatement.setString(2, activite.gethdebut());
				preparedStatement.setString(3, activite.gethfin());
				preparedStatement.setString(4, activite.getname());
				preparedStatement.setInt(5, activite.getid());
			
				
					
				preparedStatement.executeUpdate();
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		 }

}