<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('STUDENT')">


<form:form action="group/student/save.do" modelAttribute="groupForm">

<form:hidden path="subjectId"/>

<acme:textbox2 code="group.name" path="name"/>
<acme:textbox2 code="group.description" path="description"/>
<acme:textbox2 code="group.startDate" path="startDate"/>
<acme:textbox2 code="group.endDate" path="endDate"/>
<acme:submit name="save" code="acme.save"/>
<acme:cancel url="/group/student/list.do?q=${groupForm.subjectId }" code="acme.cancel"/>

</form:form>

</security:authorize>

