package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import beans.Activite;
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
		loadDatabase();
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
	
	private void updateAtRiskUsers() {
		Statement statement = null;
		ResultSet rs = null;
		loadDatabase();
		boolean available = true;
		
		try {
			statement = connexion.createStatement();
			String login = currentUser.getlogin();
			StringBuilder query = new StringBuilder();
			
			query.append("select login from attendances ");
			query.append("where login != '"+login+"'");
			query.append("and idActivity in (");
				query.append("select attendances.idActivity from users join attendances ");
				query.append("on users.login = attendances.login ");
				query.append("join activities on attendances.idActivity = activities.idActivity ");
				query.append("where users.login = '"+login+"' ");
				query.append("and CURRENT_DATE - activities.dateActivity <= 10");
			query.append(")");
			
			rs = statement.executeQuery(query.toString());
			
			// Boucle des login qui ont été aux mêmes activités dans les 10 derniers jours
			while(rs.next()) {
				String loginAtRisk = rs.getString("login");
				System.out.println("risque: "+loginAtRisk);
				for (User u : getusers()) {
					if (u.getlogin().equals(loginAtRisk)) {
						statement.executeUpdate("UPDATE users SET isatrisk = 1 WHERE users.login='"+loginAtRisk+"'");
						u.setrisk(true);
					}
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
	}
	
	public void editUser(String login, String nom, String prenom, String birth, boolean covidPositive) {
		Statement statement = null;
		loadDatabase();
		
		try {
			statement = connexion.createStatement();
			if ((login != currentUser.getlogin()) && loginAvailable(login)) {
				statement.executeUpdate("UPDATE users SET login = '"+login+"' WHERE users.login='"+login+"'");
				currentUser.setuserlogin(login);
			}
			if (nom != currentUser.getlastname()) {
				statement.executeUpdate("UPDATE users SET lastname = '"+nom+"' WHERE users.login='"+login+"'");
				currentUser.setlastname(nom);
			}
			if (prenom != currentUser.getfirstname()) {
				statement.executeUpdate("UPDATE users SET firstname = '"+prenom+"' WHERE users.login='"+login+"'");
				currentUser.setfirstname(prenom);
			}
			if (birth != currentUser.getbirth()) {
				statement.executeUpdate("UPDATE users SET birth = '"+birth+"' WHERE users.login='"+login+"'");
				currentUser.setbirth(birth);
			}
			boolean isAlreadyPositive = Boolean.parseBoolean(currentUser.getcovid());
			if (covidPositive && (!isAlreadyPositive)) {
				statement.executeUpdate("UPDATE users SET hascovid = 1 WHERE users.login='"+login+"'");
				currentUser.setcovid(covidPositive);
				this.updateAtRiskUsers();
			}
			if (!covidPositive && isAlreadyPositive) {
				currentUser.setcovid(covidPositive);
				statement.executeUpdate("UPDATE users SET hascovid = 0 WHERE users.login='"+login+"'");
			}
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
	 
	public void modifieruser(User user) {
		 loadDatabase();
		 try {
			java.sql.PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE users SET  lastname=? ,firstname=? ,birth=? , hascovid=?  WHERE login=?");
			
		
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
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM users WHERE login=?");
		
			preparedStatement.setString(1, login);
			preparedStatement.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	public void ajouteruser(User user) {
		 loadDatabase();
		 try {
			java.sql.PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO users(login,password, firstname, lastname, birth) VALUES(?,?, ?, ?, ?)");
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
	 
	public List<Activite> getactivities(){
			List<Activite> activities = new ArrayList<Activite>();
			Statement statement = null;
			ResultSet rs = null;
			
			loadDatabase();
			
			try {
				statement = connexion.createStatement();
				rs = statement.executeQuery("SELECT * FROM activities");
				
			while(rs.next()) {
					int id= rs.getInt("idActivity");
					String date = rs.getString("dateActivity");
					String heuredebut = rs.getString("startHour");
					String heurefin = rs.getString("endHour");
					String name = rs.getString("name");
				    int lieu=rs.getInt("idPlace");
				    
				   
				Activite activite = new Activite();
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
	 	 
	public void ajouteractivity(Activite activite ) {
		 loadDatabase();
		 try {
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement("INSERT INTO activities(idActivity,dateActivity,startHour, endHour, name) VALUES(?,?, ?, ?, ?)");
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
			PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement("DELETE FROM activities WHERE idActivity=?");
		
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 public Activite getactivite(int id) {
		 Activite  activite = new Activite();
		 loadDatabase();
		 try {
			 PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT * FROM activities WHERE idActivity=?");
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

	 public void modifieractivite(Activite activite) {
			 loadDatabase();
			 try {
				PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement("UPDATE activities SET  dateActivity=? ,startHour=? ,endHour=? , name=?  WHERE idActivity=?");
				
			
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
	 
	 public User getuser(String login) {
		 User user = new User();
		 loadDatabase();
		 try {
			 java.sql.PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM users WHERE login=?");
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

}