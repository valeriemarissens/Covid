package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bdd.Bdd;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getServletContext().getRequestDispatcher( "/WEB-INF/edit.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nouveauLogin = request.getParameter("nouveauLogin");
		String nouveauNom = request.getParameter("nouveauNom");
		String nouveauPrenom = request.getParameter("nouveauPrenom");
		String nouvelleDateNaissance = request.getParameter("nouvelleDateNaissance");
		String positif = request.getParameter("positif");
		boolean positifBool = (positif == null) ? false : true;
		
		System.out.println(nouveauLogin+", "+nouveauNom+", "+nouveauPrenom+", "+ nouvelleDateNaissance+", "+positifBool+" == "+positif);
		
		if ((nouveauLogin != "") && (nouveauNom != "") && (nouveauPrenom != "") && (nouvelleDateNaissance != "") && (positif != "")) {
			if (areCorrect(nouveauLogin, nouveauNom, nouveauPrenom, nouvelleDateNaissance)) {
				Bdd bdd = Bdd.getInstance();
				bdd.editUser(nouveauLogin, nouveauNom, nouveauPrenom, nouvelleDateNaissance, positifBool);
				this.getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
			}
			else {
				String erreur="Veuillez complÈter correctement tous les champs";
	        	request.setAttribute("erreur", erreur);    
	           	this.getServletContext().getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
			}
		}
		else{
        	String erreur="Veuillez complÈter tous les champs";
        	request.setAttribute("erreur", erreur);    
           	this.getServletContext().getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
        }
	}
	
	private boolean areCorrect(String login, String nom, String prenom, String birth) {
		String regexLogin = "[A-Za-z0-9_]+";
        String regexNom = "[A-Z][a-zÈ‡Ô‰Îˆ¸Ó‚ÍÙ˚]+";
		
		boolean loginCorrect = login.matches(regexLogin);
		boolean nomCorrect = nom.matches(regexNom);
		boolean prenomCorrect = prenom.matches(regexNom);
		boolean birthCorrect = true;
		
		System.out.println(loginCorrect +"-"+ nomCorrect +"-"+ prenomCorrect);
		
		return loginCorrect && nomCorrect && prenomCorrect && birthCorrect;
	}

}
