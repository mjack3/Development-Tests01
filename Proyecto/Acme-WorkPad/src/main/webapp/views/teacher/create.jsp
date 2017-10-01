<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:acme_form skip_fields="userAccount" hiddenFields="seminars,bibliographiesRecords,subjects,folders,activitiesRecords,socialIdentities" another_mapped_classes="domain.Actor" type="create" entity="${teacher}" url="teacher/administrator/save.do" cancel="welcome/index.do">
	<acme:acme_input entity="${teacher.userAccount}" name="userAccount.username" field="username"/>
	<acme:acme_input entity="${teacher.userAccount}" name="userAccount.password" field="password" typeIn="password"/>
	<acme:acme_input entity="${teacher.userAccount}" name="userAccount.authorities" field="authorities" typeIn="hidden"/>
</acme:acme_form>
