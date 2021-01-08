package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import bdd.Bdd;
import beans.User;




public class Adduser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Adduser() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
		this.getServletContext().getRequestDispatcher("/WEB-INF/add.jsp").forward(request, response);

	}else {
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
		Bdd tableusers = new Bdd();
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String birth = request.getParameter("birth");
	
		
		if(login != null && firstname != null && lastname != null && birth != null && password != null  ) {
		User user = new User();
		User.setuserlogin(login);
		User.setpassword(password);
		User.setfirstname(firstname);
		User.setlastname(lastname);
		User.setbirth(birth);
	
		
		tableusers.ajouteruser(user);
		request.setAttribute("users", tableusers.getusers());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

	}
		else{
			response.sendRedirect(request.getContextPath()+"login.jsp");
		}

}}