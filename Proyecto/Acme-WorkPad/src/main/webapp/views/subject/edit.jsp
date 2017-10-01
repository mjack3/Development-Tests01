<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMINISTRATOR')">


<acme:acme_form hiddenFields="id,version,userAccount,administrator,bulletins,teacher,bibliographiesRecords,activities,groups,students,category,assigments"
type="edit" entity="${subject}" url="administrator/subject/edit.do" numberSteps="0.25" cancel="welcome/index.do">

</acme:acme_form>

</security:authorize>