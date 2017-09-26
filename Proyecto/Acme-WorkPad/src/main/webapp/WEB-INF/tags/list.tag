<!-- 
	author: jjvalle
	version: 3.1
	copyrigth: jjvalle (2017)
	license: creativecommons 4.0
	It is not allowed to use this code without the prior approval of the author
 -->

<%@tag import="javax.servlet.jsp.el.VariableResolver"%>
<%@tag import="java.io.PrintWriter"%>
<%@tag import="java.util.LinkedHashMap"%>
<%@tag import="java.util.Map.Entry"%>
<%@tag import="java.text.SimpleDateFormat"%>
<%@tag import="org.springframework.context.ApplicationContext"%>
<%@tag import="org.springframework.beans.factory.annotation.Autowired"%>
<%@tag import="org.springframework.context.MessageSource"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.HashMap"%>
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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%@ attribute name="requestURI" required="true" rtexprvalue="true" %>
<%@ attribute name="list" required="true" rtexprvalue="true" type="java.lang.Object" %>
<%@ attribute name="pagesize" required="false" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="hidden_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="entityUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="editUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="deleteUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="columNames" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="field_mapping" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="viewUrl" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="another_mapped_urls" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="image_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="edit_show_null_conditions" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="edit_auth" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="sortable" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="video_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="color_fields" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="extraColumns" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="variable" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="time_stamps" required="false" rtexprvalue="true" type="java.lang.String" %>
<!--
-- Color fields --
{field:value:color} -> if field has value set color of row
-->

<%
	if(pagesize == null) {
		pagesize = 10; }
%>

