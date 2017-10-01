<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list another_mapped_urls="{mailmessage.move:mailmessage/actor/moveTo.do}" viewUrl="mailmessage/actor/view.do" columNames="{sender:mailmessage.sender}" field_mapping="{sender:name, priority:value}" hidden_fields="recipient" list="${mailmessage}" deleteUrl="mailmessage/actor/delete.do" requestURI="mailmessage/actor/list.do" pagesize="6"/>
