
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
	<form method="GET" action="subject/search.do"> 
	
	<input type="radio" name="sw" value="sin"> <spring:message code="subject.withoutSeats" /> <br>
  	<input checked="checked" type="radio" name="sw" value="con"> <spring:message code="subject.withSeats" /> <br>
	
		<input type="text" name="keyword"/>
		<button type="submit"><spring:message code="subject.search" /></button>
	</form>

 </div>
  
<!-- LIST DE KARLI -->

<jstl:if test="${a==1}">
<security:authorize access="hasRole('STUDENT')">

<acme:list entityUrl="{bulletins:bulletin/list.do, teacher:teacher/view.do, bibliographiesRecords:bibliographyrecord/list.do, activities:activity/list.do, administator:administrator/view.do, groups:groupsubject/list.do, students:student/list.do, category:category/view.do, assigments:assignment/list.do}" list="${subject}"  requestURI="subject/student/list.do" hidden_fields="administrator" pagesize="6">
<a href="groupsubject/student/create.do"> <spring:message
							code='subject.createGroup' /></a>


</acme:list>
</security:authorize>
</jstl:if>

<security:authorize access="hasRole('STUDENT')">
<jstl:if test="${a==2}">
<acme:list entityUrl="{bulletins:bulletin/list.do, teacher:teacher/view.do, bibliographiesRecords:bibliographyrecord/list.do, activities:activity/list.do, administator:administrator/view.do, groups:groupsubject/list.do, students:student/list.do, category:category/view.do, assigments:assignment/list.do}" list="${subject}"  requestURI="subject/student/register/list.do" hidden_fields="administrator" pagesize="6" variable="e">
<a href="groupsubject/student/create.do"> <spring:message
							code='subject.createGroup' /></a>
<jstl:if test="${e.seats>0 and !subjectByStudent.contains(e)}">
<a href="subject/student/subscribe.do?q=${e.id}"> <spring:message
							code='subject.registerSubject' /></a>
							</jstl:if>
</acme:list>

</jstl:if>
</security:authorize>

<security:authorize access="!hasRole('STUDENT')">

<jstl:if test="${a==3}">
<acme:list entityUrl="{bulletins:bulletin/list.do, teacher:teacher/view.do, bibliographiesRecords:bibliographyrecord/list.do, activities:activity/list.do, administator:administrator/view.do, groups:groupsubject/list.do, students:student/list.do, category:category/view.do, assigments:assignment/list.do}" list="${subject}" hidden_fields="administrator"  requestURI="subject/list.do" pagesize="6" variable="e">

</acme:list>

</jstl:if>
</security:authorize>
 
 
 <!-- Reme -->
 <security:authorize access="hasRole('ADMINISTRATOR')">

	<jstl:if test="${var==2}">

		<acme:list list="${subjects}" requestURI="subject/list.do" variable="subjectId" entityUrl="{}"
			hidden_fields="id,version,userAccount,administrator,bulletins,teacher,bibliographiesRecords,activities,groups,students,category,assigments"
			deleteUrl="administrator/subject/delete.do" editUrl="administrator/subject/edit.do">
			
			
		</acme:list>
		
	</jstl:if>

</security:authorize>
 
 
 
 
 
 
 