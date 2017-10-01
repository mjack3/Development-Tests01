<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('STUDENT')">
<form:form action="groupsubject/student/save.do" modelAttribute="groupsubject">
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="submission"/>


<acme:inputText code="groupsubject.name" path="name"/>
<acme:inputText code="groupsubject.description" path="description"/>
<acme:inputText code="groupsubject.startDate" path="startDate"/>
<acme:inputText code="groupsubject.endDate" path="endDate"/>
<br/>

<acme:submit name="save" code="acme.save"/>




</form:form>
</security:authorize>