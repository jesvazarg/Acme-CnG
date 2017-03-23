<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form method="post" action="comment/actor/edit.do" modelAttribute="comment" >

<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="postedMoment"/>
<form:hidden path="postedTo"/>
<form:hidden path="postedBy"/>
<form:hidden path="banned"/>

<acme:input code="comment.title" path="title"/>
<acme:textarea code="comment.text" path="text"/>

<form:label path="starsNumber">
		<spring:message code="comment.stars" />
</form:label>	
<form:select path="starsNumber">
		<form:option value="0" label="0" />		
		<form:option value="1" label="1" />
		<form:option value="2" label="2" />
		<form:option value="3" label="3" />
		<form:option value="4" label="4" />
		<form:option value="5" label="5" />
</form:select>
<form:errors path="starsNumber" cssClass="error" />


</form:form>