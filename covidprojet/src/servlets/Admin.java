package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.Bdd;

/**
 * Servlet implementation class Admin
 */

public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession session = request.getSession( false ); 
	        

		 if ( session != null )
		 {
		 session.invalidate(); 

		
		this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		
	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setHeader("cache-control","no-cache, no-store,must-revalidate");
			response.setHeader("Expires","0");
				
				String login = request.getParameter("login");
		        String password = request.getParameter("password");
		        Bdd user= Bdd.getInstance();
		        boolean trouve;
		        trouve= user.findUser(login,password, true);
		        if(trouve){
		        	   HttpSession session = request.getSession();
		        	   session.setAttribute("login", login);
		               request.setAttribute("users", user.getusers());
		           	this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		        }
		        else{
		        	String erreur="login ou mot de passe incorrect";
		        	request.setAttribute("erreur", erreur);
		    
		           	this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		        }
				


	}

}
