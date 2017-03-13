<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="isAuthenticated()">
	<form:form action="${requestURI}" modelAttribute="createActorForm">
		<acme:input code="profile.username" path="username" />
		<acme:password code="profile.password" path="password" />
		<acme:password code="profile.confirm" path="confirm" />
		<acme:input code="profile.name" path="name" />
		<acme:input code="profile.email" path="email" />
		<acme:input code="profile.phoneNumber" path="phoneNumber" />
		
		<acme:submit name="save" code="profile.save" />
		<acme:cancel url="profile/display.do?actorId=${profile.id}" code="profile.cancel" />
	</form:form>
</security:authorize>
