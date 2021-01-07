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
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setHeader("cache-control","no-cache, no-store,must-revalidate");
			response.setHeader("Expires","0");

			String login = request.getParameter("login");
	        String password = request.getParameter("password");
	        Bdd bdd = Bdd.getInstance();
	        boolean trouveAdmin = bdd.findUser(login, password, true);
	        boolean trouveUser = bdd.findUser(login, password, false);
	        
	        if (trouveAdmin || trouveUser){
	        	   HttpSession session = request.getSession();
	        	   session.setAttribute("login", login);
	               request.setAttribute("users", bdd.getusers());
	               
	               // Redirige vers l'espace de l'administrateur
	               if (trouveAdmin) {
	            	   this.getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	               }
	               // Redirige vers l'espace de l'utilisateur normal
	               else {
	            	   session.setAttribute("user", bdd.getCurrentUser());
	            	   this.getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
	               }
	        }
	        else{
	        	String erreur="login ou mot de passe incorrect";
	        	request.setAttribute("erreur", erreur);
	    
	           	this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	        }
	}

}
