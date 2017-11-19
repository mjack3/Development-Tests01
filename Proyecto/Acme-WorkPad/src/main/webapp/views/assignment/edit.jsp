<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI }" modelAttribute="assignmentForm">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="subjectId"/>

	<acme:textbox2 code="assignment.title" path="title"/>
	<acme:textarea code="assignment.description" path="description"/>
	<label> <spring:message code="assignment.startDate"/> </label><br />
		<input class="form-control" value="${assignment.startDate}" type="text" placeholder="DD/MM/YYYY" name="startDate"/>
		<form:errors cssClass="error" path="startDate" /> <br />
		
		<label> <spring:message code="assignment.endDate"/> </label><br />
		<input class="form-control" value="${assignment.endDate}" type="text"  placeholder="DD/MM/YYYY" name="endDate"/>
		<form:errors cssClass="error" path="endDate" /> <br />
	<acme:textbox2 code="assignment.link" path="link"/>

<acme:submit name="save" code="assignment.save"/>
<input onclick="window.location='subject/teacher/list.do'" type="button" class="btn btn-warning" value="<spring:message code="assignment.cancel" />">

</form:form>
