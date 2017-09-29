
<%--
 * login.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div> 
	<form method="GET" action="subject/search.do"> 
	
	<input type="radio" name="sw" value="sin"> <spring:message code="subject.withoutSeats" /> <br>
  	<input checked="checked" type="radio" name="sw" value="con"> <spring:message code="subject.withSeats" /> <br>
	
		<input type="text" name="keyword"/>
		<button type="submit"><spring:message code="subject.search" /></button>
	</form>

 </div>
  
  LISTAR GENÉRICO FEO PARA QUE PODAIS VER EL BUSCADOR <BR/>
  
<display:table name="subjects" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag"> 
	
	<display:column property="title" />
	
	</display:table>
 
 
 
 
 
 
 
 
 