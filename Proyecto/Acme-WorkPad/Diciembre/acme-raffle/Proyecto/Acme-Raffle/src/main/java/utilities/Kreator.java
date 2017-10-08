
package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import domain.DomainEntity;

/**
 * @author jvz19
 * @version 1.0
 *          <p>
 *          This is a sample class that create a default controller and view and configure all
 *          </p>
 */

public class Kreator {

	static final void create_controller(Class<? extends DomainEntity> clazz) throws Exception {
		File currentDirFile = new File(".");
		StringBuilder str = new StringBuilder(currentDirFile.getAbsolutePath());
		str.setLength(str.length() - 1);
		str.append(String.format("//src//main//java//controllers//%sController.java", clazz.getSimpleName()));

		PrintWriter pr = new PrintWriter(new FileOutputStream(new File(str.toString())));
		pr.println("package controllers;");
		pr.println();
		pr.println("import java.util.*;");
		pr.println();
		pr.println("import javax.validation.Valid;");
		pr.println();
		pr.println("import org.springframework.beans.factory.annotation.Autowired;");
		pr.println("import org.springframework.stereotype.Controller;");
		pr.println("import org.springframework.web.bind.annotation.RequestMapping;");
		pr.println("import org.springframework.web.servlet.ModelAndView;");
		pr.println("import org.springframework.web.bind.annotation.RequestMethod;");
		pr.println("import org.springframework.web.bind.annotation.RequestParam;");
		pr.println("import org.springframework.validation.BindingResult;");

		pr.println();
		pr.println(String.format("import domain.%s;", clazz.getSimpleName()));
		pr.println();
		pr.println(String.format("import services.%sService;", clazz.getSimpleName()));
		pr.println();
		pr.println("@Controller");
		pr.println(String.format("@RequestMapping(\"/%s\")", clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("public class %sController {", clazz.getSimpleName()));
		pr.println();

		pr.println("\t@Autowired");
		pr.println(String.format("\t%sService %sService;", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));

		pr.println();

		pr.println(String.format("\t@RequestMapping(value = \"/%s/create\", method = RequestMethod.GET)", user_name));
		pr.println("\tpublic ModelAndView create() {");
		pr.println("\t\tModelAndView result;");
		pr.println();
		pr.println(String.format("\t\tresult = createNewModelAndView(%sService.create(), null);", clazz.getSimpleName().toLowerCase()));
		pr.println();
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\t@RequestMapping(value=\"/%s/save\", method=RequestMethod.POST, params = \"save\")", user_name));
		pr.println(String.format("\tpublic ModelAndView saveCreate(@Valid %s %s, BindingResult binding) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println("\tModelAndView result;");
		pr.println("\t\tif (binding.hasErrors()) {");
		pr.println(String.format("\t\t\tresult = createEditModelAndView(%s, null);", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t} else {");
		pr.println("\t\t\ttry {");
		pr.println(String.format("\t\t\t\t%sService.save(%s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\t\t\tresult = new ModelAndView(\"redirect:/%s/list.do\");", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t\t} catch (Throwable th) {");
		pr.println(String.format("\t\t\t\tresult = createEditModelAndView(%s, \"%s.commit.error\");", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t\t}");
		pr.println("\t\t}");
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\tprotected ModelAndView createNewModelAndView(%s %s, String message) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\tModelAndView result;");
		pr.println(String.format("\t\tresult = new ModelAndView(\"%s/create\");", clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\tresult.addObject(\"%s\", %s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\tresult.addObject(\"message\", message);");
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println("\t@RequestMapping(value = \"/list\", method = RequestMethod.GET)");
		pr.println("\tpublic ModelAndView list() {");
		pr.println("\t\tModelAndView result;");
		pr.println();
		pr.println(String.format("\t\tresult = new ModelAndView(\"%s/list\");", clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\tresult.addObject(\"%s\", %sService.findAll());", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println();
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\t@RequestMapping(value = \"/%s/edit\", method = RequestMethod.GET)", user_name));
		pr.println(String.format("\tpublic ModelAndView edit(@RequestParam %s %s) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\tModelAndView result;");
		pr.println(String.format("\t\tresult = new ModelAndView(\"%s/edit\");", clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\tresult.addObject(\"%s\", %s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\t@RequestMapping(value = \"/%s/edit\", method = RequestMethod.POST,params = \"delete\")", user_name));
		pr.println(String.format("\tpublic ModelAndView deleteEdit(@Valid %s %s) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\tModelAndView result;");
		pr.println();
		pr.println("\t\ttry {");
		pr.println(String.format("\t\t\t%sService.delete(%s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\t\tresult = new ModelAndView(\"redirect:/%s/list.do\");", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t} catch (Throwable th) {");
		pr.println(String.format("\t\t\tresult = createEditModelAndView(%s, \"%s.commit.error\");", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t}");
		pr.println();
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\t@RequestMapping(value=\"/%s/edit\", method=RequestMethod.POST, params = \"save\")", user_name));
		pr.println(String.format("\tpublic ModelAndView saveEdit(@Valid %s %s, BindingResult binding) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println("\tModelAndView result;");
		pr.println("\tif (binding.hasErrors()) {");
		pr.println(String.format("\t\tresult = createEditModelAndView(%s, null);", clazz.getSimpleName().toLowerCase()));
		pr.println("\t} else {");
		pr.println("\t\ttry {");
		pr.println(String.format("\t\t\t%sService.save(%s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\t\t\tresult = new ModelAndView(\"redirect:/%s/list.do\");", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t\t} catch (Throwable th) {");
		pr.println(String.format("\t\t\t\tresult = createEditModelAndView(%s, \"%s.commit.error\");", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t\t}");
		pr.println("\t\t}");
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println(String.format("\tprotected ModelAndView createEditModelAndView(%s %s, String message) {", clazz.getSimpleName(), clazz.getSimpleName().toLowerCase()));
		pr.println(String.format("\t\tModelAndView result = new ModelAndView(\"%s/edit\");", clazz.getSimpleName().toLowerCase()));
		pr.println();
		pr.println(String.format("\t\tresult.addObject(\"%s\", %s);", clazz.getSimpleName().toLowerCase(), clazz.getSimpleName().toLowerCase()));
		pr.println("\t\tresult.addObject(\"message\", message);");
		pr.println();
		pr.println("\t\treturn result;");
		pr.println("\t}");

		pr.println();
		pr.println("}");
		pr.flush();
		pr.close();

		System.err.println(String.format(">> The %sController are created in 'controllers.%sController.java'", clazz.getSimpleName(), clazz.getSimpleName()));
	}

	static final void create_security(Class<? extends DomainEntity> clazz) throws Exception {
		File currentDirFile = new File(".");
		StringBuilder str = new StringBuilder(currentDirFile.getAbsolutePath());
		str.setLength(str.length() - 1);
		str.append("//src//main//resources//spring//config//security.xml");

		final File file = new File(str.toString());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		Element root = doc.getDocumentElement();

		Node n = root.getElementsByTagName("security:http").item(0);

		Comment comment = doc.createComment(String.format("There are the %s urls by '%s'", clazz.getSimpleName().toLowerCase(), System.getProperty("user.name")));
		n.appendChild(comment);
		n.appendChild(insert_node(clazz, doc, comment, "create"));
		n.appendChild(insert_node(clazz, doc, comment, "edit"));
		n.appendChild(insert_node_all(clazz, doc, comment, "list"));
		n.appendChild(insert_node_all(clazz, doc, comment, "save"));

		printDocument(doc, new FileOutputStream(file));

		System.err.println(String.format(">> The urls are added in 'security.xml'", clazz.getSimpleName(), clazz.getSimpleName()));
	}

	private static final Node insert_node(Class<? extends DomainEntity> clazz, Document doc, Node n, String method) {
		Element create = doc.createElement("security:intercept-url");
		create.setAttribute("pattern", String.format("/%s/%s/%s.do", clazz.getSimpleName().toLowerCase(), user_name, method));

		if (roles.length > 1) {
			StringBuilder str_bldr = new StringBuilder();

			for (int i = 0; i < roles.length; i++) {
				str_bldr.append(String.format("'%s'", roles[i]));
				str_bldr.append(i < roles.length - 1 ? "," : "");
			}

			create.setAttribute("access", String.format("hasAnyRole(%s)", str_bldr));
		} else {
			create.setAttribute("access", String.format("hasRole('%s')", roles[0]));
		}

		return create;
	}

	private static final Node insert_node_all(Class<? extends DomainEntity> clazz, Document doc, Node n, String method) {
		Element create = doc.createElement("security:intercept-url");
		create.setAttribute("pattern", String.format("/%s/%s/%s.do", clazz.getSimpleName().toLowerCase(), user_name, method));

		create.setAttribute("access", "permitAll");

		return create;
	}

	private final static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

	static final void createViews(Class<? extends DomainEntity> clazz) throws Exception {
		File currentDirFile = new File(".");
		StringBuilder str = new StringBuilder(currentDirFile.getAbsolutePath());
		str.setLength(str.length() - 1);
		str.append(String.format("//src//main//webapp//views//%s", clazz.getSimpleName().toLowerCase()));

		final File file = new File(str.toString());
		file.mkdir();

		create_messages_for(clazz, null, file);
		create_messages_for(clazz, "es", file);

		create_tiles_for(clazz, null, file);
		create_tiles_for(clazz, "es", file);

		create_list_view(clazz, file);
		create_create_view(clazz, file);
		create_edit_view(clazz, file);

		System.err.println(String.format(">> The views are created in 'views/%s'", clazz.getSimpleName().toLowerCase()));
	}

	private static final void create_list_view(Class<? extends DomainEntity> clazz, File file) throws Exception {
		File out = new File(file.getPath() + File.separator + "list.jsp");

		PrintWriter pr = new PrintWriter(new FileOutputStream(out));
		pr.println("<%@page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>");
		pr.println();
		pr.println("<%@taglib prefix=\"jstl\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
		pr.println("<%@taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\"%>");

		pr.println("<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
		pr.println("<%@taglib prefix=\"security\" uri=\"http://www.springframework.org/security/tags\"%>");
		pr.println("<%@taglib prefix=\"display\" uri=\"http://displaytag.sf.net\"%>");
		pr.println("<%@ taglib prefix=\"acme\" tagdir=\"/WEB-INF/tags\"%>");
		pr.println();
		pr.println();

		Map<String, String> entityUrl = new HashMap<String, String>();
		for (Field e : clazz.getDeclaredFields()) {
			if (DomainEntity.class.isAssignableFrom(e.getType())) {
				entityUrl.put(e.getName(), String.format("%s/view.do", e.getType().getSimpleName().toLowerCase()));
			} else if (Collection.class.isAssignableFrom(e.getType()) && e.getGenericType().toString().contains("domain.")) {
				StringBuilder repaired = new StringBuilder(e.getGenericType().toString());
				repaired.delete(0, repaired.indexOf("domain.") + "domain.".length());
				repaired.setLength(repaired.length() - 1);

				entityUrl.put(e.getName(), String.format("%s/list.do", repaired.toString().toLowerCase()));
			}
		}

		String deleteUrl = String.format("%s/%s/list.do", clazz.getSimpleName().toLowerCase(), user_name);
		String editUrl = String.format("%s/%s/edit.do", clazz.getSimpleName().toLowerCase(), user_name);
		String requestURI = String.format("%s/list.do", clazz.getSimpleName().toLowerCase());

		pr.println(String.format("<acme:list entityUrl=\"%s\" list=\"${%s}\" deleteUrl=\"%s\" editUrl=\"%s\" requestURI=\"%s\" pagesize=\"6\"/>", entityUrl.toString().replace("=", ":"), clazz.getSimpleName().toLowerCase(), deleteUrl, editUrl, requestURI));

		pr.flush();
		pr.close();
	}

	private static final void create_create_view(Class<? extends DomainEntity> clazz, File file) throws Exception {
		File out = new File(file.getPath() + File.separator + "create.jsp");

		PrintWriter pr = new PrintWriter(new FileOutputStream(out));
		pr.println("<%@page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>");
		pr.println();
		pr.println("<%@taglib prefix=\"jstl\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
		pr.println("<%@taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\"%>");

		pr.println("<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
		pr.println("<%@taglib prefix=\"security\" uri=\"http://www.springframework.org/security/tags\"%>");
		pr.println("<%@taglib prefix=\"display\" uri=\"http://displaytag.sf.net\"%>");
		pr.println("<%@ taglib prefix=\"acme\" tagdir=\"/WEB-INF/tags\"%>");
		pr.println();
		pr.println();

		String url = String.format("%s/%s/save.do", clazz.getSimpleName().toLowerCase(), user_name);

		pr.println(String.format("<acme:acme_form type=\"create\" entity=\"${%s}\" url=\"%s\" numberSteps=\"0.25\" cancel=\"welcome/index.do\"/>", clazz.getSimpleName().toLowerCase(), url));

		pr.flush();
		pr.close();
	}

	private static final void create_edit_view(Class<? extends DomainEntity> clazz, File file) throws Exception {
		File out = new File(file.getPath() + File.separator + "edit.jsp");

		PrintWriter pr = new PrintWriter(new FileOutputStream(out));
		pr.println("<%@page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>");
		pr.println();
		pr.println("<%@taglib prefix=\"jstl\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
		pr.println("<%@taglib prefix=\"spring\" uri=\"http://www.springframework.org/tags\"%>");

		pr.println("<%@taglib prefix=\"form\" uri=\"http://www.springframework.org/tags/form\"%>");
		pr.println("<%@taglib prefix=\"security\" uri=\"http://www.springframework.org/security/tags\"%>");
		pr.println("<%@taglib prefix=\"display\" uri=\"http://displaytag.sf.net\"%>");
		pr.println("<%@ taglib prefix=\"acme\" tagdir=\"/WEB-INF/tags\"%>");
		pr.println();
		pr.println();

		String url = String.format("%s/%s/edit.do", clazz.getSimpleName().toLowerCase(), user_name);

		pr.println(String.format("<acme:acme_form type=\"edit\" entity=\"${%s}\" url=\"%s\" numberSteps=\"0.25\" cancel=\"welcome/index.do\"/>", clazz.getSimpleName().toLowerCase(), url));

		pr.flush();
		pr.close();
	}

	private static final void create_tiles_for(Class<? extends DomainEntity> clazz, String lang, File file) throws Exception {
		String file_name = lang == null ? "tiles.xml" : String.format("tiles_%s.xml", lang);
		File out = new File(file.getPath() + File.separator + file_name);

		PrintWriter pr = new PrintWriter(new FileOutputStream(out));

		pr.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		pr.println();
		pr.println("<!DOCTYPE tiles-definitions PUBLIC");
		pr.println("\t\"-//Apache Software Foundation//DTD Tiles Configuration 2.0//EN\"");
		pr.println("\t\"http://tiles.apache.org/dtds/tiles-config_2_0.dtd\">");
		pr.println();

		pr.println("<tiles-definitions>");
		pr.println();

		pr.println(String.format("\t<definition name=\"%s/edit\" extends=\"master.page\">", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t<put-attribute name=\"title\" value=\"view name\" />");
		pr.println(String.format("\t\t<put-attribute name=\"body\" value=\"/views/%s/edit.jsp\" />", clazz.getSimpleName().toLowerCase()));
		pr.println("\t</definition>");
		pr.println();

		pr.println(String.format("\t<definition name=\"%s/create\" extends=\"master.page\">", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t<put-attribute name=\"title\" value=\"view name\" />");
		pr.println(String.format("\t\t<put-attribute name=\"body\" value=\"/views/%s/create.jsp\" />", clazz.getSimpleName().toLowerCase()));
		pr.println("\t</definition>");
		pr.println();

		pr.println(String.format("\t<definition name=\"%s/list\" extends=\"master.page\">", clazz.getSimpleName().toLowerCase()));
		pr.println("\t\t<put-attribute name=\"title\" value=\"view name\" />");
		pr.println(String.format("\t\t<put-attribute name=\"body\" value=\"/views/%s/list.jsp\" />", clazz.getSimpleName().toLowerCase()));
		pr.println("\t</definition>");
		pr.println();

		pr.println("</tiles-definitions>");

		pr.flush();
		pr.close();
	}

	private static final void create_messages_for(Class<? extends DomainEntity> clazz, String lang, File file) throws Exception {
		String file_name = lang == null ? "messages.properties" : String.format("messages_%s.properties", lang);
		File out = new File(file.getPath() + File.separator + file_name);

		PrintWriter pr = new PrintWriter(out, "ISO-8859-1");

		if (lang == null) {
			pr.println(String.format("%s.commit.error\t\t\t\t\t=\tFailed to commit", clazz.getSimpleName().toLowerCase()));
		} else {
			pr.println(String.format("%s.commit.error\t\t\t\t\t=\tError al hacer commit", clazz.getSimpleName().toLowerCase()));
		}

		for (Field e : clazz.getDeclaredFields()) {
			pr.println(String.format("%s.%s%s=\tnone", clazz.getSimpleName().toLowerCase(), e.getName(), tab_of(e.getName())));
		}

		pr.flush();
		pr.close();
	}

	private static final String tab_of(String str) {
		int var = (int) Math.ceil(str.length() / 4.0);

		switch (var) {
		case 6:
			return "\t\t";
		case 5:
			return "\t\t\t";
		case 4:
			return "\t\t\t\t";
		case 3:
			return "\t\t\t\t\t";
		case 2:
			return "\t\t\t\t\t\t";
		case 1:
			return "\t\t\t\t\t\t\t";

		default:
			return "\t";
		}
	}

	static final void configure_tiles(Class<? extends DomainEntity> clazz) throws Exception {
		File currentDirFile = new File(".");
		StringBuilder str = new StringBuilder(currentDirFile.getAbsolutePath());
		str.setLength(str.length() - 1);
		str.append("//src//main//resources//spring//config//tiles.xml");

		final File file = new File(str.toString());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		Element root = doc.getDocumentElement();

		Node n = root.getElementsByTagName("util:list").item(0);

		Element create = doc.createElement("value");
		create.setTextContent(String.format("/views/%s/tiles.xml", clazz.getSimpleName().toLowerCase()));

		n.appendChild(create);

		printDocument(doc, new FileOutputStream(file));

		System.err.println(String.format(">> tiles.xml was configured", clazz.getSimpleName().toLowerCase()));
	}

	static final void configure_i18n(Class<? extends DomainEntity> clazz) throws Exception {
		File currentDirFile = new File(".");
		StringBuilder str = new StringBuilder(currentDirFile.getAbsolutePath());
		str.setLength(str.length() - 1);
		str.append("//src//main//resources//spring//config//i18n-l10n.xml");

		final File file = new File(str.toString());

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		Element root = doc.getDocumentElement();

		Node n = root.getElementsByTagName("util:list").item(0);

		Element create = doc.createElement("value");
		create.setTextContent(String.format("/views/%s/messages", clazz.getSimpleName().toLowerCase()));

		n.appendChild(create);

		printDocument(doc, new FileOutputStream(file));

		System.err.println(String.format(">> i18n-l10n.xml was configured", clazz.getSimpleName().toLowerCase()));
	}


	private static final String		class_name	= "School";
	private static final String		user_name	= "administrator";
	private static final String[]	roles		= new String[] {
		"ADMINISTRATOR"
	};


	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		long i = System.currentTimeMillis();

		final Class<? extends DomainEntity> clazz = (Class<? extends DomainEntity>) Class.forName(String.format("domain.%s", class_name));

		create_controller(clazz);
		create_security(clazz);
		createViews(clazz);
		configure_tiles(clazz);
		configure_i18n(clazz);

		System.err.println(String.format(">> \tterminated in %d miliseconds", System.currentTimeMillis() - i));

	}
}
