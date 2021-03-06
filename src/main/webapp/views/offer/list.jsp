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

<jstl:if test="${general}">
	<spring:message code="offer.searchText"/>
	<input type="text" value="" id="textSearch" />
	<input type="button" id="buttonSearch"
	value="<spring:message code="offer.search"/>" />
	
	<script type="text/javascript">
		$(document).ready(function(){
			var urlSearch = 'offer/customer/search.do?keyword=';
			$("#buttonSearch").click(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
			$("#buttonSearch").onsubmit(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
		});
	</script>
</jstl:if>
<display:table name="offers" id="offer" requestURI="${requestURI }" class="displaytag">
	
	<jstl:if test="${offer.banned}">
		<acme:column code="offer.title" property="title" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="offer.description" property="description" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="offer.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="offer.originPlace" property="originPlace" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="offer.destinationPlace" property="destinationPlace" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
	</jstl:if>
	<jstl:if test="${!offer.banned}">
		<acme:column code="offer.title" property="title" />
		<acme:column code="offer.description" property="description"/>
		<acme:column code="offer.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
		<acme:column code="offer.originPlace" property="originPlace" />
		<acme:column code="offer.destinationPlace" property="destinationPlace" />
	</jstl:if>
	
	<jstl:if test="${general!=true}">
		<jstl:if test="${offer.banned}">
			<acme:column code="offer.banned" property="banned" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		</jstl:if>
		<jstl:if test="${!offer.banned}">
			<acme:column code="offer.banned" property="banned"/>
		</jstl:if>
	</jstl:if>
			
	<display:column>
		<a href="offer/customer/display.do?offerId=${offer.id}"><spring:message
			code="offer.display" />
		</a>
	</display:column>
	
	<spring:message code="offer.customer" var="customerHeader" />
	<display:column title="${customerHeader}">
		<a href="profile/display.do?actorId=${offer.customer.id}"><jstl:out value="${offer.customer.name}"/>
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${myOfferOption!=true}">
	<security:authentication var="principalUserAccount" property="principal" />
		<display:column>
			<jstl:if test="${principalUserAccount.id != offer.customer.userAccount.id && offer.banned=='false'}">
				<jstl:set var="showButton" value="true" />
				<jstl:forEach items="${offer.applies }" var="apply">
					<jstl:if test="${apply.customer.userAccount.id == principalUserAccount.id }">
						<jstl:set var="showButton" value="false" />
					</jstl:if>
				</jstl:forEach>
				
				<jsp:useBean id="now" class="java.util.Date"/>
				<jstl:if test="${showButton && (offer.movingMoment gt now || offer.movingMoment== now) }">
					<form:form action="apply/customer/create.do?transactionId=${offer.id}"
						modelAttribute="apply" method="post">
						<acme:submit name="createApply" code="request.apply"/>
						<%-- <input type="submit" name="apply"
							value="<spring:message code="request.apply" />" />&nbsp; --%>
					</form:form>
				</jstl:if>
				
			</jstl:if>
		</display:column>
	</jstl:if>
	</security:authorize>	
	
	
	<security:authorize access="hasRole('ADMIN')">
	
		<display:column>
		<jstl:if test="${!offer.banned}">
			<a href="offer/customer/bann.do?offerId=${offer.id}"><spring:message
				code="offer.bann" />
			</a>
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