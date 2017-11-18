<%--
 * edit.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="${requestParam}" modelAttribute="submission">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<div class="form-group" style="width: 20%;">


		<label> <spring:message code="submission.grade" />
		</label> <br /> <input class="form-control" value="${grade}" type="number"
			name="grade" max="100" min="0" />
		<form:errors cssClass="error" path="grade" />
		<br /> [0 - 100] <br />

	</div>
	<!-- Buttons -->

	<acme:submit name="save" code="submission.save" />

	<input onclick="window.history.back()" type="button"
		class="btn btn-warning"
		value="<spring:message code="submission.cancel" />">


</form:form>