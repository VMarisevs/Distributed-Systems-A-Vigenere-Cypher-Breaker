package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatusHandler  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * temp
		 */
		//response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		
		
		Job[] jobs = Work.getStatus();
		/*
		for (int i = 0; i < jobs.length; i++){
			out.println("cypher text: " + jobs[i].getCypherText() + " Status: " + jobs[i].getStatus() + "<br/>");
		}*/
		
		//String message = new String("this is a passed message");
		
		request.setAttribute("jobs", jobs);
		request.getRequestDispatcher("status.jsp").forward(request, response);

		
	}


}
