package ie.gmit.sw;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CrackerHandler extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long taskId;
		int maxKeyLength = Integer.parseInt(req.getParameter("frmMaxKeyLength"));
		String cypherText = req.getParameter("frmCypherText");
		String taskNumber = req.getParameter("frmStatus");
		String result = new String();
		
		/* These attributes are for both jsp pages*/
		req.setAttribute("cypherText", cypherText);
		req.setAttribute("maxKeyLength", maxKeyLength);
		req.setAttribute("inQueueSize", Work.inQueueSize());
		req.setAttribute("outQueueSize", Work.outQueueSize());
		
		/*
		 *  if form was posted, from main page task is not defined
		 *  In this case creating a job, or checking if this job is done
		 */
		if (taskNumber == null){
			// generating new job id
			taskId = System.currentTimeMillis();
			// creating new job
			Work.add(new Job(taskId,null,cypherText,maxKeyLength));
			// setting attribute of new generated task
			req.setAttribute("jobNumber", taskId);
			
			req.getRequestDispatcher("process.jsp").forward(req, resp);
			
		} else{
			taskId = Long.parseLong(taskNumber);
			req.setAttribute("jobNumber", taskId);

			
			if (Work.contains(taskId)){
				// if job is done, load ready.jsp with result attribute
				result = Work.get(taskId);

				req.setAttribute("plainText", result);
				
				req.getRequestDispatcher("ready.jsp").forward(req, resp);
				
			} else{
				/*
				 *  if job is not done, and job id is generated
				 *  just load process page again and keep doing that
				 *  until job is done.
				 */
				req.getRequestDispatcher("process.jsp").forward(req, resp);
			}

		}
				
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
	

}
