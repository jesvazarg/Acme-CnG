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

<security:authorize access="hasRole('CUSTOMER')">
	<form:form method="post" action="request/customer/edit.do" modelAttribute="request" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="banned" />
	<form:hidden path="postedToComments"/>
	
	<acme:input code="request.title" path="title" />
	<acme:input code="request.description" path="description" />
	<acme:input code="request.movingMoment" path="movingMoment" /><spring:message code="movingMoment.format" />
	
	<fieldset>
				<legend align="left">
					<spring:message code="request.originPlace" />
				</legend>
				<acme:input code="request.originPlace.address" path="originPlace.address" />
				<acme:input code="request.originPlace.latitude" path="originPlace.latitude"/>
				<acme:input code="request.originPlace.longitude" path="originPlace.longitude"/>				
	</fieldset>
	
	<fieldset>
				<legend align="left">
					<spring:message code="request.destinationPlace" />
				</legend>
				<acme:input code="request.destinationPlace.address" path="destinationPlace.address" />
				<acme:input code="request.destinationPlace.latitude" path="destinationPlace.latitude" />
				<acme:input code="request.destinationPlace.longitude" path="destinationPlace.longitude" />				
	</fieldset>
		
	
	
	<acme:submit name="save" code="request.save" />
	<jstl:if test="${request.id!=0 }">
		<acme:submit name="delete" code="request.delete" />
	</jstl:if>
	
	<acme:cancel url="request/customer/list.do" code="request.cancel" />
	
</form:form>
</security:authorize>
