<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div>
	<img src="${image}" alt="Acme-WorkPad Co., Inc." /> 
</div>
<div style="width: 60%">

	<nav class="navbar navbar-default" style="margin-bottom: 0px">
		<div class="container-fluid">
			<div class="navbar-header">
				<ul class="nav navbar-nav">
					<security:authorize access="isAnonymous()">
						<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
						<li><a class="fNiv" href="student/create.do"><spring:message code="master.page.student.create" /></a></li>
						<li><a href="subject/list.do"><spring:message code="master.page.subject" /></a></li>
						 <li><a href="teacher/list.do"><spring:message code="master.page.teacher.list" /></a></li>
					</security:authorize>	
					
					<security:authorize access="isAuthenticated()">
						<security:authentication property="principal.id" var="id" />
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.profile" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
							<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
							<li><a href="folder/actor/list.do"><spring:message
									code="master.page.actor.folders" /> </a></li>
							<security:authorize access="hasRole('ADMINISTRATOR')">
							<li><a href="administrator/edit.do"><spring:message code="master.page.actor.edit" /></a></li>
							</security:authorize>
							<security:authorize access="hasRole('STUDENT')">
							 <li><a href="student/edit.do"><spring:message code="master.page.actor.edit" /></a></li>
							 	
							 </security:authorize>
							  <security:authorize access="hasRole('TEACHER')">
       						<li><a href="teacher/edit.do"><spring:message code="master.page.actor.edit" /></a></li>
      						</security:authorize>
				          </ul>
				        </li>
				        
				        <li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.subject" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          <li><a href="subject/list.do"><spring:message code="master.page.subject" /></a></li>
				          <security:authorize access="hasRole('ADMINISTRATOR')">
				         
				          <li><a class="fNiv" href="subject/administrator/list.do"><spring:message code="master.page.mySubject" /></a></li>
				          </security:authorize>
				          <security:authorize access="hasRole('STUDENT')">
				         
				          <li><a class="fNiv" href="subject/student/list.do"><spring:message code="master.page.mySubject" /></a></li>
				          <li><a href="assignment/student/list.do"><spring:message code="master.page.student.assignment" /></a></li>
          					<li><a href="group/student/mylist.do"><spring:message code="master.page.student.group" /></a></li>
          					 <li><a href="teacher/list.do"><spring:message code="master.page.teacher.list" /></a></li>	
				          </security:authorize>
				          <security:authorize access="hasRole('TEACHER')">
				            <li><a href="subject/teacher/list.do"><spring:message   code="master.page.teacher.subject" /></a></li>
       
				          
				          </security:authorize>
				          </ul>
				          </li>
				          
				           <security:authorize access="hasRole('TEACHER')">       
       						<li><a href="seminar/teacher/list.do"><spring:message code="master.page.seminar.teacher.list" /></a></li>
				          <li><a href="teacher/list.do"><spring:message code="master.page.teacher.list" /></a></li>
				          </security:authorize>
				        	
						
						<security:authentication property="principal.id" var="id" />
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Records<span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          		<li><a href="activityRecord/authenticated/list.do"><spring:message
								code="master.page.listActivityRecord" /> </a></li>
								
								<li><a href="activityRecord/authenticated/listSystem.do"><spring:message
								code="master.page.listActivityRecordSystem" /> </a></li>
				          </ul>
				          </li>
						
						
									
					</security:authorize>
					
					
					<!-- Reme -->
					
					<security:authorize access="hasRole('ADMINISTRATOR')">
					
          			<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.teacher.list" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
				          <li><a href="teacher/administrator/create.do"><spring:message code="master.page.teacher.create" /></a></li>
				          <li><a href="teacher/list.do"><spring:message code="master.page.teacher.list" /></a></li>
						 </ul>
				     
						
						 
						<li><a class="fNiv" href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
						
					</security:authorize>
						 
					
					
					<!-- KARLI -->
					
					
					
      
      
      <security:authorize access="hasRole('STUDENT')">
							
      	<li class="dropdown">
         <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="master.page.seminar.student.list" /><span class="caret"></span></a>
				          <ul class="dropdown-menu">
          <li><a href="seminar/student/list.do"><spring:message code="master.page.seminar.student.list" /></a></li>
          <li><a href="seminar/student/myList.do"><spring:message code="master.page.seminar.student.myList" /></a></li>
       </ul>
       <li><a href="teacher/list.do"><spring:message code="master.page.teacher.list" /></a></li>
      </security:authorize>

	<security:authorize access="isAuthenticated()">
	
		<li><a class="fNiv" href="student/list.do"><spring:message code="master.page.listStudent"/></a></li>				
      
       
      </security:authorize>

      
     

      
      <security:authorize access="hasRole('ADMINISTRATOR')">
      	
      	<li><a href="school/administrator/edit.do"><spring:message code="master.page.school.edit" /></a></li>
      </security:authorize>
      
      
      
     

					
				</ul>
			</div>
		</div>
	</nav>
	<a href="?language=en"> <img src="images/flag_en.png" /> </a>
	<a href="?language=es"> <img src="images/flag_es.png" /> </a>
</div>

