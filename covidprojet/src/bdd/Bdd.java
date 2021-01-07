package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.User;

public class Bdd {

	private Connection connexion;
	/**
	 * Singleton : instance partagée.
	 */
	private static final Bdd instance = new Bdd();
	private User currentUser;
	
	private Bdd() {
		currentUser = new User();
	}
	
	private void loadDatabase() {
	    // Chargement du driver
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
	 	
	/**
	 * Trouve un user ou admin.
	 * 
	 * @param login login de l'utilisateur.
	 * @param password mdp.
	 * @param isAdmin vrai s'il est administrateur.
	 * @return
	 */
	public boolean findUser (String login, String password, boolean isAdmin){
		Statement statement;
		ResultSet rs;
		loadDatabase();
		boolean trouve = false;
		int adminInt = isAdmin ? 1 : 0;
		
		try {
			statement = connexion.createStatement();
			rs = statement.executeQuery("SELECT * FROM users where login='"+login+"' and password='"+password+"' and isadmin='"+adminInt+"'");
			
			while(rs.next()) {
				if((rs.getString("login") != "") && (rs.getString("password") != "")){
					trouve = true;
					this.setCurrentUser(rs);
				}
				else{
					trouve = false;
			}
		}
		connexion.close();	 
		 
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return trouve;
	}
	
	/**
	 * Met à jour l'utilisateur qui est en ce moment dans le site.
	 * 
	 * @param rs résultat de la requête sql.
	 */
	private void setCurrentUser(ResultSet rs) {			
		try {
			if (currentUser == null)
				currentUser = new User();
			
			String login = rs.getString("login");
			String lastname = rs.getString("lastname");
			String firstname = rs.getString("firstname");
			String birth = rs.getString("birth");
			boolean hascovid = rs.getBoolean("hascovid");
			boolean isatrisk = rs.getBoolean("isatrisk");
			
			currentUser.setuserlogin(login);
			currentUser.setlastname(lastname);
			currentUser.setfirstname(firstname);
			currentUser.setbirth(birth);
			currentUser.setcovid(hascovid);
			currentUser.setrisk(isatrisk);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return l'utilisateur qui est actuellement dans le site.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
			
	// retourner la liste des utilisateurs 	
	public List<User> getusers(){
		List<User> users = new ArrayList<User>();
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
			
				User user  = new User();
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
            } catch (SQLException ignore) {}
		}
		
		return users;
	}
	
	private boolean loginAvailable(String login) {
		Statement statement = null;
		ResultSet rs = null;
		loadDatabase();
		boolean available = true;
		
		try {
			statement = connexion.createStatement();
			rs = statement.executeQuery("SELECT * FROM users");
			
		while(rs.next()) {
				String loginUsed = rs.getString("login");
				if (login.equals(loginUsed)) {
					available = false;
				}				
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            }
			catch (SQLException ignore) {}
		}
		
		return available;
	}
	
	public void editUser(String login, String nom, String prenom, String birth, boolean covidPositive) {
		Statement statement = null;
		loadDatabase();
		
		try {
			statement = connexion.createStatement();
			if ((login != "") && loginAvailable(login)) {
				statement.executeUpdate("UPDATE 'users' SET login = '"+login+"' WHERE 'users'.'login'='"+login+"'");
			}
			if (nom != "") {
				statement.executeUpdate("UPDATE 'users' SET lastname = '"+nom+"' WHERE 'users'.'login'='"+login+"'");
			}
			if (prenom != "") {
				statement.executeUpdate("UPDATE 'users' SET firstname = '"+prenom+"' WHERE 'users'.'login'='"+login+"'");
			}
			if (birth != "") {
				statement.executeUpdate("UPDATE 'users' SET birth = '"+birth+"' WHERE 'users'.'login'='"+login+"'");
			}
			if (covidPositive) {
				statement.executeUpdate("UPDATE 'users' SET hascovid = 1 WHERE 'users'.'login'='"+login+"'");
				// todo : il faut mettre tous les autres à risque
			}
			
			
			connexion.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
                if (statement != null)
                    statement.close();
                if (connexion != null)
                    connexion.close();
            }
			catch (SQLException ignore) {}
		}
	} 
	
	public static Bdd getInstance() {
		return instance;
	}
	 
}