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

<security:authorize access="hasRole('STUDENT')">
	<acme:list list="${seminars}" requestURI="seminar/student/list.do" pagesize="6">
	<jstl:if test="${!mySeminars.contains(row)}">
		<td>
		<a href="seminar/student/into.do?q=${row.id}"> <spring:message code="seminar.into" />  </a>
		</td>
	</jstl:if>
	<jstl:if test="${mySeminars.contains(row)}">
		<td>
		<a href="seminar/student/out.do?q=${row.id}"> <spring:message code="seminar.out" />  </a>
		</td>
	</jstl:if>
	
	</acme:list>
</security:authorize>

<security:authorize access="hasRole('TEACHER')">
	<acme:list list="${seminars}" requestURI="seminar/student/list.do" editUrl="seminar/teacher/edit.do" deleteUrl="seminar/teacher/delete.do" pagesize="6">
	</acme:list>
	<br/><br/>
	<a href="seminar/teacher/create.do"> <spring:message
					code="seminar.create" /> </a>
</security:authorize>

