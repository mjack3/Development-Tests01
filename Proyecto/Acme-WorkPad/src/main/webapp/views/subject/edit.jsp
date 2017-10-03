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


<acme:acme_form hiddenFields="id,version,userAccount,administrator,bulletins,teacher,bibliographiesRecords,activities,groups,students,assigments"
type="edit" entity="${subject}"  url="subject/administrator/edit.do" numberSteps="0.25" cancel="welcome/index.do" >
<div class="form-group" style="width: 55%;">
		<label for="label"><spring:message code="subject.category" /> </label> <select
			name="category">
			<jstl:forEach var="s" items="${categories}">
				<option value="${s.id}">${s.name}</option>
			</jstl:forEach>
		</select>
	</div>

</acme:acme_form>



</security:authorize>