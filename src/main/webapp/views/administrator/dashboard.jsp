<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	<h2><spring:message code="admin.dashboard.levelC"/></h2>

	<b><spring:message code="admin.dashboard.c1"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.c2"/></b>
	<br/>
	&nbsp&nbsp&nbsp&nbsp<jstl:out value="${avgTransactionsPerCustomer}" />
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.c3"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.c4"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.c5"/></b>
	
	<br/><br/><br/>
	<h2><spring:message code="admin.dashboard.levelB"/></h2>

	<b><spring:message code="admin.dashboard.b1"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.b2"/></b>
	<br/>
	&nbsp&nbsp&nbsp&nbsp<jstl:out value="${avgCommentsPerActor}" />
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.b3"/></b>
	
	<br/><br/><br/>
	<h2><spring:message code="admin.dashboard.levelA"/></h2>

	<b><spring:message code="admin.dashboard.a1"/></b>
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
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.a2"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.a3"/></b>
	
	<br/><br/>
	<b><spring:message code="admin.dashboard.a4"/></b>
	<display:table name="actorMoreGotMessages" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">
		
		<spring:message code="admin.dashboard.actor.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}" sortable="true" />
	
		<spring:message code="admin.dashboard.actor.email" var="emailHeader" />
		<display:column property="email" title="${emailHeader}"	sortable="false" />
		
		<spring:message code="admin.dashboard.actor.phoneNumber" var="phoneNumberHeader" />
		<display:column property="phoneNumber" title="${phoneNumberHeader}"	sortable="false" />
		
	</display:table>
	
</security:authorize>