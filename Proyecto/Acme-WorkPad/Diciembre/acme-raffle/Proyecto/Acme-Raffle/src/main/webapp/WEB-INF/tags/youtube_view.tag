<!-- 
	author: jjvalle
	version: 1.0
 -->

<%@tag import="java.util.Collection"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.LinkedList"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@tag import="java.util.Date"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.DomainEntity"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@tag language="java" body-content="scriptless" %>

<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="title" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="width" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="heigth" required="false" rtexprvalue="true" type="java.lang.String" %>

<%
	int w = 420, h = 315;
	if(width != null) {
		w = Integer.valueOf(width);
	}
	
	if(heigth != null) {
		h = Integer.valueOf(heigth);
	}
	
	if(title != null) {
%>
	<h1><%=title %></h1>
<%
	}
%>

<%
	if(url instanceof Collection) {
%>
<%
	Collection colletion = (Collection) url;
	for(Object o : colletion) {
%>
	<%=String.format("<iframe width='%d' height='%d' src='https://www.youtube.com/embed/%s'></iframe><br />", w, h, o.toString().replace("https://www.youtube.com/watch?v=", "")) %>
<%
%>
<%
	} } else {
%>
	<%=String.format("<iframe width='%d' height='%d' src='https://www.youtube.com/embed/%s'></iframe><br />", w, h, url.toString().replace("https://www.youtube.com/watch?v=", "")) %>
<%
	}
%>