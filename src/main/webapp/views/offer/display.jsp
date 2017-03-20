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
			<jstl:out value="${offer.originPlace}"/>
		</li>
		<li>
			<b><spring:message code="offer.destinationPlace" /></b>
			<jstl:out value="${offer.destinationPlace}"/>
		</li>
		<!-- <li>
			<b><spring:message code="offer.comments" /></b>
			<display:table name="${offer.comments}" id="row" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
				
				<spring:message code="profile.comments.title" var="titleHeader" />
				<display:column property="title" title="${titleHeader}" sortable="true" />
				
				<spring:message code="profile.comments.postedMoment" var="postedMomentHeader" />
				<display:column property="postedMoment" title="${postedMomentHeader}" sortable="true" />
				
				<spring:message code="profile.comments.text" var="textHeader" />
				<display:column property="text" title="${textHeader}" sortable="false" />
				
				<spring:message code="profile.comments.starsNumber" var="starsNumberHeader" />
				<display:column property="starsNumber" title="${starsNumberHeader}" sortable="true" />
				
				<spring:message code="profile.comments.banned" var="bannedHeader" />
				<display:column property="banned" title="${bannedHeader}" sortable="true" />
				
				<spring:message code="profile.comments.postedBy" var="postedByHeader" />
				<display:column property="postedBy" title="${postedByHeader}" sortable="true" />
				
			</display:table>
		</li> -->
		
	</ul>
</div>
