<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="minvalue" required="false" rtexprvalue="true" %>
<%@ attribute name="maxvalue" required="false" rtexprvalue="true" %>

<%-- Definition --%>

<%
if(minvalue==null) {
	minvalue="0"; }

if(maxvalue==null) {
	maxvalue="0"; }
%>

<input style="width: 25%" type="number" min="${minvalue}" max="${maxvalue}" class="form-control" name="${path}" id="InputNumber${path}">
