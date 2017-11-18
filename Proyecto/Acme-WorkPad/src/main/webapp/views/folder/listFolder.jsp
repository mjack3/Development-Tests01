<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="btn-group btn-group-xs" role="group" aria-label="label">
	<button onclick="javascript:location.href='folder/actor/createSubFolder.do?folder=${folder.id}'"
		type="button" class="btn btn-default">
		<spring:message code="folder.new" />
	</button>
</div>
<table class="table table-over">
	<thead>
		<tr>
			<th><spring:message code="folder.folderName" /></th>
			<th><spring:message code="folder.messages" /></th>
		</tr>
	</thead>
	<jstl:forEach items="${folder.folderChildren}" var="c">
		<tr>
			<td><a href="folder/actor/listFolder.do?folder=${c.id}">${c.folderName}</a></td>

			<!-- Si tiene subcarpetas, no se muestra cuantos mensajes tiene dicha carpeta -->
			<jstl:if test="${fn:length(c.folderChildren) == 0}">
				<td><span class="badge">${fn:length(c.messages)}</span></td>
			</jstl:if>
			<jstl:if test="${fn:length(c.folderChildren) != 0}">
				<td><span class="badge"></span></td>
			</jstl:if>

			<td><jstl:if
					test="${c.folderName ne 'Inbox' and c.folderName ne 'Outbox' and c.folderName ne 'Trashbox' and c.folderName ne 'Spambox'}">
						<a href="folder/actor/editSubFolder.do?folder=${c.id}"><spring:message
								code="acme.edit" /></a>
				</jstl:if></td>
			<td><jstl:if
					test="${c.folderName ne 'Inbox' and c.folderName ne 'Outbox' and c.folderName ne 'Trashbox' and c.folderName ne 'Spambox'}">
					<jstl:if test="${fn:length(c.folderChildren)==0}">
						<a href="folder/actor/deleteSubFolder.do?folder=${c.id}"><spring:message
							code="acme.delete" /></a>
					</jstl:if>
					
				</jstl:if></td>
		</tr>
	</jstl:forEach>
	<tr>
			<td>
				<a href="mailmessage/actor/list.do?folder=${folder.id}">
					<spring:message code="mailmessage.view.messages" />
				</a>
			</td>	
			
			<td><span class="badge">${fn:length(folder.messages)}</span></td>
	</tr>

</table>


