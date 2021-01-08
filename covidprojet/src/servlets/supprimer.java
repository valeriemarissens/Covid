package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.Bdd;


@WebServlet("/supprimer")
public class supprimer extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public supprimer() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableusers = Bdd.getInstance();
		
       HttpSession session=request.getSession();
   if(session.getAttribute("login")!=null && session!=null){
	String login =request.getParameter("userlogin");
	tableusers.supprimeruser(login);
	request.setAttribute("users", tableusers.getusers());
	this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	
	}else{
		response.sendRedirect(request.getContextPath()+"/connexion.jsp");
	}
	}

	
	

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
}
}
