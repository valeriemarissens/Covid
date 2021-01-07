package beans;

public class User {
	
	private	String login;
	private	String lastname ;
	private String firstname ;
	private String birth ;
	private boolean hascovid ;
	private boolean isatrisk ;
	
	
	public String getlogin() {
		return login;
	}
	
	public void setuserlogin(String login) {
		this.login = login;
	}
	
	public String getlastname() {
		return lastname;
	}
	
	public void setlastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getfirstname() {
		return firstname;
	}
	
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	
	
	public String getbirth() {
		return birth;
	}
	
	public void setbirth(String birth) {
		this.birth = birth;
	}
	
	public String getcovid() {
		return String.valueOf(hascovid);
	}
	
	public void setcovid(boolean hascovid) {
		this.hascovid = hascovid;
	}
	
	public String getatrisk() {
		return String.valueOf(isatrisk);
	}
	
	public void setrisk(boolean isatrisk) {
		this.isatrisk = isatrisk;	
	}

	
	

}
