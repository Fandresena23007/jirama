<%@ page import="function.*,jirama.*" %>

<%
	GetSet g = new GetSet();
	out.print(g.createAllInClass(new PrelevAnnuler()));
%>