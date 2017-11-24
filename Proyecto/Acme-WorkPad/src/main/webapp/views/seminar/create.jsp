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

	<div class="form-group" style="width: 20%;">

		<label> <spring:message code="seminar.title" /></label> <br />
		<input class="form-control" value="${seminar.title}" type="text"
			name="title" />
		<form:errors cssClass="error" path="title" />
		
		<br /> 
		
		<label> <spring:message code="seminar.summary"/> </label><br />
		<form:textarea cols="30" rows="10" path="summary" class="form-control"/>
		<form:errors cssClass="error" path="summary" /> 
		
		<br />
		
		<label> <spring:message code="seminar.organisedDate" /></label> <br />
		<input class="form-control" value="${seminar.organisedDate}" type="text"
			name="organisedDate" />
		<form:errors cssClass="error" path="organisedDate" />
		
		<br /> 
		
		<label> <spring:message code="seminar.duration" /></label> <br />
		<input class="form-control" value="${seminar.duration}" type="number" min="1"
			name="duration" />
		<form:errors cssClass="error" path="duration" />
		
		<br /> 
		
		<label> <spring:message code="seminar.hall" /></label> <br />
		<input class="form-control" value="${seminar.hall}" type="text" 
			name="hall" />
		<form:errors cssClass="error" path="hall" />
		
		<br /> 
		
		<label> <spring:message code="seminar.seats" /></label> <br />
		<input class="form-control" value="${seminar.seats}"type="number" min="1"
			name="seats" />
		<form:errors cssClass="error" path="seats" />
		
		<br /> 
		</div>
		

	<spring:message code="seminar.save" var="actorSaveHeader" />
	<spring:message code="seminar.cancel" var="actorCancelHeader" />
	<input type="submit" class="btn btn-primary" name="save"
		value="${actorSaveHeader}" />
	<input onclick="window.location='seminar/teacher/list.do'"
		class="btn btn-warning" type="button" name="cancel"
		value="${actorCancelHeader}" />


</form:form>

</security:authorize>

