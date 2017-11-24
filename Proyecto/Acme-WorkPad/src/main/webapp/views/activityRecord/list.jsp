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
<spring:message code="activityrecord.writtenDate" var="date"></spring:message>
<spring:message code="activityrecord.attachments" var="attachments"></spring:message>
<spring:message code="activityrecord.description" var="description"></spring:message>
<spring:message code="activityrecord.edit" var="edit"></spring:message>
<spring:message code="activityrecord.delete" var="delete"></spring:message>

<display:table name="activityRecord" id="row" requestURI="${requestURI}"
	pagesize="12" class="table table-over">
	<display:column property="description" title="${description}" sortable="false" />
	<display:column property="writtenDate" title="${date}" sortable="false" />
	<display:column title="${attachments}" sortable="false">
	
	<jstl:forEach items="${row.attachments}" var="e">
	<a href="${e}" > <jstl:out value="${e}"></jstl:out></a>
	
	</jstl:forEach>
	</display:column>
	
	<jstl:choose>
	
			<jstl:when test="${principal.userAccount.id == userAccountId }">
			<display:column title="${edit}">
			<a href="activityRecord/authenticated/edit.do?q=${row.id }"> <spring:message code="activityrecord.edit" /> </a>
			
			</display:column>
			<display:column title="${delete}">
			<a href="activityRecord/authenticated/delete.do?q=${row.id }"> <spring:message code="activityrecord.delete" /> </a>
		</display:column>
			</jstl:when>
		</jstl:choose>
		
		
	
	</display:table>
	<jstl:choose>
			<jstl:when test="${principal.userAccount.id == userAccountId }">
					<a class="btn btn-primary" href="activityRecord/authenticated/create.do"> <spring:message code="activity.create" /> </a>
			</jstl:when>
	</jstl:choose>
	


</security:authorize>
	
	