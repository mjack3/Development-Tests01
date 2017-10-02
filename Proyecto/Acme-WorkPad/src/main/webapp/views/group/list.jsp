<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:list  list="${groupsubject}" requestURI="group/student/list.do" entityUrl="{submission:submission/list.do}" variable="student" pagesize="6">



</acme:list>

<security:authorize access="hasRole('STUDENT')">


<jstl:if test="${subjectscond==true}">

<a href="group/student/create.do"> <spring:message
							code='subject.createGroup' /></a>
</jstl:if>						
</security:authorize>