<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="table table-over" keepStatus="true"
	name="students" requestURI="${requestURI}" id="row">


<spring:message code="student.name" var="h" />
<display:column title="${h }" property="name" />

<spring:message code="student.surname" var="h" />
<display:column title="${h }" property="surname" />

<spring:message code="student.phone" var="h" />
<display:column title="${h }" property="phone" />

<spring:message code="student.email" var="h" />
<display:column title="${h }" property="email" />

<spring:message code="student.postalAddress" var="postalAddress" />
<display:column title="${h }" property="postalAddress" />

<spring:message code="student.activitiesRecords" var="h" />
<display:column title="${h }">
<a href="activityRecord/authenticated/list.do?userAccountId=${row.userAccount.id }"> <jstl:out value="${h }"/> </a>
</display:column>



	
	
</display:table>