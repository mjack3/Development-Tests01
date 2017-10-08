<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="code" required="true" rtexprvalue="true" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" %>

<%-- Definition --%>

<%
if(placeholder==null){
	placeholder="";
}
%>

<div>
<label for="InputText${path}"><spring:message code="${code}" /></label>
<input style="width: 25%" type="text" class="form-control" name="${path}" id="InputText${path}" placeholder="<spring:message code="${code}" />">
</div>