<%
	Collection<?> iterate;
	if(list instanceof Collection) {
		iterate = (Collection) list;
	} else {
		iterate = Arrays.asList(list);
	}

	if(iterate.isEmpty()) {
%>
	<display:table name="${iterate}" id="row" requestURI="${requestURI}" pagesize="${pagesize}" class="table table-over"></display:table>
<%
	} else {
		Map<String, String> time_stamps_map = new HashMap<String, String>();
		if(time_stamps != null && !time_stamps.trim().isEmpty()) {
			StringBuilder str = new StringBuilder(time_stamps);
			str.deleteCharAt(0);
			str.deleteCharAt(str.length() -1);
			
			for(String e : str.toString().split(",")) {
				String[] arr = e.split(":");
				time_stamps_map.put(arr[0].trim(), arr[1].trim());
			}
		}
		
		Map<String, String> extra = new LinkedHashMap<String, String>();
		if(extraColumns != null && !extraColumns.trim().isEmpty()) {
			for(String e : extraColumns.trim().substring(1, extraColumns.trim().length() -1).split(",")) {
				String[] arr = e.split(",");
				extra.put(arr[0].trim(), arr[1].trim());
			}
		}
		
		Map<String, Map<String, String>> field_value_map = new HashMap<String, Map<String, String>>();
		
		if(color_fields != null && !color_fields.trim().isEmpty()) {
			for(String e : color_fields.trim().substring(1, color_fields.length() -1).split(",")) {
				String[] arr = e.split(":");
				if(field_value_map.containsKey(arr[0].trim())) {
					Map<String, String> aux = field_value_map.get(arr[0].trim());
					aux.put(arr[1].trim(), arr[2].trim());
					
					field_value_map.put(arr[0].trim(), aux);
				} else {
					Map<String, String> aux = new HashMap<String, String>();
					aux.put(arr[1].trim(), arr[2].trim());
					field_value_map.put(arr[0].trim(), aux);
				}
			}
		}
		
		boolean sort = false;
		
		if(sortable != null && !sortable.trim().isEmpty()) {
			sort = Boolean.valueOf(sortable.trim());
		}
		
		List<String> video_fields_list = new LinkedList<String>();
		
		if(video_fields != null && !video_fields.trim().isEmpty()) {
			for(String e : video_fields.split(",")) {
				video_fields_list.add(e.trim());
			}
		}
		
		Map<String, String> another_mapped_urls_map = new HashMap<String, String>();
		if(another_mapped_urls != null && !another_mapped_urls.trim().isEmpty()) {
			for(String r : another_mapped_urls.substring(1, another_mapped_urls.length() -1).split(",")) {
				String[] str = r.split(":");
				another_mapped_urls_map.put(str[0].trim(), str[1].trim());
			}
		}
		
		List<String> edit_show_null_conditions_list = new LinkedList<String>();
		
		if(edit_show_null_conditions != null && !edit_show_null_conditions.trim().isEmpty()) {
			for(String e : edit_show_null_conditions.split(",")) {
				edit_show_null_conditions_list.add(e);
			}
		}
%>
	<display:table style="border:none;" name="${list}" id="row" requestURI="${requestURI}" pagesize="${pagesize}" class="table table-over">
<%
		DomainEntity entity = (DomainEntity) row;
		List<String> hidden = new LinkedList<String>();
		if(hidden_fields != null && !hidden_fields.trim().isEmpty()) {
			hidden.addAll(Arrays.asList(hidden_fields.split(",")));
			for(String s : hidden) {
				s = s.trim(); } }
		
		Map<String, String> map = new HashMap<String, String>();
		if(entityUrl != null && !entityUrl.trim().isEmpty()) {
			StringBuilder repaired = new StringBuilder(entityUrl);
			repaired.deleteCharAt(0);
			repaired.setLength(repaired.length() -1);
			
			if(repaired.length() > 0) {
				for(String u : repaired.toString().split(",")) {
					String[] arr = u.trim().split(":");
					map.put(arr[0], arr[1]);
				}
			}
		}
		
		List<Field> iterate_fields = new LinkedList<Field>(Arrays.asList(entity.getClass().getDeclaredFields()));
		
		if(entity.getClass().getSuperclass() != null && DomainEntity.class.isAssignableFrom(entity.getClass().getSuperclass()) && !entity.getClass().getSuperclass().equals(DomainEntity.class)) {
			iterate_fields.addAll(Arrays.asList(entity.getClass().getSuperclass().getDeclaredFields()));
		}
		
		for(Field e : iterate_fields) {
			e.setAccessible(true);
			
			if(hidden.contains(e.getName())) {
				continue;
			}
			
			Map<String, String> colums = new HashMap<String, String>();
			if(columNames != null && !columNames.trim().isEmpty()) {
				StringBuilder repaired = new StringBuilder(columNames);
				repaired.deleteCharAt(0);
				repaired.setLength(repaired.length() -1);
				
				for(String u : repaired.toString().split(",")) {
					String[] arr = u.trim().split(":");
					colums.put(arr[0], arr[1]);
				}
			}
			
			Map<String, String> mapping = new HashMap<String, String>();
			if(field_mapping != null && !field_mapping.trim().isEmpty()) {
				for(String r : field_mapping.substring(1, field_mapping.length() -1).split(",")) {
					String[] str = r.split(":");
					mapping.put(str[0].trim(), str[1].trim());
				}
			}
			
			if(image_fields == null) {
				image_fields = "";
			}
			
			Object obj = e.get(row);
			String style = "";
			
			if(obj != null && field_value_map.containsKey(e.getName())) {
				Object value = obj;
				if(mapping.containsKey(e.getName())) {
					Class<?> field_class = e.getType();
					
					Field f = field_class.getDeclaredField(mapping.get(e.getName()));
					f.setAccessible(true);
					value = f.get(obj);
				}
				
				if(field_value_map.get(e.getName()).containsKey(value)) {
					style = String.format("background:%s;", field_value_map.get(e.getName()).get(value.toString()));
				}
			}
			
%>
			<spring:message code='<%=colums.containsKey(e.getName()) ? colums.get(e.getName()) : map.containsKey(e.getName()) || !(e.get(row) instanceof DomainEntity) ? String.format("%s.%s", row.getClass().getSimpleName().toLowerCase(), e.getName()) : "acme.colum"%>' var="title" />
			<display:column title="${title}" sortable ="<%=sort%>" style="<%=style %>">
<%
				if(obj instanceof Collection) {
					Collection<?> collection = (Collection<?>) obj;
					if(!collection.isEmpty()) {
%>
<%
				if(collection.iterator().next() instanceof DomainEntity) {
					DomainEntity domain = (DomainEntity) collection.iterator().next();
%>
						<a href="<%=map.containsKey(e.getName()) ? map.get(e.getName()) + "?q=" + entity.getId() : "#"%>">
<%
						if(map.containsKey(e.getName())) {
%>
						<spring:message code='<%=row.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' /><br />
<%
						}
%>
						</a>
<%
				} else {
					for(Object d : collection) {
						Object val = d;
						
						if(val != null && video_fields_list.contains(e.getName())) {
							val = String.format("<iframe width='420' height='315' src='https://www.youtube.com/embed/%s'></iframe><br />", val.toString().replace("https://www.youtube.com/watch?v=", ""));
						}
						
						if(val != null && image_fields.contains(e.getName())) {
							val = String.format("<img src='%s' style='max-height: 128px; max-width: 128px;'><br />", val.toString());
						}
%>
						<%=val == null ? "" : val + "<br />"%>
<%
				} }
%>
<%
					} } else {
						Object val = e.get(entity);
						
						if(mapping.containsKey(e.getName())) {
							Class<?> field_class = e.getType();
							Field f = field_class.getDeclaredField(mapping.get(e.getName()));
							f.setAccessible(true);
							val = f.get(val);
						}
%>
<%
						if(map.containsKey(e.getName()) && obj instanceof DomainEntity) {
							
%>
							<a href="<%=map.get(e.getName()) + "?q=" + ((DomainEntity) obj).getId()%>">
								<spring:message code='<%=row.getClass().getSimpleName().toLowerCase() + "." + e.getName() %>' />
							</a>
<%
						} else {
%>
<%
						if(val instanceof Date) {
							SimpleDateFormat format;
							if(time_stamps_map.containsKey(e.getName())) {
								format = new SimpleDateFormat(time_stamps_map.get(e.getName()));
							} else {
								format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
							}
%>
							<%=val == null ? "" : format.format(val) %>
<%
						} else {
							if(val != null && video_fields_list.contains(e.getName())) {
								val = String.format("<iframe width='420' height='315' src='https://www.youtube.com/embed/%s'></iframe>", val.toString().replace("https://www.youtube.com/watch?v=", ""));
							}
%>
							<%=val == null ? "" : image_fields.contains(e.getName()) ? String.format("<img src='%s' style='max-height: 128px; max-width: 128px;'>", val.toString()) : val.toString() %>
<%
							}
						}
%>
<%
				}
%>
			</display:column>
<%
		}
%>
<%
		boolean show_edit = true;

		for(Field e : entity.getClass().getDeclaredFields()) {
			e.setAccessible(true);
			Object val = e.get(entity);
			if(edit_show_null_conditions_list.contains(e.getName()) && val != null) {
				show_edit = false;
				break;
			}
		}
		
		if(editUrl != null && show_edit) {
%>
<%
			if(edit_auth != null && !edit_auth.trim().isEmpty()) {
%>
		<security:authorize access="hasRole('<%=edit_auth%>')">
			<display:column sortable ="false">
				<a href="<%=editUrl + "?q=" + entity.getId()%>"><spring:message code="acme.edit"/></a>
			</display:column>
		</security:authorize>
<%
			} else {
%>
				<display:column sortable ="false">
					<a href="<%=editUrl + "?q=" + entity.getId()%>"><spring:message code="acme.edit"/></a>
				</display:column>
<%
			}
%>
<%
		}
%>
<%
		if(deleteUrl != null) {
%>
			<display:column sortable ="false">
				<a href="<%=deleteUrl + "?q=" + entity.getId()%>"><spring:message code="acme.delete"/></a>
			</display:column>
<%
		}
%>

<%
	if(viewUrl != null) {
%>
	<display:column sortable ="false">
		<a href="<%=viewUrl + "?q=" + entity.getId()%>"><spring:message code="acme.view"/></a>
	</display:column>
<%
	}
%>
<%
	for(Entry<String, String> entry : extra.entrySet()) {
%>
	<display:column sortable ="false">
		<a href="<%=entry.getValue()%>"><spring:message code="<%=entry.getKey()%>"/></a>
	</display:column>
<%
	}
%>

<%
	for(Entry<String, String> e : another_mapped_urls_map.entrySet()) {
%>
	<display:column sortable ="false">
		<a href="<%=e.getValue() + "?q=" + entity.getId()%>"><spring:message code="<%=e.getKey() %>"/></a>
	</display:column>
<%
	}
%>

	<display:column sortable ="false">
	<%
		if(getJspBody() != null) {
			JspContext context = getJspBody().getJspContext();
			
			if(variable != null) {
				VariableResolver resolver = context.getVariableResolver();
				request.setAttribute(variable, resolver.resolveVariable("row"));
			}
			
			getJspBody().invoke(out);
		}
	%>
	</display:column>

	</display:table>
<%
	}
%>
