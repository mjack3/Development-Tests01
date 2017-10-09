<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('STUDENT')">
<acme:list  list="${group}"  hidden_fields="students" requestURI="${requestURL}" entityUrl="{submission:submission/student/list.do}" variable="e" pagesize="6">

<td><jstl:if
				test="${isgroup==false and today.before(e.endDate)}">
				<a href="group/student/subscribe.do?q=${e.id}"> <spring:message
						code='group.registerSubject' /></a>
			</jstl:if></td>
		<td>
</acme:list>
</security:authorize>

<security:authorize access="hasRole('TEACHER')">
<acme:list  list="${group}" hidden_fields="students" requestURI="group/student/list.do" entityUrl="{submission:submission/teacher/list.do}"  pagesize="6">
</acme:list>
</security:authorize>

