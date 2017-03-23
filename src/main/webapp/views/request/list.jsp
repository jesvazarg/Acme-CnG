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


<display:table name="requests" id="row" requestURI="${requestURI }" class="displaytag">
	
	<acme:column code="request.title" property="title"/>
	<acme:column code="request.description" property="description"/>
	<acme:column code="request.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="request.originPlace" property="originPlace" />
	<acme:column code="request.destinationPlace" property="destinationPlace" />
	
	<jstl:if test="${general!=true}">
		<acme:column code="request.banned" property="banned"/>
	</jstl:if>
			
	<display:column>
		<a href="request/customer/display.do?requestId=${row.id}"><spring:message
			code="request.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="profile/display.do?actorId=${row.customer.id}"><spring:message
			code="request.display" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${principalUserAccount.id == row.customer.userAccount.id &&  row.banned=='false'}">
				<li><a href="apply/customer/create.do?requestId=${row.id}">
					<spring:message code="request.apply"/>
				</a></li>
			</jstl:if>
		</display:column>
	</security:authorize>	
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">	
	<li>
		<a href="request/customer/create.do?">
			<spring:message code="request.create"/>
		</a>
	</li>
</security:authorize>