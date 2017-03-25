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
	
	<spring:message code="offer.customer" var="customerHeader" />
	<display:column title="${customerHeader}">
		<a href="profile/display.do?actorId=${offer.customer.id}"><jstl:out value="${offer.customer.name}"/>
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
	<security:authentication var="principalUserAccount" property="principal" />
		<display:column>
			<jstl:if test="${principalUserAccount.id != offer.customer.userAccount.id &&  offer.banned=='false'}">
				<form:form action="apply/customer/create.do?transactionId=${offer.id}"
					modelAttribute="apply" method="post">
					<acme:submit name="createApply" code="offer.apply"/>
				</form:form>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="offer/customer/bann.do?offerId=${offer.id}"><spring:message
				code="offer.bann" />
			</a>
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