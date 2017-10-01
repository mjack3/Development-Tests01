<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${error != null}">
	<br />
	<div class="alert alert-danger" style="width: 55%;">
		<strong>Error</strong>
		<spring:message code="acme.error.message" />
	</div>
</jstl:if>

<acme:acme_form save_button_msg="mailmessage.send" areaFields="body"
	skip_fields="priority,sent" type="create" entity="${mailmessage}"
	url="mailmessage/actor/save.do" numberSteps="0.25" >
	<div class="form-group" style="width: 55%;">
		<label><spring:message code='mailmessage.priority' /></label> <select
			name="priority">
			<option value="HIGH">HIGH</option>
			<option value="NEUTRAL">NEUTRAL</option>
			<option value="LOW">LOW</option>
		</select>
	</div>
	<div class="form-group" style="width: 55%;">
		<label><spring:message code='mailmessage.recipient' /></label> <input
			id="recipent" name="mail" type="text" class="form-control">
	</div>
</acme:acme_form>
