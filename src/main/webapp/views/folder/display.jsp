<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="messagesFolder" id="row" requestURI="${requestURI }" class="displaytag">
	
	<acme:column code="message.sentMoment" property="sentMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="message.title" property="title" />
	<acme:column code="message.sender" property="sender.name" />
	
	<display:column>
		<a href="actor/displayMessage.do?messageId=${row.id} "><spring:message code="message.display" /></a>
	</display:column>
	
</display:table>