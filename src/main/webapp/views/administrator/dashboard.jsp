<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	<h2><spring:message code="admin.dashboard.levelC"/></h2>

	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c1"/></b></legend>
		<br/>
		&nbsp&nbsp&nbsp&nbsp<jstl:out value="${ratioOfferPerRequest}" />
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c2"/></b></legend>
		<br/>
		&nbsp&nbsp&nbsp&nbsp<jstl:out value="${avgTransactionsPerCustomer}" />
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c3"/></b></legend>
		<br/>
		
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c4"/></b></legend>
		<br/>
		&nbsp&nbsp&nbsp&nbsp<jstl:out value="${customerWithMostAcceptedApplies.name}" />
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c5"/></b></legend>
		<display:table name="findCustomerWithMostDeniedApplications" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phoneNumber" property="phoneNumber"/>
			
		</display:table>
	</fieldset>
	
	<br/>
	<h2><spring:message code="admin.dashboard.levelB"/></h2>
	
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.b1"/></b></legend>
		<br/>
		&nbsp&nbsp&nbsp&nbsp<jstl:out value="${findAvgPerCommentable}" />
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.b2"/></b></legend>
		<br/>
		&nbsp&nbsp&nbsp&nbsp<jstl:out value="${avgCommentsPerActor}" />
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.b3"/></b></legend>
		<display:table name="find10PercentAvgCommentsPerActor" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phoneNumber" property="phoneNumber"/>
			
		</display:table>
	</fieldset>
	
	<br/>
	<h2><spring:message code="admin.dashboard.levelA"/></h2>
	
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.a1"/></b></legend>
		<ul>
			<li>
				<b><spring:message code="admin.dashboard.min"/>:</b>
				<jstl:out value="${minAvMaxMessagesPerActor[0]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.avg"/>:</b>
				<jstl:out value="${minAvMaxMessagesPerActor[1]}"/>
			</li>
			<li>
				<b><spring:message code="admin.dashboard.max"/>:</b>
				<jstl:out value="${minAvMaxMessagesPerActor[2]}"/>
			</li>
		</ul>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.a2"/></b></legend>
		<br/>
		
		<br/><br/>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.a3"/></b></legend>
		<display:table name="findActorWithMostMessagesSent" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phoneNumber" property="phoneNumber"/>
			
		</display:table>
	</fieldset>
	
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.a4"/></b></legend>
		<display:table name="actorMoreGotMessages" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
			
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phoneNumber" property="phoneNumber"/>
			
		</display:table>
	</fieldset>
	
</security:authorize>