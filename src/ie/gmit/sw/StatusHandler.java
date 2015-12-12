package ie.gmit.sw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class StatusHandler  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Job[] jobs = Work.getStatus();
		request.setAttribute("jobs", jobs);
		request.getRequestDispatcher("status.jsp").forward(request, response);

	}


}
