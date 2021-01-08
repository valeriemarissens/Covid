package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.Bdd;
import beans.user;

/**
 * Servlet implementation class Modifier
 */

public class Modifier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Modifier() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableusers = new Bdd();
		
HttpSession session=request.getSession();
if(session.getAttribute("login")!=null && session!=null){

	String userlogin = request.getParameter("userlogin");
	
	request.setAttribute("user", tableusers.getuser(userlogin));

	this.getServletContext().getRequestDispatcher("/WEB-INF/modifier.jsp").forward(request, response);
	
}else{
	response.sendRedirect(request.getContextPath()+"/login.jsp");
}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableusers=new Bdd();
	
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
			String login = request.getParameter("userlogin");
			String lastname = request.getParameter("lastname");
			String firstname=request.getParameter("firstname");
			String birth=request.getParameter("birth");
			boolean hascovid=request.getParameter("hascovid") != null;
	
			if(login != null && lastname != null && firstname != null && birth != null  ) {
			user user = new user();
			user.setuserlogin(login);
			user.setlastname(lastname);
			user.setfirstname(firstname);
			user.setbirth(birth);
			user.setcovid(hascovid);
		
			
			
			
			
		
			tableusers.modifieruser(user);
	
		request.setAttribute("users", tableusers.getusers());
		
		
		
			this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}	else{
	//esponse.sendRedirect(request.getContextPath()+"/login.jsp");
		
		
}

} 
	}

}
