<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@ page import="ie.gmit.sw.Job" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="1">
<title>GMIT - Distributed Systems - Vigenere Cypher Breaker</title>

	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

</head>
<body>

<%
	// getting array of jobs from Status Handler HttpServlet
	Job[] jobs = (Job[])request.getAttribute("jobs");

%>

	<div class="container">
		 <div class="jumbotron">
		    <h2>This is a status page</h2>
		    <p>This page is refreshing automatically every second, to see the status of whole system. It shows how many users sends the requests to break the cypher text, and their status. Note that this systems is running asynchronous requests to RMI object and which is faster to break that comes first</p>
		    <p>Note that I am not using full encapsulation for Jobs, in this case I need to update instance of the job, and job in array will be up to date.</p> 
		  </div>
		  
		  <div class="row">
			  <% for (int i = 0; i < jobs.length; i++){ %>
			  	
			  	<%
				  	String message,colour;
		  			switch (jobs[i].getStatus()){
		  				case 'p':
		  					message = new String("pending");
		  					// red color
		  					colour = new String("panel-danger");		  					
		  					break;
		  				case 'o':
		  					message = new String("processing");
		  					// yellow
		  					colour = new String("panel-warning");
		  					break;
		  				case 'r':
		  					message = new String("ready");
		  					// green
		  					colour = new String("panel-success");
		  					break;
		  				default:
		  					message = new String("Error!!!");
		  					colour = new String("");
		  					break;
		  			}	  			
			  	%>
			  	<div class="col-sm-4 panel <%=colour %>">
			  		<div class="panel-heading" style=" font-size: 30px;">
			  			<strong>Job: <%=jobs[i].getJobId() %></strong><br/>
			  			Status: <%=message %>
			  		</div>
			  		
			  		<div class="panel-body">
			  			<strong>Cypher text:</strong>
			  				<%=jobs[i].getCypherText() %>
			  		</div>
			  		
			  		<% // if there is a decrypted result then print it out
			  			if (jobs[i].getPlainText() != null){
			  				%>
			  				<div class="panel-footer">
			  					<strong>Decrypted text:</strong>
			  					<%=jobs[i].getPlainText() %>
			  				</div>
			  				
			  				
			  				<%
			  			}
		  			%>
			  		
			  		
			  		
			  	</div>
			  	
			  <% } %>
		  </div>
	</div>
</body>
</html>