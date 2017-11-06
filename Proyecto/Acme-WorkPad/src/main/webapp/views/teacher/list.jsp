<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list list="${teachers}" requestURI="teacher/list.do" 
hidden_fields="seminars,bibliographiesRecords,userAccount,folders,activitiesRecords,socialIdentities" 
pagesize="6"
entityUrl="{subjects: subject/listTeacher.do}"
variable="row"
>

<security:authorize access="isAuthenticated()">

<td>
<a href="activityRecord/authenticated/list.do?userAccountId=${row.userAccount.id }"> <spring:message code="teacher.activitiesRecords" /> </a>
</td>

</security:authorize>

</acme:list>



