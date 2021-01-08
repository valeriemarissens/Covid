package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

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
	private String msgErreur = ""; 
       
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
		
		if ((nouveauLogin != "") && (nouveauNom != "") && (nouveauPrenom != "") && (nouvelleDateNaissance != "") && (positif != "")) {
			if (areCorrect(nouveauLogin, nouveauNom, nouveauPrenom, nouvelleDateNaissance)) {
				Bdd bdd = Bdd.getInstance();
				bdd.editUser(nouveauLogin, nouveauNom, nouveauPrenom, nouvelleDateNaissance, positifBool);
				this.getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
			}
			else {
				String erreur="Veuillez complÈter correctement tous les champs : "+msgErreur;
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
	
	/**
	 * VÈrifie, avec l'utilisation de regexp, que les champs mis sont corrects.
	 * 
	 * @param login
	 * @param nom
	 * @param prenom
	 * @param birth
	 * @return vrai si les champs sont tous corrects.
	 */
	private boolean areCorrect(String login, String nom, String prenom, String birth) {
		String regexLogin = "[A-Za-z0-9_]+";
        String regexNom = "[A-Z][a-zÈ‡Ô‰Îˆ¸Ó‚ÍÙ˚]+";
		
		boolean loginCorrect = login.matches(regexLogin);
		boolean nomCorrect = nom.matches(regexNom);
		boolean prenomCorrect = prenom.matches(regexNom);
		
		Date birthDate = Date.valueOf(birth);
        java.util.Date today = Calendar.getInstance().getTime();
        boolean birthCorrect = !birthDate.after(today);
        
        if (!loginCorrect) msgErreur = "format de login ";
        if (!nomCorrect) msgErreur += "format du nom ";
        if (!prenomCorrect) msgErreur += "format du prÈnom ";
        if (!birthCorrect) msgErreur += "date de naissance ";
		
		return loginCorrect && nomCorrect && prenomCorrect && birthCorrect;
	}

}
