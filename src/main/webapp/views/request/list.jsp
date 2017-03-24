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
	<spring:message code="request.searchText"/>
	<input type="text" value="" id="textSearch" />
	<input type="button" id="buttonSearch"
	value="<spring:message code="request.search"/>" />
	
	<script type="text/javascript">
		$(document).ready(function(){
			var urlSearch = 'request/customer/search.do?keyword=';
			$("#buttonSearch").click(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
			$("#buttonSearch").onsubmit(function(){
				window.location.replace(urlSearch + $("#textSearch").val());
			});
		});
	</script>
</jstl:if>
<display:table name="requests" id="row" requestURI="${requestURI }" class="displaytag">
	
	<acme:column code="request.title" property="title"/>
	<acme:column code="request.description" property="description"/>
	<acme:column code="request.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="request.originPlace" property="originPlace" />
	<acme:column code="request.destinationPlace" property="destinationPlace" />
	
	<jstl:if test="${general!=true}">
		<acme:column code="request.banned" property="banned"/>
	</jstl:if>
			
	<spring:message code="request.request" var="requestHeader" />
	<display:column title="${requestHeader}">
		<a href="request/customer/display.do?requestId=${row.id}"><spring:message
			code="request.display" />
		</a>
	</display:column>
	
	<spring:message code="request.customer" var="customerHeader" />
	<display:column title="${customerHeader}">
		<a href="profile/display.do?actorId=${row.customer.id}"><jstl:out value="${row.customer.name}"/>
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${principalUserAccount.id != row.customer.userAccount.id &&  row.banned=='false'}">
				<a href="apply/customer/create.do?transactionId=${row.id}">
					<spring:message code="request.apply"/>
				</a>
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