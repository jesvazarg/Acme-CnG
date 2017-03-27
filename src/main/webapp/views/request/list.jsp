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
	
	<jstl:if test="${row.banned}">
		<acme:column code="request.title" property="title" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="request.description" property="description" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="request.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="request.originPlace" property="originPlace" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
		<acme:column code="request.destinationPlace" property="destinationPlace" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
	</jstl:if>
	<jstl:if test="${!row.banned}">
		<acme:column code="request.title" property="title"/>
		<acme:column code="request.description" property="description"/>
		<acme:column code="request.movingMoment" property="movingMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
		<acme:column code="request.originPlace" property="originPlace" />
		<acme:column code="request.destinationPlace" property="destinationPlace" />
	</jstl:if>
	
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
	<security:authentication var="principalUserAccount" property="principal" />
		<display:column>
			<jstl:if test="${principalUserAccount.id != row.customer.userAccount.id && row.banned=='false'}">
				<jstl:set var="showButton" value="true" />
				<jstl:forEach items="${row.applies }" var="apply">
					<jstl:if test="${apply.customer.userAccount.id == principalUserAccount.id }">
						<jstl:set var="showButton" value="false" />
					</jstl:if>
				</jstl:forEach>
				
				<jsp:useBean id="now" class="java.util.Date"/>
				<jstl:if test="${showButton && (row.movingMoment gt now || row.movingMoment== now) }">
					<form:form action="apply/customer/create.do?transactionId=${row.id}"
						modelAttribute="apply" method="post">
						<acme:submit name="createApply" code="request.apply"/>
						<%-- <input type="submit" name="apply"
							value="<spring:message code="request.apply" />" />&nbsp; --%>
					</form:form>
				</jstl:if>
				
			</jstl:if>
		</display:column>
	</security:authorize>	
	
		<security:authorize access="hasRole('ADMIN')">
	
		<display:column>
		<jstl:if test="${!row.banned}">
			<a href="request/customer/bann.do?requestId=${row.id}"><spring:message
				code="request.ban" />
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