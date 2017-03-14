<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<ul>
		<li>
			<b><spring:message code="message.sentMoment"/>:</b>
			<jstl:out value="${thisMessage.sentMoment}" />
		</li>
		
		<li>
			<b><spring:message code="message.title"/>:</b>
			<jstl:out value="${thisMessage.title}"/>
		</li>
		
		<li>
			<b><spring:message code="message.text"/>:</b>
			<jstl:out value="${thisMessage.text}"/>
		</li>
		
		<li>
			<b><spring:message code="message.sender" />:</b>
			<jstl:out value="${thisMessage.sender.name}" />
		</li>
		
		<li>
			<b><spring:message code="message.recipient" />:</b>
			<jstl:out value="${thisMessage.recipient.name}" />
		</li>
		
		<li>
			<b><spring:message code="message.attachments" />:</b>
			<jstl:out value="${thisMessage.attachments}" />
		</li>
		
	</ul>
	
</div>

<jstl:if test="${isRecipient}">
	<div>
		<a href="actor/message/response.do?messageId=${thisMessage.id}"><spring:message
				code="message.response" /></a>
	</div>
</jstl:if>