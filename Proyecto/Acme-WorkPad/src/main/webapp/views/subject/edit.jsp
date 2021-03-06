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


<acme:acme_form skip_fields="category" type="edit" entity="${subject}"  url="subject/administrator/edit.do" numberSteps="0.25" cancel="welcome/index.do" >
<div class="form-group" style="width: 55%;">
		<acme:select items="${categories}" itemLabel="name" code="subject.category" path="category"/>
</div>

</acme:acme_form>



</security:authorize>