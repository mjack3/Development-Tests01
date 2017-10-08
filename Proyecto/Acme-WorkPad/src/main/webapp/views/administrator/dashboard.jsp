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

<table class="table">
		<tr>
			<td> <spring:message code="administrator.dashboard.one" />  </td>
			<td> 
			<jstl:forEach var="a" items="${dashboard.get(0)}">
					${a.name} <br/>
			</jstl:forEach>
			 </td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.two" />  </td>
			<td>
			<jstl:forEach var="a" items="${dashboard.get(1)}">
					${a.name} <br/>
			</jstl:forEach>
			</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.three" />  </td>
			<td> 
			<jstl:forEach var="a" items="${dashboard.get(2)}">
					${a.name} <br/>
			</jstl:forEach>
			 </td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.four" />  </td>
			<td> ${dashboard.get(3)[0]} , ${dashboard.get(3)[1]} , ${dashboard.get(3)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.five" />  </td>
			<td> ${dashboard.get(4)[0]} , ${dashboard.get(4)[1]} , ${dashboard.get(4)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.six" />  </td>
			<td> ${dashboard.get(5)[0]} , ${dashboard.get(5)[1]} , ${dashboard.get(5)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.seven" />  </td>
			<td> ${dashboard.get(6)[0]} , ${dashboard.get(6)[1]} , ${dashboard.get(6)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.ocho" />  </td>
			<td> ${dashboard.get(7)[0]} , ${dashboard.get(7)[1]} , ${dashboard.get(7)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.nine" />  </td>
			<td> 
			<jstl:forEach var="a" items="${dashboard.get(8)}">
					${a.name} <br/>
			</jstl:forEach>
			</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.ten" />  </td>
			<td> ${dashboard.get(9)[0]} , ${dashboard.get(9)[1]} , ${dashboard.get(9)[2]}</td>
		</tr>
		<tr>
			<td> <spring:message code="administrator.dashboard.eleven" />  </td>
			<td> 
			<jstl:forEach var="a" items="${dashboard.get(10)}">
					${a.name} <br/>
			</jstl:forEach>
			</td>
		</tr>
</table>
