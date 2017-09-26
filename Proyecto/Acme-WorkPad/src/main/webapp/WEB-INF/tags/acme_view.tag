<!-- 
	author: jjvalle
	version: 1.5
	copyrigth: jjvalle (2017)
	license: creativecommons 4.0
	It is not allowed to use this code without the prior approval of the author
 -->

<%@tag import="java.util.List"%>
<%@tag import="java.util.LinkedList"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@tag import="java.util.Date"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="domain.DomainEntity"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@tag language="java" body-content="scriptless" %>

<%@ attribute name="entity" required="true" rtexprvalue="true" type="domain.DomainEntity" %>
<%@ attribute name="field_mapping" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="image_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="skip_fields" required="false" rtexprvalue="true" type="java.lang.String" %>

<table class="table">

<%
	if(entity != null) {
		Map<String, String> mapping = new HashMap<String, String>();
		if(field_mapping != null && !field_mapping.trim().isEmpty()) {
			for(String e : field_mapping.substring(1, field_mapping.length() -1).split(",")) {
				String[] str = e.split(":");
				mapping.put(str[0].trim(), str[1].trim());
			}
		}
		
		if(image_fields == null) {
			image_fields = "";
		}
		
		List<String> skip_fields_list = new LinkedList<String>();
		if(skip_fields != null && !skip_fields.trim().isEmpty()) {
			for(String e : skip_fields.split(",")) {
				skip_fields_list.add(e.trim());
			}
		}
		
		for(Field e : entity.getClass().getDeclaredFields()) {
			if(skip_fields_list.contains(e.getName())) {
				continue;
			}
			
			e.setAccessible(true);
%>
<%
			if(DomainEntity.class.isAssignableFrom(e.getType())) {
				continue;
			}

			Object val = e.get(entity);
			if(val instanceof Date) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				val = format.format(val);
			}
			
			if(mapping.containsKey(e.getName())) {
				Class<?> field_class = e.getType();
				Field f = field_class.getDeclaredField(mapping.get(e.getName()));
				f.setAccessible(true);
				val = f.get(val);
			}
			
%>
				<tr>
					<td><spring:message code='<%=entity.getClass().getSimpleName().toLowerCase() + "." + e.getName()%>'/></td>
					<td><%=val == null ? "" : image_fields.contains(e.getName()) ? String.format("<img src='%s' style='max-height: 128px; max-width: 128px;'>", val.toString()) : val%></td>
				</tr>
<%
		}
	}
%>

<%
	if(getJspBody() != null) {
		getJspBody().invoke(out); 
	}
%>
</table>