package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.Bdd;
import beans.activite;
import beans.user;

/**
 * Servlet implementation class modifactivite
 */
@WebServlet("/modifactivite")
public class modifactivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modifactivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableactivities = new Bdd();
		
HttpSession session=request.getSession();
if(session.getAttribute("login")!=null && session!=null){

	int id  = Integer.parseInt(request.getParameter("id"));
	
	request.setAttribute("activite", tableactivities.getactivite(id));

	this.getServletContext().getRequestDispatcher("/WEB-INF/modifactivite.jsp").forward(request, response);
	
}else{
	response.sendRedirect(request.getContextPath()+"/login.jsp");
}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableactivities =new Bdd();
	
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
			String date= request.getParameter("date");
			String hdebut = request.getParameter("hdebut");
			String hfin=request.getParameter("hfin");
			String name =request.getParameter("name");
			
	
			if(date != null && hdebut != null && hfin != null && name != null ){
				
			activite activite = new activite();
			
		activite.setdate(date);
		activite.sethdebut(hdebut);
		activite.sethfin(hfin);
		activite.setname(name);
		
			
		
			
		
			tableactivities.modifieractivite(activite);
	
		request.setAttribute("activities", tableactivities.getactivities());
		
		
		
			this.getServletContext().getRequestDispatcher("/WEB-INF/activite.jsp").forward(request, response);
	
	}
			
		
		
		
		
		}else{
	response.sendRedirect(request.getContextPath()+"/login.jsp");
		
		
}

} 
	}
	

