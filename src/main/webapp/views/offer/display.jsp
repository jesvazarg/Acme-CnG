<%--
 * action-1.jsp
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

<div>
	<ul>
		<li>
			<b><spring:message code="offer.title" /></b>
			<jstl:out value="${offer.title}"/>
		</li>
		<li>
			<b><spring:message code="offer.description" /></b>
			<jstl:out value="${offer.description}"/>
		</li>
		<li>
			<b><spring:message code="offer.movingMoment" /></b>
			<jstl:out value="${offer.movingMoment}"/>
		</li>
		<li>
			<b><spring:message code="offer.originPlace" /></b>
			<jstl:out value="${offer.originPlace.address}"/>
			<jstl:out value="."/>
			<jstl:out value="Coordenadas: "/>
			<jstl:out value="("/>
			<jstl:out value="${offer.originPlace.latitude}"/>
			<jstl:out value=","/>
			<jstl:out value="${offer.originPlace.longitude}"/>
			<jstl:out value=")"/>
		</li>
		
		<li>
			<b><spring:message code="offer.destinationPlace" /></b>
			<jstl:out value="${offer.destinationPlace.address}"/>
			<jstl:out value="."/>
			<jstl:out value="Coordenadas: "/>
			<jstl:out value="("/>
			<jstl:out value="${offer.originPlace.latitude}"/>
			<jstl:out value=","/>
			<jstl:out value="${offer.originPlace.longitude}"/>
			<jstl:out value=")"/>
		</li>
		
	</ul>
</div>

<security:authorize access="hasRole('CUSTOMER')">
	<div>
		<jstl:if test="${principalUserAccount.id == offer.customer.userAccount.id &&  offer.banned=='false'}">
			<li><a href="apply/customer/create.do?offerId=${offer.id}">
				<spring:message code="offer.apply"/>
			</a></li>
		</jstl:if>
	</div>
</security:authorize>
