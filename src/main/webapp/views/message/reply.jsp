<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form method="post" action="message/actor/forward.do" modelAttribute="messageEmail" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sentMoment" />
	<form:hidden path="folder" />
	<form:hidden path="sender" />
	<form:hidden path="title" />
	<form:hidden path="text" />
	<form:hidden path="attachments" />

	
	<acme:select items="${recipients}" itemLabel="name" code="message.recipient" path="recipient" />
	
	
	
	<acme:submit name="save" code="message.save" />
	
	<acme:cancel url="folder/actor/list/outBox.do" code="message.cancel" />
	
</form:form>