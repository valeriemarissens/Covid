package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bdd.Bdd;


@WebServlet("/Retour")
public class Retour extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public Retour() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Bdd tablesusers = new Bdd();
		request.setAttribute("users", tablesusers.getusers());
	 	this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
