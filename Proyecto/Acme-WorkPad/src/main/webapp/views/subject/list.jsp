
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<label><spring:message code="subject.withoutSeats" /> </label>
<input type="radio" value="without" id="withoutBTN" name="check" onclick="changeWithout(this)">

<label><spring:message code="subject.withSeats" /> </label>
<input type="radio" value="with" id="withBTN"  name="check" onclick="changeWith(this)">

<input type="text" id="textSearch" /> 
<button type="button" class="btn btn-primary" >  <spring:message code="subject.search" /></button>

<br/>

<security:authorize access="isAnonymous()">

	<acme:list
		entityUrl="{teacher:teacher/view.do,bibliographiesRecords:bibliographyrecord/list.do,  administator:administrator/view.do, category:category/view.do, assigments:assignment/list.do}"
		list="${subject}" variable="e" requestURI="${requestURI}"
		hidden_fields="administrator,activities,assigments,bulletins,groups,students"
		pagesize="6">
	</acme:list>

</security:authorize>

<security:authorize access="hasRole('STUDENT')">

	<acme:list
		entityUrl="{groups:group/student/list.do,bulletins:bulletin/actor/list.do, teacher:teacher/view.do,bibliographiesRecords:bibliographyrecord/list.do,  administator:administrator/view.do, category:category/view.do, assigments:assignment/list.do}"
		list="${subject}" variable="e" requestURI="${requestURI}"
		hidden_fields="administrator,activities,assigments,students"
		pagesize="6">
		<td><a href="bulletin/actor/create.do?q=${e.id}"> <spring:message
					code="bulletin.create" />
		</a></td>
		<td><jstl:if
				test="${e.seats>0 and !subjectByStudent.contains(e)}">
				<a href="subject/student/subscribe.do?q=${e.id}"> <spring:message
						code='subject.registerSubject' /></a>
			</jstl:if></td>
		<td>
		<jstl:if
				test="${subjectByStudent.contains(e)}">
				<a href="group/student/create.do"> <spring:message
							code='subject.createGroup' /></a>
			</jstl:if>
		</td>
	</acme:list>

</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<acme:list
		entityUrl="{groups:group/student/list.do,bulletins:bulletin/actor/list.do, teacher:teacher/view.do,bibliographiesRecords:bibliographyrecord/list.do,  administator:administrator/view.do, category:category/view.do, assigments:assignment/list.do}"
		list="${subject}" variable="e" requestURI="${requestURI}"
		hidden_fields="administrator,activities,assigments,students"
		pagesize="6">
		
		<jstl:if test="${MySubjects == 1}">
		<td><a href="bulletin/actor/create.do?q=${e.id}"> <spring:message
					code="bulletin.create" />
		</a></td>
		<td><a href="subject/administrator/delete.do?q=${e.id}"> <spring:message
					code='acme.delete' /></a></td>
		<td><a href="subject/administrator/edit.do?q=${e.id}"> <spring:message
					code='acme.edit' /></a></td>
		<td><a href="subject/administrator/associateTeacher.do?q=${e.id}"> <spring:message
					code='subject.associate' /></a></td>
		</jstl:if>
	</acme:list>

</security:authorize>

<security:authorize access="hasRole('TEACHER')">

	<acme:list
		entityUrl="{groups:group/student/list.do,bulletins:bulletin/actor/list.do, teacher:teacher/view.do,bibliographiesRecords:bibliographyrecord/list.do,  administator:administrator/view.do, category:category/view.do, assigments:assignment/list.do}"
		list="${subject}" variable="e" requestURI="${requestURI}"
		hidden_fields="administrator,activities,assigments,students"
		pagesize="6">
		<td><a href="bulletin/actor/create.do?q=${e.id}"> <spring:message
					code="bulletin.create" />
		</a></td>
		<jstl:if test="${principal.subjects.contains(e)}">
			<td><a href="activity/teacher/list.do?subjectId=${e.id }"> <spring:message
						code="subject.activities" /></td>
			</a>
			<td>
		<a href="assignment/teacher/list.do?subjectId=${e.id}"> <spring:message
					code="subject.assigments" />
			</td>
			</a>
			</jstl:if>
	</acme:list>

</security:authorize>




<script>
$(document).ready(function(){
    $("button").click(function(){
        $.ajax({success: function(result){
        	var input,inputCheck, filter, table, tr, tdTitle,tdSyllabus,tdSeats,i;
        	input = document.getElementById("textSearch");
        	inputCheck = $('input[name=check]:checked').val();
        	filter = input.value.toUpperCase();
        	table = document.getElementById("row");
        	tr = table.getElementsByTagName("tr");
        	for (i = 0; i < tr.length; i++) {
        		tdTitle = tr[i].getElementsByTagName("td")[0];
        		tdSyllabus = tr[i].getElementsByTagName("td")[2];
        		tdSeats = tr[i].getElementsByTagName("td")[3];
        		if(inputCheck == "without"){
        			if (tdTitle || tdSyllabus || tdSeats) {
            			if ((tdTitle.innerHTML.toUpperCase().indexOf(filter) > -1 || 
            				tdSyllabus.innerHTML.toUpperCase().indexOf(filter) > -1)
            				&& tdSeats.innerHTML.toUpperCase() == "0") {
    	          	        tr[i].style.display = "";
    	          	      } else {
    	          	        tr[i].style.display = "none";
    	          	      }            			
            			}
        		}else if(inputCheck == "with"){
        			if (tdTitle || tdSyllabus|| tdSeats) {
            			if ((tdTitle.innerHTML.toUpperCase().indexOf(filter) > -1 || 
            				tdSyllabus.innerHTML.toUpperCase().indexOf(filter) > -1)
            				&& tdSeats.innerHTML.toUpperCase() != "0") {
    	          	        tr[i].style.display = "";
    	          	      } else {
    	          	        tr[i].style.display = "none";
    	          	      }
            			
            			}
        		}
        		
          	      
        	}
        }});
    });
});
</script>



<script>

var radioStateWithout = false;
var radioStateWith = false;
function changeWithout(element){
    if(radioStateWithout == false) {
        checkWithout();
        radioStateWithout = true;
    }else{
        uncheckWithout();
        radioStateWithout = false;
    }
}

function changeWith(element){
    if(radioStateWith == false) {
        checkWith();
        radioStateWith = true;
    }else{
        uncheckWith();
        radioStateWith = false;
    }
}

function checkWithout() {
    document.getElementById("withoutBTN").checked = true;
}
function uncheckWithout() {
    document.getElementById("withoutBTN").checked = false;
}
function checkWith() {
    document.getElementById("withBTN").checked = true;
}
function uncheckWith() {
    document.getElementById("withBTN").checked = false;
}
</script>


