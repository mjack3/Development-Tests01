<!-- 
	author: jjvalle
	version: 2.0
	copyrigth: jjvalle (2017)
	license: creativecommons 4.0
	It is not allowed to use this code without the prior approval of the author
 -->

<%@tag import="org.springframework.validation.FieldError"%>
<%@tag import="java.util.Enumeration"%>
<%@tag import="org.springframework.validation.BindingResult"%>
<%@tag import="java.util.Collections"%>
<%@tag import="java.util.HashSet"%>
<%@tag import="java.math.BigInteger"%>
<%@tag import="java.math.BigDecimal"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="security.UserAccount"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@tag import="java.lang.reflect.Modifier"%>
<%@tag import="java.util.LinkedList"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.Date"%>
<%@tag import="java.util.Collection"%>
<%@tag import="java.util.List"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.DomainEntity"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@tag language="java" body-content="scriptless" %>

<%@ attribute name="url" required="true" rtexprvalue="true" %>
<%@ attribute name="type" required="false" rtexprvalue="true" %>
<%@ attribute name="cancel" required="false" rtexprvalue="true" %>
<%@ attribute name="entity" required="true" rtexprvalue="true" type="domain.DomainEntity" %>
<%@ attribute name="numberMin" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="numberMax" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="numberSteps" required="false" rtexprvalue="true" type="java.lang.Double" %>
<%@ attribute name="areaFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="hiddenFields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="another_mapped_classes" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="skip_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="steps_of" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="min_of" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="max_of" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="save_button_msg" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="date_stamp" required="false" rtexprvalue="true" type="java.lang.String" %>

<%
	// Errors
	Enumeration<? extends String> names = request.getAttributeNames();
	while(names.hasMoreElements()) {
		String next = names.nextElement();
		if(request.getAttribute(next) instanceof BindingResult) {
			BindingResult binding = (BindingResult) request.getAttribute(next);
			if(binding.hasErrors()) {
				for(FieldError e : binding.getFieldErrors()) {
%>
					<div class="alert alert-warning" style="max-width: 55%;">
					  <strong><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getField() %>' /></strong> <%=e.getDefaultMessage() %>
					</div>
<%
				}
			} else {
				break;
			}
		}
	}
%>

<form:form action="${url}" modelAttribute="<%=entity.getClass().getSimpleName().toLowerCase() %>" method="POST" >
<%
	final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	final SimpleDateFormat format_time = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	List<String> date_stamp_list = new LinkedList<String>();
	
	if(date_stamp != null && !date_stamp.trim().isEmpty()) {
		for(String e : date_stamp.split(",")) {
			date_stamp_list.add(e.trim());
		}
	}

	Map<String, Number> steps_of_map = new HashMap<String, Number>();
	Map<String, Number> min_of_map = new HashMap<String, Number>();
	Map<String, Number> max_of_map = new HashMap<String, Number>();
	
	if(steps_of != null && !steps_of.isEmpty()) {
		String splitter = steps_of.substring(1, steps_of.length() -1);
		for(String e : splitter.split(",")) {
			String[] t = e.split(":");
			steps_of_map.put(t[0].trim(), new BigDecimal(t[1]));
		}
	}
	
	if(min_of != null && !min_of.isEmpty()) {
		String splitter = min_of.substring(1, min_of.length() -1);
		for(String e : splitter.split(",")) {
			String[] t = e.split(":");
			min_of_map.put(t[0].trim(), new BigInteger(t[1]));
		}
	}
	
	if(max_of != null && !max_of.isEmpty()) {
		String splitter = max_of.substring(1, max_of.length() -1);
		for(String e : splitter.split(",")) {
			String[] t = e.split(":");
			max_of_map.put(t[0].trim(), new BigInteger(t[1]));
		}
	}
	
	List<String> areas = new ArrayList<String>();
	if(areaFields != null) {
		areas.addAll(Arrays.asList(areaFields.split(","))); }
	
	Class<? extends DomainEntity> clazz = entity.getClass();
	if(type == null || type.trim().isEmpty()) {
		type = "create"; }
	
	boolean show = type.equalsIgnoreCase("edit");
	
	List<Class<? extends DomainEntity>> another = new LinkedList<Class<? extends DomainEntity>>();
	List<Field> fields = new LinkedList<Field>(Arrays.asList(clazz.getDeclaredFields()));
	
	if(another_mapped_classes != null && !another_mapped_classes.trim().isEmpty()) {
		for(String e : another_mapped_classes.split(",")) {
			fields.addAll(Arrays.asList(((Class<? extends DomainEntity>) Class.forName(e.trim())).getDeclaredFields()));
		}
	}
	
	List<String> skiped = new LinkedList<String>();
	
	if(skip_fields != null && !skip_fields.trim().isEmpty()) {
		skiped.addAll(Arrays.asList(skip_fields.split(",")));
	}
	
	List<String> hidden_fields_list = new LinkedList<String>();
	
	if(hiddenFields != null && !hiddenFields.trim().isEmpty()) {
		hidden_fields_list = Arrays.asList(hiddenFields.split(","));
	}
	
