package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bdd.Bdd;


@WebServlet("/Activite")
public class Activite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Activite() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		  Bdd user= new Bdd();
		  request.setAttribute("activities", user.getactivities());
         	this.getServletContext().getRequestDispatcher("/WEB-INF/activite.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
