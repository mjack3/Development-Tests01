<%@tag import="java.lang.reflect.Field"%>
<%@ tag language="java" body-content="empty" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute name="entity" required="true" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="field" required="true" rtexprvalue="true" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="showVal" required="false" rtexprvalue="true" type="java.lang.Boolean" %>
<%@ attribute name="typeIn" required="false" rtexprvalue="true" %>

<%
	Field f = entity.getClass().getDeclaredField(field);
	f.setAccessible(true);
	
	if(showVal == null) {
		showVal = false;
	}
	
	if(typeIn == null) {
		typeIn = "text";
	}
%>

<%
	if(typeIn.equalsIgnoreCase("hidden")) {
%>
	<form:hidden path="<%=name %>"/>
<%
	} else {
%>
<div class="form-group" style="width: 55%;">
	<label for="email"><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + f.getName() %>' /></label>
	<input <%=showVal ? String.format("value='%s'", f.get(entity)) : "" %> name="<%=name%>" type="<%=typeIn%>" class="form-control" id="<%=name%>">
</div>
<%
	}
%>