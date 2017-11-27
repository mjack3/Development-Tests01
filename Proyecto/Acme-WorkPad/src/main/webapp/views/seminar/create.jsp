<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('TEACHER')">
<form:form action="seminar/teacher/save.do" modelAttribute="seminar">

<form:hidden path="id"/>
<form:hidden path="version"/>
	
<acme:textbox2 code="seminar.title" path="title"/>
<acme:textbox2 code="seminar.summary" path="summary"/>
<acme:textbox2 code="seminar.organisedDate" path="organisedDate"/>
<acme:textbox2 code="seminar.duration" path="duration"/>
<acme:textbox2 code="seminar.hall" path="hall"/>
<acme:textbox2 code="seminar.seats" path="seats"/>

		
		

	
	

	<spring:message code="seminar.save" var="actorSaveHeader" />
	<spring:message code="seminar.cancel" var="actorCancelHeader" />
	
<input type="submit" class="btn btn-primary" name="save"
		value="${actorSaveHeader}" />
	<input onclick="window.location='seminar/teacher/list.do'"
		class="btn btn-warning" type="button" name="cancel"
		value="${actorCancelHeader}" />

</form:form>

</security:authorize>

