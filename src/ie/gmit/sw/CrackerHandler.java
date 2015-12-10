package ie.gmit.sw;

import java.io.*;
import java.rmi.Naming;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import ie.gmit.rm.VigenereBreaker;

public class CrackerHandler extends HttpServlet {
	
	private String remoteHost = null;
	private static long jobNumber = 0;
	private Map<Long,String> jobMap;
	String cypherText;
	int maxKeyLength;
	
	public CrackerHandler() {
		jobMap = new HashMap<Long,String>();
	}
	
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		
		maxKeyLength = Integer.parseInt(req.getParameter("frmMaxKeyLength"));
		cypherText = req.getParameter("frmCypherText");
		String taskNumber = req.getParameter("frmStatus");
		String result = new String();
		

		/*
		 *  if form was posted, from main page task is not defined
		 *  In this case creating a job, or checking if this job is done
		 */
		if (taskNumber == null){
			
			jobNumber = System.currentTimeMillis();
			taskNumber = new String("T" + jobNumber);
			
			new Thread(){
			    public void run(){
			    	try {
						//String result = new String(getVigenereBreaker().decrypt("CHZQDRLTKAXFWOXGSDVYBXENACQNUJIHMXP", 4));
						String result = new String(getVigenereBreaker().decrypt(cypherText, maxKeyLength));
						jobMap.put(jobNumber, result);
						
					} catch (Exception e) {}
			    }
			  }.start();
			
		}
		
		if (jobMap.containsKey(jobNumber)){
			if (jobMap.get(jobNumber).length() >0){
				result = new String(jobMap.get(jobNumber));
			}
		}
		
		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");		
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");		
		
		if (result.length() > 0);
			out.print("<h1>" + result + "</h1>");
		
		out.print("RMI Server is located at " + remoteHost);
		out.print("<P>Maximum Key Length: " + maxKeyLength);		
		out.print("<P>CypherText: " + cypherText);
		out.print("<P>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print("<P>Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");				
		
		out.print("<form name=\"frmCracker\">");
		out.print("<input name=\"frmMaxKeyLength\" type=\"hidden\" value=\"" + maxKeyLength + "\">");
		out.print("<input name=\"frmCypherText\" type=\"hidden\" value=\"" + cypherText + "\">");
		out.print("<input name=\"frmStatus\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmCracker.submit();\", 5000);");
		out.print("</script>");
				
		/*-----------------------------------------------------------------------     
		 *  Next Steps: just in case you removed the above....
		 *-----------------------------------------------------------------------
		 * 1) Generate a big random number to use a a job number, or just increment a static long variable declared at a class level, e.g. jobNumber
		 * 2) Create some type of a "message request" object from the maxKeyLength, cypherText and jobNumber.
		 * 3) Add the "message request" object to a LinkedList or BlockingQueue (the IN-queue)
		 * 4) Return the jobNumber to the client web browser with a wait interval using <meta http-equiv="refresh" content="10">. The content="10" will wait for 10s.
		 * 4) Have some process check the LinkedList or BlockingQueue for "message requests" 
		 * 5) Poll a "message request" from the front of the queue and make an RMI call to the Vigenere Cypher Service
		 * 6) Get the result and add to a Map (the OUT-queue) using the jobNumber and the key and the result as a value
		 * 7) Return the cyphertext to the client next time a request for the jobNumber is received and delete the key / value pair from the Map.
		 */
		
		//You can use this method to implement the functionality of an RMI client
		
		//
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
	
	private VigenereBreaker getVigenereBreaker() throws Exception{
		VigenereBreaker vigenereBreaker = (VigenereBreaker) 
				Naming.lookup("rmi://localhost:1099/VigenereBreakerService");
		
		return vigenereBreaker;
	}
}
