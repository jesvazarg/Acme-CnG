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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="offers" id="offer" requestURI="${requestURI }" class="displaytag">
	
	<acme:column code="offer.title" property="title"/>
	<acme:column code="offer.description" property="description"/>
	<acme:column code="offer.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="offer.originPlace" property="originPlace" />
	<acme:column code="offer.destinationPlace" property="destinationPlace" />
	
	<jstl:if test="${general!=true}">
				<acme:column code="offer.banned" property="banned"/>
	</jstl:if>
			
	<display:column>
		<a href="offer/customer/display.do?offerId=${offer.id}"><spring:message
			code="offer.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="profile/display.do?actorId=${offer.customer.id}"><spring:message
			code="offer.display" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${principalUserAccount.id == offer.customer.userAccount.id &&  offer.banned=='false'}">
				<li><a href="apply/customer/create.do?offerId=${offer.id}">
					<spring:message code="offer.apply"/>
				</a></li>
			</jstl:if>
		</display:column>
	</security:authorize>	
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">	
	<li>
		<a href="offer/customer/create.do?">
			<spring:message code="offer.create"/>
		</a>
	</li>
</security:authorize>