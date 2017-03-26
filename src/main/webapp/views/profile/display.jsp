<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<ul>
		<li>
			<b><spring:message code="profile.name" /></b>
			<jstl:out value="${profile.name}"/>
		</li>
		<li>
			<b><spring:message code="profile.email" /></b>
			<jstl:out value="${profile.email}"/>
		</li>
		<li>
			<b><spring:message code="profile.phoneNumber" /></b>
			<jstl:out value="${profile.phoneNumber}"/>
		</li>
		<li>
			<b><spring:message code="profile.comments" /></b>
			<display:table name="${comments}" id="row" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
				<jstl:if test="${!row.banned}">
					<acme:column code="profile.comments.title" property="title" sortable="true"/>
					<acme:column code="profile.comments.postedMoment" property="postedMoment" sortable="true"/>
					<acme:column code="profile.comments.text" property="text"/>
					<acme:column code="profile.comments.starsNumber" property="starsNumber" sortable="true"/>
					<acme:column code="profile.comments.banned" property="banned" sortable="true"/>
					
					<spring:message code="profile.comments.postedBy" var="postedByHeader" />
					<display:column title="${postedByHeader}">
						<a href="profile/display.do?actorId=${row.postedBy.id}"><jstl:out value="${row.postedBy.name }"/> </a>
					</display:column>
				</jstl:if>
				
				<jstl:if test="${row.banned}">
					<acme:column code="profile.comments.title" property="title" sortable="true" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
					<acme:column code="profile.comments.postedMoment" property="postedMoment" sortable="true" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
					<acme:column code="profile.comments.text" property="text" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
					<acme:column code="profile.comments.starsNumber" property="starsNumber" sortable="true" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
					<acme:column code="profile.comments.banned" property="banned" sortable="true" style="background:Tomato;border:solid;border-color:black;font-weight:bold"/>
					
					<spring:message code="profile.comments.postedBy" var="postedByHeader" />
					<display:column title="${postedByHeader}">
						<a href="profile/display.do?actorId=${row.postedBy.id}"><jstl:out value="${row.postedBy.name }"/> </a>
					</display:column>
				</jstl:if>
				
				<display:column>
					<jstl:if test="${isAdmin or principal==row.postedBy}">
						<form:form action="comment/actor/delete.do?commentId=${row.id}" modelAttribute="comment">
								<input type="submit" name="delete" value="<spring:message code="profile.delete" />" />
						</form:form>
					</jstl:if>
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
		
		<security:authorize access="isAuthenticated()">
		  		<acme:button url="comment/actor/create.do?commentablePostedToId=${profile.id}" code="profile.newComment"/>
		</security:authorize>
		
		
		<jstl:if test="${same}">
			<acme:button url="folder/actor/list/inBox.do" code="profile.inBox" />
			<acme:button url="folder/actor/list/outBox.do" code="profile.outBox" />
		</jstl:if>
		
	</ul>
</div>
