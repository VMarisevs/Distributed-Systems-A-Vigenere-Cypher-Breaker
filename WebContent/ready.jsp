<%@ include file="includes/header.jsp" %>

<%
	String cypherText = request.getAttribute("cypherText").toString();
	String plainText = request.getAttribute("plainText").toString();
	int maxKeyLength = (int)request.getAttribute("maxKeyLength");
	long jobNumber = (long)request.getAttribute("jobNumber");
%>
	
<div class="container">
	<div class="panel panel-success">
	    <div class="panel-heading" style=" font-size: 30px;">
	    	<strong>Job#: <%=jobNumber %> is ready</strong>
	    </div>
	    <div class="panel-body">
	    	<h5><strong>Maximum Key Length: <%=maxKeyLength %></strong></h5>
	    	<strong>Cypher text:</strong>
	    	<p><%=cypherText %></p>
	    </div>
	    <div class="panel-footer">
	    	<strong>Decrypted Text:</strong>
	    	<p><%=plainText %></p>
	    </div>
	</div>
</div>
	
<%@ include file="includes/footer.jsp" %>