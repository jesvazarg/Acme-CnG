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
	<form:form method="post" action="offer/customer/edit.do" modelAttribute="offer" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="banned" />
	<form:hidden path="postedToComments"/>
	
	<acme:input code="offer.title" path="title" />
	<acme:input code="offer.description" path="description" />
	<acme:input code="offer.movingMoment" path="movingMoment" />
	
	<fieldset>
				<legend align="left">
					<spring:message code="offer.originPlace" />
				</legend>
				<acme:input code="offer.originPlace.address" path="originPlace.address" />
				<acme:input code="offer.originPlace.latitude" path="originPlace.latitude"/>
				<acme:input code="offer.originPlace.longitude" path="originPlace.longitude"/>				
	</fieldset>
	
	<fieldset>
				<legend align="left">
					<spring:message code="offer.destinationPlace" />
				</legend>
				<acme:input code="offer.destinationPlace.address" path="destinationPlace.address" />
				<acme:input code="offer.destinationPlace.latitude" path="destinationPlace.latitude" />
				<acme:input code="offer.destinationPlace.longitude" path="destinationPlace.longitude" />				
	</fieldset>
		
	
	
	<acme:submit name="save" code="offer.save" />
	<jstl:if test="${offer.id!=0 }">
		<acme:submit name="delete" code="offer.delete" />
	</jstl:if>
	
	<acme:cancel url="offer/customer/list.do" code="offer.cancel" />
	
</form:form>
</security:authorize>