%>

<%
	if(show) {
%>
		<input type="hidden" name="id" value="<%=show ? entity.getId() : ""%>">
		<input type="hidden" name="version" value="<%=show ? entity.getVersion() : ""%>">
<%
	}
%>

<%
	for(Field e : fields) {
		
		if(skiped.contains(e.getName())) {
			continue; }
		
		if(Modifier.isStatic(e.getModifiers())) {
			continue;
		}
		
		if(show) {
			e.setAccessible(true); }
		
		if(hidden_fields_list.contains(e.getName())) {
			
%>
		<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>

<%
		if(DomainEntity.class.isAssignableFrom(e.getType())) {
%>
			<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>

<%
	// An exceptional case
	if(show && UserAccount.class.isAssignableFrom(e.getType())) {
%>
		<form:hidden path="<%=e.getName() %>"/>
<%
		continue;
	}
%>

<%
		if(Collection.class.isAssignableFrom(e.getType())) {
%>
			<form:hidden path="<%=e.getName() %>"/>
<%
			continue;
		}
%>
		<div class="form-group" style="width: 55%;">
<%
			if(!Boolean.class.isAssignableFrom(e.getType())) {
%>
			<label for="label"><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' /></label>
<%
			}
%>
<%
			if(e.getType().equals(String.class)) {
				if(areas.contains(e.getName())) {
%>
					<textarea name="<%=e.getName()%>" style="resize: none;" class="form-control" rows="5" id="<%=e.getName()%>"><%=show ? e.get(entity) : "" %></textarea>
<%
				} else {
%>
					<input value="<%=show ? e.get(entity) : "" %>" name="<%=e.getName()%>" type="text" class="form-control" id="<%=e.getName()%>">
<%
			} } else if(Number.class.isAssignableFrom(e.getType())) {
				Number min = numberMin;
				Number max = numberMax;
				Number step = numberSteps;
				
				if(min_of_map.containsKey(e.getName())) {
					min = min_of_map.get(e.getName());
				}
				
				if(max_of_map.containsKey(e.getName())) {
					max = max_of_map.get(e.getName());
				}
				
				if(steps_of_map.containsKey(e.getName())) {
					step = steps_of_map.get(e.getName());
				}
%>
				<input value="<%=show ? e.get(entity) : "" %>" name="<%=e.getName()%>" type="number" <%=min != null ? "min='" + min + "'" : ""%> <%=max != null ? "max='" + max + "'" : ""%> <%=step != null ? "step='" + step + "'" : ""%> class="form-control" id="<%=e.getName()%>">
<%
			} else if(Date.class.isAssignableFrom(e.getType())) {
%>
				<input placeholder="<%=date_stamp_list.contains(e.getName()) ? "dd/MM/yyyy hh:mm:ss" : "dd/MM/yyyy" %>" value="<%=show ? date_stamp_list.contains(e.getName()) ? format_time.format(e.get(entity)) : format.format(e.get(entity)) : "" %>" name="<%=e.getName()%>" type="text" class="form-control" id="<%=e.getName()%>">
<%
			} else if(Boolean.class.isAssignableFrom(e.getType())) {
%>
				<div class="checkbox">
				  <label><input <%=show ? Boolean.valueOf(e.get(entity).toString()) ? "checked" : "" : "" %> type="checkbox" value=""><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' /></label>
				</div>
<%
			}
%>
		</div>
<%
	}
%>

	<%getJspBody().invoke(out); %>

<%
	String saved_text = save_button_msg != null ? save_button_msg : "acme.save";
%>
	<input name="save" type="submit" class="btn btn-primary" value="<spring:message code="<%=saved_text %>"/>">
<%
	if(cancel != null) {
		//<input onclick="${cancel}" type="button" class="btn btn-warning" value="<spring:message code="acme.cancel" />">
%>
	<input onclick="window.history.back()" type="button" class="btn btn-warning" value="<spring:message code="acme.cancel" />">
<%
	} else {
%>
	<input onclick="window.history.back()" type="button" class="btn btn-warning" value="<spring:message code="acme.cancel" />">
<%
	}
%>
	<button type="reset" class="btn btn-danger" value="Reset"><spring:message code="acme.clear"/></button>

</form:form>