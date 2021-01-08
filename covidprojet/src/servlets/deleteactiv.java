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
 * Servlet implementation class deleteactiv
 */
@WebServlet("/deleteactiv")
public class deleteactiv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteactiv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("cache-control","no-cache, no-store,must-revalidate");
		response.setHeader("Expires","0");
		Bdd tableactivities = Bdd.getInstance();
		
       HttpSession session=request.getSession();
   if(session.getAttribute("login")!=null && session!=null){
	int id = Integer.parseInt(request.getParameter("id"));
	tableactivities.supprimeractivitie(id);
	request.setAttribute("activities", tableactivities.getactivities());
	this.getServletContext().getRequestDispatcher("/WEB-INF/activite.jsp").forward(request, response);
	
	}else{
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	}

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
