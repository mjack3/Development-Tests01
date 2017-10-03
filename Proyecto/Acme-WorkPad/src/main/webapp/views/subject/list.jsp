
<%--
 * login.jsp
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


<div> 
	<form method="GET" action="${requestSearch }">
	<input type="radio" name="sw" value="sin"> <spring:message code="subject.withoutSeats" /> <br>
  	<input checked="checked" type="radio" name="sw" value="con"> <spring:message code="subject.withSeats" /> <br>
	
		<input type="text" name="keyword"/>
		<button type="submit"><spring:message code="subject.search" /></button>
	</form>
	

 </div>
  
<!-- LIST DE KARLI -->

<acme:list entityUrl="{bulletins:bulletin/list.do,teacher:teacher/view.do,bibliographiesRecords:bibliographyrecord/list.do,  administator:administrator/view.do, groups:group/student/list.do, students:student/list.do, category:category/view.do, assigments:assignment/list.do}" list="${subject}" variable="e"  requestURI="${requestURI}" hidden_fields="administrator,activities" pagesize="6">

<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${e.seats>0 and !subjectByStudent.contains(e)}">
<a href="subject/student/subscribe.do?q=${e.id}"> <spring:message
							code='subject.registerSubject' /></a>
							</jstl:if>
</security:authorize >
<security:authorize access="hasRole('ADMINISTRATOR')">

<a href="subject/administrator/delete.do?q=${e.id}"> <spring:message
							code='acme.delete' /></a>


</security:authorize>
<security:authorize access="hasRole('ADMINISTRATOR')">

<a href="subject/administrator/edit.do?q=${e.id}"> <spring:message
							code='acme.edit' /></a>
</security:authorize>


<security:authorize access="hasRole('TEACHER')">


<jstl:if test="${principal.subjects.contains(e)}">
	<a href="activity/teacher/list.do?subjectId=${e.id }"> <spring:message code="subject.activities" /> </a>
</jstl:if>



</security:authorize>



</acme:list>


 
 <!-- Reme Adaptado-->

 
 
 
 
 
 
 