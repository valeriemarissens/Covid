package beans;

public class user {
	
	private	String login;
	private	String lastname ;
	private String firstname ;
	private String birth ;
	private int hascovid ;
	private int isatrisk ;
	
	
	
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
	
	public int getcovid() {
		return hascovid;
	}
	public void setcovid(int hascovid) {
		this.hascovid = hascovid;
	}
	public int risk() {
		return isatrisk;
	}
	public void setrisk(int isatrisk) {
	this.isatrisk = isatrisk;
		
	}

	
	

}
