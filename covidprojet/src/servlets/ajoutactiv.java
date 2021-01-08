package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bdd.Bdd;
import beans.Activite;
import beans.User;


@WebServlet("/ajoutactiv")
public class ajoutactiv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ajoutactiv() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutactiv.jsp").forward(request, response);

	}else {
		
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		HttpSession session=request.getSession();
		if(session.getAttribute("login")!=null && session!=null){
		Bdd tableactivities = Bdd.getInstance();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String date = request.getParameter("date");
		String heuredebut = request.getParameter("hdebut");
		String heurefin = request.getParameter("hfin");
		String name = request.getParameter("name");
	
		
		if(  date != null && heuredebut != null && heurefin != null && name != null  ) {
		Activite activite = new Activite();
		activite.setid(id);
		activite.setdate(date);
		activite.sethdebut(heuredebut);
		activite.sethfin(heurefin);
		activite.setname(name);
	
		
		tableactivities.ajouteractivity(activite);
		request.setAttribute("activities", tableactivities.getactivities());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/activite.jsp").forward(request, response);

	}
	}}
