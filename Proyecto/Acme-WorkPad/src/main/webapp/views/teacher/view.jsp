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





<acme:acme_view entity="${teacher}" skip_fields="name,surname,email,phone,postalAddress,seminars,bibliographiesRecords,subjects"  >

 <tr>
	<td> <b><spring:message code="teacher.name"/></b> <jstl:out value="${teacher.name}"/> </td>
<td>

 <tr>
	<td> <b><spring:message code="teacher.surname"/></b> <jstl:out value="${teacher.surname}"/> </td>
<td>

 <tr>
	<td> <b><spring:message code="teacher.email"/></b> <jstl:out value="${teacher.email}"/> </td>
<td>

 <tr>
	<td> <b><spring:message code="teacher.phone"/></b> <jstl:out value="${teacher.phone}"/> </td>
<td>

 <tr>
	<td> <b><spring:message code="teacher.postalAddress"/></b> <jstl:out value="${teacher.postalAddress}"/> </td>
<td>

</acme:acme_view>

<br/>



