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
		
		<li>
			<b><spring:message code="offer.comments" /></b>
			<display:table name="${comments}" id="row" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
				<spring:message code="offer.comments.title" var="titleHeader" />
				<display:column property="title" title="${titleHeader}" sortable="true" />
				
				<spring:message code="offer.comments.postedMoment" var="postedMomentHeader" />
				<display:column property="postedMoment" title="${postedMomentHeader}" sortable="true" />
				
				<spring:message code="offer.comments.text" var="textHeader" />
				<display:column property="text" title="${textHeader}" sortable="false" />
				
				<spring:message code="offer.comments.starsNumber" var="starsNumberHeader" />
				<display:column property="starsNumber" title="${starsNumberHeader}" sortable="true" />
				
				<spring:message code="offer.comments.banned" var="bannedHeader" />
				<display:column property="banned" title="${bannedHeader}" sortable="true" />
				
				<spring:message code="offer.comments.postedBy" var="postedByHeader" />
				<display:column title="${postedByHeader}">
					<a href="profile/display.do?actorId=${row.postedBy.id}"><jstl:out value="${row.postedBy.name }"/> </a>
				</display:column>
				
				<security:authorize access="hasRole('ADMIN')">
					<display:column>
						<jstl:if test="${!row.banned}">
							<form:form action="comment/actor/ban.do?commentId=${row.id}" modelAttribute="comment">
								<input type="submit" name="ban" value="<spring:message code="offer.bann" />" />
							</form:form>
						</jstl:if>
						
					</display:column>
				</security:authorize>
			
			</display:table>
		</li>
		
	</ul>
</div>

 <security:authorize access="isAuthenticated()">
		  		<acme:button url="comment/actor/create.do?commentablePostedToId=${offer.id}" code="offer.newComment"/>
	</security:authorize>
		
		

<security:authorize access="hasRole('CUSTOMER')">
	<div>
		<jstl:if test="${principalUserAccount.id == offer.customer.userAccount.id &&  offer.banned=='false'}">
			<li><a href="apply/customer/create.do?offerId=${offer.id}">
				<spring:message code="offer.apply"/>
			</a></li>
		</jstl:if>
	</div>
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">

		<jstl:if test="${isCustomer}">
			<li><a href="offer/customer/edit.do?offerId=${offer.id}">
				<spring:message code="offer.edit"/>
			</a></li>
		</jstl:if>
	
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
		
			<jstl:if test="${!isCustomer}">
				<li><a href="apply/customer/create.do?offerId=${offer.id}">
					<spring:message code="offer.apply"/>
				</a></li>
			</jstl:if>
		
	</security:authorize>	
	
