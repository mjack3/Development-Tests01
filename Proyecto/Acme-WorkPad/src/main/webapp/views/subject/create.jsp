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


<acme:acme_form skip_fields="category,ticker,teacher" type="create" entity="${subject}"  url="subject/administrator/create.do" numberSteps="0.25" cancel="welcome/index.do" >
<div class="form-group" style="width: 55%;">
		<acme:select items="${categories}" itemLabel="name" code="subject.category" path="category"/>
		<br/>
		<label for="label"><spring:message code="subject.teacher" />
			</label> <select name="teacher">
				<jstl:forEach var="s" items="${teachers}">
					<option value="${s.id}">${s.name}</option>
				</jstl:forEach>
			</select>
			<br/>
		
</div>

</acme:acme_form>



</security:authorize>