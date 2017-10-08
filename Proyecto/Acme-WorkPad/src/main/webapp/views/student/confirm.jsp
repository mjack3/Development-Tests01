<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="isAnonymous()">

<form:form action="student/saveConfirm.do" modelAttribute="student" method="POST" >

<h1>  <spring:message code="student.confirm" /> </h1>
<input type="submit" class="btn btn-primary" value="<spring:message code="student.save" />">
<input onclick="window.history.back()" type="button" class="btn btn-warning" value="<spring:message code="student.cancel" />">


</form:form>

</security:authorize>

<security:authorize access="hasRole('STUDENT')">

<form:form action="student/saveConfirmEdit.do" modelAttribute="student" method="POST" >

<h1>  <spring:message code="student.confirm" /> </h1>
<input type="submit" class="btn btn-primary" value="<spring:message code="student.save" />">
<input onclick="window.history.back()" type="button" class="btn btn-warning" value="<spring:message code="student.cancel" />">


</form:form>

</security:authorize>