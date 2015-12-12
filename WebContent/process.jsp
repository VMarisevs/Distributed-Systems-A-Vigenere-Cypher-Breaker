<%@ include file="includes/header.jsp" %>

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
<script>var wait=setTimeout("document.frmCracker.submit();", 5000);</script>
	<form name="frmCracker">
		<input name="frmMaxKeyLength" type="hidden" value="<%=maxKeyLength %>">
		<input name="frmCypherText" type="hidden" value="<%=cypherText %>">
		<input name="frmStatus" type="hidden" value="<%=jobNumber %>">
	</form>
	
<%@ include file="includes/footer.jsp" %>