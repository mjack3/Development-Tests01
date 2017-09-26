<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes --%> 

<%@ attribute name="code" required="true" rtexprvalue="true" %>

<%-- Definition --%>


<div style="width: 25%" class="alert alert-danger" role="alert">
	<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	<span class="sr-only">Error:</span>
	<spring:message code="${code}" />
</div>