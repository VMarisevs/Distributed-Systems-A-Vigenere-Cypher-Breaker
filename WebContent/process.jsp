<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>GMIT - Distributed Systems - Vigenere Cypher Breaker</title>

	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


	<script>var wait=setTimeout("document.frmCracker.submit();", 5000);</script>
</head>

<body>
<%
	String cypherText = request.getAttribute("cypherText").toString();
	int maxKeyLength = (int)request.getAttribute("maxKeyLength");
	long jobNumber = (long)request.getAttribute("jobNumber");
%>

	<div class="container">
		<div class="panel panel-warning">
		    <div class="panel-heading" style=" font-size: 30px;">
		    	<strong>Processing request for Job#: <%=jobNumber %></strong>
		    </div>
		    <div class="panel-body">
		    	<h5><strong>Maximum Key Length: <%=maxKeyLength %></strong></h5>
		    	<strong>Cypher text:</strong>
		    	<p><%=cypherText %></p>
		    </div>
		</div>
	</div>

<% // resubmitting form, to keep user up to date with information %>
	<form name="frmCracker">
		<input name="frmMaxKeyLength" type="hidden" value="<%=maxKeyLength %>">
		<input name="frmCypherText" type="hidden" value="<%=cypherText %>">
		<input name="frmStatus" type="hidden" value="<%=jobNumber %>">
	</form>
	
	
</body>
</html>