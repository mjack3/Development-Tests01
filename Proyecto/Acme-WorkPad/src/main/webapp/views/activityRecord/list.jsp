<%--
 * action-1.jsp
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

<security:authorize access="isAuthenticated()">

<acme:list variable="row"  requestURI="${requestURI }" list="${activityRecords }"  >
		
		<jstl:choose>
			<jstl:when test="${principal.userAccount.id == userAccountId }">
			<td>
			<a href="activityRecord/authenticated/edit.do?q=${row.id }"> <spring:message code="activityrecord.edit" /> </a>
			</td>
			<td>
			<a href="activityRecord/authenticated/delete.do?q=${row.id }"> <spring:message code="activityrecord.delete" /> </a>
			</td>
			</jstl:when>
		</jstl:choose>
		
		<jstl:if test="">
		
		</jstl:if>
		
</acme:list>
	<br />
	
	<jstl:choose>
			<jstl:when test="${principal.userAccount.id == userAccountId }">
					<a class="btn btn-primary" href="activityRecord/authenticated/create.do"> <spring:message code="activity.create" /> </a>
			</jstl:when>
	</jstl:choose>
	


</security:authorize>
	
	