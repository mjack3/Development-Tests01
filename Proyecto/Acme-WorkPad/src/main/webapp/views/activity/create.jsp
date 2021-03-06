<%--
 * action-2.jsp
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


<form:form action="activity/teacher/create.do" modelAttribute="activityForm">

<form:hidden path="subjectId"/>
<acme:textbox2 code="activity.title" path="title"/>
<acme:textarea code="activity.description" path="description"/>
<acme:textbox2 code="activity.startDate" path="startDate"/>
<acme:textbox2 code="activity.endDate" path="endDate"/>
<acme:textbox2 code="activity.link" path="link"/>

<acme:submit name="save" code="activity.save"/>

<acme:cancel url="activity/teacher/list.do?subjectId=${activityForm.subjectId }" code="activity.cancel"/>

</form:form>
